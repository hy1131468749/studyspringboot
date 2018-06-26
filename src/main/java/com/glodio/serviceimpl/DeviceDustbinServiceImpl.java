package com.glodio.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glodio.bean.DeviceCover;
import com.glodio.bean.DeviceDustbin;
import com.glodio.bean.device.GCDataBean;
import com.glodio.dao.IDeviceDAO;
import com.glodio.dao.IDeviceDustbinDAO;
import com.glodio.service.IDeviceDustbinService;

@Service("iDeviceDustbinService")
public class DeviceDustbinServiceImpl implements IDeviceDustbinService {
	@Autowired
	IDeviceDAO iDeviceDAO;
	@Autowired
	IDeviceDustbinDAO iDeviceDustbinDAO;

	@Override
	public int addBean(DeviceDustbin bean) {
		int row = 0;
		try {
			row = iDeviceDustbinDAO.addBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public int updateBean(DeviceDustbin bean) {
		int row = 0;
		try {
			row = iDeviceDustbinDAO.updateBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public DeviceDustbin queryBean(String deviceId) {
		DeviceDustbin bean = null;
		try {
			bean = iDeviceDustbinDAO.queryBean(deviceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public ArrayList<DeviceDustbin> queryAllBeanForDay() {
		ArrayList<DeviceDustbin> list = null;
		try {
			list = iDeviceDustbinDAO.queryAllBeanForDay();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean dustbinDataProcess(GCDataBean gcDataBean) {
		boolean bResult = false;
		DeviceDustbin bean = null;
		
		if(gcDataBean == null) {
			return bResult;
		}
		
		if(gcDataBean.getSN() != null) {
			//查询设备对象是否存在
			if(iDeviceDAO.queryBean(gcDataBean.getSN()) != null) {
				//查询数据对象
				/*DeviceDustbin queryBean = queryBean(gcDataBean.getSN());
				if(queryBean != null) {
					queryBean.setReportTime(new Date(System.currentTimeMillis()));
					queryBean.setReportDay(new Date(System.currentTimeMillis()));
					queryBean.setFullLeft(Integer.parseInt(gcDataBean.getDT().getFL()) == 1 ? "满" : "未满");
					queryBean.setFullRight(Integer.parseInt(gcDataBean.getDT().getFR()) == 1 ? "满" : "未满");
					queryBean.setSmokeStatus(Integer.parseInt(gcDataBean.getDT().getSS()) == 1 ? "有" : "无");
					queryBean.setObliquityStatus(Integer.parseInt(gcDataBean.getDT().getBS()) == 1 ? "异常" : "正常");
					//更新保存
					if(updateBean(queryBean) == 1) {
						bResult = true;
					}				
				}
				else {// 新保存设备数据
*/					bean = new DeviceDustbin();
					bean.setDeviceId(gcDataBean.getSN());
					bean.setReportTime(new Date(System.currentTimeMillis()));
					bean.setReportDay(new Date(System.currentTimeMillis()));
					bean.setFullLeft(Integer.parseInt(gcDataBean.getDT().getFL()) == 1 ? "满" : "未满");
					bean.setFullRight(Integer.parseInt(gcDataBean.getDT().getFR()) == 1 ? "满" : "未满");
					bean.setSmokeStatus(Integer.parseInt(gcDataBean.getDT().getSS()) == 1 ? "有" : "无");
					bean.setObliquityStatus(Integer.parseInt(gcDataBean.getDT().getBS()) == 1 ? "异常" : "正常");
					//新增保存
					if(addBean(bean) == 1) {
						bResult = true;
					}
				
			}
		}
		
		return bResult;
	}

	@Override
	public List<DeviceDustbin> selectLastByDeviceIds(List<String> deviceIds) {
		
		return iDeviceDustbinDAO.selectLastByDeviceIds(deviceIds);
	}

}
