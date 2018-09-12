import java.util.*;
import java.io.*;

public class Neural{
	
	//declare necessary variables
	private static double [] w = new double [10];
	private static double x1;
	private static double x2;
	private static double y;
	private static double eta;
	private static double [] EdivW = new double [10];
	
	//define necessary functions
	public static double UA() {
		return w[1]+w[2]*x1+w[3]*x2;
	}
	
	public static double UB() {
		return w[4]+w[5]*x1+w[6]*x2;
	}
	
	public static double VA() {
		return Math.max(UA(), 0);
	}
	
	public static double VB() {
		return Math.max(UB(), 0);
	}
	
	public static double UC() {
		return w[7]+w[8]*VA()+w[9]*VB();
	}
	
	public static double VC() {
		return 1/(1+Math.exp(-UC()));
	}
	
	public static double E() {
		return Math.pow((VC()-y), 2)/2;
	}
	
	public static double EdivVC() {
		return (VC()-y);
	}
	
	public static double EdivUC() {
		return EdivVC()*VC()*(1-VC());
	}
	
	public static double EdivVA() {
		return w[8]*EdivUC();
	}
	
	public static double EdivVB() {
		return w[9]*EdivUC();
	}
	
	public static double EdivUA() {
		if(UA()>=0) {
			return EdivVA();
		}
		else {
			return 0;
		}
	}
	
	public static double EdivUB() {
		if(UB()>=0) {
			return EdivVB();
		}
		else {
			return 0;
		}
	}
	
	public static void getEdivW() {
		EdivW[1] = 1*EdivUA();
		EdivW[2] = x1*EdivUA();
		EdivW[3] = x2*EdivUA();
		EdivW[4] = EdivUB();
		EdivW[5] = x1*EdivUB();
		EdivW[6] = x2*EdivUB();
		EdivW[7] = EdivUC();
		EdivW[8] = VA()*EdivUC();
		EdivW[9] = VB()*EdivUC();
	}
	
	public static void updateW() {
		getEdivW();
		for(int i = 1; i<10; i++) {
			w[i] = w[i] - eta*EdivW[i];
		}
	}
	
	public static double evaluationSetError() {
		double error = 0;
		//read file and calculate evaluation set errors
		try {
    		File f2 = new File("hw2_midterm_A_eval.txt");
    		Scanner scan2 =new Scanner(f2);
    		while(scan2.hasNextLine() && scan2.hasNextDouble()) {
    			x1 = scan2.nextDouble();
    			x2 = scan2.nextDouble();
    			y = scan2.nextDouble();
    			error = error + Math.pow((VC()-y), 2)/2;
    		}
    		scan2.close();

    	}
    	catch(FileNotFoundException ex) {
            System.out.println("Unable to open the file");                
        }
		return error;
	}
	
	public static void main(String[] args) {
        if( args.length < 1)
        {
            System.out.println("Usage: java Neural FLAG [args]");
            return;
        }

        int flag = Integer.valueOf(args[0]);
        if(flag == 100) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	x1 = Double.valueOf(args[10]);
        	x2 = Double.valueOf(args[11]);
        	
        	System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f",UA(), VA(), UB(), VB(), UC(), VC()));
        }
        
        else if(flag == 200) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	x1 = Double.valueOf(args[10]);
        	x2 = Double.valueOf(args[11]);
        	y = Double.valueOf(args[12]);
        	
