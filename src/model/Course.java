/**********************************************************
 * File: Course.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Course.
 *********************************************************/

package model;

public class Course
{

	private Institution IES; // Object from the Institution Class that keeps institution's info

	private int id; // Holds the info of the course's id
	private int id_institution; // Holds the info of the institution's id of the course
	private String name; // Holds the info of the course's name
	private int studentsNumber; // Holds the info of the number of students in the course
	private int  enrolledStudentsNumber; // Holds the info of the number of students enrolled in the course
	private String city; // Holds the info of the course's city
	private float courseGrade; // holds the info of the course's score in the Enade
	private String state; // holds the info of the course's state

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Course*/
	public Course(int id,
				  int id_institution,
				  String name,
				  int studentsNumber,	
				  int  enrolledStudentsNumber ,
				  String city,
				  float courseGrade,
				  String state,
				  Institution IES)
	{
		this.id = id;
		this.name = name;
		this.studentsNumber = studentsNumber;
		this. enrolledStudentsNumber  =  enrolledStudentsNumber ;
		this.city = city;
		this.courseGrade = courseGrade;
		this.state = state;
		this.IES = IES;
	}

	// Method to return the content of attribute state
	public String getState()
	{
		return state;
	}

	// Method to set a content on variable state
	public void setState(String state)
	{
		this.state = state;
	}

	// Method to return the content of attribute id_institution
	public int getId_institution()
	{
		return id_institution;
	}

	// Method to return the content of attribute id
	public int getId()
	{
		return id;
	}

	// Method to set a content on variable id
	public void setId(int id)
	{
		this.id = id;
	}

	// Method to return the content of attribute IES
	public Institution getIES()
	{
		return IES;
	}

	// Method to set a content on variable IES
	public void setIES(Institution iES)
	{
		IES = iES;
	}

	// Method to return the content of attribute name
	public String getName()
	{
		return name;
	}

	// Method to set a content on variable name
	public void setName(String name)
	{
		this.name = name;
	}

	// Method to return the content of attribute studentsNumber
	public int getStudentsNumber()
	{
		return studentsNumber;
	}

	// Method to set a content on variable studentsNumber
	public void setStudentsNumber(int studentsNumber)
	{
		this.studentsNumber = studentsNumber;
	}

	// Method to return the content of attribute  enrolledStudentsNumber 
	public int getEnrolledStudentsNumber()
	{
		return  enrolledStudentsNumber ;
	}

	// Method to set a content on variable  enrolledStudentsNumber 
	public void setEnrolledStudentsNumber (int enrolledStudentsNumber)
	{

		if(enrolledStudentsNumber < this.studentsNumber)
		{
			this.enrolledStudentsNumber = enrolledStudentsNumber ;
		}
		else
		{
			System.err.println("N Estudantes inscritos maior que N Estudantes");
		}

	}

	// Method to return the content of attribute city
	public String getCity()
	{
		return city;
	}

	// Method to set a content on variable city
	public void setCity(String city)
	{
		this.city = city;
	}

	// Method to return the content of attribute courseGrade
	public float getCourseGrade()
	{
		return courseGrade;
	}

	// Method to set a content on variable courseGrade
	public void setCourseGrade(float courseGrade)
	{
		this.courseGrade = courseGrade;
	}

}