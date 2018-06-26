package com.glodio;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 当参数类型为date时进行处理，可以根据项目需要修改
 * @author Administrator
 *
 */
public class StringToDateConverter implements Converter<String, Date> {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
	
	@Override
	public Date convert(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}

		value = value.trim();
		
		try {
			if(value.contains("-")){
				// 表示采用的日期格式是以"-"表示的。eg: 2010-09-09
				SimpleDateFormat formatter;
				if (value.contains(":")) {
					// 表示传入的时间是有时分秒的
					formatter = new SimpleDateFormat(DATE_FORMAT);
				}else{
					// 表示传入的时间是没有时分秒的
					formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
				}
				Date dtDate = formatter.parse(value);
				return dtDate;
			}else if (value.matches("^\\d+$")) {
				// 表示传入的是一串数字
				Long lDate = new Long(value);
				return new Date(lDate);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("parser %s to Date fail", value));
		}
		throw new RuntimeException(String.format("parser %s to Date fail", value));
	}

}
