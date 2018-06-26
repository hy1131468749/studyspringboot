app.controller('mainCtl',['$scope','$rootScope','$http','$state','$location',function($scope,$rootScope,$http,$state,$location){
	//获取菜单
    var mainIndex = "";
	$.ajax({
		type:"post",
		url: "/system/getUrls",
		async:false,
		data:{},
		success:function (resData) {
		   
			if(resData.urlView){
		    	var menus = "";
		    	$.each(resData.urlView,function(key,value){
		    	    $("#menu"+value.alias).hide();
		    	});	
		    }
			if(resData.showViews ){
				mainIndex = "main."+resData.showViews[0].alias;
		    }
			
		}
	});   
	if(localStorage.userName){
    	$scope.userName = localStorage.userName;
    }else{
    	$state.go('index');
    }  
    //监测选中目录和路由
    function monitorRouter () {
    	var routers  = ['/main/smokedetector','/main/devicehggaslist','/main/devices','/main/connecttype','/main/org','/main/user','/main/urlmanage']
    	//var routers  = ['/main/well','/main/dustbin','/main/testData','/main/devices']
    	var routerStr = $location.path();
    	if(routerStr == '/main'){
    		
    		$state.go(mainIndex);
    	}else{
    		if(routers.indexOf(routerStr) > -1){
    			$('.oNavlist').removeClass('active');
    			$('.oNavlist').eq(routers.indexOf(routerStr)).addClass('active');
    		}
    	}
    }
    monitorRouter ();
    $('.oNavlist').click(function () {
    	$('.oNavlist').removeClass('active');
    	$(this).addClass('active');
    })
    //退出
    $scope.quit = function () {
    	localStorage.userName = '';
    	$state.go('index');
    }
}])