/**********************************************************
 * File: Course.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Course.
 *********************************************************/

package model;

public class Course
{

	private Institution IES; // Describes the institution of the Course

	private int id; // Describes the id of the Course
	private int id_institution; // Describes the id of the Institution of the Course
	private String name; // Describes the name of the Course
	private int studentsNumber; // Describes the number of students on the Course
	private int  enrolledStudentsNumber; // Describes the number of students enrolled on the Course
	private String city; // Describes the city of the Course
	private float courseGrade; // holds the info of the course's score in the Enade
	private String state; // Describes the state of the Course

	// Constructor to receive all the attributes as parameters and initialize the object Course
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

    // Access and returns the property state
	public String getState()
	{
		return state;
	}

    // Sets a new value for the state property
	public void setState(String state)
	{
		this.state = state;
	}

    // Access and returns the property id_institution
	public int getId_institution()
	{
		return id_institution;
	}

    // Access and returns the property id
	public int getId()
	{
		return id;
	}

    // Sets a new value for the id property
	public void setId(int id)
	{
		this.id = id;
	}

    // Access and returns the property iES
	public Institution getIES()
	{
		return IES;
	}

    // Sets a new value for the iES property
	public void setIES(Institution iES)
	{
		IES = iES;
	}

    // Access and returns the property name
	public String getName()
	{
		return name;
	}

    // Sets a new value for the name property
	public void setName(String name)
	{
		this.name = name;
	}

    // Access and returns the property studentsNumber
	public int getStudentsNumber()
	{
		return studentsNumber;
	}

    // Sets a new value for the studentsNumber property
	public void setStudentsNumber(int studentsNumber)
	{
		this.studentsNumber = studentsNumber;
	}

    // Access and returns the property enrolledStudentsNumber
	public int getEnrolledStudentsNumber()
	{
		return  enrolledStudentsNumber ;
	}

    // Sets a new value for the enrolledStudentsNumber property
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

    // Access and returns the property city
	public String getCity()
	{
		return city;
	}

    // Sets a new value for the city property
	public void setCity(String city)
	{
		this.city = city;
	}

    // Access and returns the property courseGrade
	public float getCourseGrade()
	{
		return courseGrade;
	}

    // Sets a new value for the courseGrade property
	public void setCourseGrade(float courseGrade)
	{
		this.courseGrade = courseGrade;
	}
}
