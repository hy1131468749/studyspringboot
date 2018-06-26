package com.glodio.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFile {
	public static Logger dataLogger = LoggerFactory.getLogger("dataLogger");
	public static Logger alarmLogger = LoggerFactory.getLogger("alarmLogger");
	public static Logger cmdLogger = LoggerFactory.getLogger("cmdLogger");
	public static Logger onlineLogger = LoggerFactory.getLogger("onlineLogger");
	public static Logger sqlLogger = LoggerFactory.getLogger("sqlLogger");
	// 设备相关日志
	public static Logger devLogger = LoggerFactory.getLogger("devLogger");
}
