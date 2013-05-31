package com.gzeport.app.gps.pojo;

import java.util.Date;

public class VehicleInfo implements java.io.Serializable {

	@Override
	public String toString() {
		return "[{vehicleId:"+ this.getVehicleId()+",VTKey:"+this.getVTKey()+
		",Recvtime:"+this.getRecvtime()+ ",Gpstime:"+this.getGpstime()+
		",Lat:"+this.getLat()+	",Lon:"+this.getLon()+",Plate:"+this.getPlate()+
		",Height:"+this.getHeight()+	",Speed:"+this.getSpeed()+
		",GpsSpeed:"+this.getGpsSpeed()+	",Dir:"+this.getDir()+
		",Mile:"+this.getMile()+	",Eff:"+this.getEff()+
		",Alarm:"+this.getAlarm()+	",Run:"+this.getRun()+
		",StatusChars:"+this.getStatusChars()+	",GetMode:"+this.getGetMode()+
		",TagChar:"+this.getTagChar()+",Protocol:"+this.getProtocol()+
		",Provider:"+this.getProvider()+",Platecolor:"+this.getPlatecolor()+"}]";
	}

	private static final long serialVersionUID = 2969329996020821906L;
	private Long vehicleId;
	private String VTKey;
	private String Plate;
	private String InAreaNo;
	private Date Recvtime;
	private Date Gpstime;
	private String Lat;
	private String Lon;
	private String Height;
	private String Speed;
	private String GpsSpeed;
	private String Dir;
	private String Mile;
	private String Eff;
	private String Alarm;
	private String Run;
	private String StatusChars;
	private String GetMode;
	private String TagChar;
	private String Protocol;
	private String Provider;
	private String Platecolor;

	
	
	public String getInAreaNo() {
		return InAreaNo;
	}

	public void setInAreaNo(String inAreaNo) {
		InAreaNo = inAreaNo;
	}

	public String getPlatecolor() {
		return Platecolor;
	}

	public void setPlatecolor(String platecolor) {
		Platecolor = platecolor;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVTKey() {
		return VTKey;
	}

	public void setVTKey(String vTKey) {
		VTKey = vTKey;
	}

	public String getPlate() {
		return Plate;
	}

	public void setPlate(String plate) {
		Plate = plate;
	}

	public Date getRecvtime() {
		return Recvtime;
	}

	public void setRecvtime(Date recvtime) {
		Recvtime = recvtime;
	}

	public Date getGpstime() {
		return Gpstime;
	}

	public void setGpstime(Date gpstime) {
		Gpstime = gpstime;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public String getLon() {
		return Lon;
	}

	public void setLon(String lon) {
		Lon = lon;
	}

	public String getHeight() {
		return Height;
	}

	public void setHeight(String height) {
		Height = height;
	}



	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	public String getGpsSpeed() {
		return GpsSpeed;
	}

	public void setGpsSpeed(String gpsSpeed) {
		GpsSpeed = gpsSpeed;
	}

	public String getDir() {
		return Dir;
	}

	public void setDir(String dir) {
		Dir = dir;
	}

	public String getMile() {
		return Mile;
	}

	public void setMile(String mile) {
		Mile = mile;
	}

	public String getEff() {
		return Eff;
	}

	public void setEff(String eff) {
		Eff = eff;
	}

	public String getAlarm() {
		return Alarm;
	}

	public void setAlarm(String alarm) {
		Alarm = alarm;
	}

	public String getRun() {
		return Run;
	}

	public void setRun(String run) {
		Run = run;
	}

	public String getStatusChars() {
		return StatusChars;
	}

	public void setStatusChars(String statusChars) {
		StatusChars = statusChars;
	}

	public String getGetMode() {
		return GetMode;
	}

	public void setGetMode(String getMode) {
		GetMode = getMode;
	}

	public String getTagChar() {
		return TagChar;
	}

	public void setTagChar(String tagChar) {
		TagChar = tagChar;
	}

	public String getProtocol() {
		return Protocol;
	}

	public void setProtocol(String protocol) {
		Protocol = protocol;
	}

	public String getProvider() {
		return Provider;
	}

	public void setProvider(String provider) {
		Provider = provider;
	}

}
