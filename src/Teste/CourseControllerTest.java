/***********************************************************
 * File: CourseControllerTest.java
 * Purpose: Responsible to make unit tests in all the CourseController's methods.
 ***********************************************************/

package Teste;

import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import controller.CourseController;
import model.Course;
import model.Institution;

@SuppressWarnings("ALL")
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

		int courseCode = controller.searchCourseCode("Administracao");
		controller.searchCourse(courseCode, "SP");

        boolean institutionRemoved = controller.removeInstitution(1);
		assertTrue(institutionRemoved);
	}

	/* This method is responsible to test if Universities' names removal wasn't
	 * successful on the Database */
	public void testRemoveFalseInstitution()
	{
		CourseController controller = new CourseController(getContext());

		int courseCode = controller.searchCourseCode("Administracao");
		controller.searchCourse(courseCode, "SP");

        boolean institutionRemoved = controller.removeInstitution(999);
		assertFalse(institutionRemoved);
	}

	/* This method is responsible to test if the University's ID (position) is
	 * registered on the correct Brazilian State, based on the Course's ID and
	 * the University's Brazilian State, on the Database */
	public void testInstitutionCode()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "SP");

        int institutionCode = controller.getInstitutionCode(2);
		Assert.assertEquals(322, institutionCode);
	}

	/* This method is responsible to test if the University's ID (position)
	 * isn't registered on the incorrect Brazilian State, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testInstitutionCodeIndexOutOfBounds()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "AC");

        int institutionCode = controller.getInstitutionCode(200);
		Assert.assertEquals(-1, institutionCode);
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State has the correct ENADE's grade, based on the Course's ID
	 * and the University's Brazilian State, on the Database */
	public void testCourseGrade()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "SP");

        float courseGrade = controller.getCourseGrade(2);
		Assert.assertEquals((float) 4.882, courseGrade);
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State hasn't the correct ENADE's grade, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testCourseGradeIndexOutOfBounds()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(1, "AC");

        float courseGrade = controller.getCourseGrade(200);
		Assert.assertEquals((float) -1, courseGrade);
	}

	/* This method is responsible to test if the University's name is registered
	 * correctly on the Database */
	public void testSearchInstitution()
	{
		Institution institution;
		CourseController controller = new CourseController(getContext());

		institution = controller.searchInstitution(1);
        String institutionName = institution.getName();
		Assert.assertEquals("UNIVERSIDADE FEDERAL DE MATO GROSSO", institutionName);
	}

	/* Test if the University's info has the correct info
	 * compared with University's course info, based on Course's ID
	 * and University's Brazilian State, on the Database */
	public void testInstitutionInfo() throws Exception
	{
		CourseController controller = new CourseController(getContext());
		Institution firstInstitution = controller.searchInstitution(2);
		controller.searchCourse(1, "DF");

		Course course = new Course(1, 2, "UNIVERSIDADE DE BRASILIA", 141, 89, "BRASILIA", (float) 3.735,
                                   "DF", firstInstitution);

		List<String> institutionInfo = controller.getInstitutionInfo(1);

        String firstInstitutionName = firstInstitution.getName();
		Assert.assertEquals(institutionInfo.get(0), firstInstitutionName);

        String firstInstitutionOrganization = firstInstitution.getAcademicOrganization();
		Assert.assertEquals(institutionInfo.get(1), firstInstitutionOrganization);

        String firstInstitutionType = firstInstitution.getType();
		Assert.assertEquals(institutionInfo.get(2), firstInstitutionType);

        String courseCity = course.getCity();
		Assert.assertEquals(institutionInfo.get(3), courseCity);

        int enrolledStudentsNumber = course.getEnrolledStudentsNumber();
        String formattedEnrolledStudentsNumber = String.format("%d", enrolledStudentsNumber);
		Assert.assertEquals(institutionInfo.get(4), formattedEnrolledStudentsNumber);

        int studentsNumber = course.getStudentsNumber();
        String formattedStudentsNumber = String.format("%d", studentsNumber);
        Assert.assertEquals(institutionInfo.get(5), formattedStudentsNumber);
	}

	// Test if the Course info has null University's info
	public void testInstitutionInfoIndexOutOfBounds() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		Institution firstInstitution = controller.searchInstitution(2);
		controller.searchCourse(1, "DF");
        new Course(1, 2, "UNIVERSIDADE DE BRASILIA", 141, 89, "BRASILIA", (float) 3.735,
                "DF", firstInstitution);

        List<String> institutionInfo = null;

		try
		{
			institutionInfo = controller.getInstitutionInfo(9893);
		}
		catch (Error e)
		{
			Log.e(this.getClass().toString(), "Error on getting out of index institution info: " + e);
		}

		Assert.assertNull(institutionInfo);
	}

	/* Test if the comparison between two different Universities
	 * (with two different Brazilian States) was successful */
	public void testCompareState() throws Exception
    {
		CourseController controller = new CourseController(getContext());

		String firstState = "DF";
        String secondState = "AM";

        List<Float> statesGrades = controller.compareState(firstState, secondState, 1);
        float firstStateGrade = statesGrades.get(0);

		assertEquals(firstStateGrade, (float) 1.9448332);
	}

	/* Test if the Course's ID is registered correctly on the University's name on the Database */
	public void testSearchCourseCode()
	{
		int courseCode;
		CourseController controller = new CourseController(getContext());

        courseCode = controller.searchCourseCode("Administracao");
		Assert.assertEquals(1, courseCode);
	}

	/* Test if three different Universities (on the same Brazilian State)
	 * with the same course info are registered correctly
	 * based on Course's ID and University's Brazilian State on the Database */
	public void testSearchCourseUniversityID()
	{
		CourseController controller = new CourseController(getContext());
		ArrayList<Course> secondCoursesList = new ArrayList<Course>();
		ArrayList<Course> firstCoursesList = new ArrayList<Course>();

		Institution firstInstitution = new Institution("FACULDADE BARAO DO RIO BRANCO",
		                                               "FACULDADES", "PRIVADA", 2132);

		Institution secondInstitution = new Institution("FACULDDE DA AMAZONIA OCIDENTAL",
		                                                "FACULDADES", "PRIVADA", 2343);

		Institution thirdInstitution = new Institution("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
		                                               "FACULDADES", "PRIVADA", 2072);

		Course firstCourse = new Course(1, 2072, "ADMINISTRACAO", 29, 26, "CRUZEIRO DO SUL",
		                                (float) 0.785, "AC", thirdInstitution);

		Course secondCourse = new Course(1, 2132, "ADMINISTRACAO", 147, 125, "RIO BRANCO",
		                                 (float) 1.605, "AC", firstInstitution);

		Course thirdCourse = new Course(1, 2343, "ADMINISTRACAO", 49, 48, "RIO BRANCO",
		                                (float) 1.432, "AC", secondInstitution);

		secondCoursesList.add(secondCourse);
		secondCoursesList.add(thirdCourse);
		secondCoursesList.add(firstCourse);

		firstCoursesList = controller.searchCourse(1, "AC");

        Course baseCourse = secondCoursesList.get(0);
        Course compareCourse = firstCoursesList.get(0);

        float secondCourseGrade = baseCourse.getCourseGrade();
        float firstCourseGrade = compareCourse.getCourseGrade();
		Assert.assertEquals(secondCourseGrade, firstCourseGrade);

        int secondCourseId = baseCourse.getId();
        int firstCourseId = compareCourse.getId();
		Assert.assertEquals(secondCourseId, firstCourseId);

        int secondCourseInstitutionId = baseCourse.getId_institution();
        int firstCourseInstitutionId = compareCourse.getId_institution();
		Assert.assertEquals(secondCourseInstitutionId, firstCourseInstitutionId);

        int secondCourseStudentsNumber = baseCourse.getStudentsNumber();
        int firstCourseStudentsNumber = compareCourse.getStudentsNumber();
		Assert.assertEquals(secondCourseStudentsNumber, firstCourseStudentsNumber);

        Course secondBaseCourse = secondCoursesList.get(1);
        Course secondCompareCourse = firstCoursesList.get(1);

        secondCourseGrade = secondBaseCourse.getCourseGrade();
        firstCourseGrade = secondCompareCourse.getCourseGrade();
		Assert.assertEquals(secondCourseGrade, firstCourseGrade);


        secondCourseId = secondBaseCourse.getId();
        firstCourseId = secondCompareCourse.getId();
        Assert.assertEquals(secondCourseId, firstCourseId);

        secondCourseInstitutionId = secondBaseCourse.getId_institution();
        firstCourseInstitutionId = secondCompareCourse.getId_institution();
        Assert.assertEquals(secondCourseInstitutionId, firstCourseInstitutionId);

        secondCourseStudentsNumber = secondBaseCourse.getStudentsNumber();
        firstCourseStudentsNumber = secondCompareCourse.getStudentsNumber();
        Assert.assertEquals(secondCourseStudentsNumber, firstCourseStudentsNumber);

        Course thirdBaseCourse = secondCoursesList.get(2);
        Course thirdCompareCourse = firstCoursesList.get(2);

        secondCourseGrade = thirdBaseCourse.getCourseGrade();
        firstCourseGrade = thirdCompareCourse.getCourseGrade();
        Assert.assertEquals(secondCourseGrade, firstCourseGrade);


        secondCourseId = thirdBaseCourse.getId();
        firstCourseId = thirdCompareCourse.getId();
        Assert.assertEquals(secondCourseId, firstCourseId);

        secondCourseInstitutionId = thirdBaseCourse.getId_institution();
        firstCourseInstitutionId = thirdCompareCourse.getId_institution();
        Assert.assertEquals(secondCourseInstitutionId, firstCourseInstitutionId);

        secondCourseStudentsNumber = thirdBaseCourse.getStudentsNumber();
        firstCourseStudentsNumber = thirdCompareCourse.getStudentsNumber();
        Assert.assertEquals(secondCourseStudentsNumber, firstCourseStudentsNumber);
	}

	/* Test if one University with one course info
	 * is registered correctly based on Course's ID, University's Brazilian
	 * State and University's name on the Database */
	public void testSearchCourseID()
    {
        CourseController controller = new CourseController(getContext());
        ArrayList<Course> secondCoursesList = new ArrayList<Course>();
        ArrayList<Course> firstCoursesList = new ArrayList<Course>();

        Institution thirdInstitution = new Institution("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
                                                       "FACULDADES", "PRIVADA", 2072);

        Course firstCourse = new Course(1, 2072, "ADMINISTRACAO", 29, 26, "CRUZEIRO DO SUL",
                                        (float) 0.785, "AC", thirdInstitution);

        secondCoursesList.add(firstCourse);

        this.courses = controller.searchCourse(1, "AC", "CRUZEIRO DO SUL");

        float secondCourseGrade = secondCoursesList.get(0).getCourseGrade();
        float firstCourseGrade = firstCoursesList.get(0).getCourseGrade();
        Assert.assertEquals(secondCourseGrade, firstCourseGrade);

        int secondCourseId = secondCoursesList.get(0).getId();
        int firstCourseId = firstCoursesList.get(0).getId();
        Assert.assertEquals(secondCourseId, firstCourseId);

        int secondCourseInstitutionId = secondCoursesList.get(0).getId_institution();
        int firstCourseInstitutionId = firstCoursesList.get(0).getId_institution();
        Assert.assertEquals(secondCourseInstitutionId, firstCourseInstitutionId);

        int secondCourseStudentsNumber = secondCoursesList.get(0).getStudentsNumber();
        int firstCourseStudentsNumber = firstCoursesList.get(0).getStudentsNumber();
        Assert.assertEquals(secondCourseStudentsNumber, firstCourseStudentsNumber);
    }

	/* Test if one University with one course info
	 * is registered correctly based on Course's ID, University's Brazilian
	 * State, University's City and University's Type on the Database */
	public void testSearchCourseInfo()
	{
		CourseController controller = new CourseController(getContext());
		ArrayList<Course> secondCoursesList = new ArrayList<Course>();
		ArrayList<Course> firstCoursesList = new ArrayList<Course>();

		Institution firstInstitution = new Institution("UNIVERSIDADE DE BRASILIA", "UNIVERSIDADES",
		                                               "PUBLICA", 2);

		Course firstCourse = new Course(1, 2, "ADMINISTRACAO", 141, 89, "BRASILIA", (float) 3.735,
		                                "DF", firstInstitution);

		secondCoursesList.add(firstCourse);

		firstCoursesList = controller.searchCourse(1, "DF", "BRASILIA", "PUBLICA");

        float secondCourseGrade = secondCoursesList.get(0).getCourseGrade();
        float firstCourseGrade = firstCoursesList.get(0).getCourseGrade();
        Assert.assertEquals(secondCourseGrade, firstCourseGrade);

        int secondCourseId = secondCoursesList.get(0).getId();
        int firstCourseId = firstCoursesList.get(0).getId();
        Assert.assertEquals(secondCourseId, firstCourseId);

        int secondCourseInstitutionId = secondCoursesList.get(0).getId_institution();
        int firstCourseInstitutionId = firstCoursesList.get(0).getId_institution();
        Assert.assertEquals(secondCourseInstitutionId, firstCourseInstitutionId);

        int secondCourseStudentsNumber = secondCoursesList.get(0).getStudentsNumber();
        int firstCourseStudentsNumber = firstCoursesList.get(0).getStudentsNumber();
        Assert.assertEquals(secondCourseStudentsNumber, firstCourseStudentsNumber);
	}

	/* Test if two different Brazilian Cities (in the same State)
	 * is registered correctly based on Course's ID and State on the Database */
	public void testSearchCities()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondCitiesList = new ArrayList<String>();
		List<String> firstCitiesList;

		String firstCity = "CRUZEIRO DO SUL";
		String secondCity = "RIO BRANCO";

		secondCitiesList.add(firstCity);
		secondCitiesList.add(secondCity);

		firstCitiesList = controller.searchCities(1, "AC");
		Assert.assertEquals(secondCitiesList, firstCitiesList);
	}

	/* Test if the Acre's University Type is Private, based on the Course's ID and the University's Type */
	public void testSearchTypeAC()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList;

		String firstType = "PRIVADA";

		secondTypeList.add(firstType);

		firstTypeList = controller.searchTypes(1, "RIO BRANCO");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* Test Brasilia's three Universities Types (Public, Private and "Both")
	 * based on the Course's ID and the University's Type */
	public void testSearchTypeDF()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList;

		String firstType =  "Ambas";
		String secondType = "PRIVADA";
		String thirdType =  "PUBLICA";

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
		List<String> firstTypeList;

		String secondType = "PRIVADA";
		String thirdType = "PUBLICA";

		secondTypeList.add(secondType);
		secondTypeList.add(thirdType);

		firstTypeList = controller.searchStateTypes(1, "DF");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test if the Acre's University Type is
	 * Private, based on the Course's ID and the University's Brazilian State */
	public void testSearchTypeStateAC()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondTypeList = new ArrayList<String>();
		List<String> firstTypeList;

		String firstType = "PRIVADA";

		secondTypeList.add(firstType);

		firstTypeList = controller.searchStateTypes(1, "AC");
		Assert.assertEquals(secondTypeList, firstTypeList);
	}

	/* This method is responsible to test if all the Brazilian States are
	 * registered correctly on the Database */
	public void testSearchState()
	{
		CourseController controller = new CourseController(getContext());
		List<String> secondStateList = new ArrayList<String>();
		List<String> firstStateList;

		String firstState = "AL";
		String secondState = "AM";
		String thirdState = "AP";
		String fourthState = "BA";
		String fifthState = "CE";
		String sixthState = "DF";
		String seventhState = "GO";
		String eighthState = "MA";
		String ninthState = "MG";
		String tenthState = "MS";
		String eleventhState = "MT";
		String twelfthState = "PA";
		String thirteenthState = "PB";
		String fourteenthState = "PE";
		String fifteenthState = "PI";
		String sixteenthState = "PR";
		String seventeenthState = "RJ";
		String eighteenthState = "RN";
		String nineteenthState = "RR";
		String twentiethState = "RS";
		String twentyFirstState = "SC";
		String twentySecondState = "SE";
		String twentyThirdState = "SP";

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
	public void testSearchCourseCodeState() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList;

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

		firstCourseList = controller.searchCoursesNames(1, "TO");

        final int numberInstitution = 14; // Indicates the number of institutions added to the array

		for (int i = 0; i < numberInstitution; i++)
		{
            String secondCourse = secondCourseList.get(i);
            String firstCourse = firstCourseList.get(i);

			Assert.assertEquals(secondCourse, firstCourse);
		}
	}

	/* Test if two Universities' names together with their ENADE's grades are registered correctly,
	 * based on the Courses' ID, State, City and University Type, on the Database */
	public void testSearchStateCityType() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList;

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2.468000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1.366000");

		firstCourseList = controller.searchCoursesNames(1, "TO", "PALMAS", "PUBLICA");

        final int numberInstitution = 2; // Indicates the number of institutions added to the array

		for (int i = 1; i < numberInstitution; i++)
		{
            String secondCourse = secondCourseList.get(i);
            String firstCourse = firstCourseList.get(i);

            Assert.assertEquals(secondCourse, firstCourse);
		}
	}

	/* Test if seven Universities' names together with their respectives ENADE's grades
	 * are registered correctly on the Database based on the Courses' ID, State and City */
	public void testSearchStateCity() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList;

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2.468000");
		secondCourseList.add("CENTRO UNIVERSITARIO LUTERANO DE PALMAS - 2.185000");
		secondCourseList.add("INSTITUTO DE ENSINO E PESQUISA OBJETIVO - 2.185000");
		secondCourseList.add("FACULDADE SERRA DO CARMO - 1.443000");
		secondCourseList.add("FACULDADE CATOLICA DO TOCANTINS - 1.433000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1.366000");
		secondCourseList.add("FACULDADE ITOP - 1.202000");

		firstCourseList = controller.searchCoursesNames(1, "TO", "PALMAS");

        final int numberInstitution = 7; // Indicates the number of institutions added to the array

		for (int i = 1; i < numberInstitution; i++)
		{
            String secondCourse = secondCourseList.get(i);
            String firstCourse = firstCourseList.get(i);

            Assert.assertEquals(secondCourse, firstCourse);
		}
	}

	/* Test if five Universities' names together with their respectives ENADE's grades are
	 * registered correctly on the Database, based on the Courses' ID, State and type */
	public void testSearchStateIntegerType() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		List<String> secondCourseList = new ArrayList<String>();
		List<String> firstCourseList;

		secondCourseList.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2.468000");
		secondCourseList.add("CENTRO UNIVERSITARIO UNIRG - 2.290000");
		secondCourseList.add("FACULDADE INTEGRADA DE ARAGUATINS - 2.103000");
		secondCourseList.add("FACULDADE GUARAI - 2.014000");
		secondCourseList.add("UNIVERSIDADE DO TOCANTINS - 1.366000");

		firstCourseList = controller.searchCoursesNames(1, "TO", 2);

        final int numberInstitution = 5; // Indicates the number of institutions added to the array

		for (int i = 1; i < numberInstitution; i++)
		{
            String secondCourse = secondCourseList.get(i);
            String firstCourse = firstCourseList.get(i);

            Assert.assertEquals(secondCourse, firstCourse);
		}
	}

	// Test if database return the correct course and institution code
	public void testSearchInstitutionCode()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(2, "DF");

        int institutionCode = controller.getInstitutionCode(0);
		Assert.assertEquals(2, institutionCode);
	}

    // Test if database return the correct course code and grade
	public void testCoursesCodes()
	{
		CourseController controller = new CourseController(getContext());

		controller.searchCourse(2, "DF");

        float courseGrade = controller.getCourseGrade(0);
		Assert.assertEquals((float) 4.482, courseGrade);
	}

	/* Test if two Universities's from two Cities (from two Brazilian States) aren't the same.
	 * It ensures that they're from different Cities and State so that you can make comparisons
	 * between them */
	public void testCompareCity() throws Exception
    {
		CourseController controller = new CourseController(getContext());

		String firstState = "DF";
        String firstCity = "BRASILIA";

		String secondState = "AM";
        String secondCity = "MANAUS";

        List<Float> citiesGrades = controller.compareCity(1, firstState, firstCity, secondState, secondCity);
        float firstCityGrade = citiesGrades.get(0);
		assertNotSame(firstCityGrade, "1.900000");
	}

	/* Test if the Universities Types based on Course's ID, States and Types,
	 * have different ENADE's grades average, on the Database */
	public void testCompareType() throws Exception
    {
		CourseController controller = new CourseController(getContext());
		List<Float> typeResult = controller.compareType(1, "AC", "Privada", "AL", "Privada");

        float firstTypeGrade = typeResult.get(0);
		Assert.assertEquals((float) 1.5185001, firstTypeGrade);

        float secondTypeGrade = typeResult.get(1);
		Assert.assertEquals((float) 2.285000, secondTypeGrade);
	}

	// Test the Brazilian State's ENADE grade average, based on State and Course's ID
	public void testStateAverageGrade() throws Exception
    {
		CourseController controller = new CourseController(getContext());

        float stateGrade = controller.calculateStateGrade("SP", 1);
		Assert.assertEquals((float) 2.249236, stateGrade);
	}
}