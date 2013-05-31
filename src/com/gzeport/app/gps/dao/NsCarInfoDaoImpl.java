package com.gzeport.app.gps.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gzeport.app.gps.common.NSGPSConstats;
import com.gzeport.app.gps.pojo.NsCarInfo;
/**
 * @ClassName NsCarInfoDaoImpl
 * @Description 监控车辆信息
 * @author luyd luyuandeng@gzeport.com
 * @date 2013-5-30
 */
public class NsCarInfoDaoImpl extends HibernateDaoSupport implements INsCarInfoDao {

	Log log = LogFactory.getLog(this.getClass().getName());
	
	//private RedisTemplate<Serializable, Serializable> redisTemplate;
	
/*	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}*/
	
	/*	public void setRedisTemplate(
	RedisTemplate<Serializable, Serializable> redisTemplate) {
this.redisTemplate = redisTemplate;
}*/
	
	
	private IVehicleInfoDao  vehInfoDAO  ;

	public IVehicleInfoDao getVehInfoDAO() {
		return vehInfoDAO;
	}

	public void setVehInfoDAO(IVehicleInfoDao vehInfoDAO) {
		this.vehInfoDAO = vehInfoDAO;
	}



	@Override
	public void addVehicleInfo(NsCarInfo instance) {
		
	}

	/**
	 * @功能: 监控车辆列表 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午3:51:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NsCarInfo> getNsCarINfoList() {
		List<NsCarInfo>  list = null;
		list = (List<NsCarInfo> ) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = " from NsCarInfo carinfo  ";
              					Query query = session.createQuery(hql);
              				//	query.setFirstResult(0);
              					//query.setMaxResults(15);
					List <NsCarInfo> infolist =query.list();
					return infolist;
				}});
		return list;
	}

	/**
	 * @功能: 更新监控状态为监控 或未监控状态 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午3:22:30
	 */
	@Override
	public boolean updateNsCarStatus(String plate, String inAreaNo,String status) {
		NsCarInfo ns = new NsCarInfo();
		ns.setPlate(plate);
		boolean b = true;
		try{
			List<NsCarInfo> ls  = (List<NsCarInfo>) this.getHibernateTemplate().findByExample(ns);
			if(ls!=null&&ls.size()>0){
				ns =ls.get(0);
				ns.setStatus(status);
				if(status.equals(NSGPSConstats.NSCARINFO_STATUS0)){
					ns	.setStoptime(new Date());
					//从redis 内存中移除
				  b =	this.vehInfoDAO.updateSetData(plate, ns.getInareano(), NSGPSConstats.CARPLATE_DEL);
					
				}else  if(status.equals(NSGPSConstats.NSCARINFO_STATUS1) ){
					//更新到redis 内存
					ns.setStarttime(new Date());
					b = this.vehInfoDAO.updateSetData(plate, ns.getInareano(), NSGPSConstats.CARPLATE_ADD);
				}
			}
		}catch(Exception ex){
			log.info(ex.getMessage());
			return false;
		}
 		return b;
	}
}
