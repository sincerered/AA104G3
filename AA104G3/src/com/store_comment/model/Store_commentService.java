package com.store_comment.model;

import java.sql.Date;
import java.util.List;

public class Store_commentService {
	private Store_commentDAO_interface dao;
	
	public Store_commentService() {
		dao = new Store_commentDAO();
	}
	
	public Store_commentVO addStore_Comment(String storecomno, String memno, String stono, String comdetail, Date comdate, Integer stoscore) {
		Store_commentVO store_commentVO = new Store_commentVO();
		store_commentVO.setStorecomno(storecomno);
		store_commentVO.setMemno(memno);
		store_commentVO.setStono(stono);
		store_commentVO.setComdetail(comdetail);
		store_commentVO.setComdate(comdate);
		store_commentVO.setStoscore(stoscore);
		dao.insert(store_commentVO);
		return store_commentVO;
	}
	
	public Store_commentVO updateStore_Comment(String storecomno, String memno, String stono, String comdetail, Date comdate, Integer stoscore) {
		Store_commentVO store_commentVO = new Store_commentVO();
		store_commentVO.setStorecomno(storecomno);
		store_commentVO.setMemno(memno);
		store_commentVO.setStono(stono);
		store_commentVO.setComdetail(comdetail);
		store_commentVO.setComdate(comdate);
		store_commentVO.setStoscore(stoscore);
		dao.update(store_commentVO);
		return store_commentVO;
	}

	public Store_commentVO updateStore_Comment(Store_commentVO store_commentVO) {
		dao.update(store_commentVO);
		return store_commentVO;
	}
	
	public void deleteStore_Comment(String storecomno) {
		dao.delete(storecomno);
	}
	
	public Store_commentVO getOneStore_Comment(String storecomno) {
		return dao.findByPrimaryKey(storecomno);
	}
	
	public List<Store_commentVO> getAll() {
		return dao.getAll();
	}
}