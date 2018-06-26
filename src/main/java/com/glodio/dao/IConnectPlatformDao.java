package com.glodio.dao;

import java.util.List;

import com.glodio.bean.ConnectPlatform;

public interface IConnectPlatformDao {
    int deleteByPrimaryKey(Long id);

    int insert(ConnectPlatform record);

    int insertSelective(ConnectPlatform record);

    ConnectPlatform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConnectPlatform record);

    int updateByPrimaryKey(ConnectPlatform record);
    
    List<ConnectPlatform> selectList();
    
    List<ConnectPlatform> selectListByTypeId(Integer typeId);
    
    List<ConnectPlatform> selectAllList();
    
}