<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="BIG5" import="com.store.model.StoreVO" import="java.util.GregorianCalendar"
    import="java.util.Calendar"
    import="java.text.SimpleDateFormat"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>���a�|������</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="<%= request.getContextPath() %>/js/react.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/react-dom.min.js"></script>
    <!-- <script src="build/react-with-addons.min.js"></script> -->
    
    <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/front-end/store/css/store_main.css">
  </head>
<body>
<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>�q���ƭק� - update_reaervation_input.jsp</h3>
		<a href="<%=request.getContextPath()%>/front-end/chenken_index.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></td>
	</tr>
</table>

<h3>��ƭק�:</h3>
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/reservation/reservation.do" name="form1">
<table border="0">
	<tr>
		<td>�q��s��:<font color=red><b>*</b></font></td>
		<td>${reservationVO.resvno }</td>
	</tr>
	<tr>
		<td>�q��m�W:</td>
		<td><input type="TEXT" name="memno" size="45" value="${reservationVO.memno }" /></td>
	</tr>
	<tr>
		<td>���:</td>
		<td><input type="TEXT" name="tableno" size="45"	value="${reservationVO.stotableVO.tableno }" /></td>
	</tr>
	<tr>
		<td>�q����:</td>
		<td bgcolor="#CCCCFF">
		    <input class="cal-TextBox"
			onFocus="this.blur()" size="9" readonly type="text" name="resvdate" value="${reservationVO.resvdate }">
			<a class="so-BtnLink"
			href="javascript:calClick();return false;"
			onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true);"
			onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
			onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('form1','hiredate','BTN_date');return false;">
		    <img align="middle" border="0" name="BTN_date"	src="images/btn_date_up.gif" width="22" height="17" alt="�}�l���"></a>
		</td>
	</tr>
	<tr>
		<td>�q��ɶ�:</td>
		<td><input type="TEXT" name="resvperiod" size="45"	value="${reservationVO.resvperiod }" /></td>
	</tr>
	<tr>
		<td>�q�촪��:</td>
		<td><input type="TEXT" name="teamno" size="45" value="${reservationVO.teamno }" /></td>
	</tr>

	
	<tr>
		<td>�q�쪬�A:</td>
		<td><input type="TEXT" name="resvstate" size="45" value="${reservationVO.resvstate }" /></td>		
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="resvno" value="${reservationVO.resvno }">

<input type="hidden" name="requestURL" value="${param.requestURL}"><!--������e�X�ק諸�ӷ��������|��,�A�e��Controller�ǳ���椧��-->
<input type="hidden" name="whichPage" value="${param.whichPage }">  <!--�u�Ω�:istAllEmp.jsp-->
<input type="hidden" name="rowsPerPage" value="${param.rowsPerPage }">  <!--�u�Ω�:istAllEmp.jsp-->
<input type="hidden" name="compositeQuery" value="${param.compositeQuery }">  <!--�u�Ω�:istAllEmp.jsp-->
<input type="submit" value="�e�X�ק�"></FORM>

<br>�e�X�ק諸�ӷ��������|:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%= request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"):</font> <%= request.getParameter("whichPage")%> (���d�ҥثe�u�Ω�:istAllEmp.jsp))</b>
</body>
</html>