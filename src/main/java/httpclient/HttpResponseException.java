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

public class HttpResponseException extends BaseException
{
    private static final long serialVersionUID = -6628209936438996109L;

    public HttpResponseException(IExceptionEnum exceptionEnum)
    {
        super(exceptionEnum);
    }
    
    public HttpResponseException(IExceptionEnum exceptionEnum, Object[] params)
    {
        super(exceptionEnum, params);
    }
}
