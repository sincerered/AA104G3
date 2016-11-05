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
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
				Map<String, String[]> map = req.getParameterMap();
				
				/***************************2.�}�l�ƦX�d��***************************************/
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
				
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listReservations_ByCompositionQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/reservation/listReservations_ByCompositionQuery.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
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
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String resvno = req.getParameter("resvno");
				
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
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/reservation/listAllReservation.jsp�j ��  �i/dept/listReservations_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String resvno = req.getParameter("resvno").trim();
				String memno = req.getParameter("memno").trim();
				String tableno = req.getParameter("tableno").trim();				
				
				java.sql.Date resvdate = null;
				try {
					resvdate = java.sql.Date.valueOf(req.getParameter("resvdate").trim());
				} catch (IllegalArgumentException e) {
					resvdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
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
					req.setAttribute("reservationVO", reservationVO); // �t����J�榡���~��reservationVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/reservation/update_reservation_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				ReservationService reservationSvc = new ReservationService();
				reservationVO = reservationSvc.updateReservation(resvno, memno, tableno, resvdate, resvperiod, teamno, resvstate);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/				

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/reservation/update_reservation_input.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
