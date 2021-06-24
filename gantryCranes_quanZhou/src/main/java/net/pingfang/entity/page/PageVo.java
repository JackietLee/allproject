package net.pingfang.entity.page;

import java.io.Serializable;
import java.util.List;

/**
* <p>Title:PageVo </p>
* <p>Description: 分页对象</p>
* @author Administrator 
* @date 2019-5-25
*/
public class PageVo<T> implements Serializable {
	private static final long serialVersionUID = -6710532081301192385L;

	/**
	* 基本属性分析：
	* 1.当前页 currentPage
	* 2.每页多少数据 pageSize
	* 3.数据总条数 totalCount
	* 4.总页数 pageCount
	* 5.数据集合 List<T> list
	* 6.每页显示的最多的页码数 pageNumSize
	* 7.拼接的分页字符串 pageHtml
	*/
	private int currentPage = 1;	//默认值

	private int pageSize = 15;		//默认值

	private int totalCount;

	private int pageCount;
	
	private int startRow;
	
	private int endRow;

	private List<T> list;
	private T obj;
	
/*	public PageVo(List<T> list, int totalCount){
		this.list = list;
		this.totalCount = totalCount;
	}*/

	/**
	* 基本逻辑分析：
	* 1.list总数判断得出totalCount和pageCount
	* 2.currentPage*pageSize得出startRow和endRow
	* 3.通过list.submit(startRow,endRow)得出需要展示的当前页的数据;
	* 4.通过currentPage、pageSize、totalCount、pageCount拼出分页html字符串前台
	* 需要拼接的项目有首页、上一页、下一页、末页以及中间具体页码
	* 5.前台点击上述项目时，ajax传递currentPage和pageSize及查询条件至后台
	* 6.根据查询条件查询出list，和currentPage和PageSize传入PageBean对象生成需要展示的list以及分页html
	*/

	/**
	* <p>初始化方法</p>
	* @param list 数据集合 currentPage 页面传递的当前页码 pageSize 页面传递的pageSize
	* @author  
	* @date 
	*/
	public void initPage(Integer currentPage, Integer pageSize, Integer totalCount){
		//1.初始化各属性的值
		if(currentPage != null && currentPage >= 1){
			this.currentPage = currentPage;
		}
		if(pageSize != null && pageSize >= 1){
			this.pageSize = pageSize;
		}
		//this.list = list;
		
		this.totalCount = totalCount;
		this.pageCount = totalCount%this.pageSize == 0?totalCount/this.pageSize:totalCount/this.pageSize+1;
		startRow = (this.currentPage-1)*this.pageSize;
		endRow = this.currentPage*this.pageSize;
		/*if(endRow > list.size()){
			endRow = list.size();
		}*/
		//3.截取list
		/*if(!this.list.isEmpty()){
			this.list = this.list.subList(startRow, endRow);
		}*/
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	

}
