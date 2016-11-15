package com.stotable.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.util.HibernateUtil;

public class StotableDAO implements StotableDAO_interface {

	private static final String GET_ALL_STMT = "from StotableVO order by tableno";
	
	@Override
	public void insert(StotableVO stotableVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			session.saveOrUpdate(stotableVO);
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(StotableVO stotableVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			session.saveOrUpdate(stotableVO);
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(String tableno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			StotableVO stotableVO = new StotableVO();
			stotableVO.setTableno(tableno);
			session.delete(stotableVO);
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public StotableVO findByPrimaryKey(String tableno) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StotableVO stotableVO = null;
		
		try {
			session.beginTransaction();

			stotableVO = (StotableVO)session.get(StotableVO.class, tableno);
			
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return stotableVO;
	}

	@Override
	public List<StotableVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StotableVO> list = null;
		
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
	public List<StotableVO> findByStono(String stono) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
