
import java.util.*;
import java.io.*;

public class Ice{
	private static double[] x = {
		1855,1856,1857,1858,1859,1860,1861,1862,1863,1864,1865,1866,
		1867,1868,1869,1870,1871,1872,1873,1874,1875,1876,1877,1878,
		1879,1880,1881,1882,1883,1884,1885,1886,1887,1888,1889,1890,
		1891,1892,1893,1894,1895,1896,1897,1898,1899,1900,1901,1902,
		1903,1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,
		1915,1916,1917,1918,1919,1920,1921,1922,1923,1924,1925,1926,
		1927,1928,1929,1930,1931,1932,1933,1934,1935,1936,1937,1938,
		1939,1940,1941,1942,1943,1944,1945,1946,1947,1948,1949,1950,
		1951,1952,1953,1954,1955,1956,1957,1958,1959,1960,1961,1962,
		1963,1964,1965,1966,1967,1968,1969,1970,1971,1972,1973,1974,
		1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986,
		1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,
		1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,
		2011,2012,2013,2014,2015,2016
	};
	
	private static int [] y = {
		118,151,121,96,110,117,132,104,125,118,125,123,110,127,131,
		99,126,144,136,126,91,130,62,112,99,161,78,124,119,124,128,
		131,113,88,75,111,97,112,101,101,91,110,100,130,111,107,105,
		89,126,108,97,94,83,106,98,101,108,99,88,115,102,116,115,82,
		110,81,96,125,104,105,124,103,106,96,107,98,65,115,91,94,101,
		121,105,97,105,96,82,116,114,92,98,101,104,96,109,122,114,81,
		85,92,114,111,95,126,105,108,117,112,113,120,65,98,91,108,113,
		110,105,97,105,107,88,115,123,118,99,93,96,54,111,85,107,89,87,
		97,93,88,99,108,94,74,119,102,47,82,53,115,21,89,80,101,95,66,
		106,97,87,109,57,87,117,91,62,65
	};
	
	public static int getNumber() {
		return x.length;
	}
	
	public static double getMean() {
		double total = 0;
		for(int i = 0; i < y.length; i++) {
			total = total + y[i];
		}
		return total/y.length;
	}
	
	public static double getSD() {
		double mean = getMean();
		double total = 0;
		for(int i = 0; i < y.length; i++) {
			total = total + Math.pow((y[i]-mean), 2);
		}
		return Math.sqrt(total/(y.length-1));
	}
	
	public static double[] normalizeX() {
		double meanX;
    	double total = 0;
		for(int i = 0; i < x.length; i++) {
			total = total + x[i];
		}
		meanX = total/x.length;
		
		double stdX;
		double total2 = 0;
		for(int i = 0; i < x.length; i++) {
			total2 = total2 + Math.pow((x[i]-meanX), 2);
		}
		stdX = Math.sqrt(total2/(x.length-1));
		
		double [] normalizeX = new double[x.length];
		for(int i = 0; i < x.length; i++) {
			normalizeX[i] = (x[i]-meanX)/stdX;
		}
		
		return normalizeX;
	}
	
	public static double [] linearRegression(double beta0, double beta1) {
		double [] function = new double[getNumber()];
		for(int i = 0; i < function.length; i++) {
			function[i] = beta0 + beta1*x[i];
		}
		return function;
	}
	
	public static double MSE(double beta0, double beta1) {
		double [] function = linearRegression(beta0, beta1);
		double total = 0;
		for(int i = 0; i < function.length; i++) {
			total = total + Math.pow((function[i]-y[i]), 2);
		}
		return total/getNumber();
	}
	
	public static double gradientDescentBeta0(double beta0, double beta1) {
		double [] function = linearRegression(beta0, beta1);
		double total = 0;
		for(int i = 0; i < function.length; i++) {
			total = total + (function[i]-y[i]);
		}
		return total*2/getNumber();
	}
	
	public static double gradientDescentBeta1(double beta0, double beta1) {
		double [] function = linearRegression(beta0, beta1);
		double total = 0;
		for(int i = 0; i < function.length; i++) {
			total = total + (function[i]-y[i])*x[i];
		}
		return total*2/getNumber();
	}
	
	public static double iterationBeta0(double eta, double beta0, double beta1) {
		return beta0 - eta*gradientDescentBeta0(beta0, beta1);
	}
	
	public static double iterationBeta1(double eta, double beta0, double beta1) {
		return beta1 - eta*gradientDescentBeta1(beta0, beta1);
	}
	
