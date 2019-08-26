package sanger_application_2019.coding_task;

import java.util.regex.*;


public class Tag {

	private static String sequenceRegex = "([ATGC])+";
	
	private String sequence;
	public String getSequence() {
		return sequence;
	}
	
	
	public Tag(String sequence){
		this.sequence = sequence;
	}
	
	
	public static Boolean isValidSequence(String s){
		Boolean result = false;
		
		Pattern pattern = Pattern.compile(Tag.sequenceRegex); 
		Matcher matcher = pattern.matcher(s); 
        result = matcher.matches();
        
        return result;
	}

}
