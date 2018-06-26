package com.glodio.dao;

import java.util.List;

import com.glodio.bean.DeviceSmokeDetector;

public interface DeviceSmokeDetectorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceSmokeDetector record);

    int insertSelective(DeviceSmokeDetector record);

    DeviceSmokeDetector selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceSmokeDetector record);

    int updateByPrimaryKey(DeviceSmokeDetector record);
    
    List<DeviceSmokeDetector> selectByDeviceIds(List<String> deviceIds);
    
    DeviceSmokeDetector selectByDeviceId(String deviceId);
}