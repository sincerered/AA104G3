package com.store.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.store.model.StoreService;
import com.store.model.StoreVO;

/**
 * Servlet implementation class StoreServlet
 */
@MultipartConfig(fileSizeThreshold=10*1024, maxFileSize=5*1024*1024, maxRequestSize=5*5*1024*1024)
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
					return;
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
					return;
				}
				if (!stopw1.equals(stopw2)) {
					errorMsgs.add("密碼不一樣");
				}
				
				
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("get_store_register".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				HttpSession session = req.getSession();
				if (session.getAttribute("stono") != null) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				if (session.getAttribute("memno") != null) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}			
				
				
				RequestDispatcher successView = req
						.getRequestDispatcher("/front-end/store/register.jsp");
				successView.forward(req, res);
				return;
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
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/login.jsp");
					failureView.forward(req, res);
					return;
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
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneByStoid(stoid);
				if (storeVO == null) {
					errorMsgs.add("帳號或密碼錯誤");
				} else if (!stopw.equals(storeVO.getStopw())) {
					errorMsgs.add("帳號或密碼錯誤");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/login.jsp");
					failureView.forward(req, res);
					return;
				}
				HttpSession session = req.getSession();
				session.setAttribute("stono", storeVO.getStono());
				res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}			
		}
		
		if ("get_stopic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String stono = req.getParameter("stono");
				if (stono == null) {
					errorMsgs.add("請選擇店家");
					return;
				}
				StoreService storeSvc = new StoreService();
				
				StoreVO storeVO = storeSvc.getOneStore(stono);
				
				ServletOutputStream out = res.getOutputStream();
				out.write(storeVO.getStopic());
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("logout".equals(action)) {
			req.getSession().invalidate();
			res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
		}

		if ("get_store_update_pwd".equals(action)) {
			HttpSession session = req.getSession();
			String stono = (String)session.getAttribute("stono");
			try {
				if (stono == null || stono.trim().length() == 0) {
					String url = "/front-end/store/register.jsp?requestURL=" + req.getServletPath();
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					
					return;
				}
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				if (storeVO == null) {
					System.out.println("stono in session but storeVO is null");
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_update_pwd.jsp");
				successView.forward(req, res);
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("update_pwd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				HttpSession session = req.getSession();
				String stono = (String)session.getAttribute("stono");
				if (stono == null || stono.trim().length() == 0) {
					res.sendRedirect("/front-end/chenken_index.jsp");
				}
				
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				
				String stopwOld = req.getParameter("stopwOld");
				if (stopwOld == null || stopwOld.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/front-end/store/store_update_pwd.jsp");
					failureView.forward(req, res);
					return;
				}
				if (!stopwOld.equals(storeVO.getStopw())) {
					errorMsgs.add("密碼錯誤");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/front-end/store/store_update_pwd.jsp");
					failureView.forward(req, res);
					return;
				}
				String stopwNew = req.getParameter("stopwNew");
				String stopwConfirm = req.getParameter("stopwConfirm");
				if (stopwNew == null || stopwNew.trim().length() == 0 || 
						stopwConfirm == null || stopwConfirm.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/front-end/store/store_update_pwd.jsp");
					failureView.forward(req, res);
					return;
				}
				if (!stopwNew.equals(stopwConfirm)) {
					errorMsgs.add("密碼錯誤");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/front-end/store/store_update_pwd.jsp");
					failureView.forward(req, res);
					return;
				}				
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("get_store_page".equals(action)) {
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
					return;
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
					return;
				}
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req
						.getRequestDispatcher("/front-end/store/get_store_page.jsp");
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("get_store_main".equals(action)) {
			HttpSession session = req.getSession();
			String stono = (String)session.getAttribute("stono");
			String memno = (String)session.getAttribute("memno");
			try {
				if (stono == null || stono.trim().length() == 0) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				if (memno != null) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;					
				}
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				if (storeVO == null) {
					System.out.println("stono in session but storeVO is null");
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				
				
				
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_main.jsp");
				successView.forward(req, res);
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("get_store_resv".equals(action)) {
			try {
				HttpSession session = req.getSession();
				String stono = (String)session.getAttribute("stono");
				if (stono == null || stono.trim().length() == 0) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				if (storeVO == null) {
					System.out.println("stono in session but storeVO is null");
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				
				
				
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_main.jsp");
				successView.forward(req, res);
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("get_store_detail".equals(action)) {
			HttpSession session = req.getSession();
			String stono = (String)session.getAttribute("stono");
			try {
				if (stono == null || stono.trim().length() == 0) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				if (storeVO == null) {
					System.out.println("stono in session but storeVO is null");
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				
				
				
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_detail.jsp");
				successView.forward(req, res);
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("get_store_update_image".equals(action)) {
			HttpSession session = req.getSession();
			String stono = (String)session.getAttribute("stono");
			try {
				if (stono == null || stono.trim().length() == 0) {
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(stono);
				if (storeVO == null) {
					System.out.println("stono in session but storeVO is null");
					res.sendRedirect(req.getContextPath() + "/front-end/chenken_index.jsp");
					return;
				}
				
				
				
				req.setAttribute("storeVO", storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_update_image.jsp");
				successView.forward(req, res);
				return;
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("update_image".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Part part = req.getPart("stopic");
				if (part == null) {
					System.out.println("stopic is null");
					errorMsgs.add("請選擇圖片");
				}
				long fileSize = part.getSize();
				if (fileSize == 0) {
					errorMsgs.add("請選擇圖片");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/store/store_update_image.jsp");
					failureView.forward(req, res);
					return;
				}
				
				InputStream inputStream = part.getInputStream();
				byte[] bytes = new byte[(int)fileSize];
				inputStream.read(bytes);
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore((String)req.getSession().getAttribute("stono"));
				storeVO.setStopic(bytes);
				storeSvc.updateStore(storeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/store/store_update_image.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		}
		
	}

}
