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

import org.springframework.http.HttpStatus;

public interface IExceptionEnum {

    public String getErrorCode();

    public String getErrorDesc();

    default public HttpStatus getHttpStatus() {
        return null;
    }
}
