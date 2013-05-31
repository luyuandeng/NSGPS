package com.gzeport.app.gps.util;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

/**
 * 功能说明： 特殊属性：
 *
 * @version 1.0
 * @Date: Apr 9, 2009
 */
@SuppressWarnings("unchecked")
public class Java2JSON {
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 *
	 * 方法说明：
	 * @param list
	 * @param count
	 * @return
	 */
	public static String generate(java.util.Collection<?> list, int count) {
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("topics", list);
		map.put("totalCount", count);
		return StringUtil.convertEspecial(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 将list转换成json.
	 * @param list
	 * @param count
	 * @param jsonConfig
	 * @return
	 */
	public static String generate(java.util.Collection<?> list, int count, JsonConfig jsonConfig) {
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("totalCount", count);
		map.put("topics", list);		
		return StringUtil.convertEspecial(JSONObject.fromObject(map, jsonConfig).toString());
	}

	/**
	 * 方法说明：
	 * @param list
	 * @param count
	 * @param excludeClass
	 * @param excludeProperty
	 * @return
	 */
	public static String generate(List<?> list, int count, Class excludeClass, String excludeProperty) {
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerPropertyExclusion(excludeClass, excludeProperty);
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("totalCount", count);
		map.put("topics", list);
		return StringUtil.convertEspecial(JSONObject.fromObject(map,jsonConfig).toString());
	}

	public static <T extends Object> String generate(Collection<T> list, int count, JsonExcludeInfo[] jsonExcludeInfos) {
		JsonConfig jsonConfig = new JsonConfig();
		for (JsonExcludeInfo ex : jsonExcludeInfos) {
			jsonConfig.registerPropertyExclusions(ex.getExcludeClass(), ex.getExcludeProperties());
		}
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("totalCount", count);
		map.put("topics", list);
		return StringUtil.convertEspecial(JSONObject.fromObject(map,jsonConfig).toString());
	}

	/**
	 *
	 * 方法说明：
	 * @param object
	 * @return
	 */
	public static String javabean2jsonString(Object object, JsonConfig jsonConfig) {
		if(jsonConfig==null) jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(DATE_PATTERN));
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor(DATE_PATTERN));
		jsonConfig = registerDefaultNumberValue(jsonConfig);//设置数字型的默认值为null，而不是原默认为0
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("success", true);
		if(object!=null) map.put("data", object);
		String json = JSONObject.fromObject(map, jsonConfig).toString();
		if(object!=null){
			json = json.substring(0, 8) + "[" + json.substring(8);
			int pos = json.lastIndexOf("},");
			json = json.substring(0,pos)+"}]"+json.substring(pos+1);
		}
		return json;
	}

	/**
	 * TODO 注册jsonConfig,设置Integer、Double、Long值为null时，默认值为null，而不为0。 
	 * @param jsonConfig
	 * @return
	 */
	public static JsonConfig registerDefaultNumberValue(JsonConfig jsonConfig)
	{
		jsonConfig.registerDefaultValueProcessor(Integer.class,   
		        new DefaultValueProcessor() {   
		            public Object getDefaultValue(Class type) {   
		                return null;   
		            }   
		        }); 
		jsonConfig.registerDefaultValueProcessor(Double.class,   
		        new DefaultValueProcessor() {   
		            public Object getDefaultValue(Class type) {   
		                return null;   
		            }   
		        }); 
		jsonConfig.registerDefaultValueProcessor(Long.class,   
		        new DefaultValueProcessor() {   
		            public Object getDefaultValue(Class type) {   
		                return null;   
		            }   
		        }); 
		return jsonConfig;
	}
	/**
	 *
	 * 方法说明：
	 * @param object
	 * @return
	 */
	public static String javabean2jsonString(Object object) {
		JsonConfig jsonConfig=new JsonConfig();
		return javabean2jsonString(object, jsonConfig);
	}

