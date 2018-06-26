package com.glodio.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glodio.bean.DeviceHggas;
import com.glodio.bean.User;
import com.glodio.bean.vo.DeviceHggasVo;
import com.glodio.service.DeviceHggasService;
import com.glodio.util.StringUtil;

@Controller
@RequestMapping("/system/deviceHggasController")
public class DeviceHggasController {
	
	@Autowired
	private DeviceHggasService deviceHggasService;
	
	@Autowired
	private HttpServletRequest request;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/findList",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> findList(@RequestParam(defaultValue="1") Integer pageNumber,
			@RequestParam(defaultValue="10") Integer pageSize,String startTime,String endTime,Integer alarmStatus ){
		Map<String,Object> resultMap = new HashMap<>();
		PageHelper.startPage(pageNumber,  pageSize);
		PageHelper.orderBy("id desc");
		User user = getUser(request);
		if(user == null){
			return resultMap;
		}
		Date DstartTime = convertTime(startTime);
		Date DendTime = convertTime(endTime);
	    List<DeviceHggas> list = deviceHggasService.selectListByQuery(user.getOrgId(), DstartTime, DendTime, alarmStatus);
	    List<DeviceHggasVo> dataList = new ArrayList<>();
	    if(list != null){
	    	DeviceHggasVo hgvo = null;
	    	for(DeviceHggas bean : list){
	    		hgvo = new DeviceHggasVo();
	    		hgvo.setId(bean.getId());
	    		hgvo.setDeviceId(bean.getDeviceId());
	    		hgvo.setIccid(bean.getIccid());
	    		hgvo.setElectricity(bean.getElectricity());
	    		hgvo.setReportTime(bean.getReportTime());
	    		hgvo.setSignal(bean.getDeviceSignal());
	    		hgvo.setDeviceName(bean.getDeviceName());
	    		hgvo.setImsi(bean.getImsi());
	    		if(bean.getAlarmStatus() == 0){  
	    			hgvo.setAlarmStatus("正常");	
	    		}else if(bean.getAlarmStatus() == 1){
	    			hgvo.setAlarmStatus("告警");
	    		}	
	
	    		dataList.add(hgvo);
	    	}
	    }
	       
	    long total = 0;
	    if(list!=null){
	    	total =  ((Page) list).getPages(); 
	    }
	    resultMap.put("total", total);
	    resultMap.put("list", dataList);
	    return resultMap;
	}
	
	private User getUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user;
	}
	
	private Date convertTime(String time){
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(!StringUtil.strIsNullOrEmpty(time)){
			try {
				date = simpleDateFormat.parse(time);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
		
	}
}
