package com.glodio.service;

import java.util.List;

import com.glodio.bean.HGDeviceSmokeDetector;

public interface HGDeviceSmokeDetectorService {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(HGDeviceSmokeDetector record);

    boolean insertSelective(HGDeviceSmokeDetector record);

    HGDeviceSmokeDetector selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(HGDeviceSmokeDetector record);

    boolean updateByPrimaryKey(HGDeviceSmokeDetector record);

    List<HGDeviceSmokeDetector> selectListByOrgId(Integer orgId);
}