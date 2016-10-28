package com.store_comment.model;

import java.sql.Date;

public class Store_CommentVO {
	private String storecomno;
	private String memno;
	private String stono;
	private String comdetail;
	private Date comdate;
	private Integer stoscore;
	
	public String getStorecomno() {
		return storecomno;
	}
	public void setStorecomno(String storecomno) {
		this.storecomno = storecomno;
	}
	public String getMemno() {
		return memno;
	}
	public void setMemno(String memno) {
		this.memno = memno;
	}
	public String getStono() {
		return stono;
	}
	public void setStono(String stono) {
		this.stono = stono;
	}
	public String getComdetail() {
		return comdetail;
	}
	public void setComdetail(String comdetail) {
		this.comdetail = comdetail;
	}
	public Date getComdate() {
		return comdate;
	}
	public void setComdate(Date comdate) {
		this.comdate = comdate;
	}
	public Integer getStoscore() {
		return stoscore;
	}
	public void setStoscore(Integer stoscore) {
		this.stoscore = stoscore;
	}

}
