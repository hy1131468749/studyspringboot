package com.glodio.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glodio.bean.DeviceHggas;
import com.glodio.bean.HGDeviceSmokeDetector;
import com.glodio.dao.DeviceHggasMapper;
import com.glodio.service.DeviceHggasService;

@Service
@Transactional
public class DeviceHggasServiceImpl implements DeviceHggasService  {
   
	@Autowired
	private DeviceHggasMapper deviceHggasMapper;

	@Override
	public boolean deleteByPrimaryKey(Long id) {
		deviceHggasMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public boolean insert(DeviceHggas record) {
		deviceHggasMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(DeviceHggas record) {
		deviceHggasMapper.insertSelective(record);
		return true;
	}

	@Override
	public DeviceHggas selectByPrimaryKey(Long id) {
		
		return deviceHggasMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(DeviceHggas record) {
		deviceHggasMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public boolean updateByPrimaryKey(DeviceHggas record) {
		deviceHggasMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	public List<DeviceHggas> selectListByOrgId(Integer orgId) {
		return deviceHggasMapper.selectListByOrgId(orgId);
	}

	@Override
	public List<DeviceHggas> selectLastByDeviceIds(List<String> deviceIds) {
		return deviceHggasMapper.selectLastByDeviceIds(deviceIds);
	}

	@Override
	public List<DeviceHggas> selectListByQuery(Integer orgId, Date startTime, Date endTime, Integer alarmStatus) {
		Map<String,Object> params = new HashMap<>();
		params.put("orgId", orgId);
		if(startTime !=null && endTime!=null){
			params.put("startTime", startTime);
			params.put("endTime", endTime);
		}
		if(alarmStatus !=null ){
			params.put("alarmStatus", alarmStatus);
		}
		return deviceHggasMapper.selectListByQuery(params);
	}

   
}