package sanger_application_2019.coding_task;

public class SampleTube extends Tube{

	public SampleTube(Sample sample){
		super(sample);
		this.type = "sample tube";
	}
	
	public SampleTube(){
		super();
		this.type = "sample tube";
	}
	
	public Response addSample(Sample sample){
		if(samples.size() > 0) {
			return new Response(false, "Couldn't add a sample to the tube because it already contains a sample.");
		}
		if(sample == null){
			return new Response(false, "Couldn't add a sample to the tube because the sample provided was null.");
		}
		samples.add(sample);
		return new Response(true, "Successfully added sample with unique id " + sample.getUniqueId() + "to tube with barcode " + getBarcode() + ".");
		
	}
	
}
