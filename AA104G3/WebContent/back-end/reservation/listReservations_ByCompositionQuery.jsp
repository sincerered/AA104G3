<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="BIG5" 
    import="com.store.model.StoreVO"
    import="com.stotable.model.StotableVO"
    import="java.util.*"
    import="java.text.SimpleDateFormat"
    import="org.json.JSONArray"
    import="org.json.JSONObject"
    import="com.reservation.model.ReservationVO"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>店家會員中心</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/jquery-ui.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="<%= request.getContextPath() %>/js/react.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/react-dom.min.js"></script>
    <!-- <script src="build/react-with-addons.min.js"></script> -->
    
    <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery-ui.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/babel.min.js"></script>
    <script type="text/jsx" src="<%= request.getContextPath() %>/back-end/reservation/js/listReservations.jsx"></script>
  </head>
<body>
<a href="<%= request.getContextPath() %>/front-end/chenken_index.jsp">home</a>
<c:if test="${not empty errorMsgs}">
  <font color='red'>請修正以下錯誤:
  <ul>
  <c:forEach var="message" items="${errorMsgs}">
    <li>${message}</li>
  </c:forEach>
  </ul>
  </font>
</c:if>





	<input type="hidden" name="rowNumber" value="${requestScope.rowNumber }">
<FORM method="post" id="submitForm" action="<%= request.getContextPath()%>/reservation/reservation.do">
	
	<input type="hidden" name="action" value="listReservations_ByCompositionQuery">
	<input type="hidden" name="whichPage" value="${(empty param.whichPage )?"1":param.whichPage }">
	<input type="hidden" name="requestURL" value="<%= request.getServletPath()%>">
	<input type="hidden" id="compositeQuery" name="compositeQuery" value="${param.compositeQuery }">
	
	<select name="rowsPerPage">
		<option value="1" ${param.rowsPerPage=="1"?"selected":"" }>1筆</option>
		<option value="3" ${param.rowsPerPage=="3"?"selected":"" }>3筆</option>
		<option value="5" ${param.rowsPerPage=="5"?"selected":"" }>5筆</option>
		<option value="10" ${(param.rowsPerPage=="10" || empty param.rowsPerPage)?"selected":"" }>10筆</option>
	</select>
</FORM>



<div id="compositeQueryPanel">
	<input type="text" data-key="resvno" placeholder="resvno" value="${requestScope.queryMap.resvno[0]}">
	<input type="text" data-key="memname" placeholder="memname" value="${requestScope.queryMap.memname[0]}">
	<input type="text" data-key="memno" placeholder="memno" value="${requestScope.queryMap.memno[0]}">
	<input type="text" data-key="memid" placeholder="memid" value="${requestScope.queryMap.memid[0]}">
	<input type="text" data-key="stono" placeholder="stono" value="${requestScope.queryMap.stono[0]}">
	
	<br>
	<select data-key="resvdateMin">
		<option value="" ${(empty requestScope.queryMap.resvdateMin[0])?'selected':'' }>所有</option>
		<option value="-7" ${(requestScope.queryMap.resvdateMin[0] == '-7')?'selected':'' }>7天內</option>
		<option value="-30" ${(requestScope.queryMap.resvdateMin[0] == '-30')?'selected':'' }>30天內</option>
		<option value="-90" ${(requestScope.queryMap.resvdateMin[0] == '-90')?'selected':'' }>90天內</option>
		<option value="-180" ${(requestScope.queryMap.resvdateMin[0] == '-180')?'selected':'' }>180天內</option>
	</select>
	每頁
	總筆數${requestScope.rowNumber }&nbsp;
	第${(empty param.whichPage )?"1":param.whichPage }頁
</div>	

	<input type="button" id="newPage" value="送出">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" id="prevPage" value="上一頁">
	<input type="button" id="nextPage" value="下一頁">


