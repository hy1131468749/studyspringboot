package com.glodio.dao;

import java.util.List;
import java.util.Map;

import com.glodio.bean.DeviceHggas;

public interface DeviceHggasMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceHggas record);

    int insertSelective(DeviceHggas record);

    DeviceHggas selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceHggas record);

    int updateByPrimaryKey(DeviceHggas record);
    
    List<DeviceHggas> selectListByOrgId(Integer orgId);
    
    List<DeviceHggas> selectLastByDeviceIds(List<String> deviceIds);
    
    List<DeviceHggas> selectListByQuery(Map<String,Object> params);
}