package com.glodio.controller;

import java.util.ArrayList;
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
import com.glodio.bean.Url;
import com.glodio.service.UrlService;
import com.glodio.util.OldETreeNode;

@Controller
@RequestMapping("/system/rbac/url")
public class UrlManagerController {
	
	@Autowired
	private UrlService urlService;
	
	 /**
     * 列表查询数据
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/findList",method=RequestMethod.POST)
	public  Map<String, Object> findList(@RequestParam(defaultValue="1") Integer pageNumber,
			@RequestParam(defaultValue="10") Integer pageSize){
		Map<String,Object> resultMap = new HashMap<>();
		PageHelper.startPage(pageNumber,  pageSize);
	    List<Url> list = urlService.selectList();
	    long total = 0;
	    if(list!=null){
	    	total =  ((Page) list).getPages(); 
	    }
	    resultMap.put("total", total);
	    resultMap.put("list", list);
	    return resultMap;
	}
	
    
    @ResponseBody
	@RequestMapping(value="/saveUrl",method=RequestMethod.POST)
    public Map<String,Object> saveUrl(Url url) throws Exception {
    	Map<String,Object> resultMap = new HashMap<>();
        boolean result = urlService.insertSelective(url);
    	resultMap.put("result", result);
    	return resultMap;
    }
    
    
    /**
     * 根据parentId查询数据
     * @param parentId
     * @return
     */
    @ResponseBody
	@RequestMapping(value="/findZtreeList",method=RequestMethod.POST)
    public List<OldETreeNode> findZtreeList (@RequestParam(defaultValue="0",value="id") Integer parentId){
    	List<OldETreeNode> list = new ArrayList<>();
    	List<Url> urlList = urlService.selectListByparentId(parentId);
    	if(urlList != null && !urlList.isEmpty()){
    		OldETreeNode oldETreeNode = null;
    		for(Url url : urlList ){
    			oldETreeNode = new OldETreeNode();
    			oldETreeNode.setOpen(false);
    			oldETreeNode.setName(url.getUrlName());
    			oldETreeNode.setId(url.getId());
    			if(1 == url.getIsCatalog()){
    				oldETreeNode.setIsParent(true);
    			}
    			oldETreeNode.setpId(url.getParentId());
    			list.add(oldETreeNode);
    		}
    	}
    	
    	return list;
    }
    
    @ResponseBody
   	@RequestMapping(value="/findDetail",method=RequestMethod.POST)
    public Map<String,Object> findDetail(@RequestParam Integer id){
    	Map<String,Object> resultMap = new HashMap<>();
    	Url url = urlService.selectByPrimaryKey(id);
    	resultMap.put("bean", url);
    	return resultMap;
    }
    
    @ResponseBody
   	@RequestMapping(value="/editUrl",method=RequestMethod.POST)
    public Map<String,Object> editUrl(Url url){
    	Map<String,Object> resultMap = new HashMap<>();
    	boolean result = urlService.updateByPrimaryKeySelective(url);
    	resultMap.put("result", result);
    	return resultMap;
    }
    
    @ResponseBody
   	@RequestMapping(value="/deleteUrl",method=RequestMethod.POST)
    public Map<String,Object> deleteUrl(@RequestParam Integer id){
    	Map<String,Object> resultMap = new HashMap<>();
    	boolean result = urlService.deleteByPrimaryKey(id);
    	resultMap.put("result", result);
    	return resultMap;
    }
    
   
}
