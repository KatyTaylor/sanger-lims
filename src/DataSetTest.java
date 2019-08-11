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

		Tube result = DataSet.findTubeByBarcode("NT-00004");
		
		assert(result != null);
		assertEquals("NT-00004", result.getBarcode());
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
		assertEquals("CAT", s.getTag().getSequence());
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
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		
		Response r = DataSet.moveSample("NT-00001", "NT-00011");
		
		assert(r.getSuccess());
		
		Tube t = DataSet.findTubeByBarcode("NT-00001");
		assertEquals(0, t.getSamples().size());
		
		Tube t2 = DataSet.findTubeByBarcode("NT-00011");
		assertEquals("customer-0-sampleName-0", t2.getSamples().get(0).getUniqueId());
	}
	
	@Test
	void testMoveSample_nonExistentSample() {
		resetTestContext();
		
		DataSet.createLibraryTube();
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		DataSet.moveSample("NT-00001", "NT-00011");
		
		Response r = DataSet.moveSample("NT-00001", "NT-00011");
		System.out.println(r.getMessage());
		
		//NT-00001 should have no sample left so the second move should fail.
		assert(!r.getSuccess());
	}
	
	@Test
	void testMoveSample_nonExistentSourceTube() {
		resetTestContext();

		DataSet.createLibraryTube();
		DataSet.appendTagToSample("customer-0-sampleName-0", "CAT");
		
		Response r = DataSet.moveSample("No source tube has this barcode", "NT-00011");
		System.out.println(r.getMessage());
		
		assert(!r.getSuccess());
		
		Tube t2 = DataSet.findTubeByBarcode("NT-00011");
		assertEquals(0, t2.getSamples().size());
	}
	
	@Test
	void testMoveSamples() {
		resetTestContext();
		
		DataSet.createLibraryTube();
		String[] barcodesOfSourceTubes = {"NT-00001", "NT-00002", "NT-00003", "NT-00004"};
		DataSet.appendTagToSample("customer-0-sampleName-0", "AAA");
		DataSet.appendTagToSample("customer-1-sampleName-1", "TTT");
		DataSet.appendTagToSample("customer-2-sampleName-2", "GGG");
		DataSet.appendTagToSample("customer-3-sampleName-3", "CCC");
		
		Response r = DataSet.moveSamples(barcodesOfSourceTubes, "NT-00011");
		System.out.println(r.getMessage());
		
		assert(r.getSuccess());
		
		Tube t = DataSet.findTubeByBarcode("NT-00001");
		assertEquals(0, t.getSamples().size());
		
		Tube t2 = DataSet.findTubeByBarcode("NT-00011");
		assertEquals(4, t2.getSamples().size());
	}
	
}
