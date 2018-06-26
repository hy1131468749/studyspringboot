package com.glodio.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.glodio.bean.DeviceSmokeDetector;
import com.glodio.dao.DeviceSmokeDetectorMapper;
import com.glodio.service.DeviceSmokeDetectorService;

@Service
@Transactional
public class DeviceSmokeDetectorServiceImpl implements DeviceSmokeDetectorService {
    
	@Autowired
	private DeviceSmokeDetectorMapper deviceSmokeDetectorMapper;

	@Override
	public boolean deleteByPrimaryKey(Long id) {
		deviceSmokeDetectorMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public boolean insert(DeviceSmokeDetector record) {
		deviceSmokeDetectorMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(DeviceSmokeDetector record) {
		deviceSmokeDetectorMapper.insertSelective(record);
		return true;
	}

	@Override
	public DeviceSmokeDetector selectByPrimaryKey(Long id) {
		return deviceSmokeDetectorMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(DeviceSmokeDetector record) {
		deviceSmokeDetectorMapper.updateByPrimaryKey(record);
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(DeviceSmokeDetector record) {
		deviceSmokeDetectorMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	public List<DeviceSmokeDetector> selectByDeviceIds(List<String> deviceIds) {
		if(deviceIds == null)
			return null;
		return deviceSmokeDetectorMapper.selectByDeviceIds(deviceIds);
	}

	@Override
	public DeviceSmokeDetector selectByDeviceId(String deviceId) {
		return deviceSmokeDetectorMapper.selectByDeviceId(deviceId);
	}

	
   
}