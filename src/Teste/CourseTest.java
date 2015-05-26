/***********************************************************
 * File: CouserTest.java
 * Purpose: Implement all the unit tests in the Course model's methods 
***********************************************************/

package Teste;

import android.test.AndroidTestCase;
import model.Course;
import model.Institution;
import junit.framework.Assert;

public class CourseTest extends AndroidTestCase
{
	// This method is responsible to signal the Test Startup. It's executed before each Test Method
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	// This method is responsible to signal the Test Ending. It's executed after each Test Method
	protected void tearDown() throws Exception
	{
		super.tearDown();

	}

	// Method to test if class is not null
	public void courseTest()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		Assert.assertNotNull(course);
	}

	// Method for testing the return of attribute state from class Course
	public void testGetState()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		Assert.assertEquals(course.getState(), "DF");
	}

	// Method to test the assignment of state attribute from Class Course
	public void testSetState()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		course.setState("AC");
		Assert.assertEquals(course.getState(), "AC");
	}

	// Method for testing the return of attribute IES from class Course
	public void testGetIES()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		// Object from the Institution Class that  is instantiated to be used in testing
		Institution institution = new Institution("UnB", "Universidade", "Publica", 1);

		course.setIES(institution);

		Assert.assertEquals(institution, course.getIES());
	}

	// Method to test the assignment of IES attribute from Class Course
	public void testSetIES()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		// Object from the Course Class that  is instantiated to be used in testing
		Institution institution = new Institution("UFBA", "Universidade", "Publica", 10);

		course.setIES(institution);

		Assert.assertSame(institution, course.getIES());
	}

	// Method for testing the return of attribute id_institution from class Course
	public void testGetId_institution()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Institution institution = new Institution("UFBA", "Universidade", "Publica", 10);

		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", institution);

		Assert.assertEquals(course.getId_institution(), 0);
	}

	// Method for testing the return of attribute id from class Course
	public void testGetId()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		Assert.assertEquals(course.getId(), 3);
	}

	// Method to test the assignment of id attribute from Class Course
	public void testSetId()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		course.setId(5);
		
		Assert.assertEquals(course.getId(), 5);
	}

	// Method for testing the return of attribute name from class Course
	public void testGetName()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		assertEquals("Direito", course.getName());
	}

	// Method to test the assignment of name attribute from Class Course
	public void testSetName()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		course.setName("medicina");
		
		assertSame("medicina", course.getName());
	}

	// Method for testing the return of attribute studentsNumber from class Course
	public void testGetStudentsNumber()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 100, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		assertEquals(100, course.getStudentsNumber());
	}

	// Method to test the assignment of studentsNumber attribute from Class Course
	public void testSetStudentsNumber()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		course.setStudentsNumber(500);
		
		assertEquals(500, course.getStudentsNumber());
	}

	// Method for testing the return of attribute enrolledStudentsNumber from class Course
	public void testGetEnrolledStudentsNumber()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 5, "portoalegre",
				(float) 2.45, "DF", null);

		assertSame(5, course.getEnrolledStudentsNumber());
	}

	// Method to test the assignment of enrolledStudentsNumber attribute from Class Course
	public void testSetEnrolledStudentsNumber()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 20, 15, "portoalegre",
				(float) 2.45, "DF", null);

		course.setEnrolledStudentsNumber(20);

		assertNotSame(20, course.getEnrolledStudentsNumber());
	}

	// Method for testing the return of attribute city from class Course
	public void testGetCity()
	{
		Course course = new Course(3, 6, "Direito", 25, 15, "curitiba",
				(float) 2.45, "DF", null);
		assertEquals("curitiba", course.getCity());
	}

	// Method to test the assignment of city attribute from Class Course
	public void testSetCity()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		course.setCity("BeloHorizonte");
		
		assertSame("BeloHorizonte", course.getCity());
	}

	// Method for testing the return of attribute conceitoEnade from class Course
	public void testGetCourseGrade()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 5, "DF", null);
		
		assertEquals((float) 5, course.getCourseGrade());
	}

	// Method to test the assignment of CourseGrade attribute from Class Course
	public void testSetCourseGrade()
	{
		// Object from the Course Class that  is instantiated to be used in testing
		Course course = new Course(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		
		course.setCourseGrade((float) 4);
		
		assertEquals((float) 4, course.getCourseGrade());
	}
}
