package spelling;

/** A class for timing the Dictionary Implementations
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DictionaryBenchmarking {	
	public static void main(String [] args) {	    
	    int trials = 100;	  
	    
	    String dictFile = "data/dict.txt";	    
		int increment = 2000;		
		int numSteps = 20;		
		int start = 50000;
		
		String notInDictionary = "notaword";
		System.out.println("num" + "\t" + "LL" + "\t" + "BST");
		
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment){
			// Time the creation of finding a word that is not in the dictionary.
			DictionaryLL llDict = new DictionaryLL();
			DictionaryBST bstDict = new DictionaryBST();
			
			DictionaryLoader.loadDictionary(llDict, dictFile, numToCheck);
			DictionaryLoader.loadDictionary(bstDict, dictFile, numToCheck);
			
			long startTime = System.nanoTime();
			for (int i = 0; i < trials; i++) {
				llDict.isWord(notInDictionary);
			}
			long endTime = System.nanoTime();
			double timeLL = (endTime - startTime)/10000;  
			
			startTime = System.nanoTime();
			for (int i = 0; i < trials; i++) {
				bstDict.isWord(notInDictionary);
			}
			endTime = System.nanoTime();
			double timeBST = (endTime - startTime)/10000;
			
			
			System.out.println(numToCheck + "\t" + timeLL + "\t" + timeBST);			
		}	
	}	
}