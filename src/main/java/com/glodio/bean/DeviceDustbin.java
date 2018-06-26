package com.glodio.bean;

import java.util.Date;

public class DeviceDustbin {
    private Long id;

    private String deviceId;

    private String deviceName;

    private String lat;

    private String lng;

    private String address;

    private String fullLeft;

    private String fullRight;

    private String smokeStatus;

    private String obliquityStatus;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFullLeft() {
        return fullLeft;
    }

    public void setFullLeft(String fullLeft) {
        this.fullLeft = fullLeft == null ? null : fullLeft.trim();
    }

    public String getFullRight() {
        return fullRight;
    }

    public void setFullRight(String fullRight) {
        this.fullRight = fullRight == null ? null : fullRight.trim();
    }

    public String getSmokeStatus() {
        return smokeStatus;
    }

    public void setSmokeStatus(String smokeStatus) {
        this.smokeStatus = smokeStatus == null ? null : smokeStatus.trim();
    }

    public String getObliquityStatus() {
        return obliquityStatus;
    }

    public void setObliquityStatus(String obliquityStatus) {
        this.obliquityStatus = obliquityStatus == null ? null : obliquityStatus.trim();
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