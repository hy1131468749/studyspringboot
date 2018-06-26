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
import com.glodio.bean.Role;
import com.glodio.bean.User;
import com.glodio.service.IOrgService;
import com.glodio.service.IRoleService;
import com.glodio.service.IUserService;
import com.glodio.util.CopyBean;

@Controller
@RequestMapping("/system/rbac/user")
public class UserManagerController {

	@Resource
	IUserService iUserService;
	@Resource
	IRoleService IRoleService;
	@Resource
	IOrgService iOrgService;
	
	@RequestMapping("/UserList")
	public @ResponseBody Map<String, Object> UserList(HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> list = null;
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

		total = iUserService.queryAllCountForOrgId(orgIdList);
		if(total > 0) {
			list = iUserService.queryBeansForOrgId(map);
		}
		total = (total+pageSize)/pageSize;
		dataMap.put("total", total);
		dataMap.put("list", list);
		
		return dataMap;
	}
	
	@RequestMapping("/UserLoad")
	public @ResponseBody Map<String, Object> UserLoad(HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		int iUsreFlag = Integer.parseInt(session.getAttribute("iUsreFlag").toString());
		
		if(iUsreFlag == 0) {
			dataMap.put("status", false);
		}
		else {
			
			User sessionUser = (User)request.getSession().getAttribute("user");
			
			User queryBean = iUserService.queryBeanPassword(request.getSession().getAttribute("name").toString());
			
			if(sessionUser != null && queryBean != null) {
				dataMap.put("status", true);
				dataMap.put("user", queryBean);
				if(sessionUser.getSystemUser() == 1) {
					dataMap.put("systemUser", 1);
				}
				else {
					dataMap.put("systemUser", 0);
				}
				if(sessionUser.getUsername().equals(queryBean.getUsername())) {
					dataMap.put("result", true);//当前选择编辑的用户是登录的用户，则可以编辑用户名和密码
				}
				else {
					dataMap.put("result", false);
				}
			}
			else {
				dataMap.put("result", false);
			}
		}
		
		return dataMap;
	}
	
	@RequestMapping("/UserNew")
	public @ResponseBody String UserNew(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iUsreFlag", 0);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/UserView")
	public @ResponseBody String UserView(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iUsreFlag", 1);
		session.setAttribute("name", name);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/UserEdit")
	public @ResponseBody String UserEdit(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("iUsreFlag", 1);
		session.setAttribute("name", name);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/UserQueryRemove")
	public @ResponseBody Map<String, Object> UserQueryRemove(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		User user = iUserService.queryBean(name);
		if(user != null) {
			if(user.getSystemUser() == 1) {
				dataMap.put("result", true);
			}
			else {
				dataMap.put("result",false);
			}
		}
		else {
			dataMap.put("result",false);
		}
		
		return dataMap;
	}
	
	@RequestMapping("/UserRemove")
	public @ResponseBody Map<String, Object> UserRemove(HttpServletRequest request,@RequestParam("id") Integer id) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int iRows = 0;
		
		iRows = iUserService.deleteById(id);
		if(iRows == 1) {
			dataMap.put("result", true);
		}
		else {
			dataMap.put("result",false);
		}
		
		return dataMap;
	}
	
	@RequestMapping("/UserSearch")
	public @ResponseBody Map<String, Object> UserSearch(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		ArrayList<User> list = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();

		list = iUserService.queryBeans(name);
		if(list != null) {
			dataMap.put("total", list.size());
			dataMap.put("list", list);
		}

		return dataMap;
	}
	
	@RequestMapping("/UserValidate")
	public @ResponseBody Map<String, Object> UserValidate(HttpServletRequest request,@RequestParam("name") String name) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		User queryBean = iUserService.queryBean(name);
		if(queryBean != null) {	
			dataMap.put("result", true);
		}
		else {	
			dataMap.put("result", false);
		}

		return dataMap;
	}
	
