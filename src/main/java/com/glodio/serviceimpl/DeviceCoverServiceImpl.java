package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glodio.bean.DeviceCover;
import com.glodio.bean.device.MCDataBean;
import com.glodio.dao.IDeviceCoverDAO;
import com.glodio.dao.IDeviceDAO;
import com.glodio.service.IDeviceCoverService;

@Service("iDeviceCoverService")
public class DeviceCoverServiceImpl implements IDeviceCoverService {
	@Resource
	IDeviceCoverDAO iDeviceCoverDAO;
	@Resource
	IDeviceDAO iDeviceDAO;

	@Override
	public int addBean(DeviceCover bean) {
		int row = 0;
		
		try {
			row = iDeviceCoverDAO.addBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}

	
	@Override
	public int updateBean(DeviceCover bean) {
		int row = 0;
		
		try {
			row = iDeviceCoverDAO.updateBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}

	@Override
	public DeviceCover queryBean(String deviceId) {
		DeviceCover bean = null;
		
		try {
			bean = iDeviceCoverDAO.queryBean(deviceId);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return bean;
	}

	@Override
	public ArrayList<DeviceCover> queryAllBeanForDay() {
		ArrayList<DeviceCover> list = null;
		
		try {
			list = iDeviceCoverDAO.queryAllBeanForDay();
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return list;
	}

	/**
	 * 井盖数据进行处理
	 * 先查询当前设备数据库中是否已有数据，有数据，则更新
	 * 无数据，则新建
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean coverDataProcess(MCDataBean mcDataBean) {
		boolean bResult = false;
		DeviceCover bean = null;
		
		if(mcDataBean == null) {
			return bResult;
		}
		
		if(mcDataBean.getSN() != null) {
			//查询设备对象是否存在
			if(iDeviceDAO.queryBean(mcDataBean.getSN()) != null) {
				//查询数据对象
				DeviceCover queryBean = queryBean(mcDataBean.getSN());
				/*if(queryBean != null) {
					queryBean.setReportTime(new Date(System.currentTimeMillis()));
					queryBean.setReportDay(new Date(System.currentTimeMillis()));
					queryBean.setIsOpen((Integer.parseInt(mcDataBean.getDATAS().getData7()) == 1 || 
							Integer.parseInt(mcDataBean.getDATAS().getData10()) == 1 ) ? "告警" : "正常");
					queryBean.setWaterLevel(Integer.parseInt(mcDataBean.getDATAS().getData6()) == 1 ? "告警" : "正常");
					if(updateBean(queryBean) == 1) {
						bResult = true;
					}				
				}
				else {// 新保存设备数据
*/					bean = new DeviceCover();
					bean.setDeviceId(mcDataBean.getSN());
					bean.setIsOpen((Integer.parseInt(mcDataBean.getDATAS().getData7()) == 1 || 
									Integer.parseInt(mcDataBean.getDATAS().getData10()) == 1 ) ? "告警" : "正常");
					bean.setWaterLevel(Integer.parseInt(mcDataBean.getDATAS().getData6()) == 1 ? "告警" : "正常");
					bean.setReportTime(new Date(System.currentTimeMillis()));
					bean.setReportDay(new Date(System.currentTimeMillis()));
					
					if(addBean(bean) == 1) {
						bResult = true;
					
				}
			}
		}
		
		return bResult;
	}


	@Override
	public List<DeviceCover> selectLastByDeviceIds(List<String> deviceIds) {
		
		return iDeviceCoverDAO.selectLastByDeviceIds(deviceIds);
	}

}
