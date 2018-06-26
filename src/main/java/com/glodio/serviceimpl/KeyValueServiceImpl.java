package com.glodio.serviceimpl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glodio.bean.KeyValue;
import com.glodio.dao.IKeyValueDAO;
import com.glodio.service.IKeyValueService;

@Service("iKeyValueService")
public class KeyValueServiceImpl implements IKeyValueService{
	@Resource
	IKeyValueDAO iKeyValueDAO;

	@Override
	public ArrayList<KeyValue> queryBeansForAll() {
		ArrayList<KeyValue> list = null;
		
		try {
			list = iKeyValueDAO.queryBeansForAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
