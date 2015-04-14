/**********************************************************
 * File: Institution.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Institution.
 *********************************************************/

package br.unb.deolhonoenade.model;

import java.util.ArrayList;

public class Institution
{

	private ArrayList<Course> Courses = new ArrayList<Course>();

	private String name;
	private String academicOrganization;
	private String type;
	private int codIES;

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Institution*/
	public Institution(	String name,
						String academicOrganization,
						String type,
						int codIES)
	{
		super();
		this.name = name;
		this.academicOrganization = academicOrganization;
		this.type = type;
		this.codIES = codIES;
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

	// Method to return the content of attribute codIES
	public int getCodIES()
	{
		return codIES;
	}

	// Method to set a content on variable codIES
	public void setCodIES(int codIES)
	{
		this.codIES = codIES;
	}

}