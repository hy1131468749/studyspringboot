package com.glodio.dao;

import java.util.List;

import com.glodio.bean.ConnectType;


public interface ConnectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConnectType record);

    int insertSelective(ConnectType record);

    ConnectType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConnectType record);

    int updateByPrimaryKey(ConnectType record);
    
    List<ConnectType> selectList();
}