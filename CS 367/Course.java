///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             Course.java
// Semester:         CS 367 Spring 2016
//
// Author:           Jiatong Li   jli553@wisc.edu
// CS Login:         jiatong
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to represent every Course in our Course Registration environment
 * 
 * @author CS367
 *
 */

public class Course
{

	private String courseCode;
	private String name;

	// Number of students allowed in the course
	private int maxCapacity;
	// Number of students enrolled in the course
	private int classCount;

	// The PriorityQueue structure
	private PriorityQueue<Student> registrationQueue;

	// List of students who are finally enrolled in the course
	private List<Student> courseRoster;
	
	/**
	 * Construct a new course with specific code, name and capacity.
	 * 
	 * @param classcode
	 * 			course code
	 * @param name
	 * 			class name
	 * @param maxCapacity
	 * 			class capacity
	 */
	public Course(String classCode, String name, int maxCapacity)
	{
		courseCode = classCode; // course code
		this.name = name;  // course name
		this.maxCapacity = maxCapacity;  // class capacity
		classCount = 0;  // number of student enrolled
		// course registration list
		registrationQueue = new PriorityQueue<Student> ();
		// enrolled students list
		courseRoster = new ArrayList<Student> ();
	}

	/**
	 * Creates a new PriorityqueueItem - with appropriate priority(coins) and
	 * this student in the item's queue. Add this item to the registrationQueue.
	 * 
	 * @param student
	 *            the student
	 * @param coins
	 *            the number of coins the student has
	 */
	public void addStudent(Student student, int coins)
	{
		// This method is called from Studentcenter.java
		// Enqueue a newly created PQItem.
		// Checking if a PriorityQueueItem with the same priority already exists
		// is done in the enqueue method.
		PriorityQueueItem<Student> pqitem =
				new PriorityQueueItem<Student>(coins);
		pqitem.add(student);
		registrationQueue.enqueue(pqitem);
	}

	/**
	 * Populates the courseRoster from the registration list.
	 * Use the PriorityQueueIterator for this task.
	 */
	public void processRegistrationList()
	{
		// populate courseRoster from registrationQueue
		// Use the PriorityQueueIterator for this task.
		Iterator<PriorityQueueItem<Student>> itr = registrationQueue.iterator();
		//when we have next priority in the list or the class is not full
		while(itr.hasNext() && this.classCount < this.maxCapacity){
			PriorityQueueItem<Student> item = itr.next();
			//the list has more students to add
			while(!item.getList().isEmpty()){
				Student enrolledStudent = item.getList().dequeue();
				//add the student to the class
				courseRoster.add(enrolledStudent);
				//change the number of students in the class
				this.classCount++;
			}
		}
	}

	/**
	 * Returns the course name.
	 * @return course name
	 */
	public String getCourseName()
	{
		return name;
	}
	/**
	 * Returns the course code.
	 * @return course code
	 */
	public String getCourseCode()
	{
		return courseCode;
	}
	/**
	 * Returns the registration list for the course.
	 * @return registration list
	 */
	public List<Student> getCourseRegister()
	{
		return courseRoster;
	}
}
