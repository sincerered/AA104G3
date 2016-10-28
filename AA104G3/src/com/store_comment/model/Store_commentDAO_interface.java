package com.store_comment.model;

import java.util.List;

public interface Store_commentDAO_interface {
	public void insert(Store_commentVO store_commentVO);
	public void update(Store_commentVO store_commentVO);
	public void delete(String storecomno);
	public Store_commentVO findByPrimaryKey(String storecomno);
	public List<Store_commentVO> getAll();
	public List<Store_commentVO> findByMemno(String memno);
	public List<Store_commentVO> findByStono(String stono);
}
