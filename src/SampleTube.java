import java.util.ArrayList;


public class SampleTube extends Tube{

	public Sample sample;		//can have 0 or 1 samples
	
	public SampleTube(Sample sample){
		//System.out.println("SampleTube constructor");
		this.sample = sample;
		this.samples = new ArrayList<Sample>();
		if(sample != null) this.samples.add(sample);
	}
	
	public Response addSample(Sample sample){
		if(samples.size() == 0) {
			samples.add(sample);
			return new Response(true, "Successfully added sample with unique id " + sample.getUniqueId() + "to tube with barcode " + barcode + ".");
		}
		else {
			return new Response(false, "Couldn't add a sample to the tube because it already contains a sample.");
		}
	}
	
	public Response removeSample(Sample sample){
		this.sample = null;
		
		samples.remove(sample);
		
		return new Response(true, "");
	}
	
}