        	System.out.println(String.format("%.5f %.5f %.5f",E(), EdivVC(), EdivUC()));
        }
        
        else if(flag == 300) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	x1 = Double.valueOf(args[10]);
        	x2 = Double.valueOf(args[11]);
        	y = Double.valueOf(args[12]);
        	
        	System.out.println(String.format("%.5f %.5f %.5f %.5f", EdivVA(), EdivUA(), EdivVB(), EdivUB()));
        }
        
        else if(flag == 400) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	x1 = Double.valueOf(args[10]);
        	x2 = Double.valueOf(args[11]);
        	y = Double.valueOf(args[12]);
        	
        	getEdivW(); // load the value of w into the variable EdivW
        	System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", EdivW[1], EdivW[2], EdivW[3], EdivW[4], EdivW[5], EdivW[6], EdivW[7], EdivW[8], EdivW[9]));
        }
        
        else if(flag == 500) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	x1 = Double.valueOf(args[10]);
        	x2 = Double.valueOf(args[11]);
        	y = Double.valueOf(args[12]);
        	eta = Double.valueOf(args[13]);
        	
        	System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8], w[9]));
        	System.out.println(String.format("%.5f",E()));
        	updateW(); // update the new w into array w.
        	System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8], w[9]));
        	System.out.println(String.format("%.5f",E()));
        	
        }
        
        else if(flag == 600) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	eta = Double.valueOf(args[10]);
        	
        	//read file and calculate evaluation set errors.
        	try {
        		File f = new File("hw2_midterm_A_train.txt");
        		Scanner scan =new Scanner(f);
        		while(scan.hasNextLine() && scan.hasNextDouble()) {
        			x1 = scan.nextDouble();
        			x2 = scan.nextDouble();
        			y = scan.nextDouble();
        			updateW();
        			System.out.println(String.format("%.5f %.5f %.5f",x1, x2, y));
        			System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8], w[9]));
                	System.out.println(String.format("%.5f",evaluationSetError()));
        		}
        		scan.close();

        	}
        	catch(FileNotFoundException ex) {
                System.out.println("Unable to open the file");                
            } 	
        }
        
        else if(flag == 700) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	eta = Double.valueOf(args[10]);
        	int T = Integer.valueOf(args[11]);
        	//check the times of epochs
        	for(int i = 0; i<T; i++) {
        		try {
        			File f = new File("hw2_midterm_A_train.txt");
        			Scanner scan =new Scanner(f);
        			while(scan.hasNextLine() && scan.hasNextDouble()) {
        				x1 = scan.nextDouble();
        				x2 = scan.nextDouble();
        				y = scan.nextDouble();
        				updateW();
        			}
        			System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8], w[9]));
        			System.out.println(String.format("%.5f",evaluationSetError()));
        			scan.close();
        		}
        		catch(FileNotFoundException ex) {
        			System.out.println("Unable to open the file");                
        		}
        	}
        }
        
        else if(flag == 800) {
        	for(int i = 1; i < 10; i++) {
        		w[i]=Double.valueOf(args[i]);
        	}
        	eta = Double.valueOf(args[10]);
        	int T = Integer.valueOf(args[11]);
        	double evaluationSetError = 100000000;
        	int count = 0;
        	
        	for(int i = 0; i<T; i++) {
        		count++;
        		try {
        			File f = new File("hw2_midterm_A_train.txt");
        			Scanner scan =new Scanner(f);
        			while(scan.hasNextLine() && scan.hasNextDouble()) {
        				x1 = scan.nextDouble();
        				x2 = scan.nextDouble();
        				y = scan.nextDouble();
        				updateW();
        			}
        			//check whether the error is increasing.
        			if(evaluationSetError >= evaluationSetError()) {
        				evaluationSetError = evaluationSetError();
        			}
        			else {
        				System.out.println(count);
        				System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f %.5f", w[1], w[2], w[3], w[4], w[5], w[6], w[7], w[8], w[9]));
        				System.out.println(String.format("%.5f",evaluationSetError()));
        				break;
        			}
        			scan.close();
        		}
        		catch(FileNotFoundException ex) {
        			System.out.println("Unable to open the file");                
        		}
        	}
        	
        	//calculate the test set classification accuracy
        	int correctPredict = 0;
        	int samplesize = 0;
        	try {
    			File f3 = new File("hw2_midterm_A_test.txt");
    			Scanner scan3 =new Scanner(f3);
    			while(scan3.hasNextLine() && scan3.hasNextDouble()) {
    				samplesize++;
    				x1 = scan3.nextDouble();
    				x2 = scan3.nextDouble();
    				y = scan3.nextDouble();
    				if(VC()>0.5) {
    					if(y == 1) {
    						correctPredict++;
    					}
    				}
    				else {
    					if(y == 0) {
    						correctPredict++;
    					}
    				}
    			}
    			System.out.println(String.format("%.5f",(double)correctPredict/samplesize));
    			scan3.close();
    		}
    		catch(FileNotFoundException ex) {
    			System.out.println("Unable to open the file");                
    		}
        	
        }
        return;
    }
}