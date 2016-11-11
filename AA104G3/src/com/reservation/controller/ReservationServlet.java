package com.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

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
		Map<String, String[]> ker = req.getParameterMap();
		for (String str : ker.keySet()) {
			System.out.println("map   " + str + "    " + ker.get(str)[0]);;
		}
		System.out.println(req.getServletPath());
		System.out.println(req.getPathInfo());
		System.out.println(req.getRequestURL());
		System.out.println(req.getRequestURI());
		System.out.println(req.getQueryString());
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
				
				String initRowsPerPage = req.getParameter("rowsPerPage");
				if (initRowsPerPage == null) rowsPerPage = 10;
				else rowsPerPage = Integer.parseInt(initRowsPerPage);
				String initWhichPage = req.getParameter("whichPage");
				if (initWhichPage == null) whichPage = 1;
				else whichPage = Integer.parseInt(initWhichPage);
				
				if (rowNumber == 0)
					pageNumber = 1;
				else if (rowNumber % rowsPerPage == 0) 
					pageNumber = rowNumber / rowsPerPage;
				else pageNumber = rowNumber / rowsPerPage +1;
				
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
				e.printStackTrace();
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
				String resvno = req.getParameter("updateResvno");
				
				if (resvno == null || resvno.trim().length() == 0) {
					errorMsgs.add("請輸入訂位編號");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("requestURL");
					failureView.forward(req, res);
				} 
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
			PrintWriter out = res.getWriter();
		
			try {
				String data = req.getParameter("data");
				JSONArray jsonArray = new JSONArray(data);
				int len = jsonArray.length();
				JSONObject jsonObject = null;
				ReservationVO reservationVO = null;
				Set<ReservationVO> set = new HashSet<ReservationVO>();
				for (int i = 0; i < len; i++) {
					jsonObject = jsonArray.getJSONObject(i);
					reservationVO = new ReservationVO();
					reservationVO.setResvno(jsonObject.getString("resvno").trim());
					reservationVO.setMemno(jsonObject.getString("memno").trim());
					reservationVO.setTableno(jsonObject.getString("tableno").trim());
					reservationVO.setResvdate(java.sql.Date.valueOf(jsonObject.getString("resvdate").trim()));
					reservationVO.setResvperiod(jsonObject.getString("resvperiod").trim());
					String teamno = null;
					try {
						teamno = jsonObject.getString("teamno").trim();
					} catch (Exception e) {
					}
					reservationVO.setTeamno(teamno);
					reservationVO.setResvstate(jsonObject.getString("resvstate").trim());
					set.add(reservationVO);
				}
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/


				// Send the use back to the form, if there were errors
				
				
				/***************************2.開始修改資料*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationSvc.updateReservations(set);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				

                
                out.print("commit");
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				out.print("rollback");
			}
		}
	}

}
