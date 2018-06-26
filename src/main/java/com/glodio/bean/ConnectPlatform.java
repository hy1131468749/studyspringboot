package com.glodio.bean;

import java.util.Date;

import com.glodio.util.StringUtil;

public class ConnectPlatform   {
    private Long id;

    private Integer typeId;

    private String name;

    private String http;

    private Date updateTime;

    private Date createTime;

    private String ip;

    private Integer port;

    private String nbAppid;

    private String nbSecret;

    private String nbCa;

    private String nbCaPassword;

    private String nbPkcs12;

    private String nbPkcs12Password;

    private String nbManufacturerId;

    private String nbManufacturerName;

    private String nbModel;

    private String nbProtocolType;

    private String nbDeviceType;

    private Integer udpWebsocketPort;

    private String ftpUrl;

    private String hardware;

    private String videoIp;

    private Integer videoPort;

    private String videoUser;

    private String videoPassword;

    private String nokiaUserName;

    private String nokiaPassword;

    private String nokiaIdentifier;

    private String nokiaGroupName;

    private String onenetAppkey;

    private String onenetToken;

    private String smokeDetectorPartnerid;

    private String smokeDetectorPartnerKey;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http == null ? null : http.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNbAppid() {
        return nbAppid;
    }

    public void setNbAppid(String nbAppid) {
        this.nbAppid = nbAppid == null ? null : nbAppid.trim();
    }

    public String getNbSecret() {
        return nbSecret;
    }

    public void setNbSecret(String nbSecret) {
        this.nbSecret = nbSecret == null ? null : nbSecret.trim();
    }

    public String getNbCa() {
        return nbCa;
    }

    public void setNbCa(String nbCa) {
        this.nbCa = nbCa == null ? null : nbCa.trim();
    }

    public String getNbCaPassword() {
        return nbCaPassword;
    }

    public void setNbCaPassword(String nbCaPassword) {
        this.nbCaPassword = nbCaPassword == null ? null : nbCaPassword.trim();
    }

    public String getNbPkcs12() {
        return nbPkcs12;
    }

    public void setNbPkcs12(String nbPkcs12) {
        this.nbPkcs12 = nbPkcs12 == null ? null : nbPkcs12.trim();
    }

    public String getNbPkcs12Password() {
        return nbPkcs12Password;
    }

    public void setNbPkcs12Password(String nbPkcs12Password) {
        this.nbPkcs12Password = nbPkcs12Password == null ? null : nbPkcs12Password.trim();
    }

    public String getNbManufacturerId() {
        return nbManufacturerId;
    }

    public void setNbManufacturerId(String nbManufacturerId) {
        this.nbManufacturerId = nbManufacturerId == null ? null : nbManufacturerId.trim();
    }

    public String getNbManufacturerName() {
        return nbManufacturerName;
    }

    public void setNbManufacturerName(String nbManufacturerName) {
        this.nbManufacturerName = nbManufacturerName == null ? null : nbManufacturerName.trim();
    }

    public String getNbModel() {
        return nbModel;
    }

    public void setNbModel(String nbModel) {
        this.nbModel = nbModel == null ? null : nbModel.trim();
    }

    public String getNbProtocolType() {
        return nbProtocolType;
    }

    public void setNbProtocolType(String nbProtocolType) {
        this.nbProtocolType = nbProtocolType == null ? null : nbProtocolType.trim();
    }

    public String getNbDeviceType() {
        return nbDeviceType;
    }

    public void setNbDeviceType(String nbDeviceType) {
        this.nbDeviceType = nbDeviceType == null ? null : nbDeviceType.trim();
    }

    public Integer getUdpWebsocketPort() {
        return udpWebsocketPort;
    }

    public void setUdpWebsocketPort(Integer udpWebsocketPort) {
        this.udpWebsocketPort = udpWebsocketPort;
    }

    public String getFtpUrl() {
        return ftpUrl;
    }

    public void setFtpUrl(String ftpUrl) {
        this.ftpUrl = ftpUrl == null ? null : ftpUrl.trim();
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware == null ? null : hardware.trim();
    }

