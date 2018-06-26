package com.glodio.util;

import java.util.ArrayList;

public class MenuTreeNode {
	// 节点ID
	private int id = 0;

	// 父节点ID
	private int pId = 0;

	// private String catalogLevel = null;

	// private String upCatalogName = null;

	// 标题
	private String name = null;

	// 节点是否展开
	private boolean open = false;

	// 图标
	private String icon = null;

	// url用file字段代替,这样相当于diy-ZTree节点,防止多打开一个新窗口
	private String file;

	// 摄像头id
	private String deviceId;
	
	private ArrayList<MenuTreeNode> nodes;

	// 地区和企业
	public MenuTreeNode(int id, int pId, String title) {
		this.id = id;
		this.pId = pId;
		this.name = title;
	}

	// 设备信息
	public MenuTreeNode(int id, int pId, String title, String deviceId) {
		this.id = id;
		this.pId = pId;
		this.name = title;
		this.deviceId = deviceId;
	}

	// tabs-url
	public MenuTreeNode(int id, int pid, String title, String file, boolean open,String icon) {
		this.id = id;
		this.pId = pid;
		this.name = title;
		this.file = file;
		this.open = open;
		this.icon = icon;
	}

	/*
	 * public ZTreeNode(int id, int pid, String title,String file,boolean
	 * open,String catalogLevel,String upCatalogName) { this.id = id; this.pId =
	 * pid; this.name = title; this.file = file; this.open = open;
	 * this.catalogLevel = catalogLevel; this.upCatalogName = upCatalogName; }
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String path) {
		this.icon = path;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	/*
	 * public String getCatalogLevel() { return catalogLevel; }
	 * 
	 * public void setCatalogLevel(String catalogLevel) { this.catalogLevel =
	 * catalogLevel; }
	 * 
	 * public String getUpCatalogName() { return upCatalogName; }
	 * 
	 * public void setUpCatalogName(String upCatalogName) { this.upCatalogName =
	 * upCatalogName; }
	 */

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public ArrayList<MenuTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<MenuTreeNode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		return "{" + "id:" + id + "," + "pid:" + pId + "," + "name:" + name + "," + "file:" + file + "}";
	}

}
