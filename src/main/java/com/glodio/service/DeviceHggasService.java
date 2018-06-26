package com.glodio.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.glodio.bean.DeviceHggas;
import com.glodio.bean.HGDeviceSmokeDetector;

public interface DeviceHggasService {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(DeviceHggas record);

    boolean insertSelective(DeviceHggas record);

    DeviceHggas selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(DeviceHggas record);

    boolean updateByPrimaryKey(DeviceHggas record);
    
    List<DeviceHggas> selectListByOrgId(Integer orgId);
    
    List<DeviceHggas> selectLastByDeviceIds(List<String> deviceIds);
    
    List<DeviceHggas> selectListByQuery(Integer orgId,Date startTime ,Date endTime ,Integer alarmStatus);
}