package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glodio.bean.Device;
import com.glodio.dao.IDeviceDAO;
import com.glodio.service.IDeviceService;

@Service("iDeviceService")
public class DeviceServiceImpl implements IDeviceService {
	@Autowired
	IDeviceDAO iDeviceDAO;

	@Override
	public ArrayList<Device> queryAllBean() {
		ArrayList<Device> list = null;
		
		try {
			list = iDeviceDAO.queryAllBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int deleteBean(String deviceId) {
		int iRows = 0;
		
		try {
			iRows = iDeviceDAO.deleteBean(deviceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return iRows;
	}

	@Override
	public int addBean(Device record) {
		int iRows = 0;
		
		try {
			iRows = iDeviceDAO.addBean(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return iRows;
	}


	@Override
	public int updateBean(Device record) {
		int iRows = 0;
		
		try {
			iRows = iDeviceDAO.updateBean(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return iRows;
	}

	@Override
	public Device queryBean(String deviceId) {
		Device bean = null;
		
		try {
			bean = iDeviceDAO.queryBean(deviceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

	@Override
	public ArrayList<Device> queryBeansForDeviceType(int deviceType,int orgId) {
		ArrayList<Device> list = null;
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("deviceType", deviceType);
			params.put("orgId", orgId);
			list = iDeviceDAO.queryBeansForDeviceType(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ArrayList<Device> queryList(Map<String,Object> params) {
		return iDeviceDAO.queryList(params);
	}

	@Override
	public Device queryBeanByNbDeviceId(String nbDeviceId) {
		return iDeviceDAO.queryBeanByNbDeviceId(nbDeviceId);
	}

}
