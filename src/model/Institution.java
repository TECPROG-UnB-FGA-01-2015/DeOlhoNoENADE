/**********************************************************
 * File: Institution.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Institution.
 *********************************************************/

package model;

import java.util.ArrayList;

public class Institution
{
	private ArrayList<Course> Courses = new ArrayList<Course>(); // Describes a list of courses of the Institution

	private String name; // Describes the name of the Institution
	private String academicOrganization; // Describes the academic organization of the Institution
	private String type; // Describes the type of the Institution
	private int institutionCode; // Describes the code of the Institution

	// Constructor to receive all the attributes as parameters and initialize the object Institution
	public Institution(String name,
					   String academicOrganization,
					   String type,
					   int institutionCode)
	{
		super();
		this.name = name;
		this.academicOrganization = academicOrganization;
		this.type = type;
		this.institutionCode = institutionCode;
	}

	// Insert a class to attribute Courses
	public void addCourse(Course course)
	{
		this.Courses.add(course);
	}

    // Access and returns the property courses
	public ArrayList<Course> getCourses()
	{
		return Courses;
	}

    // Sets a new value for the courses property
	public void setCourses(ArrayList<Course> cursos)
	{
		Courses = cursos;
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

    // Access and returns the property academicOrganization
	public String getAcademicOrganization()
	{
		return academicOrganization;
	}

    // Sets a new value for the academicOrganization property
	public void setAcademicOrganization(String academicOrganization)
	{
		this.academicOrganization = academicOrganization;
	}

    // Access and returns the property type
	public String getType()
	{
		return type;
	}

    // Sets a new value for the type property
	public void setType(String type)
	{
		this.type = type;
	}

    // Access and returns the property institutionCode
	public int getInstitutionCode()
	{
		return institutionCode;
	}

    // Sets a new value for the institutionCode property
	public void setInstitutionCode(int institutionCode)
	{
		this.institutionCode = institutionCode;
	}
}
