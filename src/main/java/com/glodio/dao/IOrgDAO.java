package com.glodio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glodio.bean.Org;

public interface IOrgDAO {
    int addBean(Org bean);
    
    int deleteBean(String orgName);
    
    int deleteById(Integer id);
    
    int updateBean(Org bean);
    
    Org queryBean(String orgName);
    
    ArrayList<Org> queryBeans(String orgName);
    
    ArrayList<Org> queryBeansForParentName (String parentName);
    
    Org queryBeanForId(int id);
    
    Org queryBeanForOrgId(int orgId);
    
    Org queryBeanForMaxSubId(int parentId);
    
    ArrayList<Org> queryByPage(Map<String, Object> map);
    
    ArrayList<Org> queryAllBeans();
    
    int queryAllCount();
    
    
    List<Integer> queryOrgIdListForParentId(int orgId);
    
    List<Integer> queryOrgIdCollection(List<Integer> list);
    
	int queryAllCountForOrgId(List<Integer> list);
	
	ArrayList<Org> queryBeansForOrgId(Map<String, Object> map);
}