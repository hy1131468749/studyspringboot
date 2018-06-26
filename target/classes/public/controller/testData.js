app.controller('testDataCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
    window.clearInterval($rootScope.timeGet);
    //控制器的逻辑
    function spanLineHeight () {
    	$("#owellTable span").each(function () {
	    	if($(this).hasClass('spanBr')){
	    		$(this).css('padding-top',$(this).innerHeight()/2 - 18 + 'px');
	    	}else{
	    		$(this).css('padding-top',$(this).innerHeight()/2 - 9 + 'px');
	    	}
	    })
    }
    $(window).resize(function () {
    	setTimeout(function () {
    		spanLineHeight ();
    	},100);
    })
    spanLineHeight ();
    
    
    //地图初始化
    $scope.wellDevlist = [];
    function getWellDev () {
    	$.ajax({
	    	type:"get",
	    	url:$rootScope.httpUrl + "device/testData",
	    	async:true,
	    	success:function (resData) {
	    		if(resData.ttList.length){
//	    			if(resData.ttList.length == $scope.wellDevlist.length){
//	    				
//	    			}else{
		    			$scope.wellDevlist = resData.ttList;
		    			$scope.wellDevlist = $scope.wellDevlist.reverse();
			    		$scope.$digest();
//	    			}
	    		}
	    	}
	    });
    }
    getWellDev ();
    $rootScope.timeGet = setInterval(function () {
    	getWellDev ();
    },10000)
    
}])