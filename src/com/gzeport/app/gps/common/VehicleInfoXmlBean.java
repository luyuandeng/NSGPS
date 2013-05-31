package com.gzeport.app.gps.common;

import java.text.SimpleDateFormat;

import com.gzeport.app.gps.pojo.VehicleInfo;


public class VehicleInfoXmlBean {
	
	private String VTKey;    //GPS设备号
	private String Plate;       //车牌号
	private String InAreaNo; //途中编号（车次）
	private String Recvtime;  //接收时间
	private String Gpstime;  //GPS时间
	private String Lat;       //经度
	private String Lon;    //纬度
	private String Height;  //高度
	private String Speed;   //记录仪速度
	private String GpsSpeed; //GPS速度
	private String Dir;      //方向
	private String Mile;    //记录仪里程 
	private String Eff;     //GPS有效标识 1有效 0无效
	private String Alarm;  //是否报警1报警  0 正常
	private String Run;    //是否运行 1点火 2熄火 
	private String StatusChars;//GPS8个状态字
	private String GetMode;   //GPS数据来源 1'：GPS接入服务器获得，'2'：后台接入服务器获得，3：省厅接入，4：行业监管接入
	private String TagChar;  //GPS其他标志 null or '0'：正常 '1'：盲区补偿GPS
	private String Protocol;   //最初来源协议  可表明 STATUSCHARS该如何解释。0：未知，1：DB44，2：JTT808
	private String Provider;  //提供者
	private String Platecolor; //车牌颜色
	
	public VehicleInfoXmlBean(VehicleInfo info) {
	this.setAlarm(info.getAlarm());
	this.setDir(info.getDir());
	this.setEff(info.getEff());
	this.setGetMode(info.getMile());
	this.setGpsSpeed(info.getGpsSpeed());
	
	if(info.getGpstime()!=null){
		this.setGpstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getGpstime()));
	}
	this.setHeight(info.getHeight());
	this.setInAreaNo(info.getInAreaNo());
	this.setLat(info.getLat());
	this.setLon(info.getLon());
	this.setMile(info.getMile());
	this.setPlate(info.getPlate());
	this.setPlatecolor(info.getPlatecolor());
	this.setProtocol(info.getProtocol());
	this.setProvider(info.getProvider());
	if(info.getRecvtime()!=null){
		this.setRecvtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getRecvtime()));
	}
	this.setRun(info.getRun());
	this.setSpeed(info.getSpeed());
	this.setStatusChars(info.getStatusChars());
	this.setTagChar(info.getTagChar());
	this.setVTKey(info.getVTKey());

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
	public String getInAreaNo() {
		return InAreaNo;
	}
	public void setInAreaNo(String inAreaNo) {
		InAreaNo = inAreaNo;
	}
	public String getRecvtime() {
		return Recvtime;
	}
	public void setRecvtime(String recvtime) {
		Recvtime = recvtime;
	}
	public String getGpstime() {
		return Gpstime;
	}
	public void setGpstime(String gpstime) {
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
	public String getPlatecolor() {
		return Platecolor;
	}
	public void setPlatecolor(String platecolor) {
		Platecolor = platecolor;
	}

}
