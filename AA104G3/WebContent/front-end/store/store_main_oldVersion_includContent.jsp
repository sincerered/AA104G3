<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.stono }">
	<jsp:forward page="/front-end/chenken_index.jsp"></jsp:forward>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>店家會員中心</title>

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

    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-2">
			<%@ include file="page/leftSide_list.file" %>>
        </div>
        <div class="col-xs-12 col-sm-10">
          <ol class="breadcrumb">
            <li><a href="<%= request.getContextPath() %>/front-end/chenken_index.jsp">Home</a></li>
			
				<li class="active">會員主頁</li>
			
			<c:if test="${param.action_002 == 'store_detail' }">
				<li><a href="<%= request.getContextPath() %>/store/store.do?action=store_main">會員主頁</a></li>
            	<li class="active">會員資料</li>
			</c:if>                  
			<c:if test="${param.action_002 == 'store_changeImage' }">
				<li><a href="<%= request.getContextPath() %>/store/store.do?action=store_main">會員主頁</a></li>
            	<li><a href="<%= request.getContextPath() %>/store/store.do?action=store_main&action_002=store_detail">會員資料</a></li>
            	<li class="active">更改圖片</li>
			</c:if>
          </ol>

          <div class="col-xs-12 col-sm-12">
            

            <div class="col-xs-12 col-sm-12">
            	<div>param.action= ${param.action }</div>
            	<div>param.action_002= ${param.action_002 }</div>
            	<div>request.getServletPath= <%= request.getServletPath() %></div>
            	<div>test1= ${'aaa' == 'aaa'? ('fsa'):'no' }</div>
				<c:if test="${empty param.action_002 }">
					<%@ include file="page/store_main.file" %>
				</c:if>            	
				<c:if test="${param.action_002 == 'store_detail' }">
					<%@ include file="page/store_detail.file" %>
				</c:if>
				<c:if test="${param.action_002 == 'store_changeImage' }">
					<%@ include file="page/store_changeImage.file" %>
				</c:if>
            </div>


            
          </div>
        </div>

      </div>
    </div>   
  </body>
</html>