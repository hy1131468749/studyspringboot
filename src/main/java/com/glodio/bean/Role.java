package com.glodio.bean;

import java.util.Date;

public class Role {
	private Integer id;

    private String rolename;

    private String url;

    private String operation;

	private String urlremark;
    
    private String operemark;

    private Date cjsj;

    private Date xgsj;
    
    private Integer orgId;
    
    private Integer orgLevel;
    
    private String orgName;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }
    
    public String getUrlremark() {
		return urlremark;
	}

	public void setUrlremark(String urlremark) {
		this.urlremark = urlremark;
	}

	public String getOperemark() {
		return operemark;
	}

	public void setOperemark(String operemark) {
		this.operemark = operemark;
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
}