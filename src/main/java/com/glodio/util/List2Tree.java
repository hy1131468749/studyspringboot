package com.glodio.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ZTreeNode list covert tree structure
 * 
 * @author qizhong.jiang
 *
 */
public class List2Tree {
	
	/**
	 * 
	 * @param records ETreeNode list collection
	 * @return sort ETreeNode  list,if the parameter is empty, return to the empty
	 */
	public static ArrayList<ETreeNode> getTreeStructure(ArrayList<ETreeNode> records) {
		ArrayList<ETreeNode> tree = null;
		
		if((records.size() > 0)) {
			tree = new ArrayList<ETreeNode>();
		    Map<Integer, ETreeNode> map = new HashMap<Integer, ETreeNode>();
		    
		    for (ETreeNode record : records) {
		        map.put(record.getId(), record);
		    }
		    
		    for (ETreeNode record : records) {
		        if (record.getpId() == 0) {
		        	tree.add(record);
		        } else {
		        	ETreeNode parentRecord = map.get(record.getpId());
		            if (parentRecord == null) {
		            	tree.add(record);
		                continue;
		            }
		            ArrayList<ETreeNode> list = parentRecord.getChildren();
		            list = list == null ? new ArrayList<ETreeNode>() : list;
		            list.add(record);
		            parentRecord.setOpen(false);
		            parentRecord.setChildren(list);
		        }
		    }
		}
	   
	    return tree;
	}
}
