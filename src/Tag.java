import java.util.regex.*;


public class Tag {

	public String sequence; 	
	
	private static String sequenceRegex = "([ATGC])+";
	
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
