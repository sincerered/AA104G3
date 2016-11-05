package com.store.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StoreJDBCDAO implements StoreDAO_interface{
	private static Connection con;
	static {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException se){
			se.printStackTrace();
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
	public void update(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
	public void delete(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, stono);
			
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
	public StoreVO findByPrimaryKey(String stono) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO storeVO = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
	public StoreVO findByStoid(String stoid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO storeVO = null;
		ResultSet rs = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott2", "0000");
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
		return storeVO;
	}
	
	@Override
	public Set<StoreVO> getAll(Map<String, String[]> map) {
		
		return null;
	}
	
	public static void main(String[] args){
		StoreJDBCDAO storeDAO = new StoreJDBCDAO();
		List<StoreVO> list = storeDAO.getAll();
		for (StoreVO storeVO : list) {
			
			System.out.print(storeVO.getStono());
			System.out.print(storeVO.getStoid());
			System.out.print(storeVO.getStopw());
			System.out.print(storeVO.getStostate());
			System.out.print(storeVO.getStoname());
			System.out.print(storeVO.getStoemail());
			System.out.print(storeVO.getStoaddr());
			System.out.print(storeVO.getStophone());
			System.out.print(storeVO.getStoowner());
			System.out.print(storeVO.getStolong());
			System.out.print(storeVO.getStolati());
			System.out.print(storeVO.getStopic());
			System.out.print(storeVO.getStoproof());
			System.out.print(storeVO.getStobh());
			System.out.print(storeVO.getBankno());
			System.out.print(storeVO.getBankname());
			System.out.print(storeVO.getAccountname());
			System.out.println(storeVO.getAccountno());
		}
		
		StoreVO storeVO1 = storeDAO.findByPrimaryKey("000001");
		System.out.print(storeVO1.getStono());
		System.out.print(storeVO1.getStoid());
		System.out.print(storeVO1.getStopw());
		System.out.print(storeVO1.getStostate());
		System.out.print(storeVO1.getStoname());
		System.out.print(storeVO1.getStoemail());
		System.out.print(storeVO1.getStoaddr());
		System.out.print(storeVO1.getStophone());
		System.out.print(storeVO1.getStoowner());
		System.out.print(storeVO1.getStolong());
		System.out.print(storeVO1.getStolati());
		System.out.print(storeVO1.getStopic());
		System.out.print(storeVO1.getStoproof());
		System.out.print(storeVO1.getStobh());
		System.out.print(storeVO1.getBankno());
		System.out.print(storeVO1.getBankname());
		System.out.print(storeVO1.getAccountname());
		System.out.println(storeVO1.getAccountno());		
		
		StoreVO storeVO2 = new StoreVO();
		byte[] bytes = null;
		try {
			FileInputStream fin = new FileInputStream("C:/AA104_WebApp/apache-tomcat-7.0.68/webapps/ROOT/tomcat.png");
			bytes = new byte[fin.available()];
			fin.read(bytes);
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		storeVO2.setStono("000001");
		storeVO2.setStoid("aaaaa");
		storeVO2.setStopw("aaaaa");
		storeVO2.setStostate("1");
		storeVO2.setStoname("aaaaa");
		storeVO2.setStoemail("aaaaa");
		storeVO2.setStoaddr("aaaaa");
		storeVO2.setStophone("aaaaa");
		storeVO2.setStoowner("aaaaa");
		storeVO2.setStolong(20.1111);
		storeVO2.setStolati(11.0001);
		storeVO2.setStopic(bytes);
		storeVO2.setStoproof(null);
		storeVO2.setStobh("aaaaa");
		storeVO2.setBankno("aaa");
		storeVO2.setBankname("aaaaa");
		storeVO2.setAccountname("aaaaa");
		storeVO2.setAccountno("aaaaa");
		
		
		storeDAO.insert(storeVO2);
		storeDAO.update(storeVO2);
//		storeDAO.delete("000001");
	}

	
}
