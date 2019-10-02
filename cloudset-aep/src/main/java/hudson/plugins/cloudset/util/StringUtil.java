package hudson.plugins.cloudset.util;

public class StringUtil {

	public static String getOnlyIDFromString(String completeString){
		String onlyId = completeString;
		if(completeString.contains("(") && completeString.contains(")")){
			onlyId = completeString.substring(completeString.indexOf("(")+1,
					completeString.length()-1);
		}
		return onlyId;
	}
	
	public static String getDetailFromString(String completeString){
		String detail = completeString;
		if(completeString.contains("(") && completeString.contains(")")){
			detail = completeString.substring(0,
					completeString.indexOf("("));
		}
		return detail;
	}
	
}
