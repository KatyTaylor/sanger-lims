import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class main {

	public static Scanner scanner = new Scanner(System.in);
	private static Boolean exit = false;
	
	public static void main(String args[]) {
		
		String userInput = askForUserInput();

		while(exit == false){
			respondToUserInput(userInput);
			userInput = askForUserInput();
		}
		
		return;		
	}
	
	private static String askForUserInput(){
		printLineDivider();
		System.out.println("What do you want to do?");
		System.out.println("The possible commands are shown below.");
		System.out.println("To perform a command, type the command name and provide all its parameters, as shown."); 
		System.out.println("Example: tag_sample (customer-49-sampleName-49, AAAGGTC)");
		System.out.println("- show_samples");
		System.out.println("- create_sample (sample name, customer name)");
		System.out.println("- add_to_tube (sample name, destination tube barcode)");
		System.out.println("- show_tubes");
		System.out.println("- create_sample_tube");
		System.out.println("- create_library_tube");
		System.out.println("- search_by_barcode (barcode)");		
		System.out.println("- move_sample (source tube barcode, destination tube barcode)");
		System.out.println("- move_samples (source tube barcode 1, source tube barcode 2, ..., destination tube barcode)");
		System.out.println("- tag_sample (sample unique id, tag sequence)");
		System.out.println("- exit");
		printLineDivider();
		
		String userInput = scanner.nextLine();
		if(userInput.equals("exit")) exit = true;
		
		return userInput;
	}
	
	private static void respondToUserInput(String userInput){
			
		if(!validateUserInput(userInput)) {
			System.out.println("Couldn't understand the format of the user input.");
			System.out.println("To perform a command, type the command name and provide all its parameters, as shown."); 
			System.out.println("Example: tag_sample (customer-49-sampleName-49, AAAGGTC)");
			return;
		};
		
		String command = userInput.trim();
		String[] parameters = null;
		
		Boolean hasParameters = userInput.contains("(");
		
		if(hasParameters){
			Integer openBracketIndex = userInput.indexOf("(");
			Integer closeBracketIndex = userInput.indexOf(")");
			
			command = userInput.substring(0, openBracketIndex).trim();
			
			String parameterString = userInput.substring(openBracketIndex+1, closeBracketIndex).trim();
			parameters = parameterString.split(",");
			
			//parameters = parameters.trim(); ???
			
			for(Integer i=0; i < parameters.length; i++) parameters[i] = parameters[i].trim();
		}
		
		printLineDivider();
		switch(command){
			case "show_samples":
				showAllSamples(parameters);
				break;
			case "create_sample":
				if(!checkParams(2, parameters)) break;
				createNewSample(parameters);
				break;
			case "add_to_tube":
				if(!checkParams(2, parameters)) break;
				addToTube(parameters);
				break;
			case "show_tubes":
				showAllTubes(parameters);
				break;
			case "create_sample_tube":
				if(!checkParams(2, parameters)) break;
				createNewSampleTube(parameters);
				break;
			case "create_library_tube":
				createNewLibraryTube(parameters);
				break;
			case "search_by_barcode":
				if(!checkParams(1, parameters)) break;
				searchByBarcode(parameters);
				break;
			case "move_sample":
				if(!checkParams(2, parameters)) break;
				moveSample(parameters);
				break;
			case "move_samples":
				if(!checkParams(2, parameters)) break;
				moveSamples(parameters);
				break;
			case "tag_sample":
				if(!checkParams(2, parameters)) break;
				tagSample(parameters);
				break;
			case "exit":
				exit = true;
				return;
			default:
				System.out.println("This command doesn't exist.");
				break;
		}
		printLineDivider();
	}
	
	/********* helper methods **************/
	
	public static Boolean checkParams(Integer expectedNumberParams, String[] params){
		Boolean result = true;
		if(params == null || params.length < expectedNumberParams) {
			result = false;
			System.out.println("Not enough parameters were provided. Expected number: " + expectedNumberParams + ".");
		}
		else {
			for(String param : params) {
				if(param == null || param == "") {
					result = false;
					System.out.println("One of the parameters provided was blank.");
					break;
				}
			}
		}
		return result;
	}
	
	public static void printLineDivider(){
		System.out.println("--------------------------");
	}	
	
	public static Boolean validateUserInput(String userInput) {
		Boolean result = false;
		
		String expectedUserInputPattern = "\\s*\\w+\\s*(\\(\\s*(\\s*(\\s*[^\\s,()\"']+\\s*,)*\\s*[^\\s,()\"']+\\s*)?\\s*\\))?\\s*";
		
		Pattern pattern = Pattern.compile(expectedUserInputPattern); 
		
		Matcher matcher = pattern.matcher(userInput); 
		
        result = matcher.matches();
		
		return result;
	}
	
	/********* methods to respond to user input **************/
	
	private static void showAllSamples(String[] parameters){
		for(Sample s : DataSet.getSamples()){
			s.print();
			printLineDivider();
		}
		System.out.println("Total number of samples: " + DataSet.getSamples().size());
	}
	
	private static void createNewSample(String[] parameters){
		String name = parameters[0];
		String customerName = parameters[1];
		Response r = DataSet.createSample(name, customerName);
		System.out.println(r.getMessage());
	}
	
	private static void addToTube(String[] parameters){
		String name = parameters[0];
		String destinationTubeBarcode = parameters[1];
		Response r = DataSet.addToTube(name, destinationTubeBarcode);
		System.out.println(r.getMessage());
	}
	
	private static void showAllTubes(String[] parameters){
		System.out.println("Sample tubes:");
		printLineDivider();
		for(SampleTube st : DataSet.getSampleTubes()){
			st.print();
			printLineDivider();
		}
		System.out.println("Total number of sample tubes: " + DataSet.getSampleTubes().size());
		printLineDivider();

		System.out.println("Library tubes:");
		printLineDivider();
		for(LibraryTube lt : DataSet.getLibraryTubes()){
			lt.print();
			printLineDivider();
		}
		System.out.println("Total number of library tubes: " + DataSet.getLibraryTubes().size());
	}
	
	private static void createNewSampleTube(String[] parameters){
		Response r = DataSet.createSampleTube();
		System.out.println(r.getMessage());
	}
	
	private static void createNewLibraryTube(String[] parameters){
		Response r = DataSet.createLibraryTube();
		System.out.println(r.getMessage());
	}

	private static void searchByBarcode(String[] parameters){
		Tube result = DataSet.findTubeByBarcode(parameters[0]);	
		if(result == null) System.out.println("Couldn't find it");
		else result.printWithSamples();
	}
	
	private static void moveSample(String[] parameters){
		String sourceTubeBarcode = parameters[0];
		String destinationTubeBarcode = parameters[1];
		Response r = DataSet.moveSample(sourceTubeBarcode, destinationTubeBarcode);
		System.out.println(r.getMessage());
	}
	
	private static void moveSamples(String[] parameters){
		
		String[] sourceTubeBarcodes = new String[parameters.length-1];
		String destinationTubeBarcode = parameters[parameters.length-1];
		
		for(Integer i = 0; i < parameters.length; i++) {
			if(i == parameters.length - 1) {
				destinationTubeBarcode = parameters[i];
			}
			else {
				sourceTubeBarcodes[i] = parameters[i];
			}
		}		

		Response r = DataSet.moveSamplesStepwise(sourceTubeBarcodes, destinationTubeBarcode);
		System.out.println(r.getMessage());
	}
	
	private static void tagSample(String[] parameters){
		String sampleUniqueId = parameters[0];
		String tagSequence = parameters[1];
		
		printLineDivider();		
		Response r = DataSet.appendTagToSample(sampleUniqueId, tagSequence);
		System.out.println(r.getMessage());
	}	
	
}
