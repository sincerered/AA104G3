<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<%
String kerker = "dsfsd";
pageContext.setAttribute("kerker", kerker);
%>

	${errorMsgs }
	<br>
	stono =  ${storeVO.stono }
	<br>
	this is chenken's index
	<br>
	<a href="<%= request.getContextPath() %>/store/store.do?action=logout">logout</a>
	<a href="<%= request.getContextPath() %>/front-end/login.jsp">login</a>
	<a href="<%= request.getContextPath() %>/front-end/store/register.jsp">register</a>
	<a href="<%= request.getContextPath() %>/store/store.do?action=get_store_main">store</a>
	<a href="<%= request.getContextPath() %>/reservation/reservation.do?action=listReservations_ByCompositionQuery">listResv_byComposition</a>
	
	<form action="<%= request.getContextPath() %>/store/store.do" method="post">
		<input type="text" name="stono" />
		<input type="hidden" name="action" value="getOne_for_display" />
		<input type="submit" value="submit" />
		
	</form>
	
	
</body>
</html>