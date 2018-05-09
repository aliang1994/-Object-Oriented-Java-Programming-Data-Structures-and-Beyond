package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** 
 * A class for timing the EfficientDocument and BasicDocument classes * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking {	
	public static void main(String [] args) {	 
	    int trials = 100; 			 // Run each test more than once to get bigger numbers and less noise. 
	    String textfile = "data/warAndPeace.txt";			// The text to test on
		int increment = 20000;		// The amount of characters to increment each step. 
		int numSteps = 20;			// The number of steps to run.  
		int start = 50000;			// THe number of characters to start with. 
		
		// TODO: Fill in the rest of this method so that it runs two loops
		// and prints out timing results as described in the assignment 
		// instructions and following the pseudocode below.
		
		System.out.println("NumChars " + " BasicTime " + " EffTime" );
		
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment){  // numToCheck holds the number of characters that you should read from the file to create both a BasicDocument and an EfficientDocument.  
			
			
			String s = getStringFromFile(textfile, numToCheck); 
			
			long bdStart = System.nanoTime();
			for (int k=0; k<trials; k++){
				BasicDocument bd = new BasicDocument(s);				
				bd.getFleschScore();				
			}
			long bdEnd = System.nanoTime();
			double bdTime = (bdEnd-bdStart)/100000000;
			
			long edStart = System.nanoTime();
			for (int k=0; k<trials; k++){				
				EfficientDocument ed = new EfficientDocument(s);				
				ed.getFleschScore();				
			}			
			long edEnd = System.nanoTime();
			double edTime = (edEnd - edStart)/100000000;
			
			System.out.println(numToCheck + "\t" + bdTime + "\t" + edTime);
			
			
			
			/* Each time through this loop you should:
			 * 1. Print out numToCheck followed by a tab (\t) (NOT a newline)
			 * 2. Read numToCheck characters from the file into a String
			 *     Hint: use the helper method below.
			 * 3. Time a loop that runs trials times (trials is the variable above) that:
			 *     a. Creates a BasicDocument 
			 *     b. Calls fleshScore on this document
			 * 4. Print out the time it took to complete the loop in step 3 
			 *      (on the same line as the first print statement) followed by a tab (\t)
			 * 5. Time a loop that runs trials times (trials is the variable above) that:
			 *     a. Creates an EfficientDocument 
			 *     b. Calls fleshScore on this document
			 * 6. Print out the time it took to complete the loop in step 5 
			 *      (on the same line as the first print statement) followed by a newline (\n) 
			 */  			 
		}	
	}
	
	/** 
	 * Get a specified number of characters from a text file
	 * 
	 * @param filename The file to read from
	 * @param numChars The number of characters to read
	 * @return The text string from the file with the appropriate number of characters
	 */
	public static String getStringFromFile(String filename, int numChars) {		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e){
		  System.out.println(e);
		  System.exit(0);
		}		
		return s.toString();
	}	
}