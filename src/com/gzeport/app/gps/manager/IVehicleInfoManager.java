package com.gzeport.app.gps.manager;

import com.gzeport.app.gps.common.ResponseXmlBean;


public interface IVehicleInfoManager {
	 public ResponseXmlBean startGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime,
			String eTime);
		public ResponseXmlBean stopGetGpsData(String userName, String password,
				String queryType, String plate, String inAreaNo, String sTime) ;
}
