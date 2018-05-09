package textgen;

import java.util.AbstractList;
/** 
 * A class that implements a doubly linked list * 
 * @author UC San Diego Intermediate Programming MOOC team
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail; 
	int size;

	/** 
	 * Create a new empty LinkedList 
	 */
	public MyLinkedList() {
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element){		
		if(element == null){
			throw new NullPointerException("Input is null");
		}
		LLNode<E> node = new LLNode<E>(element);
		node.prev = tail.prev;
		tail.prev = node;
		node.next = node.prev.next;
		node.prev.next = node;
		size++;
		
		return true;
	}

	/** 
	 * Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. 
	 */
	public E get(int index){
		if(index<0 || index>=size){ 
			throw new IndexOutOfBoundsException("Index is out of bound");
		}
		LLNode<E> start =  head;
		for(int k=0; k<=index; k++){			
			start = start.next;
		}
		return start.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element){
		if(element == null){
			throw new NullPointerException("input is null");
		}
		if (index<0 || index>= size){
			throw new IndexOutOfBoundsException("index is not reachable");
		}
		LLNode<E> curr = head;
		for (int k=0; k<=index; k++){
			curr = curr.next;			
		}
		LLNode<E> node = new LLNode<E>(element);
		node.prev = curr.prev;
		curr.prev = node;
		node.next = node.prev.next;
		node.prev.next = node;
		
		size++;
	}

	/** 
	 * Return the size of the list 
	 */
	public int size() {		
		return size;
	}

	/** 
	 * Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list 
	 */
	public E remove(int index) {
		if(index<0 || index>size){
			throw new IndexOutOfBoundsException("index is invalid");
		}
		LLNode<E> curr = head;
		for (int k=0; k<=index; k++){
			curr = curr.next;			
		}
		
		curr.prev.next = curr.next;
		curr.next.prev = curr.prev;
		
		size--;
		return curr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {		
		if(element==null){
			throw new NullPointerException("input is invalid");
		}
		if(index<0 || index>= size){
			throw new IndexOutOfBoundsException("index is invalid");
		}
		E currData = get(index);
		LLNode<E> curr = head;
		for(int k=0; k<=index; k++){
			curr = curr.next;
		}
		curr.data = element;
		return currData;
	}   
}

class LLNode<E> {    // recursive data type!!!
	LLNode<E> prev;
	LLNode<E> next;
	E data;
	
	public LLNode(E e) 	{
		this.data = e;	
		this.prev = null;
		this.next = null;
	}	
	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode) {
		this(e);
		this.next = nextNode;
		this.prev = prevNode;
	}		
}