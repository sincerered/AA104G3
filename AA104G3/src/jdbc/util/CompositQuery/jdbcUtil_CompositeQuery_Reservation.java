package jdbc.util.CompositQuery;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class jdbcUtil_CompositeQuery_Reservation {
	public static String get_aCondition_For_Oracle(String columnName, String value) {
		
		String aCondition = null;
		
		
		if ("resvno".equals(columnName) || "memno".equals(columnName) || "tableno".equals(columnName) || "teamno".equals(columnName) || "tablestate".equals(columnName)){
			aCondition = columnName + "='" + value + "'";
		} else if ("resvdateMax".equals(columnName)) {
			aCondition = "to_char(resvdate, 'yyyy-mm-dd')<='" + value + "'"; 
		} else if ("resvdateMin".equals(columnName)) {
			aCondition = "to_char(resvdate, 'yyyy-mm-dd')>='" + value + "'"; 
		} else if ("resvperiodMax".equals(columnName)) {
			aCondition = "instr(resvperiod, '1')<=" + value;
		} else if ("resvperiodMin".equals(columnName)) {
			aCondition = "instr(resvperiod, '1', -1) - 1>=" + value;
		} else if ("stono".equals(columnName)) {
			aCondition = "tableno in (SELECT tableno FROM stotable WHERE stono='" + value + "')";
		} else if ("memid".equals(columnName)) {
			aCondition = "memno in (SELECT memno FROM member WHERE memid='" + value + "')";
		}
		
		return aCondition + " ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> notColumnName = new HashSet<String>();
		notColumnName.add("action");
		notColumnName.add("rowsPerPage");
		notColumnName.add("whichPage");
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		String orderBy = null;
		for (String key : keys) {
			String value = map.get(key)[0];
			if ("orderBy".equals(key)) {
				orderBy = value;
			} else if (value != null && value.trim().length() != 0 && !notColumnName.contains(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());
				
				if (count == 1) {
					whereCondition.append(" WHERE " + aCondition);
				} else {
					whereCondition.append(" AND " + aCondition);
				}
			}
		}
		if (orderBy == null) {
			whereCondition.append("order by resvno DESC");
		} else {
			whereCondition.append("order by " + orderBy);
		}
		
		
		
		return whereCondition.toString();
		
	}
}
