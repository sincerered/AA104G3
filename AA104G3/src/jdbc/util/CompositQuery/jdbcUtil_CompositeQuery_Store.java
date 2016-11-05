package jdbc.util.CompositQuery;

import java.util.Map;
import java.util.Set;

public class jdbcUtil_CompositeQuery_Store {
	public static String get_aCondition_For_Oracle(String columnName, String value) {
		
		return "";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			count++;
			String value = map.get(key)[0];
			if (value != null) {
				
			}
		}
		
		return "";
	}
}
