package com.glodio.bean;

import java.util.Date;

public class DeviceParam {
    private Long id;

    private String deviceId;

    private String flow;

    private String imsi;

    private String signalIntensity;

    private String electricity;

    private String electricityThreshold;

    private String reportFrequency;

    private String detectionFrequency;

    private String sampleFrequency;

    private String urgentReportFrequency;

    private String urgentSampleFrequency;

    private Date createTime;

    private Date sendTime;

    private Date updateTime;

    private Integer type;

    private Integer issueState;

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

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow == null ? null : flow.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(String signalIntensity) {
        this.signalIntensity = signalIntensity == null ? null : signalIntensity.trim();
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity == null ? null : electricity.trim();
    }

    public String getElectricityThreshold() {
        return electricityThreshold;
    }

    public void setElectricityThreshold(String electricityThreshold) {
        this.electricityThreshold = electricityThreshold == null ? null : electricityThreshold.trim();
    }

    public String getReportFrequency() {
        return reportFrequency;
    }

    public void setReportFrequency(String reportFrequency) {
        this.reportFrequency = reportFrequency == null ? null : reportFrequency.trim();
    }

    public String getDetectionFrequency() {
        return detectionFrequency;
    }

    public void setDetectionFrequency(String detectionFrequency) {
        this.detectionFrequency = detectionFrequency == null ? null : detectionFrequency.trim();
    }

    public String getSampleFrequency() {
        return sampleFrequency;
    }

    public void setSampleFrequency(String sampleFrequency) {
        this.sampleFrequency = sampleFrequency == null ? null : sampleFrequency.trim();
    }

    public String getUrgentReportFrequency() {
        return urgentReportFrequency;
    }

    public void setUrgentReportFrequency(String urgentReportFrequency) {
        this.urgentReportFrequency = urgentReportFrequency == null ? null : urgentReportFrequency.trim();
    }

    public String getUrgentSampleFrequency() {
        return urgentSampleFrequency;
    }

    public void setUrgentSampleFrequency(String urgentSampleFrequency) {
        this.urgentSampleFrequency = urgentSampleFrequency == null ? null : urgentSampleFrequency.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIssueState() {
        return issueState;
    }

    public void setIssueState(Integer issueState) {
        this.issueState = issueState;
    }
}