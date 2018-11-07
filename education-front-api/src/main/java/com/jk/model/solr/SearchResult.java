package com.jk.model.solr;

import com.jk.model.education.MessageBean;

import java.util.List;
/**
 * 类描述：    solr 分页工具类
 * 创建人：LDW
 * 创建时间：2018/10/26 11:32
 * 修改人：LDW
 * 修改时间：2018/10/26 11:32
 * 修改备注：
 * @version ：1.0
 */
public class SearchResult {

	//列表
	private List<MessageBean> messList;
	//总记录数
	private long recordCount;
	//总页数
	private long pageCount;
	//当前页
	private long curPage;

	public List<MessageBean> getMessList() {
		return messList;
	}

	public void setMessList(List<MessageBean> messList) {
		this.messList = messList;
	}

	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	
}
