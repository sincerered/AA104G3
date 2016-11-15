package com.reservation.model_jndi;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

public class ReservationService {
	private ReservationDAO_interface dao;
	
	public ReservationService() {
		dao = new ReservationJNDIDAO();
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

	public Set<ReservationVO> updateReservations(Set<ReservationVO> set){
		dao.updates(set);
		return set;		
	}
	
	public ReservationVO updateReservation(ReservationVO reservationVO) {
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

	public List<ReservationVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<ReservationVO> getReservationByTableno(String tableno) {
		return dao.findByTableno(tableno);
	}
}
