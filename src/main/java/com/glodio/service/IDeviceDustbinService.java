package com.glodio.service;

import java.util.ArrayList;
import java.util.List;

import com.glodio.bean.DeviceDustbin;
import com.glodio.bean.device.GCDataBean;

public interface IDeviceDustbinService {
	int addBean(DeviceDustbin bean);
	
    int updateBean(DeviceDustbin bean);
    
    DeviceDustbin queryBean(String deviceId);
    
	ArrayList<DeviceDustbin> queryAllBeanForDay();
	
	boolean dustbinDataProcess(GCDataBean gcDataBean);
	
	List<DeviceDustbin> selectLastByDeviceIds(List<String> deviceIds);
    
}
