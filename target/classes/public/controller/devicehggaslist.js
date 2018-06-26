app.controller('devicehggaslistCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
	//时间控件初始化
	 $("#startTime").datetimepicker({
	        format: 'yyyy-mm-dd hh:ii'
	    });
	 $("#endTime").datetimepicker({
	        format: 'yyyy-mm-dd hh:ii'
	    });
	
	
	$scope.pageNumber = 1;
	$scope.total = 1;
	$scope.dataList = [];
	function getCarList () {
		$scope.pageNumber = $scope.pageNumber > $scope.total ?  $scope.total : $scope.pageNumber;
		$scope.pageNumber = $scope.pageNumber === 0 ? 1 : $scope.pageNumber;
		var data = {
			pageNumber:$scope.pageNumber,
			pageSize:10,
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val(),
			alarmStatus:$("#alarmStatus").val(),
		}
		$.ajax({
			type:"post",
			url:$rootScope.httpUrl + "deviceHggasController/findList",
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
	$scope.queryList = function (){
		$scope.pageNumber = 1;
		getCarList ();
	}
	
	
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
	

}])

