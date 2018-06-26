package com.glodio.serviceimpl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glodio.bean.MenuIcon;
import com.glodio.dao.IMenuIconDAO;
import com.glodio.service.IMenuIconService;

@Service("iMenuIconService")
public class MenuIconServiceImpl implements IMenuIconService {
	
	@Resource
	IMenuIconDAO iMenuIconDAO;

	@Override
	public MenuIcon queryBean(String iconName) {
		MenuIcon bean = null;
		try {
			bean = iMenuIconDAO.queryBean(iconName);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<MenuIcon> queryBeans() {
		ArrayList<MenuIcon> list = null;
		
		try {
			list = iMenuIconDAO.queryBeans();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
