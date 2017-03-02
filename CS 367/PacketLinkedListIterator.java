///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Receiver.java
// File:             PacketLinkedListIterator.java
// Semester:         367 Spring 2016
//
// Author:           Qiannan Guo qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The iterator implementation for PacketLinkedList.
 */
public class PacketLinkedListIterator<T> implements Iterator<T> {
	// TODO: add field when needed
	private Listnode<T> curr;
	
	/**
	 * Constructs a PacketLinkedListIterator by passing a head node of
	 * PacketLinkedList.
	 * 
	 * @param head
	 */
	public PacketLinkedListIterator(Listnode<T> head) {
		// TODO
		curr = head;
	}
	
	/**
	 * Returns the next element in the iteration.
	 * @return the next element in the iteration
	 * @throws NoSuchElementException if the iteration has no more elements
	 */
	@Override
	public T next() {
		//TODO: replace the default return statment
		if(curr == null){
			throw new NoSuchElementException();
		}
		curr = curr.getNext();
		return curr.getData();
	}
	
	/**
	 * Returns true if the iteration has more elements.
	 * @return true if the iteration has more elements
	 */
	@Override
	public boolean hasNext() {
		//TODO: replace the default return statment
		return curr.getNext() != null;
	}

        /**
         * The remove operation is not supported by this iterator
         * @throws UnsupportedOperationException if the remove operation is not supported by this iterator
         */
        @Override
	public void remove() {
	  throw new UnsupportedOperationException();
	}
}
