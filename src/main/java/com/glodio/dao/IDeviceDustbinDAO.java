package com.glodio.dao;

import java.util.ArrayList;
import java.util.List;

import com.glodio.bean.DeviceDustbin;

public interface IDeviceDustbinDAO {
	int addBean(DeviceDustbin bean);
	
	DeviceDustbin queryBean(String deviceId);
	
	ArrayList<DeviceDustbin> queryAllBeanForDay();
	
	int updateBean(DeviceDustbin bean);
	
	List<DeviceDustbin> selectLastByDeviceIds(List<String> deviceIds);
}