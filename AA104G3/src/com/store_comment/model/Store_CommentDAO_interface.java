package com.store_comment.model;

import java.util.List;

public interface Store_CommentDAO_interface {
	public void insert(Store_CommentVO store_commentVO);
	public void update(Store_CommentVO store_commentVO);
	public void delete(String storecomno);
	public Store_CommentVO findByPrimaryKey(String storecomno);
	public List<Store_CommentVO> getAll();
	public List<Store_CommentVO> findByMemno(String memno);
	public List<Store_CommentVO> findByStono(String stono);
}
