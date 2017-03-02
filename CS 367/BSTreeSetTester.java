///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:             BSTreeSetTester.java
// Semester:         CS 367 Spring 2016
//
// Author:           Qiannan Guo
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> 
													implements SetTesterADT<K>{

    /** Root of this tree */
    BSTNode<K> root;

    /** Number of items in this tree*/
    int numKeys;

    /**
     * rebalanceThreshold &lt;= 0 DOES NOT REBALANCE (BSTree).<br>
     * rebalanceThreshold  &gt;0 rebalances the tree (BSTreeB).
      */
    int rebalanceThreshold;

    /**
     * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
     * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
     * rebalanceThreshold is non-positive, isBalanced must be true.
     */
    boolean isBalanced;


    /**
     * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
     *
     * @param rbt the rebalance threshold
     */
    public BSTreeSetTester(int rbt) {
        //TODO
    	root = null;
    	rebalanceThreshold = rbt;
    	numKeys = 0;
    	isBalanced = true;
    }

    /**
     * Add node to binary search tree. Remember to update the height and
     * balancedFfactor of each node. Also rebalance as needed based on
     * rebalanceThreshold.
     *
     * @param key the key to add into the BST
     * @throws IllegalArgumentException if the key is null
     * @throws DuplicateKeyException if the key is a duplicate
     */
    public void add(K key) {
        //TODO
    	if(key == null){
    		throw new IllegalArgumentException();
    	}
    	//if key has already been in the tree, that means it is duplicated
    	if(contains(key)){
    		throw new DuplicateKeyException();
    	}
    	//use an auxiliary to do tasks recursively
    	root = add(root, key);
    	//update the number of elements in the tree
    	numKeys++;
    	//check if rebalance needed
    	if(isBalanced == false){
    		rebalance();
    	}
    }

    /**
     * Rebalances the tree by:
     * 1. Copying all keys in the BST in sorted order into an array.
     *    Hint: Use your BSTIterator.
     * 2. Rebuilding the tree from the sorted array of keys.
     */
    public void rebalance() {
        K[] keys = (K[]) new Comparable[numKeys];
        //TODO
        int count = 0;
        Iterator<K> itr = iterator();
        while(itr.hasNext()){
        	keys[count] = itr.next();
        	count++;
        }
        //transfer the array back to a balanced BST
        root = sortedArrayToBST(keys,0,numKeys-1);
        isBalanced = true;
    }

    /**
     * Recursively rebuilds a binary search tree from a sorted array.
     * Reconstruct the tree in a way similar to how binary search would explore
     * elements in the sorted array. The middle value in the sorted array will
     * become the root, the middles of the two remaining halves become the left
     * and right children of the root, and so on. This can be done recursively.
     * Remember to update the height and balanceFactor of each node.
     *
     * @param keys the sorted array of keys
     * @param start the first index of the part of the array used
     * @param stop the last index of the part of the array used
     * @return root of the new balanced binary search tree
     */
    private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) {
        //TODO
    	if(start > stop){
    		return null;
    	}
    	//find the middle element in the array
    	int mid = (stop+start)/2;
    	//save it as the root of BST
    	BSTNode<K> newArray = new BSTNode<K>(keys[mid]);
    	//use auxiliaries to recursively deal with the subtree
    	newArray.setLeftChild(sortedArrayToBST(keys,start,mid-1));
    	newArray.setRightChild(sortedArrayToBST(keys,mid+1,stop));
    	
    	//update the height and balanced factor for each node after rebalancing
    	//for the node only have left leaf, its height is the height of left
    	//leaf+1, its balance is the height of left leaf
    	if(newArray.getLeftChild() != null &&
    			newArray.getRightChild() == null){
    		newArray.setHeight(newArray.getLeftChild().getHeight()+1);
    		newArray.setBalanceFactor(newArray.getLeftChild().getHeight());
    	}
    	//for the node only have right leaf, its height is the height of right
    	//leaf+1, its balance is the height of right leaf
    	else if(newArray.getRightChild() != null &&
    			newArray.getLeftChild() == null){
    		newArray.setHeight(newArray.getRightChild().getHeight()+1);
    		newArray.setBalanceFactor(-newArray.getRightChild().getHeight());
    	}
    	//for the node have both two leaves, its height is the max of left 
    	//leaf's height and right leaf's height, then +1, its factor is left
    	//leaf's height - right leaf's
    	else if(newArray.getRightChild() != null &&
    			newArray.getLeftChild() != null){
    		int newHeight = Math.max(newArray.getLeftChild().getHeight()+1,
    				newArray.getRightChild().getHeight()+1);
    		newArray.setHeight(newHeight);
    		newArray.setBalanceFactor(newArray.getLeftChild().getHeight()-
    				newArray.getRightChild().getHeight());
    	}
    	return newArray;
    }

    /**
     * Returns true iff the key is in the binary search tree.
     *
     * @param key the key to search
     * @return true if the key is in the binary search tree
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(K key) {
        //TODO
    	if(key == null){
    		throw new IllegalArgumentException();
    	}
    	//an auxiliary to do recursion
        return contains(root, key);
    }

    /**
     * Returns the sorted list of keys in the tree that are in the specified
     * range (inclusive of minValue, exclusive of maxValue). This can be done
     * recursively.
     *
     * @param minValue the minimum value of the desired range (inclusive)
     * @param maxValue the maximum value of the desired range (exclusive)
     * @return the sorted list of keys in the specified range
     * @throws IllegalArgumentException if either minValue or maxValue is
     * null, or minValue is larger than maxValue
     */
    public List<K> subSet(K minValue, K maxValue) {
        //TODO
    	if(minValue == null || maxValue == null ||
    			minValue.compareTo(maxValue)>0){
    		throw new IllegalArgumentException();
    	}
    	//a list to store subset and for return
    	List<K> subSet = new <K> ArrayList();
    	//an auxiliary to do the task recursively in a BST
    	subSet = subSetHelper(subSet,root,minValue,maxValue);
        return subSet;
    }

    /**
     * Return an iterator for the binary search tree.
     * @return the iterator
     */
    public Iterator<K> iterator() {
        //TODO
        return new BSTIterator<K>(root);
    }

    /**
     * Clears the tree, i.e., removes all the keys in the tree.
     */
    public void clear() {
        //TODO
    	root = null;
    	numKeys = 0;
    	isBalanced = true;
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return the number of keys
     */
    public int size() {
        //TODO
        return numKeys;
    }

    /**
     * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
     *
     * @param maxDisplayLevels from the top of the BST that will be displayed
     */
    public void displayTree(int maxDisplayLevels) {
        if (rebalanceThreshold > 0) {
            System.out.println("---------------------------" +
                    "BSTreeBSet Display-------------------------------");
        } else {
            System.out.println("---------------------------" +
                    "BSTreeSet Display--------------------------------");   
        }
        displayTreeHelper(root, 0, maxDisplayLevels);
    }

    private void displayTreeHelper(BSTNode<K> n, int curDepth,
                                   int maxDisplayLevels) {
        if(maxDisplayLevels <= curDepth) return;
        if (n == null)
            return;
        for (int i = 0; i < curDepth; i++) {
            System.out.print("|--");
        }
        System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
                n.getBalanceFactor() + "}");
        displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
        displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
    }
    
    /**
     * an add-helper method to add key into the tree with root a.
     *
     * @param a The root node of the BST we want to add elements in
     * @param key The element we want to add into the BST
     * @return the new BST after adding key 
     */
    private BSTNode<K> add(BSTNode<K> a, K key){
    	//if a does not have a node, directly create a new BST
    	if(a == null){
		a = new BSTNode<K> (key);
		}
		else{
			//for element bigger than the root a, try to put the key into the 
			//left subtree
			if(key.compareTo(a.getKey()) < 0){
				a.setLeftChild(add(a.getLeftChild(), key));
	    	}
			//for element smaller than the root a, try to put the key into the 
			//right subtree
			else{
				a.setRightChild(add(a.getRightChild(), key));
			}
			//update the height and balance factor of each node in the BST
			if(a.getLeftChild() != null &&
	    			a.getRightChild() == null){
	    		a.setHeight(a.getLeftChild().getHeight()+1);
	    		a.setBalanceFactor(a.getLeftChild().getHeight());
	    	}
	    	else if(a.getRightChild() != null &&
	    			a.getLeftChild() == null){
	    		a.setHeight(a.getRightChild().getHeight()+1);
	    		a.setBalanceFactor(-a.getRightChild().getHeight());
	    	}
	    	else{
	    		int newHeight = Math.max(a.getLeftChild().getHeight()+1,
	    				a.getRightChild().getHeight()+1);
	    		a.setHeight(newHeight);
	    		a.setBalanceFactor(a.getLeftChild().getHeight()-
	    				a.getRightChild().getHeight());
	    	}
			//check whether the tree is balanced or not after adding a new key
	        if(rebalanceThreshold > 0 &&
	        		Math.abs(a.getBalanceFactor()) > rebalanceThreshold){
	                	isBalanced = false;
	        }
		}
    	return a;
    }
    
    /**
     * Returns true iff the key is in the binary search tree.
     *
     * @param a the root node of the BST we want to search
     * @param key the key to search
     * @return true if the key is in the binary search tree
     */
    private boolean contains(BSTNode<K> a, K key){
    	if(a == null){
    		return false;
    	}
    	else{
    		//if the key equals to a's key, that means the key exists in the BST
    		if(a.getKey().compareTo(key) == 0){
    			return true;
    		}
    		//for key smaller than a's key, find the element in the left subtree
    		else if(a.getKey().compareTo(key) > 0){
    			//recursion
    			return contains(a.getLeftChild(), key);
    		}
    		//for the key greater than a's key, find the element in the right
    		//subtree
    		else{
    			return contains(a.getRightChild(), key);
    		}
    	}
    }
    
    /**
     * A method helps subSet to do recursion. Returns the sorted list of keys 
     * in the tree that are in the specified range (inclusive of minValue, 
     * exclusive of maxValue).
     *
     * @param minValue the minimum value of the desired range (inclusive)
     * @param maxValue the maximum value of the desired range (exclusive)
     * @param s a list we want to store subSet in.
     * @param a the BST we want to find subSet from.
     * 
     * @return the sorted list of keys in the specified range
     */
    private List<K> subSetHelper(List<K> s, BSTNode<K> a, 
    		K minValue, K maxValue){
    	if(a != null){
    		//if the key smaller than minValue, find the element bigger than the 
        	//key which is in a's right subtree.
    		if(a.getKey().compareTo(minValue) < 0){
    			subSetHelper(s,a.getRightChild(),minValue,maxValue);
    		}
    		//if a's key is bigger than minValue and smaller than maxValue, add
    		//them in the list
	    	if(a.getKey().compareTo(minValue) >= 0 &&
	    			a.getKey().compareTo(maxValue) < 0){
	    		subSetHelper(s,a.getLeftChild(),minValue,maxValue);
	    		s.add(a.getKey());
	    		subSetHelper(s,a.getRightChild(),minValue,maxValue);
	    	}
	    	//if the key bigger than maxValue, find the element smaller than the 
	    	//key which is in a's left subtree.
	    	if(a.getKey().compareTo(maxValue) >= 0){
	    		subSetHelper(s,a.getLeftChild(),minValue,maxValue);
	    	}
    	}
    	return s;
    }
}
