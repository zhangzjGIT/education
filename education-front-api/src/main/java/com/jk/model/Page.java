package com.jk.model;


public class Page {
	
	/**
	 * 第几页
	 */
	private Integer page = 1;
	/**
	 * 每页几条
	 */
	private Integer rows = 10;
	
	private Integer startIndex = 0;
	
	private Integer endIndex = 0;
	
	/**
	 * 方法: calculate <br>
	 * 描述: 计算分页开始结束位置 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017-4-14 上午10:34:17
	 */
	public void calculate(){
		this.startIndex = (page * rows) - rows;
		this.endIndex = page * rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	
	
}
