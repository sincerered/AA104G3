package com.store.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.reservation.model.ReservationVO;

import hibernate.util.HibernateUtil;
import jdbc.util.CompositQuery.jdbcUtil_CompositeQuery_Reservation;

public class StoreDAO implements StoreDAO_interface {

	private static final String GET_ALL_STMT = "from StoreVO order by stono";
	
	@Override
	public void insert(StoreVO storeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(storeVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void update(StoreVO storeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(storeVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(String stono) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			StoreVO storeVO = (StoreVO) session.get(StoreVO.class, stono);
			session.delete(storeVO);
			
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public StoreVO findByPrimaryKey(String stono) {
		StoreVO storeVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			storeVO = (StoreVO)session.get(StoreVO.class, stono);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {
		List<StoreVO> list = null;
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

	@Override
	public StoreVO findByStoid(String stoid) {
		StoreVO storeVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from StoreVO where stoid = ?");
			query.setParameter(0, stoid);
			List<StoreVO> list = query.list();
			if (list.size() > 0) {
				storeVO = list.get(0);
			}
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return storeVO;
	}
	

	@Override
	public List<StoreVO> getAll(Map<String, String[]> map) {
		List<StoreVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
		session.beginTransaction();
		Criteria query = session.createCriteria(StoreVO.class);
		Set<String> keys = map.keySet();
		int count = 0;
		
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				query = get_aCriteria_For_AnyDB(query, key, value);
				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		query.addOrder( Order.desc("stono") );
		list = query.list();
		
		session.getTransaction().commit();
		
		
		
	} catch (RuntimeException ex) {
		session.getTransaction().rollback();
		throw ex;
	}
	return list;
}
	
	public static Criteria get_aCriteria_For_AnyDB(Criteria query, String columnName, String value) {
		if ("stostate".equals(columnName) || "stoid".equals(columnName))    //用於Integer
			query.add(Restrictions.eq(columnName, value));    
		else if ("stono".equals(columnName))   //用於Double
			query.add(Restrictions.idEq(value));               
		else if ("stoname".equals(columnName))   //用於Double
			query.add(Restrictions.like(columnName, "%" + value + "%"));               
		else if ("stoaddr".equals(columnName))  //用於varchar
			query.add(Restrictions.like(columnName, "%" + value + "%"));
		
		return query;
	}

}
