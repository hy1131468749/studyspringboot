/*$$$!!Warning: Huawei key information asset. No spread without permission.$$$*/
/*CODEMARK:#*/
/*$$$!!Warning: Deleting or modifying the preceding information is prohibited.$$$*/
/*
 * File Name: com.huawei.iom.iocm.app.reg.GetVerifyCodeDTONA2Cloud.java
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * simple introduction
 *
 * <p>detailed comment
 * @author g00345683 Create on 2015年11月19日
 * @see
 * @since 1.0.0
 */
public class DTOCloud2NA
{
    @Size(max=256)
    @NotNull
    private String deviceId;
    
    @Size(max=256)
    @NotNull
    private String verifyCode;
    
    private Integer timeout;
    
    @Size(max=32)
    private String psk;

	public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getVerifyCode()
    {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode)
    {
        this.verifyCode = verifyCode;
    }

    public Integer getTimeout()
    {
        return timeout;
    }

    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }
}
