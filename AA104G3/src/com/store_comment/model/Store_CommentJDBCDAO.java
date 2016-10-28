package com.store_comment.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.store_comment.model.Store_CommentVO;

public class Store_CommentJDBCDAO implements Store_CommentDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO store_comment (storecomno, memno, stono, comdetail, comdate, stoscore) VALUES(lpad(to_char(store_comment_SEQ.NEXTVAL),6,'0'),?,?,?,SYSDATE,?)";
	private static final String UPDATE_STMT = "UPDATE store_comment SET memno=?, stono=?, comdetail=?, comdate=?, stoscore=? WHERE storecomno=?";
	private static final String DELETE_STMT = "DELETE FROM store_comment WHERE storecomno=?";
	private static final String GET_ALL_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment";
	private static final String GET_ONE_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE storecomno=?";
	private static final String GET_BY_MEMNO_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE memno=?";
	private static final String GET_BY_STONO_STMT = "SELECT storecomno, memno, stono, comdetail, comdate, stoscore FROM store_comment WHERE stono=?";

	@Override
	public void insert(Store_CommentVO store_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, store_commentVO.getMemno());
			pstmt.setString(2, store_commentVO.getStono());
			pstmt.setString(3, store_commentVO.getComdetail());
			pstmt.setInt(4, store_commentVO.getStoscore());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
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
	public void update(Store_CommentVO store_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, store_commentVO.getMemno());
			pstmt.setString(2, store_commentVO.getStono());
			pstmt.setString(3, store_commentVO.getComdetail());
			pstmt.setDate(4, store_commentVO.getComdate());
			pstmt.setInt(5, store_commentVO.getStoscore());
			pstmt.setString(6, store_commentVO.getStorecomno());
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, storecomno);
			
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
	public Store_CommentVO findByPrimaryKey(String storecomno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Store_CommentVO store_commentVO = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, storecomno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_CommentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			
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
	public List<Store_CommentVO> getAll() {
		Store_CommentVO store_commentVO = null;
		List<Store_CommentVO> list = new ArrayList<Store_CommentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_CommentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
	public List<Store_CommentVO> findByMemno(String memno) {
		Store_CommentVO store_commentVO = null;
		List<Store_CommentVO> list = new ArrayList<Store_CommentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(GET_BY_MEMNO_STMT);
			
			pstmt.setString(1, memno);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_CommentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
	public List<Store_CommentVO> findByStono(String stono) {
		Store_CommentVO store_commentVO = null;
		List<Store_CommentVO> list = new ArrayList<Store_CommentVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(GET_BY_MEMNO_STMT);
			
			pstmt.setString(1, stono);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				store_commentVO = new Store_CommentVO();
				
				store_commentVO.setStorecomno(rs.getString("storecomno"));
				store_commentVO.setMemno(rs.getString("memno"));
				store_commentVO.setStono(rs.getString("stono"));
				store_commentVO.setComdetail(rs.getString("comdetail"));
				store_commentVO.setComdate(rs.getDate("comdate"));
				store_commentVO.setStoscore(rs.getInt("stoscore"));
				
				list.add(store_commentVO);
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
	
	public static void main(String[] argd) {
		Store_CommentJDBCDAO  store_commentDAO = new Store_CommentJDBCDAO();
		List<Store_CommentVO> list1 = store_commentDAO.getAll();
		List<Store_CommentVO> list2 = store_commentDAO.findByMemno("000002");
		List<Store_CommentVO> list3 = store_commentDAO.findByStono("000002");
		
		for (Store_CommentVO store_commentVO : list1) {
			System.out.print(store_commentVO.getStorecomno());
			System.out.print(store_commentVO.getMemno());
			System.out.print(store_commentVO.getStono());
			System.out.print(store_commentVO.getComdetail());
			System.out.print(store_commentVO.getComdate());
			System.out.println(store_commentVO.getStoscore());
		}	
		for (Store_CommentVO store_commentVO : list2) {
			System.out.print(store_commentVO.getStorecomno());
			System.out.print(store_commentVO.getMemno());
			System.out.print(store_commentVO.getStono());
			System.out.print(store_commentVO.getComdetail());
			System.out.print(store_commentVO.getComdate());
			System.out.println(store_commentVO.getStoscore());
		}	
		for (Store_CommentVO store_commentVO : list3) {
			System.out.print(store_commentVO.getStorecomno());
			System.out.print(store_commentVO.getMemno());
			System.out.print(store_commentVO.getStono());
			System.out.print(store_commentVO.getComdetail());
			System.out.print(store_commentVO.getComdate());
			System.out.println(store_commentVO.getStoscore());
		}	
		
		Store_CommentVO store_commentVO = store_commentDAO.findByPrimaryKey("000001");
		System.out.print(store_commentVO.getStorecomno());
		System.out.print(store_commentVO.getMemno());
		System.out.print(store_commentVO.getStono());
		System.out.print(store_commentVO.getComdetail());
		System.out.print(store_commentVO.getComdate());
		System.out.println(store_commentVO.getStoscore());

		Store_CommentVO store_commentVO2 = new Store_CommentVO();
		store_commentVO2.setStorecomno("000001");
		store_commentVO2.setMemno("000002");
		store_commentVO2.setStono("000003");
		store_commentVO2.setComdetail("fsafwegsdgasdfewf");
		store_commentVO2.setComdate(new Date(new java.util.Date().getTime()));
		store_commentVO2.setStoscore(1);
		
		store_commentDAO.insert(store_commentVO2);
		store_commentDAO.update(store_commentVO2);
		store_commentDAO.delete("000004");
		
	}

}
