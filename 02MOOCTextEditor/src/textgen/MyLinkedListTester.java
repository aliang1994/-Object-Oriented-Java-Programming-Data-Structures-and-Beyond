package textgen;

import static org.junit.Assert.*;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team *
 */
public class MyLinkedListTester {
	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		
		emptyList = new MyLinkedList<Integer>();
		
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++){
			longerList.add(i);
		}
		
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);		
	}
	
	/** 
	 * Test if the get method is working correctly.
	 */
	/* 
	 * You should not need to add much to this method.
	 * We provide it as an example of a thorough test. 
	 */
	@Test
	public void testGet(){
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);			
			fail("should have thrown IndexOutOfBoundsException");
		}
		catch (IndexOutOfBoundsException e) {					
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("should have thrown IndexOutOfBoundsException");
		}
		catch (IndexOutOfBoundsException e) {		
		}
		
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {		
		}
		
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {		
		}
		
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
		
	/** 
	 * Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  
	 */
	@Test
	public void testRemove(){
		//test remove integers
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size", 2, list1.size());
		try{
			list1.remove(4);
			fail("should have thrown IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException e){			
		}
		
		//test remove strings
		assertEquals("Remove: check deleted element", "A", shortList.remove(0));
		assertEquals("Remove: check updated element", "B", shortList.get(0));
		assertEquals("Remove: check size", 1, shortList.size());
		try{
			shortList.remove(-1);
			fail("should have thrown IndexOutOfBoundsException");			
		}
		catch(IndexOutOfBoundsException e){			
		}
	}
	
	/** 
	 * Test adding an element into the end of the list, specifically
	 * public boolean add(E element)
	 */
	@Test
	public void testAddEnd(){
		//test regular cases
		emptyList.add(3);			
		assertEquals("check adding numbers to the end of a list",(Integer)3, emptyList.get(0));
		
		shortList.add("C");
		assertEquals("checking adding strings to the end of a list", "C", shortList.get(2));
		
		longerList.add(11);
		assertEquals("checking adding numbder to longer list", (Integer)11, longerList.get(10));
		
		//test corner cases
		try{
			String test = null;
			shortList.add(test);
			fail ("Input value is null. Check NullPointerException");			
		}
		catch(NullPointerException e){	
		}		
	}
	
	/** 
	 * Test the size of the list
	 */
	@Test
	public void testSize(){
		list1.add(2,25);
		assertEquals("testing size of list1", 4, list1.size());
		
		assertEquals("testing size of emptyList", 0, emptyList.size());
		
		longerList.add(3,5);
		assertEquals("testing size of longerList", 11, longerList.size());
	}
	
	/**
	 * Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 */
	@Test
	public void testAddAtIndex(){
		//test add string
		shortList.add(0,"C");		
		assertEquals("test add strings", "C", shortList.get(0));     
		
		try{
			shortList.add(1,null);
			fail("should have thrown NullPointerException");
		}
		catch(NullPointerException e) {			
		}
		 
		try{
			shortList.add(-1,"C");
			fail("should have thrown IndexOutOfBoundException");
		}
		catch(IndexOutOfBoundsException e){			
		}
		
		//test add integers
		list1.add(1,25);
		assertEquals("test add integers",(Integer)25, list1.get(1));
		
		try {
			list1.add(1,null);
			fail("should have thrown NullPointerException");
		}
		catch (NullPointerException e){			
		}       
		try {
			list1.add(-1,25);
			fail("should have thrown IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException e){			
		}
	}
	
	/** 
	 * Test setting an element in the list 
	 */
	@Test
	public void testSet(){		
		//test list1
	    int deletedVal = list1.set(1, 12);	    
	    assertEquals("check updated integers", (Integer) 12, list1.get(1));
	    assertEquals("check deleted values", (Integer) 21, (Integer)deletedVal);
	    try{
	    	list1.set(8, 25);
	    	fail("should throw IndexOutOfBoundsException");
	    }
	    catch(IndexOutOfBoundsException e){	    	
	    }
	    try{
	    	list1.set(1, null);
	    	fail("should throw NullPointerException");
	    }
	    catch(NullPointerException e){	    	
	    }
	}	
}