var app = angular.module('myApp',['ui.router'])
// $stateProvider 定义多重路由的
// $urlRouterProvider 定义非状态url的跳转
.config(['$stateProvider','$urlRouterProvider',function($sp,$urp){
	//定义路由
	$sp.state('index',{
		url: '/',
		templateUrl: 'src/login.html',
		//所有的界面(逻辑)js代码都写在controller中
		controller:'loginCtl'
	})
	//页面父路由
	.state('main',{
		url: '/main',
		templateUrl: 'src/main.html',
		controller:'mainCtl'
	})
	
	.state('main.smokedetector',{
		url:'/smokedetector',
		templateUrl:'src/smokedetector.html',
		controller:'smokedetectorCtl'
	})

	//设备
	.state('main.devices',{
		url: '/devices',
		templateUrl: 'src/devices.html',
		controller:'devicesCtl'
	})
	//对接平台管理
	.state('main.connectplatform',{
		url: '/connectplatform',
		templateUrl: 'src/connectplatform.html',
		controller:'connectPlatformCtl'
	})
	//组织管理
	.state('main.org',{
		url: '/orgCtl',
		templateUrl: 'src/org.html',
		controller:'orgCtl'
	})
	//用户管理
	.state('main.user',{
		url: '/userCtl',
		templateUrl: 'src/user.html',
		controller:'userCtl'
	})
	//平台管理
	.state('main.connecttype',{
		url: '/connectTypeCtl',
		templateUrl: 'src/connecttype.html',
		controller:'connectTypeCtl'
	})
	//角色管理
	.state('main.role',{
		url: '/roleCtl',
		templateUrl: 'src/role.html',
		controller:'roleCtl'
	})
	//权限管理
	.state('main.urlmanage',{
		url: '/urlmanageCtl',
		templateUrl: 'src/urlmanage.html',
		controller:'urlmanageCtl'
	})
	
	//红光烟感传感器列表
	.state('main.devicehggaslist',{
		url: '/devicehggaslistCtl',
		templateUrl: 'src/devicehggaslist.html',
		controller:'devicehggaslistCtl'
	})
	
	//默认跳转的路由
	$urp.when('','/');
}])
.service('mapServiceFun',function () {
	//地图初始化显示
	this.showMap = function (eleId) {
		this.map = new BMap.Map(eleId);
		this.map.centerAndZoom(new BMap.Point(121.51741,31.198117), 11);
		this.map.addControl(new BMap.MapTypeControl({
			mapTypes:[
	            BMAP_NORMAL_MAP,
	            BMAP_HYBRID_MAP
	        ]
		}));
		
		var mapStyle = [
		        	    {
		        	          "featureType": "road",
		        	          "elementType": "all",
		        	          "stylers": {
		        	                    "color": "#5a6378ff"
		        	          }
		                },
		                {
		                      "featureType": "background",
		                      "elementType": "geometry",
		                      "stylers": {
		                                "color": "#002036ff"
		                      }
		                }]
		        		this.map.setMapStyle({styleJson:mapStyle});
		        		
		        		/*根据ip所在城市定位地图中心点*/
		        		var copyMap = this.map;
		        		var myCity = new BMap.LocalCity();
		        		myCity.get(myFun)
		        		
		        		// 返回城市信息回调函数
		        		function myFun(result){
		        				var cityName = result.name;
		        				//alert(cityName);
		        				copyMap.setCenter(result.name);
		        		}
		
		this.map.enableScrollWheelZoom();
		var size = new BMap.Size(10,20)
		this.map.addControl(new BMap.CityListControl({
			anchor:BMAP_ANCHOR_TOP_LEFT,
			offset:size
		}));
	},

	//给地图添加maker点
	this.addPoint = function (pType,pDate,centerPoint) {
		this.map.clearOverlays();
		//this.map.centerAndZoom(new BMap.Point(centerPoint.lng,centerPoint.lat), 11);
		var _this = this;
		var opts = {
				width : 180,
				height: 150, 
				title : "" ,
				enableMessage:true
			};
		for(var i = 0;i < pDate.length;i++){
			var myIcon = new BMap.Icon("./public/img/device_type/" + pType + '.png', new BMap.Size(40,40));
			//var marker = new BMap.Marker(new BMap.Point(121.51741 + 0.001*(Math.floor(Math.random()*10 +1 )),31.198117 + 0.001*(Math.floor(Math.random()*10+1))),{icon:myIcon});
			var marker = new BMap.Marker(new BMap.Point(pDate[i].lng,pDate[i].lat),{icon:myIcon});
			var content = '<div class="' + pType + ' alertMap"><h3>SN:' + pDate[i].sn + '</h3><div>' + this.getpointContent(pType,pDate[i]) + '</div></div>';
			this.map.addOverlay(marker);
			addClickHandler(content,marker);
		}
		function addClickHandler(content,marker){
			marker.addEventListener("click",function(e){
				var point = new BMap.Point(e.target.getPosition().lng, e.target.getPosition().lat);
				var infoWindow = new BMap.InfoWindow(content,opts);
				_this.map.openInfoWindow(infoWindow,point);
			});
		}
	},
	this.getpointContent = function (pType,devData) {
		var pStr = '';

		if(pType == 'device_01'){
			//井盖
			pStr = '<li>开启:<span style="color:#000;">' + devData.isOpen + '</span>' + '</li>'
				+ '<li>水位:<span style="color:#000;">' + devData.waterLine + '</span>' + '</li>';
		}else if(pType == 'device_03'){
			//垃圾箱
			pStr = '<li>左桶:<span style="color:#069CF1;">' + devData.fullLeft + '</span>' + '</li>'
				+ '<li>右桶:<span style="color:#069CF1;">' + devData.fullRight + '</span>' + '</li>'
				+ '<li>烟雾:<span style="color:#069CF1;">' + devData.smokeStatus + '</span>' + '</li>'
				+ '<li>倾倒:<span style="color:#069CF1;">' + devData.obliquityStatus + '</span>' + '</li>';
		}else if(pType == 'device_02'){
			//环保箱
		
			pStr = '<li>温度:<span style="color:#069CF1;">' + devData.TMP.V + '</span>' + '</li>'
				+ '<li>湿度:<span style="color:#069CF1;">' + devData.HUN.V + '</span>' + '</li>'
				+ '<li>PM2.5:<span style="color:#069CF1;">' + devData.P25.V + '</span>' + '</li>'
				+ '<li>PM10:<span style="color:#069CF1;">' + devData.PM10.V + '</span>' + '</li>';
		}else if(pType == 'device_04'){
			//烟感传感器
		
			pStr = '<li>传感器:<span style="color:#069CF1;">' + devData.IF1 + '</span>' + '</li>';
				/*+ '<li>传感器2:<span style="color:#069CF1;">' + devData.IF2 + '</span>' + '</li>';*/
		}
		return pStr;
	}
})
.controller('IndexController',['$rootScope','$scope','$state',function($rootScope,$scope,$state){
	//$rootScope.httpUrl = 'http://192.168.11.16:7080/IRGDD/system/';
	$rootScope.httpUrl = './system/';
	
	$rootScope.$on('$stateChangeStart',function(){
//		console.log('状态开始改变');
	});
	$rootScope.$on('$stateNotFound',function(){
//		console.log('没有与之对应的状态');
	});
	$rootScope.$on('$viewContentLoading',function(){
//		console.log('视图内容正在加载');
	});
	$rootScope.$on('$viewContentLoaded',function(){
//		console.log('视图内容已经加载完毕');
	});
	
	if(localStorage.userName){
    	$state.go('main');
    }else{
    	$state.go('index');
    }
}])
//设备告警返回class过滤器
.filter('devWarnClass',function () {
	return function (val) {
		if(val == '正常' || val == '未满'){
			val = 'colorGreen';
		}else if(val == '离线'){
			val = 'colorGray';
		}else{
			val = 'colorRed';
		}
		return val;
	}
})
.filter('dateFilter',function () {
	return function (val) {
		var time = new Date(val);
		var year = time.getFullYear();
		var month = time.getMonth() + 1 > 9 ? time.getMonth() + 1  : '0' + (time.getMonth() + 1);
		var day = time.getDate();
		var hours = time.getHours() > 9 ? time.getHours() : '0' + time.getHours();
		var minutes = time.getMinutes() > 9 ? time.getMinutes() : '0' + time.getMinutes();
		var seconds = time.getSeconds() > 9 ? time.getSeconds() : '0' + time.getSeconds();
		var dateStr = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
		return dateStr;
	}
})
