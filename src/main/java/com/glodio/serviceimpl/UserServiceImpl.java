package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glodio.bean.User;
import com.glodio.dao.IUserDAO;
import com.glodio.service.IUserService;
import com.glodio.util.LogFile;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

	//@Resource
	@Autowired
	private IUserDAO iUserDAO;
	
	@Override
	public int addBean(User bean) {
		int iRows = 0;
		
		try {
			iRows = iUserDAO.addBean(bean);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [addBean],Exception message is:{}Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public int deleteBean(String strValue) {
		int iRows = 0;
		
		try {
			iRows = iUserDAO.deleteBean(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [deleteBean],Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public int updateBean(User bean) {
		int iRows = 0;
		
		try {
			iRows = iUserDAO.updateBean(bean);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [updateBean],Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public User queryBean(String strValue) {
		User bean = null;
		try {
			bean = iUserDAO.queryBean(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBean],Exception message is:{}", e.getMessage());
		}
		return bean;
	}

	@Override
	public ArrayList<User> queryBeans(String strValue) {
		ArrayList<User> list = null;
		
		try {
			list = iUserDAO.queryBeans(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeans],Exception message is:{}", e.getMessage());
		}
		
		return list;
	}

	@Override
	public ArrayList<Map<String, Object>> queryByPage(Map<String, Object> map) {
		ArrayList<Map<String, Object>> list = null;
		
		try {
			list = iUserDAO.queryByPage(map);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryByPage],Exception message is:{}", e.getMessage());
		}

		return list;
	}

	@Override
	public int queryAllCount() {
		int count = 0;
		
		try {
			count = iUserDAO.queryAllCount();
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryAllCount],Exception message is:{}", e.getMessage());
		}
		
		return count;
	}

	@Override
	public boolean loginUser(User bean) {
		boolean bLoginResult = false;
		
		try {
			User queryBean = iUserDAO.queryBeanPassword(bean.getUsername());
			if (queryBean != null) {
				if(queryBean.getPassword().equals(bean.getPassword())) {
					bLoginResult =true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogFile.sqlLogger.error("Method is [loginUser],Exception message is:{}", e.getMessage());
		}
		
		return bLoginResult;
	}
	
	@Override
	public ArrayList<Map<String, Object>> queryBeansForOrgId(Map<String, Object> map) {
		ArrayList<Map<String, Object>> resultlist = null;

		try {
			resultlist = iUserDAO.queryBeansForOrgId(map);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeansForOrgId],Exception message is:{}", e.getMessage());
		}

		return resultlist;
	}

	@Override
	public int queryAllCountForOrgId(List<Integer> list) {
		int count = 0;

		try {
			count = iUserDAO.queryAllCountForOrgId(list);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryAllCountForOrgId],Exception message is:{}", e.getMessage());
		}

		return count;
	}

	@Override
	public User queryBeanPassword(String strValue) {
		User bean = null;
		try {
			bean = iUserDAO.queryBeanPassword(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeanPassword],Exception message is:{}", e.getMessage());
		}
		
		return bean;
	}

	@Override
	public User queryBeanById(Integer id) {
		
		return iUserDAO.queryBeanById(id);
	}

	@Override
	public int deleteById(Integer id) {
		
		return iUserDAO.deleteById(id);
	}

}
