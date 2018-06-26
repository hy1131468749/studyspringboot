package com.glodio.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.glodio.bean.ConnectPlatform;

public interface IConnectPlatformService {
	boolean deleteByPrimaryKey(Long id);

    boolean insert(ConnectPlatform record);

    boolean insertSelective(ConnectPlatform record);

    ConnectPlatform selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(ConnectPlatform record);

    boolean updateByPrimaryKey(ConnectPlatform record);
    
    List<ConnectPlatform> selectList();
    
    List<ConnectPlatform> selectListByTypeId(Integer typeId);

	List<ConnectPlatform> selectAllList();
}