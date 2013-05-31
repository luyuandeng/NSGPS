package com.gzeport.app.gps.manager;

import java.util.List;

import com.gzeport.app.gps.dao.INsCarInfoDao;
import com.gzeport.app.gps.pojo.NsCarInfo;
/**
 * @ClassName NsCarinfoManagerImpl
 * @Description 南沙车辆GPS监控管理类
 * @author luyd luyuandeng@gzeport.com
 * @date 2013-5-30
 */
public class NsCarinfoManagerImpl  implements NsCarInfoManager{
	
	private INsCarInfoDao nsCarInfoDao;

	public INsCarInfoDao getNsCarInfoDao() {
		return nsCarInfoDao;
	}

	public void setNsCarInfoDao(INsCarInfoDao nsCarInfoDao) {
		this.nsCarInfoDao = nsCarInfoDao;
	}

	@Override
	public void addVehicleInfo(NsCarInfo instance) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @功能: 监控车辆列表 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-30 下午6:26:47
	 */
	@Override
	public List<NsCarInfo> getNsCarINfoList() {
		return this.nsCarInfoDao.getNsCarINfoList();
	}

	/**
	 * @功能: 更新车辆监控状态  为监控 或未监控 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午3:21:38
	 */
	@Override
	public boolean updateNSCarStatus(String plate, String inAreaNo,String status) {
		return this.nsCarInfoDao.updateNsCarStatus( plate, inAreaNo,  status);
 	}

}
