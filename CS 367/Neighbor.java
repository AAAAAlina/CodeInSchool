///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            SpyGraph
// Files:            SpyGraph.java
//					 Neighbor.java
//					 Player.java
//					 GraphNode.java
// Semester:         CS 367 Spring 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentry
//
////////////////////////////////////////////////////////////////////////////////

/**
 * Store neighbors of the GraphNode and create some useful methods.
 *
 * @author Qiannan Guo
 */
public class Neighbor implements Comparable<Neighbor>{
	
	//field
	private int cost;
	private GraphNode neighbor;

	//constructors
	public Neighbor(int cost, GraphNode neighbor){
		this.cost = cost;
		this.neighbor = neighbor;
	}
	
	/**
	 * obtain the cost
	 *
	 * @return the cost of the edge
	 */
	public int getCost(){
		return cost;
	}
	
	/**
	 * obtain the GraphNode type of the Neighbor
	 *
	 * @return the graphNode of the neighbor
	 */
	public GraphNode getNeighborNode(){
		return neighbor;
	}
	
	/**
	 * compare the neighbor to another neighbor
	 *
	 * @param otherNode another neighbor we want to compare with
	 * @return how difference they are
	 * @throws IllegalArgumentException if the otherNode is nothing
	 */
	public int compareTo(Neighbor otherNode){
		if(otherNode == null){
			throw new IllegalArgumentException();
		}
		//get the string name
		String nodeName = neighbor.getNodeName();
		String nodeName2 = otherNode.getNeighborNode().getNodeName();
		//find their difference
		int diff = nodeName.compareTo(nodeName2);
		return diff;
	}
	
	/**
	 * get string name of the Neighbor
	 *
	 * @return string name of the neighbor
	 */
	public String toString(){
		String nodeName = neighbor.getNodeName();
		String printout = "--"+cost+"--> "+nodeName;
		return printout;
	}
	
}
