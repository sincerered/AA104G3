package com.reservation.model_jndi;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

public interface ReservationDAO_interface {
	public void insert(ReservationVO reservationVO);
	public void update(ReservationVO reservationVO);
	public void updates(Set<ReservationVO> set);
	public void delete(String resvno);
	public ReservationVO findByPrimaryKey(String resvno);
	public List<ReservationVO> getAll();
	public List<ReservationVO> getAll(Map<String, String[]> map);
	public List<ReservationVO> findByMemno(String memno);
	public List<ReservationVO> findByTableno(String tableno);
	public List<ReservationVO> findByTeamno(String teamno);
}
