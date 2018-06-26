app.controller('userCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	  function initTree(){
		   //初始化树
		   var zTreeObj;
		   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		   var setting = {
				   callback: {
						onClick: zTreeOnClick   
		           }
		   };
		   function zTreeOnClick(event, treeId, treeNode) {
			    $scope.orgId = treeNode.realId;
			    $scope.orgName = treeNode.name;
			    $("#orgId").val(treeNode.id);
			    $('#myModal').modal('hide')
			    $scope.$digest();
			};
		   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
		  
		   
		   $.ajax({
				type:"post",
				url:$rootScope.httpUrl + "rbac/org/Ztree",
				async:true,
				data:{},
				success:function (resData) {
					 zTreeObj = $.fn.zTree.init($("#treeDemo"), setting,resData);
				}
			});
		   
		 }
	  initTree();
	
	//初始化角色下拉选框
	$.ajax({
		type:"post",
		url:$rootScope.httpUrl + "rbac/role/RoleSearch",
		async:true,
		data:{name:''},
		success:function (resData) {
			if(resData.list){
				var list = resData.list;
				$.each(list,function(key,bean){
					if(key == 0){
					  $("#roleId").append("<option selected='selected' value="+bean.id+">"+bean.rolename+"</option>");
					}else{
					  $("#roleId").append("<option  value="+bean.id+">"+bean.rolename+"</option>");
					}
					
				});	
			}
		}
	});
      
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
			url:$rootScope.httpUrl + "rbac/user/UserList",
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
		var roleName = $("#roleId").find("option:selected").text();
		if($scope.requsetType == 0){
			url = $rootScope.httpUrl + "rbac/user/UserSave";
			message = "添加";
			data = data+"&iUsreFlag=0"+"&rname="+roleName+"&oldName=";
		}else if($scope.requsetType == 1){
			url = $rootScope.httpUrl + "rbac/user/UserSave";
			message = "更新";
			data = data+"&id="+$scope.id+"&iUsreFlag=1"+"&oldName="+$scope.oldName+"&rname="+roleName;
		}
		
		if(!$scope.username || !$scope.password ||!$scope.orgId ){
			return;
		}
		$.ajax({
			type:"post",
			data:data,
			url:url,
			async:true,
			success:function (resData) {
				if(resData.result){
					$("#alertModal .modal-body").text(message+"成功！");
					getCarList();
				}else{
					var msg = "";
					if(resData.msg){
						msg = resData.msg;
					}
					$("#alertModal .modal-body").text(message+"失败！"+msg);
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
	$scope.addTitle = '用户新增';
	$scope.showAE = function (requsetType,id) {
		$.each($('#addform').find('input'),function(i, ele){
          
			if($(ele).attr('type')=='text'){
            	 $scope[ele.id] = "";
             };
         });
		$("#roleId").val("");
		$scope.oldName = "";
		$scope.requsetType = requsetType;
		if(requsetType == 0){
			$scope.addTitle = '用户新增';
			$("#orgButton").show();
			$("#roleId").removeAttr("disabled"); 

		}else if(requsetType == 1){
			$("#orgButton").hide();
			$("#roleId").attr("disabled","disabled");    
			$scope.id = id;
			$scope.addTitle = '用户修改';
			var bean ;
			$.ajax({
				type:"post",
				data: {id:$scope.id},
				url:$rootScope.httpUrl + "rbac/user/findDetail",
				async:true,
				success:function (resData) {
					bean = resData.bean;
					angular.forEach(bean, function (value,key){
						if($("#"+key).length > 0){
						   $scope[key] = value;
					   }
		            });
					var rolename = bean.rolename;
					var roleValue = "";
					var count=$("#roleId").get(0).options.length;  
				    for(var i=0;i<count;i++){  
				        if($("#roleId").get(0).options[i].text == rolename)    
				        {  
				            roleValue = $("#roleId").get(0).options[i].value;
				            break;    
				        }    
				    }
				    $scope.roleId =roleValue;
					$scope.oldName = bean.username;
					$scope.$digest(bean);
					$("#roleId").val(roleValue);
					//解决第一个出现自己添加的
				    
					//$("#roleId option:first").remove();
					
				}
			});

		}
	}
	
	$scope.choiceParentOrg = function(){
		   $('#myModal').modal('show')

	}
	
	$scope.username = '';
	$scope.$watch('username', function(newValue, oldValue) {
		
		if(!newValue) {
			$scope.isUsername = true;
		} else {
			$scope.isUsername = false;
		}
	});

	$scope.password = '';
	$scope.$watch('password', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isPassword = true;
		} else {
			$scope.isPassword = false;
		}
	});
	
	$scope.orgName = '';
	$scope.$watch('orgName', function(newValue, oldValue) {
		if(!newValue) {
			$scope.isOrgName = true;
		} else {
			$scope.isOrgName = false;
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
				url:$rootScope.httpUrl + "rbac/user/UserRemove",
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