	@RequestMapping(value = "/UserSave",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> UserSave(HttpServletRequest request,User bean,
			@RequestParam("username") String username,@RequestParam("rname") String rname,@Param("oldName") String oldName) throws Exception{//rname:rolename
		Map<String,Object> jsonObject = new HashMap<>();
		int iUsreFlag = Integer.parseInt(request.getParameter("iUsreFlag"));
		//System.out.println(rname);
		if(iUsreFlag == 0) {//新增
			//验证用户名是否存在
			User queryBean = iUserService.queryBean(username);
			if(queryBean!=null){
				jsonObject.put("result", false);
				jsonObject.put("msg", "该用户名已存在。");	
				return jsonObject;
			}
			if(add(bean,rname)) {	
				jsonObject.put("result", true);	
			}
			else {
				jsonObject.put("result", false);	
			}
		}
		else {//编辑
			if(edit(bean,username,rname,oldName)) {	
				jsonObject.put("result", true);	
			}
			else {
				jsonObject.put("result", false);	
			}
		}
	
		return jsonObject;
	}
	
	public boolean add(User bean,String rolename) throws Exception{
		boolean bResult = false;
		
		bean.setRolename(rolename);
		bean.setCjsj(new Date(System.currentTimeMillis()));	
		bean.setXgsj(new Date(System.currentTimeMillis()));
		bean.setSystemUser(0);
		// 初始设置首页用户配置颜色为12种告警类型全部选定
		bean.setAlarmColors("1;2;3;4;5;6;7;8;9;10;11;12;");
		
		//设置父机构id
		Org org = iOrgService.queryBeanForOrgId(bean.getOrgId());
		if(org != null) {
			bean.setParentId(org.getParentId());
			bean.setOrgName(org.getOrgName());
			bean.setOrgId(org.getOrgId());
		}
		if(iUserService.addBean(bean) == 1) {
			bResult = true;
		}
		else {
			bResult = false;
		}
		
		return bResult;
	}
	
	public boolean edit(User bean ,String username,String rolename,String oldName) throws Exception{
		boolean bResult = false;
		
		String strQueryName = "";
		if(oldName.equals("")) {// 为空时，说明是编辑其他用户，不是修改自身
			strQueryName = username;
		}
		else {// 修改自己，用原用户名，查询
			strQueryName = oldName;
		}
		User queryBean = iUserService.queryBean(strQueryName);
		if(queryBean != null) {
			bean.setRolename(rolename);
			bean.setCjsj(queryBean.getCjsj());	
			bean.setXgsj(new Date(System.currentTimeMillis()));
			bean.setSystemUser(queryBean.getSystemUser());
			
			//设置父机构id
			Integer iOrgId = 0;
			if(bean.getOrgId() != null ) {
				iOrgId = bean.getOrgId();
			}
			else {
				iOrgId = queryBean.getOrgId();
			}
			Org org = iOrgService.queryBeanForOrgId(iOrgId);
			if(org != null) {
				bean.setParentId(org.getParentId());
				bean.setOrgName(org.getOrgName());
			}
			
			CopyBean.Target2Src(queryBean,bean);
			if(iUserService.updateBean(queryBean) == 1) {
				bResult = true;
			}
			else {
				bResult = false;
			}
		}

		return bResult;
	}
	
	@RequestMapping("/OldPasswordValidate")
	public @ResponseBody Map<String, Object> OldPasswordValidate(HttpServletRequest request,@RequestParam("username") String username,
			@Param("password") String password) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		User queryBean = iUserService.queryBeanPassword(username);
		if(queryBean != null) {
			if(queryBean.getPassword().equals(password)) {
				dataMap.put("result", true);
			}
			else {
				dataMap.put("result", false);
			}
		}
		else {	
			dataMap.put("result", false);
		}

		return dataMap;
	}
	
	@RequestMapping("/ModifyPassword")
	public @ResponseBody Map<String, Object> ModifyPassword(HttpServletRequest request,@RequestParam("username") String username,
			@Param("password") String password) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//System.out.println(username + "  "+password);
		
		User queryBean = iUserService.queryBean(username);
		if(queryBean != null) {
			queryBean.setPassword(password);
			queryBean.setXgsj(new Date(System.currentTimeMillis()));
			
			if(iUserService.updateBean(queryBean) == 1) {
				dataMap.put("result", true);
			}
			else {
				dataMap.put("result", false);
			}
		}
		else {	
			dataMap.put("result", false);
		}

		return dataMap;
	}
	
	@RequestMapping("/UserInfor")
	public @ResponseBody Map<String, Object> UserInfor(HttpServletRequest request,@Param("username") String username) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();

		User queryBean = iUserService.queryBean(username);
		if(queryBean != null) {
			dataMap.put("result", true);
			dataMap.put("user", queryBean);
			Role roleBean = IRoleService.queryBean(queryBean.getRolename());
			if(roleBean != null) {
				dataMap.put("roleResult",true);
				dataMap.put("url",roleBean.getUrl());
			}
		}
		else {
			dataMap.put("result", false);
		}
		
		return dataMap;
	}
	
	@RequestMapping("/findDetail")
	@ResponseBody
	public Map<String, Object> findDetail(@RequestParam Integer id){
		Map<String,Object> resultMap = new HashMap<>();
		User bean = iUserService.queryBeanById(id);
		resultMap.put("bean", bean);
		return resultMap;
		
	}
	
	
}
