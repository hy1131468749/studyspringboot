app.controller('devicesCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	
	 function initTree(){
		   //初始化树
		   var zTreeObj;
		   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		   var setting = {
				   callback: {
						onClick: zTreeOnClick   
		           },async: {
		       		enable: true,
		    		url: $rootScope.httpUrl + "station/qyeryTreeList",
		    		autoParam: ["id"]
		    	}
		           
		   };
		   function zTreeOnClick(event, treeId, treeNode) {
			    if(!treeNode.isParent){
			        $scope.connectPlatform = treeNode.id;
			        $scope.connectPlatformName = treeNode.name;
			        $("#connectPlatform").val(treeNode.id);
			        $('#connectPlatformDialog').modal('hide')
			        $scope.$digest();
			    }
			    var parentNode = treeNode.getParentNode();
			    $scope.typeId = parentNode.id;
			};
		   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
		 
		   zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);
		 }
	initTree();
	$scope.choiceConnnectPlatform = function(){
	    $('#connectPlatformDialog').modal('show')

	}
	//分页车辆获取
	$scope.userNum = 1;
	$scope.carTotal = 1;
	$scope.devList = [];
	function getCarList () {
		$scope.userNum = $scope.userNum > $scope.carTotal ?  $scope.carTotal : $scope.userNum;
		$scope.userNum = $scope.userNum === 0 ? 1 : $scope.userNum;
		var data = {
			pageNumber:$scope.userNum,
			pageSize:10
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "/station/deviceList",
			async:true,
			data:data,
			success:function (resData) {
				if(resData.deviceList){
					$scope.devList = resData.deviceList;
					$scope.$digest();
					$scope.carTotal = resData.total;
					$("#carTotal").html(resData.total);
				}
			}
		});
	}
	getCarList ();
	//页码输入框的范围
	$("#inputPage")[0].oninput = function () {
		if(isNaN($(this).val())){
			$(this).val(0);
		}
		if($(this).val() < 0){
			$(this).val(0);
		}else{
			$(this).val($(this).val()*1);
		}
		if($(this).val() > $scope.carTotal){
			$(this).val($scope.carTotal);
		}
		
	}
    //上一页下一页
	$scope.gotoPage = function (){
		if($scope.userNum < 1){
			$scope.userNum = 1;
			return;
		}
		if($scope.userNum > $scope.carTotal){
			$scope.userNum = $scope.carTotal;
			return;
		}
		getCarList ();
	}
	$scope.prevPage = function (){
		--$scope.userNum;
		if($scope.userNum < 1){
			$scope.userNum = 1;
			return;
		}
		getCarList ();
	}
	$scope.nextPage = function (){
		++$scope.userNum;
		if($scope.userNum > $scope.carTotal){
			$scope.userNum = $scope.carTotal;
			return;
		}
		getCarList ();
	}
	//编辑和删除
	$scope.addDev = function () {
		if(!$scope.deviceName || !$scope.devNumber || !$scope.addlat || !$scope.addlng||!$scope.connectPlatformName ){
			return;
		}
		$.ajax({
			type:"post",
			data:{
				deviceName:$scope.deviceName,
				deviceId:$scope.devNumber,
				lat:$scope.addlat,
				lng:$scope.addlng,
				deviceType:$scope.devType,
				connectPlatform:$scope.connectPlatform,
				remark:$scope.remake,
				typeId:$scope.typeId 
				
			},
			url:$rootScope.httpUrl + "/station/addDevice ",
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text('添加成功');
					getCarList();
				}else{
					$("#alertModal .modal-body").text('添加失败');
				}
				$("#alertModal").modal('show');
			}
		});
		$('#addModal_d').modal('hide');
		
	}
	//获取用户，或者打开添加
	$scope.addTitle = '设备新增';
	$scope.showUser = function (type) {
		$scope.deviceName = '';
		$scope.devNumber = '';
		$scope.addlat = '';
		$scope.addlng = '';
		$scope.remake = '';
		$scope.connectPlatform = '';
		$scope.connectPlatformName = '';
		if(type == 0){
			$scope.addTitle = '设备新增';
			return;
		}
		$scope.addTitle = '设备编辑';
	}
	
	//设备名验证
	$scope.deviceName = '';
	$scope.$watch('deviceName', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isName = true;
		} else {
			$scope.isName = false;
		}
	});
	//序列号验证
	$scope.devNumber = '';
	$scope.$watch('devNumber', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isNum = true;
		} else {
			$scope.isNum = false;
		}
	});
	//对接平台验证
	$scope.connectPlatformName = '';
	$scope.$watch('connectPlatformName', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isConnectPlatformName = true;
		} else {
			$scope.isConnectPlatformName = false;
		}
	});
	//设备类型验证
	$scope.devType = '0';
	$scope.$watch('devType', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isDevType = true;
		} else {
			$scope.isDevType = false;
		}
	});
	
	//添加设备地图处理
	function mapInit () {
		var mp = new BMap.Map("addDevMap");
		var point = new BMap.Point(121.185607,31.31642);
		mp.centerAndZoom(point, 11);
		mp.enableScrollWheelZoom();
		mp.enableInertialDragging();
		
		mp.enableContinuousZoom();
		
		var size = new BMap.Size(10, 20);
		mp.addControl(new BMap.CityListControl({
		    anchor: BMAP_ANCHOR_TOP_LEFT,
		    offset: size
		}));
		//地图搜索功能
		function G(id) {
			return document.getElementById(id);
		}
		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{
				"input" : "suggestId",
				"location" : mp
			}
		);
		ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
			
			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});
	
		var myValue;
		ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		var _value = e.item.value;
			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
			
			setPlace();
		});
	
		function setPlace(){
			mp.clearOverlays();    //清除地图上所有覆盖物
			function myFun(){
				var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
				mp.centerAndZoom(pp, 18);
				mp.addOverlay(new BMap.Marker(pp));    //添加标注
				$scope.addlng = pp.lng;
				$scope.addlat = pp.lat;
				$scope.$digest();
			}
			var local = new BMap.LocalSearch(mp, { //智能搜索
			  onSearchComplete: myFun
			});
			local.search(myValue);
		}
		//点击地图获取坐标
		mp.addEventListener("click",function(e){
			var pt = new BMap.Point(e.point.lng, e.point.lat);
			adMaker(pt)
			$scope.addlng = e.point.lng;
			$scope.addlat = e.point.lat;
			$scope.$digest();
		});
		function adMaker(pp) {
			mp.clearOverlays();
			mp.addOverlay(new BMap.Marker(pp));    //添加标注
		}
	}
	mapInit ();
	
	//删除模块
	$scope.isDevId = function (devId) {
		$scope.removeDevId = devId;
		
	}
	
	//删除
	$scope.removeDev = function () {
		if($scope.removeDevId){
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "/station/delDevice ",
				async:true,
				data:{deviceId:$scope.removeDevId},
				success:function (resData) {
					if(resData.result){
						$("#alertModal .modal-body").text('删除成功');
						getCarList();
					}else{
						$("#alertModal .modal-body").text('删除失败');
					}
					$("#alertModal").modal('show');
				}
			});
		}
		$('#removeModal').modal('hide');
	}
	//获取默认的设置参数
	$scope.getAttrHttp = function (i,devid) {
		$scope.devid = devid;
		if(i*1 === 0){
			//井盖
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "/station/getParamForMC",
				async:true,
				data:{deviceId:devid},
				success:function (resData) {
					if(resData){
						resData = JSON.parse(resData).DATAS;
						$scope.wellWarns = resData.status;
						$scope.hinge = resData.hinge;
						$scope.travelSwitch = resData.travel_switch;
						$scope.heartbeatTime = resData.beacon_interval*1;
						$scope.levelWarn = resData.level_interval*1;
						$scope.journey = resData.travel_interval*1;
						$scope.$digest();
					}
				}
			});
		}else if(i*1 === 1){
			//垃圾箱
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "/station/getParamForGC",
				async:true,
				data:{deviceId:devid},
				success:function (resData) {
					if(resData){
						resData = JSON.parse(resData);
						$scope.dustTime = new Date(resData.TS*1);
						$scope.$digest();
					}
				}
			});
		}else if(i*1 === 2){
			//环保箱
			
		}else{
			return;
		}
		
	}
	//点击设置参数事件
	$scope.showSetPara = function (i,devid) {
		//新增烟感处理
		if(i == 3){
			$scope.devid = devid;
			$("#smokeModule").modal('show');
			$.ajax({
				type:"post",
				data: {deviceId:devid},
				url:$rootScope.httpUrl + "deviceParamController/findDetail",
				async:true,
				success:function (resData) {
				
					bean = resData.bean;
					angular.forEach(bean, function (value,key){
						if($("#"+key).length > 0 ){			
							
							if( value == "" || value==null || value=="null" || value == 0){
								$scope[key] = "";
							}else{
							    $scope[key] = Number(value);
							}
					   }
		            });
					$scope.$digest();
				}
			});
			return;
		}
		//原来的处理方式
		var moduleIdArr = ['wellModule','dustbinModule','envModule'];
		if(i === 2){
			return;
		}
		moduleIdArr[i*1] && $("#" + moduleIdArr[i*1]).modal('show');
		$scope.getAttrHttp(i,devid);
	}
	
	//烟感
	$scope.smokeEdit = function(){
		var deviceId = $scope.devid;
		var data = $("#editSmokeForm").serialize();
		var url = $rootScope.httpUrl + "deviceParamController/saveAndUpdate";
		data = data+"&deviceId="+deviceId;
		$.ajax({
			type:"post",
			data:data,
			url:url,
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text("修改成功！");
					getCarList();
				}else{
					var msg = "";
					if(resData.msg){
						msg = resData.msg;
					}
					$("#alertModal .modal-body").text("修改失败！"+msg);
				}
				$("#alertModal").modal('show');
				$("#smokeModule").modal('hide');
			}
		});	
	}
	
	
	
		
	
	$scope.issue = function(deviceId){
		$scope.devid = deviceId;
		$("#issueSmokeModule").modal('show');
		$.ajax({
			type:"post",
			data: {deviceId:deviceId},
			url:$rootScope.httpUrl + "deviceParamController/findDetail",
			async:true,
			success:function (resData) {
			
				bean = resData.bean;
				angular.forEach(bean, function (value,key){
					
					if($("#"+key+"Issue").length > 0 && value!="" && value!=null){			
						
						if( value == "" || value==null || value=="null" || value == 0){
							$scope[key+"Issue"] = "";
						}else{
						    $scope[key+"Issue"] = Number(value);
						}
				   }
	            });
				$scope.$digest();
			}
		});	
	}
	
	//烟感下发指令
	$scope.smokeIssue = function(){
		var deviceId = $scope.devid;
		var url = $rootScope.httpUrl + "deviceParamController/issueCommand";
		$.ajax({
			type:"post",
			data:{deviceId:deviceId},
			url:url,
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text("发送成功！");
					getCarList();
				}else{
					var msg = "";
					if(resData.msg){
						msg = resData.msg;
					}
					$("#alertModal .modal-body").text("发送失败！"+msg);
				}
				$("#alertModal").modal('show');
				$("#issueSmokeModule").modal('hide');
			}
		});	
	}
	
	$scope.selfCheck =  function(deviceId){
		var url = $rootScope.httpUrl + "deviceParamController/selfCheck";
		$.ajax({
			type:"post",
			data:{deviceId:deviceId},
			url:url,
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text("发送成功！");
					getCarList();
				}else{
					var msg = "";
					if(resData.msg){
						msg = resData.msg;
					}
					$("#alertModal .modal-body").text("发送失败！"+msg);
				}
				$("#alertModal").modal('show');
				$("#issueSmokeModule").modal('hide');
			}
		});	
	}
	
	//井盖
	$scope.wellEnv = function () {
		var resData = {
			deviceId:$scope.devid,
			status:$scope.wellWarns,
			hinge:$scope.hinge,
			travel_switch:$scope.travelSwitch,
			beacon_interval:$scope.heartbeatTime,
			level_interval:$scope.levelWarn,
			travel_interval:$scope.journey
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "/station/setParamForMC",
			async:true,
			data:resData,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text('保存成功');
					getCarList();
				}else{
					$("#alertModal .modal-body").text('保存失败');
				}
				$("#alertModal").modal('show');
			}
		});
		$("#wellModule").modal('hide');
	}
	//垃圾箱
	$scope.dustEnv = function () {
		if(!$scope.dustTime){
			$("#alertModal .modal-body").text('时间格式不正确');
			$("#alertModal").modal('show');
			return;
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "/station/setParamForGC",
			async:true,
			data:{deviceId:$scope.devid,TS:$scope.dustTime.getTime()},
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text('保存成功');
					getCarList();
				}else{
					$("#alertModal .modal-body").text('保存失败');
				}
				$("#alertModal").modal('show');
			}
		});
		$("#dustbinModule").modal('hide');
	}
	//环保箱
	$scope.sumitEnv = function () {
		$("#envModule").modal('hide');
	}
	
	/**************设备下发数据验证*******************/
	//井盖心跳检测周期 0 - 24
	$scope.$watch('heartbeatTime', function(newValue, oldValue) {
		if(newValue > 24) {
			$scope.heartbeatTime = 24;
		} else if(!(newValue > 0)){
			$scope.heartbeatTime = 1;
		}
	});
	//液位报警恢复检测周期
	$scope.$watch('levelWarn', function(newValue, oldValue) {
		if(!(newValue > 0)) {
			$scope.levelWarn = 1;
		}
	});
	//行程开关报警恢复检测周期
	$scope.$watch('journey', function(newValue, oldValue) {
		if(!(newValue > 0)) {
			$scope.journey = 1;
		} 
	});
	$scope.hinge = '0';
	$scope.travelSwitch = '0';
	$scope.wellWarns = '0';
}])
.filter('filterDevType',function () {
	return function (val) {
		var devTypeArr = ['智能井盖','智能垃圾箱','智能环保箱','智能烟感监测'];
		return devTypeArr[val*1];
	}
})
.filter('filterPlatform',function () {
	return function (val) {
		var platformArr = ['UDP协议','华为NBIoT平台','','诺基亚NBIoT平台'];
		return platformArr[val*1];
	}
})