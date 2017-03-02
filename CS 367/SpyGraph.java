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
 * Stores all vertexes as a list of GraphNodes.  Provides necessary graph operations as
 * need by the SpyGame class.
 * 
 * @author strominger
 *
 */
public class SpyGraph implements Iterable<GraphNode> {

    /** DO NOT EDIT -- USE THIS LIST TO STORE ALL GRAPHNODES */
    private List<GraphNode> vlist;


    /**
     * Initializes an empty list of GraphNode objects
     */
    public SpyGraph(){
         // TODO initialize data member(s)
    	vlist = new ArrayList<GraphNode> ();
    }

    /**
     * Add a vertex with this label to the list of vertexes.
     * No duplicate vertex names are allowed.
     * @param name The name of the new GraphNode to create and add to the list.
     */
    public void addGraphNode(String name){
         // TODO implement this method
    	//initialize a boolean to check whether there are duplicated items
    	boolean isDup = false;
    	for(int i = 0; i < vlist.size(); i++){
    		String check = vlist.get(i).getNodeName();
    		if(check.equals(name)){
    			isDup = true;
    		}
    	}
    	if(!isDup){
    		GraphNode newItem = new GraphNode(name);
    		vlist.add(newItem);
    	}
    }

    /**
     * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
     * Also sets the cost for each neighbor pair.
     *   
     * @param v1name The name of the first vertex of this edge
     * @param v2name The name of second vertex of this edge
     * @param cost The cost of traveling to this edge
     * @throws IllegalArgumentException if the names are the same
     */
    public void addEdge(String v1name, String v2name, int cost) throws IllegalArgumentException{
         // TODO implement this method
    	if(v1name.equals(v2name)){
    		throw new IllegalArgumentException();
    	}
    	GraphNode v1 = getNodeFromName(v1name);
    	GraphNode v2 = getNodeFromName(v2name);
    	v1.addNeighbor(v2, cost);
    	v2.addNeighbor(v1, cost);
    }

    /**
     * Return an iterator through all nodes in the SpyGraph
     * @return iterator through all nodes in alphabetical order.
     */
    public Iterator<GraphNode> iterator() {
        return vlist.iterator();
    }

    /**
     * Return Breadth First Search list of nodes on path 
     * from one Node to another.
     * @param start First node in BFS traversal
     * @param end Last node (match node) in BFS traversal
     * @return The BFS traversal from start to end node.
     */
    public List<Neighbor> BFS( String start, String end ) {
         // TODO implement this method
         // may need and create a companion method
    	if(start == null || end == null){
    		throw new IllegalArgumentException();
    	}
    	List<Neighbor> route = new ArrayList<Neighbor> ();
    	List<GraphNode> visited = new ArrayList<GraphNode> ();
    	int trace = 0;
    	boolean stop = false;
    	doBFS(start, end, route, visited, trace, stop);
    	if(route.isEmpty()){
    		throw new IllegalArgumentException();
    	}
        return route;
    }
    
    private void doBFS(String start, String end, List<Neighbor> route,
    		List<GraphNode> visited, int trace, boolean stop){
    	GraphNode option = getNodeFromName(start);
    	List<Neighbor> optionNeighbor = option.getNeighbors();
    	Queue<Neighbor> queue = new LinkedList<Neighbor> ();
    	for(int i = 0; i < optionNeighbor.size(); i++){
			if(!visited.contains(optionNeighbor.get(i).getNeighborNode())){
				visited.add(optionNeighbor.get(i).getNeighborNode());
				queue.add(optionNeighbor.get(i));
			}
		}
    	while(!queue.isEmpty() && !stop){
    			route.add(queue.remove());
    		start = route.get(route.size()-1).getNeighborNode().getNodeName();
    		for(int i = 0; i < route.size(); i++){
    			if(route.get(i).getNeighborNode().getNodeName().equals(end)){
    				stop = true;
    			}
    		}
    		if(!stop){
    			doBFS(start, end, route, visited, trace+1, stop);
    		}
    		for(int i = 0; i < route.size(); i++){
    			if(route.get(i).getNeighborNode().getNodeName().equals(end)){
    				stop = true;
    			}
    		}
    		if(!stop){
    			route.remove(route.size()-1);
    		}
    		else{
    			return;
    		}
    	}
    }


