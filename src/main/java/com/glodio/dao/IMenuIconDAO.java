package com.glodio.dao;

import java.util.ArrayList;

import com.glodio.bean.MenuIcon;

public interface IMenuIconDAO {
    MenuIcon queryBean(String iconName);
    
    ArrayList<MenuIcon> queryBeans();
}