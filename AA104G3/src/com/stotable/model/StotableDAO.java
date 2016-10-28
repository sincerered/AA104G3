package com.stotable.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class StotableDAO implements StotableDAO_interface{
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException e){
			e.printStackTrace();
		}
		
	}
	
	private static final String INSERT_STMT = "INSERT INTO stotable (tableno, stono, tablemin, tablemax, tablecount) VALUES(lpad(to_char(stotable_SEQ.NEXTVAL),6,'0'),?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE stotable SET stono=?, tablemin=?, tablemax=?, tablecount=? WHERE tableno=?";
	private static final String DELETE_STMT = "DELETE FROM stotable where tableno=?";
	private static final String GET_ALL_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount FROM stotable";
	private static final String GET_ONE_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount FROM stotable WHERE tableno=?";
	private static final String GET_BY_STONO_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount FROM stotable WHERE stono=?";
	
	@Override
	public void insert(StotableVO stotableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, stotableVO.getStono());
			pstmt.setInt(2, stotableVO.getTablemin());
			pstmt.setInt(3, stotableVO.getTablemax());
			pstmt.setInt(4, stotableVO.getTablecount());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
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
	public void update(StotableVO stotableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, stotableVO.getStono());
			pstmt.setInt(2, stotableVO.getTablemin());
			pstmt.setInt(3, stotableVO.getTablemax());
			pstmt.setInt(4, stotableVO.getTablecount());
			pstmt.setString(5, stotableVO.getTableno());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
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
	public void delete(String tableno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, tableno);
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
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
	public StotableVO findByPrimaryKey(String tableno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StotableVO stotableVO = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, tableno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				stotableVO = new StotableVO();
				
				stotableVO.setTableno(rs.getString("tableno"));
				stotableVO.setStono(rs.getString("stono"));
				stotableVO.setTablemin(rs.getInt("tablemin"));
				stotableVO.setTablemax(rs.getInt("tablemax"));
				stotableVO.setTablecount(rs.getInt("tablecount"));
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
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
		return stotableVO;
	}

	@Override
	public List<StotableVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		StotableVO stotableVO = null;
		ResultSet rs = null;
		List<StotableVO> list = new ArrayList<StotableVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				stotableVO = new StotableVO();
				
				stotableVO.setTableno(rs.getString("tableno"));
				stotableVO.setStono(rs.getString("stono"));
				stotableVO.setTablemin(rs.getInt("tablemin"));
				stotableVO.setTablemax(rs.getInt("tablemax"));
				stotableVO.setTablecount(rs.getInt("tablecount"));
				
				list.add(stotableVO);
			}
		}catch(SQLException se){
			se.printStackTrace();
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
	public List<StotableVO> findByStono(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StotableVO stotableVO = null;
		ResultSet rs = null;
		List<StotableVO> list = new ArrayList<StotableVO>();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_STONO_STMT);
			
			pstmt.setString(1, stono);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				stotableVO = new StotableVO();
				
				stotableVO.setTableno(rs.getString("tableno"));
				stotableVO.setStono(rs.getString("stono"));
				stotableVO.setTablemin(rs.getInt("tablemin"));
				stotableVO.setTablemax(rs.getInt("tablemax"));
				stotableVO.setTablecount(rs.getInt("tablecount"));
				list.add(stotableVO);
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
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
