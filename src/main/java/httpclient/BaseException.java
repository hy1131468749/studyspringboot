/*
 * Copyright Notice:
 *      Copyright  1998-2008, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */

package httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.glodio.hwnbiot.beans.ExceptionMsg;

public class BaseException extends Exception
{
    private static final long serialVersionUID = 9064118745135279107L;

    private final static Logger log = LoggerFactory.getLogger(BaseException.class);

    /**
     * 异常id，用于查找异常描述
     */
    private String exceptionID;

    /**
     * 异常描述需要的参数值
     */
    private Object[] params;

	private HttpStatus httpStatus;
    
    private ExceptionMsg exceptionMsg;

    public BaseException(String exceptionType, String exceptionDesc)
    {
        this.exceptionMsg = new ExceptionMsg(exceptionType, exceptionDesc);
    }

    public BaseException(ExceptionMsg exceptionMsg)
    {
        this.exceptionMsg = exceptionMsg;
    }

    public BaseException(IExceptionEnum exceptionEnum)
    {
        this.httpStatus = exceptionEnum.getHttpStatus();
        this.exceptionMsg = new ExceptionMsg(exceptionEnum.getErrorCode(),
                exceptionEnum.getErrorDesc());
    }

    public BaseException(HttpStatus httpStatus, IExceptionEnum exceptionEnum)
    {
        this.httpStatus = httpStatus;
        this.exceptionMsg = new ExceptionMsg(exceptionEnum.getErrorCode(),
                exceptionEnum.getErrorDesc());
    }

    public BaseException(IExceptionEnum exceptionEnum, Object[] params)
    {
        String exceptionDesc = exceptionEnum.getErrorDesc();

        if ((params != null) && (exceptionDesc != null))
        {
            try
            {
                exceptionDesc = String.format(exceptionDesc, params);
            }
            catch (Exception e)
            {
                log.error("Exception: {}", e);
            }
        }

        this.exceptionMsg = new ExceptionMsg(exceptionEnum.getErrorCode(),
                exceptionDesc);
    }

    public String getExceptionID()
    {
        return this.exceptionID;
    }

    public ExceptionMsg getExceptionMsg()
    {
        return this.exceptionMsg;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public Object[] getParams()
    {
        return params;
    }
    
    public void setParams(Object[] params) {
		this.params = params;
	}

    @Override
    public String getMessage()
    {
        String message = exceptionMsg.getError_code() + "-"
                + exceptionMsg.getError_desc();

        return message;
    }
}
