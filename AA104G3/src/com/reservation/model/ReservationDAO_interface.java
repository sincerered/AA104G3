package com.reservation.model;

import java.util.List;

public interface ReservationDAO_interface {
	public void insert(ReservationVO reservationVO);
	public void update(ReservationVO reservationVO);
	public void delete(String resvno);
	public ReservationVO findByPrimaryKey(String resvno);
	public List<ReservationVO> getAll();
	public List<ReservationVO> findByMemno(String memno);
	public List<ReservationVO> findByTableno(String tableno);
	public List<ReservationVO> findByTeamno(String teamno);
}
