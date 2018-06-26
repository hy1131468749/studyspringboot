package com.glodio.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Org;
import com.glodio.bean.User;
import com.glodio.service.IOrgService;
import com.glodio.service.IRoleService;
import com.glodio.service.ISystemResourceService;
import com.glodio.util.CopyBean;
import com.glodio.util.OldETreeNode;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/system/rbac/org")
public class OrgController {
	

	@Resource
	IOrgService iOrgService;
	@Resource
	IRoleService IRoleService;
	@Resource
	ISystemResourceService iSystemResourceService;
	
	@RequestMapping("/OrgList")
	public @ResponseBody Map<String, Object> OrgList(HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Org> list = null;
		int total = 0;
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		if(pageNumber > 0) {
			pageNumber = (pageNumber-1)*pageSize;
		}
		else {
			pageNumber = 0;
		}
		
		map.put("pageNumber", pageNumber);
		map.put("pageSize", pageSize);
		
		// 获取当前用户bean,取得用户所在机构，根据所在机构获取用户所有机构权限,再根据所有机构的显示数据信息
		User user = (User)request.getSession().getAttribute("user");
		List<Integer> orgIdList = iOrgService.getOrgIdCollection(user.getOrgId());
		map.put("list", orgIdList);

		total = iOrgService.queryAllCountForOrgId(orgIdList);
		if(total > 0) {
			list = iOrgService.queryBeansForOrgId(map);
		}
		total = (total+pageSize)/pageSize;
		dataMap.put("total", total);
		dataMap.put("list", list);
		
		return dataMap;
	}
	
	@RequestMapping("/OrgLoad")
	public @ResponseBody Map<String, Object> OrgLoad(HttpServletRequest request,@Param("name") String name) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();

		Org queryBean = iOrgService.queryBean(name);
		if(queryBean != null) {
			dataMap.put("status", true);
			dataMap.put("org", queryBean);
		}
		else {
			dataMap.put("status", false);
		}
		
