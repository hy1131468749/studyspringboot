package com.glodio.bean;

import java.util.Date;

public class DeviceSmokeDetector {
    private Long id;

    private String deviceId;

    private String deviceName;

    private String lat;

    private String lng;

    private String if1;

    private Integer if1Status;

    private String if2;

    private Integer if2Status;

    private Date reportTime;

    private Date reportDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
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

    public String getIf1() {
        return if1;
    }

    public void setIf1(String if1) {
        this.if1 = if1 == null ? null : if1.trim();
    }

    public Integer getIf1Status() {
        return if1Status;
    }

    public void setIf1Status(Integer if1Status) {
        this.if1Status = if1Status;
    }

    public String getIf2() {
        return if2;
    }

    public void setIf2(String if2) {
        this.if2 = if2 == null ? null : if2.trim();
    }

    public Integer getIf2Status() {
        return if2Status;
    }

    public void setIf2Status(Integer if2Status) {
        this.if2Status = if2Status;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getReportDay() {
        return reportDay;
    }

    public void setReportDay(Date reportDay) {
        this.reportDay = reportDay;
    }
}