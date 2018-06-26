package com.glodio.dao;

import java.util.List;
import java.util.Map;

import com.glodio.bean.DeviceParam;

public interface DeviceParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceParam record);

    int insertSelective(DeviceParam record);

    DeviceParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceParam record);

    int updateByPrimaryKey(DeviceParam record);
    
    DeviceParam selectByDeviceId(String deviceId);
    
    int updateByIdAndTypeselective(DeviceParam record);
    
    DeviceParam selectByDeviceIdAndType(Map<String,Object> params);

    List<DeviceParam>  selectByLikeFlow(String flow);
    
    List<DeviceParam> selectListByDeviceIds (List<String> deviceIds);
   
}