		return dataMap;
	}
	
	@RequestMapping("/OrgNew")
	public @ResponseBody String OrgNew(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iOrgFlag", 0);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/OrgView")
	public @ResponseBody String OrgView(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iOrgFlag", 1);
		session.setAttribute("name", name);
		
		
		return "SUCCESS";
	}
	
	@RequestMapping("/OrgEdit")
	public @ResponseBody String OrgEdit(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iOrgFlag", 1);
		session.setAttribute("name", name);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/OrgRemove")
	public @ResponseBody Map<String, Object> OrgRemove(HttpServletRequest request,Integer id) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int iRows = 0;
		Org org = iOrgService.queryBeanForId(id);
		String name = org.getOrgName();
		// query delete org is have sub org
		ArrayList<Org> list = iOrgService.queryBeansForParentName(name);
		// have sub org
		if(list.size() > 0) {
			dataMap.put("status", false);
			return dataMap;
		}
		else {
			dataMap.put("status", true);
			iRows = iOrgService.deleteBean(name);
			if(iRows == 1) {
				dataMap.put("result", true);
			}
			else {
				dataMap.put("result",false);
			}
		}

		return dataMap;
	}
	
	@RequestMapping("/OrgSearch")
	public @ResponseBody Map<String, Object> OrgSearch(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		ArrayList<Org> list = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();

		list = iOrgService.queryBeans(name);
		if(list.size() >0) {
			dataMap.put("total", list.size());
			dataMap.put("list", list);
		}
		else {
			dataMap.put("total", 0);
			dataMap.put("list", list);
		}

		return dataMap;
	}
	
	@RequestMapping("/OrgValidate")
	public @ResponseBody Map<String, Object> OrgValidate(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		Org queryBean = iOrgService.queryBean(name);
		if(queryBean != null) {	
			dataMap.put("result", true);
		}
		else {	
			dataMap.put("result", false);
		}

		return dataMap;
	}
	
	//produces = "text/html;charset=UTF-8" ,
	@RequestMapping(value = "/OrgSave",method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> OrgSave(HttpServletRequest request,Org bean,@Param("oldOrgName") String oldOrgName) throws Exception{//rname:rolename
		Map<String, Object> dataMap = new HashMap<>();
		
		if(add(bean)) {	
			dataMap.put("result", true);	
		}
		else {
			dataMap.put("result", false);	
		}

		return dataMap;
	}
	
	
	@RequestMapping(value = "/OrgEditSave",method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> OrgEditSave(HttpServletRequest request,Org bean, Integer id) throws Exception{
		Map<String, Object> dataMap = new HashMap<>();
		
		if(edit(bean,id)) {	
			dataMap.put("result", true);
		}
		else {
			dataMap.put("result", false);	
		}
	
		return dataMap;
	}
	
	
	public boolean add(Org bean) throws Exception{
		boolean bResult = false;
		
		bean.setCreateTime(new Date(System.currentTimeMillis()));	
		bean.setModifyTime(new Date(System.currentTimeMillis()));
		
		// get parent org information
		Org parentBean = iOrgService.queryBeanForOrgId(Integer.valueOf(bean.getParentId()));
		if(parentBean != null) {// have parent org
			bean.setOrgLevel(parentBean.getOrgLevel()+1);
			bean.setParentName(parentBean.getOrgName());
			// get parent org all sub org for max sub org informaton
			Org subBean = iOrgService.queryBeanForMaxSubId(parentBean.getOrgId());
			if(subBean != null) {
				bean.setOrgId(subBean.getOrgId()+1);
			}
			else {
				bean.setOrgId(Integer.valueOf(parentBean.getOrgId()+"1"));
			}
		}
		
		bean.setOrgType(2);// 2:表示管理机构属于管理端(政府端)
		if(iOrgService.addBean(bean) == 1) {
			bResult = true;
		}
		else {
			bResult = false;
		}
		
		return bResult;
	}
	
	/**
	 * @param bean
	 * @param oldOrgName
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Org bean,Integer id) throws Exception{
		boolean bResult = false;
		
		Org queryBean = iOrgService.queryBeanForId(id);
		if(queryBean != null) {
			bean.setCreateTime(queryBean.getCreateTime());	
			bean.setModifyTime(new Date(System.currentTimeMillis()));
			bean.setOrgType(queryBean.getOrgType());
			
			CopyBean.Target2Src(queryBean,bean);
			if(iOrgService.updateBean(queryBean) == 1) {
				bResult = true;
			}
			else {
				bResult = false;
			}
		}

		return bResult;
	}
	
	
	@RequestMapping("/Ztree")
	public @ResponseBody JSONArray Ztree(HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<OldETreeNode> list = iSystemResourceService.GetSystemOrg(request);
		
		User user = (User)request.getSession().getAttribute("user");
		
		//user.getOrgId就是顶层机构
		//return JSONArray.fromObject(getRecordTree(list,user.getParentId()));
		return JSONArray.fromObject(getRecordTree(list));
	}
	
	@RequestMapping("/GovernmentZtree")
	public @ResponseBody JSONArray GovernmentZtree(HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<OldETreeNode> list = iSystemResourceService.GetSystemGovernmentOrg(request);
		
		return JSONArray.fromObject(getRecordTree(list));
	}
	
	/*public static List<OldETreeNode> getRecordTree(List<OldETreeNode> records) {
	    List<OldETreeNode> result = new ArrayList<OldETreeNode>();
	    Map<Integer, OldETreeNode> map = new HashMap<Integer, OldETreeNode>();
	    
	    for (OldETreeNode record : records) {
	        map.put(record.getId(), record);
	    }
	    
	    for (OldETreeNode record : records) {
	        if (record.getpId() == 0) {
	        	//record.setState("closed");
	            result.add(record);
	        } else {
	        	OldETreeNode parentRecord = map.get(record.getpId());
	            if (parentRecord == null) {
	            	result.add(record);
	                continue;
	            }
	            ArrayList<OldETreeNode> list = parentRecord.getChildren();
	            list = list == null ? new ArrayList<OldETreeNode>() : list;
	            list.add(record);
	            parentRecord.setOpen(false);
	            parentRecord.setChildren(list);
	        }
	    }
	    
	    return result;
	}*/
	
	public static List<OldETreeNode> getRecordTree(List<OldETreeNode> records) {
	    List<OldETreeNode> result = new ArrayList<OldETreeNode>();
	    Map<Integer, OldETreeNode> map = new HashMap<Integer, OldETreeNode>();
	    
	    for (OldETreeNode record : records) {
	        map.put(record.getId(), record);
	    }
	    
	    for (OldETreeNode record : records) {
	        if (record.getpId() == 0) {
	        	//record.setState("closed");
	            result.add(record);
	        } else {
	        	OldETreeNode parentRecord = map.get(record.getpId());
	            if (parentRecord == null) {
	            	result.add(record);
	                continue;
	            }
	            ArrayList<OldETreeNode> list = parentRecord.getChildren();
	            list = list == null ? new ArrayList<OldETreeNode>() : list;
	            list.add(record);
	            parentRecord.setOpen(false);
	            parentRecord.setChildren(list);
	        }
	    }
	    for (OldETreeNode record : records) {
	    	record.setIsParent(!record.getOpen());
	    }
	    return result;
	}
	
	@RequestMapping("/findDetail")
	@ResponseBody
	public Map<String,Object> findDetail (@RequestParam Integer id){
		Map<String,Object> resultMap = new HashMap<>();
		Org bean = iOrgService.queryBeanForId(id);
		resultMap.put("bean", bean);
		return resultMap;
	}
	
	
	@RequestMapping("/test123")
	public @ResponseBody Map<String, Object> test123(HttpServletRequest request,@Param("orgId") int orgId) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Integer> resultList = new ArrayList<>();
		resultList = iOrgService.getOrgIdCollection(orgId);
		
		dataMap.put("list", resultList);

		return dataMap;
	}
	

}
