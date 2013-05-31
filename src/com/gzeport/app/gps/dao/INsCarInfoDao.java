package com.gzeport.app.gps.dao;

import java.util.List;

import com.gzeport.app.gps.pojo.NsCarInfo;

public interface INsCarInfoDao {

	public void addVehicleInfo(NsCarInfo instance);
	
	public List<NsCarInfo> getNsCarINfoList();

	public boolean updateNsCarStatus(String plate,String inAreaNo, String status);
}
