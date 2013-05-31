package com.gzeport.app.gps.manager;

import java.util.List;

import com.gzeport.app.gps.pojo.NsCarInfo;

public interface NsCarInfoManager {
	
	public void addVehicleInfo(NsCarInfo instance);
	
	public List<NsCarInfo> getNsCarINfoList();
	
	public boolean updateNSCarStatus(String plate,String inAreaNo,String status);

}
