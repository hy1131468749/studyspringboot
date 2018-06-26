package com.glodio.service;

import java.util.List;

import com.glodio.bean.DeviceEmgd;
import com.glodio.bean.DeviceSmokeDetector;

public interface DeviceSmokeDetectorService {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(DeviceSmokeDetector record);

    boolean insertSelective(DeviceSmokeDetector record);

    DeviceSmokeDetector selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(DeviceSmokeDetector record);

    boolean updateByPrimaryKey(DeviceSmokeDetector record);
    
    List<DeviceSmokeDetector> selectByDeviceIds(List<String> deviceIds);
    
    DeviceSmokeDetector selectByDeviceId(String deviceId);
}