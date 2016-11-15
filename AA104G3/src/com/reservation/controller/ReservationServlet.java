package com.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.reservation.model.ReservationService;
import com.reservation.model.ReservationVO;
import com.stotable.model.StotableVO;

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
				HttpSession session = req.getSession();
				
				String initWhichPage = req.getParameter("whichPage");
				
				String compositeQuery = req.getParameter("compositeQuery");
				System.out.println("compositeQuery:    " + compositeQuery);
				if (compositeQuery == null || compositeQuery.trim().length() == 0) compositeQuery = "{}";
				
				HashMap<String, String[]> map = new HashMap<String, String[]>();
				JSONObject jsonObject = new JSONObject(compositeQuery);
				Iterator<String> ite = jsonObject.keys();
				while(ite.hasNext()) {
					String key = ite.next();
					map.put(key, new String[] {jsonObject.getString(key)});
				}
				
				req.setAttribute("queryMap", map);
				
				if (initWhichPage == null || initWhichPage.trim().length() == 0) {
					initWhichPage = "1";
				}
				
				
				
				/***************************2.開始複合查詢***************************************/
				ReservationService reservationSvc = new ReservationService();
				List<ReservationVO> list  = reservationSvc.getAll(map);
				
				
				
				int rowNumber = list.size();
				int rowsPerPage = 0;
				int whichPage = Integer.parseInt(initWhichPage);
				int pageNumber = 0;
				int[] pageIndexArray = null;
				
				System.out.println(req.getParameter("rowsPerPage"));
				String initRowsPerPage = req.getParameter("rowsPerPage");
				if (initRowsPerPage == null || initRowsPerPage.trim().length() == 0) rowsPerPage = 10;
				else rowsPerPage = Integer.parseInt(initRowsPerPage);
				
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
				req.setAttribute("pageNumber", pageNumber);
				req.setAttribute("pageIndexArray", pageIndexArray);
				
				System.out.println("0000000000000 list.size()      " + list.size());
				System.out.println(list.get(0).getMemno());
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
				String resvno = req.getParameter("resvno");
				
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
			res.setCharacterEncoding("utf-8");
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
					
					String resvno = jsonObject.getString("resvno");
					if (resvno == null || resvno.trim().length() == 0) {
						errorMsgs.add("無輸入訂位編號");
					} else if (!resvno.matches("^\\d{6}$")) {
						errorMsgs.add("訂位編號只能是6位的數字");
					}
					
					String memno = jsonObject.getString("memno");
					if (memno == null || memno.trim().length() == 0) {
						errorMsgs.add("請輸入會員編號");
					} else if (!memno.matches("^\\d{6}$")) {
						errorMsgs.add("會員編號只能是6位的數字");
					}
					String tableno = jsonObject.getString("tableno");
					if (tableno == null || tableno.trim().length() == 0) {
						errorMsgs.add("請輸入桌位編號");
					} else if (!tableno.matches("^\\d{6}$")) {
						errorMsgs.add("桌位編號只能是6位的數字");
					}
					String resvdate = jsonObject.getString("resvdate");
					if (resvdate == null || resvdate.trim().length() == 0) {
						errorMsgs.add("請輸入訂位日期");
					}
					java.sql.Date date = null;
					try {
						date = java.sql.Date.valueOf(resvdate);
					} catch (IllegalArgumentException e) {
						errorMsgs.add("日期格式錯誤,應為yyyy-mm-dd");
					}
					String resvperiod = jsonObject.getString("resvperiod");
					if (resvperiod == null || resvperiod.trim().length() == 0) {
						errorMsgs.add("請輸入訂位時間");
					} else if (!resvperiod.matches("^[0-1]{24}$")) {
						errorMsgs.add("訂位時間格式錯誤");
					}
					String teamno = jsonObject.getString("teamno");
					if (teamno == null || teamno.trim().length() == 0) {
						teamno = null;
					} else if (!teamno.matches("^\\d{6}$")) {
						errorMsgs.add("訂位編號只能是6位的數字或無輸入");
					}
					String resvstate = jsonObject.getString("resvstate");
					if (resvstate == null || resvstate.trim().length() == 0) {
						errorMsgs.add("無輸入訂位編號");
					} else if (!resvstate.matches("^\\d{1,2}$")) {
						errorMsgs.add("訂位狀態只能是1到2位的數字");
					}				
					
					if (!errorMsgs.isEmpty()) {
						throw new ServletException();
					}
					
					reservationVO = new ReservationVO();
					reservationVO.setResvno(resvno);
					reservationVO.setMemno(memno);
					StotableVO stotableVO = new StotableVO();
					stotableVO.setTableno(tableno);
					reservationVO.setStotableVO(stotableVO);
					reservationVO.setResvdate(date);
					reservationVO.setResvperiod(resvperiod);
					reservationVO.setTeamno(teamno);
					reservationVO.setResvstate(resvstate);
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
				out.print(errorMsgs);
			}
		}
		
		if ("update".equals(action)) { // 來自update_reservation_input.jsp的請求
			System.out.println("into resv controller action=update");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			ReservationVO reservationVO = null;
			try {
				String resvno = req.getParameter("resvno");
				if (resvno == null || resvno.trim().length() == 0) {
					errorMsgs.add("無輸入訂位編號");
				} else if (!resvno.matches("^\\d{6}$")) {
					errorMsgs.add("訂位編號只能是6位的數字");
				}
				
				String memno = req.getParameter("memno");
				if (memno == null || memno.trim().length() == 0) {
					errorMsgs.add("請輸入會員編號");
				} else if (!memno.matches("^\\d{6}$")) {
					errorMsgs.add("會員編號只能是6位的數字");
				}
				String tableno = req.getParameter("tableno");
				if (tableno == null || tableno.trim().length() == 0) {
					errorMsgs.add("請輸入桌位編號");
				} else if (!tableno.matches("^\\d{6}$")) {
					errorMsgs.add("桌位編號只能是6位的數字");
				}
				String resvdate = req.getParameter("resvdate");
				if (resvdate == null || resvdate.trim().length() == 0) {
					errorMsgs.add("請輸入訂位日期");
				}
				java.sql.Date date = null;
				try {
					date = java.sql.Date.valueOf(resvdate);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("日期格式錯誤,應為yyyy-mm-dd");
					date = new java.sql.Date(System.currentTimeMillis());
				}
				String resvperiod = req.getParameter("resvperiod");
				if (resvperiod == null || resvperiod.trim().length() == 0) {
					errorMsgs.add("請輸入訂位時間");
				} else if (!resvperiod.matches("^[0-1]{24}$")) {
					errorMsgs.add("訂位時間格式錯誤");
				}
				String teamno = req.getParameter("teamno");
				if (teamno == null || teamno.trim().length() == 0) {
					teamno = null;
				} else if (!teamno.matches("^\\d{6}$")) {
					errorMsgs.add("訂位編號只能是6位的數字或無輸入");
				}
				String resvstate = req.getParameter("resvstate");
				if (resvstate == null || resvstate.trim().length() == 0) {
					errorMsgs.add("無輸入訂位編號");
				} else if (!resvstate.matches("^\\d{1,2}$")) {
					errorMsgs.add("訂位狀態只能是1到2位的數字");
				}				
				
			
				
				
				reservationVO = new ReservationVO();
				reservationVO.setResvno(resvno);
				reservationVO.setMemno(memno);
				StotableVO stotableVO = new StotableVO();
				stotableVO.setTableno(tableno);
				reservationVO.setStotableVO(stotableVO);
				reservationVO.setResvdate(date);
				reservationVO.setResvperiod(resvperiod);
				reservationVO.setTeamno(teamno);
				reservationVO.setResvstate(resvstate);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reservationVO", reservationVO);
					RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reservation/update_reservation_input.jsp");
					failureView.forward(req, res);
				}
					
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/


				// Send the use back to the form, if there were errors
				
				
				/***************************2.開始修改資料*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationVO = reservationSvc.updateReservation(resvno, memno, tableno, date, resvperiod, teamno, resvstate);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				String compositeQuery = req.getParameter("compositeQuery");
				if (compositeQuery == null || compositeQuery.trim().length() == 0) {
					compositeQuery = "{}";
				}
				
				HashMap<String, String[]> map = new HashMap<String, String[]>();
				JSONObject jsonObject = new JSONObject(compositeQuery);
				Iterator<String> ite = jsonObject.keys();
				while(ite.hasNext()) {
					String key = ite.next();
					map.put(key, new String[] {jsonObject.getString(key)});
				}
				
				req.setAttribute("queryMap", map);
				
				List<ReservationVO> list = reservationSvc.getAll(map);
				
				
				
				String initWhichPage = req.getParameter("whichPage");
				if (initWhichPage == null || initWhichPage.trim().length() == 0) initWhichPage = "10";
				int rowNumber = list.size();
				int rowsPerPage = 0;
				int whichPage = Integer.parseInt(initWhichPage);
				int pageNumber = 0;
				int[] pageIndexArray = null;
				
				System.out.println(req.getParameter("rowsPerPage"));
				String initRowsPerPage = req.getParameter("rowsPerPage");
				if (initRowsPerPage == null || initRowsPerPage.trim().length() == 0) rowsPerPage = 10;
				else rowsPerPage = Integer.parseInt(initRowsPerPage);
				
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
				req.setAttribute("pageNumber", pageNumber);
				req.setAttribute("pageIndexArray", pageIndexArray);
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listReservations_ByCompositionQuery", list); // 資料庫取出的list物件,存入request
								
				
				req.setAttribute("listReservations_ByCompositionQuery", list);
				
				String requestURL = req.getParameter("requestURL");
                RequestDispatcher successView = req.getRequestDispatcher(requestURL);
                successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("reservationVO", reservationVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reservation/update_reservation_input.jsp");
					failureView.forward(req, res);
			}
		}
	}

}
