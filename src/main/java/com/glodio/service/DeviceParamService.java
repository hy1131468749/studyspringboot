package com.glodio.service;

import java.util.List;

import com.glodio.bean.DeviceParam;

public interface DeviceParamService {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(DeviceParam record);

    boolean insertSelective(DeviceParam record);

    DeviceParam selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(DeviceParam record);

    boolean updateByPrimaryKey(DeviceParam record);
    
    DeviceParam selectByDeviceId(String deviceId);
    
    boolean updateByIdAndTypeselective(DeviceParam record);
    
    DeviceParam selectByDeviceIdAndType(String deviceId,Integer type);
    
    DeviceParam  selectByLikeFlow(String flow);
    
    List<DeviceParam> selectListByDeviceIds (List<String> deviceIds);
}