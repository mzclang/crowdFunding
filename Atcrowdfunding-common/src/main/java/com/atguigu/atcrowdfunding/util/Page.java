package com.atguigu.atcrowdfunding.util;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.Cert;

public class Page<T> {

		private Integer pageno;//当前页
		private Integer pagesize;//页面数据大小
		private List<T> datas;
		private Integer totalsize; //总共多少条记录
		private Integer totalno; //总共多少页
		
		public Page(Integer pageno,Integer pagesize) {
			if(pageno<=0) {
				this.pageno=0;
			}else {
				this.pageno=pageno;
			}
			
			if(pagesize<=0) {
				this.pagesize=10;
			}else {
				this.pagesize = pagesize;
			}
		}
		
		public Integer getPageno() {
			return pageno;
		}
		public void setPageno(Integer pageno) {
			this.pageno = pageno;
		}
		public Integer getPagesize() {
			return pagesize;
		}
		public void setPagesize(Integer pagesize) {
			this.pagesize = pagesize;
		}
		public List<T> getDatas() {
			return datas;
		}
		public void setDatas(List<T> datas) {
			this.datas = datas;
		}
		public Integer getTotalsize() {
			return totalsize;
		}
		public void setTotalsize(Integer totalsize) {
			this.totalsize = totalsize;
			this.totalno = (totalsize%pagesize)==0?(totalsize/pagesize):(totalsize/pagesize+1);
		}
		public Integer getTotalno() {
			return totalno;
		}
		private void setTotalno(Integer totalno) {
			this.totalno = totalno;
		}
		
		//取开始索引
		public Integer getStartIndex() {
			return (this.pageno-1)*pagesize;
		}

		
}
