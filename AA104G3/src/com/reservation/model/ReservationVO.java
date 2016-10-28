package com.reservation.model;

import java.sql.Date;

public class ReservationVO {
	private String resvno;
	private String memno;
	private String tableno;
	private Date resvdate;
	private String resvperiod;
	private String teamno;
	private String resvstate;
	public String getResvno() {
		return resvno;
	}
	public void setResvno(String resvno) {
		this.resvno = resvno;
	}
	public String getMemno() {
		return memno;
	}
	public void setMemno(String memno) {
		this.memno = memno;
	}
	public String getTableno() {
		return tableno;
	}
	public void setTableno(String tableno) {
		this.tableno = tableno;
	}
	public Date getResvdate() {
		return resvdate;
	}
	public void setResvdate(Date resvdate) {
		this.resvdate = resvdate;
	}
	public String getResvperiod() {
		return resvperiod;
	}
	public void setResvperiod(String resvperiod) {
		this.resvperiod = resvperiod;
	}
	public String getTeamno() {
		return teamno;
	}
	public void setTeamno(String teamno) {
		this.teamno = teamno;
	}
	public String getResvstate() {
		return resvstate;
	}
	public void setResvstate(String resvstate) {
		this.resvstate = resvstate;
	}
	
}
