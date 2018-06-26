package com.glodio.serviceimpl;


import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.glodio.bean.ConnectPlatform;
import com.glodio.dao.IConnectPlatformDao;
import com.glodio.service.IConnectPlatformService;
import com.glodio.util.StringUtil;

@Service
@Transactional
public class ConnectPlatformServiceImpl implements IConnectPlatformService {
    
	@Resource
	private IConnectPlatformDao connectPlatformDao;
	 
	@Override
	public boolean deleteByPrimaryKey(Long id) {
		 connectPlatformDao.deleteByPrimaryKey(id); 
	     return true; 
	}

	@Override
	public boolean insert(ConnectPlatform record) {
		if(record == null){
		    return false;
		}
		if(!record.validateData())
			return false;
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		connectPlatformDao.insert(record);
		return true; 
		
	}

	@Override
	public boolean insertSelective(ConnectPlatform record) {
		if(record == null)
			return false;
		if(!record.validateData())
			return false;
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		connectPlatformDao.insertSelective(record);
		return true;
	}
    
	@Transactional(readOnly = true)  
	@Override
	public ConnectPlatform selectByPrimaryKey(Long id) {
		return connectPlatformDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(ConnectPlatform record) {
		if( record == null )
			return false;
		record.setUpdateTime(new Date());
		connectPlatformDao.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public  boolean updateByPrimaryKey(ConnectPlatform record) {
		if(record == null){
			return false;
		}
		if(!record.validateData())
			return false;
		record.setUpdateTime(new Date());
		connectPlatformDao.updateByPrimaryKey(record);
		return true;
	}
	
	@Transactional(readOnly = true)  
	@Override
	public List<ConnectPlatform> selectList() {
     	return connectPlatformDao.selectList();
	}
	
	@Transactional(readOnly = true)  
	@Override
	public List<ConnectPlatform> selectListByTypeId(Integer typeId) {
		return connectPlatformDao.selectListByTypeId(typeId);
	}
	
	@Transactional(readOnly = true)  
	@Override
	public List<ConnectPlatform> selectAllList() {
		return connectPlatformDao.selectAllList();
	}
  
}