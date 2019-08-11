import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class mainTest {

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
