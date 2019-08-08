import java.util.ArrayList;


public class DataSet{
	
	/******* mock of a database *********/
	
	public static ArrayList<Sample> samples;
	public static ArrayList<SampleTube> sampleTubes;
	public static ArrayList<LibraryTube> libraryTubes;
		
	static{
		initializeData();
	}
	
	/****** methods to create test data *******/
	
	private static void initializeData(){
		samples = createSamples(50);
		
		sampleTubes = new ArrayList<SampleTube>();
		for(Integer i = 0; i < 10; i++){
			Sample sample = samples.get(i);
			SampleTube st = new SampleTube(sample);
			sampleTubes.add(st);
		}
		
		libraryTubes = new ArrayList<LibraryTube>();
		/*for(Integer i = 10; i < 20; i++){
			Sample sample = samples.get(i);
			LibraryTube lt = new LibraryTube(sample);
			libraryTubes.add(lt);
		}*/
	}
	
	private static ArrayList<Sample> createSamples(Integer number){
		ArrayList<Sample> output = new ArrayList<Sample>(); 
		
		for(Integer i = 0; i < number; i++){
			Sample s = new Sample("sampleName-" + i, "customer-" + i);			
			output.add(s);
		}
		
		return output;
	}
	
	/********* methods to query the data **************/
	
	public static Tube findTubeByBarcode(String barcode){
		Tube result = null;
		
		for(SampleTube st : sampleTubes){
			if(st.barcode.equals(barcode)){
				result = st;
				break;
			}
		}
		for(LibraryTube lt : libraryTubes){
			if(lt.barcode.equals(barcode)){
				result = lt;
				break;
			}
		}
			
		return result;
	}
	
	/********* methods to manipulate the data **************/
	
	public static Response createSample(String name, String customerName){
		for(Sample s : samples){
			if(s.getName().equals(name) && s.getCustomerName().equals(customerName)){
				return new Response(false, "Name and customer combination already exists. Please choose a new name for the sample.");
			}
		}
		Sample newSample = new Sample(name, customerName);
		samples.add(newSample);
		return new Response(true, "Successfully created sample with name " + name + " and customer name " + customerName + ".");
	}
	
	public static Response createSampleTube(){
		SampleTube newSampleTube = new SampleTube(null);
		sampleTubes.add(newSampleTube);
		return new Response(true, "Successfully created sample tube with barcode " + newSampleTube.barcode + ".");
	}
	
	public static Response createLibraryTube(){
		LibraryTube newLibraryTube = new LibraryTube();
		libraryTubes.add(newLibraryTube);
		return new Response(true, "Successfully created library tube with barcode " + newLibraryTube.barcode + ".");
	}
	
	public static void appendTagToSample(String sampleUniqueId, String tagSequence){
		Tag newTag = new Tag(tagSequence);
		for(Sample s : samples){
			if(s.getUniqueId().equals(sampleUniqueId)){
				Response r = s.setTag(newTag);
				System.out.println(r.getMessage());
				break;
			}
		}
	}

	public static Response moveSample(String sampleUniqueId, String sourceTubeBarcode, String destinationTubeBarcode){
		Sample sampleToMove = null;
		Tube sourceTube = null;
		Tube destinationTube = null;
		
		for(Sample s : samples){
			if(s.getUniqueId().equals(sampleUniqueId)){
				sampleToMove = s;
				break;
			}
		}
		sourceTube = findTubeByBarcode(sourceTubeBarcode);
		destinationTube = findTubeByBarcode(destinationTubeBarcode);
		
		sourceTube.printWithSamples();
		main.printLineDivider();
		destinationTube.printWithSamples();
		main.printLineDivider();
		
		if(sampleToMove != null && sourceTube != null && destinationTube != null){
			Response r = sampleToMove.moveTubes(sourceTube, destinationTube);
			
			sourceTube.printWithSamples();
			main.printLineDivider();
			destinationTube.printWithSamples();
			main.printLineDivider();
			
			return r;
		}
		else{
			return new Response(false, "Could not find the sample or one of the tubes");
		}
	}
	
	public static void moveAllSamplesBetweenTubes(String sourceTubeBarcode, String destinationTubeBarcode){
		Tube sourceTube = findTubeByBarcode(sourceTubeBarcode);
		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTube, destinationTube);
		
		while(destinationTube.state != "Passed"){
			protocol.moveNextSample();
			destinationTube = protocol.destinationTube;
		}
	}
	
}
