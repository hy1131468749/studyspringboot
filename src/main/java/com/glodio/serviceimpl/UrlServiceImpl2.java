package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glodio.bean.Url;
import com.glodio.dao.IUrlDAO;
import com.glodio.service.IUrlService;
import com.glodio.util.LogFile;

@Service("iUrlService")
public class UrlServiceImpl2 implements IUrlService {
	@Resource
	private  IUrlDAO iUrlDAO;
	
	@Override
	public int addBean(Url bean) {
		int iRows = 0;
		
		try {
			iRows = iUrlDAO.addBean(bean);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [addBean],Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public int deleteBean(String strValue) {
		int iRows = 0;
		
		try {
			iRows = iUrlDAO.deleteBean(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [deleteBean],Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public int updateBean(Url bean) {
		int iRows = 0;
		
		try {
			iRows = iUrlDAO.updateBean(bean);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [updateBean],Exception message is:{}", e.getMessage());
		}
		
		return iRows;
	}

	@Override
	public Url queryBean(String strValue) {
		Url bean = null;
		try {
			bean = iUrlDAO.queryBean(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBean],Exception message is:{}", e.getMessage());
		}
		
		return bean;
	}

	@Override
	public ArrayList<Url> queryBeans(String strValue) {
		ArrayList<Url> list = null;
		
		try {
			list = iUrlDAO.queryBeans(strValue);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeans],Exception message is:{}", e.getMessage());
		}
		
		return list;
	}

	@Override
	public ArrayList<Url> queryByPage(Map<String, Object> map) {
		ArrayList<Url> list = null;
		
		try {
			list = iUrlDAO.queryByPage(map);
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
			count = iUrlDAO.queryAllCount();
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryAllCount],Exception message is:{}", e.getMessage());
		}
		
		return count;
	}

	@Override
	public Url queryByMaxForParentId(int parentId) {
		Url bean = null;
		
		try {
			bean = iUrlDAO.queryByMaxForParentId(parentId);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryByMaxForParentId],Exception message is:{}", e.getMessage());
		}

		return bean;
	}

	@Override
	public ArrayList<Url> queryBeansForCataLogLevel(int iLevel) {
		ArrayList<Url> list = null;
		
		try {
			list = iUrlDAO.queryBeansForCataLogLevel(iLevel);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeansForCataLogLevel],Exception message is:{}", e.getMessage());
		}
		
		return list;
	}

	@Override
	public ArrayList<Url> queryBeansForSubId(List<Integer> list) {
		ArrayList<Url> resultlist = null;
		
		try {
			resultlist = iUrlDAO.queryBeansForSubId(list);
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryBeansForSubId],Exception message is:{}", e.getMessage());
		}
		
		return resultlist;
	}

	@Override
	public ArrayList<Url> queryAllBeans() {
		ArrayList<Url> list = null;
		
		try {
			list = iUrlDAO.queryAllBeans();
		} catch (Exception e) {
			//e.printStackTrace();
			LogFile.sqlLogger.error("Method is [queryAllBeans],Exception message is:{}", e.getMessage());
		}
		
		return list;
	}
}
