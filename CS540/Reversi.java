import java.util.*;

class State {
    char[] board;

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
    }

    public int getScore() {

        // TO DO: return game theoretic value of the board
    	
    	//initial scores
    	int dark = 0;
    	int light = 0;
    	int score = 0;
    	
    	//count scores for dark and light
    	for(int i = 0; i < this.board.length; i++) {
    		if(this.board[i] == '1') {
    			dark++;
    		}
    		else if(this.board[i] == '2') {
    			light++;
    		}
    	}

    	if(dark>light) score = 1;
    	if(dark<light) score = -1;
        return score;
    }
    
    public boolean isTerminal() {
    	
        // TO DO: determine if the board is a terminal node or not and return boolean
    	
    	boolean is_terminal = true;
    	
    	//check successors for player 1
    	State [] successors = getSuccessors('1');
		if(successors.length>0) {
			is_terminal = false;
		}
			
		//check successors for player 2
		State [] othersuccessors = getSuccessors('2');
		if(othersuccessors.length>0) {
			is_terminal = false;
		}

        return is_terminal;
    }

    public State[] getSuccessors(char player) {

        // TO DO: get all successors and return them in proper order
    	
    	//initialize array with enough space
    	ArrayList<State> successors = new ArrayList<State>();
    	
    	//check every disk is legal to put or not
    	for(int i = 0; i < this.board.length; i++) {
    		if(this.board[i] == '0') {
    			char[] newboard = Arrays.copyOf(this.board, this.board.length);
    			
    			//check the 8 directions' situations for every disk
    			if((i-5)>=0 && (i-5)!=3 && (i-5)!=7) {
    				if(this.board[i-5] != player && this.board[i-5] != '0') {
    					if((i-5*2)>=0 && (i-5*2)!=3 && (i-5*2)!=7) {
    						
    						if(this.board[i-5*2] == player) {
    							newboard[i] = player;
    							newboard[i-5] = player;
    						}
    						
    						else if(this.board[i-5*2] != '0') {
    							if((i-5*3)>=0 && this.board[i-5*3] == player) {
        							newboard[i] = player;
        							newboard[i-5] = player;
        							newboard[i-5*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i-4)>=0) {
    				if(this.board[i-4] != player && this.board[i-4] != '0') {
    					if((i-4*2)>=0) {
    						
    						if(this.board[i-4*2] == player) {
    							newboard[i] = player;
    							newboard[i-4] = player;
    						}
    						
    						else if(this.board[i-4*2] != '0') {
    							if((i-4*3)>=0 && this.board[i-4*3] == player) {
        							newboard[i] = player;
        							newboard[i-4] = player;
        							newboard[i-4*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i-4)>=0 && (i-3)!=4 && (i-3)!=8 && (i-3)!=12) {
    				if(this.board[i-3] != player && this.board[i-3] != '0') {
    					if((i-3*2)>=0 && (i-3*2)!=0 && (i-3*2)!=4 && (i-3*2)!=8) {
    						
    						if(this.board[i-3*2] == player) {
    							newboard[i] = player;
    							newboard[i-3] = player;
    						}
    						
    						else if(this.board[i-3*2] != '0') {
    							if((i-3*3)>0 && (i-3*3)!=4 && this.board[i-3*3] == player) {
        							newboard[i] = player;
        							newboard[i-3] = player;
        							newboard[i-3*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i-1)>=0 && (i-1)!=3 && (i-1)!=7 && (i-1)!=11) {
    				if(this.board[i-1] != player && this.board[i-1] != '0') {
    					if((i-1*2)>=0 && (i-1*2)!=3 && (i-1*2)!=7 && (i-1*2)!=11) {
    						
    						if(this.board[i-1*2] == player) {
    							newboard[i] = player;
    							newboard[i-1] = player;
    						}
    						
    						else if(this.board[i-1*2] != '0') {
    							if((i-1*3)>=0 && (i-1*3)!=3 && (i-1*3)!=7 && (i-1*3)!=11 && this.board[i-1*3] == player) {
        							newboard[i] = player;
        							newboard[i-1] = player;
        							newboard[i-1*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i+1)<=15 && (i+1)!=4 && (i+1)!=8 && (i+1)!=12) {
    				if(this.board[i+1] != player && this.board[i+1] != '0') {
    					if((i+1*2)<=15 && (i+1*2)!=4 && (i+1*2)!=8 && (i+1*2)!=12) {
    						
    						if(this.board[i+1*2] == player) {
    							newboard[i] = player;
    							newboard[i+1] = player;
    						}
    						
    						else if(this.board[i+1*2] != '0') {
    							if((i+1*3)<=15 && (i+1*3)!=4 && (i+1*3)!=8 && (i+1*3)!=12 && this.board[i+1*3] == player) {
        							newboard[i] = player;
        							newboard[i+1] = player;
        							newboard[i+1*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i+3)<15 && (i+3)!=3 && (i+3)!=7 && (i+3)!=11) {
    				if(this.board[i+3] != player && this.board[i+3] != '0') {
    					if((i+3*2)<15 && (i+3*2)!=7 && (i+3*2)!=11) {
    						
    						if(this.board[i+3*2] == player) {
    							newboard[i] = player;
    							newboard[i+3] = player;
    						}
    						
    						else if(this.board[i+3*2] != '0') {
    							if((i+3*3)<15 && (i+3*3)!=11 && this.board[i+3*3] == player) {
        							newboard[i] = player;
        							newboard[i+3] = player;
        							newboard[i+3*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i+4)<=15) {
    				if(this.board[i+4] != player && this.board[i+4] != '0') {
    					if((i+4*2)<=15) {
    						
    						if(this.board[i+4*2] == player) {
    							newboard[i] = player;
    							newboard[i+4] = player;
    						}
    						
    						else if(this.board[i+4*2] != '0') {
    							if((i+4*3)<=15 && this.board[i+4*3] == player) {
        							newboard[i] = player;
        							newboard[i+4] = player;
        							newboard[i+4*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			if((i+5)<=15 && (i+5)!=8 && (i+5)!=12) {
    				if(this.board[i+5] != player && this.board[i+5] != '0') {
    					if((i+5*2)<=15 && (i+5*2)!=12) {
    						
    						if(this.board[i+5*2] == player) {
    							newboard[i] = player;
    							newboard[i+5] = player;
    						}
    						
    						else if(this.board[i+5*2] != '0') {
    							if((i+5*3)<=15 && this.board[i+5*3] == player) {
        							newboard[i] = player;
        							newboard[i+5] = player;
        							newboard[i+5*2] = player;
    							}
    						}
    					}
    				}
    			}
    			
    			//if the disk is legal, add a new successor
    			State newstate = new State(newboard);
    			if(!newstate.equals(this)) {
    				successors.add(newstate);
    			}
    			
    		}
    	}
    	State[] successors_result = new State[successors.size()];
    	successors_result = successors.toArray(successors_result);
        return successors_result;
    }
 
    public void printState(int option, char player) {

        // TO DO: print a State based on option (flag)
    	if(option == 1) {
    		boolean printself = true;
    		State [] successors = getSuccessors(player);
    		for(int i = 0; i < successors.length; i++) {
    			System.out.println(successors[i].getBoard());
    			printself = false;
    		}
    		
    		//if there is no successor for the player but the state is not terminal, print itself
    		if(printself && !isTerminal()) {
    			System.out.println(this.getBoard());
    		}
    	}
    	
    	else if(option == 2) {
    		if(isTerminal()) {
    			System.out.println(getScore());
    		}
    		else {
    			System.out.println("non-terminal");
    		}
    	}
    	
    	else if(option == 3) {
    		System.out.println(Minimax.run(this, player));
    		System.out.println(Minimax.countstate);
    	}
    	
    	else if(option == 4) {
    		int optimal = 0;
    		boolean nosuccessors = true;
    		
    		//if the current state is terminal, no output
    		if(this.isTerminal()) {
    			return;
    		}
    		
    		//if player is 1, find the Minimax from the Max value
    		State[] successors = getSuccessors(player);
    		if(player == '1') {
    			for(int i = 0; i < successors.length; i++) {
    				nosuccessors = false;
    				if(Minimax.run(successors[i], '2')>Minimax.run(successors[optimal], '2')) {
    					optimal = i;
    				}
    			}
    		}
    		
    		//if player is 2, find the Minimax from the Min value
    		else {
    			for(int i = 0; i < successors.length; i++) {
    				nosuccessors = false;
    				if(Minimax.run(successors[i], '1')<Minimax.run(successors[optimal], '1')) {
    					optimal = i;
    				}
    			}
    		}
    		
    		//if there is no successor for the player but the state is not terminal, print itself
    		if(nosuccessors) {
    			System.out.println(this.getBoard());
    			return;
    		}
    		System.out.println(successors[optimal].getBoard());
    	}  	
    	
    	else if(option == 5) {
    		System.out.println(Minimax.run_with_pruning(this, player));
    		System.out.println(Minimax.countstate_pruning);
    	}
    	
    	else if(option == 6) {
    		int optimal = 0;
    		boolean nosuccessors = true;
    		if(this.isTerminal()) {
    			return;
    		}
    		State[] successors = getSuccessors(player);
    		if(player == '1') {
    			for(int i = 0; i < successors.length; i++) {
    				nosuccessors = false;
    				if(Minimax.run_with_pruning(successors[i], '2')>Minimax.run_with_pruning(successors[optimal], '2')) {
    					optimal = i;
    				}
    			}
    		}
    		else {
    			for(int i = 0; i < successors.length; i++) {
    				nosuccessors = false;
    				if(Minimax.run_with_pruning(successors[i], '1')<Minimax.run_with_pruning(successors[optimal], '1')) {
    					optimal = i;
    				}
    			}
    		}
    		
    		if(nosuccessors) {
    			System.out.println(this.getBoard());
    			return;
    		}
    		
    		System.out.println(successors[optimal].getBoard());
    	}
    	
    }

    public String getBoard() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(this.board[i]);
        }
        return builder.toString().trim();
    }

    public boolean equals(State src) {
        for (int i = 0; i < 16; i++) {
            if (this.board[i] != src.board[i])
                return false;
        }
        return true;
    }
}

class Minimax {
	
	public static int countstate = 0;
	public static int countstate_pruning = 0;
	
	private static int max_value(State curr_state) {
		
        // TO DO: implement Max-Value of the Minimax algorithm
		countstate++;
		//set alpha as negative infinite
		int alpha = -2147483648;
		boolean nosuccessors = true;
		if(curr_state.isTerminal()) {
			return curr_state.getScore();
		}
		else {
			State[] successors = curr_state.getSuccessors('1');

			for(int i = 0; i < successors.length; i++) {
				alpha = Math.max(alpha, min_value(successors[i]));
				nosuccessors = false;
			}
		}
		if(nosuccessors) alpha = min_value(curr_state);
		return alpha;
	}
	
	private static int min_value(State curr_state) {
		
        // TO DO: implement Min-Value of the Minimax algorithm
		countstate++;
		//set beta as infinite
		int beta = 2147483647;
		boolean nosuccessors = true;
		if(curr_state.isTerminal()) {
			return curr_state.getScore();
		}
		else {
			State[] successors = curr_state.getSuccessors('2');
			for(int i = 0; i < successors.length; i++) {
				beta = Math.min(beta, max_value(successors[i]));
				nosuccessors = false;
			}
		}
		if(nosuccessors) beta = max_value(curr_state);
		return beta;

	}
	
	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        // TO DO: implement Max-Value of the alpha-beta pruning algorithm
		countstate_pruning++;
		boolean nosuccessors = true;
		if(curr_state.isTerminal()) return curr_state.getScore();
		else {
			State[] successors = curr_state.getSuccessors('1');
			for(int i = 0; i < successors.length; i++) {
				nosuccessors = false;
				alpha = Math.max(alpha, min_value_with_pruning(successors[i], alpha, beta));
				if(alpha>=beta) return beta;
			}
		}
		if(nosuccessors) alpha = min_value_with_pruning(curr_state, alpha, beta);
		return alpha;
	}
	
	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        // TO DO: implement Min-Value of the alpha-beta pruning algorithm
		countstate_pruning++;
		boolean nosuccessors = true;
		if(curr_state.isTerminal()) return curr_state.getScore();
		else {
			State[] successors = curr_state.getSuccessors('2');
			for(int i = 0; i < successors.length; i++) {
				nosuccessors = false;
				beta = Math.min(beta, max_value_with_pruning(successors[i], alpha, beta));
				if(alpha>=beta) return alpha;
			}
		}
		if(nosuccessors) beta = max_value_with_pruning(curr_state, alpha, beta);
		return beta;
	}
	
	public static int run(State curr_state, char player) {

        // TO DO: run the Minimax algorithm and return the game theoretic value
		int result;
		if(player == '1') {
			result = max_value(curr_state);
		}
		else {
			result = min_value(curr_state);
		}
		return result;
	}
	
	public static int run_with_pruning(State curr_state, char player) {
	    
        // TO DO: run the alpha-beta pruning algorithm and return the game theoretic value
		int result;
		if(player == '1') {
			result = max_value_with_pruning(curr_state, -2147483648, 2147483647);
		}
		else {
			result = min_value_with_pruning(curr_state, -2147483648, 2147483647);
		}
		return result;
	}
}

public class Reversi {
    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }
        int flag = Integer.valueOf(args[0]);
        char[] board = new char[16];
        for (int i = 0; i < 16; i++) {
            board[i] = args[2].charAt(i);
        }
        int option = flag / 100;
        char player = args[1].charAt(0);
        if ((player != '1' && player != '2') || args[1].length() != 1) {
            System.out.println("Invalid Player Input");
            return;
        }
        State init = new State(board);
        init.printState(option, player);
    }
}
