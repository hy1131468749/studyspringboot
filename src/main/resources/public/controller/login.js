app.controller('loginCtl',['$scope','$state','$rootScope',function($scope,$state,$rootScope){
	$scope.subMit = function(){
		if($scope.passward && $scope.userName){
			$.ajax({
				type:"post",
				url:$rootScope.httpUrl + "login",
				async:true,
				data:{
					"username":$scope.userName,
					"password":$scope.passward
				},
				success:function (resData) {
					if(resData.result){
						localStorage.userName = $scope.userName;
						$state.go('main');
					}else{
						$("#errorModal").modal('show');
					}
				}
			});
		} else {
			$("#valNullModal").modal('show');
		}
	}
	$("#formSumit").find('input').keydown(function() {
		if(event.keyCode == "13") {
			$scope.subMit();
		}
	});
}]);