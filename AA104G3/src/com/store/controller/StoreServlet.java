package com.store.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.model.StoreService;
import com.store.model.StoreVO;

/**
 * Servlet implementation class StoreServlet
 */

public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		if ("register".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String stoid = req.getParameter("stoid");
				String stopw1 = req.getParameter("stopw1");
				String stopw2 = req.getParameter("stopw2");
				if (stoid == null || stoid.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/register.jsp");
					failureView.forward(req, res);
				}
				if (stoid.indexOf(" ") != -1) {
					errorMsgs.add("帳號不能包含空白字元");
				}
				if (stopw1 == null || stopw1.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				if (stopw2 == null || stopw2.trim().length() == 0) {
					errorMsgs.add("請輸入密碼確認");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/register.jsp");
					failureView.forward(req, res);
				}
				if (!stopw1.equals(stopw2)) {
					errorMsgs.add("密碼不一樣");
				}
				
				
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String stoid = req.getParameter("stoid");
				String stopw = req.getParameter("stopw");
				if (stoid == null || stoid.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/register.jsp");
					failureView.forward(req, res);
				}
				if (stoid.indexOf(" ") != -1) {
					errorMsgs.add("帳號不能包含空白字元");
				}
				if (stopw == null || stopw.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
			
				if (stopw.indexOf(" ") != -1) {
					errorMsgs.add("密碼不能包含空白字元");
				}				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/register.jsp");
					failureView.forward(req, res);
				}
				
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneByStoid(stoid);
				if (storeVO == null) {
					errorMsgs.add("帳號或密碼錯誤");
				} else if (!stopw.equals(storeVO.getStopw())) {
					errorMsgs.add("帳號或密碼錯誤");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/register.jsp");
					failureView.forward(req, res);
				}
				HttpSession session = req.getSession();
				session.setAttribute("stono", storeVO.getStono());
				res.sendRedirect("/front-end/chenken_index.jsp");
				
			} catch (Exception e) {
				throw new ServletException(e);
			}			
		}
		if ("store_page".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String stono = req.getParameter("stono");
				if (stono == null) {
					errorMsgs.add("無此店家資料");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/chenken_index.jsp");
					failureView.forward(req, res);
				}
				
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				
				if (storeVO == null) {
					errorMsgs.add("無此店家資料");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/chenken_index.jsp");
					failureView.forward(req, res);
				}
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req
						.getRequestDispatcher("/front-end/store/store_page.jsp");
				successView.forward(req, res);
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("getTableCanReservation".equals(action)) {
			
		}
	}

}
