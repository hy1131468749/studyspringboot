package com.glodio.dao;

import java.util.List;

import com.glodio.bean.Url;

public interface UrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Url record);

    int insertSelective(Url record);

    Url selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Url record);

    int updateByPrimaryKey(Url record);
    
    List<Url> selectList();
    
    List<Url> selectListByparentId(Integer parentId);
    
    Long selectCountParentId(Integer parentId);
    
    List<Url> selectListByIds(List<Integer> ids );
}