<script>
	var compositeQueryPanel;
	var compositeQuery;
	function mergeCompositeQuery() {
		var childs = compositeQueryPanel.children;
		var len = childs.length;
		var obj = {};
		for (var i = 0; i < len; i++) {
			var child = childs[i];
			if (child.value !== "") {
				obj[child.dataset.key] = child.value;
			}
		}
		compositeQuery.value = JSON.stringify(obj);
	}
	
	jQuery(function() {
		compositeQueryPanel = document.getElementById('compositeQueryPanel');
		compositeQuery = document.getElementById('compositeQuery');
		submitForm = document.getElementById('submitForm');
		updateResvnoInput = submitForm.querySelector('input[name="resvno"]');
		actionInput = submitForm.querySelector('input[name="action"]');
		rowsPerPage = document.querySelector('select[name="rowsPerPage"]');
		whichPage = document.querySelector('input[name="whichPage"]');
		rowNumber = document.querySelector('input[name="rowNumber"]');
		newPage = document.getElementById('newPage');
		prevPage = document.getElementById('prevPage');
		nextPage = document.getElementById('nextPage');
		newPage.addEventListener('click', function(e) {
			whichPage.value = '';
			mergeCompositeQuery()
			submitForm.submit();
		}, false);
		prevPage.addEventListener('click', function(e) {
			if (whichPage.value != '1') {
				whichPage.value = parseInt(whichPage.value) - 1;
				mergeCompositeQuery()
				submitForm.submit();
			}
		}, false);
		nextPage.addEventListener('click', function(e) {
			if (parseInt(rowNumber.value) / parseInt(rowsPerPage.value) >= parseInt(whichPage.value)) {
				whichPage.value = parseInt(whichPage.value) + 1;
				mergeCompositeQuery()
				submitForm.submit();
			}
		}, false);
		//updateBtns = document.querySelectorAll('table input[name="updateBtn"]');
		//var len = updateBtns.length;
		//for (var i = 0; i < len; i++) {
		//	updateBtns[i].addEventListener('click', function() {
		//		updateResvnoInput.value = this.dataset.resvno;
		//		submitForm.action = '<%=request.getContextPath()%>/reservation/reservation.do';
		//		actionInput.value = 'getOne_For_Update';
		//		submitForm.submit();
		//	}, false);
		//}
		
		
	}); 
</script>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>訂位編號</th>
		<th>會員編號</th>
		<th>訂位桌位</th>
		<th>訂位日期</th>
		<th>訂位時間</th>
		<th>揪團編號</th>
		<th>訂位狀態</th>
		<th>修改</th>
		<th>詳細資料</th>
	</tr>
	<%
	JSONArray jsonArray = new JSONArray();
	
	
	%>
	<jsp:useBean id="reservationSvc" class="com.reservation.model.ReservationService"/>
	
	<c:forEach var="reservationVO" items="${requestScope.listReservations_ByCompositionQuery}">
	<%	
		ReservationVO reservationVO = (ReservationVO)pageContext.getAttribute("reservationVO");
		JSONObject resvJson = new JSONObject(reservationVO);
	
		StotableVO stotableVO = reservationVO.getStotableVO();
		String tableno = stotableVO.getTableno();
		resvJson.put("tableno", tableno);
		jsonArray.put(resvJson);
	%>
		<tr align='center' valign='middle'>
			<td>${reservationVO.resvno}</td>
			<td>${reservationVO.memno}</td>
			<td>${reservationVO.stotableVO.tableno}</td>
			<td>${reservationVO.resvdate}</td>
			<td>${reservationVO.resvperiod.indexOf('1')}:00-${reservationVO.resvperiod.lastIndexOf('1') + 1}:00 </td>
			<td>${reservationVO.teamno}</td>
			<td>${reservationVO.resvstate}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reservation/reservation.do">
			    <input type="submit" value="修改"> 
			    <input type="hidden" name="resvno" value="${reservationVO.resvno}">
			    <input type="hidden" name="action" value="getOne_For_Update">
			    <input type="hidden" name="whichPage" value="${param.whichPage }">
			    <input type="hidden" name="rowsPerPage" value="${param.rowsPerPage }">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			    <input type="hidden" name="compositeQuery" value="${param.compositeQuery }">
			    
			    
			</td></FORM>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reservation/reservation.do">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="resvno" value="${reservationVO.resvno}">
			    <input type="hidden" name="action" value="">
			</td></FORM>
		</tr>
	</c:forEach>
</table>

<div id="content">
</div>	

<script type="text/jsx">
	var url = '<%=request.getContextPath()%>/reservation/reservation.do';
	var heads = {
		"resvno":"訂位編號",
		"memno":"訂位會員",
		"teamno":"揪團編號",
		"tableno":"桌位編號",
		"resvdate":"訂位日期",
		"resvperiod":"訂位時間",
		"resvstate":"訂位狀態",
	};
	var immutable = [1, 0, 0, 0, 0, 0, 0];
	var data = <%=jsonArray.toString() %>;
	ReactDOM.render(<Excel heads={heads} initialData={data} url={url} immutable={immutable}/>,
			document.getElementById('content'));
</script>

</body>
</html>