	/**
	 *
	 * 方法说明：
	 * @param object
	 * @param excludeClass
	 * @param excludeProperty
	 * @return
	 */
	public static String javabean2jsonString(Object object, Class excludeClass, String excludeProperty){
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerPropertyExclusion(excludeClass, excludeProperty);
		return javabean2jsonString(object,jsonConfig);
	}
	/**
	 * 方法说明：java对象生成json字符串,过滤掉部分属性以避免产生死循环
	 * @param object
	 * @param jsonExcludeInfos
	 * @return
	 */
	public static String javabean2jsonString(Object object,JsonExcludeInfo[] jsonExcludeInfos){
		JsonConfig jsonConfig=new JsonConfig();
		//配置属性过滤
		for(int i=0;i<jsonExcludeInfos.length;i++){
			jsonConfig.registerPropertyExclusions(jsonExcludeInfos[i].getExcludeClass(),jsonExcludeInfos[i].getExcludeProperties());
		}
		return javabean2jsonString(object,jsonConfig);
	}
	/**
	 *
	 * 方法说明：
	 * @param s
	 * @param beanClass
	 * @return
	 */
	public static Set jsonString2JavaBeanSet(String s, Class beanClass){
		Set set=new HashSet();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(DATE_PATTERN));
		JSONArray jsonArray = JSONArray.fromObject(s);
		for(Object object:jsonArray){
			set.add(JSONObject.toBean(JSONObject.fromObject(object), beanClass));
		}
		return set;
	}

	/**
	 *
	 * 方法说明：
	 * @param s
	 * @param beanClass
	 * @return
	 */
	public static Set jsonString2JavaBean(String s, Class beanClass){
		Set set= new java.util.LinkedHashSet();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(DATE_PATTERN));
		JSONArray jsonArray = JSONArray.fromObject(s);
		for(Object object:jsonArray){
			set.add(JSONObject.toBean(JSONObject.fromObject(object), beanClass));
		}
		return set;
	}

	/**
	 *
	 * 方法说明：
	 * @param s
	 * @param beanClass
	 * @return
	 */
	public static List jsonString2BeanList(String s, Class beanClass){
		List set= new java.util.LinkedList();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(DATE_PATTERN));
		JSONArray jsonArray = JSONArray.fromObject(s);
		for(Object object:jsonArray){
			set.add(JSONObject.toBean(JSONObject.fromObject(object), beanClass));
		}
		return set;
	}

	/**
	 *
	 * 方法说明：集合转换成json数据
	 *
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String Collection2jsonString(java.util.Collection collection) {
		return net.sf.json.JSONArray.fromObject(collection).toString();
	}

	/**
	 *
	 * 方法说明：更新、添加、删除返回的json数据对象
	 *
	 * @param msg
	 * @param success
	 * @return
	 */
	public static String JsonResult(String msg, boolean success) {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("msg", msg);
		map.put("success", success);
		return JSONObject.fromObject(map).toString();
	}

	/**
	 *
	 * 方法说明：验证失败返回的json数据对象
	 *
	 * @param msg
	 * @param success
	 * @return
	 */
	public static String JsonError(String msg, boolean success) {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("errors", msg);
		map.put("success", success);
		return JSONObject.fromObject(map).toString();
	}
	/**
	 *
	 * 方法说明：验证失败返回的json数据对象
	 *
	 * @param msg
	 * @param success
	 * @return
	 */
	public static String JsonError(Map _map, boolean success) {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("errors", _map);
		map.put("success", success);
		return JSONObject.fromObject(map).toString();
	}
	/**
	 *
	 * 方法说明：
	 * @param map
	 * @return
	 */
	public static String Map2JsonData(Map map){
		return JSONObject.fromObject(map).toString();
	}
	/**
	 *
	 * 方法说明：
	 * @param map
	 * @return
	 */
	public static String Map2JsonDataString(Map _map){
		java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("success", true);
		List objs = new java.util.ArrayList();
		objs.add(_map);
		map.put("data", objs);
		return JSONObject.fromObject(map).toString();
	}
	/**
	 *
	 * 方法说明：
	 * @param map
	 * @return
	 */
	public static String Map2JsonDataString(Map map,Boolean result){
		java.util.Map<String, Object> _map = new java.util.HashMap<String, Object>();
		_map.put("success", result);
		_map.put("data", map);
		return JSONObject.fromObject(_map).toString();
	}
	/**
	 *
	 * 方法说明：把集合数据转成json的字符串格式
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String Collection2JsonArrayData(Collection<Map<String,Object>> collection) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("[");
		for(java.util.Iterator<Map<String,Object>> $it =collection.iterator();$it.hasNext();){
			Map map = (Map)$it.next();
			result.append("[");
			for(java.util.Iterator it = map.keySet().iterator();it.hasNext();){
				Object key = it.next();
				Object value = map.get(key);
				result.append("\'"+value+"\'");
				if(it.hasNext())result.append(",");
			}
			result.append("]");
			if($it.hasNext())result.append(",");
		}
		return StringUtil.convertEspecial(result.append("]").toString());
	}
	
	/**
	 *
	 * 方法说明：把集合数据转成json的字符串格式
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String Set2JsonArrayData(Set<String> resultList) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("[");
		for(java.util.Iterator<String> $it = resultList.iterator();$it.hasNext();){
				String v = (String)$it.next();
				result.append("[");
				result.append("\'"+v+"\'");
				result.append("]");
				if($it.hasNext())result.append(",");
		}
		return StringUtil.convertEspecial(result.append("]").toString());
	}
	/***
	 *
	 * 方法说明：目前只考虑一维/二维
	 * @param obj
	 * @return
	 */
	public static String array2JsonArrayData(Object[] objs){
		StringBuffer result = new StringBuffer();
		result.append("[");
		for(int i=0;i<objs.length;i++){
			Object obj = objs[i];
			if(obj instanceof Object[]){
				Object[] _objs = (Object[])obj;
				result.append("[");
				for(int k=0;k<_objs.length;k++){
					result.append("'"+_objs[k]+"'");
					if(k!=_objs.length-1)
						result.append(",");
				}
				result.append("]");
			}else{
				result.append("['"+obj+"']");
			}
			if(i!=objs.length-1)result.append(",");
		}
		return result.append("]").toString();
	}
	/**
	 *
	 * 方法说明：
	 * @param objs
	 * @param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static String enum2JsonArrayData(Object[] objs,String[] fields) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		java.util.Collection<Map<String,Object>> collection = new java.util.ArrayList<Map<String,Object>>();
		for(Object obj : objs){
			Map<String,Object> map = new java.util.LinkedHashMap<String,Object>();
			for(String field : fields){
				map.put(field,BeanUtils.getProperty(obj,field));
			}
			if(!(map.get("deleteFlag")!=null
					&&(Boolean)map.get("deleteFlag")))
			collection.add(map);
		}
		return Collection2JsonArrayData(collection);
	}

	/**
	 *
	 * 方法说明：
	 * @param args
	 */
	public static void main(String[] args){
		Object[] objs = new Object[3];
		objs[0] = "a";objs[1] = "b";objs[2] = "c";
		System.out.println(array2JsonArrayData(new Object[]{objs}));
	}
}
