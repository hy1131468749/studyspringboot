package com.glodio.dao;

import java.util.ArrayList;
import java.util.Map;

import com.glodio.bean.Url;

public interface IUrlDAO {
    int addBean(Url bean);
    
    int deleteBean(String strValue);
    
    int updateBean(Url bean);
    
    Url queryBean(String strValue);
    
    ArrayList<Url> queryAllBeans();
    
    ArrayList<Url> queryBeans(String strValue);
    
    ArrayList<Url> queryBeansForSubId(java.util.List<Integer> list);
    
    ArrayList<Url> queryBeansForCataLogLevel(int iLevel);
    
    Url queryByMaxForParentId(int parentId);
    
    ArrayList<Url> queryByPage(Map<String, Object> map);
    
    int queryAllCount();
}