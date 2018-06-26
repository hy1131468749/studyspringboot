package com.glodio.util;

public class CommandNode {
	private String add;
	
	private String edit;
	
	private String del;
	
	private String view;
	
	private String search;
	
	public CommandNode(String strAdd,String strEdit,String strView,String strDel,String strSearch) {
		this.add = strAdd;
		this.edit = strEdit;
		this.view = strView;
		this.del = strDel;
		this.search = strSearch;
		
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
