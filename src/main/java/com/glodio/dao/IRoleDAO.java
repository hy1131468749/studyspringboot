package com.glodio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.glodio.bean.Org;
import com.glodio.bean.Role;

public interface IRoleDAO {
    int addBean(Role bean);
    
    int deleteBean(String strValue);
    
    int updateBean(Role bean);
    
    Role queryBean(String strValue);
    
    Role queryBeanForNameAndOrgid(@Param("rolename")String rolename,@Param("orgId") int orgId);
    
    ArrayList<Role> queryBeans(String strValue);
    
    ArrayList<Role> queryByPage(Map<String, Object> map);
    
    int queryAllCount();
    
	int queryAllCountForOrgId(List<Integer> list);
	
	ArrayList<Role> queryBeansForOrgId(Map<String, Object> map);
	
	List<Role> selectList();
	
	Role selectByRoleName(String roleName);
	
	int deleteById(Integer id);
	
	Role selectById(Integer id);
	
	List<Role> selectListByOrgId(Integer orgId);
	
}