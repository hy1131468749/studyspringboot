package com.glodio.dao;

import java.util.ArrayList;
import java.util.Map;
import com.glodio.bean.Device;

public interface IDeviceDAO {
    ArrayList<Device> queryAllBean();
    
    ArrayList<Device> queryBeansForDeviceType(Map<String,Object> params);
    
    Device queryBean(String deviceId);

    int deleteBean(String deviceId);

    int addBean(Device record);

    int updateBean(Device record);
    
    ArrayList<Device> queryList(Map<String,Object> params);
    
    Device queryBeanByNbDeviceId(String nbDeviceId);
    
}