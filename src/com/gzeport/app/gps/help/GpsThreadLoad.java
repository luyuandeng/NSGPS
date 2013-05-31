package com.gzeport.app.gps.help;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gzeport.app.gps.help.thread.NewThread;


public class GpsThreadLoad extends HttpServlet {

	private static final long serialVersionUID = 5046458013623462125L;
	private static final Logger logger = org.apache.log4j.Logger.getLogger(GpsThreadLoad.class);
	private Properties property;//配置信息
	
	private NewThread newthread;
	
	public NewThread getNewthread() {
		return newthread;
	}

	public void setNewthread(NewThread newthread) {
		this.newthread = newthread;
	}

	public GpsThreadLoad(){
		 super();
	}
	
	 public void init() throws ServletException { 
			initParamsBean();
			
		//	NewThread newthread = new NewThread();
			if(this.newthread!=null){
				Thread thread =  new Thread(this.newthread);
				logger.info("开始启动线程");
				thread.start();
			}
			
	 }
	 
	 public void 	initParamsBean(){
		 logger.info("开始初始参数Bean");
		 WebApplicationContext   ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		 this.newthread=(NewThread)  ctx.getBean("newThread");
	 }
}

