/***********************************************************
 * File: TestControllerCurso.java
 * Purpose: Responsible to make unit tests in all the CourseController's methods.
 ***********************************************************/

package br.unb.deolhonoenade.Teste;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.DAO.OperacoesBancoDeDados;
import br.unb.deolhonoenade.controller.CourseController;
import br.unb.deolhonoenade.model.Course;
import br.unb.deolhonoenade.model.Institution;
import junit.framework.Assert;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class CourseControllerTest extends AndroidTestCase
{

	private ArrayList<Course> courses;

	// This method is responsible to call the parent constructor with no arguments
	public CourseControllerTest()
	{
		super();
	}

	/* This method is responsible to signal the Test Startup.
	 * It's executed before each Test Method */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/* This method is responsible to signal the Test Ending. 
	 * It's executed after each Test Method */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	// This method is responsible to test if the Controller Object isn't Null
	public void testCourseController()
	{
		CourseController controller = new CourseController(getContext());
		Assert.assertNotNull(controller);
	}

	// This method is responsible to test if the Controller Object from the Database isn't Null
	public void testGetDatabase()
	{
		CourseController controller = new CourseController(getContext());

		Assert.assertNotNull(controller.getDatabase());

	}

	/* This method is responsible to test if Universities' names removal was
	 * successful on the Database It ensures that you can't choose the same
	 * University twice with the same Brazilian State to compare them */
	public void testRemoveTrueInstitution()
	{
		CourseController controller = new CourseController(getContext());

		int codCurso = controller.searchCourseCode("Administracao");

		controller.searchCourse(codCurso, "SP");

		assertTrue(controller.removeInstitution(1));

	}

	/* This method is responsible to test if Universities' names removal wasn't
	 * successful on the Database */
	public void testRemoveFalseInstitution()
	{
		CourseController controller = new CourseController(getContext());

		int codCurso = controller.searchCourseCode("Administracao");

		controller.searchCourse(codCurso, "SP");

		assertFalse(controller.removeInstitution(999));

	}

	/* This method is responsible to test if the University's ID (position) is
	 * registered on the correct Brazilian State, based on the Course's ID and
	 * the University's Brazilian State, on the Database */
	public void testInstitutionCode()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "SP");
		Assert.assertEquals(322, controller.getInstitutionCode(2));
	}

	/* This method is responsible to test if the University's ID (position)
	 * isn't registered on the incorrect Brazilian State, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testInstitutionCodeIndexOutOfBounds()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "AC");
		Assert.assertEquals(-1, controller.getInstitutionCode(200));
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State has the correct ENADE's grade, based on the Course's ID
	 * and the University's Brazilian State, on the Database */
	public void testCourseGrade()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "SP");

		Assert.assertEquals((float) 4.882,
		                    controller.getCourseGrade(2));
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State hasn't the correct ENADE's grade, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testCourseGradeIndexOutOfBounds()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "AC");

		Assert.assertEquals((float) -1,
		                    controller.getCourseGrade(200));
	}

	/* This method is responsible to test if the University's name is registered
	 * correctly on the Database */
	public void testSearchInstitution()
	{
		Institution institution;
		CourseController controller = new CourseController(getContext());

		institution = controller.searchInstitution(1);
		Assert.assertEquals("UNIVERSIDADE FEDERAL DE MATO GROSSO",
		                    institution.getName());
	}

	/* This method is responsible to test if the University's info has the
	 * correct info compared with University's course info, based on Course's ID
	 * and University's Brazilian State, on the Database */
	public void testInstitutionInfo()
	{

		CourseController controller = new CourseController(getContext());
		Institution firstInstitution = controller.searchInstitution(2);
		controller.searchCourse(1, "DF");
		Course course = new Course(1,
		                        2,
		                        "UNIVERSIDADE DE BRASILIA",
		                        141,
		                        89,
		                        "BRASILIA",
		                        (float) 3.735,
		                        "DF",
		                        firstInstitution);
		List<String> institutionInfo = controller.getInstitutionInfo(1);

		Assert.assertEquals(institutionInfo.get(0), firstInstitution.getName());
		Assert.assertEquals(institutionInfo.get(1), firstInstitution.getAcademicOrganization());
		Assert.assertEquals(institutionInfo.get(2), firstInstitution.getType());
		Assert.assertEquals(institutionInfo.get(3), course.getCity());
		Assert.assertEquals(institutionInfo.get(4),
		                    String.format("%d",
		                                  course.getEnrolledStudentsNumber()));
		Assert.assertEquals(institutionInfo.get(5),
		                    String.format("%d", course.getStudentsNumber()));
	}

	/* This method is responsible to test if the University's course info
	 * (University Name, Academic Organization, University Type - Public/Private
	 * Universities, University City, Registered Students - who participated on
	 * the ENADE's test - and Course's number of students) has null University's
	 * info */
	public void testInstitutionInfoIndexOutOfBounds()
	{

		CourseController controller = new CourseController(getContext());
		Institution firstInstitution = controller.searchInstitution(2);
		controller.searchCourse(1, "DF");
		Course course = new Course(1,
		                        2,
		                        "UNIVERSIDADE DE BRASILIA",
		                        141,
		                        89,
		                        "BRASILIA",
		                        (float) 3.735,
		                        "DF",
		                        firstInstitution);

		List<String> institutionInfo = null;

		try
		{
			institutionInfo = controller.getInstitutionInfo(9893);
		}
		catch (Error e)
		{
			e.printStackTrace();
		}

		Assert.assertNull(institutionInfo);

	}

	/* This method is responsible to test if the comparison between two
	 * different Universities (with two different Brazilian States) was
	 * successful */
	public void testCompareState()
	{
		CourseController controller = new CourseController(getContext());

		String firstState = "DF", secondState = "AM";

		assertEquals(controller.compareState(firstState, secondState, 1).get(0),
		             (float) 1.9448332);
	}

	/* This method is responsible to test if the Course's ID is registered
	 * correctly on the University's name on the Database */
	public void testSearchCourseCode()
	{
		int codCurso;
		CourseController controller = new CourseController(getContext());

		codCurso = controller.searchCourseCode("Administracao");
		Assert.assertEquals(1, codCurso);
	}

	/* This method is responsible to test if three different Universities (on
	 * the same Brazilian State) with the same course info (Universities' Names,
	 * Academic Organizations, Universities Type - Public/Private Universities,
	 * Universities' Cities, Registered Students - who participated on the
	 * ENADE's test - and Courses' number of students) are registered correctly
	 * based on Course's ID and University's Brazilian State on the Database */
	public void testSearchCourseUniversityID()
	{
		CourseController controller = new CourseController(getContext());
		ArrayList<Course> secondCoursesList = new ArrayList<Course>();
		ArrayList<Course> firstCoursesList = new ArrayList<Course>();

		Institution firstInstitution = new Institution("FACULDADE BARAO DO RIO BRANCO",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2132);
		Institution secondInstitution = new Institution("FACULDDE DA AMAZONIA OCIDENTAL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2343);
		Institution thirdInstitution = new Institution("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2072);

		Course firstCourse = new Course(1,
		                         2072,
		                         "ADMINISTRACAO",
		                         29,
		                         26,
		                         "CRUZEIRO DO SUL",
		                         (float) 0.785,
		                         "AC",
		                         thirdInstitution);
		Course secondCourse = new Course(1,
		                         2132,
		                         "ADMINISTRACAO",
		                         147,
		                         125,
		                         "RIO BRANCO",
		                         (float) 1.605,
		                         "AC",
		                         firstInstitution);
		Course thirdCourse = new Course(1,
		                         2343,
		                         "ADMINISTRACAO",
		                         49,
		                         48,
		                         "RIO BRANCO",
		                         (float) 1.432,
		                         "AC",
		                         secondInstitution);

		secondCoursesList.add(secondCourse);
		secondCoursesList.add(thirdCourse);
		secondCoursesList.add(firstCourse);

		courses = controller.searchCourse(1, "AC");

		Assert.assertEquals(secondCoursesList.get(0).getCourseGrade(), firstCoursesList.get(0)
		        .getCourseGrade());
		Assert.assertEquals(secondCoursesList.get(0).getId(), firstCoursesList.get(0).getId());
		Assert.assertEquals(secondCoursesList.get(0).getId_institution(), firstCoursesList.get(0)
		        .getId_institution());
		Assert.assertEquals(secondCoursesList.get(0).getStudentsNumber(), firstCoursesList.get(0)
		        .getStudentsNumber());

		Assert.assertEquals(secondCoursesList.get(1).getCourseGrade(), firstCoursesList.get(1)
		        .getCourseGrade());
		Assert.assertEquals(secondCoursesList.get(1).getId(), firstCoursesList.get(1).getId());
		Assert.assertEquals(secondCoursesList.get(1).getId_institution(), firstCoursesList.get(1)
		        .getId_institution());
		Assert.assertEquals(secondCoursesList.get(1).getStudentsNumber(), firstCoursesList.get(1)
		        .getStudentsNumber());

		Assert.assertEquals(secondCoursesList.get(2).getCourseGrade(), firstCoursesList.get(2)
		        .getCourseGrade());
		Assert.assertEquals(secondCoursesList.get(2).getId(), firstCoursesList.get(2).getId());
		Assert.assertEquals(secondCoursesList.get(2).getId_institution(), firstCoursesList.get(2)
		        .getId_institution());
		Assert.assertEquals(secondCoursesList.get(2).getStudentsNumber(), firstCoursesList.get(2)
		        .getStudentsNumber());

	}

	/* This method is responsible to test if one University with one course info
	 * (Universities' Names, Academic Organizations, Universities Type -
	 * Public/Private Universities, Universities' Cities, Registered Students -
	 * who participated on the ENADE's test - and Courses' number of students)
	 * are registered correctly based on Course's ID, University's Brazilian
	 * State and University's name on the Database */
	public void testSearchCourseID()
	{
		CourseController controller = new CourseController(getContext());
		ArrayList<Course> secondCoursesList = new ArrayList<Course>();
		ArrayList<Course> firstCoursesList = new ArrayList<Course>();

		Institution thirdInstitution = new Institution("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2072);

		Course firstCourse = new Course(1,
		                         2072,
		                         "ADMINISTRACAO",
		                         29,
		                         26,
		                         "CRUZEIRO DO SUL",
		                         (float) 0.785,
		                         "AC",
		                         thirdInstitution);

		secondCoursesList.add(firstCourse);

		courses = controller.searchCourse(1, "AC", "CRUZEIRO DO SUL");

		Assert.assertEquals(secondCoursesList.get(0).getCourseGrade(), firstCoursesList.get(0)
		        .getCourseGrade());
		Assert.assertEquals(secondCoursesList.get(0).getId(), firstCoursesList.get(0).getId());
		Assert.assertEquals(secondCoursesList.get(0).getId_institution(), firstCoursesList.get(0)
		        .getId_institution());
		Assert.assertEquals(secondCoursesList.get(0).getStudentsNumber(), firstCoursesList.get(0)
		        .getStudentsNumber());

	}

	/* This method is responsible to test if one University with one course info
	 * (Universities' Names, Academic Organizations, Universities Type -
	 * Public/Private Universities, Universities' Cities, Registered Students -
	 * who participated on the ENADE's test - and Courses' number of students)
	 * are registered correctly based on Course's ID, University's Brazilian
	 * State, University's City and University's Type on the Database */
	public void testSearchCourseInfo()
	{
		CourseController controller = new CourseController(getContext());
		ArrayList<Course> secondCoursesList = new ArrayList<Course>();
		ArrayList<Course> firstCoursesList = new ArrayList<Course>();

		Institution firstInstitution = new Institution("UNIVERSIDADE DE BRASILIA",
		                                  "UNIVERSIDADES",
		                                  "PUBLICA",
		                                  2);

		Course firstCourse = new Course(1,
		                         2,
		                         "ADMINISTRACAO",
		                         141,
		                         89,
		                         "BRASILIA",
		                         (float) 3.735,
		                         "DF",
		                         firstInstitution);

		secondCoursesList.add(firstCourse);

		courses = controller.searchCourse(1, "DF", "BRASILIA", "PUBLICA");

		Assert.assertEquals(secondCoursesList.get(0).getCourseGrade(), firstCoursesList.get(0)
		        .getCourseGrade());
		Assert.assertEquals(secondCoursesList.get(0).getId(), firstCoursesList.get(0).getId());
		Assert.assertEquals(secondCoursesList.get(0).getId_institution(), firstCoursesList.get(0)
		        .getId_institution());
		Assert.assertEquals(secondCoursesList.get(0).getStudentsNumber(), firstCoursesList.get(0)
		        .getStudentsNumber());
	}

	/* This method is responsible to test if two different Brazilian Cities
	 * (with the same Brazilian State) are registered correctly based on
	 * Course's ID and University's Brazilian State on the Database */
	public void testSearchCities()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCitiesList = new ArrayList<String>();
		List<String> firstCitiesList = new ArrayList<String>();

		String firstCity = new String("CRUZEIRO DO SUL");
		String secondCity = new String("RIO BRANCO");

		secondCitiesList.add(firstCity);
		secondCitiesList.add(secondCity);

		firstCitiesList = controller.searchCities(1, "AC");
		Assert.assertEquals(secondCitiesList, firstCitiesList);
	}

	/* This method is responsible to test if the Acre's University Type is
	 * Private, based on the Course's ID and the University's Type */
	public void testSearchTypeAC()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList = new ArrayList<String>();

		String firstType = new String("PRIVADA");

		secondTypeList.add(firstType);

		firstTypeList = controller.searchTypes(1, "RIO BRANCO");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test Brasilia's three Universities Types
	 * (Public, Private and "Both") based on the Course's ID and the
	 * University's Type */
	public void testSearchTypeDF()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList = new ArrayList<String>();

		String firstType = new String("Ambas");
		String secondType = new String("PRIVADA");
		String thirdType = new String("PUBLICA");

		secondTypeList.add(firstType);
		secondTypeList.add(secondType);
		secondTypeList.add(thirdType);

		firstTypeList = controller.searchTypes(1, "BRASILIA");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test two Universities' Types (Public and
	 * Private) based on the Course's ID and the University's Brazilian State */
	public void testSearchTypeState()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList = new ArrayList<String>();

		String secondType = new String("PRIVADA");
		String thirdType = new String("PUBLICA");

		secondTypeList.add(secondType);
		secondTypeList.add(thirdType);
		
		firstTypeList = controller.searchTypeState(1, "DF");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test if the Acre's University Type is
	 * Private, based on the Course's ID and the University's Brazilian State */
	public void testSearchTypeStateAC()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList = new ArrayList<String>();

		String firstType = new String("PRIVADA");

		secondTypeList.add(firstType);

		firstTypeList = controller.searchTypeState(1, "AC");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test if all the Brazilian States are
	 * registered correctly on the Database */
	public void testSearchState()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondStateList = new ArrayList<String>();
		List<String> firstStateList = new ArrayList<String>();

		String firstState = new String("AL");
		String secondState = new String("AM");
		String thirdState = new String("AP");
		String fourthState = new String("BA");
		String fifthState = new String("CE");
		String sixthState = new String("DF");
		String seventhState = new String("GO");
		String eighthState = new String("MA");
		String ninthState = new String("MG");
		String tenthState = new String("MS");
		String eleventhState = new String("MT");
		String twelfthState = new String("PA");
		String thirteenthState = new String("PB");
		String fourteenthState = new String("PE");
		String fifteenthState = new String("PI");
		String sixteenthState = new String("PR");
		String seventeenthState = new String("RJ");
		String eighteenthState = new String("RN");
		String nineteenthState = new String("RR");
		String twentiethState = new String("RS");
		String twentyFirstState = new String("SC");
		String twentySecondState = new String("SE");
		String twentyThirdState = new String("SP");

		secondStateList.add(firstState);
		secondStateList.add(secondState);
		secondStateList.add(thirdState);
		secondStateList.add(fourthState);
		secondStateList.add(fifthState);
		secondStateList.add(sixthState);
		secondStateList.add(seventhState);
		secondStateList.add(eighthState);
		secondStateList.add(ninthState);
		secondStateList.add(tenthState);
		secondStateList.add(eleventhState);
		secondStateList.add(twelfthState);
		secondStateList.add(thirteenthState);
		secondStateList.add(fourteenthState);
		secondStateList.add(fifteenthState);
		secondStateList.add(sixteenthState);
		secondStateList.add(seventeenthState);
		secondStateList.add(eighteenthState);
		secondStateList.add(nineteenthState);
		secondStateList.add(twentiethState);
		secondStateList.add(twentyFirstState);
		secondStateList.add(twentySecondState);
		secondStateList.add(twentyThirdState);

		firstStateList = controller.searchState(67);
		Assert.assertEquals(secondStateList, firstStateList);
	}

	/* This method is responsible to test if fourteen Universities' names
	 * together with their respectives ENADE's grades are registered correctly,
	 * based on the Courses' ID and the Universities' Brazilian States, on the
	 * Database */
	public void testSearchCourseCodeState()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList = new ArrayList<String>();

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		secondCourseList.add("FACULDADE DE CIENCIAS HUMANAS, ECONOMICAS E DA SAUDE DE ARAGUAINA - 2,460000");
		secondCourseList.add("FACULDADE DE ADMINISTRACAO DE EMPRESAS DE PARAISO DO TOCANTINS - 2,357000");
		secondCourseList.add("CENTRO UNIVERSITARIO UNIRG - 2,290000");
		secondCourseList.add("CENTRO UNIVERSITARIO LUTERANO DE PALMAS - 2,185000");
		secondCourseList.add("INSTITUTO DE ENSINO E PESQUISA OBJETIVO - 2,185000");
		secondCourseList.add("FACULDADE INTEGRADA DE ARAGUATINS - 2,103000");
		secondCourseList.add("FACULDADE GUARAI - 2,014000");
		secondCourseList.add("FACULDADE CATOLICA DOM ORIONE - 1,502000");
		secondCourseList.add("FACULDADE SERRA DO CARMO - 1,443000");
		secondCourseList.add("FACULDADE CATOLICA DO TOCANTINS - 1,433000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1,366000");
		secondCourseList.add("FACULDADE ITOP - 1,202000");
		secondCourseList.add("FACULDADE SAO MARCOS - 0,209000");

		firstCourseList = controller.searchState(1, "TO");

		for (int i = 0; i < 14; i++)
		{
			Assert.assertEquals(secondCourseList.get(i), firstCourseList.get(i));
		}

	}

	/* This method is responsible to test if two Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States, Universities' Cities and
	 * Universities' Types, on the Database */
	public void testSearchStateCityType()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList = new ArrayList<String>();

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1,366000");

		firstCourseList = controller.searchStateCityType(1, "TO", "PALMAS", "PUBLICA");

		for (int i = 1; i < 2; i++)
		{
			Assert.assertEquals(secondCourseList.get(i), firstCourseList.get(i));
		}
	}

	/* This method is responsible to test if seven Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States and Universities' Cities,
	 * on the Database */
	public void testSearchStateCity()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList = new ArrayList<String>();

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		secondCourseList.add("CENTRO UNIVERSITARIO LUTERANO DE PALMAS - 2,185000");
		secondCourseList.add("INSTITUTO DE ENSINO E PESQUISA OBJETIVO - 2,185000");
		secondCourseList.add("FACULDADE SERRA DO CARMO - 1,443000");
		secondCourseList.add("FACULDADE CATOLICA DO TOCANTINS - 1,433000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1,366000");
		secondCourseList.add("FACULDADE ITOP - 1,202000");

		firstCourseList = controller.seachStateCity(1, "TO", "PALMAS");
		for (int i = 1; i < 7; i++)
		{
			Assert.assertEquals(secondCourseList.get(i), firstCourseList.get(i));
		}
	}

	/* This method is responsible to test if five Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States and Universities' Types,
	 * on the Database */
	public void testSearchStateIntegerType()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList = new ArrayList<String>();

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		secondCourseList.add("CENTRO UNIVERSITARIO UNIRG - 2,290000");
		secondCourseList.add("FACULDADE INTEGRADA DE ARAGUATINS - 2,103000");
		secondCourseList.add("FACULDADE GUARAI - 2,014000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1,366000");

		firstCourseList = controller.searchStateIntegerType(1, "TO", 2);
		for (int i = 1; i < 5; i++)
		{
			Assert.assertEquals(secondCourseList.get(i), firstCourseList.get(i));
		}

	}

	/* This method is responsible to test if Universities' IDs together with
	 * their respectives Course's IDs are registered correctly, based on the
	 * Courses' ID and Universities' Brazilian States, on the Database */
	public void testSearchInstitutionCode()
	{

		CourseController controller = new CourseController(getContext());

		controller.searchCourse(2, "DF");
		Assert.assertEquals(2, controller.getInstitutionCode(0));

	}

	/* This method is responsible to test if Universities' ENADE grades together
	 * with their respectives Courses are registered correctly, based on the
	 * Courses' ID and Universities' Brazilian States, on the Database */
	public void testCoursesCodes()
	{

		CourseController controller = new CourseController(getContext());

		controller.searchCourse(2, "DF");
		Assert.assertEquals((float) 4.482,
		                    controller.getInstitutionCode(0));

	}

	/* This method is responsible to test if two Universities's from two Cities
	 * (from two Brazilian States) aren't the same. It ensures that they're from
	 * different Cities and Brazilian States so that you can make comparisons
	 * between them */
	public void testCompareCity()
	{
		CourseController controller = new CourseController(getContext());

		String firstState = "DF", firstCity = "BRASILIA";
		String secondState = "AM", secondCity = "MANAUS";

		assertNotSame(controller.compareCity(1,
		                                          firstState,
		                                          firstCity,
		                                          secondState,
		                                          secondCity).get(0),
		              "1.900000");

	}

	/* This method is responsible to test the Universities's Types based on
	 * Course's ID, First University's Brazilian State, First University's Type,
	 * Second University's Brazilian State and Second University's Type have
	 * different ENADE's grades average, on the Database */
	public void testCompareType()
	{
		CourseController controller = new CourseController(getContext());
		List<Float> typeResult = controller.compareType(1,
		                                                  "AC",
		                                                  "Privada",
		                                                  "AL",
		                                                  "Privada");

		Assert.assertEquals((float) 1.5185001, typeResult.get(0));
		Assert.assertEquals((float) 2.285000, typeResult.get(1));
	}

	/* This method is responsible to test the Brazilian State's ENADE grade
	 * average, based on Universities's Brazilian State, and Course's ID, on the
	 * Database */
	public void testStateAverageGrade()
	{
		CourseController controller = new CourseController(getContext());

		Assert.assertEquals((float) 2.249236, controller.calculateStateGrade("SP", 1));

	}

}
