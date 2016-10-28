package com.reservation.model;

import java.sql.Date;
import java.util.List;

public class ReservationService {
	private ReservationDAO_interface dao;
	
	public ReservationService() {
		dao = new ReservationDAO();
	}
	
	public ReservationVO addReservation(String resvno, String memno, String tableno, Date resvdate, String resvperiod, String teamno, String resvstate) {
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setResvno(resvno);
		reservationVO.setMemno(memno);
		reservationVO.setTableno(tableno);
		reservationVO.setResvdate(resvdate);
		reservationVO.setResvperiod(resvperiod);
		reservationVO.setTeamno(teamno);
		reservationVO.setResvstate(resvstate);
		dao.insert(reservationVO);
		return reservationVO;
	}
	
	public ReservationVO updateReservation(String resvno, String memno, String tableno, Date resvdate, String resvperiod, String teamno, String resvstate) {
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setResvno(resvno);
		reservationVO.setMemno(memno);
		reservationVO.setTableno(tableno);
		reservationVO.setResvdate(resvdate);
		reservationVO.setResvperiod(resvperiod);
		reservationVO.setTeamno(teamno);
		reservationVO.setResvstate(resvstate);
		dao.update(reservationVO);
		return reservationVO;		
	}
	
	public void deleteReservation(String resvno) {
		dao.delete(resvno);
	}
	
	public ReservationVO getOneReservation(String resvno) {
		return dao.findByPrimaryKey(resvno);
	}
	
	public List<ReservationVO> getAll() {
		return dao.getAll();
	}
	
	public List<ReservationVO> getReservationByTableno(String tableno) {
		return dao.findByTableno(tableno);
	}
}
