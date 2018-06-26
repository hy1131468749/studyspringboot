package com.glodio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.glodio.bean.User;

@Repository("iUserDAO")
public interface IUserDAO {
    int addBean(User bean);
    
    int deleteBean(String strValue);
    
    int deleteById(Integer id);
    
    int updateBean(User bean);
    
    User queryBean(String strValue);
    
    User queryBeanPassword(String strValue);
    
    ArrayList<User> queryBeans(String strValue);
    
    ArrayList<Map<String, Object>> queryByPage(Map<String, Object> map);
    
    int queryAllCount();
    
	int queryAllCountForOrgId(List<Integer> list);
	
	ArrayList<Map<String, Object>> queryBeansForOrgId(Map<String, Object> map);
	
	User queryBeanById(Integer id);
}