app.controller('dustbinCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
    window.clearInterval($rootScope.timeGet);
    //控制器的逻辑
    function spanLineHeight () {
    	$("#odustbinTable span").each(function () {
	    	$(this).css('line-height',$(this).innerHeight() + 'px');
	    })
    }
    $(window).resize(function () {
	    spanLineHeight ();
    })
    spanLineHeight ();
    
    mapServiceFun.showMap('dustbinMap');
    function getDustbinDev () {
    	$.ajax({
	    	type:"get",
	    	url:$rootScope.httpUrl + "device/garbageCanData ",
	    	async:true,
	    	success:function (resData) {
	    		if(resData.gcList.length){
	    			$scope.dustbinDevlist = resData.gcList;
		    		$scope.$digest();
		    		mapServiceFun.addPoint('device_03',resData.gcList,{lng:121.469616,lat:31.249267});
	    		}
	    	}
	    });
    }
    getDustbinDev ();
    $rootScope.timeGet = setInterval(function () {
    	getDustbinDev ();
    },30000);
    
}])