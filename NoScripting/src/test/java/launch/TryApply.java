package launch;

import org.testng.SkipException;
import org.testng.annotations.Test;

public class TryApply {

	@Test()
	   public void testcase1(){
	      System.out.println("TestCase 1 - Execution started");
	      assert true;
	   }
	   @Test()
	   public void testcase2(){
	      System.out.println("TestCase 2 - Execution started");
	      assert false;
	   }
	   @Test()
	   public void testcase3(){
	      System.out.println("TestCase 3 - Execution started");
	      throw new SkipException("Skipping the testcase 3");
	   }
	
	

	

}
