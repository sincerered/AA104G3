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
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
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
				
				
				
				/***************************2.�}�l�ƦX�d��***************************************/
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
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listReservations_ByCompositionQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/reservation/listReservations_ByCompositionQuery.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
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
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String resvno = req.getParameter("resvno");
				
				if (resvno == null || resvno.trim().length() == 0) {
					errorMsgs.add("�п�J�q��s��");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("requestURL");
					failureView.forward(req, res);
				} 
				/***************************2.�}�l�d�߸��****************************************/
				ReservationService reservationSvc = new ReservationService();
				ReservationVO reservationVO = reservationSvc.getOneReservation(resvno);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("reservationVO", reservationVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/reservation/update_reservation_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_emp_input.jsp
				System.out.println("1321321321321");
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z************************************/
			} catch (Exception e) {
				System.out.println(e);
				errorMsgs.add("�ק��ƨ��X�ɥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}			
		}
		if ("updates".equals(action)) { // �Ӧ�update_reservation_input.jsp���ШD
			
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
						errorMsgs.add("�L��J�q��s��");
					} else if (!resvno.matches("^\\d{6}$")) {
						errorMsgs.add("�q��s���u��O6�쪺�Ʀr");
					}
					
					String memno = jsonObject.getString("memno");
					if (memno == null || memno.trim().length() == 0) {
						errorMsgs.add("�п�J�|���s��");
					} else if (!memno.matches("^\\d{6}$")) {
						errorMsgs.add("�|���s���u��O6�쪺�Ʀr");
					}
					String tableno = jsonObject.getString("tableno");
					if (tableno == null || tableno.trim().length() == 0) {
						errorMsgs.add("�п�J���s��");
					} else if (!tableno.matches("^\\d{6}$")) {
						errorMsgs.add("���s���u��O6�쪺�Ʀr");
					}
					String resvdate = jsonObject.getString("resvdate");
					if (resvdate == null || resvdate.trim().length() == 0) {
						errorMsgs.add("�п�J�q����");
					}
					java.sql.Date date = null;
					try {
						date = java.sql.Date.valueOf(resvdate);
					} catch (IllegalArgumentException e) {
						errorMsgs.add("����榡���~,����yyyy-mm-dd");
					}
					String resvperiod = jsonObject.getString("resvperiod");
					if (resvperiod == null || resvperiod.trim().length() == 0) {
						errorMsgs.add("�п�J�q��ɶ�");
					} else if (!resvperiod.matches("^[0-1]{24}$")) {
						errorMsgs.add("�q��ɶ��榡���~");
					}
					String teamno = jsonObject.getString("teamno");
					if (teamno == null || teamno.trim().length() == 0) {
						teamno = null;
					} else if (!teamno.matches("^\\d{6}$")) {
						errorMsgs.add("�q��s���u��O6�쪺�Ʀr�εL��J");
					}
					String resvstate = jsonObject.getString("resvstate");
					if (resvstate == null || resvstate.trim().length() == 0) {
						errorMsgs.add("�L��J�q��s��");
					} else if (!resvstate.matches("^\\d{1,2}$")) {
						errorMsgs.add("�q�쪬�A�u��O1��2�쪺�Ʀr");
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
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/


				// Send the use back to the form, if there were errors
				
				
				/***************************2.�}�l�ק���*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationSvc.updateReservations(set);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/				

                
                out.print("commit");
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				out.print(errorMsgs);
			}
		}
		
		if ("update".equals(action)) { // �Ӧ�update_reservation_input.jsp���ШD
			System.out.println("into resv controller action=update");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			ReservationVO reservationVO = null;
			try {
				String resvno = req.getParameter("resvno");
				if (resvno == null || resvno.trim().length() == 0) {
					errorMsgs.add("�L��J�q��s��");
				} else if (!resvno.matches("^\\d{6}$")) {
					errorMsgs.add("�q��s���u��O6�쪺�Ʀr");
				}
				
				String memno = req.getParameter("memno");
				if (memno == null || memno.trim().length() == 0) {
					errorMsgs.add("�п�J�|���s��");
				} else if (!memno.matches("^\\d{6}$")) {
					errorMsgs.add("�|���s���u��O6�쪺�Ʀr");
				}
				String tableno = req.getParameter("tableno");
				if (tableno == null || tableno.trim().length() == 0) {
					errorMsgs.add("�п�J���s��");
				} else if (!tableno.matches("^\\d{6}$")) {
					errorMsgs.add("���s���u��O6�쪺�Ʀr");
				}
				String resvdate = req.getParameter("resvdate");
				if (resvdate == null || resvdate.trim().length() == 0) {
					errorMsgs.add("�п�J�q����");
				}
				java.sql.Date date = null;
				try {
					date = java.sql.Date.valueOf(resvdate);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("����榡���~,����yyyy-mm-dd");
					date = new java.sql.Date(System.currentTimeMillis());
				}
				String resvperiod = req.getParameter("resvperiod");
				if (resvperiod == null || resvperiod.trim().length() == 0) {
					errorMsgs.add("�п�J�q��ɶ�");
				} else if (!resvperiod.matches("^[0-1]{24}$")) {
					errorMsgs.add("�q��ɶ��榡���~");
				}
				String teamno = req.getParameter("teamno");
				if (teamno == null || teamno.trim().length() == 0) {
					teamno = null;
				} else if (!teamno.matches("^\\d{6}$")) {
					errorMsgs.add("�q��s���u��O6�쪺�Ʀr�εL��J");
				}
				String resvstate = req.getParameter("resvstate");
				if (resvstate == null || resvstate.trim().length() == 0) {
					errorMsgs.add("�L��J�q��s��");
				} else if (!resvstate.matches("^\\d{1,2}$")) {
					errorMsgs.add("�q�쪬�A�u��O1��2�쪺�Ʀr");
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
					
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/


				// Send the use back to the form, if there were errors
				
				
				/***************************2.�}�l�ק���*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationVO = reservationSvc.updateReservation(resvno, memno, tableno, date, resvperiod, teamno, resvstate);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				
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
				
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listReservations_ByCompositionQuery", list); // ��Ʈw���X��list����,�s�Jrequest
								
				
				req.setAttribute("listReservations_ByCompositionQuery", list);
				
				String requestURL = req.getParameter("requestURL");
                RequestDispatcher successView = req.getRequestDispatcher(requestURL);
                successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
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
