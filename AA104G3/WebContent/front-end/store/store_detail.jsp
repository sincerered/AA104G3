<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty sessionScope.storeVO }">
	<jsp:forward page="/front-end/chenken_index.jsp"></jsp:forward>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>���a�|������</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="<%=request.getContextPath()%>/js/react.min.js"></script>
<script src="<%=request.getContextPath()%>/js/react-dom.min.js"></script>
<!-- <script src="build/react-with-addons.min.js"></script> -->

<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/store/css/store_main.css">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-2">
				<%@ include file="page/leftSide_list.file"%>
			</div>
			<div class="col-xs-12 col-sm-10">
				<ol class="breadcrumb">
					<li><a
						href="<%=request.getContextPath()%>/front-end/chenken_index.jsp">Home</a></li>

					<li><a href="<%=request.getContextPath()%>/store/store.do?action=get_store_main">�|���D��</a></li>
					<li class="active">�|�����</li>
				</ol>

				<div class="col-xs-12 col-sm-12">
					<div class="col-xs-12 col-sm-12">
						<div>param.action= ${param.action }</div>
						<div>param.action_002= ${param.action_002 }</div>
						<div>request.getServletPath=<%=request.getServletPath()%></div>
						<div>test1= ${'aaa' == 'aaa'? ('fsa'):'no' }</div>
						<div class="row text-center">
							<ul class="nav nav-pills">
								<li role="presentation" class=""><a href="<%=request.getContextPath()%>/store/store.do?action=get_store_update_pwd">���K�X</a></li>
								<li role="presentation"><a
									href="<%=request.getContextPath()%>/front-end/store/store_update_image.jsp">���Ϥ�</a></li>
								<li role="presentation"><a href="#">Messages</a></li>
							</ul>
							<div class="chenken-store-detail-list">
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">�m�W�G</div>
									<div class="col-xs-12 col-sm-9">${storeVO.stoname}</div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">�H�c�G</div>
									<div class="col-xs-12 col-sm-9">${storeVO.stoemail}</div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">�q�ܡG</div>
									<div class="col-xs-12 col-sm-9">${storeVO.stophone}</div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">�a�}�G</div>
									<div class="col-xs-12 col-sm-9">${storeVO.stoaddr}</div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">�a�ϸg�n�סG</div>
									<div class="col-xs-12 col-sm-9"></div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">��~�ɶ��G</div>
									<div class="col-xs-12 col-sm-9"></div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">���Y�Ȧ�W�١G</div>
									<div class="col-xs-12 col-sm-9"></div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3">���Y�Ȧ�b���G</div>
									<div class="col-xs-12 col-sm-9"></div>
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="col-xs-12 col-sm-3"></div>
									<div class="col-xs-12 col-sm-9"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>