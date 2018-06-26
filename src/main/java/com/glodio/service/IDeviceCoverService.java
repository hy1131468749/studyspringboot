package com.glodio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glodio.bean.DeviceCover;
import com.glodio.bean.device.MCDataBean;

public interface IDeviceCoverService {
	int addBean(DeviceCover bean);
	
    int updateBean(DeviceCover bean);
    
    DeviceCover queryBean(String deviceId);
    
	ArrayList<DeviceCover> queryAllBeanForDay();
	
	boolean coverDataProcess(MCDataBean mcDataBean);
	
	List<DeviceCover> selectLastByDeviceIds(List<String> deviceIds);
    
}
