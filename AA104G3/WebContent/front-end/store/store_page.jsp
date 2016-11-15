<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty requestScope.storeVO}">
	<jsp:forward page="/front-end/chenken_index.jsp"></jsp:forward>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${storeVO.stoname}</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/front-end/store/css/reservation.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">
   
      body {
        position: relative;
      }
      ul.nav-tabs {
        height: 50px;
      }

    </style>
    <script src="<%= request.getContextPath() %>/js/react.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/react-dom.min.js"></script>

    <script src="<%= request.getContextPath() %>/js/babel.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/front-end/store/js/store_page.js"></script>
  </head>


  <!-- Modal -->


  <body data-spy="scroll" data-target="#navbar-example">
  	${storeVO.stoname}
    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-12">
          <div class="row">
            
            <div class="col-xs-12 col-sm-8">
              <div class="row">
                <img class="img-responsive" src="<%=request.getContextPath() %>/store/store.do?action=get_stopic&stono=${storeVO.stono}" alt="nothing">
                
              </div>
              
            </div>
            <div class="col-xs-12 col-sm-4">
            </div>
          </div>
        </div>


        <div class="col-xs-12 col-sm-12" style="height:50px">

         <div class="row">
           <div class="col-xs-12 col-sm-12" id="navbar-example" style="position:relative;top:0px;z-index:1;background-color: rgb(87, 190, 230);height:50px">
             <ul class="nav nav-tabs" role="tablist">
               <li role="presentation"><a href="#div3">第一區</a></li>
               <li role="presentation"><a href="#div4">第二區</a></li>
               <li role="presentation"><a href="#div1">第三區</a></li>
               <li style="float:right"><a href="" data-toggle="modal" data-target="#myModal" data-stono="000001">訂位</a></li>
             </ul>
           </div>
         </div>
        </div>
         
        <div class="col-xs-12 col-sm-12" style="height:333px;border:2px solid blue;" id="div1"></div>
        <div class="col-xs-12 col-sm-12" style="height:333px;border:2px solid blue;" id="div2"></div>
        <div class="col-xs-12 col-sm-12" style="height:333px;border:2px solid blue;" id="div3"></div>
        <div class="col-xs-12 col-sm-12" style="height:333px;border:2px solid blue;" id="div4"></div>
        <div class="col-xs-12 col-sm-12" style="height:333px;border:2px solid blue;" id="div5"></div>
       
          

      </div>
    </div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Modal title</h4>
          </div>
          <div class="modal-body">
            <div id="chenken-reservation" data-stono=""></div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>    

    <script src="<%= request.getContextPath() %>/front-end/store/js/reservation.jsx" type="text/jsx"></script>
  </body>
</html>