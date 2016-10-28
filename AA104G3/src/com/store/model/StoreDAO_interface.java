package com.store.model;

import java.util.List;

public interface StoreDAO_interface {
	public void insert(StoreVO storeVO);
	public void update(StoreVO storeVO);
	public void delete(String stono);
	public StoreVO findByPrimaryKey(String stono);
	public List<StoreVO> getAll();
	public StoreVO findByStoid(String stoname);
}
