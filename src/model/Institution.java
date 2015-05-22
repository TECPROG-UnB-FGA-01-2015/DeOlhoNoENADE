/**********************************************************
 * File: Institution.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Institution.
 *********************************************************/

package model;

import java.util.ArrayList;

public class Institution
{
	private ArrayList<Course> Courses = new ArrayList<Course>();

	private String name; // Holds the info of the institution's name
	private String academicOrganization; // Holds the info of the institution's academic organization
	private String type; // Holds the info of the institution's type
	private int institutionCode; // Holds the info of the institution's code

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Institution*/
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

	// Method to insert a class to attribute Courses
	public void addCourse(Course course)
	{
		this.Courses.add(course);
	}

	// Method to return the content of attribute Courses
	public ArrayList<Course> getCourses()
	{
		return Courses;
	}

	// Method to set a content on variable Courses
	public void setCourses(ArrayList<Course> cursos)
	{
		Courses = cursos;
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

	// Method to return the content of attribute academicOrganization
	public String getAcademicOrganization()
	{
		return academicOrganization;
	}

	// Method to set a content on variable academicOrganization
	public void setAcademicOrganization(String academicOrganization)
	{
		this.academicOrganization = academicOrganization;
	}

	// Method to return the content of attribute type
	public String getType()
	{
		return type;
	}

	// Method to set a content on variable type
	public void setType(String type)
	{
		this.type = type;
	}

	// Method to return the content of attribute institutionCode
	public int getInstitutionCode()
	{
		return institutionCode;
	}

	// Method to set a content on variable institutionCode
	public void setInstitutionCode(int institutionCode)
	{
		this.institutionCode = institutionCode;
	}
}
