<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty sessionScope.storeVO }" >
	<jsp:forward page="/front-end/chenken_index.jsp" />
</c:if>
<c:if test="${not empty sessionScope.memberVO }" >
	<jsp:forward page="/front-end/chenken_index.jsp" />
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
    
    <script src="http://maps.google.com/maps/api/js?key=AIzaSyBigIj9ATOeMIulS0A4P35t6LPhW_yts18"></script>
    
    <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/front-end/store/js/stobh.js"></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/front-end/store/css/store_main.css">
 
    <style type="text/css">
      div#stobhPanel{
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

		form>div:not(:last-child){
			margin-bottom: 8px;
		}
    </style>
    
    <script>
    	var stoidInput;
    	var stoaddrInput;
    	var geocoder;
    	var stolongInput;
    	var stolatiInput;
    	var showMap;
    	var stobhPanel;
    	
    	jQuery(function() {
    		stoidInput = document.querySelector('input[name="stoid"]');
    		stoaddrInput = document.querySelector('input[name="stoaddr"]');
    		stolongInput = document.querySelector('input[name="stolong"]');
    		stolatiInput = document.querySelector('input[name="stolati"]');
    		stobhPanel = document.getElementById('stobhPanel');
    		showMap = document.getElementById('showMap');
    		
    		geocoder = new google.maps.Geocoder()
    		
    		stoidInput.addEventListener('blur', function() {
    			$.ajax({
    				url:'<%= request.getContextPath()%>/store/store.do',
    				type:'post',
    				data:{action: 'confirm_stoid', stoid: stoidInput.value},
    				success: function(data) {
    					console.log(data);
    					if (data == 'true') {
    						
    					}
    				},
    			});
    		}, false);
    		
    		stoaddrInput.addEventListener('blur', function(e) {
    			geocoder.geocode({
    				address: e.target.value,
    			}, function(results, status) {
    				if (status == google.maps.GeocoderStatus.OK) {
    					var lat = results[0].geometry.location.lat();
    					var lng = results[0].geometry.location.lng();
    					
    					stolatiInput.value = lat;
    					stolongInput.value = lng;
    					  var latlng = new google.maps.LatLng(lat, lng);
    					  var map = new google.maps.Map(showMap, {
    					    zoom: 14,
    					    center: latlng,
    					    mapTypeId: google.maps.MapTypeId.ROADMAP
    					  });
    					  var mark = new google.maps.Marker({
    						    position: latlng,
    						    map: map,
    						    icon: '../../images/mapMarker.png'
    						  });
    				}
    			});
    		}, false);
    		
    		stolatiInput.parentNode.addEventListener('change', function(e) {
    			console.log(stolatiInput.value + '    ' + stolongInput.value);
    		}, false);
    		
    		ReactDOM.render(React.createElement(
    				Stobh,
    				{unselectedColor: 'rgb(255, 255, 255)', selectedColor: 'rgb(180, 50, 200)'}
    		), stobhPanel);
    		
    	});
    </script>
    
  </head>
  <body>
  <div class="container">
    <form method="post" action="<%=request.getContextPath()%>/store/store.do">
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">帳號</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoid" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">密碼</div>
        <div class="col-xs-12 col-sm-10"><input type="password" name="stopw" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">確認密碼</div>
        <div class="col-xs-12 col-sm-10"><input type="password" name="stopwConfirm" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">店家名稱</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoname" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">店家電話</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stophone" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">電子郵件地址</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoemail" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">確認電子郵件地址</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoemailConfirm" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">地址</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoaddr" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">經緯度</div>
        <div class="col-xs-12 col-sm-10">
        	<input type="text" name="stolati" class="form-control">
        	<input type="text" name="stolong" class="form-control">
        	<div id="showMap" style="width:400px;height:300px;"></div>
        </div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">店家擁有者</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="stoowner" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">圖片</div>
        <div class="col-xs-12 col-sm-10"><input type="file" name="stopic" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">營業執照</div>
        <div class="col-xs-12 col-sm-10"><input type="file" name="stoproof" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">營業時間</div>
        <div class="col-xs-12 col-sm-10" id="stobhPanel"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">銀行代碼</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="bankno" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">銀行名稱</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="bankname" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">銀行帳號</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="accountno" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2">銀行帳號戶名</div>
        <div class="col-xs-12 col-sm-10"><input type="text" name="accountname" class="form-control"></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2"></div>
        <div class="col-xs-12 col-sm-10"><input type="submit" name=""></div>
      </div>
      <div class="col-xs-12 col-sm-12">
        <div class="col-xs-12 col-sm-2"></div>
        <div class="col-xs-12 col-sm-10"><input type="text" name=""></div>
      </div>
      <input type="hidden" name="action" value="register">
    </form>
    
   </div> 
  	
  </body>
</html>