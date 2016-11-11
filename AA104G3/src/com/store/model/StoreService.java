package com.store.model;

import java.util.List;

public class StoreService {
	private StoreDAO_interface dao;
	
	public StoreService() {
		dao = new StoreJNDIDAO();
	}
	
	public StoreVO addStore(String stoid, String stopw, String stostate, String stoname, String stoemail, String stoaddr, String stophone, String stoowner, Double stolong, Double stolati, byte[] stopic, byte[] stoproof, String stobh, String bankno, String bankname, String accountname, String accountno) {
		StoreVO storeVO = new StoreVO();
		storeVO.setStoid(stoid);
		storeVO.setStopw(stopw);
		storeVO.setStostate(stostate);
		storeVO.setStoname(stoname);
		storeVO.setStoemail(stoemail);
		storeVO.setStoaddr(stoaddr);
		storeVO.setStophone(stophone);
		storeVO.setStoowner(stoowner);
		storeVO.setStolong(stolong);
		storeVO.setStolati(stolati);
		storeVO.setStopic(stopic);
		storeVO.setStoproof(stoproof);
		storeVO.setStobh(stobh);
		storeVO.setBankno(bankno);
		storeVO.setBankname(bankname);
		storeVO.setAccountname(accountname);
		storeVO.setAccountno(accountno);
		dao.insert(storeVO);
		return storeVO;
	}
	
	public StoreVO updateStore(String stono, String stoid, String stopw, String stostate, String stoname, String stoemail, String stoaddr, String stophone, String stoowner, Double stolong, Double stolati, byte[] stopic, byte[] stoproof, String stobh, String bankno, String bankname, String accountname, String accountno) {
		StoreVO storeVO = new StoreVO();
		storeVO.setStono(stono);
		storeVO.setStoid(stoid);
		storeVO.setStopw(stopw);
		storeVO.setStostate(stostate);
		storeVO.setStoname(stoname);
		storeVO.setStoemail(stoemail);
		storeVO.setStoaddr(stoaddr);
		storeVO.setStophone(stophone);
		storeVO.setStoowner(stoowner);
		storeVO.setStolong(stolong);
		storeVO.setStolati(stolati);
		storeVO.setStopic(stopic);
		storeVO.setStoproof(stoproof);
		storeVO.setStobh(stobh);
		storeVO.setBankno(bankno);
		storeVO.setBankname(bankname);
		storeVO.setAccountname(accountname);
		storeVO.setAccountno(accountno);
		dao.update(storeVO);
		return storeVO;		
	}

	public StoreVO updateStore(StoreVO storeVO) {
		dao.update(storeVO);
		return storeVO;		
	}
	
	public void deleteStore(String stono) {
		dao.delete(stono);
	}
	
	public StoreVO getOneStore(String stono) {
		return dao.findByPrimaryKey(stono);
	}
	
	public List<StoreVO> getAll() {
		return dao.getAll();
	}
	
	public StoreVO getOneByStoid(String stoid) {
		return dao.findByStoid(stoid);
	}
}
