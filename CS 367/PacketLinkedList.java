///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Receiver.java
// File:             PacketLinkedList.java
// Semester:         367 Spring 2016
//
// Author:           Qiannan Guo qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////
/**
 * A Single-linked linkedlist with a "dumb" header node (no data in the node), but
 * without a tail node. It implements ListADT<E> and returns
 * PacketLinkedListIterator when requiring a iterator.
 */
public class PacketLinkedList<E> implements ListADT<E> {
	// TODO: Add your fields here. 
	//       Please see ListADT for detailed javadoc
	//
	private Listnode<E> dumb;
	private int numItems;

	/**
	 * Constructs a empty PacketLinkedList
	 */
	public PacketLinkedList() {
		// TODO
		dumb = new Listnode<E> (null);
		numItems = 0;
	}

	@Override
	public void add(E item) {
		// TODO
		Listnode<E> newnode = new Listnode<E> (item);
		Listnode<E> curr = dumb;
		while(curr.getNext()!=null){
			curr = curr.getNext();
		}
		curr.setNext(newnode);
		numItems++;
	}

	@Override
	public void add(int pos, E item) {
		// TODO
		if(pos<0 || pos>=numItems){
			throw new IndexOutOfBoundsException();
		}
		Listnode<E> newnode = new Listnode<E> (item);
		Listnode<E> curr = dumb;
		if(pos == numItems){
			add(item);
		}
		for(int i=0; i<pos; i++){
			curr = curr.getNext();
		}
		newnode.setNext(curr.getNext());
		curr.setNext(newnode);
		numItems++;
	}

	@Override
	public boolean contains(E item) {
		// TODO: replace the default return statement
		Listnode<E> curr = dumb;
		for(int i=0; i<numItems; i++){
			curr = curr.getNext();
			if(curr.equals(item)){
				return true;
			}
		}
		return false;
	}

	@Override
	public E get(int pos) {
		// TODO: replace the default return statement
		if(pos<0 || pos>=numItems){
			throw new IndexOutOfBoundsException();
		}
		Listnode<E> curr = dumb;
		for(int i=0; i<pos; i++){
			curr = curr.getNext();
		}
		return curr.getNext().getData();
	}

	@Override
	public boolean isEmpty() {
		// TODO: replace the default return statement
		if(dumb.getNext() == null){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public E remove(int pos) {
		// TODO: replace the default return statement
		if(pos<0 || pos>=numItems){
			throw new IndexOutOfBoundsException();
		}
		Listnode<E> curr = dumb;
		for(int i=0; i<pos; i++){
			curr = curr.getNext();
		}
		E removedItem = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		numItems--;
		return removedItem;
	}

	@Override
	public int size() {
		// TODO: replace the default return statement
		return numItems;
	}

	@Override
	public PacketLinkedListIterator<E> iterator() {
		// TODO: replace the default return statement
		return new PacketLinkedListIterator<E> (dumb);
	}

}
