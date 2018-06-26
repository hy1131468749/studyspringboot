package com.glodio.service;

import java.util.List;

import com.glodio.bean.Url;

public interface UrlService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Url record);

    boolean insertSelective(Url record) throws Exception;

    Url selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Url record);

    boolean updateByPrimaryKey(Url record);
    
    List<Url> selectList();
    
    List<Url> selectListByparentId(Integer parentId);
    
    Long selectCountParentId(Integer parentId);
    
    List<Url> selectListByIds(List<Integer> ids );
}