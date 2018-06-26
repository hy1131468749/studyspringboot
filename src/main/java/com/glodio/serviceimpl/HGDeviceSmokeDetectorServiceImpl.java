package com.glodio.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.glodio.bean.HGDeviceSmokeDetector;
import com.glodio.dao.HGDeviceSmokeDetectorMapper;
import com.glodio.service.HGDeviceSmokeDetectorService;

@Service
@Transactional
public class HGDeviceSmokeDetectorServiceImpl implements HGDeviceSmokeDetectorService  {
   
    @Autowired 
	private  HGDeviceSmokeDetectorMapper hGDeviceSmokeDetectorMapper;

	@Override
	public boolean deleteByPrimaryKey(Long id) {
		hGDeviceSmokeDetectorMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public boolean insert(HGDeviceSmokeDetector record) {
		hGDeviceSmokeDetectorMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(HGDeviceSmokeDetector record) {
		hGDeviceSmokeDetectorMapper.insertSelective(record);
		return true;
	}

	@Override
	public HGDeviceSmokeDetector selectByPrimaryKey(Long id) {
		return hGDeviceSmokeDetectorMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(HGDeviceSmokeDetector record) {
		hGDeviceSmokeDetectorMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public boolean updateByPrimaryKey(HGDeviceSmokeDetector record) {
		hGDeviceSmokeDetectorMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	public List<HGDeviceSmokeDetector> selectListByOrgId(Integer orgId) {
		
		return hGDeviceSmokeDetectorMapper.selectListByOrgId(orgId);
	}

	
	
	
   
}