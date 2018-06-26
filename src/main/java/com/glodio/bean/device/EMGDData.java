package com.glodio.bean.device;

import java.util.Date;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EMGDData {
	public EMGDData() {
		
	}
	@JsonProperty("deviceName")
	private String deviceName;
    @JsonProperty("SN")
	private String SN;
    @JsonProperty("isOnline")
	private String isOnline;
    @JsonProperty("reportTime")
	private Date reportTime;
    @JsonProperty("deviceId")
   	private String deviceId;
	
	// 纬度
    @JsonProperty("lat")
	private String lat;
	// 经度
    @JsonProperty("lng")
	private String lng;
	
	// 温度
    @JsonProperty("TMP")
	private EMGDDataValue TMP;
	// 湿度
    @JsonProperty("HUN")
	private EMGDDataValue HUN;
	// PM2.5
    @JsonProperty("P25")
	private EMGDDataValue P25;
	// PM10
    @JsonProperty("PM10")
	private EMGDDataValue PM10;
	// 二氧化硫
    @JsonProperty("SO2")
	private EMGDDataValue SO2;
	// 二氧化氮
    @JsonProperty("NO2")
	private EMGDDataValue NO2;
	// 一氧化碳
    @JsonProperty("CO")
	private EMGDDataValue CO;
	// 臭氧
    @JsonProperty("O3")
	private EMGDDataValue O3;
	// 风速
    @JsonProperty("WS")
	private EMGDDataValue WS;
	// 风向
    @JsonProperty("WD")
	private EMGDDataValue WD;
	// 噪音
    @JsonProperty("NOS")
	private EMGDDataValue NOS;
	// 水温
    @JsonProperty("WT")
	private EMGDDataValue WT;
	// 溶氧量
    @JsonProperty("WO")
	private EMGDDataValue WO;
	// 浑浊度
    @JsonProperty("NTU")
	private EMGDDataValue NTU;
	// 电导率
    @JsonProperty("MS")
	private EMGDDataValue MS;
	// PH值
    @JsonProperty("PH")
	private EMGDDataValue PH;
	// 氨氮
    @JsonProperty("NH3-N")
	private EMGDDataValue NH3_N;
	// 化学需氧量
    @JsonProperty("COD")
	private EMGDDataValue COD;
	// 挥发性有机物
    @JsonProperty("VOCS")
	private EMGDDataValue VOCS;
	// 余氯，residual chlorine
    @JsonProperty("RC")
	private EMGDDataValue RC;
	// 总磷，total phosphorus
    @JsonProperty("TPH")
	private EMGDDataValue TPH;
	// 总磷，total phosphorus
    @JsonProperty("TN")
	private EMGDDataValue TN;
	// 叶绿素，chlorophyl
    @JsonProperty("CHL")
	private EMGDDataValue CHL;
	// 蓝绿藻，blue-green algae
    @JsonProperty("BGA")
	private EMGDDataValue BGA;
	// 水中油，Oil in water
    @JsonProperty("OIW")
	private EMGDDataValue OIW;
	// 数字ORP（氧化反应电位）
    @JsonProperty("ORP")
	private EMGDDataValue ORP;
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public EMGDDataValue getTMP() {
		return TMP;
	}
	public void setTMP(EMGDDataValue tMP) {
		TMP = tMP;
	}
	public EMGDDataValue getHUN() {
		return HUN;
	}
	public void setHUN(EMGDDataValue hUN) {
		HUN = hUN;
	}
	public EMGDDataValue getP25() {
		return P25;
	}
	public void setP25(EMGDDataValue p25) {
		P25 = p25;
	}
	public EMGDDataValue getPM10() {
		return PM10;
	}
	public void setPM10(EMGDDataValue pM10) {
		PM10 = pM10;
	}
	public EMGDDataValue getSO2() {
		return SO2;
	}
	public void setSO2(EMGDDataValue sO2) {
		SO2 = sO2;
	}
	public EMGDDataValue getNO2() {
		return NO2;
	}
	public void setNO2(EMGDDataValue nO2) {
		NO2 = nO2;
	}
	public EMGDDataValue getCO() {
		return CO;
	}
	public void setCO(EMGDDataValue cO) {
		CO = cO;
	}
	public EMGDDataValue getO3() {
		return O3;
	}
	public void setO3(EMGDDataValue o3) {
		O3 = o3;
	}
	public EMGDDataValue getWS() {
		return WS;
	}
	public void setWS(EMGDDataValue wS) {
		WS = wS;
	}
	public EMGDDataValue getWD() {
		return WD;
	}
	public void setWD(EMGDDataValue wD) {
		WD = wD;
	}
	public EMGDDataValue getNOS() {
		return NOS;
	}
	public void setNOS(EMGDDataValue nOS) {
		NOS = nOS;
	}
	public EMGDDataValue getWT() {
		return WT;
	}
	public void setWT(EMGDDataValue wT) {
		WT = wT;
	}
	public EMGDDataValue getWO() {
		return WO;
	}
	public void setWO(EMGDDataValue wO) {
		WO = wO;
	}
	public EMGDDataValue getNTU() {
		return NTU;
	}
	public void setNTU(EMGDDataValue nTU) {
		NTU = nTU;
	}
	public EMGDDataValue getMS() {
		return MS;
	}
	public void setMS(EMGDDataValue mS) {
		MS = mS;
	}
	public EMGDDataValue getPH() {
		return PH;
	}
	public void setPH(EMGDDataValue pH) {
		PH = pH;
	}
	public EMGDDataValue getNH3_N() {
		return NH3_N;
	}
	public void setNH3_N(EMGDDataValue nH3_N) {
		NH3_N = nH3_N;
	}
	public EMGDDataValue getCOD() {
		return COD;
	}
	public void setCOD(EMGDDataValue cOD) {
		COD = cOD;
	}
	public EMGDDataValue getVOCS() {
		return VOCS;
	}
	public void setVOCS(EMGDDataValue vOCS) {
		VOCS = vOCS;
	}
	public EMGDDataValue getRC() {
		return RC;
	}
	public void setRC(EMGDDataValue rC) {
		RC = rC;
	}
	public EMGDDataValue getTPH() {
		return TPH;
	}
	public void setTPH(EMGDDataValue tPH) {
		TPH = tPH;
	}
	public EMGDDataValue getTN() {
		return TN;
	}
	public void setTN(EMGDDataValue tN) {
		TN = tN;
	}
	public EMGDDataValue getCHL() {
		return CHL;
	}
	public void setCHL(EMGDDataValue cHL) {
		CHL = cHL;
	}
	public EMGDDataValue getBGA() {
		return BGA;
	}
	public void setBGA(EMGDDataValue bGA) {
		BGA = bGA;
	}
	public EMGDDataValue getOIW() {
		return OIW;
	}
	public void setOIW(EMGDDataValue oIW) {
		OIW = oIW;
	}
	public EMGDDataValue getORP() {
		return ORP;
	}
	public void setORP(EMGDDataValue oRP) {
		ORP = oRP;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}	
  
}
