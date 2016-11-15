<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty sessionScope.storeVO }">
	<jsp:forward page="/front-end/chenken_index.jsp"></jsp:forward>
</c:if>
<c:if test="${not empty sessionScope.memberVO }">
	<jsp:forward page="/front-end/chenken_index.jsp"></jsp:forward>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title Page</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="<%= request.getContextPath() %>/js/jquery.js"></script>
    <script src="<%= request.getContextPath() %>js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/react.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/react-dom.min.js"></script>
    <!-- <script src="build/react-with-addons.min.js"></script> -->
    <script src="<%= request.getContextPath() %>/js/babel.min.js"></script>


    <style type="text/css">
      form.chenken-input-text input {
        width: 100%;
        padding: 4px;
        font-size: 1.3em;
        font-weight: bold;
        color: #777;
        margin-bottom: 15px;
      }

      form.chenken-input-text input:focus {
        color: #111;
      }
    </style>
    <script type="text/javascript">

   
    </script>
  </head>
  <body>
    <div class="col-sm-4 col-sm-push-4">
      <div class="row">
      	<c:if test="${not empty errorMsgs }">
      		<c:forEach var="errorMsg" items="${errorMsgs }">
      			<div style="color:red">${errorMsg}</div>
      		</c:forEach>
      	</c:if>
        <form class="chenken-input-text" action="<%= request.getContextPath() %>/store/store.do">
          <input type="text" name="stoid" placeholder="±b¸¹">
          <input type="password" name="stopw" placeholder="±K½X">
          <input type="hidden" name="action" value="login">
          <input type="submit" name="" value="µn¤J">
        </form>
        <a href="<%= request.getContextPath() %>/front-end/store/register.jsp">µù¥U</a>
        
      </div>
    </div>
   
  </body>
</html>