///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueueItem.java
// Semester:         CS 367 Spring 2016
//
// Author:           Jiatong Li    jli553@wisc.edu
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
 * 
 * Class to represent object stored at every entry in the PriorityQueue. ie, The
 * internal node structure of {@link PriorityQueue}
 * 
 * @author CS367
 *
 * @param <E>
 *            the generic type of the data content stored in the list
 */
public class PriorityQueueItem<E> implements Comparable<PriorityQueueItem<E>>
{

	private int priority;
	private Queue<E> queue;
	/**
	 * Constructs a PriorityQueueItem according to its priority
	 * 
	 * @param priority
	 */
	public PriorityQueueItem(int priority)
	{
		this.priority = priority;
		queue = new Queue<E>();
	}
	/**
	 * Returns the priority of the item.
	 * 
	 * @return priority.
	 */
	public int getPriority()
	{
		return this.priority;
	}
	/**
	 * Returns the queue.
	 * 
	 * @return queue.
	 */
	public Queue<E> getList()
	{
		return this.queue;
	}

	/**
	 * Add an item to the queue of this PriorityQueueItem object
	 * 
	 * @param item
	 *            item to add to the queue
	 */
	public void add(E item)
	{
		this.queue.enqueue(item);
	}

	/**
	 * Compares this Node to another node on basis of priority
	 * 
	 * @param o
	 *            other node to compare to
	 * @return -1 if this node's priority is lesser, +1 if this nodes priority
	 *         is higher after, else 0 if priorities are the same.
	 */
	@Override
	public int compareTo(PriorityQueueItem<E> o) {
		if(this.priority < o.priority){
			return -1;
		}
		else if (this.priority > o.priority){
			return 1;
		}
		else {
			return 0;
		}
	}

}
