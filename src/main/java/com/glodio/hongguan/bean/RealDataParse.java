package com.glodio.hongguan.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import com.glodio.util.HexUtilty;

import net.sf.json.JSONObject;

public class RealDataParse {
    public static String hexString2String(String s) {  
        if (s == null || s.equals("")) {  
            return "";  
        }  
        s = s.replace(" ", "");
        s = s.replace("\"", "");
        	
		Map<Integer, String> map = new HashMap<>();
        
        byte[] byteArray = new byte[2];
        for (int i = 0; i < s.length();) {
        	// 解码位大小
        	int b = 2;
        	// type字段解码
        	byteArray[0] = (byte) (0xff & Integer.parseInt(s.substring(i, i+b), 16));
        	byteArray[1] = (byte) (0xff & Integer.parseInt(s.substring(i+b, i+b+2), 16));
        	int type = getType(byteArray[0], byteArray[1]);
        	//System.out.println(byteArray[i]+" "+byteArray[i+1]);
        	b = 4;
        	// length字段解码
        	byteArray[0] = (byte) (0xff & Integer.parseInt(s.substring(i+b, i+b+2), 16));
        	byteArray[1] = (byte) (0xff & Integer.parseInt(s.substring(i+b+2,i+b+4), 16));
        	int length = getType(byteArray[0],byteArray[1]);
        	// 字段长度信息不用存
        	//lengthList.add(length);
        	b = 8;
        	// value字段解码
        	String value = "";
        	// 获取待解码的16进制字符串
        	String hexValue = s.substring(i+b, i+b+length*2);
        	//System.out.println("hexValue:"+hexValue);
        	
        	// 根据type类型为字符串处理   
        	// 1001 1003  1004 1005 1017 1018 9000 9999:字符串类型
        	if(type == 110 || type == 111 ||type == 1001 || 
        			type == 1003 || type == 1004 || type == 1005 ||
        				type == 1017 || type == 1018 || type == 9000 || type == 9999) {
              	byte[] bytesValue = HexUtilty.hexString2Bytes(hexValue);
            	value = new String(bytesValue);
        	}
        	else {// 类型为date or int处理
        		int time = Integer.parseInt(hexValue, 16);
        		value = String.valueOf(time);
        	}

        	map.put(type, value);
        	b = b+length*2;
        	// 更新i
        	i = i+b;
        	
        	//System.out.println("type:"+type+" "+"length:"+length+" "+"value:"+value);
        	//System.out.println("type:"+type+" "+"value:"+value);
        }
        
        // 组装成JSON字符串返回
        JSONObject json = new JSONObject();
        if(!map.containsKey(1015)) {
	        for(Entry<Integer, String> entry:map.entrySet()) {
	    		switch (entry.getKey()) {
	    		case 1003://imsi
	    			json.put("imsi", entry.getValue());
	    			break;
				case 1004:// imei
					json.put("deviceId", entry.getValue());
					break;
				case 1002:// 时间
					json.put("reportTime", entry.getValue());
					break;
				case 1007:// 电量
					json.put("electricity", entry.getValue());
					break;
				case 1006:// 信号强度
					json.put("signal", entry.getValue());
					break;
				case 1018:// 行业类别
					json.put("iccid", entry.getValue());
					break;
				case 4101:// 燃气报警状态
					json.put("alarmStatus", entry.getValue());
					break;
				default:
					break;
				}
	        }
	        
	        // 正常上报，设备告警状态为0，无告警
	        if(json.containsKey("deviceId") && (!json.containsKey("alarmStatus"))) {
	        	json.put("alarmStatus", "0");
	        }
        }
        else {
	        for(Entry<Integer, String> entry:map.entrySet()) {
	    		switch (entry.getKey()) {
				case 1015:// 指令流水
					json.put("flow", entry.getValue());
					break;
				case 1010:// 电量阀值
					json.put("electricityThreshold", entry.getValue());
					break;
				case 1008:// 采样频率
					json.put("sampleFrequency", entry.getValue());
					break;
				case 1009:// 上报频率
					json.put("reportFrequency", entry.getValue());
					break;
				case 1012:// 检测频率
					json.put("detectionFrequency", entry.getValue());
					break;
				case 1013:// 紧急采样频率
					json.put("urgentSampleFrequency", entry.getValue());
					break;
				case 1014:// 紧急上报频率
					json.put("urgentReportFrequency", entry.getValue());
					break;
				//添加自检信息
				case 1003://imsi
	    			json.put("imsi", entry.getValue());
	    			break;	
				case 1004:// imei
					json.put("deviceId", entry.getValue());
					break;	
				case 1006://信号强度
					json.put("signalIntensity", entry.getValue());
					break;
				case 1011://自检结果
					json.put("selfCheckResult", entry.getValue());
					break;		
				default:
					break;
				}
	        }
        }
        
     //  System.out.println("parse data:"+" "+json.toString());
        return json.toString();  
    }
    
	public static int getType(byte hByte,byte lByte) {
		return  ((hByte & 0xFF) << 8) | ((lByte & 0xFF) << 0);
	}
	
	
}
