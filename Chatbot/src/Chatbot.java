
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./WARC201709_wid.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            // count occurence of w
            for(int i = 0; i < corpus.size(); i++) {
            	if(w==corpus.get(i)) {
            		count++;
            	}
            }
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //generate random number
            double r = (double)n1/(double)n2;
            
            ArrayList<Double> interval = new ArrayList<Double>();
            //count occurence of w
            for(int i = 0; i < 4700; i++) {
            	int count = 0;
            	for(int j = 0; j < corpus.size(); j++) {
            		if(i == corpus.get(j)) {
            			count++;
            		}
            	}
            	//build the interval array
            	double prob = count/(double)corpus.size();
            	if(prob != 0) {
            		if(interval.size() == 0) {
            			interval.add(prob);
            		}
            		else {
            			interval.add(interval.get(i-1) + prob);
            		}
            	}
            }
            //find which interval that r located, and print the output
            for(int i = 0; i < interval.size(); i++) {
            	if(i == 0) {
            		if(r <= interval.get(i)) {
            			System.out.println(i);
                		System.out.println(String.format("%.7f",(double)0));
                		System.out.println(String.format("%.7f",interval.get(i)));
            		}
            	}
            	else {
            		if(r>interval.get(i-1) && r<=interval.get(i)) {
            			System.out.println(i);
            			System.out.println(String.format("%.7f",interval.get(i-1)));
            			System.out.println(String.format("%.7f",interval.get(i)));
            		}
            	}
            }

        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //count occurence of w and words after h
            for(int i = 0; i < corpus.size(); i++) {
            	if(h == corpus.get(i)) {
            		words_after_h.add(i);
            		if(w == corpus.get(i+1)) {
            			count++;
            		}
            	}
            }

            //output 
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //generate random number
            double r = (double)n1/(double)n2;
            ArrayList<Double> interval = new ArrayList<Double>();
            //count occurence of w and words after h
            for(int j = 0; j < 4700; j++) {
            	int count = 0;
            	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            	for(int i = 0; i < corpus.size(); i++) {
            		if(h == corpus.get(i)) {
            			words_after_h.add(i);
            			if(j == corpus.get(i+1)) {
            				count++;
            			}
            		}
            	}
            	//build the interval array
            	double prob = count/(double)words_after_h.size();
            	if(prob != 0) {
            		if(interval.size() == 0) {
            			interval.add(prob);
            		}
            		else {
            			interval.add(interval.get(interval.size()-1) + prob);
            		}
            	}
            	//find which interval that r located, and print the output
        		if(interval.size()==0) {
        		}
        		else if((interval.size()-1) == 0) {
                	if(r <= interval.get(interval.size()-1)) {
                		System.out.println(j);
                    	System.out.println(String.format("%.7f",(double)0));
                    	System.out.println(String.format("%.7f",interval.get(interval.size()-1)));
                    	break;
                	}
                }
                else {
                	if(r>interval.get(interval.size()-2) && r<=interval.get(interval.size()-1)) {
                		System.out.println(j);
                		System.out.println(String.format("%.7f",interval.get(interval.size()-2)));
                		System.out.println(String.format("%.7f",interval.get(interval.size()-1)));
                		break;
                	}
                }
            }
            

        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //count occurence of w and words after h1 and h2
            for(int i = 0; i < corpus.size(); i++) {
            	if(h1 == corpus.get(i)) {
            		if(h2 == corpus.get(i+1)) {
            			words_after_h1h2.add(i);
            			if(w == corpus.get(i+2)) {
            				count++;
            			}
            		}
            	}
            }
            
            //output 
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //generate random number
            double r = (double)n1/(double)n2;
            ArrayList<Double> interval = new ArrayList<Double>();
            //count occurence of w and words after h1 and h2
            for(int j = 0; j < 4700; j++) {
            	int count = 0;
            	ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            	for(int i = 0; i < corpus.size(); i++) {
                	if(h1 == corpus.get(i)) {
                		if(h2 == corpus.get(i+1)) {
                			words_after_h1h2.add(i);
                			if(j == corpus.get(i+2)) {
                				count++;
                			}
                		}
                	}
                }
            	//build the interval array
            	if(words_after_h1h2.size() == 0) {
            		System.out.println("undefined");
            		break;
            	}
            	else {
            		double prob = count/(double)words_after_h1h2.size();
            		if(prob != 0) {
            			if(interval.size() == 0) {
            				interval.add(prob);
            			}
            			else {
            				interval.add(interval.get(interval.size()-1) + prob);
            			}
            		}
            		//find which interval that r located, and print the output
            		if(interval.size()==0) {
            		}
            		else if((interval.size()-1) == 0) {
            			if(r <= interval.get(interval.size()-1)) {
            				System.out.println(j);
            				System.out.println(String.format("%.7f",(double)0));
            				System.out.println(String.format("%.7f",interval.get(interval.size()-1)));
            				break;
            			}
            		}
            		else {
            			if(r>interval.get(interval.size()-2) && r<=interval.get(interval.size()-1)) {
            				System.out.println(j);
            				System.out.println(String.format("%.7f",interval.get(interval.size()-2)));
            				System.out.println(String.format("%.7f",interval.get(interval.size()-1)));
            				break;
            			}
            		}
            	}
            }
            

        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                ArrayList<Double> interval = new ArrayList<Double>();
                for(int i = 0; i < 4700; i++) {
                	int count = 0;
                	for(int j = 0; j < corpus.size(); j++) {
                		if(i == corpus.get(j)) {
                			count++;
                		}
                	}
                	double prob = count/(double)corpus.size();
                	if(prob != 0) {
                		if(interval.size() == 0) {
                			interval.add(prob);
                		}
                		else {
                			interval.add(interval.get(i-1) + prob);
                		}
                	}
                }
                for(int i = 0; i < interval.size(); i++) {
                	if(i == 0) {
                		if(r <= interval.get(i)) {
                			h1 = i;
                		}
                	}
                	else {
                		if(r>interval.get(i-1) && r<=interval.get(i)) {
                			h1 = i;
                		}
                	}
                }
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                // Generate second word using r
                ArrayList<Double> interval2 = new ArrayList<Double>();
                r = rng.nextDouble();
                for(int j = 0; j < 4700; j++) {
                	int count = 0;
                	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
                	for(int i = 0; i < corpus.size(); i++) {
                		if(h1 == corpus.get(i)) {
                			words_after_h.add(i);
                			if(j == corpus.get(i+1)) {
                				count++;
                			}
                		}
                	}
                	double prob = count/(double)words_after_h.size();
                	if(prob != 0) {
                		if(interval2.size() == 0) {
                			interval2.add(prob);
                		}
                		else {
                			interval2.add(interval2.get(interval2.size()-1) + prob);
                		}
                	}
            		if(interval2.size()==0) {
            		}
            		else if((interval2.size()-1) == 0) {
                    	if(r <= interval2.get(interval2.size()-1)) {
                    		h2 = j;
                    		break;
                    	}
                    }
                    else {
                    	if(r>interval2.get(interval2.size()-2) && r<=interval2.get(interval2.size()-1)) {
                    		h2 = j;
                    		break;
                    	}
                    }
                }
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                // Generate second word using r
                double r = rng.nextDouble();
                ArrayList<Double> interval2 = new ArrayList<Double>();
                for(int j = 0; j < 4700; j++) {
                	int count = 0;
                	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
                	for(int i = 0; i < corpus.size(); i++) {
                		if(h1 == corpus.get(i)) {
                			words_after_h.add(i);
                			if(j == corpus.get(i+1)) {
                				count++;
                			}
                		}
                	}
                	double prob = count/(double)words_after_h.size();
                	if(prob != 0) {
                		if(interval2.size() == 0) {
                			interval2.add(prob);
                		}
                		else {
                			interval2.add(interval2.get(interval2.size()-1) + prob);
                		}
                	}
            		if(interval2.size()==0) {
            		}
            		else if((interval2.size()-1) == 0) {
                    	if(r <= interval2.get(interval2.size()-1)) {
                    		h2 = j;
                    		break;
                    	}
                    }
                    else {
                    	if(r>interval2.get(interval2.size()-2) && r<=interval2.get(interval2.size()-1)) {
                    		h2 = j;
                    		break;
                    	}
                    }
                }
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // Generate new word using h1,h2
                ArrayList<Double> interval = new ArrayList<Double>();
                
                for(int j = 0; j < 4700; j++) {
                	int count = 0;
                	ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
                	for(int i = 0; i < corpus.size(); i++) {
                    	if(h1 == corpus.get(i)) {
                    		if(h2 == corpus.get(i+1)) {
                    			words_after_h1h2.add(i);
                    			if(j == corpus.get(i+2)) {
                    				count++;
                    			}
                    		}
                    	}
                    }
                	//the condition that there is not h1 h2 order words
                	if(words_after_h1h2.size() == 0) {
                		ArrayList<Double> interval3 = new ArrayList<Double>();
                		for(int k = 0; k < 4700; k++) {
                        	int count2 = 0;
                        	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
                        	for(int i = 0; i < corpus.size(); i++) {
                        		if(h2 == corpus.get(i)) {
                        			words_after_h.add(i);
                        			if(k == corpus.get(i+1)) {
                        				count2++;
                        			}
                        		}
                        	}
                        	double prob = count2/(double)words_after_h.size();
                        	if(prob != 0) {
                        		if(interval3.size() == 0) {
                        			interval3.add(prob);
                        		}
                        		else {
                        			interval3.add(interval3.get(interval3.size()-1) + prob);
                        		}
                        	}
                    		if(interval3.size()==0) {
                    		}
                    		else if((interval3.size()-1) == 0) {
                            	if(r <= interval3.get(interval3.size()-1)) {
                            		h2 = k;
                            		break;
                            	}
                            }
                            else {
                            	if(r>interval3.get(interval3.size()-2) && r<=interval3.get(interval3.size()-1)) {
                            		h2 = k;
                            		break;
                            	}
                            }
                        }
                		break;
                	}
                	else {
                		double prob = count/(double)words_after_h1h2.size();
                		if(prob != 0) {
                			if(interval.size() == 0) {
                				interval.add(prob);
                			}
                			else {
                				interval.add(interval.get(interval.size()-1) + prob);
                			}
                		}
                		if(interval.size()==0) {
                		}
                		else if((interval.size()-1) == 0) {
                			if(r <= interval.get(interval.size()-1)) {
                				w = j;
                				break;
                			}
                		}
                		else {
                			if(r>interval.get(interval.size()-2) && r<=interval.get(interval.size()-1)) {
                				w = j;
                				break;
                			}
                		}
                	}
                }
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
