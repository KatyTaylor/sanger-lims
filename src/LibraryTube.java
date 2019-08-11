import java.util.ArrayList;


public class LibraryTube extends Tube{

	public LibraryTube(){
		super();
		this.type = "library tube";
	}
	
	public LibraryTube(Sample sample){
		super(sample);
		this.type = "library tube";
	}
	
	public Response addSample(Sample sample){
		if(sample.getTag() == null) {
			return new Response(false, "Couldn't add sample " + sample.getUniqueId() + " to library tube " + getBarcode() + " because it has no tag.");
		}
		if(sample == null){
			return new Response(false, "Couldn't add a sample to the tube because the sample provided was null.");
		}
		samples.add(sample);
		return new Response(true, "Successfully added sample " + sample.getUniqueId() + " to tube " + getBarcode() + ".");	
	}
	
}
