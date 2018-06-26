package com.glodio.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glodio.bean.ConnectPlatform;
import com.glodio.service.IConnectPlatformService;


@Controller
@RequestMapping("/system/connectPlatform")
public class ConnectPlatformController {
	
    @Autowired
	private IConnectPlatformService connectPlatformService;
	
    /**
     * 列表查询数据
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/selectList",method=RequestMethod.POST)
	public  Map<String, Object> selectList(@RequestParam(defaultValue="1") Integer pageNumber,
			@RequestParam(defaultValue="10") Integer pageSize){
		Map<String,Object> resultMap = new HashMap<>();
		PageHelper.startPage(pageNumber,  pageSize);
		List<ConnectPlatform> list = connectPlatformService.selectList();
		long total =  ((Page) list).getPages();
		resultMap.put("total", total);
		resultMap.put("cpList", list);
		return resultMap;
	}
	
    
    
    
	/**
	 * 添加新的平台
	 * @param connectPlatform
	 * @return
	 */
	@RequestMapping(value="/addConnectPlatform",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> addConnectPlatform(ConnectPlatform connectPlatform){
	    Map<String,Object> resultMap = new HashMap<>();
		boolean result = connectPlatformService.insertSelective(connectPlatform);
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * 根据id删除对接平台
	 * @param id
	 * @return
	 */
    @RequestMapping(value="/delConnectPlatform",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delConnectPlatform(@RequestParam Long id){
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = connectPlatformService.deleteByPrimaryKey(id);
        resultMap.put("result", result);
        return resultMap;
    }
	
    /**
     * 根据id查询详细信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findDetail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findDetail(@RequestParam Long id){
        Map<String,Object> resultMap = new HashMap<>();
        ConnectPlatform connectPlatform = connectPlatformService.selectByPrimaryKey(id);
        resultMap.put("bean", connectPlatform);
        return resultMap;
    }
    
    
    /**
	 * 更新平台信息
	 * @param connectPlatform
	 * @return
	 */
	@RequestMapping(value="/editConnectPlatform",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> editConnectPlatform(ConnectPlatform connectPlatform){
	    Map<String,Object> resultMap = new HashMap<>();
		boolean result = connectPlatformService.updateByPrimaryKeySelective(connectPlatform);
		resultMap.put("result", result);
		return resultMap;
	}
    
    
    
}
