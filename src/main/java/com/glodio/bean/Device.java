package com.glodio.bean;

import java.util.Date;

public class Device {
    private Integer id;

    private String deviceId;
    
    private String nbDeviceId;

    private String deviceName;

    private String lat;

    private String lng;

    private Integer deviceType;

    private Integer connectPlatform;

    private String remark;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private Integer orgId;

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getNbDeviceId() {
		return nbDeviceId;
	}

	public void setNbDeviceId(String nbDeviceId) {
		this.nbDeviceId = nbDeviceId;
	}

	public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getConnectPlatform() {
        return connectPlatform;
    }

    public void setConnectPlatform(Integer connectPlatform) {
        this.connectPlatform = connectPlatform;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}