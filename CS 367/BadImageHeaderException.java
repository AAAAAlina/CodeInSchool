///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Receiver.java
// File:             BadImageHeaderException.java
// Semester:         367 Spring 2016
//
// Author:           Qiannan Guo qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This exception should be thrown whenever the received image buffer has any
 * bad part due to the broken header.
 * 
 * @author Alina(Qiannan Guo)
 */
public class BadImageHeaderException extends RuntimeException {

	/**
	 * Constructs a BadImageHeaderException with a message
	 * @param s the error message
	 */
	public BadImageHeaderException(String s) {
		super(s);
	}

	/**
	 * Constructs a BadImageHeaderException
	 */
	public BadImageHeaderException() {
	}
}
