//import java.util.ArrayList;


public class SamplesProgressionProtocol {

	public Tube sourceTube;
	public Tube destinationTube;
	public String state;
	
	public SamplesProgressionProtocol(Tube sourceTube, Tube destinationTube){
		this.sourceTube = sourceTube;
		this.destinationTube = destinationTube;
		this.destinationTube.state = "Pending";
		
		System.out.println(this.destinationTube.state);
	}
	
	public void moveNextSample(){
		
		if(sourceTube.samples.size() > 0){
			Response r = sourceTube.samples.get(0).moveTubes(sourceTube, destinationTube);
		}
		if(sourceTube.samples.size() > 0){
			this.destinationTube.state = "Started";
		}else{
			this.destinationTube.state = "Passed";
		}
		System.out.println(this.destinationTube.state);
	}

}
