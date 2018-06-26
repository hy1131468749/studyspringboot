app.controller('connectPlatformCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	//初始化角色下拉选框
	$.ajax({
		type:"post",
		url:$rootScope.httpUrl + "connectType/selectAllList",
		async:true,
		data:{},
		success:function (resData) {
			if(resData.list){
				var list = resData.list;
				$.each(list,function(key,bean){
				    $("#typeId").append("<option  value="+bean.id+">"+bean.typeName+"</option>");
				});	
			}
		}
	});
	//select 改变事件
	function showCorrectInf(dateValue,type){
	    for(var i=1;i<6;i++){
		    $("#typediv"+i).hide();
		         if(type == 0){
		             $.each($('#typediv'+i).find('input'),function(i, ele){
					    if($(ele).attr('type')=='text'){
		            	     $scope[ele.id] = "";
		                };
		             });
		         }
		   }
		   if($("#typediv"+dateValue).length > 0){
		       $("#typediv"+dateValue).show();
		   }
	}
	$scope.selectTypeIdChange = function(dateValue){
		showCorrectInf(dateValue,0);
	   
	   
	}
	//分页车辆获取
	$scope.pageNumber = 1;
	$scope.total = 1;
	$scope.cpList = [];
	function getCarList () {
		$scope.pageNumber = $scope.pageNumber > $scope.total ?  $scope.total : $scope.pageNumber;
		$scope.pageNumber = $scope.pageNumber === 0 ? 1 : $scope.pageNumber;
		var data = {
			pageNumber:$scope.pageNumber,
			pageSize:10
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "connectPlatform/selectList",
			async:true,
			data:data,
			success:function (resData) {
				if(resData.cpList){
					$scope.cpList = resData.cpList;
					$scope.$digest();
					$scope.total = resData.total;
					$("#total").html(resData.total);
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
		if($(this).val() > $scope.total){
			$(this).val($scope.total);
		}
		
	}
    //上一页下一页
	$scope.gotoPage = function (){
		if($scope.pageNumber < 1){
			$scope.pageNumber = 1;
			return;
		}
		if($scope.pageNumber > $scope.total){
			$scope.pageNumber = $scope.total;
			return;
		}
		getCarList ();
	}
	$scope.prevPage = function (){
		--$scope.pageNumber;
		if($scope.pageNumber < 1){
			$scope.pageNumber = 1;
			return;
		}
		getCarList ();
	}
	$scope.nextPage = function (){
		++$scope.pageNumber;
		if($scope.pageNumber > $scope.total){
			$scope.pageNumber = $scope.total;
			return;
		}
		getCarList ();
	}
	//编辑和删除
	$scope.add = function () {
		var url = "";
		var message = "";
		var data = $("#addConnectPlatform").serialize();
		if($scope.requsetType == 0){
			url = $rootScope.httpUrl + "connectPlatform/addConnectPlatform";
			message = "添加";
		}else if($scope.requsetType == 1){
			url = $rootScope.httpUrl + "connectPlatform/editConnectPlatform";
			message = "更新";
			data = data+"&id="+$scope.id;
		}
		if(!$scope.name || !$scope.typeId || !$scope.http || !$scope.ip || !$scope.port){
			return;
		}
		var typeId = $("#typeId").val();
	
		switch (typeId) {
		case '1':
			if(!$scope.name || !$scope.nbAppid || !$scope.nbSecret){
				return;
			}
			break;

		case '2':
			if(!$scope.udpWebsocketPort ){
				return;
			}
			break;
			
		case '3':
			if(!$scope.nokiaUserName || !$scope.nokiaPassword){
				
				return;
			}
			break;
		case '4':
			if(!$scope.onenetAppkey || !$scope.onenetToken){
				
				return;
			}
		case '5':
			if(!$scope.smokeDetectorPartnerid || !$scope.smokeDetectorPartnerKey){
				
				return;
			}	
			break;	
		default: 
			return;
		    break;
		}
		
		$.ajax({
			type:"post",
			data:data,
			url:url,
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text(message+"成功");
					getCarList();
				}else{
					$("#alertModal .modal-body").text(message+"失败");
				}
				$("#alertModal").modal('show');
			}
		});
		$('#addModal_d').modal('hide');
		
	}
	
	
	/*//编辑和删除
	$scope.edit = function () {
		if(!$scope.nbName || !$scope.nbHttp || !$scope.nbIp || !$scope.nbPort ||
				!$scope.nbAppid || !$scope.nbSecret){
			return;
		}
		$.ajax({
			type:"post",
			data: $("#addConnectPlatform").serialize(),
			url:$rootScope.httpUrl + "connectPlatform/findDetail",
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text('修改成功');
					getCarList();
				}else{
					$("#alertModal .modal-body").text('修改失败');
				}
				$("#alertModal").modal('show');
			}
		});
		$('#addModal_d').modal('hide');
		
	}*/
	
	
	//获取用户，或者打开添加
	$scope.addTitle = '对接平台新增';
	$scope.showUser = function (requsetType,id) {
		$.each($('#addConnectPlatform').find('input'),function(i, ele){
          
			if($(ele).attr('type')=='text'){
            	 $scope[ele.id] = "";
             };
         });
		$scope.typeId = '';
		showCorrectInf($scope.typeId,0);
		$scope.requsetType = requsetType;
		if(requsetType == 0){
			$scope.addTitle = '对接平台新增';
		}else if(requsetType == 1){
			$scope.id = id;
			$scope.addTitle = '对接平台修改';
			var bean ;
			$.ajax({
				type:"post",
				data: {id:$scope.id},
				url:$rootScope.httpUrl + "connectPlatform/findDetail",
				async:true,
				success:function (resData) {
					bean = resData.bean;
					angular.forEach(bean, function (value,key){
						if($("#"+key).length > 0){
						   $scope[key] = value;
					   }
		            });
					$scope.typeId = bean.typeId;
					
					$scope.$digest();
					
					$("#typeId").val(bean.typeId);
					//解决第一个出现自己添加的
					//$("#typeId option:first").remove();
					showCorrectInf(bean.typeId,1);
			
				}
			});

		}
	}
	
	
	//对接平台类型
	$scope.typeId = '';
	$scope.$watch('typeId', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isTypeId = true;
		} else {
			$scope.isTypeId = false;
		}
	});
	
	$scope.name = '';
	$scope.$watch('name', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isName = true;
		} else {
			$scope.isName = false;
		}
	});
	//web应用协议
	$scope.http = '';
	$scope.$watch('http', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isHttp = true;
		} else {
			$scope.isHttp = false;
		}
	});
	//应用服务器地址
	$scope.ip = '';
	$scope.$watch('ip', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isIp = true;
		} else {
			$scope.isIp = false;
		}
	});
	//应用服务器端口
	$scope.port = '';
	$scope.$watch('port', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isPort = true;
		} else {
			if(isNaN(newValue)){
				$scope.isPort = true;
			}else {
				$scope.isPort = false;
			}
		}
	});
	//应用appid验证
	$scope.nbAppid = '';
	$scope.$watch('nbAppid', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isNbAppid = true;
		} else {
			$scope.isNbAppid = false;
		}
	});
	//应用secret验证
	$scope.nbSecret = '';
	$scope.$watch('nbSecret', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isNbSecret = true;
		} else {
			$scope.isNbSecret = false;
		}
	});	
	
	$scope.udpTcpIp = '';
	$scope.$watch('udpTcpIp', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isUdpTcpIp = true;
		} else {
			$scope.isUdpTcpIp = false;
		}
	});
	
	$scope.udpSocketPort = '';
	$scope.$watch('udpSocketPort', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isUdpSocketPort = true;
		} else {
			$scope.isUdpSocketPort = false;
		}
	});
	
	$scope.udpWebsocketPort = '';
	$scope.$watch('udpWebsocketPort', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isUdpWebsocketPort = true;
		} else {
			$scope.isUdpWebsocketPort = false;
		}
	});
	
	$scope.nokiaUserName = '';
	$scope.$watch('nokiaUserName', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isNokiaUserName = true;
		} else {
			$scope.isNokiaUserName = false;
		}
	});
	
	$scope.nokiaPassword = '';
	$scope.$watch('nokiaPassword', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isNokiaPassword = true;
		} else {
			$scope.isNokiaPassword = false;
		}
	});
	
	$scope.onenetAppkey = '';
	$scope.$watch('onenetAppkey', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isOnenetAppkey = true;
		} else {
			$scope.isOnenetAppkey = false;
		}
	});
	
	$scope.onenetToken = '';
	$scope.$watch('onenetToken', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isOnenetToken = true;
		} else {
			$scope.isOnenetToken = false;
		}
	});
	
	
	$scope.smokeDetectorPartnerid = '';
	$scope.$watch('smokeDetectorPartnerid', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isSmokeDetectorPartnerid = true;
		} else {
			$scope.isSmokeDetectorPartnerid = false;
		}
	});
	
	$scope.smokeDetectorPartnerKey = '';
	$scope.$watch('smokeDetectorPartnerKey', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isSmokeDetectorPartnerKey = true;
		} else {
			$scope.isSmokeDetectorPartnerKey = false;
		}
	});
	
	//删除模块
	$scope.isCpId = function (id) {
		$scope.removeConnectPlatformId = id;
	}
	
	//删除
	$scope.removeConnectPlatform = function () {
	
		if($scope.removeConnectPlatformId){
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "connectPlatform/delConnectPlatform ",
				async:true,
				data:{id:$scope.removeConnectPlatformId},
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

}])

