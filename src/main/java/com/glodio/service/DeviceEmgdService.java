package com.glodio.service;

import java.util.List;

import com.glodio.bean.DeviceEmgd;

public interface DeviceEmgdService {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(DeviceEmgd record);

    boolean insertSelective(DeviceEmgd record);

    DeviceEmgd selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(DeviceEmgd record);

    boolean updateByPrimaryKey(DeviceEmgd record);
    
    DeviceEmgd selectByDeviceId(String deviceId);
    
    List<DeviceEmgd> selectByDeviceIds(List<String> deviceIds);
    
    List<DeviceEmgd> selectLastByDeviceIds(List<String> deviceIds);
}