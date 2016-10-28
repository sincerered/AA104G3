package com.stotable.model;

import java.util.List;



public class StotableService {
	private StotableDAO_interface dao;
	
	public StotableService() {
		dao = new StotableDAO();
	}
	
	public StotableVO addStotable(String tableno, String stono, Integer tablemin, Integer tablemax, Integer tablecount, Integer tablestate) {
		StotableVO stotableVO = new StotableVO();
		stotableVO.setTableno(tableno);
		stotableVO.setStono(stono);
		stotableVO.setTablemin(tablemin);
		stotableVO.setTablemax(tablemax);
		stotableVO.setTablecount(tablecount);
		stotableVO.setTablestate(tablestate);
		dao.insert(stotableVO);
		return stotableVO;
	}
	
	public StotableVO updateStotable(String tableno, String stono, Integer tablemin, Integer tablemax, Integer tablecount, Integer tablestate) {
		StotableVO stotableVO = new StotableVO();
		stotableVO.setTableno(tableno);
		stotableVO.setStono(stono);
		stotableVO.setTablemin(tablemin);
		stotableVO.setTablemax(tablemax);
		stotableVO.setTablecount(tablecount);
		stotableVO.setTablestate(tablestate);	
		
		dao.update(stotableVO);
		return stotableVO;		
	}
	
	public void deleteStotable(String tableno) {
		dao.delete(tableno);
	}
	
	public StotableVO getOneStotable(String tableno) {
		return dao.findByPrimaryKey(tableno);
	}
	
	public List<StotableVO> getAll() {
		return dao.getAll();
	}
}