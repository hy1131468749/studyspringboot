package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.ConnectPlatform;
import com.glodio.bean.KeyValue;
import com.glodio.bean.Org;
import com.glodio.bean.Url;
import com.glodio.bean.User;
import com.glodio.service.IConnectPlatformService;
import com.glodio.service.IKeyValueService;
import com.glodio.service.IOrgService;
import com.glodio.service.IRoleService;
import com.glodio.service.ISystemResourceService;
import com.glodio.service.IUrlService;
import com.glodio.service.IUserService;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.ETreeNode;
import com.glodio.util.OldETreeNode;

@Service("iSystemMenuService")
public class SystemResourceServiceImpl implements ISystemResourceService {
	@Resource
	IUrlService iUrlService;

	@Resource
	IKeyValueService iKeyValueService;
	
	@Resource
	IUserService iUserService;
	
	@Resource
	IOrgService iOrgService;
	
	@Resource
	IConnectPlatformService iConnectPlatformService;
	
	@Override
	public ArrayList<ETreeNode> GetSystemMenu(HttpServletRequest request) {
		ArrayList<ETreeNode> nodeList = new ArrayList<>();;
		ArrayList<Url> list = null;
		
		// 获取用户
		//User user = (User)request.getSession().getAttribute("user");
		User user = iUserService.queryBean("admin");
		if(user != null && user.getUrlIndex() != null && (!user.getUrlIndex().equals(""))) 
		{
			String[] arrayIndex = user.getUrlIndex().split(",");
			List<Integer> indexList = new ArrayList<>();
			
			for(int i = 0;i < arrayIndex.length;i++) 
				indexList.add(Integer.parseInt(arrayIndex[i]));

 			list = iUrlService.queryBeansForSubId(indexList);
			for(Url url:list) 
			{
				ETreeNode node = null;
				if(url.getParentId() == 0) 
				{
					node = new ETreeNode(url.getSubId(),url.getParentId(), 
										 url.getUrlName(), url.getUrlAddr(),true,url.getIconName());
				}
				else 
				{
					node = new ETreeNode(url.getSubId(),url.getParentId(), 
										 url.getUrlName(), url.getUrlAddr(),false,url.getIconName());
				}
				nodeList.add(node);
			}
		}

		return nodeList;
	}


	
	@Override
	public Map<String, String> GetKeyValueMap() {
		ArrayList<KeyValue> list = null;
		
		list = iKeyValueService.queryBeansForAll();
		for(KeyValue keyValue:list) {
			SystemKeyValueMap.getInstance().put(keyValue.getKey(), keyValue.getValue());
		}
		
		return SystemKeyValueMap.getInstance();
	}

	@Override
	public ArrayList<OldETreeNode> GetSystemOrg(HttpServletRequest request) {
		ArrayList<OldETreeNode> nodeList = new ArrayList<>();
		ArrayList<Org> list = null;

		list = iOrgService.queryAllBeans();
		OldETreeNode node = null;
		for(Org org:list) {
			node = new OldETreeNode(org.getOrgId(),org.getParentId(), org.getOrgName(),org.getOrgType());
			node.setRealId(org.getId()+"");
			nodeList.add(node);
		}
		
		return nodeList;
	}
	
	@Override
	public ArrayList<OldETreeNode> GetSystemGovernmentOrg(HttpServletRequest request) {
		ArrayList<OldETreeNode> nodeList = new ArrayList<>();
		ArrayList<Org> list = null;

		list = iOrgService.queryAllBeans();
		OldETreeNode node = null;
		for(Org org:list) {
			if(org.getOrgType() == 2) {// 主管安监机构
				node = new OldETreeNode(org.getOrgId(),org.getParentId(), org.getOrgName());
				nodeList.add(node);
			}
		}
		
		return nodeList;
	}

	@Override
	public Map<String, ConnectPlatform> getConnectPlatformMap() {
		List<ConnectPlatform> list = iConnectPlatformService.selectAllList();
		for(ConnectPlatform bean:list) {
			ConnectPlatformMap.getInstance().put(bean.getId()+"", bean);
		}
		
		return ConnectPlatformMap.getInstance();
	}
	
}
