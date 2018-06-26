package com.glodio.dao;

import com.glodio.bean.Testdata;

public interface TestdataMapper {
    int insert(Testdata record);

    int insertSelective(Testdata record);
}