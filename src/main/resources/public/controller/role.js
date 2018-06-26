app.controller('roleCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	var choiceId = ""; 
	function initTree(){
		   //初始化树
		   var zTreeObj;
		   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		   var setting = {
				   callback: {
						onCheck: zTreeOnCheck
		           },async: {
		       		enable: true,
		    		url: $rootScope.httpUrl + "rbac/role/findZtreeList?choiceId="+choiceId,
		    		autoParam: ["id"]
		    	   },check: {
		    			enable: true,
		    			autoCheckTrigger: true
		    		}
		           
		   };
		   function zTreeOnCheck(event, treeId, treeNode) {
			   
			   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			   var nodes = treeObj.getNodes();
			   var url = "";
			   var urlremark = "";
			   $.each(nodes,function(key,value){
				   if( value.checked ){
					   urlremark += value.id+";";
					   url  +=value.name+";";
				   }
			   });
			   $("#url").val(url);
			   $("#urlremark").val(urlremark);
			  
			   $scope.url = url;
			   $scope.urlremark = urlremark;
			   $scope.$digest();
			  
			};
		   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
		 
		   zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);
		 }
	initTree();
	$scope.choiceRole = function(){
		
	    $('#ztreeDialog').modal('show')

	}
	
	//分页车辆获取
	$scope.pageNumber = 1;
	$scope.total = 1;
	$scope.dataList = [];
	function getCarList () {
		$scope.pageNumber = $scope.pageNumber > $scope.total ?  $scope.total : $scope.pageNumber;
		$scope.pageNumber = $scope.pageNumber === 0 ? 1 : $scope.pageNumber;
		var data = {
			pageNumber:$scope.pageNumber,
			pageSize:10
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "rbac/role/findList",
			async:true,
			data:data,
			success:function (resData) {
				if(resData.list){
					$scope.dataList = resData.list;
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
		var data = $("#addform").serialize();
		data += "&url="+$("#url").val();
		if($scope.requsetType == 0){
			url = $rootScope.httpUrl + "rbac/role/saveRole";
			message = "添加";
		}else if($scope.requsetType == 1){
			url = $rootScope.httpUrl + "rbac/role/editRole";
			message = "更新";
			data = data+"&id="+$scope.id;
		}
		
		if(!$scope.url || !$scope.rolename){
			return;
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
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.refresh();
				}else{
					$("#alertModal .modal-body").text(message+"失败");
				}
				$("#alertModal").modal('show');
			}
		});
		$('#addModal_d').modal('hide')
		
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
	$scope.addTitle = '角色新增';
	$scope.showAE = function (requsetType,id) {
		choiceId = "";
		$.each($('#addform').find('input'),function(i, ele){
          
			if($(ele).attr('type')=='text'){
            	 $scope[ele.id] = "";
             };
         });
	
		$scope.requsetType = requsetType;
		if(requsetType == 0){
			$scope.addTitle = '角色新增';
			
		}else if(requsetType == 1){
			
			$scope.id = id;
			$scope.addTitle = '角色修改';
			choiceId = id;
			initTree();
			
			var bean ;
			$.ajax({
				type:"post",
				data: {id:$scope.id},
				url:$rootScope.httpUrl + "rbac/role/findDetail",
				async:true,
				success:function (resData) {
					bean = resData.bean;
					angular.forEach(bean, function (value,key){
						if($("#"+key).length > 0){
						   $scope[key] = value;
					   }
		            });
					$("#urlremark").val(bean.urlremark);
					$scope.$digest();
				}
			});

		}
	}
	
	
	
	$scope.url = '';
	$scope.$watch('url', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isUrl = true;
		} else {
			$scope.isUrl = false;
		}
	});
	
	$scope.rolename = '';
	$scope.$watch('rolename', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isRolename = true;
		} else {
			$scope.isRolename = false;
		}
	});
	
	
	
	//删除模块
	$scope.showDelete = function (id) {
		$scope.removeId = id;
	}
	
	//删除
	$scope.remove = function () {
	
		if($scope.removeId){
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "rbac/role/deleteRole ",
				async:true,
				data:{id:$scope.removeId},
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

