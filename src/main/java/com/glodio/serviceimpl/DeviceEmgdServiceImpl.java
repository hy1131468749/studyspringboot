package com.glodio.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glodio.bean.DeviceEmgd;
import com.glodio.dao.DeviceEmgdMapper;
import com.glodio.service.DeviceEmgdService;
@Service
@Transactional
public class DeviceEmgdServiceImpl implements DeviceEmgdService {
    
	@Autowired
	private DeviceEmgdMapper deviceEmgdMapper;

	@Override
	public boolean deleteByPrimaryKey(Long id) {
        deviceEmgdMapper.deleteByPrimaryKey(id);
        return true;
	}

	@Override
	public boolean insert(DeviceEmgd record) {
		if(record.getReportTime() == null)
			record.setReportTime(new Date());
		deviceEmgdMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(DeviceEmgd record) {
		if(record.getReportTime() == null)
			record.setReportTime(new Date());
		deviceEmgdMapper.insertSelective(record);
		return false;
	}

	@Override
	public DeviceEmgd selectByPrimaryKey(Long id) {
		return deviceEmgdMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(DeviceEmgd record) {
		 deviceEmgdMapper.updateByPrimaryKeySelective(record);
		 return true;
	}

	@Override
	public boolean updateByPrimaryKey(DeviceEmgd record) {
		deviceEmgdMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	public DeviceEmgd selectByDeviceId(String deviceId) {
		return deviceEmgdMapper.selectByDeviceId(deviceId);
	}

	@Override
	public List<DeviceEmgd> selectByDeviceIds(List<String> deviceIds) {
		if(deviceIds == null || deviceIds.isEmpty())
			return null;
		return deviceEmgdMapper.selectByDeviceIds(deviceIds);
	}

	@Override
	public List<DeviceEmgd> selectLastByDeviceIds(List<String> deviceIds) {
		
		return deviceEmgdMapper.selectByDeviceIds(deviceIds);
	}
	
    
}