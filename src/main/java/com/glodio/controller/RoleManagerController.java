package com.glodio.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glodio.bean.Role;
import com.glodio.bean.Url;
import com.glodio.bean.User;
import com.glodio.service.IRoleService;
import com.glodio.service.UrlService;
import com.glodio.util.OldETreeNode;
import com.glodio.util.StringUtil;

@Controller
@RequestMapping("/system/rbac/role")
public class RoleManagerController {
	
	
	@Autowired
    HttpServletRequest request;
	
	@Resource
	private IRoleService iRoleService;
	
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
		User user = getUser(request);
		if(user == null){
			return resultMap;
		}
		PageHelper.startPage(pageNumber,  pageSize);
	    List<Role> list = iRoleService.selectListByOrgId(user.getOrgId());
	    long total = 0;
	    if(list!=null){
	    	total =  ((Page) list).getPages(); 
	    }
	    resultMap.put("total", total);
	    resultMap.put("list", list);
	    return resultMap;
	}
	
    /**
     * 根据parentId查询数据
     * @param parentId
     * @return
     */
    @ResponseBody
	@RequestMapping(value="/findZtreeList",method=RequestMethod.POST)
    public List<OldETreeNode> findZtreeList (Integer choiceId,@RequestParam(defaultValue="0",value="id") Integer parentId,HttpServletRequest request ){
    	Role role = null;
    	if(null != choiceId)
    		role =  iRoleService.selectById(choiceId);
    	List<String> urlIds = new ArrayList<>();
    	if(role !=null && !StringUtil.strIsNullOrEmpty(role.getUrlremark())){
    		Collections.addAll(urlIds, role.getUrlremark().split(";"));
    	}
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
    			if(urlIds.contains(url.getId()+"")){
    				oldETreeNode.setChecked(true);	
    			}
    			oldETreeNode.setpId(url.getParentId());
    			list.add(oldETreeNode);
    		}
    	}
    	
    	return list;
    }
    
    @ResponseBody
	@RequestMapping(value="/saveRole",method=RequestMethod.POST)
    public Map<String,Object> saveRole (Role bean){
    	 Map<String,Object> resultMap = new HashMap<>();
    	 boolean result = false;
    	 //和组织进行关联
    	 resultMap.put("result", result);
    	 User user = getUser(request);
    	 if(user == null){
    		 return resultMap;
    	 }
    	 
    	 bean.setOrgId(user.getOrgId());
    	 bean.setCjsj(new Date());
    	 bean.setXgsj(new Date());
    	 if(iRoleService.addBean(bean) > 0){
    		 result = true;
    	 }
    	 resultMap.put("result", result);
    	 return resultMap;
    }
    
    @ResponseBody
   	@RequestMapping(value="/editRole",method=RequestMethod.POST)
       public Map<String,Object> editRole (Role bean){
       	 Map<String,Object> resultMap = new HashMap<>();
       	 boolean result = false;
       	 bean.setXgsj(new Date());
       	 if(iRoleService.updateBean(bean) > 0){
       		 result = true;
       	 }
       	 resultMap.put("result", result);
       	 return resultMap;
       }
    
    
    @ResponseBody
	@RequestMapping(value="/deleteRole",method=RequestMethod.POST)
    public Map<String,Object> deleteRole (@RequestParam Integer id){
    	 Map<String,Object> resultMap = new HashMap<>();
    	 boolean result = false;
    	 int count = iRoleService.deleteById(id);
    	 if(count > 0){
    		 result = true;
    	 }
    	 resultMap.put("result", result);
    	 return resultMap;
    		 
    }
    
    @ResponseBody
   	@RequestMapping(value="/findDetail",method=RequestMethod.POST)
    public Map<String,Object> findDetail (@RequestParam Integer id){
   	     Map<String,Object> resultMap = new HashMap<>();
   	     Role role = iRoleService.selectById(id);
   	     resultMap.put("bean", role);
   	     return resultMap;
	     
    }
	
    private User getUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user;
	}
    
	@RequestMapping("/RoleSearch")
	public @ResponseBody Map<String, Object> RoleSearch(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		List<Role> list = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", 0);
		dataMap.put("list", list);
		User user = getUser(request);
		if(user == null){
			return dataMap;
		}
		
		list = iRoleService.selectListByOrgId(user.getOrgId());
		if(list.size() > 0) {
			dataMap.put("total", list.size());
			dataMap.put("list", list);
		}
		else {
			
		}

		return dataMap;
	}
   
    
}