    public static void main(String[] args) {
        if( args.length != 1 && args.length != 2 && args.length != 3)
        {
            System.out.println("Usage: java Ice FLAG [arg1 arg2]");
            return;
        }

        int flag = Integer.valueOf(args[0]);

        if(flag == 100) {
        	for(int i = 0; i < x.length; i++) {
        		System.out.println((int)x[i]+" "+y[i]);
        	}
        }
        
        else if(flag == 200) {
        	System.out.println(getNumber());
        	System.out.println(String.format("%.2f",getMean()));
        	System.out.println(String.format("%.2f",getSD()));
        }
        
        else if(flag == 300) {
        	double beta0 = Double.valueOf(args[1]);
        	double beta1 = Double.valueOf(args[2]);
        	System.out.println(String.format("%.2f",MSE(beta0, beta1)));
        }
        
        else if(flag == 400) {
        	double beta0 = Double.valueOf(args[1]);
        	double beta1 = Double.valueOf(args[2]);
        	System.out.println(String.format("%.2f",gradientDescentBeta0(beta0, beta1)));
        	System.out.println(String.format("%.2f",gradientDescentBeta1(beta0, beta1)));
        }
        
        else if(flag == 500) {
        	double eta = Double.valueOf(args[1]);
        	int T = Integer.valueOf(args[2]);
        	double beta0 = 0;
        	double beta1 = 0;
        	for(int i = 0; i<T; i++) {
        		double newbeta0 = iterationBeta0(eta, beta0, beta1);
        		double newbeta1 = iterationBeta1(eta, beta0, beta1);
        		beta0 = newbeta0;
        		beta1 = newbeta1;
        		System.out.print(i+1);
        		System.out.print(" ");
        		System.out.print(String.format("%.2f",beta0));
        		System.out.print(" ");
            	System.out.print(String.format("%.2f",beta1));
            	System.out.print(" ");
            	System.out.println(String.format("%.2f",MSE(beta0, beta1)));
        	}
        }
        
        else if(flag == 600) {
        	double meanY = getMean();
        	double meanX;
        	double total = 0;
    		for(int i = 0; i < x.length; i++) {
    			total = total + x[i];
    		}
    		meanX = total/x.length;
    		
        	double beta1;
        	double total2 = 0;
        	double total3 = 0;
        	for(int i = 0; i < getNumber(); i++) {
        		total2 = total2 + (x[i]-meanX)*(y[i]-meanY);
        		total3 = total3 + Math.pow((x[i]-meanX), 2);
        	}
        	beta1 = total2/total3;
        	
        	double beta0 = meanY - beta1*meanX;
        	
        	System.out.print(String.format("%.2f",beta0));
    		System.out.print(" ");
        	System.out.print(String.format("%.2f",beta1));
        	System.out.print(" ");
        	System.out.println(String.format("%.2f",MSE(beta0, beta1)));
        }
        
        else if(flag == 700) {
        	int year = Integer.valueOf(args[1]);
        	double meanY = getMean();
        	double meanX;
        	double total = 0;
    		for(int i = 0; i < x.length; i++) {
    			total = total + x[i];
    		}
    		meanX = total/x.length;
    		
        	double beta1;
        	double total2 = 0;
        	double total3 = 0;
        	for(int i = 0; i < getNumber(); i++) {
        		total2 = total2 + (x[i]-meanX)*(y[i]-meanY);
        		total3 = total3 + Math.pow((x[i]-meanX), 2);
        	}
        	beta1 = total2/total3;
        	
        	double beta0 = meanY - beta1*meanX;
        	System.out.println(String.format("%.2f",(beta0 + beta1*year)));
        }
        
        else if(flag == 800) {
        	x = normalizeX();
        	double eta = Double.valueOf(args[1]);
        	int T = Integer.valueOf(args[2]);
        	double beta0 = 0;
        	double beta1 = 0;
        	for(int i = 0; i<T; i++) {
        		double newbeta0 = iterationBeta0(eta, beta0, beta1);
        		double newbeta1 = iterationBeta1(eta, beta0, beta1);
        		beta0 = newbeta0;
        		beta1 = newbeta1;
        		System.out.print(i+1);
        		System.out.print(" ");
        		System.out.print(String.format("%.2f",beta0));
        		System.out.print(" ");
            	System.out.print(String.format("%.2f",beta1));
            	System.out.print(" ");
            	System.out.println(String.format("%.2f",MSE(beta0, beta1)));
        	}
        }
        
        else if(flag == 900) {
        	
        	
        	x = normalizeX();
        	double eta = Double.valueOf(args[1]);
        	int T = Integer.valueOf(args[2]);
        	double beta0 = 0;
        	double beta1 = 0;
        	for(int i = 0; i<T; i++) {
        		Random ran = new Random();
        		int t = ran.nextInt(getNumber());
        		//System.out.println(t);
        		double newbeta0 = beta0 - eta * (2*(beta0+beta1*x[t]-y[t]));
        		double newbeta1 = beta1 - eta * (2*(beta0+beta1*x[t]-y[t])*x[t]);
        		beta0 = newbeta0;
        		beta1 = newbeta1;
        		System.out.print(i+1);
        		System.out.print(" ");
        		System.out.print(String.format("%.2f",beta0));
        		System.out.print(" ");
            	System.out.print(String.format("%.2f",beta1));
            	System.out.print(" ");
            	System.out.println(String.format("%.2f",MSE(beta0, beta1)));
        	}
        }
        return;
    }
}