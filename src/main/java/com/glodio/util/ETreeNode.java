package com.glodio.util;

import java.util.ArrayList;

public class ETreeNode {
	// 节点ID
	private int id = 0;

	// 父节点ID
	private int pId = 0;

	// 标题
	private String name = null;
	
	// url
	private String file = null;

	// 节点是否展开
	private boolean open = true;

	// 图标
	private String icon = null;

	private ArrayList<ETreeNode> children;

	// tabs-url
	public ETreeNode(int id, int pid, String name, String file, boolean open,String icon) {
		this.id = id;
		this.pId = pid;
		this.name = name;
		this.file = file;
		this.open = open;
		this.icon = icon;
	}
	

	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}


	public void setOpen(boolean open) {
		this.open = open;
	}


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

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ArrayList<ETreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ETreeNode> children) {
		this.children = children;
	}
}
