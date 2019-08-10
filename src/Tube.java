import java.util.ArrayList;


public class Tube {
	
	public static Integer latestTubeNumber = 0;
	
	public ArrayList<Sample> samples; //not sure what access we want
	public String barcode;				//format NT-00001 ...
	public String state;		//for transfer between tubes protocol; pending, started, passed
	
	public Tube(){
		//samples = new ArrayList<Sample>();
		
		setBarcode();	//not sure if this should be at Tube or sub-class level
		setState();
	}
	
	public static void resetLatestTubeNumber() {
		latestTubeNumber = 0;
	}
	
	private void setBarcode(){
		String result = "";
		Tube.latestTubeNumber++;
		
		//would query latest used from the database somehow here
		String prefix = "NT-";
		String number = String.valueOf(latestTubeNumber);
		if(number.length() < 5){
			Integer numZerosPad = 5 - number.length();
			for(Integer i = 1; i <= numZerosPad; i++){
				number = "0" + number;
			}
		}
		
		result = prefix + number;
		//System.out.println(result);
		this.barcode = result;
	}
	
	private void setState(){
		if(samples == null || samples.isEmpty()){
			state = "Pending";
		}
	}
	
	public ArrayList<Sample> getSamples(){
		ArrayList<Sample> output;
		
		output = samples;
		
		return output;
	}
	
	public void printSamples(){
		for(Sample s : samples){
			System.out.println("Name: " + s.getName());
			System.out.println("Unique id: " + s.getUniqueId());
			System.out.println("Tag: " + s.getTag());
			System.out.println("Customer: " + s.getCustomerName());
			System.out.println("-----------------------------");
		}
	}
	
	public Response addSample(Sample sample){
		samples.add(sample);
		return new Response(true, "");
	}
	
	public Response removeSample(Sample sample){
		System.out.println("remove sample in tube");
		System.out.println(samples);
		samples.remove(sample);
		System.out.println(samples);
		return new Response(true, "");
	}
	
	public void printWithSamples(){
		System.out.println("Tube information");
		System.out.println("Barcode: " + barcode);
		for(Sample s : samples){
			s.print();
		}
	}
	
	public void print(){
		System.out.println("Tube information");
		System.out.println("Barcode: " + barcode);
	}
}
