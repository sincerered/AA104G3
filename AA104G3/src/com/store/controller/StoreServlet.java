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
				if (errorMsgs.isEmpty()) {
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
				
				
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}

}
