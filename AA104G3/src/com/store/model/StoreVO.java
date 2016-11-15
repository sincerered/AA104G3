package com.store.model;

import java.util.HashSet;
import java.util.Set;

import com.stotable.model.StotableVO;

public class StoreVO {
	private String stono;
	private String stoid;
	private String stopw;
	private String stostate;
	private String stoname;
	private String stoemail;
	private String stoaddr;
	private String stophone;
	private String stoowner;
	private Double stolong;
	private Double stolati;
	private byte[] stopic;
	private byte[] stoproof;
	private String stobh;
	private String bankno;
	private String bankname;
	private String accountname;
	private String accountno;
	private Set<StotableVO> stotables = new HashSet<StotableVO>();
	
	public String getStono() {
		return stono;
	}
	public void setStono(String stono) {
		this.stono = stono;
	}
	public String getStoid() {
		return stoid;
	}
	public void setStoid(String stoid) {
		this.stoid = stoid;
	}
	public String getStopw() {
		return stopw;
	}
	public void setStopw(String stopw) {
		this.stopw = stopw;
	}
	public String getStostate() {
		return stostate;
	}
	public void setStostate(String stostate) {
		this.stostate = stostate;
	}
	public String getStoname() {
		return stoname;
	}
	public void setStoname(String stoname) {
		this.stoname = stoname;
	}
	public String getStoemail() {
		return stoemail;
	}
	public void setStoemail(String stoemail) {
		this.stoemail = stoemail;
	}
	public String getStoaddr() {
		return stoaddr;
	}
	public void setStoaddr(String stoaddr) {
		this.stoaddr = stoaddr;
	}
	public String getStophone() {
		return stophone;
	}
	public void setStophone(String stophone) {
		this.stophone = stophone;
	}
	public String getStoowner() {
		return stoowner;
	}
	public void setStoowner(String stoowner) {
		this.stoowner = stoowner;
	}
	public Double getStolong() {
		return stolong;
	}
	public void setStolong(Double stolong) {
		this.stolong = stolong;
	}
	public Double getStolati() {
		return stolati;
	}
	public void setStolati(Double stolati) {
		this.stolati = stolati;
	}
	public byte[] getStopic() {
		return stopic;
	}
	public void setStopic(byte[] stopic) {
		this.stopic = stopic;
	}
	public byte[] getStoproof() {
		return stoproof;
	}
	public void setStoproof(byte[] stoproof) {
		this.stoproof = stoproof;
	}
	public String getStobh() {
		return stobh;
	}
	public void setStobh(String stobh) {
		this.stobh = stobh;
	}
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public Set<StotableVO> getStotables() {
		return stotables;
	}
	public void setStotables(Set<StotableVO> stotables) {
		this.stotables = stotables;
	}
}
