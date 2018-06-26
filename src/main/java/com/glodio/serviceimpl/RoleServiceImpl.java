package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glodio.bean.Org;
import com.glodio.bean.Role;
import com.glodio.dao.IRoleDAO;
import com.glodio.service.IRoleService;

@Service("iRoleService")
public class RoleServiceImpl implements IRoleService {

	@Resource
	private IRoleDAO iRoleDAO;
	
	@Override
	public int addBean(Role bean) {
		int iRows = 0;
		
		try {
			iRows = iRoleDAO.addBean(bean);
			return iRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int deleteBean(String strValue) {
		int iRows = 0;
		
		try {
			iRows = iRoleDAO.deleteBean(strValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return iRows;
	}

	@Override
	public int updateBean(Role bean) {
		int iRows = 0;
		
		try {
			iRows = iRoleDAO.updateBean(bean);
			return iRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Role queryBean(String strValue) {
		Role bean = null;
		try {
			bean = iRoleDAO.queryBean(strValue);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Role> queryBeans(String strValue) {
		ArrayList<Role> list = null;
		
		try {
			list = iRoleDAO.queryBeans(strValue);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Role> queryByPage(Map<String, Object> map) {
		ArrayList<Role> list = null;
		
		try {
			list = iRoleDAO.queryByPage(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int queryAllCount() {
		int count = 0;
		
		try {
			count = iRoleDAO.queryAllCount();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public ArrayList<Role> queryBeansForOrgId(Map<String, Object> map) {
		ArrayList<Role> resultlist = null;

		try {
			resultlist = iRoleDAO.queryBeansForOrgId(map);
			return resultlist;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int queryAllCountForOrgId(List<Integer> list) {
		int count = 0;

		try {
			count = iRoleDAO.queryAllCountForOrgId(list);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public Role queryBeanForNameAndOrgid(String rolename, int orgId) {
		Role bean = null;
		try {
			bean = iRoleDAO.queryBeanForNameAndOrgid(rolename,orgId);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Role> selectList() {
		return iRoleDAO.selectList();
	}

	@Override
	public Role selectByRoleName(String roleName) {
		return iRoleDAO.selectByRoleName(roleName);
	}

	@Override
	public int deleteById(Integer id) {
		return iRoleDAO.deleteById(id);
	}

	@Override
	public Role selectById(Integer id) {
		
		return iRoleDAO.selectById(id);
	}

	@Override
	public List<Role> selectListByOrgId(Integer orgId) {
		return iRoleDAO.selectListByOrgId(orgId);
	}

}
