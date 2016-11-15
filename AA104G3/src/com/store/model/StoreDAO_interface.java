package com.store.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StoreDAO_interface {
	public void insert(StoreVO storeVO);
	public void update(StoreVO storeVO);
	public void delete(String stono);
	public StoreVO findByPrimaryKey(String stono);
	public List<StoreVO> getAll();
	public StoreVO findByStoid(String stoname);
	public List<StoreVO> getAll(Map<String, String[]> map);
}
