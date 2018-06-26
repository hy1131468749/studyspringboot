
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
public class DTONA2Cloud
{

	@Size(max=256)
    private String verifyCode;
    
    @Size(max=256)
    @NotNull
    private String nodeId;
    
    @Size(max=256)
    private String endUserId;
    
    private Integer timeout;
    
    public String getVerifyCode()
    {
        return verifyCode;
    }


    public void setVerifyCode(String verifyCode)
    {
        this.verifyCode = verifyCode;
    }


    public String getNodeId()
    {
        return nodeId;
    }


    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getEndUserId()
    {
        return endUserId;
    }


    public void setEndUserId(String endUserId)
    {
        this.endUserId = endUserId;
    }


    public Integer getTimeout()
    {
        return timeout;
    }


    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }

}
