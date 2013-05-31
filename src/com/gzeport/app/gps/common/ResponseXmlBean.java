package com.gzeport.app.gps.common;

public class ResponseXmlBean {
	
	private String ResponseCode;  //返回代码 与请求对应 1是开始 2停止
	private String Plate;    //车牌号
	private String InAreaNo;//车辆入区编号 （车次编号）
	private String StartTime;  //开始时间
	private String EndTime;  //结束时间
	private String ResultCode; //返回结果代码 1正常 0是失败
	private String Message ; //返回提示信息
	
	
	public String getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
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
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}

}
