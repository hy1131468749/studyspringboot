package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glodio.bean.Org;
import com.glodio.dao.IOrgDAO;
import com.glodio.service.IOrgService;

@Service("iOrgService")
public class OrgServiceImpl implements IOrgService {

	@Resource
	private IOrgDAO iOrgDAO;
	
	@Override
	public int addBean(Org bean) {
		int iRows = 0;
		
		try {
			iRows = iOrgDAO.addBean(bean);
			return iRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int deleteBean(String orgName) {
		int iRows = 0;
		
		try {
			iRows = iOrgDAO.deleteBean(orgName);
			return iRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int updateBean(Org bean) {
		int iRows = 0;
		
		try {
			iRows = iOrgDAO.updateBean(bean);
			return iRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Org queryBean(String orgName) {
		Org bean = null;
		try {
			bean = iOrgDAO.queryBean(orgName);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Org> queryBeans(String orgName) {
		ArrayList<Org> list = null;
		
		try {
			list = iOrgDAO.queryBeans(orgName);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Org> queryByPage(Map<String, Object> map) {
		ArrayList<Org> list = null;
		
		try {
			list = iOrgDAO.queryByPage(map);
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
			count = iOrgDAO.queryAllCount();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Org queryBeanForOrgId(int orgId) {
		Org bean = null;
		try {
			bean = iOrgDAO.queryBeanForOrgId(orgId);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Org queryBeanForMaxSubId(int parentId) {
		Org bean = null;
		
		try {
			bean = iOrgDAO.queryBeanForMaxSubId(parentId);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<Org> queryBeansForParentName(String parentName) {
		ArrayList<Org> list = null;
		
		try {
			list = iOrgDAO.queryBeansForParentName(parentName);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public List<Integer> queryOrgIdCollection(List<Integer> list) {
		List<Integer> resultList = null;
		try {
			resultList = iOrgDAO.queryOrgIdCollection(list);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Integer> queryOrgIdListForParentId(int orgId) {
		List<Integer> list = null;
		try {
			list = iOrgDAO.queryOrgIdListForParentId(orgId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Integer> getOrgIdCollection(int orgId) {
		List<Integer> resultList = new ArrayList<>();
		
		// save self orgId
		resultList.add(orgId);
		
		// query orgId collection for parent_id is orgId
		List<Integer> tempList = queryOrgIdListForParentId(orgId);
		if(tempList.size() > 0) {
			// save query collection
			resultList.addAll(tempList);
			
			List<Integer> list = null;
			// recursion query
			while(true) {
				// agin find orgId collection for before collection
				list = queryOrgIdCollection(tempList);
				if(list.size() > 0) {
					resultList.addAll(list);
					tempList.clear();
					tempList.addAll(list);
				}
				else {
					break;
				}
			}
		}
		
		return resultList;
	}


	@Override
	public String getOrgNameForOrgId(int orgId) {
		String orgName = "";
		
		try {
			Org org = iOrgDAO.queryBeanForOrgId(orgId);
			if(org != null) {
				orgName = org.getOrgName();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return orgName;
	}

	@Override
	public int queryAllCountForOrgId(List<Integer> list) {
		int count = 0;
		
		try {
			count = iOrgDAO.queryAllCountForOrgId(list);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public ArrayList<Org> queryBeansForOrgId(Map<String, Object> map) {
		ArrayList<Org> list = null;
		
		try {
			list = iOrgDAO.queryBeansForOrgId(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Org> queryAllBeans() {
		ArrayList<Org> list = null;
		
		try {
			list = iOrgDAO.queryAllBeans();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Org queryBeanForId(int id) {
		
		return iOrgDAO.queryBeanForId(id);
	}

	@Override
	public int deleteById(Integer id) {
		
		return iOrgDAO.deleteById(id);
	}


	
}
