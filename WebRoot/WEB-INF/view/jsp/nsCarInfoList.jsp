<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>广州电子口岸--南沙在途GPS监控信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="车辆信息监控列表">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css"> 

/* CSS Document */

 

body { 

    font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 

    color: #4f6b72; 

    background: #E6EAE9; 

} 

  

a { 

    color: #c75f3e; 

} 

  

#mytable { 

    width: 900px; 

    padding: 0; 

    margin: 0; 

} 

  

caption { 

    padding: 0 0 5px 0; 

    width: 900px;      

    font: italic 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 

    text-align: right; 

} 

  

th { 

    font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 

    color: #4f6b72; 

    border-right: 1px solid #C1DAD7; 

    border-bottom: 1px solid #C1DAD7; 

    border-top: 1px solid #C1DAD7; 

    letter-spacing: 2px; 

    text-transform: uppercase; 

    text-align: left; 

    padding: 6px 6px 6px 12px; 

    background: #CAE8EA url(images/bg_header.jpg) no-repeat; 

} 

  

th.nobg { 

    border-top: 0; 

    border-left: 0; 

    border-right: 1px solid #C1DAD7; 

    background: none; 

} 

  

td { 

    border-right: 1px solid #C1DAD7; 

    border-bottom: 1px solid #C1DAD7; 

    background: #fff; 

    font-size:11px; 

    padding: 6px 6px 6px 12px; 

    color: #4f6b72; 

} 

  

 

td.alt { 

    background: #F5FAFA; 

    color: #797268; 

} 

  

th.spec { 

    border-left: 1px solid #C1DAD7; 

    border-top: 0; 

    background: #fff url(images/bullet1.gif) no-repeat; 

    font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 

} 

  

th.specalt { 

    border-left: 1px solid #C1DAD7; 

    border-top: 0; 

    background: #f5fafa url(images/bullet2.gif) no-repeat; 

    font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 

    color: #797268; 

} 

/*---------for IE 5.x bug*/

html>body td{ font-size:11px;} 

</style> 



  </head>
  
  <body>
<!--     This is my JSP page. ${message}<br> -->
    <table  id="mytable"   align="center" width="100%">
	    <tr bgcolor="gray">
	    <th>序号</th><th>车牌号</th><th>在途编号</th><th>最近开启时间</th><th>最近禁用时间</th><th>状态</th><th>操作</th>
	    </tr>
    
   <c:forEach var="car"  items="${message}" varStatus="s"  step="1">
 	     <tr>
	    <td>${s.index}</td><td>${car.plate}</td><td>${car.inareano }</td><td>${car.starttime }</td><td>${car.stoptime }</td><td><c:choose><c:when test="${car.status == '1'}"><font color="green">正在监控</font></c:when><c:otherwise ><font color="red">未监控</font></c:otherwise></c:choose></td>
	    <td><c:choose><c:when test="${car.status == '1'}"><font color="gray">手动开启</font>&nbsp;&nbsp;&nbsp;&nbsp; <a href="<%=basePath%>stopNsCarinfo.do?plate=${car.plate}&inAreaNo=${car.inareano }">手动禁止</a> </c:when><c:otherwise ><a href="<%=basePath%>startNsCarinfo.do?plate=${car.plate}&inAreaNo=${car.inareano }">手动开启</a>&nbsp;&nbsp;&nbsp;&nbsp;<font color="gray">手动禁止</font></c:otherwise></c:choose>
	    </tr>
   </c:forEach>
    
   </table>
  </body>
</html>
