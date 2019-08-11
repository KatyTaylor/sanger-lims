import java.util.ArrayList;


public class SamplesProgressionProtocol {

	public ArrayList<Tube> sourceTubesToMove;
	public ArrayList<Tube> sourceTubesMoved;
	public Tube destinationTube;
	public String state;
	
	public SamplesProgressionProtocol(ArrayList<Tube> sourceTubes, Tube destinationTube){
		this.sourceTubesToMove = sourceTubes;
		this.sourceTubesMoved = new ArrayList<Tube>();
		this.destinationTube = destinationTube;
		this.destinationTube.state = "Pending";
		
		System.out.println("Protocol started.");
		System.out.println("State: " + this.destinationTube.state);
	}
	
	public Response moveNextSample(){
		
		if(sourceTubesToMove.size() > 0){
			Tube sourceTube = sourceTubesToMove.get(0);
			if(sourceTube.isEmpty() || sourceTube.getSamples().get(0) == null ) {
				return new Response(false, "The source tube does not contain a sample.");
			}
			
			Sample sampleToMove = sourceTube.getSamples().get(0);
			Response r = sampleToMove.moveTubes(sourceTube, destinationTube);
			if(r.getSuccess()) {
				sourceTubesToMove.remove(sourceTube);
				sourceTubesMoved.add(sourceTube);
				
				if(sourceTubesToMove.size() > 0){
					this.destinationTube.state = "Started";
				}else{
					this.destinationTube.state = "Passed";
				}
				System.out.println("State: " + this.destinationTube.state);
			}
			return r;
		}
		else {
			this.destinationTube.state = "Passed";
			return new Response(false, "Could not move next sample as all the samples have already been moved.");
		}
		
	}

}
