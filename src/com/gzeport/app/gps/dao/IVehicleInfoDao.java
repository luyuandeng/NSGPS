package com.gzeport.app.gps.dao;


import com.gzeport.app.gps.common.ResponseXmlBean;
import com.gzeport.app.gps.pojo.VehicleInfo;

public interface IVehicleInfoDao {
	public ResponseXmlBean startGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime,
			String eTime);

	public void addVehicleInfo(VehicleInfo instance);

	public ResponseXmlBean stopGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime);
	public Boolean updateSetData( final String carNo,final String carAreaNo, final String flag);
}
