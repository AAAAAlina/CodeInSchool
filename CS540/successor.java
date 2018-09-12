import java.util.*;

public class successor {
    public static class JugState {
        int[] Capacity = new int[]{0,0,0};
        int[] Content = new int[]{0,0,0};
        public JugState(JugState copyFrom)
        {
            this.Capacity[0] = copyFrom.Capacity[0];
            this.Capacity[1] = copyFrom.Capacity[1];
            this.Capacity[2] = copyFrom.Capacity[2];
            this.Content[0] = copyFrom.Content[0];
            this.Content[1] = copyFrom.Content[1];
            this.Content[2] = copyFrom.Content[2];
        }
        public JugState()
        {
        }
        public JugState(int A,int B, int C)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
            this.Capacity[2] = C;
        }
        public JugState(int A,int B, int C, int a, int b, int c)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
            this.Capacity[2] = C;
            this.Content[0] = a;
            this.Content[1] = b;
            this.Content[2] = c;
        }
 
        public void printContent()
        {
            System.out.println(this.Content[0] + " " + this.Content[1] + " " + this.Content[2]);
        }
        
        /*
         * This method is to generate next steps by pouring one of the Jug to be empty.
         * 
         * @param successors: the arraylist for storing next steps of JugStates
         * @return successors: the arraylist for storing next steps of JugStates after pouring one of jugs
         */
        public ArrayList<JugState> pourJug(ArrayList<JugState> successors) {
        	for(int i = 0; i < 3; i++) {
        		if(this.Content[i] != 0) {
        			int temp = this.Content[i];
        			this.Content[i] = 0; //clear the jug
        			successors.add(new JugState(this.Capacity[0], this.Capacity[1], this.Capacity[2], this.Content[0], this.Content[1], this.Content[2]));
        			this.Content[i] = temp;
        		}
        	}
        	return successors;
        }
        
        /*
         * This method is to generate next steps by filling one of the Jug to be full.
         * 
         * @param successors: the arraylist for storing next steps of JugStates
         * @return successors: the arraylist for storing next steps of JugStates after filling one of jugs
         */
        public ArrayList<JugState> fillJug(ArrayList<JugState> successors) {
        	for(int i = 0; i < 3; i++) {
        		if(this.Content[i] != this.Capacity[i]) {
        			int temp = this.Content[i];
        			this.Content[i] = this.Capacity[i]; //fill the jug
        			successors.add(new JugState(this.Capacity[0], this.Capacity[1], this.Capacity[2], this.Content[0], this.Content[1], this.Content[2]));
        			this.Content[i] = temp;
        		}
        	}
        	return successors;
        }
        
        /*
         * This method is to generate next steps by pouring one of the Jug to another Jug.
         * 
         * @param successors: the arraylist for storing next steps of JugStates
         * @return successors: the arraylist for storing next steps of JugStates after pouring one of jugs to another one
         */
        public ArrayList<JugState> betweenJug(ArrayList<JugState> successors) {
        	for(int i = 0; i < 3; i++) {
        		//if the jug is full, no more action available
        		if(this.Content[i] != this.Capacity[i]) {
        			for(int j = 1; j < 3; j++) {
        				//if another jug is empty, no more action available
        				if(this.Content[(i+j)%3]!=0) {
        					int temp = this.Content[i];
        					int temp2 = this.Content[(i+j)%3];
        					//if jug i can be filled.
        					if((this.Capacity[i]-this.Content[i])<=this.Content[(i+j)%3]) {
        						this.Content[(i+j)%3] = this.Content[(i+j)%3] - (this.Capacity[i]-this.Content[i]);
        						this.Content[i] = this.Capacity[i];
        					}
        					//if jug i can not be filled by another jug(no enough water)
        					else {
        						this.Content[i] = this.Content[i] + this.Content[(i+j)%3];
        						this.Content[(i+j)%3] = 0;
        					}
        					successors.add(new JugState(this.Capacity[0], this.Capacity[1], this.Capacity[2], this.Content[0], this.Content[1], this.Content[2]));
        					this.Content[i] = temp;
        					this.Content[(i+j)%3] = temp2;
        				}
        			}
        		}
        	}
        	return successors; 
        }
 
        public ArrayList<JugState> getNextStates(){
            ArrayList<JugState> successors = new ArrayList<>();
            // TODO add all successors to the list
            successors = pourJug(successors);
            successors = fillJug(successors);
            successors = betweenJug(successors);
            return successors;
        }
    }

    public static void main(String[] args) {
        if( args.length != 6 )
        {
            System.out.println("Usage: java successor [A] [B] [C] [a] [b] [c]");
            return;
        }

        // parse command line arguments
        JugState a = new JugState();
        a.Capacity[0] = Integer.parseInt(args[0]);
        a.Capacity[1] = Integer.parseInt(args[1]);
        a.Capacity[2] = Integer.parseInt(args[2]);
        a.Content[0] = Integer.parseInt(args[3]);
        a.Content[1] = Integer.parseInt(args[4]);
        a.Content[2] = Integer.parseInt(args[5]);

        // Implement this function
        ArrayList<JugState> asist = a.getNextStates();

        // Print out generated successors
        for(int i=0;i< asist.size(); i++)
        {
            asist.get(i).printContent();
        }

        return;
    }
}

