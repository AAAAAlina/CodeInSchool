///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueue.java
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implemented as a Binary Heap backed by an array. Implements
 * QueueADT. Each entry in the PriorityQueue is of type PriorityQueueNode<E>.
 * First element is array[1]
 * 
 * @author CS367
 *
 * @param <E>
 */
public class PriorityQueue<E> implements QueueADT<PriorityQueueItem<E>>
{
	private final int DEFAULT_CAPACITY = 100;

	// Number of elements in heap
	private int currentSize;

	/**
	 * The heap array. First element is array[1]
	 */
	private PriorityQueueItem<E>[] array;

	/**
	 * Construct an empty PriorityQueue.
	 */
	public PriorityQueue()
	{
		currentSize = 0;
		// the below code initializes the array.. similar code used for
		// expanding.
		array = (PriorityQueueItem<E>[]) Array.
				newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
	}

	/**
	 * Copy constructor
	 * 
	 * @param pq
	 *            PriorityQueue object to be copied
	 */
	public PriorityQueue(PriorityQueue<E> pq)
	{
		this.currentSize = pq.currentSize;
		this.array = Arrays.copyOf(pq.array, currentSize + 1);
	}

	/**
	 * Adds an item to this PriorityQueue. If array is full, double the array
	 * size.
	 * 
	 * @param item
	 *            object of type PriorityQueueItem<E>.
	 */
	@Override
	public void enqueue(PriorityQueueItem<E> item)
	{
		boolean added = false;
		// Check if array is full - double if necessary
		if (array.length == currentSize + 1){
			doubleArray();
		}
		// Check all nodes and find if one with equal priority exists.
		// Add to the existing node's list if it does
		for (int i = 1; i <= currentSize; i++){
			if(array[i].compareTo(item) == 0){
				array[i].add(item.getList().dequeue());
				added = true;
			}
		}
		// Else create new node with value added to list and percolate it up
		if (!added){
			PriorityQueueItem<E> tmp =
					new PriorityQueueItem<E>(item.getPriority());
			tmp.add(item.getList().dequeue());
			array[currentSize + 1] = tmp;
			percolateUp(currentSize + 1);
			currentSize++;
			added = true;
		}
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size()
	{
		return this.currentSize;
	}

	/**
	 * Returns a PriorityQueueIterator. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return iterator over the elements in this PriorityQueue
	 */
	public Iterator<PriorityQueueItem<E>> iterator()
	{
		Iterator<PriorityQueueItem<E>> iterator =
				new PriorityQueueIterator<E>(this);
		return iterator;
	}

	/**
	 * Returns the largest item in the priority queue.
	 * 
	 * @return the largest priority item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> peek()
	{
		if (currentSize == 0){
			throw new NoSuchElementException();
		}
		return array[1];
	}

	/**
	 * Removes and returns the largest item in the priority queue. Switch last
	 * element with removed element, and percolate down.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> dequeue()
	{
		// if empty
		if (this.currentSize == 0){
			throw new NoSuchElementException();
		}
		else {
			// Remove first element
			PriorityQueueItem<E> tmpData = this.array[1];	
			// Replace with last element, percolate down
			array[1] = array[currentSize];
			currentSize--;
			percolateDown(1);
			return tmpData;
		}
	}

	/**
	 * Heapify Establish heap order property from an arbitrary arrangement of
	 * items. ie, initial array that does not satisfy heap property. Runs in
	 * linear time.
	 */
	private void buildHeap()
	{
		for (int i = currentSize / 2;i > 0;i--)
			percolateDown(i);
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear()
	{
		currentSize = 0;
	}

	/**
	 * Internal method to percolate up in the heap.
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateUp(int hole)
	{
		boolean done = false;
		//check if the hole is at the right place or it is the first on
		while(hole > 1 && !done){
			//compare hole with its parent, if hole is bigger,
			//we need to switch them
			if (this.array[hole].compareTo(this.array[hole / 2]) == 1){
				PriorityQueueItem<E> tmpData = this.array[hole / 2];
				this.array[hole / 2] = this.array[hole];
				this.array[hole] = tmpData;
				hole = (hole / 2);
			}
			//if hole is smaller, stay in the same position
			else {
				done = true;
			}
		}
	}
	/**
	 * Internal method to percolate down in the heap. <a
	 * href="https://en.wikipedia.org/wiki/Binary_heap#Extract">Wiki</a>}
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole)
	{
		boolean done = false;
		//check whether hole has descendants or it is in the correct 
		//position, if hole is smaller than anyone of its two descendants,
		//switch them until hole is in the right position
		while((hole * 2) <= currentSize && !done){
			// compare hole with its first descendant
			if (this.array[hole].compareTo(this.array[hole * 2]) == -1 ||
					((hole * 2 + 1) <= currentSize &&
					this.array[hole].compareTo(this.array[hole * 2 + 1])
					== -1 )){
				// compare the 1st descendant with its second descendant and
				// switch the hole with larger descendant
				if (this.array[hole*2 + 1].compareTo(this.array[hole * 2]) 
						== 1 ){
					PriorityQueueItem<E> tmpData = this.array[hole * 2 + 1];
					this.array[hole * 2 + 1] = this.array[hole];
					this.array[hole] = tmpData;
					hole = ((hole * 2) + 1);
				}
				else{
					PriorityQueueItem<E> tmpData = this.array[hole * 2];
					this.array[hole * 2] = this.array[hole];
					this.array[hole] = tmpData;
					hole = (hole * 2);
				}
			}
			else {
				done = true;
			}
		}
	}

	/**
	 * Internal method to expand array.
	 */
	private void doubleArray()
	{
		PriorityQueueItem<E>[] newArray;

		newArray = (PriorityQueueItem<E>[]) 
				Array.newInstance(PriorityQueueItem.class, array.length * 2);

		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
	}

	/**
	 * Returns true if the PriorityQueue is empty.
	 * 
	 * @return true if priority queue is empty; otherwise false.
	 */
	@Override
	public boolean isEmpty()
	{
		if(currentSize == 0)
			return true;
		return false;
	}
}
