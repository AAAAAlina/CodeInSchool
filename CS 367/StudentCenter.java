///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            StudentCenter
// Files:            StudentCenter.java 
//					 Course.java 
//					 EmptyQueueException.java 
//					 PriorityQueue.java 
//	    			 PriorityQueueItem.java 
//					 Queue.java
//					 QueueADT.java 
//					 PriorityQueueIterator.java 
//   				 Student.java
// Semester:         cs 367 Spring 2016
//
// Author:           Jiatong Li
// Email:            jli553@wisc.edu
// CS Login:         jiatong
// Lecturer's Name:  Jim Skrentny
// Lab Section:      None
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
// Lab Section:      None
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Student Center abstraction for our simulation. Execution starts here.
 * 
 * @author CS367
 *
 */
public class StudentCenter
{

	private static int DEFAULT_POINTS = 100;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			System.err.println("Bad invocation! Correct usage: " + 
		"java StudentCentre <StudentCourseData file>" +
					"<CourseRosters File> + <StudentCourseAssignments File>");
			System.exit(1);
		}

		boolean didInitialize = readData(args[0]);

		if(!didInitialize)
		{
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		generateAndWriteResults(args[1], args[2]);
	}

	/**
	 * 
	 * @param fileName
	 *            - input file. Has 3 sections - #Points/Student, #Courses, and
	 *            multiple #Student sections. See P3 page for more details.
	 * @return true on success, false on failure.
	 * 
	 */
	public static boolean readData(String fileName)
	{
		try
		{
			String name; // name of student
			String id;  // student id
			int coins = 0;  //total coins a student can have
			// Array to store the course id that student wants to enroll and 
			// coins he wants to spend
			String[] wishList = new String[2];
			// Array to store the course id, course name and capacity 
			String[] courseInfo = new String[3];
			// the file to read
			File file = new File(fileName);
			Scanner scnr = new Scanner(file);
			// temporarily initialize the read case (state) as A
			String state = "A";
			// used to read every line in the file
			String text;
			// used to store the new student read from the file
			Student newStu = null;
			// used to boolean whether a student name and id have been read
			Boolean studentInfo = false;
			// main loop for reading the file
			while (scnr.hasNextLine()){
				// read a line without whitespace before and after the text
				text = scnr.nextLine().trim();
				// if the line read is a header
				if(text.charAt(0) == '#'){
					// define three different cases for the header
					if (text.contains("#Points/Student")){
						state = "A";
					}
					else if (text.contains("#Courses")){
						state = "B";
					}
					else if (text.contains("#Student")){
						studentInfo = false;
						state = "C";
					}
				}
				else{
					if (state == "A"){
						coins = Integer.valueOf(text.trim());
					}
					if (state == "B"){
						// split the line into three strings according to the 
						// whitespace
						courseInfo = text.split(" ", 3);
						// 1st is course id, 2nd is course name and 
						// 3rd is capacity
						// build and store the new course
						courseList.add(new Course(courseInfo[0],courseInfo[1],
								Integer.valueOf(courseInfo[2])));
					}
					if (state == "C"){
						// if student name and id have not been read
						if(!studentInfo){
							name = text.trim();
							text = scnr.nextLine();
							id = text.trim();
							// build and store the new student
							newStu = new Student(name, id, coins);
							studentList.add(newStu);
							// boolean read student info as true
							studentInfo = true;
						}
						else{
							// split the line into 2 strings
							// 1st is the course id that the student wants to
							// enroll and 2nd is the coins he wants to spend
							wishList = text.split(" ", 2);
							// find the course from the course list
							Course wishCourse = 
									getCourseFromCourseList(wishList[0]);
							// add the course to that student's cart
							newStu.addToCart(wishCourse);
							// call course.addStudent() appropriately.
							// if the student has enough coins to enroll
							// add the student to the course registration list
							if (newStu.deductCoins
									(Integer.valueOf(wishList[1]))){
								wishCourse.addStudent(newStu, 
										Integer.valueOf(wishList[1]));
							}
						}

					}
				}
			}
			scnr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("File Parse Error");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param fileName1
	 *            - output file with a line for each course
	 * @param fileName2
	 *            - output file with a line for each student
	 */
	public static void generateAndWriteResults(String fileName1, 
			String fileName2)
	{
		try
		{
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList)
			{
				writer.println("-----" + c.getCourseCode() + " " +
			c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister())
				{
					writer.println(count + ". " + s.getid() + "\t" +
				s.getName());
					s.enrollCourse(c);
					count++;
				}
				writer.println();
			}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList)
			{
				writer.println("-----" + s.getid() + " " + s.getName() +
						"-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses())
				{
					writer.println(count + ". " + c.getCourseCode() + "\t" + 
				c.getCourseName());
					count++;
				}
				writer.println();
			}
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode)
	{
		for (Course c : courseList)
		{
			if(c.getCourseCode().equals(courseCode))
			{
				return c;
			}
		}
		return null;
	}
}