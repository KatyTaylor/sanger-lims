import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DataSetTest {

	private void resetTestContext() {
		DataSet.initializeData();
	}
	
	@Test
	void testInitializeData() {
		DataSet.initializeData();
		
		assert(!DataSet.samples.isEmpty());
		assertEquals(50, DataSet.samples.size());
		
		assert(!DataSet.sampleTubes.isEmpty());
		assertEquals(10, DataSet.sampleTubes.size());
		
		assert(DataSet.libraryTubes.isEmpty());
		//assertEquals(0, DataSet.libraryTubes.size());
	}
	
	@Test
	void testCreateSamples() {
		resetTestContext();
		
		ArrayList<Sample> resultingSamples = null;
		
		resultingSamples = DataSet.createSamples(10);
		
		assert(!resultingSamples.isEmpty());
		assertEquals(10, resultingSamples.size());
	}
	
	@Test
	void testFindTubeByBarcode() {
		resetTestContext();
		
		for(Tube t : DataSet.sampleTubes) t.print();
		main.printLineDivider();
		for(Tube t : DataSet.libraryTubes) t.print();
		Tube result = DataSet.findTubeByBarcode("NT-00004");
		System.out.println(result);
		assert(result != null);
		assertEquals("NT-00004", result.barcode);
	}
	
	@Test
	void testFindTubeByBarcode_missing() {
		resetTestContext();
		
		Tube result = null;
		
		result = DataSet.findTubeByBarcode("Not a real barcode!");
		
		assertEquals(null, result);
	}
	
	@Test
	void testCreateSample() {
		resetTestContext();

		Response r = DataSet.createSample("Secret celebrity DNA", "Dawn French");
		
		assert(r.getSuccess());
		assertEquals(51, DataSet.samples.size(), "Size of sample list should have increased by one.");
		assertEquals("Secret celebrity DNA", DataSet.samples.get(50).getName(), "Last element in the list is the one we created; it should have the correct name.");
		assertEquals("Dawn French", DataSet.samples.get(50).getCustomerName(), "Last element in the list is the one we created; it should have the correct customer name.");
	}
	
	@Test
	void testCreateSample_duplicate() {
		resetTestContext();

		DataSet.createSample("Secret celebrity DNA", "Dawn French");
		Response r = DataSet.createSample("Secret celebrity DNA", "Dawn French");
		
		assert(!r.getSuccess());
		assertEquals(51, DataSet.samples.size(), "Size of sample list should have only increased by one because the second attempt should have failed.");
	}
	
	@Test
	void testCreateSampleTube() {
		resetTestContext();

		Response r = DataSet.createSampleTube();
		
		assert(r.getSuccess());
		assertEquals(11, DataSet.sampleTubes.size(), "Size of sample tube list should have increased by one.");
	}
	
	@Test
	void testCreateLibraryTube() {
		resetTestContext();

		Response r = DataSet.createLibraryTube();
		
		assert(r.getSuccess());
		assertEquals(1, DataSet.libraryTubes.size(), "Size of library tube list should have increased by one.");
	}
	
	@Test
	void testAppendTagToSample() {
		resetTestContext();

		Response r = DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		
		assert(r.getSuccess());
		
		Sample s = DataSet.findSampleByUniqueId("customer-0-sampleName-0");
		
		assert(s != null);
		assertEquals("CAT", s.getTag().sequence);
	}
	
	@Test
	void testAppendTagToSample_invalidSequence() {
		resetTestContext();
		
		Response r = DataSet.appendTagToSample("customer-0-sampleName-0", "What does a DNA sequence look like?");
		
		assert(!r.getSuccess());
		
		Sample s = DataSet.findSampleByUniqueId("customer-0-sampleName-0");
		
		assertEquals(null, s.getTag());
	}
	
	@Test
	void testAppendTagToSample_nonexistantSample() {
		resetTestContext();
		
		Response r = DataSet.appendTagToSample("Random sample name that isn't there", "CAT");
		
		assert(!r.getSuccess());
		
		Sample s = DataSet.findSampleByUniqueId("customer-0-sampleName-0");
		
		assertEquals(null, s.getTag());
	}
	
	@Test
	void testMoveSample() {
		resetTestContext();
		
		DataSet.createLibraryTube();
		
		Response r = DataSet.moveSample("customer-0-sampleName-0", "NT-00001", "NT-00011");
		
		assert(r.getSuccess());
		Tube t = DataSet.findTubeByBarcode("NT-00001");
		assertEquals(0, t.samples.size());
		
		Tube t2 = DataSet.findTubeByBarcode("NT-00011");
		assertEquals("customer-0-sampleName-0", t2.samples.get(0).getUniqueId());
	}
	
	@Test
	void testMoveAllSamplesBetweenTubes() {
		resetTestContext();
		
	}
	
}