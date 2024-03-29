import java.util.ArrayList;


public class DataSet{
	
	/******* mock of a database *********/
	
	private static ArrayList<Sample> samples;
	public static ArrayList<Sample> getSamples(){
		return samples;
	}
	
	private static ArrayList<SampleTube> sampleTubes;
	public static ArrayList<SampleTube> getSampleTubes(){
		return sampleTubes;
	}
	
	private static ArrayList<LibraryTube> libraryTubes;
	public static ArrayList<LibraryTube> getLibraryTubes(){
		return libraryTubes;
	}
		
	static{
		initializeData();
	}
	
	/****** methods to create test data *******/
	
	static void initializeData(){
		Tube.resetLatestTubeNumber();
		
		samples = createSamples(50);
		
		sampleTubes = new ArrayList<SampleTube>();
		for(Integer i = 0; i < 10; i++){
			Sample sample = samples.get(i);
			SampleTube st = new SampleTube(sample);
			sampleTubes.add(st);
		}
		
		libraryTubes = new ArrayList<LibraryTube>();
		for(Integer i = 10; i < 20; i++){
			LibraryTube lt = new LibraryTube();
			libraryTubes.add(lt);
		}
	}
	
	static ArrayList<Sample> createSamples(Integer number){
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
			if(st.getBarcode().equals(barcode)){
				result = st;
				break;
			}
		}
		for(LibraryTube lt : libraryTubes){
			if(lt.getBarcode().equals(barcode)){
				result = lt;
				break;
			}
		}
			
		return result;
	}
	
	public static Sample findSampleByUniqueId(String uniqueId){
		Sample result = null;
		
		for(Sample s : samples){
			if(s.getUniqueId().equals(uniqueId)){
				result = s;
				break;
			}
		}
		
		return result;
	}
	
	public static Tube findTubeBySampleUniqueId(String sampleUniqueId){
		Tube result = null;
		
		for(SampleTube st : sampleTubes){
			if(st.containsSample(sampleUniqueId)) {
				result = st;
				break;
			}
		}
		if(result == null) {
			for(LibraryTube lt : libraryTubes){
				if(lt.containsSample(sampleUniqueId)) {
					result = lt;
					break;
				}
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
	
	public static Response addToTube(String sampleUniqueId, String destinationTubeBarcode){
		Sample sample = findSampleByUniqueId(sampleUniqueId);
		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
		Tube tubeSampleIsAlreadyIn = findTubeBySampleUniqueId(sampleUniqueId);
		
		if(sample == null) {
			return new Response(false, "Couldn't find the sample.");
		}
		if(destinationTube == null) {
			return new Response(false, "Couldn't find the destination tube.");
		}
		if(tubeSampleIsAlreadyIn != null) {
			return new Response(false, "Sample is already in tube " + tubeSampleIsAlreadyIn.getBarcode() + ". Did you mean to use move_sample?");
		}
		
		Response r = destinationTube.addSample(sample);
		return r;
	}
	
	public static Response createSampleTube(){
		SampleTube newSampleTube = new SampleTube(null);
		sampleTubes.add(newSampleTube);
		return new Response(true, "Successfully created sample tube with barcode " + newSampleTube.getBarcode() + ".");
	}
	
	public static Response createLibraryTube(){
		LibraryTube newLibraryTube = new LibraryTube();
		libraryTubes.add(newLibraryTube);
		return new Response(true, "Successfully created library tube with barcode " + newLibraryTube.getBarcode() + ".");
	}
	
	public static Response appendTagToSample(String sampleUniqueId, String tagSequence){
		Tag newTag = new Tag(tagSequence);
		
		Sample s = findSampleByUniqueId(sampleUniqueId);
		
		if(s == null) {
			return new Response(false, "Sample was not found.");
		}
		
		Response r = s.setTag(newTag);
		return r;
		
	}
	
	public static Response moveSample(String sourceTubeBarcode, String destinationTubeBarcode){ 
		Tube sourceTube = findTubeByBarcode(sourceTubeBarcode);
		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
		
		if(sourceTube == null) {
			return new Response(false, "Could not find the source tube.");
		}
		if(destinationTube == null) {
			return new Response(false, "Could not find the destination tube.");
		}
		if (sourceTube.isEmpty() || sourceTube.getSamples().get(0) == null) {
			return new Response(false, "Could not find a sample in the source tube.");
		};
		
		Sample sampleToMove = sourceTube.getSamples().get(0);
		
		Response r = sampleToMove.moveTubes(sourceTube, destinationTube);
		return r;
	}
	
	public static Response moveSamples(String[] sourceTubeBarcodes, String destinationTubeBarcode){
		ArrayList<Tube> tubes = new ArrayList<Tube>();
		for(String s : sourceTubeBarcodes) {
			Tube t = findTubeByBarcode(s);
			if(t != null) {
				tubes.add(t);
			}
		}
		
		if(tubes.size() != sourceTubeBarcodes.length) {
			return new Response(false, "Could not find all the source tubes.");
		}
		
		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
		
		if(destinationTube == null) {
			return new Response(false, "Could not find the destination tube.");
		}
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(tubes, destinationTube);
		
		while(destinationTube.state != "Passed"){
			Response r = protocol.moveNextSample();
			destinationTube = protocol.getDestinationTube();
			if(!r.getSuccess()) {
				return r;
			}
		}
		return new Response(true, "Successfully reached the end of the protocol.");
	}
	
	public static Response moveSamplesStepwise(String[] sourceTubeBarcodes, String destinationTubeBarcode){
		ArrayList<Tube> tubes = new ArrayList<Tube>();
		for(String s : sourceTubeBarcodes) {
			Tube t = findTubeByBarcode(s);
			if(t != null) {
				tubes.add(t);
			}
		}
		
		if(tubes.size() != sourceTubeBarcodes.length) {
			return new Response(false, "Could not find all the source tubes.");
		}
		
		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
		
		if(destinationTube == null) {
			return new Response(false, "Could not find the destination tube.");
		}
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(tubes, destinationTube);
		
		while(destinationTube.state != "Passed"){
			
			System.out.println("Ready to move next sample? Please press any key to continue.");
			main.scanner.nextLine();
			
			Response r = protocol.moveNextSample();
			destinationTube = protocol.getDestinationTube();
			if(!r.getSuccess()) {
				return r;
			}
		}
		return new Response(true, "Successfully reached the end of the protocol.");
	}
	
}
