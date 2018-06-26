package com.glodio.dao;

import java.util.ArrayList;

import com.glodio.bean.KeyValue;

public interface IKeyValueDAO {
	ArrayList<KeyValue> queryBeansForAll();
}