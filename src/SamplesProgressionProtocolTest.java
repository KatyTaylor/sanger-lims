import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SamplesProgressionProtocolTest {

	@Test
	void testPending() {
		DataSet.initializeData();
		
		ArrayList<Tube> sourceTubes = new ArrayList<Tube>();
		sourceTubes.add(DataSet.getSampleTubes().get(0));
		sourceTubes.add(DataSet.getSampleTubes().get(1));
		sourceTubes.add(DataSet.getSampleTubes().get(2));
		sourceTubes.add(DataSet.getSampleTubes().get(3));
		
		Tube destinationTube = DataSet.getLibraryTubes().get(0);
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTubes, destinationTube);
		
		assertEquals("Pending", protocol.getDestinationTube().state);
	}
	
	@Test
	void testStarted() {
		DataSet.initializeData();
		
		ArrayList<Tube> sourceTubes = new ArrayList<Tube>();
		sourceTubes.add(DataSet.getSampleTubes().get(0));
		sourceTubes.add(DataSet.getSampleTubes().get(1));
		sourceTubes.add(DataSet.getSampleTubes().get(2));
		sourceTubes.add(DataSet.getSampleTubes().get(3));
		
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		
		Tube destinationTube = DataSet.getLibraryTubes().get(0);
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTubes, destinationTube);
		Response r = protocol.moveNextSample();
		
		assert(r.getSuccess());
		
		assertEquals("Started", protocol.getDestinationTube().state);		
	}
	
	@Test
	void testPassed() {
		DataSet.initializeData();
		
		ArrayList<Tube> sourceTubes = new ArrayList<Tube>();
		sourceTubes.add(DataSet.getSampleTubes().get(0));
		sourceTubes.add(DataSet.getSampleTubes().get(1));
		sourceTubes.add(DataSet.getSampleTubes().get(2));
		sourceTubes.add(DataSet.getSampleTubes().get(3));
		
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		DataSet.appendTagToSample("customer-1-sampleName-1", "CAT");
		DataSet.appendTagToSample("customer-2-sampleName-2", "CAT");
		DataSet.appendTagToSample("customer-3-sampleName-3", "CAT");
		
		Tube destinationTube = DataSet.getLibraryTubes().get(0);
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTubes, destinationTube);
		Response r = protocol.moveNextSample();
		Response r2 = protocol.moveNextSample();
		Response r3 = protocol.moveNextSample();
		Response r4 = protocol.moveNextSample();
		
		assert(r.getSuccess());
		assert(r2.getSuccess());
		assert(r3.getSuccess());
		assert(r4.getSuccess());
		
		assertEquals("Passed", protocol.getDestinationTube().state);		
	}
	
	@Test
	void testMoveExtraSample() {
		DataSet.initializeData();
		
		ArrayList<Tube> sourceTubes = new ArrayList<Tube>();
		sourceTubes.add(DataSet.getSampleTubes().get(0));
		sourceTubes.add(DataSet.getSampleTubes().get(1));
		sourceTubes.add(DataSet.getSampleTubes().get(2));
		sourceTubes.add(DataSet.getSampleTubes().get(3));
		
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		DataSet.appendTagToSample("customer-1-sampleName-1", "CAT");
		DataSet.appendTagToSample("customer-2-sampleName-2", "CAT");
		DataSet.appendTagToSample("customer-3-sampleName-3", "CAT");
		
		Tube destinationTube = DataSet.getLibraryTubes().get(0);
		
		SamplesProgressionProtocol protocol = new SamplesProgressionProtocol(sourceTubes, destinationTube);
		Response r = protocol.moveNextSample();
		Response r2 = protocol.moveNextSample();
		Response r3 = protocol.moveNextSample();
		Response r4 = protocol.moveNextSample();
		Response r5 = protocol.moveNextSample();
		
		assert(r.getSuccess());
		assert(r2.getSuccess());
		assert(r3.getSuccess());
		assert(r4.getSuccess());
		assert(!r5.getSuccess());
		
		assertEquals("Passed", protocol.getDestinationTube().state);		
	}

}
