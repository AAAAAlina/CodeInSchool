///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:             BSTIterator.java
// Semester:         CS 367 Spring 2016
//
// Author:           Qiannan Guo
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

    /** Stack to track where the iterator is in the BST*/
    Stack<BSTNode<K>> stack;

    /**
     * Constructs the iterator so that it is initially at the smallest
     * value in the set. Hint: Go to farthest left node and push each node
     * onto the stack while stepping down the BST to get there.
     *
     * @param n the root node of the BST
     */
    public BSTIterator(BSTNode<K> n){
        //TODO
    	stack = new Stack<BSTNode<K>> ();
    	BSTNode<K> current = n;
    	//a method created by myself to do in-order traversal
    	repushInOrder(current);
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
        //TODO
    	return !stack.isEmpty();
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return the next item in the iteration
     * @throws NoSuchElementException if the iterator has no more items
     */
    public K next() {
        //TODO
    	if(!hasNext()){
    		throw new NoSuchElementException();
    	}
		BSTNode<K> item = stack.pop();
		repushInOrder(item.getRightChild());
		return item.getKey();
    }

    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * the method to store nodes into the stack as an in-order traversal, also
     * we can push and pop elements in the same time without disturbing the 
     * in-order traversal.
     *
     * @param r the root node of the BST we want to push in the stack.
     */
    private void repushInOrder(BSTNode<K> r){
    	while(r != null){
    		stack.push(r);
    		r = r.getLeftChild();
    	}
    }
}
