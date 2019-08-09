import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DataSetTest {

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
		ArrayList<Sample> resultingSamples = null;
		
		resultingSamples = DataSet.createSamples(10);
		
		assert(!resultingSamples.isEmpty());
		assertEquals(10, resultingSamples.size());
	}
	
	@Test
	void testFindTubeByBarcode() {
		Tube result = null;
		
		result = DataSet.findTubeByBarcode("NT-00004");
		
		assert(result != null);
		assertEquals("NT-00004", result.barcode);
	}

}
