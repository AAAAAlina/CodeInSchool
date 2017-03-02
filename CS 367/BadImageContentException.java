///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Receiver.java
// File:             BadImageContentException.java
// Semester:         367 Spring 2016
//
// Author:           Qiannan Guo qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This exception should be thrown whenever the received image buffer has any
 * broken part due to corrupt content.
 * 
 * @author Alina(Qiannnan Guo)
 */
public class BadImageContentException extends RuntimeException {

	/**
	 * Constructs a BadImageContentException with a message
	 * @param s the error message
	 */
	public BadImageContentException(String s) {
		super(s);
	}

	/**
	 * Constructs a BadImageContentException
	 */
	public BadImageContentException() {
	}
}
