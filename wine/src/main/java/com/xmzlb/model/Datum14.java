package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum14 {

	@SerializedName("cat_id")
	@Expose
	private String catId;
	@SerializedName("cat_name")
	@Expose
	private String catName;
	@Expose
	private List<Child3> child = new ArrayList<Child3>();

	/**
	 * 
	 * @return The catId
	 */
	public String getCatId() {
		return catId;
	}

	/**
	 * 
	 * @param catId
	 *            The cat_id
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}

	/**
	 * 
	 * @return The catName
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 * 
	 * @param catName
	 *            The cat_name
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	 * 
	 * @return The child
	 */
	public List<Child3> getChild() {
		return child;
	}

	/**
	 * 
	 * @param child
	 *            The child
	 */
	public void setChild(List<Child3> child) {
		this.child = child;
	}

}