    public String getVideoIp() {
        return videoIp;
    }

    public void setVideoIp(String videoIp) {
        this.videoIp = videoIp == null ? null : videoIp.trim();
    }

    public Integer getVideoPort() {
        return videoPort;
    }

    public void setVideoPort(Integer videoPort) {
        this.videoPort = videoPort;
    }

    public String getVideoUser() {
        return videoUser;
    }

    public void setVideoUser(String videoUser) {
        this.videoUser = videoUser == null ? null : videoUser.trim();
    }

    public String getVideoPassword() {
        return videoPassword;
    }

    public void setVideoPassword(String videoPassword) {
        this.videoPassword = videoPassword == null ? null : videoPassword.trim();
    }

    public String getNokiaUserName() {
        return nokiaUserName;
    }

    public void setNokiaUserName(String nokiaUserName) {
        this.nokiaUserName = nokiaUserName == null ? null : nokiaUserName.trim();
    }

    public String getNokiaPassword() {
        return nokiaPassword;
    }

    public void setNokiaPassword(String nokiaPassword) {
        this.nokiaPassword = nokiaPassword == null ? null : nokiaPassword.trim();
    }

    public String getNokiaIdentifier() {
        return nokiaIdentifier;
    }

    public void setNokiaIdentifier(String nokiaIdentifier) {
        this.nokiaIdentifier = nokiaIdentifier == null ? null : nokiaIdentifier.trim();
    }

    public String getNokiaGroupName() {
        return nokiaGroupName;
    }

    public void setNokiaGroupName(String nokiaGroupName) {
        this.nokiaGroupName = nokiaGroupName == null ? null : nokiaGroupName.trim();
    }

    public String getOnenetAppkey() {
        return onenetAppkey;
    }

    public void setOnenetAppkey(String onenetAppkey) {
        this.onenetAppkey = onenetAppkey == null ? null : onenetAppkey.trim();
    }

    public String getOnenetToken() {
        return onenetToken;
    }

    public void setOnenetToken(String onenetToken) {
        this.onenetToken = onenetToken == null ? null : onenetToken.trim();
    }
    
 public String getSmokeDetectorPartnerid() {
		return smokeDetectorPartnerid;
	}

	public void setSmokeDetectorPartnerid(String smokeDetectorPartnerid) {
		this.smokeDetectorPartnerid = smokeDetectorPartnerid;
	}

	public String getSmokeDetectorPartnerKey() {
		return smokeDetectorPartnerKey;
	}

	public void setSmokeDetectorPartnerKey(String smokeDetectorPartnerKey) {
		this.smokeDetectorPartnerKey = smokeDetectorPartnerKey;
	}

	// 对数据进行验证
 	public boolean validateData() {
 		boolean result = true;
 		if(StringUtil.strIsNullOrEmpty(name)||StringUtil.strIsNullOrEmpty(http)||StringUtil.strIsNullOrEmpty(ip)||	port == null){
 			return false;
 		}
 		switch (typeId) {
 		    case 1:
 		    	if(StringUtil.strIsNullOrEmpty(nbAppid)||StringUtil.strIsNullOrEmpty(nbSecret)){
 		    		result = false;   	
 		    	}
 			    break;
 		    case 2:
 		    	if( udpWebsocketPort==null){	
 		    		result = false;   	
 		    	}
 			    break;
 		    case 3:
 		    	if(StringUtil.strIsNullOrEmpty(nokiaUserName)|| nokiaPassword == null){	
 		    		result = false;   	
 		    	}
 			    break;
 		   case 4:
		    	if(StringUtil.strIsNullOrEmpty(onenetAppkey)|| StringUtil.strIsNullOrEmpty(onenetToken)){	
		    		result = false;   	
		    	}
			    break;
 		   case 5:
		    	if(StringUtil.strIsNullOrEmpty(smokeDetectorPartnerid)|| StringUtil.strIsNullOrEmpty(smokeDetectorPartnerKey)){	
		    		result = false;   	
		    	}
			    break; 	    

 		    default: result = false;
 			    break;
 		}
 		return result;
 	}
}