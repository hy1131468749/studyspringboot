package com.glodio.bean;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;
    
    private String rolename;

    private Date cjsj;

    private Date xgsj;
    
    private Date lastTime;
    
    private Integer systemUser;
    
    private String urlIndex;

	private Integer orgId;
    
    private Integer orgLevel;
    
    private String orgName;
    
    private Integer parentId;
    
    private String channelIds;
    
    private String alarmColors;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
    
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Integer systemUser) {
		this.systemUser = systemUser;
	}
	
    public String getUrlIndex() {
		return urlIndex;
	}

	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	public String getAlarmColors() {
		return alarmColors;
	}

	public void setAlarmColors(String alarmColors) {
		this.alarmColors = alarmColors;
	}
}