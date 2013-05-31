package com.gzeport.app.gps.util;
/**
 * 
 * 功能说明：java对象生成json字符串时，需要被过滤的属性信息
 * 特殊属性：
 * @version 1.0  
 * @Date:   2009-5-20
 * @author  
 */
@SuppressWarnings("unchecked")
public class JsonExcludeInfo {
       
	private Class excludeClass;
    private String[] excludeProperties;
       
       
	/**
	 * 构造方法说明：
	 * @param excludeClass  要过滤属性的类
	 * @param excludeProperties 被过滤的属性
	 */
	public JsonExcludeInfo(Class excludeClass, String[] excludeProperties) {
		super();
		this.excludeClass = excludeClass;
		this.excludeProperties = excludeProperties;
	}
	public Class getExcludeClass() {
		return excludeClass;
	}
	public void setExcludeClass(Class excludeClass) {
		this.excludeClass = excludeClass;
	}
	public String[] getExcludeProperties() {
		return excludeProperties;
	}
	public void setExcludeProperties(String[] excludeProperties) {
		this.excludeProperties = excludeProperties;
	} 
}