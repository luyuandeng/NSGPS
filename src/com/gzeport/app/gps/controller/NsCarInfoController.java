package com.gzeport.app.gps.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gzeport.app.gps.common.NSGPSConstats;
import com.gzeport.app.gps.manager.NsCarInfoManager;
import com.gzeport.app.gps.pojo.NsCarInfo;

@Controller
public class NsCarInfoController {
	
	Logger log =  Logger.getLogger(this.getClass().getName());
	
	
	private NsCarInfoManager nsCarInfoManager;

	
	public NsCarInfoManager getNsCarInfoManager() {
		return nsCarInfoManager;
	}
	@Resource(name="nsCarInfoManager")
	public void setNsCarInfoManager(NsCarInfoManager nsCarInfoManager) {
		this.nsCarInfoManager = nsCarInfoManager;
	}
	
	/**
	 * @功能: 监控车辆列表 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午2:04:41
	 */
	@RequestMapping(value="nsCarInfoList.do")
	public ModelAndView getNsCarInfoList(){
		ModelAndView view = new ModelAndView("nsCarInfoList");
		//log.info("进入了Controller方法");
		List<NsCarInfo> list = null;
		list = this.nsCarInfoManager.getNsCarINfoList();
		
		if(list!=null&&list.size()>0){
			log.info("得到多少条数据："+list.size());
			view.addObject("message",list);
		}else{
			view.addObject("message","Search Successful!   counter");
		}
    	return view;
	}

	/**
	 * @功能: 手工启动 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午2:06:17
	 */
	@RequestMapping(value="startNsCarinfo.do")
	public String setNsCarStart(@RequestParam("plate")String plate,@RequestParam("inAreaNo")String inAreaNo,
			HttpServletRequest request,ModelMap map){
		byte bb[]; 
        try {
			bb = plate.getBytes("ISO-8859-1");//以"ISO-8859-1"方式解析name字符串 
		     plate= new String(bb, "UTF-8"); //再用"utf-8"格式表示name 
		     
		     this.nsCarInfoManager.updateNSCarStatus(plate,inAreaNo,NSGPSConstats.NSCARINFO_STATUS1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	//	log.info("取到参数了没有？"+plate);
		return  "forward:/nsCarInfoList.do";
	}
	
	
	/**
	 * @功能: 手工停止 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-31 下午2:06:27
	 */
	@RequestMapping(value="stopNsCarinfo.do")
	public String setNsCarStop(@RequestParam("plate")String plate,@RequestParam("inAreaNo")String inAreaNo,
			HttpServletRequest request,ModelMap map){
		byte bb[]; 
        try {
			bb = plate.getBytes("ISO-8859-1");//以"ISO-8859-1"方式解析name字符串 
		     plate= new String(bb, "UTF-8"); //再用"utf-8"格式表示name 
		     this.nsCarInfoManager.updateNSCarStatus(plate,inAreaNo,NSGPSConstats.NSCARINFO_STATUS0);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		//log.info("取到参数了没有？"+plate);
		return  "forward:/nsCarInfoList.do";
	} 
}
