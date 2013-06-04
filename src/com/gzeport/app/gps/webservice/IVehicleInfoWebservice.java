package com.gzeport.app.gps.webservice;

import javax.jws.WebService;

@WebService
public interface IVehicleInfoWebservice {
	
	public String  startGetGpsData( String userName,String password,String queryType,String plate,String inAreaNo,String sTime,String eTime);
	
	public String  stopGetGpsData( String userName,String password,String queryType,String plate,String inAreaNo,String eTime);
}
