package com.reservation.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.reservation.model.ReservationService;
import com.reservation.model.ReservationVO;

/**
 * Servlet implementation class ReservationServlet
 */
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		
		if ("listReservations_ByCompositionQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				Map<String, String[]> map = req.getParameterMap();
				
				/***************************2.開始複合查詢***************************************/
				ReservationService reservationSvc = new ReservationService();
				List<ReservationVO> list  = reservationSvc.getAll(map);
				
				int rowNumber = list.size();
				int rowsPerPage = 0; 
				int whichPage = 0; 
				int pageNumber = 0;
				int[] pageIndexArray = null;
				
				String tempRowsPerPage = req.getParameter("rowsPerPage");
				if (tempRowsPerPage == null) rowsPerPage = 10;
				else rowsPerPage = Integer.parseInt(tempRowsPerPage);
				String tempWhichPage = req.getParameter("whichPage");
				if (tempWhichPage == null) whichPage = 1;
				else whichPage = Integer.parseInt(tempWhichPage);
				
				if (rowNumber % rowsPerPage == 0) 
					pageNumber = rowNumber / rowsPerPage;
				else pageNumber = rowNumber / rowsPerPage + 1;
				
				if (whichPage >= pageNumber) {
					whichPage = pageNumber;
					list = list.subList((whichPage-1)*rowsPerPage, rowNumber);
				} else list = list.subList((whichPage-1)*rowsPerPage, whichPage*rowsPerPage);
				
				pageIndexArray = IntStream.rangeClosed(1, pageNumber).toArray();
				
				req.setAttribute("rowNumber", rowNumber);
				req.setAttribute("rowsPerPage", rowsPerPage);
				req.setAttribute("whichPage", whichPage);
				req.setAttribute("pageNumber", pageNumber);
				req.setAttribute("pageIndexArray", pageIndexArray);
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listReservations_ByCompositionQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/reservation/listReservations_ByCompositionQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/chenken_index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
			
			try {
				/***************************1.接收請求參數****************************************/
				String resvno = req.getParameter("resvno");
				
				/***************************2.開始查詢資料****************************************/
				ReservationService reservationSvc = new ReservationService();
				ReservationVO reservationVO = reservationSvc.getOneReservation(resvno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("reservationVO", reservationVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/reservation/update_reservation_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				System.out.println("1321321321321");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				System.out.println(e);
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}			
		}
		if ("updates".equals(action)) { // 來自update_reservation_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/reservation/listAllReservation.jsp】 或  【/dept/listReservations_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String resvno = req.getParameter("resvno").trim();
				String memno = req.getParameter("memno").trim();
				String tableno = req.getParameter("tableno").trim();				
				
				java.sql.Date resvdate = null;
				try {
					resvdate = java.sql.Date.valueOf(req.getParameter("resvdate").trim());
				} catch (IllegalArgumentException e) {
					resvdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String resvperiod = req.getParameter("resvperiod").trim();
				String teamno = req.getParameter("teamno").trim();
				String resvstate = req.getParameter("resvstate").trim();

				


				ReservationVO reservationVO = new ReservationVO();
				reservationVO.setResvno(resvno);
				reservationVO.setMemno(memno);
				reservationVO.setTableno(tableno);
				reservationVO.setResvdate(resvdate);
				reservationVO.setResvperiod(resvperiod);
				reservationVO.setTeamno(teamno);
				reservationVO.setResvstate(resvstate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reservationVO", reservationVO); // 含有輸入格式錯誤的reservationVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/reservation/update_reservation_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationVO = reservationSvc.updateReservation(resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/reservation/update_reservation_input.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
