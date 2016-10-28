package com.stotable.model;

import java.util.List;

public interface StotableDAO_interface {
	public void insert(StotableVO stotableVO);
	public void update(StotableVO stotableVO);
	public void delete(String stono);
	public StotableVO findByPrimaryKey(String tableno);
	public List<StotableVO> getAll();
	public List<StotableVO> findByStono(String stono);
}
