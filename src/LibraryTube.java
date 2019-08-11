import java.util.ArrayList;


public class LibraryTube extends Tube{

	public LibraryTube(){
		this.type = "library tube";
		this.samples = new ArrayList<Sample>();
	}
	
	public Response addSample(Sample sample){
		if(sample.getTag() == null) {
			return new Response(false, "Couldn't add sample " + sample.getUniqueId() + " to library tube " + barcode + " because it has no tag.");
		}
		samples.add(sample);
		return new Response(true, "Successfully added sample " + sample.getUniqueId() + " to tube " + barcode + ".");
	}
	
}
