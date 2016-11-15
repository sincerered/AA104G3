package com.store.model;

import java.sql.Connection;
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
import javax.sql.DataSource;

public class StoreJNDIDAO implements StoreDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO store (stono, stoid, stopw, stostate, stoname, stoemail, stoaddr, stophone, stoowner, stolong, stolati, stopic, stoproof, stobh, bankno, bankname, accountname, accountno) VALUES(lpad(to_char(store_SEQ.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE store SET stoid=?, stopw=?, stostate=?, stoname=?, stoemail=?, stoaddr=?, stophone=?, stoowner=?, stolong=?, stolati=?, stopic=?, stoproof=?, stobh=?, bankno=?, bankname=?, accountname=?, accountno=? WHERE stono=?";
	private static final String DELETE_STMT = "DELETE FROM store where stono=?";
	private static final String GET_ALL_STMT = "SELECT stono, stoid, stopw, stostate, stoname, stoemail, stoaddr, stophone, stoowner, stolong, stolati, stopic, stoproof, stobh, bankno, bankname, accountname, accountno FROM store";
	private static final String GET_ONE_STMT = "SELECT stono, stoid, stopw, stostate, stoname, stoemail, stoaddr, stophone, stoowner, stolong, stolati, stopic, stoproof, stobh, bankno, bankname, accountname, accountno FROM store WHERE stono=?";
	private static final String GET_ONE_BY_ID_STMT = "SELECT stono, stoid, stopw, stostate, stoname, stoemail, stoaddr, stophone, stoowner, stolong, stolati, stopic, stoproof, stobh, bankno, bankname, accountname, accountno FROM store WHERE stoid=?";
	
	@Override
	public void insert(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, storeVO.getStoid());
			pstmt.setString(2, storeVO.getStopw());
			pstmt.setString(3, storeVO.getStostate());
			pstmt.setString(4, storeVO.getStoname());
			pstmt.setString(5, storeVO.getStoemail());
			pstmt.setString(6, storeVO.getStoaddr());
			pstmt.setString(7, storeVO.getStophone());
			pstmt.setString(8, storeVO.getStoowner());
			pstmt.setDouble(9, storeVO.getStolong());
			pstmt.setDouble(10, storeVO.getStolati());
			pstmt.setBytes(11, storeVO.getStopic());
			pstmt.setBytes(12, storeVO.getStoproof());
			pstmt.setString(13, storeVO.getStobh());
			pstmt.setString(14, storeVO.getBankno());
			pstmt.setString(15, storeVO.getBankname());
			pstmt.setString(16, storeVO.getAccountname());
			pstmt.setString(17, storeVO.getAccountno());
			
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
	public void update(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, storeVO.getStoid());
			pstmt.setString(2, storeVO.getStopw());
			pstmt.setString(3, storeVO.getStostate());
			pstmt.setString(4, storeVO.getStoname());
			pstmt.setString(5, storeVO.getStoemail());
			pstmt.setString(6, storeVO.getStoaddr());
			pstmt.setString(7, storeVO.getStophone());
			pstmt.setString(8, storeVO.getStoowner());
			pstmt.setDouble(9, storeVO.getStolong());
			pstmt.setDouble(10, storeVO.getStolati());
			pstmt.setBytes(11, storeVO.getStopic());
			pstmt.setBytes(12, storeVO.getStoproof());
			pstmt.setString(13, storeVO.getStobh());
			pstmt.setString(14, storeVO.getBankno());
			pstmt.setString(15, storeVO.getBankname());
			pstmt.setString(16, storeVO.getAccountname());
			pstmt.setString(17, storeVO.getAccountno());
			pstmt.setString(18, storeVO.getStono());
			
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
	public void delete(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, stono);
			
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
	public StoreVO findByPrimaryKey(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO storeVO = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, stono);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				storeVO = new StoreVO();
				
				storeVO.setStono(rs.getString("stono"));
				storeVO.setStoid(rs.getString("stoid"));
				storeVO.setStopw(rs.getString("stopw"));
				storeVO.setStostate(rs.getString("stostate"));
				storeVO.setStoname(rs.getString("stoname"));
				storeVO.setStoemail(rs.getString("stoemail"));
				storeVO.setStoaddr(rs.getString("stoaddr"));
				storeVO.setStophone(rs.getString("stophone"));
				storeVO.setStoowner(rs.getString("stoowner"));
				storeVO.setStolong(rs.getDouble("stolong"));
				storeVO.setStolati(rs.getDouble("stolati"));
				storeVO.setStopic(rs.getBytes("stopic"));
				storeVO.setStoproof(rs.getBytes("stoproof"));
				storeVO.setStobh(rs.getString("stobh"));
				storeVO.setBankno(rs.getString("bankno"));
				storeVO.setBankname(rs.getString("bankname"));
				storeVO.setAccountname(rs.getString("accountname"));
				storeVO.setAccountno(rs.getString("accountno"));
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
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO storeVO = null;
		ResultSet rs = null;
		List<StoreVO> list = new ArrayList<StoreVO>();
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				storeVO = new StoreVO();
				
				storeVO.setStono(rs.getString("stono"));
				storeVO.setStoid(rs.getString("stoid"));
				storeVO.setStopw(rs.getString("stopw"));
				storeVO.setStostate(rs.getString("stostate"));
				storeVO.setStoname(rs.getString("stoname"));
				storeVO.setStoemail(rs.getString("stoemail"));
				storeVO.setStoaddr(rs.getString("stoaddr"));
				storeVO.setStophone(rs.getString("stophone"));
				storeVO.setStoowner(rs.getString("stoowner"));
				storeVO.setStolong(rs.getDouble("stolong"));
				storeVO.setStolati(rs.getDouble("stolati"));
				storeVO.setStopic(rs.getBytes("stopic"));
				storeVO.setStoproof(rs.getBytes("stoproof"));
				storeVO.setStobh(rs.getString("stobh"));
				storeVO.setBankno(rs.getString("bankno"));
				storeVO.setBankname(rs.getString("bankname"));
				storeVO.setAccountname(rs.getString("accountname"));
				storeVO.setAccountno(rs.getString("accountno"));
				
				list.add(storeVO);
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
	public StoreVO findByStoid(String stoid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO storeVO = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_BY_ID_STMT);
			
			pstmt.setString(1, stoid);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				storeVO = new StoreVO();
				
				storeVO.setStono(rs.getString("stono"));
				storeVO.setStoid(rs.getString("stoid"));
				storeVO.setStopw(rs.getString("stopw"));
				storeVO.setStostate(rs.getString("stostate"));
				storeVO.setStoname(rs.getString("stoname"));
				storeVO.setStoemail(rs.getString("stoemail"));
				storeVO.setStoaddr(rs.getString("stoaddr"));
				storeVO.setStophone(rs.getString("stophone"));
				storeVO.setStoowner(rs.getString("stoowner"));
				storeVO.setStolong(rs.getDouble("stolong"));
				storeVO.setStolati(rs.getDouble("stolati"));
				storeVO.setStopic(rs.getBytes("stopic"));
				storeVO.setStoproof(rs.getBytes("stoproof"));
				storeVO.setStobh(rs.getString("stobh"));
				storeVO.setBankno(rs.getString("bankno"));
				storeVO.setBankname(rs.getString("bankname"));
				storeVO.setAccountname(rs.getString("accountname"));
				storeVO.setAccountno(rs.getString("accountno"));
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
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll(Map<String, String[]> map) {
		
		return null;
	}
}
