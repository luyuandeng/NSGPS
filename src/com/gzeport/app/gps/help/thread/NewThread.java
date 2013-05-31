package com.gzeport.app.gps.help.thread;

import org.apache.log4j.Logger;

import com.gzeport.app.gps.help.CarGpsDataHelp;



public class NewThread  implements Runnable{

	private static final Logger logger = org.apache.log4j.Logger.getLogger(NewThread.class);

	
	private CarGpsDataHelp carGpsDataHelp;
	
	public CarGpsDataHelp getCarGpsDataHelp() {
		return carGpsDataHelp;
	}
	public void setCarGpsDataHelp(CarGpsDataHelp carGpsDataHelp) {
		this.carGpsDataHelp = carGpsDataHelp;
	}
	
	private Boolean isRunable =true;
	
	private Long sleepTime =1*1*1000L;
	
	public Long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
	}
	public Boolean getIsRunable() {
		return isRunable;
	}
	public void setIsRunable(Boolean isRunable) {
		this.isRunable = isRunable;
	}
	
	public NewThread(){
	}
	@Override
	public void run() {
		
		while(isRunable)
		{
			logger.info("进入redis _GPS数据采集线程执行..........................................");
			try {
				this.carGpsDataHelp.getGpsDataByJredis();
			} catch (Exception e1) {
				logger.info("进入线程执行过程出错了........"+e1.getMessage());
				e1.printStackTrace();
			}
			try {
				 Thread.sleep(this.sleepTime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				isRunable = false;
			} 
		} 
		logger.info(".........................................线程执行结束.");
		logger.info("this newthread  isRunable ="+isRunable);
	}

}
