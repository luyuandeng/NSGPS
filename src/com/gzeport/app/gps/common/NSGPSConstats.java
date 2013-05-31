package com.gzeport.app.gps.common;

import java.io.File;

import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;



public class NSGPSConstats {

	
	//Redis 服务器 数据队列
	public static final String EPORT_QUEUE_GPS="eport.queue.gps";
	
	//Redis 服务器 车辆在途监控表状态表
	public static final String EPORT_GPS_HASHTABLE="eport.gps.cartable";

	public static final String REQUEST_TYPE_OF1 ="1";  //开始请求
	
	public static final String REQUEST_TYPE_OF2 ="2";  //停止请求
	
	public static final String CARPLATE_ADD ="add";     //添加
	
	public static final String CARPLATE_DEL ="del";      //删除
	
	public static final String RESPONSE_SUCCESS ="1";  //返回成功
	
	public static final String RESPONSE_FAILURE ="0";   //返回失败
	
	public static final String SYS_FUNID  ="001";   //系统权限ID
	
	public static final String NSCARINFO_STATUS1 ="1";  //正在监控
	
	public static final String NSCARINFO_STATUS0 ="0";   //未监控 
	
 
	public static final String PATTERN = "<?xml version=\"1.0\" ?>\n";
	public static String myException(RuntimeException ex) {
		if (ex instanceof DataIntegrityViolationException) {
			return "完整性约束异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof DataRetrievalFailureException) {
			return "数据获取异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof IncorrectUpdateSemanticsDataAccessException) {
			return "更新出错异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof DeadlockLoserDataAccessException) {
			return "死锁访问异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof CleanupFailureDataAccessException) {
			return "清除失败异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof DataAccessResourceFailureException) {
			return "资源访问异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof InvalidDataAccessApiUsageException) {
			return "无效数据访问API使用异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof InvalidDataAccessResourceUsageException) {
			return "无效数据访问资源使用异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof OptimisticLockingFailureException) {
			return "乐观锁失败异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof TypeMismatchDataAccessException) {
			return "类型匹配失败异常: " + ex.getCause().getMessage();
		}

		if (ex instanceof UncategorizedDataAccessException) {
			return "其他原因异常: " + ex.getCause().getMessage();
		}
		return "null";
	}
}
