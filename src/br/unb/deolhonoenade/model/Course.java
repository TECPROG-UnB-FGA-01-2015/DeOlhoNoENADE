/**********************************************************
 * File: Course.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Course.
 *********************************************************/

package br.unb.deolhonoenade.model;

public class Course
{

	private Institution IES;

	private int id;
	private int id_institution;
	private String name;
	private int studentsNumber;
	private int  enrolledStudentsNumber ;
	private String city;
	private float enadeGrade;
	private String state;

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Course*/
	public Course(int id,
				 int id_institution,
				 String name,
				 int studentsNumber,	
				 int  enrolledStudentsNumber ,
				 String city,
				 float enadeGrade,
				 String state,
				 Institution IES)
	{
		this.id = id;
		this.name = name;
		this.studentsNumber = studentsNumber;
		this. enrolledStudentsNumber  =  enrolledStudentsNumber ;
		this.city = city;
		this.enadeGrade = enadeGrade;
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
	public int getEnrolledStudentsNumber ()
	{
		return  enrolledStudentsNumber ;
	}

	// Method to set a content on variable  enrolledStudentsNumber 
	public void setEnrolledStudentsNumber (int  enrolledStudentsNumber )
	{

		if( enrolledStudentsNumber  < this.studentsNumber)
		{
			this. enrolledStudentsNumber  =  enrolledStudentsNumber ;
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

	// Method to return the content of attribute enadeGrade
	public float getEnadeGrade()
	{
		return enadeGrade;
	}

	// Method to set a content on variable enadeGrade
	public void setEnadeGrade(float enadeGrade)
	{
		this.enadeGrade = enadeGrade;
	}

}