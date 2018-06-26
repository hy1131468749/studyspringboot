package com.glodio.service;

import java.util.ArrayList;
import java.util.Map;

import com.glodio.bean.Device;

public interface IDeviceService {
    ArrayList<Device> queryAllBean();
    
    ArrayList<Device> queryBeansForDeviceType(int deviceType,int orgId);
    
    Device queryBean(String deviceId);

    int deleteBean(String deviceId);

    int addBean(Device record);

    int updateBean(Device record);
    
    ArrayList<Device> queryList(Map<String,Object> params);
    
    Device queryBeanByNbDeviceId(String nbDeviceId);
}
