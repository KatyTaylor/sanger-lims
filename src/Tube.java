import java.util.ArrayList;


public class Tube {
	
	private static Integer latestTubeNumber = 0;
	public static Integer getLatestTubeNumber() {
		return latestTubeNumber;
	}
	public static void resetLatestTubeNumber() {
		latestTubeNumber = 0;
	}
	
	public String state;
	
	public ArrayList<Sample> samples;
	public ArrayList<Sample> getSamples(){
		return samples;
	}
	
	private String barcode;
	public String getBarcode() {
		return barcode;
	}
	private void setBarcode(){
		if(barcode != null) {
			return;
		}
		
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
		barcode = result;
	}
	
	public String type;
	public String getType(){
		return type;
	}
	
	
	public Tube(){
		setBarcode();
		this.samples = new ArrayList<Sample>();
	}
	
	public Tube(Sample initialSample){
		setBarcode();
		this.samples = new ArrayList<Sample>();
		if(initialSample != null) this.samples.add(initialSample);
	}	
	
	
	public Response addSample(Sample sample){
		samples.add(sample);
		return new Response(true, "Successfully added sample " + sample.getUniqueId() + " to tube " + barcode + ".");
	}
	
	public Response removeSample(Sample sample){
		samples.remove(sample);
		return new Response(true, "Successfully removed sample " + sample.getUniqueId() + " from tube " + barcode + ".");
	}
	
	public Boolean containsSample(String sampleUniqueId) {
		Boolean result = false;
		
		for(Sample s : samples) {
			if(s.getUniqueId().equals(sampleUniqueId)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public Boolean isEmpty() {
		if(samples == null || samples.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**************** functions to print out information ****************/
	
	public void print(){
		System.out.println("Tube information");
		System.out.println("Type: " + type);
		System.out.println("Barcode: " + barcode);
	}
	
	public void printWithSamples(){
		print();
		String samplesString = samples == null || samples.isEmpty() ? "Tube is empty." : String.valueOf(samples.size());
		System.out.println("Samples: " + samplesString);
		for(Sample s : samples){
			main.printLineDivider();
			s.print();
		}
	}
	
}
