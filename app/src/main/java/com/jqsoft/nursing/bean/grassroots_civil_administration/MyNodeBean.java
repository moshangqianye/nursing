package com.jqsoft.nursing.bean.grassroots_civil_administration;

public class MyNodeBean {
	/**
	 * 节点Id
	 */
	private int id;
	/**
	 * 节点父id
	 */
	private int pId;
	/**
	 * 节点name
	 */
	private String name;
	/**
	 * 
	 */
	private String desc;
	/**
	 * 节点名字长度
	 */
	private long length;

	
	public MyNodeBean(int id, int pId, String name,String desc) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.desc = desc;
	}

	public MyNodeBean(int id, int pId, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pId;
	}
	public void setPid(int pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}


}
