package com.glodio.bean;

import java.util.Date;

public class Url {
    private Integer id;

    private String urlName;

    private String urlAddr;
    
    private Integer isCatalog;
    
    private Integer catalogLevel;
    
    private String upCatalogName;

    private Integer parentId;

    private Integer subId;

    private String isAdd;

    private String isEdit;

    private String isView;

    private String isDel;

    private String isSearch;

    private String remark;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private String iconName;
    
    private String iconUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName == null ? null : urlName.trim();
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr == null ? null : urlAddr.trim();
    }

    public Integer getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(Integer isCatalog) {
		this.isCatalog = isCatalog;
	}

	public Integer getCatalogLevel() {
		return catalogLevel;
	}

	public void setCatalogLevel(Integer catalogLevel) {
		this.catalogLevel = catalogLevel;
	}

	public String getUpCatalogName() {
		return upCatalogName;
	}

	public void setUpCatalogName(String upCatalogName) {
		this.upCatalogName = upCatalogName;
	}

	public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public String toString() {
		return getUrlName()+":"+getSubId()+":"+getUrlAddr()+":"+"false";
	}
}