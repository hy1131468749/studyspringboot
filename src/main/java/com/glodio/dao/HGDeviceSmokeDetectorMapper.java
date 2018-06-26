package com.glodio.dao;

import java.util.List;

import com.glodio.bean.HGDeviceSmokeDetector;

public interface HGDeviceSmokeDetectorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HGDeviceSmokeDetector record);

    int insertSelective(HGDeviceSmokeDetector record);

    HGDeviceSmokeDetector selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HGDeviceSmokeDetector record);

    int updateByPrimaryKey(HGDeviceSmokeDetector record);

	List<HGDeviceSmokeDetector> selectListByOrgId(Integer orgId);
}