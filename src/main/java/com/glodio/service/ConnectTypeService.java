package com.glodio.service;

import java.util.List;

import com.glodio.bean.ConnectPlatform;
import com.glodio.bean.ConnectType;

public interface ConnectTypeService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(ConnectType record);

    boolean insertSelective(ConnectType record);

    ConnectType selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(ConnectType record);

    boolean updateByPrimaryKey(ConnectType record);
    
    List<ConnectType> selectList();
}