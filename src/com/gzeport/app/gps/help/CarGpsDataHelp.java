package com.gzeport.app.gps.help;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.gson.Gson;
import com.gzeport.app.gps.common.FileManagerBean;
import com.gzeport.app.gps.common.NSGPSConstats;
import com.gzeport.app.gps.common.VehicleInfoXmlBean;
import com.gzeport.app.gps.dao.IVehicleInfoDao;
import com.gzeport.app.gps.pojo.VehicleInfo;
import com.gzeport.app.gps.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CarGpsDataHelp {
	Log log = LogFactory.getLog(this.getClass().getName());
	
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	private FileManagerBean gpsFileManagerBean;
	
	private IVehicleInfoDao vehicleInfoDao;
	
	  Lock lock = new ReentrantLock();// 锁  
    
	public IVehicleInfoDao getVehicleInfoDao() {
		return vehicleInfoDao;
	}
	public void setVehicleInfoDao(IVehicleInfoDao vehicleInfoDao) {
		this.vehicleInfoDao = vehicleInfoDao;
	}


	private  static Gson gson = new Gson();
	
	public FileManagerBean getGpsFileManagerBean() {
		return gpsFileManagerBean;
	}
	public void setGpsFileManagerBean(FileManagerBean gpsFileManagerBean) {
		this.gpsFileManagerBean = gpsFileManagerBean;
	}
	
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}


	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @功能: 清除无用的KEY 前缀+数字KEY 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-14 下午5:21:44
	 */
	public void delRedisKey(String mathString){
		for (int i=0;i<300;i++){
			final String delKey =mathString+i;
			log.info("delKey>>>"+delKey);
			this.redisTemplate.execute(new RedisCallback<Object>() {
			  public Object doInRedis(RedisConnection connection)   
			       throws DataAccessException { 
				  	log.info("执行删除。。。。。。。");
				     connection.del(redisTemplate.getStringSerializer().serialize(delKey));
				    
				     return null;
				   }
		    });
		}
	}
	
	/**
	 * @功能: GPS数据采集的方法 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-24 下午2:19:15
	 */
	@SuppressWarnings("rawtypes")
	public void getGpsDataByJredis(){
		 final   String   nowdateStr  =new SimpleDateFormat("yyyyMMdd").format(new Date());
    	 Calendar calendar = Calendar.getInstance();
    	 calendar.setTime(new Date());
    	 calendar.add(Calendar.DAY_OF_MONTH,-1);
    	 Date  secondDate = calendar.getTime();
    	 final String  lastdateStr  =new SimpleDateFormat("yyyyMMdd").format(secondDate) ;
    	 final String fileNameBase =this.gpsFileManagerBean.getLocalBaseDir()+File.separator+this.gpsFileManagerBean.getLocalUploadDir();
    	 VehicleInfo vinfo= null;
    	 try{
    	   	 vinfo=	(VehicleInfo) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
    			 public  VehicleInfo  doInRedis(RedisConnection connection)   
    			      throws DataAccessException {  
    					byte[] cardata= connection.rPop(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_QUEUE_GPS));   
    					VehicleInfo info  = null;
    					if(cardata!=null){ //如果从队列取到了数据
    						String jsonStr =redisTemplate.getStringSerializer().deserialize(cardata);
    log.debug("cardata is >>"+jsonStr); 
    					    info = gson.fromJson(jsonStr, VehicleInfo.class);   
    					    String carPlate = info.getPlate();
    					    info.setPlatecolor(carPlate.substring(carPlate.length()-1));
    					    info.setPlate(carPlate.substring(0,carPlate.length()-2));
    					    String carPinyinKeyStr =StringUtil.getPinyin(info.getPlate());  //把车牌号由中文转为拼音字符 后的字符串
    					    //====判断匹配的车 生成报文到目录========
    					    Set<byte[]>   carSet = connection.hKeys(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE));
    					    if(carSet!=null){  //如果需要传输GPS的车辆集合不为空  取出key 车牌号集合
    					    	 java.util.Iterator<byte[]> it =  carSet.iterator();
    					    	 while (it.hasNext()){
    					    		 String carNo = redisTemplate.getStringSerializer().deserialize(it.next());
    					    		 if(carNo.equals(carPinyinKeyStr)){
    					 		log.info("匹配到了集合中的元素===>需要生成GPS数据报文到南沙系统目录");
    					 				 //**生成报文到指定目录 **///
    					 				byte[] inAreaNo = connection.hGet(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE),
    					 						redisTemplate.getStringSerializer().serialize(carNo));
    					 				info.setInAreaNo(redisTemplate.getStringSerializer().deserialize(inAreaNo));  //设置入区编号（车次）
    					 				XStream xs = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
    					 				xs.alias("VehicleInfo", VehicleInfoXmlBean.class);
    					 				VehicleInfoXmlBean xmlBean = new VehicleInfoXmlBean(info); //待传入对象转换为XML对象
    					 				String xml = NSGPSConstats.PATTERN + StringUtil.trim(xs.toXML(xmlBean));
    					 				String fileName =fileNameBase+ File.separator+"GPS"+StringUtil.parseDateToString(new Date(),"yyyyMMdd")+StringUtil.getUniqueId()+".xml";
    					 				StringUtil.doc2XmlFile(xml, fileName, null);
    					 			} //====生成报文结束========
    					    	 }
    					    }
    					 
    					    String oldKey ="p"+lastdateStr+carPinyinKeyStr;  //形如p20130512yueAH19DE
    					    String redisKey= "p"+nowdateStr+carPinyinKeyStr;
    					     Long lzscore =StringUtil.getUniqueId(); //生成分数排序号
    						 String jsonValue =gson.toJson(info);
    					    /*******保存在redis操作******/
    						connection.select(1);
    						Boolean backValue =connection.exists(redisTemplate.getStringSerializer().serialize(redisKey));
    						if(backValue){
    							connection.zAdd(redisTemplate.getStringSerializer().serialize(redisKey),lzscore,
    					        	redisTemplate.getStringSerializer().serialize(jsonValue));
    						}else{
    							connection.del(redisTemplate.getStringSerializer().serialize(oldKey));
    							connection.zAdd(redisTemplate.getStringSerializer().serialize(redisKey),lzscore,
    					        		redisTemplate.getStringSerializer().serialize(jsonValue));
    						}
    						connection.select(0);
    						/*******保存在redis操作结束******/  
    					}
    					return info ;
    			 	}
    		});
    	 }catch(Exception ex){
    		 log.info("系统出现异常"+ex.getMessage());
    	 }
		//最后入物理数据库操作
    	 if(vinfo!=null){
    		 this.vehicleInfoDao.addVehicleInfo(vinfo);	 
    	 }
	}
	
	/**
	 * @功能: 解析GPS数据请求报文 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-20 下午2:27:02
	 */
	public synchronized void processReciveFile(){
		//lock.lock();
		try {
			if (!StringUtil.trimNull(this.gpsFileManagerBean.getLocalBaseDir()).equals("")&& !StringUtil.trimNull(this.gpsFileManagerBean.getLocalDownDir()).equals("")) {
				String basePath = StringUtil.trimNull(gpsFileManagerBean.getLocalBaseDir());
				File file = new File(basePath);
				if (!file.exists()) {  // D:/NSGPS
					file.mkdir();
				}
				String path = basePath+ File.separator+ StringUtil.trimNull(gpsFileManagerBean.getLocalDownDir());
				file = new File(path);   // D:/NSGPS/out
				if (!file.exists()) {
					file.mkdir();
				}
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					if (files != null && files.length > 0) {
						for (int i = 0; i < files.length; i++) {
							File tempFile = files[i];   //待解析文件
							if (tempFile != null && tempFile.isFile()&& tempFile.canWrite()) {
								String filePath = StringUtil.toUpperString(tempFile.getName());
								boolean bool = false;
								boolean parseBool = true;  //是否需要解析
								String docFileDir="";
								if (filePath.endsWith(".XML") ) { 			// 目前只处理XML文件
									log.info("filePath=" + filePath);
									SAXReader saxReader = new SAXReader();
									Document document = null;
									
										document = saxReader.read(tempFile);
									if (document != null) {
										Element root = document.getRootElement();
										//以下判断文件是否需要解析入库。如果需要则parseBool=ture;
										log.info("root="+root);
										if (root != null) {	
											Element requsetTpye = root.element("requsetTpye");		
											String reqtype="";
											if(requsetTpye!=null){
												reqtype =requsetTpye.getTextTrim();
												if(reqtype.equals("1")||reqtype.equals("2")){
													parseBool= this.processXmlFile( root);
													if(parseBool){
														String backup = path+ File.separator+ StringUtil.getCurrTime(3);
														File dir = new File(backup);
														if (!dir.exists()) {
															dir.mkdir();
														}
														StringUtil.copyFile(tempFile.getAbsolutePath(), backup, true);  //备份并从解析目录移除
													}else{
														String errbackup = path+ File.separator+ this.gpsFileManagerBean.getLocalDowndErrorDir();
														File dir = new File(errbackup);
														if (!dir.exists()) {
															dir.mkdir();
														}
														StringUtil.copyFile(tempFile.getAbsolutePath(), errbackup, true);  //备份到错误目录并从解析目录删除
													}
												}else{
													log.info("非正常的，无法识别的报文！");
													//不解析，直接备份
													String errbackup = path+ File.separator+ this.gpsFileManagerBean.getLocalDowndErrorDir();
													File dir = new File(errbackup);
													if (!dir.exists()) {
														dir.mkdir();
													}
													StringUtil.copyFile(tempFile.getAbsolutePath(), errbackup, true);  //备份到错误目录并从解析目录删除
												}
											}
										}
									}
								}else{ 
									log.info("非XML文件！");
									//不解析，直接备份
									String errbackup = path+ File.separator+ this.gpsFileManagerBean.getLocalDowndErrorDir();
									File dir = new File(errbackup);
									if (!dir.exists()) {
										dir.mkdir();
									}
									StringUtil.copyFile(tempFile.getAbsolutePath(), errbackup, true);  //备份到错误目录并从解析目录删除
								}
							}
					   }
				   }
				}
			}else {
				log.info("未指明解释报文的根目录，请检查配置文件！");
			}
		} catch ( Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}finally{
		//	lock.unlock();// 释放锁  
		}
		
	}
	/**
	 * @功能: 解析报文方法 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-20 下午3:01:27
	 */
	private boolean processXmlFile(Element el ) {
		Element root = el;
		Element requsetTpye = root.element("requsetTpye");		
		Element sendUser = root.element("sendUser");
		Element reciveUser = root.element("reciveUser");		
		Element plate = root.element("plate");
		Element carInAreaNo = root.element("carInAreaNo");		
		Element startTime = root.element("startTime");
		Element endTime = root.element("endTime");	
		
		String xmlrequsetTpye = root.element("requsetTpye").getTextTrim();	
		String xmlsendUser = root.element("sendUser").getTextTrim();	
		String xmlreciveUser = root.element("reciveUser").getTextTrim();		
		String xmlplate = root.element("plate").getTextTrim();
		String xmlcarInAreaNo = root.element("carInAreaNo").getTextTrim();		
		String xmlstartTime = root.element("startTime").getTextTrim();
		String xmlendTime ="";
		if(endTime!=null){
			xmlendTime = root.element("endTime").getTextTrim();
		}
		Date sdate =null;
		Date edate =null;
		boolean isOk = true;
		try {
			if(xmlstartTime!=null&&!"".equals(xmlstartTime)){
				sdate = StringUtil.parseDate(xmlstartTime, "yyyy-MM-dd HH:mm:ss");
			}else{
				isOk=false;
			}
			if(xmlendTime!=null&&!"".equals(xmlendTime)){
				edate = StringUtil.parseDate(xmlendTime, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if(xmlplate==null||"".equals(xmlplate)||xmlcarInAreaNo==null||"".equals(xmlcarInAreaNo)){
			isOk=false;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
		strBuffer.append("<ResponseMessage>").append("\n");
		if(xmlrequsetTpye.equals(NSGPSConstats.REQUEST_TYPE_OF1)){
			strBuffer.append("<responseTpye>").append(NSGPSConstats.REQUEST_TYPE_OF1).append("</responseTpye>").append("\n");
		}else{
			strBuffer.append("<responseTpye>").append(NSGPSConstats.REQUEST_TYPE_OF2).append("</responseTpye>").append("\n");
		}
		strBuffer.append("<sendUser>").append(xmlreciveUser ).append("</sendUser>").append("\n");
		strBuffer.append("<reciveUser>").append(xmlsendUser).append("</reciveUser>").append("\n");
		strBuffer.append("<plate>").append(xmlplate).append("</plate>").append("\n");
		strBuffer.append("<carInAreaNo>").append(xmlcarInAreaNo).append("</carInAreaNo>").append("\n");
		if(isOk){
			strBuffer.append("<responseCode>").append(NSGPSConstats.RESPONSE_SUCCESS).append("</responseCode>").append("\n");
		}else{
			strBuffer.append("<responseCode>").append(NSGPSConstats.RESPONSE_FAILURE).append("</responseCode>").append("\n");
		}
		strBuffer.append("<startTime>").append(xmlstartTime).append("</startTime>").append("\n");
		
		strBuffer.append("</ResponseMessage>").append("\n");
		String fileName =this.gpsFileManagerBean.getLocalBaseDir()+File.separator+this.gpsFileManagerBean.getLocalUploadDir();
		fileName =fileName+File.separator+"GPS"+StringUtil.parseDateToString(new Date(),"yyyyMMddHHmmss")+StringUtil.getUniqueId()+".xml";
		StringUtil.doc2XmlFile(strBuffer.toString(),fileName , null);
		if(!isOk){
			return isOk;
		}
	try{
		if(xmlrequsetTpye.equals(NSGPSConstats.REQUEST_TYPE_OF1)){
			String[] plateArray= xmlplate.split("\\|",-1);
			String[] inAreaNoArray= xmlcarInAreaNo.split("\\|",-1);
			int len =plateArray.length;
			for(int i =0;i<len;i++){
				String carPlate =plateArray[i];
				String carAreaNo =inAreaNoArray[i];
log.info("车牌号是:"+carPlate+"入区编号是:"+i+carAreaNo);				
				
				boolean bool =this.getGpsDataByRedis(carPlate, carAreaNo,sdate, edate);
				if(bool)
					this.updateSetData(carPlate,carAreaNo, NSGPSConstats.CARPLATE_ADD);  //更新静态集合
			}
		}else if(xmlrequsetTpye.endsWith(NSGPSConstats.REQUEST_TYPE_OF2)){
			String[] plateArray= xmlplate.split("\\|",-1);
			for(String carPlate:plateArray){
				this.updateSetData(carPlate, null,NSGPSConstats.CARPLATE_DEL); //更新静态集合
			}
		}
	}catch(Exception e){
		log.info("系统执行出错了"+e.getMessage());
		return false;
	}
		
		return true;
	}
	
	/**
	 * @功能: 获取时间段的redis -GPS数据 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-21 上午9:27:43
	 */
	@SuppressWarnings("unchecked")
	public boolean getGpsDataByRedis( final String carno,final String inAreaNo,final Date startdate, final Date enddate) {
			 boolean bool=true;   
			  SimpleDateFormat dformat =new SimpleDateFormat("yyyyMMdd");
			  Date nowDate= new Date();
			  final String   dateStr =  dformat.format(nowDate);
			try {
				bool =	(Boolean) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
					 public Boolean doInRedis(RedisConnection connection)   
					      throws DataAccessException {   
					      @SuppressWarnings("unused")
					      String redisKey ="p"+dateStr+StringUtil.getPinyin(carno);
						 //zRevRange根据score值去倒序排序||zrange 顺序排列
					      connection.select(1);
					      Set<byte[]> k = connection.zRevRange(redisTemplate.getStringSerializer().serialize(redisKey), 0, -1);
					   //   List<VehicleInfo> infoList = new ArrayList<VehicleInfo>();
					     VehicleInfo instance =null;
					    if( k.size()==0)
					    	log.info("^^^^^^^^为空^^^^^^^^^^^^");
						for(byte[] value:k){
							String jsonStr =redisTemplate.getStringSerializer().deserialize(value);
							log.info(jsonStr);
							if(jsonStr!=null){
								instance = gson.fromJson(jsonStr, VehicleInfo.class);
							}
							if(instance!=null){
								//返回某时间段的数据
								if(instance.getGpstime().after(startdate)){
									if(enddate!=null&&!"".equals(enddate)){
										if(instance.getGpstime().before(enddate)) {
											instance.setInAreaNo(inAreaNo);
											vehicleInfoDao.addVehicleInfo(instance);
										}
										
									}else{
										instance.setInAreaNo(inAreaNo);
										vehicleInfoDao.addVehicleInfo(instance);
									}
								}
							}
						}
						  connection.select(0);
						  return true;
					  }});   
			}catch(JedisConnectionException j){
				log.info("或redis 服务连接没成功！"+j.getMessage());
			    bool =false ;
			}catch (Exception e) {
				 bool =false ;
				 log.info("或redis 是连接超时了 ！"+e.getMessage());
				e.printStackTrace();
			} finally{
				
			}
			return bool;
		}
	
	/**
	 * @功能: 更新redis hash 状态表 (考虑稳定性与出于多重安全保障 随后需要同时更新物理数据库表)
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-20 下午3:49:30
	 */
	public void updateSetData( final String carNo,final String carAreaNo, final String flag){
		
		 this.getRedisTemplate().execute(new RedisCallback<Object>() {   
			 public Boolean doInRedis(RedisConnection connection)   
			      throws DataAccessException { 
				 boolean issuccess = true;
				 if(NSGPSConstats.CARPLATE_ADD.equals(flag)){
					 issuccess = connection.hSet(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
						redisTemplate.getStringSerializer().serialize(carNo),
						redisTemplate.getStringSerializer().serialize(carAreaNo));
				 }else if(NSGPSConstats.CARPLATE_DEL.equals(flag)){
					  boolean ishaskey = connection.hExists(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
								redisTemplate.getStringSerializer().serialize(carNo));
						if(ishaskey){
							issuccess =	connection.hDel(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
									redisTemplate.getStringSerializer().serialize(carNo));
						}
					}
				 return issuccess;
			 }});  
	}
}
