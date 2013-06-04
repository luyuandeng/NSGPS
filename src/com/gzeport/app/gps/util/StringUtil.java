package com.gzeport.app.gps.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class StringUtil {
	
	
	private static long tmpID = 0;
	 
	private static boolean tmpIDlocked = false;

	// 判断字符串不为空
	public static boolean isNotEmpty(Object obj) {
		if (obj != null ) {
			String tmp = String.valueOf(obj);
				if (!tmp.trim().equals(""))
					return true;
			}
		return false;
	}
	
	public static String trim(String condition) {
		if (condition != null)
			return condition.replaceAll("(>[\\s]+<)", "><").trim();
		else
			return null;
	}
	// 将字符串转化为整数
	public static int parseInteger(String str) {
		if (isNotEmpty(str))
			return Integer.parseInt(str);
		return 0;
	}
	
	// 将字符串转化为长整型
	public static long parseLong(String str) {
		if (isNotEmpty(str))
			return Long.parseLong(str);
		return 0;
	}

	// 将字符串转化为双精度
	public static double parseDouble(String str) {
		if (isNotEmpty(str))
			return Double.parseDouble(str);
		return 0;
	}

	// 将字符串转换为日期
	public static Date parseDate(String str, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(str);
		return date;
	}
	
	// 将字符串转换为日期
	public static Date parseShortDate(String str ){
		 
		Date date=null;
		String format="yyyy-mm-dd";
		try {
			date = parseDate(str,format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	// 将日期转换为字符串
	public static String parseDateToString(Date date, String format)
		  {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	// 将字符串转换为特定格式的字符串
	public static String parseFormatString(String source, String format) {

		String result = "";
		SimpleDateFormat sdf = null;
		SimpleDateFormat local = new SimpleDateFormat(format);
		Date date = null;
		if(source.toUpperCase().indexOf("CST") != -1){
			sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("CST"));
			
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			result = local.format(date);
		}else{
			try {
				date = local.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			result = local.format(date);
		}
 
		return result;
	}

	public static String getCurrTime(int format) {
		String currDate = "";
		String fmStr = "";
		switch (format) {
		case 1: // '\001'
			fmStr = "yyyy-MM-dd HH:mm:ss";
			break;

		case 2: // '\002'
			fmStr = "yyyyMMddHHmmss";
			break;

		case 3: // '\003'
			fmStr = "yyyy-MM-dd";
			break;

		case 4: // '\004'
			fmStr = "yyyy-MM";
			break;

		case 5: // '\005'
			fmStr = "d";
			break;

		case 6: // '\006'
			fmStr = "HH";
			break;

		default:
			fmStr = "yyyy-MM-dd HH:mm:ss";
			break;
		}
		SimpleDateFormat fm = new SimpleDateFormat(fmStr);
		Date date = new Date();
		currDate = fm.format(date);
		return currDate;
	}
	/**
	 * @功能: 一天的开始时间 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-17 下午4:48:14
	 */
	public static Date getSrartOfCurrentDate(){
		Calendar currentDate = new GregorianCalendar();   
		 
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
	 	Date sDate =(Date)currentDate.getTime().clone();  
	 	return sDate;
	}
	
	
	/**
	 * @功能: 一天的结束时间 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-17 下午4:48:14
	 */
	public static Date getEndOfCurrentDate(){
		Calendar currentDate = new GregorianCalendar();   
		 
	    currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  

	 	Date eDate =(Date)currentDate.getTime().clone();  
	 	return eDate;
	}
	/**
	 * @功能: 一周的结束时间 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-17 下午4:50:51
	 */
	public static Date getEndOfCurrentWeek(){
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
        currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  

	 	Date eDate =(Date)currentDate.getTime().clone();  
	 	return eDate;
	}
	
	// 获取字符串长度
	public static int getTrimLength(String str) {
		if (isNotEmpty(str.trim())) {
			return str.trim().length();
		}
		return 0;
	}

	/**
	 * 字符串替换
	 * @param strFrom 要替换的目标子串
	 * @param strTo 替换后的子串
	 * @param strSource 原字符串
	 * @return 替换后的字符串
	 */

	public static String replace(String strSource, String strFrom, String strTo) {

		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}

		strDest = strDest + strSource;
		return strDest;
	}

	 
	public static long getUniqueId() {
		long ltime = 0;
		while (true) {
			if (tmpIDlocked == false) {
				tmpIDlocked = true;
				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
						.format(new Date()).toString()) * 10000;
				if (tmpID < ltime) {
					tmpID = ltime;
				} else {
					tmpID = tmpID + 1;
					ltime = tmpID;
				}
				tmpIDlocked = false;
				return ltime;
			}
		}
	 }
	
	public  static void copyFile(File src, File dst) {
		int BUFFER_SIZE = 16 * 1024;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public static boolean copyFile(String srcFilePath, String destPath) {
		File file = new File(srcFilePath);
		String filePath = (new StringBuilder(String.valueOf(destPath)))
				.append(File.separator).append(file.getName()).toString();
		String newFileName = createNewFileName(filePath);
		boolean success = file.renameTo(new File(newFileName));
		return success;
	}

	public static boolean copyFile(String srcFilePath, String destPath,
			boolean isDel  ) {
		boolean success = false;
		success = copyFile(srcFilePath, destPath);
		if (isDel && success) {
			File file = new File(srcFilePath);
			if (file.exists())
				success = file.delete();
		}
		return success;
	}
	
	public static String convertEspecial(String string) {
		string = string.replace("<", "<");
	    string = string.replace(">", ">");
		return string;
	}
	
	public static String trimNull(String inString) {
		if (inString == null)
			inString = "";
		else
			inString = inString.trim();
		return inString;
	}
	
	public static String[] trimNull(String inString[]) {
		int theLength = inString.length;
		for (int i = 0; i < theLength; i++)
			if (inString[i] == null)
				inString[i] = "";
			else
				inString[i] = inString[i].trim();

		return inString;
	}
	
	public static String toUpperString(String str) {
		String strBuer = "";
		strBuer = trimNull(str);
		if ("".equals(strBuer)) {
			return "";
		} else {
			strBuer = strBuer.toUpperCase();
			return strBuer;
		}
	}
	/**
	 * @功能: 汉字转拼音方法 
	 * @编码: luyd luyuandeng@gzeport.com 2013-5-21 下午2:12:49
	 */
	public static String getPinyin(String str) {

		String py = "";
		String[] t = new String[str.length()];

		char[] hanzi = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			hanzi[i] = str.charAt(i);
		}

		net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat t1 = new HanyuPinyinOutputFormat();
		t1.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t1.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t1.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			for (int i = 0; i < str.length(); i++) {
				if ((str.charAt(i) >= 'a' && str.charAt(i) < 'z')
						|| (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
						|| (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
					py += str.charAt(i);
				} else {
					t = PinyinHelper.toHanyuPinyinStringArray(hanzi[i], t1);
					py = py + t[0];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		return py.trim().toString();
	}
	
	  public static boolean doc2XmlFile(String documentContent, String filename, String encode) {
		Document document = string2Document(documentContent);
	    boolean flag = true;
	    try
	    {
	      OutputFormat format = OutputFormat.createPrettyPrint();
	      String defaultEncode = "UTF-8";
	      if (encode != null)
	        defaultEncode = encode;
	      filename = createNewFileName(filename);
	      XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(filename), defaultEncode), format);
	      writer.write(document);
	      writer.close();
	    }
	    catch (Exception ex)
	    {
	      flag = false;
	      ex.printStackTrace();
	    }
	    return flag;
	  }
	  /**
	   * @功能: 生成XML报文不换行 
	   * @编码: luyd luyuandeng@gzeport.com 2013-6-4 下午5:28:29
	   */
	  public static boolean doc2XmlFileOneLine(String documentContent, String filename, String encode) {
		Document document = string2Document(documentContent);
	    boolean flag = true;
	    try
	    {
	  //    OutputFormat format = OutputFormat.createPrettyPrint();
	      String defaultEncode = "UTF-8";
	      if (encode != null)
	        defaultEncode = encode;
	      filename = createNewFileName(filename);
	      XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(filename), defaultEncode));
	      writer.write(document);
	      writer.close();
	    }
	    catch (Exception ex)
	    {
	      flag = false;
	      ex.printStackTrace();
	    }
	    return flag;
	  }
	  
	  public static Document string2Document(String s)
	  {
	    Document doc = null;
	    try
	    {
	      String s2 = new String(s.getBytes());
	      doc = DocumentHelper.parseText(s2);
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return doc;
	  }
	  
	  public static String createNewFileName(String fileName)
	  {
	    String newfileName = fileName;
	    File file = new File(fileName);
	    if (file.exists())
	    {
	      int i = fileName.indexOf('.');
	      String extName = fileName.substring(0, i);
	      if ((extName.indexOf('(') != -1) && (extName.indexOf(')') != -1))
	      {
	        int k = extName.indexOf('(');
	        int m = extName.indexOf(')');
	        String str = extName.substring(k + 1, m);
	        newfileName = fileName.substring(0, k) + "(" + String.valueOf(Integer.parseInt(str) + 1) + ")" + fileName.substring(i);
	      }
	      else {
	        newfileName = fileName.substring(0, i) + "(" + String.valueOf(1) + ")" + fileName.substring(i);
	      }
	      newfileName = createNewFileName(newfileName);
	    }
	    return newfileName;
	  }
}
