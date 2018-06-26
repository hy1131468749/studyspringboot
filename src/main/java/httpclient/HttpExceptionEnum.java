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

public enum HttpExceptionEnum implements IExceptionEnum{

    HTTP_UNEXPECTED_RESPONSE_STATUS("101001", "Unexpected response status: %d."),
    
    HTTP_CREATE_SSL_CONTEXT_FAILED("101002", "Create ssl context failed."),
    
    HTTP_INIT_PARAM_INVALID("101003", "Http client param is invalid."),
    
    HTTP_URI_INVALID("101004", "The uri is invalid."),
    
    HTTP_RESPONSE_INVALID("101005", "The response is invalid."),
    
    HTTP_ENCODE_CONTENT_FAILED("101006", "Encode http content failed."),
    
    HTTP_REQUEST_FAILED("101007", "Excute request failed."),
    
    HTTP_RESPONSE_BODY_IS_NULL("101008", "The response body is null."),
    
    HTTP_EXC_END("101300","");
    
    private String errorCode;
    
    private String errorDesc;
    
    private HttpExceptionEnum(String errorCode, String errorDesc)
    {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
    
    @Override
    public String getErrorCode()
    {
        return errorCode;
    }
    
    @Override
    public String getErrorDesc()
    {
        return errorDesc;
    }

    @Override
    public String toString() {      
        return errorCode + "-" + errorDesc;
    }
}
