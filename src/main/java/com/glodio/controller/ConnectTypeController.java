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
import com.glodio.bean.ConnectType;
import com.glodio.service.ConnectTypeService;

@Controller
@RequestMapping("/system/connectType")
public class ConnectTypeController {
	
    @Autowired
	private ConnectTypeService connectTypeService;
    
	
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
		List<ConnectType> list = connectTypeService.selectList();
		long total =  ((Page) list).getPages();
		resultMap.put("total", total);
		resultMap.put("list", list);
		return resultMap;
	}
	
    
    /**
     * 列表查询所有数据
     * @return
     */
	@ResponseBody
	@RequestMapping(value="/selectAllList",method=RequestMethod.POST)
	public  Map<String, Object> selectAllList(){
		Map<String,Object> resultMap = new HashMap<>();
		List<ConnectType> list = connectTypeService.selectList();
		resultMap.put("list", list);
		return resultMap;
	}
	
    
	/**
	 * 添加新的平台
	 * @param connectPlatform
	 * @return
	 */
	@RequestMapping(value="/addConnectType",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> addConnectPlatform(ConnectType connectType){
	    Map<String,Object> resultMap = new HashMap<>();
		boolean result = connectTypeService.insertSelective(connectType);
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * 根据id删除对接平台
	 * @param id
	 * @return
	 */
    @RequestMapping(value="/delConnectType",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delConnectPlatform(@RequestParam Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = connectTypeService.deleteByPrimaryKey(id);
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
	public Map<String,Object> findDetail(@RequestParam Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        ConnectType connectType = connectTypeService.selectByPrimaryKey(id);
        resultMap.put("bean", connectType);
        return resultMap;
    }
    
    
    /**
	 * 更新平台信息
	 * @param connectPlatform
	 * @return
	 */
	@RequestMapping(value="/editConnectType",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> editConnectPlatform(ConnectType connectType){
	    Map<String,Object> resultMap = new HashMap<>();
		boolean result = connectTypeService.updateByPrimaryKeySelective(connectType);
		resultMap.put("result", result);
		return resultMap;
	}
    
    
    
}
