package com.store_comment.model;

import java.sql.Date;
import java.util.List;

public class Store_CommentService {
	private Store_CommentDAO_interface dao;
	
	public Store_CommentService() {
		dao = new Store_CommentDAO();
	}
	
	public Store_CommentVO addStore_Comment(String storecomno, String memno, String stono, String comdetail, Date comdate, Integer stoscore) {
		Store_CommentVO store_commentVO = new Store_CommentVO();
		store_commentVO.setStorecomno(storecomno);
		store_commentVO.setMemno(memno);
		store_commentVO.setStono(stono);
		store_commentVO.setComdetail(comdetail);
		store_commentVO.setComdate(comdate);
		store_commentVO.setStoscore(stoscore);
		dao.insert(store_commentVO);
		return store_commentVO;
	}
	
	public Store_CommentVO updateStore_Comment(String storecomno, String memno, String stono, String comdetail, Date comdate, Integer stoscore) {
		Store_CommentVO store_commentVO = new Store_CommentVO();
		store_commentVO.setStorecomno(storecomno);
		store_commentVO.setMemno(memno);
		store_commentVO.setStono(stono);
		store_commentVO.setComdetail(comdetail);
		store_commentVO.setComdate(comdate);
		store_commentVO.setStoscore(stoscore);
		dao.update(store_commentVO);
		return store_commentVO;
	}
	
	public void deleteStore_Comment(String storecomno) {
		dao.delete(storecomno);
	}
	
	public Store_CommentVO getOneStore_Comment(String storecomno) {
		return dao.findByPrimaryKey(storecomno);
	}
	
	public List<Store_CommentVO> getAll() {
		return dao.getAll();
	}
}