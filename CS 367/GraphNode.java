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

import java.util.*;
/**
 * Store locations of the map and create some useful methods.
 *
 * @author Qiannan Guo
 */
public class GraphNode implements Comparable<GraphNode>{
	
	//field
	public static final int NOT_NEIGHBOR = 2147483647;
	
	private List<Neighbor> neighbors;
	private String name;
	private boolean hasSpycam;
	
	//constructors
	public GraphNode(String name){
		this.name = name;
		this.hasSpycam = false;
		neighbors = new ArrayList<Neighbor> ();
	}
	
	/**
	 * get the GraphNode's name
	 *
	 * @return GraphNode's name(location's name)
	 */
	public String getNodeName(){
		return name;
	}
	
	/**
	 * get the list of one location's neighbors
	 *
	 * @return a list of the location's neighbors
	 */
	public List<Neighbor> getNeighbors(){
		return neighbors;
	}
	
	/**
	 * check whether it is the location's neighbor
	 *
	 * @param neighborName the name of the neighbor we want to check
	 * @return A boolean shows whether it is a neighbor of the location
	 */
	public boolean isNeighbor(String neighborName){
		boolean exist = false;
		//compare their strings to check if they are equal
		for(int i = 0; i < neighbors.size(); i++){
			String checkName = neighbors.get(i).getNeighborNode().getNodeName();
			if(checkName.compareTo(neighborName) == 0){
				exist = true;
			}
		}
		return exist;
	}
	
	/**
	 * add a neighbor to the location's neighborList
	 *
	 * @param neighbor the GraphNode of the neighbor wanted to add
	 * @param cost the cost from the location to that neighbor
	 * @return (description of the return value)
	 * @throws IllegalArgumentException if the neighbor is nothing
	 */
	public void addNeighbor(GraphNode neighbor, int cost){
		if(neighbor == null){
			throw new IllegalArgumentException();
		}
		//create a neighbor object
		Neighbor newItem = new Neighbor(cost ,neighbor);
		//add it to the list and sort the list
		neighbors.add(newItem);
		neighbors.sort(null);	
	}
	
	/**
	 * obtain a iterator of a list of neighbor's names
	 *
	 * @return a iterator of strings
	 */
	public Iterator<String> getNeighborNames(){
		//create a new list
		List<String> names = new ArrayList<String>();
		//add strings
		for(int i = 0; i < neighbors.size(); i++){
			names.add(neighbors.get(i).getNeighborNode().getNodeName());
		}
		return names.iterator();
	}
	
	/**
	 * check if there is a spycam at this location
	 *
	 * @return boolean to show whether here is a spycam
	 */
	public boolean getSpycam(){
		return hasSpycam;
	}

	/**
	 * set a spycam at this location
	 *
	 * @param cam to decide whether we need to set a spycam here
	 */
	public void setSpycam(boolean cam){
		hasSpycam = cam;
	}
	
	/**
	 * know the cost from this location to the indicated neighbor
	 *
	 * @param neighbor the neighbor's name that we want to know the cost
	 * @return the cost between this locattion to the neighbor
	 * @throws NotNeighborException if the neighbor's name is not in the 
	 * neighbor list
	 */
	public int getCostTo(String neighborName) throws NotNeighborException{
		for(int i = 0; i < neighbors.size(); i++){
			String check = neighbors.get(i).getNeighborNode().getNodeName();
			//find the neighbor
			if(check.equals(neighborName)){
				//get the cost
				int cost = neighbors.get(i).getCost();
				return cost;
			}
		}
		throw new NotNeighborException();
	}
	
	/**
	 * get the graphNode of one of the neighbor of current location
	 *
	 * @param name the string of the neighbor
	 * @return the GraphNode of the neighbor
	 * @throws NotNeighborException of the name is not a name of current 
	 * location's neighbors
	 */
	public GraphNode getNeighbor(String name) throws NotNeighborException{
		for(int i = 0; i < neighbors.size(); i++){
			String check = neighbors.get(i).getNeighborNode().getNodeName();
			//if the string names are equal, the name is a neighbor in the 
			//neighbor list
			if(check.equals(name)){
				GraphNode node = neighbors.get(i).getNeighborNode();
				return node;
			}
		}
		throw new NotNeighborException();
	}
	
	/**
	 * print out the cost of every neighbor of the current location
	 *
	 */
	public void displayCostToEachNeighbor(){
		for(int i = 0; i < neighbors.size(); i++){
			Neighbor item = neighbors.get(i);
			System.out.println(item.getCost()+" "
			+item.getNeighborNode().getNodeName());
		}
	}
	
	/**
	 * compare the current location to another location
	 *
	 * @param otherNode another location we want to compare with
	 * @return how difference they are
	 * @throws IllegalArgumentException if the otherNode is nothing
	 */
	public int compareTo(GraphNode otherNode){
		if(otherNode == null){
			throw new IllegalArgumentException();
		}
		//get the string name of the parameter
		String name2 = otherNode.getNodeName();
		int diff = name.compareTo(name2);
		return diff;
	}
	
	/**
	 * get string name of the Graphnode
	 *
	 * @return string name of the node
	 */
	public String toString(){
		return name;
	}
	
	/**
	 * print the neighbors' names arround the current location
	 *
	 */
	public void printNeighborNames(){
		for(int i = 0; i < neighbors.size(); i++){
			Neighbor item = neighbors.get(i);
			System.out.println(item.getCost()+" "
			+item.getNeighborNode().getNodeName());
		}
	}
}
