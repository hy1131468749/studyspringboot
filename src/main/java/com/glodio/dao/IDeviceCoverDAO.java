package com.glodio.dao;

import java.util.ArrayList;
import java.util.List;

import com.glodio.bean.DeviceCover;

public interface IDeviceCoverDAO {
	int addBean(DeviceCover bean);
	
	DeviceCover queryBean(String deviceId);
	
	ArrayList<DeviceCover> queryAllBeanForDay();
	
	int updateBean(DeviceCover bean);
	
	List<DeviceCover> selectLastByDeviceIds(List<String> deviceIds);
}