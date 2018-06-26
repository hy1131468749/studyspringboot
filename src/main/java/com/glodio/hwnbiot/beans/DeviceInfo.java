/*$$$!!Warning: Huawei key information asset. No spread without permission.$$$*/
/*CODEMARK:#*/
/*$$$!!Warning: Deleting or modifying the preceding information is prohibited.$$$*/
/*
 * File Name: com.huawei.m2m.nscl.modules.device.DeviceInfo.java
 *
 * Copyright Notice:
 *      Copyright  1998-2008, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package com.glodio.hwnbiot.beans;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * simple introduction
 *
 * <p>detailed comment
 * @author g00345683 Create on 2015年11月14日
 * @see
 * @since 1.0.0
 */
//DeviceInfo中添加字段，需要同步修改 静态信息同步接口syncDeviceInfo
public class DeviceInfo implements Serializable 
{
    private static final long serialVersionUID = 1L;

    @Size(max=256)
    private String nodeId;
    
    @Size(max=256)
    private String name;
    
    @Size(max=2048)
    private String description;
    
    @Size(max=256)
    private String manufacturerId;
    
    @Size(max=256)
    private String manufacturerName;
    
    @Size(max=256)
    private String mac;
    
    @Size(max=2048)
    private String location;
    
    @Size(max=256)
    private String deviceType;
    
    @Size(max=256)
    private String model;
    
    @Size(max=256)
    private String swVersion;
    
    @Size(max=256)
    private String fwVersion;
    
    @Size(max=256)
    private String hwVersion;
    
    @Size(max=256)
    private String protocolType;
        
    @Size(max=256)
    private String bridgeId;
    
    @Pattern(regexp = "(ONLINE|OFFLINE|INBOX|ABNORMAL)")
    private String status;
    
    @Size(max=256)
    private String statusDetail;
    
    @Pattern(regexp = "(TRUE|FALSE)")
    private String mute;
    
    @Pattern(regexp = "(TRUE|FALSE)")
    private String supportedSecurity;
    
    @Pattern(regexp = "(TRUE|FALSE)")
    private String isSecurity;
   
    @Size(max=256)
    private String signalStrength;
    
    @Size(max=256)
    private String sigVersion;
    
    @Size(max=256)
    private String serialNumber;

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName()
    {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName)
    {
        this.manufacturerName = manufacturerName;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getSwVersion()
    {
        return swVersion;
    }

    public void setSwVersion(String swVersion)
    {
        this.swVersion = swVersion;
    }

    public String getFwVersion()
    {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion)
    {
        this.fwVersion = fwVersion;
    }

    public String getHwVersion()
    {
        return hwVersion;
    }

    public void setHwVersion(String hwVersion)
    {
        this.hwVersion = hwVersion;
    }

    public String getProtocolType()
    {
        return protocolType;
    }

    public void setProtocolType(String protocolType)
    {
        this.protocolType = protocolType;
    }

    public String getBridgeId()
    {
        return bridgeId;
    }

    public void setBridgeId(String bridgeId)
    {
        this.bridgeId = bridgeId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatusDetail()
    {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail)
    {
        this.statusDetail = statusDetail;
    }

    public String getMute()
    {
        return mute;
    }

    public void setMute(String mute)
    {
        this.mute = mute;
    }
    
    public String getSupportedSecurity() {
		return supportedSecurity;
	}

	public void setSupportedSecurity(String supportedSecurity) {
		this.supportedSecurity = supportedSecurity;
	}

	public String getIsSecurity() {
		return isSecurity;
	}

	public void setIsSecurity(String isSecurity) {
		this.isSecurity = isSecurity;
	}

    public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getSigVersion() {
		return sigVersion;
	}

	public void setSigVersion(String sigVersion) {
		this.sigVersion = sigVersion;
	}

	public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
