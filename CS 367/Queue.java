///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             Queue.java
// Semester:         CS 367 Spring 2016
//
// Author:           Jiatong Li   jli553@wisc.edu
// CS Login:         jiatong
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////

/**
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
{
	// You may use a naive expandable circular array or a chain of listnodes.
	// You may NOT use Java's predefined classes such as ArrayList or
	// LinkedList.
	//Initialize the field we need
	private int numItems; // number of items in the queue
	private E[] things;  // items in the queue
	private static final int INITSIZE = 10; // initial size of the queue
	private int rearIndex;  // index of the first element 
	private int frontIndex;  // index of the last element

	/**
	 * Constructor for a new queue
	 */
	public Queue()
	{
		numItems = 0;
		things = (E[])(new Object[INITSIZE]);
		rearIndex = 0;
		frontIndex = 1;
	}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)
	{
		//check whether the item is null or not
		if(item == null){
			throw new IllegalArgumentException();
		}
		//check whether the queue is full
		if(things.length == numItems){
			expandCapacity();
		}
		//change the rearIndex
		if(rearIndex != things.length-1){
			rearIndex++;
		}
		else{
			rearIndex = 0;
		}
		//add item to the queue and change the numItems
		things[rearIndex] = item;
		numItems++;		
	}


	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue()
	{
		//check whether the queue is empty
		if(numItems == 0){
			throw new EmptyQueueException();
		}
		//save the front item in a temporary variable
		E temp = things[frontIndex];
		//change the frontIndex
		if(frontIndex != things.length-1){
			frontIndex++;
		}
		else{
			frontIndex = 0;
		}
		//decrease the numItems
		numItems--;
		return temp;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()
	{
		return things[frontIndex];
	}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty(){
		if(numItems != 0){
			return false;
		}
		return true;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()
	{
		//initialize all the fields again
		numItems = 0;
		things = (E[])(new Object[INITSIZE]);
		rearIndex = 0;
		frontIndex = 1;
	}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()
	{
		return numItems;
	}

	/**
	 * Internal method to expand array.
	 */
	private void expandCapacity()
	{
		//expanding should be done using the naive copy-all-elements approach
		//create a twice bigger array
		E[] tmp = (E[])(new Object[things.length *2]);
		//copy the items from the old queue to this new one with the correct 
		//index
		System.arraycopy(things, frontIndex, tmp, 
				frontIndex, things.length-frontIndex);
		if(frontIndex != 0){
			System.arraycopy(things, 0, tmp, things.length, frontIndex);
		}
		things = tmp;
		rearIndex = frontIndex + numItems - 1;
	}
}
