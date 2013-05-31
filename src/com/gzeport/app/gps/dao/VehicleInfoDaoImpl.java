package com.gzeport.app.gps.dao;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.gzeport.app.gps.common.FileManagerBean;
import com.gzeport.app.gps.common.NSGPSConstats;
import com.gzeport.app.gps.common.ResponseXmlBean;
import com.gzeport.app.gps.common.VehicleInfoXmlBean;
import com.gzeport.app.gps.pojo.NsCarInfo;
import com.gzeport.app.gps.pojo.VehicleInfo;
import com.gzeport.app.gps.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * @ClassName VehicleInfoDaoImpl
 * @Description  GPS车辆信息 DAO层 开启与停止具体处理类
 * @author luyd luyuandeng@gzeport.com
 * @date 2013-5-28
 */
public class VehicleInfoDaoImpl extends AbstractDAO implements IVehicleInfoDao,Serializable {
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	private static final long serialVersionUID = -5942481889688418515L;
	
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	private FileManagerBean gpsFileManagerBean;
	
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
	 * @功能: 保存对象 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:49:15 
	 */
	public void addVehicleInfo( VehicleInfo instance) {
		 this.getHibernateTemplate().saveOrUpdate(instance);
	}

	
	/**
	 * @功能: 启动GPS数据传输 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-24 下午3:23:12
	 */
	public ResponseXmlBean startGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime,
			String eTime) {
		ResponseXmlBean xmlbean = new ResponseXmlBean();
		xmlbean.setInAreaNo(inAreaNo);
		xmlbean.setPlate(plate);
		xmlbean.setStartTime(sTime);
		xmlbean.setEndTime(eTime);
		xmlbean.setResponseCode(queryType);
		boolean  isSuccess =true ;
		if(this.gpsFileManagerBean.isLocalbool()) { //做一个是否需要用户验证的开关
			  isSuccess =	this.ValidateUser(userName, password, NSGPSConstats.SYS_FUNID); //用户验证
		}
		if(isSuccess){  //通过验证
			//判断必填的参数有没有为空
			if((queryType!=null&&plate!=null&&inAreaNo!=null&&sTime!=null)&&
					(!queryType.equals("")&&!plate.equals("")&&!inAreaNo.equals("")&&!sTime.equals(""))){
			}else {
				xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
				xmlbean.setMessage("failure: the args is not  right ,please check it!");
				return xmlbean;
			}
			try {
				if(queryType.equals(NSGPSConstats.REQUEST_TYPE_OF1)){ //开始请求
					Date startdate =null;
				    String[] plateArray= plate.split("\\|",-1);   //如果查询多个车牌号的是以|分隔的 所以要分割取出来
					String[] inAreaNoArray= inAreaNo.split("\\|",-1);    
					int len =plateArray.length;
					String carPlate =null;   
					String carAreaNo =null;
					/** * @查询历史数据 */
					  if(eTime!=null&&!"".equals(eTime)){ 
						    startdate = StringUtil.parseDate(sTime, "yyyy-MM-dd HH:mm:ss");
						    Date endDate = StringUtil.parseDate(eTime, "yyyy-MM-dd HH:mm:ss");
							for(int i =0;i<len;i++){   //取得所有车牌号
							    carPlate =plateArray[i];
								carAreaNo =inAreaNoArray[i];
								List  list =	 this.getGpsHistoryData(carPlate, carAreaNo,startdate, endDate);   //转到执行查询历史数据的方法
								if(list!=null&&list.size()>0){
									xmlbean.setMessage(" has  "+list.size()+"records!");
								}
							}
							xmlbean.setResultCode( NSGPSConstats.RESPONSE_SUCCESS);
					  }else{ /***@请求实时数据的 */
					log.info(plate+"请求实时GPS数据！");
							startdate = StringUtil.parseDate(sTime, "yyyy-MM-dd HH:mm:ss");
							for(int i =0;i<len;i++){
								carPlate =plateArray[i];
							    carAreaNo =inAreaNoArray[i];
								if(carAreaNo!=null)
								this.updateSetData(carPlate,carAreaNo, NSGPSConstats.CARPLATE_ADD);  //添加到集合和 更新数据库表
							}
							xmlbean.setResultCode( NSGPSConstats.RESPONSE_SUCCESS);
					  }
				}else{
					xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
					xmlbean.setMessage( " the requestType is not right!");
				}
			} catch (ParseException e) {
				xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
				xmlbean.setMessage( "  System has  exception  case by "+e.getMessage() );
				e.printStackTrace();
			}
			
		}else{ //用户验证不通过
			xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
			xmlbean.setMessage("userinfo validate error ,username or password input error!");
		}
 		return xmlbean;
	}
	
	
   /**
     * @功能: 查询历史数据 
     * @编码: luyd luyuandeng@gzeport.com 2013-5-24 下午3:59:32
     */
  	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getGpsHistoryData(final String carPlate, final String carAreaNo,
			final Date startdate, final Date endDate) {
  		 List<VehicleInfo> mylist = null;
  		try{
  		mylist = (List <VehicleInfo> )this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List gpsList  = null;
				String hql = " from  VehicleInfo info where info.Plate =? and info.Gpstime> ? and info.Gpstime<?";
				Query query  =session.createSQLQuery(hql );
				query.setString(0, carPlate);
				query.setDate(1, startdate);
				query.setDate(2, endDate);
				gpsList =query.list();
				log.info("取得记录数是："+gpsList.size());
				return gpsList;
			}
		});
  		if(mylist!=null&&mylist.size()>0){
  			String fileNameBase =this.gpsFileManagerBean.getLocalBaseDir()+File.separator+this.gpsFileManagerBean.getLocalUploadDir();
  	  		VehicleInfoXmlBean xmlBean =null ;
  	  		for(VehicleInfo vinfo :mylist){
  	  			XStream xs = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
  					xs.alias("VehicleInfo", VehicleInfoXmlBean.class);
  					vinfo.setInAreaNo(carAreaNo);   //设置入区编号  车次
  					xmlBean = new VehicleInfoXmlBean(vinfo); //待传入对象转换为XML对象
  					String xml = NSGPSConstats.PATTERN + StringUtil.trim(xs.toXML(xmlBean));
  					String fileName =fileNameBase+ File.separator+"GPS"+StringUtil.parseDateToString(new Date(),"yyyyMMdd")+StringUtil.getUniqueId()+".xml";
  					StringUtil.doc2XmlFile(xml, fileName, null);  //生成数据报文
  	  		}
  		}
		}catch (Exception e) {
			 log.info("执行出错了了 ！"+e.getMessage());
		} finally{
		}
		return mylist;
	}


	/**
	 * @功能: 更新redis hash 状态表 (考虑稳定性与出于多重安全保障 随后需要同时更新物理数据库表)
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-20 下午3:49:30
	 */
	public Boolean updateSetData( final String carNo,final String carAreaNo, final String flag){
		Boolean bool= true;
		bool = (Boolean) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
			 public Boolean doInRedis(RedisConnection connection)   
			      throws DataAccessException { 
				 boolean issuccess = true;
				 String carPinyinKeyStr =StringUtil.getPinyin(carNo);  //转成拼音
				 if(NSGPSConstats.CARPLATE_ADD.equals(flag)){ /***启动   --添加更新到集合****/
					 issuccess = connection.hSet(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
						redisTemplate.getStringSerializer().serialize(carPinyinKeyStr),
						redisTemplate.getStringSerializer().serialize(carAreaNo));
				 }else if(NSGPSConstats.CARPLATE_DEL.equals(flag)){ /***停止   --从集合删除****/
					  boolean ishaskey = connection.hExists(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
								redisTemplate.getStringSerializer().serialize(carPinyinKeyStr));
						if(ishaskey){
							issuccess =	connection.hDel(redisTemplate.getStringSerializer().serialize(NSGPSConstats.EPORT_GPS_HASHTABLE), 
									redisTemplate.getStringSerializer().serialize(carPinyinKeyStr));
						}
					}
				 return issuccess;
			 }
		 });  
		if(bool){ //如果更新内存集合成功   更新到物理数据 库
			NsCarInfo nsCarInfo = this.hasNSCarInfo(carNo);;
			 if(NSGPSConstats.CARPLATE_ADD.equals(flag)){ 
				 if(nsCarInfo!=null) {
					 nsCarInfo.setInareano(carAreaNo);
					 nsCarInfo.setStatus(NSGPSConstats.NSCARINFO_STATUS1); //1正在监控
					 nsCarInfo.setStarttime(new Date());
					 this.getHibernateTemplate().update(nsCarInfo) ;
				 }else{
					 nsCarInfo =  new NsCarInfo();
					 nsCarInfo.setPlate(carNo);
					 nsCarInfo.setInareano(carAreaNo);
					 nsCarInfo.setStatus(NSGPSConstats.NSCARINFO_STATUS1);
					 nsCarInfo.setStarttime(new Date());
					 this.getHibernateTemplate().save(nsCarInfo);
				 }
			 }else if(NSGPSConstats.CARPLATE_DEL.equals(flag)){ 
				 if(nsCarInfo!=null) {
					// log.info("进入更新停止车辆状态方法"+nsCarInfo.getSeqid()+nsCarInfo.getPlate());
					 nsCarInfo.setStatus(NSGPSConstats.NSCARINFO_STATUS0); //0停止监控
					 nsCarInfo.setStoptime(new Date());
					 this.getHibernateTemplate().update(nsCarInfo) ;
				 }
			 }
		}
		return bool;
	}

	/**
	 * @功能: 检查是否存在 存在则返回记录 不存在返回空 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-28 下午3:13:14
	 */
	@SuppressWarnings("unchecked")
	private NsCarInfo hasNSCarInfo(String carNo) {
		NsCarInfo carinfo =null;
		List<NsCarInfo> list =this.getHibernateTemplate().find("from NsCarInfo cf where cf.plate = ?",carNo);
		if(list!=null&&list.size()>0){
			carinfo =(NsCarInfo) list.get(0);	
		} 
		return carinfo;
	}


	/**
	 * @功能: 请求停止传输GPS实时数据 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-27 上午10:09:26
	 */
	@Override
	public ResponseXmlBean stopGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime) {
		ResponseXmlBean xmlbean = new ResponseXmlBean();
		xmlbean.setInAreaNo(inAreaNo);
		xmlbean.setPlate(plate);
		xmlbean.setStartTime(sTime);
		xmlbean.setResponseCode(queryType);
		boolean  isSuccess =true ;
		if(this.gpsFileManagerBean.isLocalbool()) { //做一个是否需要用户验证的开关
			  isSuccess =	this.ValidateUser(userName, password, NSGPSConstats.SYS_FUNID); //用户验证
		}
		if(isSuccess){  //通过验证
			if((queryType!=null&&plate!=null&&inAreaNo!=null&&sTime!=null)&&
					(!queryType.equals("")&&!plate.equals("")&&!inAreaNo.equals("")&&!sTime.equals(""))){
			}else {
				xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
				xmlbean.setMessage("failure: the args is not  right ,please check it!");
				return xmlbean;
			}
			try{
				if(queryType.equals(NSGPSConstats.REQUEST_TYPE_OF2)){ //停止请求
					String[] plateArray= plate.split("\\|",-1);
					 int len =plateArray.length;
					for(int i =0;i<len;i++){
						String carPlate =plateArray[i];
					  	this.updateSetData(carPlate,null, NSGPSConstats.CARPLATE_DEL);  //更新HASH对象表
					}
					xmlbean.setResultCode( NSGPSConstats.RESPONSE_SUCCESS);
				}else{
					xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
					xmlbean.setMessage( " the requestType is not right!");
				}
			}catch(Exception ex){
				xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
				xmlbean.setMessage("the system has Exception case by !"+ex.getMessage());
				log.info("系统产生异常"+ex.getMessage());
				ex.printStackTrace();
			}
		}else{
			xmlbean.setResultCode( NSGPSConstats.RESPONSE_FAILURE);
			xmlbean.setMessage("userinfo validate error ,username or password input error!");
		}
 		return xmlbean;
	}

}
