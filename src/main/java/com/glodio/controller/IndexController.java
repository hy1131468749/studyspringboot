package com.glodio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glodio.bean.Role;
import com.glodio.bean.Testdata;
import com.glodio.bean.Url;
import com.glodio.bean.User;
import com.glodio.dao.TestdataMapper;
import com.glodio.service.IRoleService;
import com.glodio.service.ISystemResourceService;
import com.glodio.service.IUserService;
import com.glodio.service.UrlService;
import com.glodio.util.ETreeNode;
import com.glodio.util.List2Tree;
import com.glodio.util.StringUtil;

@Controller
@RequestMapping("/system")
public class IndexController {
	@Resource
	IUserService iUserService;

	@Resource
	ISystemResourceService iSystemResourceService;

	@Autowired
	UrlService urlService;

	@Resource
	private IRoleService iRoleService;

	@Autowired
	private TestdataMapper testdataMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest request, @Param("username") String username,
			@Param("password") String password) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		boolean result = false;

		User bean = iUserService.queryBeanPassword(username);
		if (bean != null && bean.getPassword().equals(password)) {
			result = true;
			request.getSession().setAttribute("user", bean);
			//String rolename = bean.getRolename();
			initRoles(bean, request.getSession());

		}

		dataMap.put("result", result);

		return dataMap;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getUrls")
	public Map<String, Object> getUrls(HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Url> urls = (List<Url>) session.getAttribute("urls");
		List<Url> allUrls = urlService.selectList();
		List<Map<String, String>> urlViews = new ArrayList<>();
		List<Map<String, String>> showViews = new ArrayList<>();
		if (urls != null) {
			StringBuilder sb = new StringBuilder();
			for (Url url : urls) {
				sb.append(url.getUrlAddr() + ";");
			}
			String surlS = sb.toString();
			resultMap.put("urls", urls);
			Map<String, String> urlViewMap = null;
			//查询出没有的权限 因为前端无法解决 所以只能把没有权限的界面进行隐藏
			for (Url url : allUrls) {
				if (!surlS.contains(url.getUrlAddr() + ";")) {
					urlViewMap = new HashMap<>();
					urlViewMap.put("alias", url.getUrlAddr().replace("src/", "").replace(".html", ""));
					urlViewMap.put("name", url.getUrlName());
					urlViews.add(urlViewMap);
				}else{
					urlViewMap = new HashMap<>();
					urlViewMap.put("alias", url.getUrlAddr().replace("src/", "").replace(".html", ""));
					urlViewMap.put("name", url.getUrlName());
					showViews.add(urlViewMap);
				}
			}
			resultMap.put("urlView", urlViews);
			resultMap.put("showViews", showViews);
		}
		return resultMap;

	}

	private void initRoles(User bean, HttpSession session) {
		if (StringUtil.strIsNullOrEmpty(bean.getRolename())) {
			logger.error("改用户没有用户名");
			return;
		}
		Role role = iRoleService.selectByRoleName(bean.getRolename());
		if (role == null || StringUtil.strIsNullOrEmpty(role.getUrlremark())) {
			logger.error("角色为空");
			return;
		}
		String[] ulrIds = role.getUrlremark().split(";");
		if (ulrIds == null || ulrIds.length == 0) {
			return;
		}
		List<Integer> ids = new ArrayList<>();
		for (String id : ulrIds) {
			ids.add(Integer.parseInt(id));
		}
		List<Url> urls = urlService.selectListByIds(ids);
		if (urls != null) {
			session.setAttribute("urls", urls);
		}

	}

	@RequestMapping("/GetUserName")
	public @ResponseBody String GetUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			return "null";
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			if (user.getUsername() != null) {
				return user.getUsername();
			} else {

				return "null";
			}
		} else {

			return "null";
		}
	}

	@RequestMapping("/getMenu")
	public @ResponseBody ArrayList<ETreeNode> getMenu(HttpServletRequest request) throws Exception {

		ArrayList<ETreeNode> list = List2Tree.getTreeStructure((iSystemResourceService.GetSystemMenu(request)));

		return list;
	}

	@RequestMapping("/getModelAndView")
	public ModelAndView getModelAndView(HttpServletRequest request) throws Exception {
		ModelAndView mView = new ModelAndView();
		mView.addObject("message", "hello world !");
		mView.setViewName("test");

		return mView;
	}
	
	
	@RequestMapping("/testInsert")
	public void testInsert(HttpServletRequest request) throws Exception {
		Date date = new Date();
		com.glodio.bean.Testdata testdata = null;
		for(int i=1;i<300;i++){
			date.setDate(date.getDate()+1);
			System.out.println(date);
			for(int j=1;j<400;j++){
				testdata = new Testdata();
				testdata.setCreateTime(date);
				testdata.setAddress(i*j+"的地址");
				testdata.setAge(new Random(5000).nextInt());
				testdata.setHeight(new Random(30).nextDouble());
				testdata.setWeight(new Random(150).nextDouble());
				testdata.setOccupation(i*j+"的职业");
				testdata.setRemark(i*j+"的备注");
				testdata.setName(i*j+"的姓名");
				testdataMapper.insert(testdata);
		    }
			
		}
		System.out.println("执行完毕");
	}
	
}
