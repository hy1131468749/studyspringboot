package com.glodio.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glodio.bean.DeviceParam;
import com.glodio.dao.DeviceParamMapper;
import com.glodio.service.DeviceParamService;

@Service
@Transactional
public class DeviceParamServiceImpl implements DeviceParamService {

	@Autowired
	private DeviceParamMapper deviceParamMapper;
	
	@Override
	public boolean deleteByPrimaryKey(Long id) {
		deviceParamMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public boolean insert(DeviceParam record) {
		deviceParamMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(DeviceParam record) {
		deviceParamMapper.insertSelective(record);
		return true;
	}

	@Override
	public DeviceParam selectByPrimaryKey(Long id) {
	
		return 	deviceParamMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(DeviceParam record) {
		deviceParamMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public boolean updateByPrimaryKey(DeviceParam record) {
		deviceParamMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	public DeviceParam selectByDeviceId(String deviceId) {
		
		return deviceParamMapper.selectByDeviceId(deviceId);
	}

	@Override
	public boolean updateByIdAndTypeselective(DeviceParam record) {
		deviceParamMapper.updateByIdAndTypeselective(record);
		return true;
	}

	@Override
	public DeviceParam selectByDeviceIdAndType(String deviceId, Integer type) {
		Map<String,Object> map = new HashMap<>();
		map.put("deviceId", deviceId);
		map.put("type", type);
		return deviceParamMapper.selectByDeviceIdAndType(map);
	}

	@Override
	public DeviceParam selectByLikeFlow(String flow) {
		List<DeviceParam> deviceParams = deviceParamMapper.selectByLikeFlow(flow);
		if(deviceParams !=null && !deviceParams.isEmpty()){
			return deviceParams.get(0);
		}else{
			return null;
		}
			
	}

	@Override
	public List<DeviceParam> selectListByDeviceIds(List<String> deviceIds) {
		
		return deviceParamMapper.selectListByDeviceIds(deviceIds);
	}
    
}