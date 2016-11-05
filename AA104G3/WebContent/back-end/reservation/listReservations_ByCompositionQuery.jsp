<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5" import="com.store.model.StoreVO" import="java.util.GregorianCalendar"
    import="java.util.Calendar"
    import="java.text.SimpleDateFormat"
    import="org.json.JSONArray"
    import="org.json.JSONObject"
    import="com.reservation.model.ReservationVO"
    import="java.util.List"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="reservationSvc" scope="page" class="com.reservation.model.ReservationService" />
<jsp:useBean id="listReservations_ByCompositionQuery" scope="request" type="java.util.List" />
${listReservations_ByCompositionQuery == null }
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
<a href="<%= request.getContextPath() %>/front-end/chenken_index.jsp">home</a>
<c:if test="${not empty errorMsgs}">
  <font color='red'>�Эץ��H�U���~:
  <ul>
  <c:forEach var="message" items="${errorMsgs}">
    <li>${message}</li>
  </c:forEach>
  </ul>
  </font>
</c:if>

<% 
	GregorianCalendar gc = new GregorianCalendar();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
%>



<FORM method="post" action="<%= request.getContextPath() %>/reservation/reservation.do">
	
	<input type="hidden" name="action" value="listReservations_ByCompositionQuery">
	<input type="hidden" name="whichPage" value="${requestScope.whichPage }">
	<input type="text" name="memname" placeholder="memname" value="${param.memname}">
	<input type="text" name="memno" placeholder="memno" value="${param.memno }">
	<input type="text" name="memid" placeholder="memid" value="${param.memid }">
	<input type="text" name="stono" placeholder="stono" value="${param.stono }">
	
	<br>
	<select name="resvdateMin">
		<%gc.add(Calendar.DAY_OF_MONTH, -7); %>
		<option value="<%=sdf.format(gc.getTime()) %>" <%= (sdf.format(gc.getTime()).equals(request.getParameter("resvdateMin")))?"selected":"" %>>7�Ѥ�</option>
		<%gc.add(Calendar.DAY_OF_MONTH, -23); %>
		<option value="<%=sdf.format(gc.getTime()) %>" <%= (sdf.format(gc.getTime()).equals(request.getParameter("resvdateMin")))?"selected":"" %>>30�Ѥ�</option>
		<%gc.add(Calendar.DAY_OF_MONTH, -60); %>
		<option value="<%=sdf.format(gc.getTime()) %>" <%= (sdf.format(gc.getTime()).equals(request.getParameter("resvdateMin")))?"selected":"" %>>90�Ѥ�</option>
		<%gc.add(Calendar.DAY_OF_MONTH, -90); %>
		<option value="<%=sdf.format(gc.getTime()) %>" <%= (sdf.format(gc.getTime()).equals(request.getParameter("resvdateMin")))?"selected":"" %>>180�Ѥ�</option>
		<option value="" <%= ("".equals(request.getParameter("resvdateMin")))?"selected":"" %>>�Ҧ�</option>
	</select>
	�C��
	<select name="rowsPerPage">
		<option value="1" ${requestScope.rowsPerPage=="1"?"selected":"" }>1��</option>
		<option value="3" ${requestScope.rowsPerPage=="3"?"selected":"" }>3��</option>
		<option value="5" ${requestScope.rowsPerPage=="5"?"selected":"" }>5��</option>
		<option value="10" ${requestScope.rowsPerPage=="10"?"selected":"" }>10��</option>
	</select>
	�`����${requestScope.rowNumber }&nbsp;
	��${requestScope.whichPage }��
	
	<input type="button" id="newPage" value="�e�X">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" id="prevPage" value="�W�@��">
	<input type="button" id="nextPage" value="�U�@��">
</FORM>

<script>
	var whichPage = document.querySelector('input[name="whichPage"]');
	var form = document.querySelector('form');
	var newPage = document.getElementById('newPage');
	var prevPage = document.getElementById('prevPage');
	var nextPage = document.getElementById('nextPage');
	newPage.addEventListener('click', function(e) {
		whichPage.value = '1';
		form.submit();
	}, false);
	prevPage.addEventListener('click', function(e) {
		if (whichPage.value != '1') {
			whichPage.value = parseInt(whichPage.value) - 1;
			form.submit();
		}
	}, false);
	nextPage.addEventListener('click', function(e) {
		whichPage.value = parseInt(whichPage.value) + 1;
		form.submit();
	}, false);
</script>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>�q��s��</th>
		<th>�|���s��</th>
		<th>�q����</th>
		<th>�q����</th>
		<th>�q��ɶ�</th>
		<th>���νs��</th>
		<th>�q�쪬�A</th>
		<th>�ק�</th>
		<th>�ԲӸ��</th>
	</tr>
	<%
	JSONArray jsonArray = new JSONArray();
	List<ReservationVO> list = (List<ReservationVO>)request.getAttribute("listReservations_ByCompositionQuery");
	for (ReservationVO resvVO : list) {
		JSONObject json = new JSONObject(resvVO);
		jsonArray.put(json);
	}
	System.out.println(jsonArray.toString());
	%>
	<c:forEach var="reservationVO" items="${listReservations_ByCompositionQuery}">
		<tr align='center' valign='middle'>
			<td>${reservationVO.resvno}</td>
			<td>${reservationVO.memno}</td>
			<td>${reservationVO.tableno}</td>
			<td>${reservationVO.resvdate}</td>
			<td>${reservationVO.resvperiod.indexOf('1')}:00-${reservationVO.resvperiod.lastIndexOf('1') + 1}:00 </td>
			<td>${reservationVO.teamno}</td>
			<td>${reservationVO.resvstate}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reservation/reservation.do">
			    <input type="submit" value="�ק�"> 
			    <input type="hidden" name="resvno" value="${reservationVO.resvno}">
			    <input type="hidden" name="action" value="getOne_For_Update">
			</td></FORM>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reservation/reservation.do">
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="resvno" value="${reservationVO.resvno}">
			    <input type="hidden" name="action" value="">
			</td></FORM>
		</tr>
	</c:forEach>
</table>
<FORM>
	
</FORM>

</body>
</html>