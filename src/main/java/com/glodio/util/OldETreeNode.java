package com.glodio.util;

import java.util.ArrayList;

public class OldETreeNode {
	

	// 节点ID
	private int id = 0;

	// 父节点ID
	private int pId = 0;

	// 标题
	private String name = null;

	// 节点是否展开
	private boolean open = true;

	// 图标
	private String icon = null;
	
	// easyui combotree node icon name
	private String iconCls = null;
	
	//坐标
	private String coordinate;
	
	//是否是父节点
	private boolean isParent ;
	
	//类型:组织类型
	int type = 0;

	private ArrayList<OldETreeNode> children;
	
	private String realId;
	
	private boolean checked;
	

	// 地区和企业
	public OldETreeNode(int id, int pId, String title) {
		this.id = id;
		this.pId = pId;
		this.name = title;
	}
	
	public OldETreeNode(int id, int pId, String title,int type) {
		this.id = id;
		this.pId = pId;
		this.name = title;
		this.type = type;
	}
	
	// 设备下发模板节点，增加折叠状态
	public OldETreeNode(int id, String title,int pId,boolean open) {
		this.id = id;
		this.pId = pId;
		this.name = title;
		this.open = open;
	}
	
	// 地区和设备
	public OldETreeNode(int id, int pId, String title,String coordinate) {
		this.id = id;
		this.pId = pId;
		this.name = title;
		this.setCoordinate(coordinate);
	}
	
	// 地区和设备、增加节点图标
	public OldETreeNode(int id, int pId, String title,String coordinate,String iconCls) {
		this.id = id;
		this.pId = pId;
		this.name = title;
		this.setCoordinate(coordinate);
		this.iconCls = iconCls;
	}
   
	public OldETreeNode() {
		
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

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ArrayList<OldETreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<OldETreeNode> children) {
		this.children = children;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getRealId() {
		return realId;
	}

	public void setRealId(String realId) {
		this.realId = realId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}




}
