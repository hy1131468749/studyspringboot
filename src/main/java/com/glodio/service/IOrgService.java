package com.glodio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glodio.bean.Org;

public interface IOrgService {
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

    List<Integer> getOrgIdCollection(int orgId);
    
    //通过orgId获取orgName
    String getOrgNameForOrgId(int orgId);
    
    // 查询总数，根据当前用户所属机构包含的所有机构集合总数
	int queryAllCountForOrgId(List<Integer> list);
	
	// 查询符合集合中的数据集合,其中list为当前用户所属机构包含的所有机构id集合 
	ArrayList<Org> queryBeansForOrgId(Map<String, Object> map);
    
}
