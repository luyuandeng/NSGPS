package com.gzeport.app.gps.webservice;

import java.io.Serializable;

import com.gzeport.app.gps.common.ResponseXmlBean;
import com.gzeport.app.gps.manager.IVehicleInfoManager;
import com.gzeport.app.gps.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class VehicleInfoWebserviceImpl implements IVehicleInfoWebservice,Serializable {

	private static final long serialVersionUID = -7334036575925646423L;
	
	private static final String PATTERN = "<?xml version=\"1.0\" ?>\n";
	
	private IVehicleInfoManager vehicleManager;
	
	
	public IVehicleInfoManager getVehicleManager() {
		return vehicleManager;
	}

	public void setVehicleManager(IVehicleInfoManager vehicleManager) {
		this.vehicleManager = vehicleManager;
	}

	@Override
	public String startGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime,
			String eTime) {
		ResponseXmlBean xmlBean =  this.vehicleManager.startGetGpsData(userName, password, queryType, plate, inAreaNo, sTime, eTime);
		XStream xs = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xs.alias("GzpeortResponse", ResponseXmlBean.class);
		String xmlString  =this.PATTERN+StringUtil.trim( xs.toXML(xmlBean));
		return xmlString;
	}
	
	@Override
	public String stopGetGpsData(String userName, String password,
			String queryType, String plate, String inAreaNo, String sTime) {
		ResponseXmlBean xmlBean = this.vehicleManager.stopGetGpsData(userName, password, queryType, plate, inAreaNo, sTime);
		XStream xs = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xs.alias("GzpeortResponse", ResponseXmlBean.class);
		String xmlString  =this.PATTERN+StringUtil.trim( xs.toXML(xmlBean));
		return xmlString;
	}
	
}