    /**
     * @param name Name corresponding to node to be returned
     * @return GraphNode associated with name, null if no such node exists
     */
    public GraphNode getNodeFromName(String name){
        for ( GraphNode n : vlist ) {
            if (n.getNodeName().equalsIgnoreCase(name))
                return n;
        }
        return null;
    }

    /**
     * Return Depth First Search list of nodes on path 
     * from one Node to another.
     * @param start First node in DFS traversal
     * @param end Last node (match node) in DFS traversal
     * @return The DFS traversal from start to end node.
     */
    public List<Neighbor> DFS(String start, String end) {
    	if(start == null || end == null){
    		throw new IllegalArgumentException();
    	}
    	List<Neighbor> route = new ArrayList<Neighbor> ();
    	//check whether the location is visited
    	List<GraphNode> visited = new ArrayList<GraphNode> ();
    	//boolean for checking whether reaches the end
    	boolean stop = false;
    	//helper method for recursion
    	doDFS(start, end, route, visited, stop);
    	if(route.isEmpty()){
    		throw new IllegalArgumentException();
    	}
        return route;
    }
    
    private void doDFS(String start, String end,
    		List<Neighbor> route, List<GraphNode> visited, boolean stop){
    	GraphNode option = getNodeFromName(start);
    	//set start to be visited
    	visited.add(option);
    	//get the neighbor of the start
    	List<Neighbor> optionNeighbor = option.getNeighbors();
    	for(int i = 0; i < optionNeighbor.size(); i++){
    		//check whether we need to stop
    		if(stop){
    			return;
    		}
    		if(!visited.contains(optionNeighbor.get(i).getNeighborNode())){
    			visited.add(optionNeighbor.get(i).getNeighborNode());
    			//reset the start
    			String start2 = 
    					optionNeighbor.get(i).getNeighborNode().getNodeName();
    			//add the neighbor to the path
    			route.add(optionNeighbor.get(i));
    			//whether the path reach the end
    			for(int c = 0; c < route.size(); c++){
    				if(route.get(c).getNeighborNode().
    						equals(getNodeFromName(end))){
    					stop = true;
    				}
    			}
    			//do the recursion
    			if(!stop){
    				doDFS(start2, end, route, visited, stop);
    			}
    			//if the path doesn't reach the end, remove the neighbor that 
    			//can not reach the end, if the path can reach the end, keep 
    			//the path unchanged.
    			for(int c = 0; c < route.size(); c++){
    				if(route.get(c).getNeighborNode().
    						equals(getNodeFromName(end))){
    					stop = true;
    				}
    			}
    			if(!stop){
    				route.remove(route.size()-1);
    			}
    			else{
    				return;
    			}
    		}
    	}
    }

    /**
     * OPTIONAL: Students are not required to implement Dijkstra's ALGORITHM
     *
     * Return Dijkstra's shortest path list of nodes on path 
     * from one Node to another.
     * @param start First node in path
     * @param end Last node (match node) in path
     * @return The shortest cost path from start to end node.
     */
    public List<Neighbor> Dijkstra(String start, String end){

        // TODO: implement Dijkstra's shortest path algorithm
        // may need and create a companion method
        
        return null;
    }


    /**
     * DO NOT EDIT THIS METHOD 
     * @return a random node from this graph
     */
    public GraphNode getRandomNode() {
        if (vlist.size() <= 0 ) {
            System.out.println("Must have nodes in the graph before randomly choosing one.");
            return null;
        }
        int randomNodeIndex = Game.RNG.nextInt(vlist.size());
        return vlist.get(randomNodeIndex);
    }


}
