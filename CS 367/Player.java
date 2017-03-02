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
 * Store player's information and create some useful methods.
 *
 * @author Qiannan Guo
 */
public class Player {
	
	//field
	private String name;
	private int budget;
	private int spycams;
	private GraphNode startnode;
	private List<String> spycamsList;
	
	//constructor
	public Player(String name, int budget, int spycams, GraphNode startnode){
		if(name == null || startnode == null || budget < 0 || spycams < 0){
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.budget = budget;
		this.spycams = spycams;
		this.startnode = startnode;
		spycamsList = new ArrayList<String> ();
	}
	
	/**
	 * get the Player's name
	 *
	 * @return Player's name
	 */
	public String getName(){
		return name;
	}

	/**
	 * get the player's budget
	 *
	 * @return player's budget
	 */
	public int getBudget(){
		return budget;
	}
	
	/**
	 * decrease the budget, decrease the cost on the way
	 *
	 * @param dec the money player spends on the way
	 */
	public void decreaseBudget(int dec){
		budget = budget - dec;
	}
	
	/**
	 * put a spycam at current location
	 *
	 * @return if it drops successfully
	 */
	public boolean dropSpycam(){
		//initial the boolean to false
		boolean drop = false;
		//check whether the spycams are enough
		if(spycams <= 0){
			System.out.println( "Not enough spycams");
		}
		else{
			//get current location
			GraphNode currLoca = getLocation();
			//check whether there is a spycam at current location
			//if not, set the spycam and decrease the number of spycam
			if(!currLoca.getSpycam()){
				spycams = spycams - 1;
				currLoca.setSpycam(true);
				//change the boolean value
				drop = true;
				//add the new spycam to the spycam list
				spycamsList.add(getLocationName());
				System.out.println("Dropped a Spy Cam at " + getLocationName());
			}
			else{
				System.out.println("Already a Spy Cam there");
			}
			
		}
		return drop;
	}
	
	/**
	 * get a spycam back from specific position
	 *
	 * @param node where need to get the node back
	 * @return if we get the spycam successfully
	 * @throws IllegalArgumentException if the node is nothing
	 */
	public boolean pickupSpycam(GraphNode node){
		if(node == null){
			throw new IllegalArgumentException();
		}
		boolean pickup = false;
		if(node.getSpycam()){
			node.setSpycam(false);
			pickup = spycamsList.remove(node.getNodeName());
		}
		getSpycamBack(pickup);
		return pickup;
	}
	
	/**
	 * know how many spycams left
	 *
	 * @return the number of spycams the player has
	 */
	public int getSpycams(){
		return spycams;
	}
	
	/**
	 * change the position to another one
	 *
	 * @param name the name of the location player wants to move
	 * @return if the player move there successfully
	 * @throws IllegalArgumentException if the name is nothing
	 */
	public boolean move(String name){
		if(name == null){
			throw new IllegalArgumentException();
		}
		//initial the boolean to false, which means the player hasn't move
		boolean move = false;
		GraphNode currLoca = getLocation();
		try{
			//how much money needed to move
			int cost = currLoca.getCostTo(name);
			//check whether the budget is enough
			if(budget-cost <= 0){
				System.out.println("Not enough money cost is " + cost + 
						" budget is " + budget);
			}
			else{
				//change the current location
				startnode = currLoca.getNeighbor(name);
				//finish the move, change the boolean value
				move = true;
				//if cost is bigger than 1, we need to spend the money
				if(cost > 1){
					decreaseBudget(cost);
				}
			}
		}
		//if the name is not a neighbor of current location
		catch(NotNeighborException e){
			System.out.println(name+" is not a neighbor of your current "
					+ "location");
		}
		return move;
	}
	
	/**
	 * get the string name of the current location
	 *
	 * @return the string name of the current location
	 */
	public String getLocationName(){
		return getLocation().getNodeName();
	}
	
	/**
	 * get the GraphNode of current Location
	 *
	 * @return the GraphNode of current Location
	 */
	public GraphNode getLocation(){
		return startnode;
	}
	
	/**
	 * change the number of spycams the player havs
	 *
	 * @param pickupSpyCam whether the spycam is successfully picked up
	 * @return the total number of spycams the player has
	 */
	public void getSpycamBack(boolean pickupSpyCam){
		//if pickup succeeds, increase the number of spycams
		if(pickupSpyCam){
			spycams = spycams + 1;
		}
	}
	
	/**
	 * print the locations that has been set spycams
	 *
	 */
	public void printSpyCamLocations(){
		for(int i = 0; i < spycamsList.size(); i++){
			System.out.println("Spy cam at "+spycamsList.get(i));
		}
	}
}
