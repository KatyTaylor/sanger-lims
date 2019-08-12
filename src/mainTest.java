import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class mainTest {

	@Test
	void testCheckParams_negative_nullParams() {
		Integer expectedNumberParams = 1;
		String[] params = null;
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(!correctParams);		
	}
	
	@Test
	void testCheckParams_negative_nullFirstParam() {
		Integer expectedNumberParams = 1;
		String[] params = {null};
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(!correctParams);		
	}
	
	@Test
	void testCheckParams_negative_notEnoughParams() {
		Integer expectedNumberParams = 3;
		String[] params = {"param1", "param2"};
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(!correctParams);		
	}
	
	@Test
	void testCheckParams_positive_1params() {
		Integer expectedNumberParams = 1;
		String[] params = {"param1"};
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(correctParams);		
	}
	
	@Test
	void testCheckParams_positive_manyParams() {
		Integer expectedNumberParams = 3;
		String[] params = {"param1", "param2", "param3"};
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(correctParams);		
	}
	
	@Test
	void testCheckParams_positive_excessParams() {
		Integer expectedNumberParams = 3;
		String[] params = {"param1", "param2", "param3", "param4"};
		
		Boolean correctParams = main.checkParams(expectedNumberParams, params);		
		assert(correctParams);		
	}
	
	@Test
	void testValidateuserInput() {
		String userInput = "command";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_2() {
		String userInput = "command()";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_3() {
		String userInput = "command (  ) ";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_4() {
		String userInput = "command(a)";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_5() {
		String userInput = "command(  a  )";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_6() {
		String userInput = "command(a,b,c)";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_7() {
		String userInput = "  command  (  a  ,  b  ,  c  )  ";
		Boolean success = main.validateUserInput(userInput);		
		assert(success);		
	}
	
	@Test
	void testValidateuserInput_negative_1() {
		String userInput = "command(a,)";
		Boolean success = main.validateUserInput(userInput);		
		assert(!success);		
	}
	
	@Test
	void testValidateuserInput_negative_2() {
		String userInput = "command)(";
		Boolean success = main.validateUserInput(userInput);		
		assert(!success);		
	}
	
	@Test
	void testValidateuserInput_negative_3() {
		String userInput = "command()command";
		Boolean success = main.validateUserInput(userInput);		
		assert(!success);		
	}

}
