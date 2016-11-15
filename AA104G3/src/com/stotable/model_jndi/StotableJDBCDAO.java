package com.stotable.model_jndi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StotableJDBCDAO implements StotableDAO_interface{
		
	private static final String INSERT_STMT = "INSERT INTO stotable (tableno, stono, tablemin, tablemax, tablecount, tablestate) VALUES(lpad(to_char(stotable_SEQ.NEXTVAL),6,'0'),?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE stotable SET stono=?, tablemin=?, tablemax=?, tablecount=?, tablestate=? WHERE tableno=?";
	private static final String DELETE_STMT = "DELETE FROM stotable where tableno=?";
	private static final String GET_ALL_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount, tablestate FROM stotable";
	private static final String GET_ONE_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount, tablestate FROM stotable WHERE tableno=?";
	private static final String GET_BY_STONO_STMT = "SELECT tableno, stono, tablemin, tablemax, tablecount, tablestate FROM stotable WHERE stono=?";
	
	@Override
	public void insert(StotableVO stotableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, stotableVO.getStono());
			pstmt.setInt(2, stotableVO.getTablemin());
			pstmt.setInt(3, stotableVO.getTablemax());
			pstmt.setInt(4, stotableVO.getTablecount());
			pstmt.setInt(5, stotableVO.getTablestate());
			
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
	public void update(StotableVO stotableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, stotableVO.getStono());
			pstmt.setInt(2, stotableVO.getTablemin());
			pstmt.setInt(3, stotableVO.getTablemax());
			pstmt.setInt(4, stotableVO.getTablecount());
			pstmt.setInt(5, stotableVO.getTablestate());
			pstmt.setString(6, stotableVO.getTableno());
			
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
	public void delete(String tableno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, tableno);
			
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
	public StotableVO findByPrimaryKey(String tableno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StotableVO stotableVO = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
				stotableVO.setTablestate(rs.getInt("tablestate"));
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				stotableVO = new StotableVO();
				
				stotableVO.setTableno(rs.getString("tableno"));
				stotableVO.setStono(rs.getString("stono"));
				stotableVO.setTablemin(rs.getInt("tablemin"));
				stotableVO.setTablemax(rs.getInt("tablemax"));
				stotableVO.setTablecount(rs.getInt("tablecount"));
				stotableVO.setTablestate(rs.getInt("tablestate"));
				
				list.add(stotableVO);
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
	public List<StotableVO> findByStono(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StotableVO stotableVO = null;
		ResultSet rs = null;
		List<StotableVO> list = new ArrayList<StotableVO>();
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
				stotableVO.setTablestate(rs.getInt("tablestate"));
				list.add(stotableVO);
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
	
	public static void main(String[] args){
		System.out.println("-------------------get all");
		StotableJDBCDAO stotableDAO = new StotableJDBCDAO();
		List<StotableVO> list = stotableDAO.getAll();
		for (StotableVO stotableVO : list) {
			
			System.out.print(stotableVO.getTableno());
			System.out.print(stotableVO.getStono());
			System.out.print(stotableVO.getTablemin());
			System.out.print(stotableVO.getTablemax());
			System.out.print(stotableVO.getTablecount());
			System.out.println(stotableVO.getTablestate());
		}
		
		System.out.println("-------------------find by stono");
		List<StotableVO> list2 = stotableDAO.findByStono("000005");
		for (StotableVO stotableVO : list2) {
			
			System.out.print(stotableVO.getTableno());
			System.out.print(stotableVO.getStono());
			System.out.print(stotableVO.getTablemin());
			System.out.print(stotableVO.getTablemax());
			System.out.print(stotableVO.getTablecount());
			System.out.println(stotableVO.getTablestate());
		}
		
		System.out.println("-------------------find by pk");
		StotableVO stotableVO1 = stotableDAO.findByPrimaryKey("000011");
		System.out.print(stotableVO1.getTableno());
		System.out.print(stotableVO1.getStono());
		System.out.print(stotableVO1.getTablemin());
		System.out.print(stotableVO1.getTablemax());
		System.out.print(stotableVO1.getTablecount());
		System.out.println(stotableVO1.getTablestate());
		
		
		StotableVO stotableVO2 = new StotableVO();
		
		stotableVO2.setTableno("000005");
		stotableVO2.setStono("000003");
		stotableVO2.setTablemin(99);
		stotableVO2.setTablemax(99);
		stotableVO2.setTablecount(99);
		stotableVO2.setTablestate(99);
		
		stotableDAO.insert(stotableVO2);
		
//		stotableDAO.update(stotableVO);
		
//		stotableDAO.delete("000002");
	}
	
}
