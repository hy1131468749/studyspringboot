package com.glodio.serviceimpl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glodio.bean.ConnectPlatform;
import com.glodio.bean.ConnectType;
import com.glodio.dao.ConnectTypeMapper;
import com.glodio.service.ConnectTypeService;
import com.glodio.util.StringUtil;

@Service
@Transactional
public class ConnectTypeServiceImpl implements ConnectTypeService {
     
	@Autowired
    private ConnectTypeMapper connectTypeMapper;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		connectTypeMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public boolean insert(ConnectType record) {
		if(StringUtil.strIsNullOrEmpty(record.getTypeName())){
		    return false;
		}
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		connectTypeMapper.insert(record);
		return true;
		
	}

	@Override
	public boolean insertSelective(ConnectType record) {
		if(StringUtil.strIsNullOrEmpty(record.getTypeName())){
		    return false;
		}
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		connectTypeMapper.insert(record);
		return true;
	}
    
	@Override
	@Transactional(readOnly = true)
	public ConnectType selectByPrimaryKey(Integer id) {
		return connectTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(ConnectType record) {
		if(StringUtil.strIsNullOrEmpty(record.getTypeName())){
		    return false;
		}
		record.setUpdateTime(new Date());
		connectTypeMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public boolean updateByPrimaryKey(ConnectType record) {
		if(StringUtil.strIsNullOrEmpty(record.getTypeName())){
		    return false;
		}
		record.setUpdateTime(new Date());
		connectTypeMapper.updateByPrimaryKey(record);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConnectType> selectList() {
		return connectTypeMapper.selectList();
	}

	
	
	

  
}