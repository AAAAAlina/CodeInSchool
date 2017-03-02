///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueueIterator.java
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
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueueIterator<T> implements Iterator<PriorityQueueItem<T>>
	{
	private PriorityQueue<T> priorityQueue;

	/**
	 * Constructs a PriorityQueueIterator by utilizing a copy of the
	 * PriorityQueue. Hint : The local priorityQueue object need not be
	 * preserved, and hence you can use the dequeue() operation.
	 * 
	 * @param pq
	 */
	public PriorityQueueIterator(PriorityQueue<T> pq)
		{
		// This copies the contents of the passed parameter to the local object.
		// Hint : see copy constructor in PriorityQueue
		priorityQueue = new PriorityQueue<T>(pq);
		}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean hasNext()
		{
		//check whether it's empty or not
		if (this.priorityQueue.size() == 0){
			return false;
		}
		else
			return true;
		}

	/**
	 * Returns the next element in the iteration. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public PriorityQueueItem<T> next()
		{
		PriorityQueueItem<T> nextItem = this.priorityQueue.dequeue();
		return nextItem;
		}

	/**
	 * Unsupported in this iterator for this assignment
	 */
	@Override
	public void remove()
		{
		// Do not implement
		throw new UnsupportedOperationException();
		}

	}
