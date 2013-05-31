package com.gzeport.app.gps.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class VehicleInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(VehicleInfoDAO.class);
	// property constants
	public static final String PLATE = "Plate";

	protected void initDao() {
		// do nothing
	}

	public void save(VehicleInfo transientInstance) {
		log.debug("saving Account instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	

	public void delete(VehicleInfo persistentInstance) {
		log.debug("deleting VehicleInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VehicleInfo findById(java.math.BigDecimal id) {
		log.debug("getting VehicleInfo instance with id: " + id);
		try {
			VehicleInfo instance = (VehicleInfo) getHibernateTemplate().get(
					"com.gzeport.app.gps.pojo.VehicleInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(VehicleInfo instance) {
		log.debug("finding VehicleInfo instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	/**
	 * @功能: GPS轨迹数据列表 只查某一天 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-5下午03:49:10 
	 * @文件: VehicleInfoDAO.java
	 */
	public List findLineDataByProperty(String propertyName, Object value) {
		log.debug("finding VehicleInfo instance with property: " + propertyName
				+ ", value: " + value);
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow =Calendar.getInstance();
		Date startDate= new Date();
		try {
			startDate = sf.parse( sf.format(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rightNow.setTime(startDate);
		rightNow.add( Calendar.DAY_OF_MONTH,1);
	 	Date endDate = rightNow.getTime();
	 	log.info("Start:"+sf.format(startDate)+" End"+sf.format(endDate));
		try {
			String queryString = "from VehicleInfo as model where model."
					+ propertyName + "= ?  and model.Gpstime >=? and model.Gpstime < ? order by model.Gpstime desc";
			return getHibernateTemplate().find(queryString,new Object[]{value,startDate,endDate});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findLineDataByDb(String carNo,Date startdate,Date enddate) {
		log.debug("finding VehicleInfo instance with carNo: " + carNo
				+ ", startdate: " + startdate+", enddate: " + startdate);
		try {
			String queryString = "from VehicleInfo as model where model.Plate= ?"
					+ " and model.Gpstime >=? and model.Gpstime < ? order by model.Gpstime desc";
			return getHibernateTemplate().find(queryString,new Object[]{carNo,startdate,enddate});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	/**
	 * @功能: 根据对象属性查找记录 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-17下午10:05:48 
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding VehicleInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from VehicleInfo as model where model."
					+ propertyName + "= ? order by model.Gpstime desc";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByUserName(Object plate) {
		return findByProperty(PLATE, plate);
	}


	public List findAll() {
		log.debug("finding all Account instances");
		try {
			String queryString = "from VehicleInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VehicleInfo merge(VehicleInfo detachedInstance) {
		log.debug("merging VehicleInfo instance");
		try {
			VehicleInfo result = (VehicleInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VehicleInfo instance) {
		log.debug("attaching dirty VehicleInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VehicleInfo instance) {
		log.debug("attaching clean VehicleInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static VehicleInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (VehicleInfoDAO) ctx.getBean("VehicleInfoDAO");
	}
}