package com.reservation.model_jndi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import jdbc.util.CompositQuery.jdbcUtil_CompositeQuery_Reservation;


public class ReservationJNDIDAO implements ReservationDAO_interface{
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	private static final String INSERT_STMT = "INSERT INTO reservation (resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate) VALUES(lpad(to_char(reservation_SEQ.NEXTVAL),6,'0'),?,?,SYSDATE,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE reservation SET memno=?, tableno=?, resvdate=?, resvperiod=?, teamno=?, resvstate=? WHERE resvno=?";
	private static final String DELETE_STMT = "DELETE FROM reservation WHERE resvno=?";
	private static final String GET_ALL_STMT = "SELECT resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate FROM reservation";
	private static final String GET_ONE_STMT = "SELECT resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate FROM reservation WHERE resvno=?";
	private static final String GET_BY_MEMNO_STMT = "SELECT resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate FROM reservation WHERE memno=?";
	private static final String GET_BY_TABLENO_STMT = "SELECT resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate FROM reservation WHERE tableno=?";
	private static final String GET_BY_TEAMNO_STMT = "SELECT resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate FROM reservation WHERE teamno=?";
	
	
	@Override
	public void insert(ReservationVO reservationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, reservationVO.getMemno());
			pstmt.setString(2, reservationVO.getTableno());
			pstmt.setString(3, reservationVO.getResvperiod());
			pstmt.setString(4, reservationVO.getTeamno());
			pstmt.setString(5, reservationVO.getResvstate());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ReservationVO reservationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, reservationVO.getMemno());
			pstmt.setString(2, reservationVO.getTableno());
			pstmt.setDate(3, reservationVO.getResvdate());
			pstmt.setString(4, reservationVO.getResvperiod());
			pstmt.setString(5, reservationVO.getTeamno());
			pstmt.setString(6, reservationVO.getResvstate());
			pstmt.setString(7, reservationVO.getResvno());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void updates(Set<ReservationVO> set){
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			for (ReservationVO reservationVO : set) {
				
				pstmt.setString(1, reservationVO.getMemno());
				pstmt.setString(2, reservationVO.getTableno());
				pstmt.setDate(3, reservationVO.getResvdate());
				pstmt.setString(4, reservationVO.getResvperiod());
				pstmt.setString(5, reservationVO.getTeamno());
				pstmt.setString(6, reservationVO.getResvstate());
				pstmt.setString(7, reservationVO.getResvno());
				
				pstmt.executeUpdate();
			}
			con.commit();
		}catch(SQLException se){
			try {
				con.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void delete(String resvno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, resvno);
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ReservationVO findByPrimaryKey(String resvno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO reservationVO = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, resvno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return reservationVO;
	}

	@Override
	public List<ReservationVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO reservationVO = null;
		ResultSet rs = null;
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
				
				list.add(reservationVO);
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<ReservationVO> findByMemno(String memno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO reservationVO = null;
		ResultSet rs = null;
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMNO_STMT);
			
			pstmt.setString(1, memno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
				
				list.add(reservationVO);
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<ReservationVO> findByTableno(String tableno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO reservationVO = null;
		ResultSet rs = null;
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_TABLENO_STMT);
			
			pstmt.setString(1, tableno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
				
				list.add(reservationVO);
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<ReservationVO> findByTeamno(String teamno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO reservationVO = null;
		ResultSet rs = null;
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_TEAMNO_STMT);
			
			pstmt.setString(1, teamno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
				
				list.add(reservationVO);
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public List<ReservationVO> getAll(Map<String, String[]> map) {
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		ReservationVO reservationVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			
			String finalSQL = "SELECT * FROM reservation "
					+ jdbcUtil_CompositeQuery_Reservation.get_WhereCondition(map);
			
			System.out.println("finalSQL by reservation is " + finalSQL);
			pstmt = con.prepareStatement(finalSQL);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reservationVO = new ReservationVO();
				
				reservationVO.setResvno(rs.getString("resvno"));
				reservationVO.setMemno(rs.getString("memno"));
				reservationVO.setTableno(rs.getString("tableno"));
				reservationVO.setResvdate(rs.getDate("resvdate"));
				reservationVO.setResvperiod(rs.getString("resvperiod"));
				reservationVO.setTeamno(rs.getString("teamno"));
				reservationVO.setResvstate(rs.getString("resvstate"));
				
				list.add(reservationVO);
			}
			
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occur. " + se.getMessage());
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
