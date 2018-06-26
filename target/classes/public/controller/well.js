app.controller('wellCtl',['$scope','$rootScope','$http','mapServiceFun',function($scope,$rootScope,$http,mapServiceFun){
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
    mapServiceFun.showMap('wellMap');
    function getWellDev () {
    	$.ajax({
	    	type:"get",
	    	url:$rootScope.httpUrl + "device/manholeCoverData",
	    	async:true,
	    	success:function (resData) {
	    		if(resData.mcList.length){
	    			$scope.wellDevlist = resData.mcList;
		    		$scope.$digest();
		    		mapServiceFun.addPoint('device_01',resData.mcList,{lng:121.469616,lat:31.249267});
	    		}
	    	}
	    });
    }
    getWellDev ();
    $rootScope.timeGet = setInterval(function () {
    	getWellDev ();
    },30000)
    
}])