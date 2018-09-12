/**
 * This is a program for Torus 8-puzzle.
 *
 *
 * @author Qiannan Guo
 */


import java.util.*;

class State {
	int[] board;
	State parentPt;
	int depth;

	public State(int[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
		this.parentPt = null;
		this.depth = 0;
	}

	public State[] getSuccessors() {
		
		// TO DO: get all four successors and return them in sorted order
		
		//find the position of the empty square(which is number zero)
		int zero = -1;
		State[] successors = new State[4];
		for(int i = 0; i < this.board.length; i++) {
			if(this.board[i] == 0) {
				zero = i;
			}
		}
		//cannot find zero means this puzzle is invalid
		if(zero == -1) {
			System.exit(-1);
		}
		
		//find the four possible successors of the state
		int[] tempboard = this.board.clone();
		tempboard[zero] = tempboard[(zero+6)%9];
		tempboard[(zero+6)%9] = 0;
		successors[0] = new State(tempboard);
		successors[0].depth = this.depth+1;
		successors[0].parentPt = this;
		
		tempboard = this.board.clone();
		tempboard[zero] = tempboard[(zero+3)%9];
		tempboard[(zero+3)%9] = 0;
		successors[1] = new State(tempboard);
		successors[1].depth = this.depth+1;
		successors[1].parentPt = this;
		
		tempboard = this.board.clone();
		if((zero%3) == 1) {
			tempboard[zero] = tempboard[(zero-1)];
			tempboard[(zero-1)] = 0;
		}
		else if((zero%3) == 0) {
			tempboard[zero] = tempboard[(zero+2)];
			tempboard[(zero+2)] = 0;
		}
		else {
			tempboard[zero] = tempboard[(zero-2)];
			tempboard[(zero-2)] = 0;
		}
		successors[2] = new State(tempboard);
		successors[2].depth = this.depth+1;
		successors[2].parentPt = this;
		
		tempboard = this.board.clone();
		if((zero%3) == 1) {
			tempboard[zero] = tempboard[(zero+1)];
			tempboard[(zero+1)] = 0;
		}
		else if((zero%3) == 0) {
			tempboard[zero] = tempboard[(zero+1)];
			tempboard[(zero+1)] = 0;
		}
		else {
			tempboard[zero] = tempboard[(zero-1)];
			tempboard[(zero-1)] = 0;
		}
		successors[3] = new State(tempboard);
		successors[3].depth = this.depth+1;
		successors[3].parentPt = this;

		//sort the four successors from the smallest to the largest
		for(int i = 0; i < successors.length; i++) {
			int min = smallestSuccessors(i, successors.length, successors);
			State temp = successors[i];
			successors[i] = successors[min];
			successors[min] = temp;
		}
		return successors;
	}

	public void printState(int option) {
		
		// TO DO: print a torus State based on option (flag)
		
		//if the option is 3, parents are also needed to be printed.
		if(option == 3) {
			System.out.print(this.getBoard());
			System.out.print(" parent ");
			if(this.parentPt == null) {
				System.out.println("0 0 0 0 0 0 0 0 0");
			}
			else {
				System.out.println(this.parentPt.getBoard());
			}
		}
		else {
			System.out.println(this.getBoard());
		}
	}

	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]).append(" ");
		}
		return builder.toString().trim();
	}

	public boolean isGoalState() {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != (i + 1) % 9)
				return false;
		}
		return true;
	}

	public boolean equals(State src) {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != src.board[i])
				return false;
		}
		return true;
	}
	
	//convert the array to a 9-digit integer
	public int arrayToInt() {
		int arrayValue = 0;
		for(int i = 0; i < this.board.length; i++) {
			arrayValue = arrayValue + (int)(this.board[i]*Math.pow(10, (8-i)));
		}
		return arrayValue;
	}
	
	//find the state has the smallest array value among a state array
	public int smallestSuccessors(int begin, int end, State[] successors) {
		int index = 0;
		int minValue = (int)(Math.pow(10, 10));
		for(int i = begin; i >=begin && i<end; i++) {
			int arrayValue = successors[i].arrayToInt();
			if(arrayValue<minValue) {
				index = i;
				minValue = arrayValue;
			}
		}
		return index;
	}
}

public class Torus {

	public static void main(String args[]) {
		if (args.length < 10) {
			System.out.println("Invalid Input");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		int[] board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = Integer.valueOf(args[i + 1]);
		}
		int option = flag / 100;
		int cutoff = flag % 100;
		
		//print the successors for option 1
		if (option == 1) {
			State init = new State(board);
			State[] successors = init.getSuccessors();
			for (State successor : successors) {
				successor.printState(option);
			}
		} else {
			State init = new State(board);
			Stack<State> stack = new Stack<>();
			List<State> prefix = new ArrayList<>();
			int goalChecked = 0;
			int maxStackSize = Integer.MIN_VALUE;
			boolean found = false;//find to goal state
			// needed for Part E
			while (true) {		
				//initialize the stack and prefix
				stack.push(init);
				prefix.clear();
				prefix.add(init);
				while (!stack.isEmpty()) {
					//TO DO: perform iterative deepening; implement prefix list		
					
					//find the maxmium of stack size
					int stacksize = stack.size();
					if(stacksize>maxStackSize) {
						maxStackSize = stacksize;
					}

					State currentState = stack.pop();
					//option 2 or 3:print the pop of stack
					if(option ==2 || option == 3) {
						currentState.printState(option);
					}
					
					if(currentState.parentPt != null){
						int parentPosition = prefix.indexOf(currentState.parentPt);
						prefix.subList((parentPosition+1), prefix.size()).clear();
						prefix.add(currentState);
					}
					
					//option 4:print the prefix
					if(currentState.depth == cutoff && option == 4) {
						for(int i = 0; i < prefix.size(); i++) {
							prefix.get(i).printState(option);
						}
						break;
					}
					
					//count for goalCheck times and check whether the goal state is found
					goalChecked++;
					if(currentState.isGoalState()) {
						found = true;
						break;
					}
					
					//find all states for specific depth
					if((currentState.depth+1)<=cutoff) {
						State[] succ = currentState.getSuccessors();
						for(int i = 0; i < succ.length; i++) {
							boolean existed = false;
							for(int j = 0; j < prefix.size(); j++) {
								if(prefix.get(j).equals(succ[i])) {
									existed = true;
								}
							}
							if(!existed) {
								stack.push(succ[i]);
							}
						}
					}
					
				}
				
				if (option != 5)
					break;
				//TO DO: perform the necessary steps to start a new iteration
			        //       for Part E
				if(found) {
					//print prefix and values required for option 5
					for(int i = 0; i < prefix.size(); i++) {
						prefix.get(i).printState(option);
					}
					System.out.println("Goal-check "+goalChecked);
					System.out.println("Max-stack-size "+maxStackSize);
					break;
				}
				//if not found the goal state, go to next depth
				if(option == 5) {
					cutoff++;
				}
			}
		}
	}
}
