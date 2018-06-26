package com.glodio.dao;

import java.util.List;

import com.glodio.bean.DeviceEmgd;

public interface DeviceEmgdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceEmgd record);

    int insertSelective(DeviceEmgd record);

    DeviceEmgd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceEmgd record);

    int updateByPrimaryKey(DeviceEmgd record);
    
    DeviceEmgd selectByDeviceId(String deviceId);
    
    List<DeviceEmgd> selectByDeviceIds(List<String> deviceIds);
    
    List<DeviceEmgd> selectLastByDeviceIds(List<String> deviceIds);
}