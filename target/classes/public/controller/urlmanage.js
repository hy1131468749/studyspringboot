app.controller('urlmanageCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	 function initTree(){
		   //初始化树
		   var zTreeObj;
		   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		   var setting = {
				   callback: {
						onClick: zTreeOnClick   
		           },async: {
		       		enable: true,
		    		url: $rootScope.httpUrl + "rbac/url/findZtreeList",
		    		autoParam: ["id"]
		    	}
		           
		   };
		   function zTreeOnClick(event, treeId, treeNode) {
			    $scope.connectPlatform = treeNode.id;
			    $scope.connectPlatformName = treeNode.name;
			    $("#parentId").val(treeNode.id);
			    $("#parentName").val(treeNode.name);
			    $('#ztreeDialog').modal('hide')
			    $scope.$digest();
			    var parentNode = treeNode.getParentNode();
			    $scope.typeId = parentNode.id;
			};
		   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
		 
		   zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);
		 }
	initTree();
	$scope.choiceParentOrg = function(){
		
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
			url:$rootScope.httpUrl + "rbac/url/findList",
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
		if($scope.requsetType == 0){
			url = $rootScope.httpUrl + "rbac/url/saveUrl";
			message = "添加";
		}else if($scope.requsetType == 1){
			url = $rootScope.httpUrl + "rbac/url/editUrl";
			message = "更新";
			data = data+"&id="+$scope.id;
		}
		
		if(!$scope.urlName || !$scope.urlAddr || !$scope.iconName){
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
					initTree();
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
	$scope.addTitle = '权限新增';
	$scope.showAE = function (requsetType,id) {
		$.each($('#addform').find('input'),function(i, ele){
          
			if($(ele).attr('type')=='text' || $(ele).attr('type')=='hidden'){
				$scope[ele.id] = "";
             };
         });
		$.each($('#addform').find('textarea'),function(i, ele){
            	 $scope[ele.id] = "";
         });
		
		$("#parentName").val("");
		$("#parentId").val("");
		$scope.requsetType = requsetType;
		if(requsetType == 0){
			$scope.addTitle = '权限新增';
			$("#parentIdDiv").show();
		}else if(requsetType == 1){
			$("#parentIdDiv").hide();
			$scope.id = id;
			$scope.addTitle = '权限修改';
			var bean ;
			$.ajax({
				type:"post",
				data: {id:$scope.id},
				url:$rootScope.httpUrl + "rbac/url/findDetail",
				async:true,
				success:function (resData) {
					bean = resData.bean;
					angular.forEach(bean, function (value,key){
						if($("#"+key).length > 0){
						   $scope[key] = value;
					   }
		            });
					$scope.$digest();
					
				}
			});

		}
	}
	
	
	//组织机构名称
	$scope.urlName = '';
	$scope.$watch('urlName', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isUrlName = true;
		} else {
			$scope.isUrlName = false;
		}
	});
	
	$scope.urlAddr = '';
	$scope.$watch('urlAddr', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isUrlAddr = true;
		} else {
			$scope.isUrlAddr = false;
		}
	});
	
	$scope.iconName = '';
	$scope.$watch('iconName', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isIconName = true;
		} else {
			$scope.isIconName = false;
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
				url:$rootScope.httpUrl + "rbac/url/deleteUrl ",
				async:true,
				data:{id:$scope.removeId},
				success:function (resData) {
					if(resData.result){
						$("#alertModal .modal-body").text('删除成功');
						getCarList();
						initTree();
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

