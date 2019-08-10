import java.util.ArrayList;


public class DataSetInstanceBackup{
	
	public ArrayList<Sample> samples;
	public ArrayList<SampleTube> sampleTubes;
	public ArrayList<LibraryTube> libraryTubes;
	
	public DataSetInstanceBackup (){
		initializeData();
	}
	
	private void initializeData(){
		samples = createSamples(50);
		
		ArrayList<SampleTube> sampleTubes = new ArrayList<SampleTube>();		
		for(Integer i = 0; i < 10; i++){
			Sample sample = samples.get(i);
			SampleTube st = new SampleTube(sample);
			sampleTubes.add(st);
		}

		this.sampleTubes = sampleTubes;
		
		ArrayList<LibraryTube> libraryTubes = new ArrayList<LibraryTube>();
		/*for(Integer i = 10; i < 20; i++){
			Sample sample = samples.get(i);
			LibraryTube lt = new LibraryTube(sample);
			libraryTubes.add(lt);
		}*/
		this.libraryTubes = libraryTubes;
	}
	
	private ArrayList<Sample> createSamples(Integer number){
		ArrayList<Sample> output = new ArrayList<Sample>(); 
		
		for(Integer i = 0; i < number; i++){
			Sample s = new Sample("sampleName-" + i, "customer-" + i);			
			output.add(s);
		}
		
		return output;
	}
	
	public DataSetInstanceBackup createSample(String name, String customerName){
		Sample newSample = new Sample(name, customerName);
		samples.add(newSample);
		return this;
	}
	
	public Tube findTubeByBarcode(String barcode){
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
	
	public DataSetInstanceBackup appendTagToSample(String sampleUniqueId, String tagSequence){
		Tag newTag = new Tag(tagSequence);
		for(Sample s : samples){
			if(s.getUniqueId().equals(sampleUniqueId)){
				s.setTag(newTag);
				break;
			}
		}
		return this;
	}

	public DataSetInstanceBackup moveSample(String sampleUniqueId, String sourceTubeBarcode, String destinationTubeBarcode){
		Sample sampleToMove = null;
		Tube sourceTube = null;
		Tube destinationTube = null;
		
		for(Sample s : samples){
			if(s.getUniqueId().equals(sampleUniqueId)){
				sampleToMove = s;
				break;
			}
		}
		for(SampleTube st : sampleTubes){
			if(sourceTube == null || destinationTube == null){
				if(st.barcode.equals(sourceTubeBarcode)){
					sourceTube = st;
					continue;
				}
				if(st.barcode.equals(destinationTubeBarcode)){
					destinationTube = st;
					continue;
				}
			} else break;
		}
		for(LibraryTube lt : libraryTubes){
			if(sourceTube == null || destinationTube == null){
				if(lt.barcode.equals(sourceTubeBarcode)){
					sourceTube = lt;
					continue;
				}
				if(lt.barcode.equals(destinationTubeBarcode)){
					destinationTube = lt;
					continue;
				}
			} else break;
		}
		if(sampleToMove != null && sourceTube != null && destinationTube != null){
			sampleToMove.moveTubes(sourceTube, destinationTube);
		}
		return this;
	}
	
//	public DataSetInstanceBackup moveAllSamplesBetweenTubes(String sourceTubeBarcode, String destinationTubeBarcode){
//		Tube sourceTube = findTubeByBarcode(sourceTubeBarcode);
//		Tube destinationTube = findTubeByBarcode(destinationTubeBarcode);
//		
//		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTube, destinationTube);
//		
//		while(destinationTube.state != "Passed"){
//			protocol.moveNextSample();
//			destinationTube = protocol.destinationTube;
//		}
//				
//		return this;
//	}
}
