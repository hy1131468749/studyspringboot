package com.glodio.service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.glodio.bean.ConnectPlatform;
import com.glodio.util.ETreeNode;
import com.glodio.util.OldETreeNode;

public interface ISystemResourceService {
	
	/**
	 * Get user menus
	 * @param request httpSeveltRequest,
	 * @return ArrayList ETreeNode for current user owned menus
	 */
	ArrayList<ETreeNode> GetSystemMenu(HttpServletRequest request);
	
	
	/**
	 * Get system parameter collection
	 * @return map 
	 */
	Map<String, String> GetKeyValueMap();
	
	Map<String, ConnectPlatform> getConnectPlatformMap();
	
	ArrayList<OldETreeNode> GetSystemOrg(HttpServletRequest request);
	
	ArrayList<OldETreeNode> GetSystemGovernmentOrg(HttpServletRequest request);
 }
