package com.reservation.controller;

import java.util.*;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.reservation.model_jndi.ReservationService;
import com.reservation.model_jndi.ReservationVO;
import com.store.model.StoreService;
import com.stotable.model_jndi.StotableService;
import com.stotable.model_jndi.StotableVO;


@ServerEndpoint("/ReservationServer/{memno}/{stono}")
public class ReservationServer {
	private static final Map<String, Map> allStore = new HashMap<String, Map>();
	private static final Map<String, Set> allMember = new HashMap<String, Set>();
	
	@OnOpen
	public void onOpen(@PathParam("memno") String memno, @PathParam("stono") String stono, Session userSession) throws IOException{
		if (allStore.get(stono) == null) {
			Set<Session> allSession = Collections.synchronizedSet(new HashSet<Session>());
			allMember.put(stono, allSession);
			
			Map<String, Integer[][]> storeMap = Collections.synchronizedMap(new HashMap<String, Integer[][]>());
			String stobh = new StoreService().getOneStore(stono).getStobh();
			int minTime = 0;
			int maxTime = 24;
			
			for (int i = 0; i < 7; i++) {
				String subStobh = stobh.substring(i * 24, (i + 1) * 24);
				int firstIndex = subStobh.indexOf("1");
				if (firstIndex > minTime) {
					minTime = firstIndex;
				}
				int lastIndex = subStobh.lastIndexOf("1");
				if (lastIndex + 1 < maxTime) {
					maxTime = lastIndex;
				}
			}
			
			String[] fineStobh = new String[7];
			for (int i = 0; i < 7; i++) {
				fineStobh[i] = stobh.substring(i * 24 + minTime, i * 24 + maxTime + 1);
			}
			
			Integer storesvdays = 30;
			
			List<StotableVO> tableList = new StotableService().getStotableByStono(stono);
			int minSeat = 99;
			int maxSeat = 0;
			ReservationService reservationSvc = new ReservationService();
			for (StotableVO stotableVO : tableList) {
				int min = stotableVO.getTablemin();
				if (min < minSeat) {
					minSeat = min;
				}
				int max = stotableVO.getTablemax();
				if (max > maxSeat) {
					maxSeat = max;
				}
			}
			
			
			Format dateFormat = new SimpleDateFormat("yyyyMMdd");
			
			Calendar calendar = new GregorianCalendar();
			List<Integer[][]> tempSeatList = new ArrayList<Integer[][]>();
			for (int i = 0; i < storesvdays; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				String dateString = dateFormat.format(calendar.getTime());
				if (i < 7) {
					String dayStobh = fineStobh[calendar.get(Calendar.DAY_OF_WEEK) - 1];
					int dayStobhLength = dayStobh.length();
					Integer[][] seat = new Integer[dayStobhLength][maxSeat - minSeat + 1];
					for (int j = 0; j < dayStobhLength; j++) {
						for (StotableVO stotableVO : tableList) {
							int count = stotableVO.getTablecount() - stotableVO.getTablestate();
							int minIndex = stotableVO.getTablemin() - minSeat;
							int maxIndex = stotableVO.getTablemax() - maxSeat;
							for (int k = minIndex; k <= maxIndex; k++) {
								seat[j][k] += count;
							}
						}
					}
					tempSeatList.add(seat);
					storeMap.put(dateString, seat);
				} else {
					storeMap.put(dateString, deepCopy(tempSeatList.get(i%7)));
				}
				
			}
			
			for (StotableVO stotableVO : tableList) {
				List<ReservationVO> listResv = reservationSvc.getReservationByTableno(stotableVO.getTableno());
				int min = stotableVO.getTablemin() - minSeat;
				int max = stotableVO.getTablemax() - minSeat;
				for (ReservationVO reservationVO : listResv) {
					String resvDate = dateFormat.format(reservationVO.getResvdate());
					String resvPeriod = reservationVO.getResvperiod();
					int resvStart = resvPeriod.indexOf("1") - minTime;
					int resvEnd = resvPeriod.lastIndexOf("1") - minTime;
					Integer[][] temp = storeMap.get(resvDate);
					for (int i = resvStart; i <= resvEnd; i++) {
						for (int j = min; j <= max; j++) {
							temp[i][j] -= 1;
						}
					}
				}
			}
			
			Integer[][] timeSeatRange = {{minTime, maxTime}, {minSeat, maxSeat}};
			storeMap.put("timeSeatRange", timeSeatRange);
			allStore.put(stono, storeMap);
		}
		allMember.get(stono).add(userSession);
		userSession.getBasicRemote().sendText(mapToJson((Map<String, Integer[][]>)allStore.get(stono)));
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		
	}
	
	
	String mapToJson(Map<String, Integer[][]> map) {
		JSONObject json = new JSONObject();
		Integer[][] timeSeatRange = map.get("timeSeatRange");
		json.put("minTime", timeSeatRange[0][0]);
		json.put("maxTime", timeSeatRange[0][1]);
		json.put("minSeat", timeSeatRange[1][0]);
		json.put("maxSeat", timeSeatRange[1][1]);
		JSONObject seat = new JSONObject();
		Set<String> keySet = map.keySet();
		for (String date : keySet) {
			if (!date.equals("timeSeatRange")) {
				Integer[][] seatIntegerArray = map.get(date);
				String[] seatStringArray = Arrays.stream(seatIntegerArray).map(arr -> {
					StringBuffer sb = new StringBuffer();
					int len = arr.length;
					for (int i = 0; i < len; i++) {
						if (arr[i] > 0) {
							sb.append("1");
						} else {
							sb.append("0");
						}
					}
					return sb.toString();
				}).toArray($ -> new String[$]);
				seat.put(date, seatStringArray);
			}
		}
		json.put("seat", seat);
		return json.toString();
	}
	
	Integer[][] deepCopy(Integer[][] matrix) {
		return Arrays.stream(matrix).map(row -> row.clone()).toArray($ -> matrix.clone());
	}
}
