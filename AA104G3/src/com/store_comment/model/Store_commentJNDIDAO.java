package com.store_comment.model;

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

public class Store_commentJNDIDAO implements Store_commentDAO_interface {
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	private static final String INSERT_STMT = "INSERT INTO store_comment (storecomno, memno, stono, comdetail, comdate, stoscore) VALUES(lpad(to_char(store_comment_SEQ.NEXTVAL),6,'0'),?,?,?,SYSDATE,?)";
	private static final String UPDATE_STMT = "UPDATE store_comment SET memno=?, stono=?, comdetail=?, comdate=?, stoscore=? WHERE storecomno=?";
	private static final String DELETE_STMT = "DELETE FROM store_comment WHERE storecomno=?";
	private static final String GET_ALL_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment";
	private static final String GET_ONE_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE storecomno=?";
	private static final String GET_BY_MEMNO_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE memno=?";
	private static final String GET_BY_STONO_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE stono=?";

	@Override
	public void insert(Store_commentVO store_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, store_commentVO.getMemno());
			pstmt.setString(2, store_commentVO.getStono());
			pstmt.setString(3, store_commentVO.getComdetail());
			pstmt.setInt(4, store_commentVO.getStoscore());
			
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
	public void update(Store_commentVO store_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, store_commentVO.getMemno());
			pstmt.setString(2, store_commentVO.getStono());
			pstmt.setString(3, store_commentVO.getComdetail());
			pstmt.setDate(4, store_commentVO.getComdate());
			pstmt.setInt(5, store_commentVO.getStoscore());
			pstmt.setString(6, store_commentVO.getStorecomno());
			
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
	public void delete(String storecomno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, storecomno);
			
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
	public Store_commentVO findByPrimaryKey(String storecomno) {
		Store_commentVO store_commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, storecomno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_commentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
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
		return store_commentVO;
	}

	@Override
	public List<Store_commentVO> getAll() {
		Store_commentVO store_commentVO = null;
		List<Store_commentVO> list = new ArrayList<Store_commentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_commentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
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
	public List<Store_commentVO> findByMemno(String memno) {
		Store_commentVO store_commentVO = null;
		List<Store_commentVO> list = new ArrayList<Store_commentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMNO_STMT);
			
			pstmt.setString(1, memno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_commentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
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
	public List<Store_commentVO> findByStono(String stono) {
		Store_commentVO store_commentVO = null;
		List<Store_commentVO> list = new ArrayList<Store_commentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMNO_STMT);
			
			pstmt.setString(1, stono);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_commentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
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
