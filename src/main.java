import java.util.Scanner;
import java.util.ArrayList;

public class main {

	private static Scanner scanner = new Scanner(System.in);
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
		System.out.println("- show_tubes");
		System.out.println("- create_sample_tube");
		System.out.println("- create_library_tube");
		System.out.println("- search_by_barcode (barcode)");		
		System.out.println("- move_sample (sample unique id, source tube barcode, destination tube barcode)");
		System.out.println("- move_all_samples (source tube barcode, destination tube barcode)");
		System.out.println("- tag_sample (sample unique id, tag sequence)");
		System.out.println("- exit");
		printLineDivider();
		
		String userInput = scanner.nextLine();
		if(userInput.equals("exit")) exit = true;
		
		return userInput;
	}
	
	private static void respondToUserInput(String userInput){

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
				createNewSample(parameters);
				break;
			case "show_tubes":
				showAllTubes(parameters);
				break;
			case "create_sample_tube":
				createNewSampleTube(parameters);
				break;
			case "create_library_tube":
				createNewLibraryTube(parameters);
				break;
			case "search_by_barcode":
				searchByBarcode(parameters);
				break;
			case "move_sample":
				moveSample(parameters);
				break;
			case "move_all_samples":
				moveAllSamples(parameters);
				break;
			case "tag_sample":
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
	
	public static void printLineDivider(){
		System.out.println("--------------------------");
	}
	
	private static void showAllSamples(String[] parameters){
		for(Sample s : DataSet.samples){
			s.print();
			printLineDivider();
		}
		System.out.println("Total number of samples: " + DataSet.samples.size());
	}
	
	private static void createNewSample(String[] parameters){
		String name = parameters[0];
		String customerName = parameters[1];
		Response r = DataSet.createSample(name, customerName);
		System.out.println(r.getMessage());
	}
	
	private static void showAllTubes(String[] parameters){
		System.out.println("Sample tubes:");
		printLineDivider();
		for(SampleTube st : DataSet.sampleTubes){
			st.print();
			printLineDivider();
		}
		System.out.println("Total number of sample tubes: " + DataSet.sampleTubes.size());
		printLineDivider();

		System.out.println("Library tubes:");
		printLineDivider();
		for(LibraryTube lt : DataSet.libraryTubes){
			lt.print();
			printLineDivider();
		}
		System.out.println("Total number of library tubes: " + DataSet.libraryTubes.size());
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
		String sampleUniqueId = parameters[0];
		String sourceTubeBarcode = parameters[1];
		String destinationTubeBarcode = parameters[2];
		Response r = DataSet.moveSample(sampleUniqueId, sourceTubeBarcode, destinationTubeBarcode);
		System.out.println(r.getMessage());
	}
	
	private static void moveAllSamples(String[] parameters){
		String sourceTubeBarcode = parameters[0];
		String destinationTubeBarcode = parameters[1];
		DataSet.moveAllSamplesBetweenTubes(sourceTubeBarcode, destinationTubeBarcode);
		System.out.println("Moved all samples from " + sourceTubeBarcode + " to " + destinationTubeBarcode + ".");
	}
	
	private static void tagSample(String[] parameters){
		String sampleUniqueId = parameters[0];
		String tagSequence = parameters[1];
		
		printLineDivider();		
		if(Tag.isValidSequence(tagSequence)){
			DataSet.appendTagToSample(sampleUniqueId, tagSequence);
			System.out.println("Tagged sample " + sampleUniqueId + " with tag " + tagSequence);

		}else{
			System.out.println("Tag sequence is invalid");
		}
	}	
	
}
