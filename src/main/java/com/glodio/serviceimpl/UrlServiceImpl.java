package com.glodio.serviceimpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import com.glodio.bean.Url;
import com.glodio.controller.IndexController;
import com.glodio.dao.UrlMapper;
import com.glodio.service.UrlService;
import com.glodio.util.StringUtil;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {
    
	@Autowired 
	private UrlMapper urlMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		Long countId = urlMapper.selectCountParentId(id);
		if(countId > 0){
			return false;
		}
        Url url = urlMapper.selectByPrimaryKey(id);
        if(url == null){
        	logger.error("数据不存在");
        	return false;
        }    
		urlMapper.deleteByPrimaryKey(id);
	    if(url.getParentId() != null){
	    	long count = urlMapper.selectCountParentId(url.getParentId());
	    	if(count == 0){
	    		Url parentUrl = urlMapper.selectByPrimaryKey(url.getParentId());
	    	    if(parentUrl == null){
	    	    	logger.error("Url中不存在id:"+url.getParentId()+"数据");
	    	    	return true;
	    	    }
	    	    parentUrl.setIsCatalog(0);
	    	    urlMapper.updateByPrimaryKey(parentUrl);
	    	}
	    		
	    }
		return true;
	}

	@Override
	public boolean insert(Url record) {
		urlMapper.insert(record);
		return true;
	}

	@Override
	public boolean insertSelective(Url record) throws Exception{
		if(StringUtil.strIsNullOrEmpty(record.getUrlAddr()) || StringUtil.strIsNullOrEmpty(record.getUrlName())
				|| StringUtil.strIsNullOrEmpty(record.getIconName())){
			return false;
		}
		record.setCreateTime(new Date());
		record.setModifyTime(new Date());
		record.setIsCatalog(0);
		if(record.getParentId() == null){
			record.setParentId(0);
			record.setCatalogLevel(1);
		}else{
			Url parent = urlMapper.selectByPrimaryKey(record.getParentId());
			if(parent == null){
				throw new  Exception("找不到父节点");
			}
			record.setCatalogLevel(parent.getCatalogLevel()+1);
			if(0 == parent.getIsCatalog()){
				parent.setIsCatalog(1);
				urlMapper.updateByPrimaryKey(parent);
			}
		}
		urlMapper.insertSelective(record);
		return true;
	}
    
	@Transactional(readOnly = true)
	@Override
	public Url selectByPrimaryKey(Integer id) {
		return urlMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(Url record) {
		urlMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public boolean updateByPrimaryKey(Url record) {
		urlMapper.updateByPrimaryKey(record);
		return true;
	}
    
	@Transactional(readOnly = true)
	@Override
	public List<Url> selectList() {
		return urlMapper.selectList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Url> selectListByparentId(Integer parentId) {
		return urlMapper.selectListByparentId(parentId);
	}

	@Override
	public Long selectCountParentId(Integer parentId) {
		
		return urlMapper.selectCountParentId(parentId);
	}

	@Override
	public List<Url> selectListByIds(List<Integer> ids) {
		
		return urlMapper.selectListByIds(ids);
	}
	
}
