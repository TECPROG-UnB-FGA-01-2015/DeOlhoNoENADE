/***********************************************************
 * File: InstitutionTest.java
 * Purpose: Implement all the unit tests in the Institution model's methods 
***********************************************************/

package br.unb.deolhonoenade.Teste;

import android.test.AndroidTestCase;
import br.unb.deolhonoenade.model.Course;
import br.unb.deolhonoenade.model.Institution;
import junit.framework.Assert;
import java.util.ArrayList;

public class InstitutionTest extends AndroidTestCase
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

	// Method to test the instantiation of the institution object
	public void institutionTest()
	{
		Institution institution = new Institution("Universidade de Brasilia",
				"Universidades", "Publica", 2);
		Assert.assertNotSame("Quimica", institution.getName());
	}

	// Method for testing the addition of a class in an institution
	public void testAddCourse()
	{
		Institution institution = new Institution("Universidade de Brasilia",
				"Universidades", "Publica", 2);
		Course course = new Course(3, 6, "Direito", 25, 15, "Porto Alegre",
				(float) 2.45, "DF", null);

		institution.addCourse(course);

		Assert.assertTrue(institution.getCourses().contains(course));
	}

	// Method for testing the return of attribute Courses from class Institution
	public void testGetCourses()
	{
		ArrayList<Course> Courses = new ArrayList<Course>(50);
		Institution institution = new Institution(
				"Universidade Federal de Ouro Preto", "Universidades",
				"Publica", 6);

		institution.setCourses(Courses);

		Assert.assertEquals(new ArrayList<Course>(10), institution.getCourses());
	}

	// Method to test the assignment of Courses attribute from Class Institution
	public void testSetCourses()
	{
		Course course = new Course(3, 6, "Direito", 25, 15, "Porto Alegre",
				(float) 2.45, "DF", null);
		course.setName("Adiministracao");
		assertSame("Adiministracao", course.getName());
	}

	// Method for testing the return of attribute nome from class Institution
	public void testGetName()
	{
		Institution institution = new Institution(
				"Universidade Federal do Amazonas", "Universidades", "Publica",
				4);
		assertEquals("Universidade Federal do Amazonas", institution.getName());
	}

	// Method to test the assignment of nome attribute from Class Institution
	public void testSetName()
	{
		Institution institution = new Institution(
				"Universidade Catolica de Petropolis", "Universidades",
				"Privada", 15);

		institution.setName("Universidade Federal de Uberlandia");
		assertSame("Universidade Federal de Uberlandia", institution.getName());
	}

	// Method for testing the return of attribute organizacaoAcademica from class Institution
	public void testGetAcademicOrganization()
	{
		Institution institution = new Institution(
				"Universidade Estadual do Ceara", "Universidades", "Publica",
				29);
		assertEquals("Universidades", institution.getAcademicOrganization());

	}

	// Method to test the assignment of organizacaoAcademica attribute from Class Institution
	public void testSetAcademicOrganization()
	{
		Institution institution = new Institution("Universidade Gama Filho",
				"Universidades", "Privada", 16);
		institution.setAcademicOrganization("Faculdades");
		assertSame("Faculdades", institution.getAcademicOrganization());
	}

	// Method for testing the return of attribute tipo from class Institution
	public void testGetType()
	{
		Institution institution = new Institution(
				"Universidade do Estado da Bahia", "Universidades", "Publica",
				40);
		assertEquals("Publica", institution.getType());
	}

	// Method to test the assignment of tipo attribute from Class Institution
	public void testSetType()
	{
		Institution institution = new Institution(
				"Universidade Estadual de Goias", "Universidades", "Publica",
				47);
		institution.setType("Publica");
		assertNotSame("Privada", institution.getType());
	}

	// Method for testing the return of attribute codIES from class Institution
	public void testGetInstitutionCode()
	{
		Institution institution = new Institution(
				"Faculdade de Direito de Franca", "Faculdades", "Publica", 59);
		assertEquals(59, institution.getInstitutionCode());
	}

	// Method to test the assignment of codIES attribute from Class Institution
	public void testSetInstitutionCode()
	{
		Institution institution = new Institution(
				"Universidade Cruzeiro do Sul", "Universidades", "Privada", 221);
		institution.setInstitutionCode(221);
		assertEquals(221, institution.getInstitutionCode());
	}

}
