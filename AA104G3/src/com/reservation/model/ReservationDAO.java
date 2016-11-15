package com.reservation.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import hibernate.util.HibernateUtil;
import jdbc.util.CompositQuery.jdbcUtil_CompositeQuery_Reservation;

public class ReservationDAO implements ReservationDAO_interface {

	private static final String GET_ALL_STMT = "from ReservationVO order by resvno";
	
	@Override
	public void insert(ReservationVO reservationVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(reservationVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(ReservationVO reservationVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(reservationVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void updates(Set<ReservationVO> set) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			for (ReservationVO reservationVO : set) {
				session.saveOrUpdate(reservationVO);
			}
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(String resvno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("delete ReservationVO where resvno=?");
			query.setParameter(0, resvno);
			System.out.println("刪除的筆數=" + query.executeUpdate());
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public ReservationVO findByPrimaryKey(String resvno) {
		ReservationVO reservationVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			reservationVO = (ReservationVO)session.get(ReservationVO.class, resvno);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
		return reservationVO;
	}

	@Override
	public List<ReservationVO> getAll() {
		List<ReservationVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

//	@Override
//	public List<ReservationVO> getAll(Map<String, String[]> map) {
//		List<ReservationVO> list = null;
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		
//		try {
//			session.beginTransaction();
//			Criteria query = session.createCriteria(ReservationVO.class);
//			Set<String> keys = map.keySet();
//			int count = 0;
//			
//			for (String key : keys) {
//				String value = map.get(key)[0];
//				if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
//					count++;
//					query = get_aCriteria_For_AnyDB(query, key, value);
//					System.out.println("有送出查詢資料的欄位數count = " + count);
//				}
//			}
//			
//			query.addOrder( Order.desc("resvno") );
//			list = query.list();
//			
//			session.getTransaction().commit();
//			
//			
//			
//		} catch (RuntimeException ex) {
//			session.getTransaction().rollback();
//			throw ex;
//		}
//		return list;
//	}
	
	@Override
	public List<ReservationVO> getAll(Map<String, String[]> map) {
		List<ReservationVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			String finalSQL = "SELECT * from reservation " 
					+ jdbcUtil_CompositeQuery_Reservation.get_WhereCondition(map);
			System.out.println("finalSQL by reservation is " + finalSQL);
			Query query = session.createSQLQuery(finalSQL).addEntity(ReservationVO.class);
			
			list = query.list();
			
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	public static Criteria get_aCriteria_For_AnyDB(Criteria query, String columnName, String value) {
		if ("memno".equals(columnName) || "tableno".equals(columnName) || "teamno".equals(columnName) || "tablestate".equals(columnName))    //用於Integer
			query.add(Restrictions.eq(columnName, value));    
		else if ("resvno".equals(columnName))   //用於Double
			query.add(Restrictions.idEq(value));               
		else if ("resvdateMax".equals(columnName))   //用於Double
			query.add(Restrictions.geProperty(columnName, "sysdate " + value));               
		else if ("resvdateMin".equals(columnName))  //用於varchar
			query.add(Restrictions.leProperty(columnName, "sysdate " + value));
		else if ("resvperiodMax".equals(columnName))                           //用於date
			query.add(Restrictions.leProperty("instr(resvperiod, '1')", value));
		else if ("resvperiodMin".equals(columnName))                           
			query.add(Restrictions.geProperty("instr(resvperiod, '1', -1) - 1", value));	
		else if ("stono".equals(columnName))                           
			query.add(Restrictions.eqProperty("instr(resvperiod, '1', -1) - 1", value));	
		return query;
	}

	@Override
	public List<ReservationVO> findByMemno(String memno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationVO> findByTableno(String tableno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationVO> findByTeamno(String teamno) {
		// TODO Auto-generated method stub
		return null;
	}

}
