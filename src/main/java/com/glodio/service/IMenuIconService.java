package com.glodio.service;

import java.util.ArrayList;

import com.glodio.bean.MenuIcon;

public interface IMenuIconService {
	MenuIcon queryBean(String iconName);
	    
	ArrayList<MenuIcon> queryBeans();
}
