/***********************************************************
 * File: CourseController.java
 * Purpose: Responsible to get the Universities' and Courses' info
 * 			to be listed and compared to each other.
 ***********************************************************/

package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import DAO.ImportarBancoDeDados;
import DAO.OperacoesBancoDeDados;
import model.Course;
import model.Institution;

public class CourseController
{
	private Institution institution;
	private ArrayList<Course> courses = new ArrayList<Course>();

	private SQLiteDatabase database;
	private OperacoesBancoDeDados databaseOperations;

	// This method is responsible to import two DAO's classes to make Database's operations
	public CourseController(Context context)
	{
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(context);

		SQLiteDatabase database = bdados.openDataBase();

		this.databaseOperations = new OperacoesBancoDeDados(database);

		this.database = database;
	}

	// This method is responsible to get the Database's object
	public SQLiteDatabase getDatabase()
	{
		return this.database;
	}

	/* This method is responsible to block the University's choice twice on the
	 * Comparison Functionality. This method removes the University's position
	 * on the Drop List when you're going to compare 2 different Universities */
	public boolean removeInstitution(int position)
	{
		try
		{
			courses.remove(position);
			return true;
		}
		catch (IndexOutOfBoundsException e)
		{
			Log.e(this.getClass().toString(), "courses IndexOutOfBounds, returning false");
			return false;
		}
	}

	// This method is responsible to get each University's ID (position) on the Courses' array
	public int getInstitutionCode(int position)
	{
		try
		{
            int institutionCode = courses.get(position).getIES().getInstitutionCode();
			return institutionCode;
		}
		catch (IndexOutOfBoundsException e)
		{
			Log.e(this.getClass().toString(), "courses IndexOutOfBounds, returning -1");
			return -1;
		}
	}

	/* This method is responsible to get the "ENADE"'s Students grades of one
	 * specific Course. ENADE (Brazilian word which means - National Evaluation
	 * of Student Performance) is a Government's test which calculates (with
	 * numbers from 0 - bad - to 5 - great) how good are the Student's grades
	 * from one of the chosen Courses It's a very important grade because you
	 * can see the differences between the good Universities from the bad
	 * Universities	*/
	public float getCourseGrade(int position)
	{
		try
		{
            float courseGrade = courses.get(position).getCourseGrade();
			return courseGrade;
		}
		catch (IndexOutOfBoundsException e)
		{
			Log.e(this.getClass().toString(), "courses IndexOutOfBounds, returning -1");
			return -1;
		}
	}

	// This method is responsible to search the Universities' IDs from the Database
	public Institution searchInstitution(int institutionCode)
	{
		this.institution = this.databaseOperations.getIES(institutionCode);

		return institution;
	}

	/* This method is responsible to get all the Universities' info (University
	 * Name, Academic Organization, Type - Public/Private Universities,
	 * University City, Registered Students - who participated on the ENADE's
	 * test - and Course's number of students) */
	public List<String> getInstitutionInfo(int position)
	{
		List<String> institutionInfo = new ArrayList<String>();
		try
		{
            String institutionName = courses.get(position).getIES().getName();
            institutionInfo.add(institutionName);

            String academicOrganization = courses.get(position).getIES().getAcademicOrganization();
            institutionInfo.add(academicOrganization);

            String institutionType = courses.get(position).getIES().getType();
            institutionInfo.add(institutionType);

            String courseCity = courses.get(position).getCity();
            institutionInfo.add(courseCity);

            int enrolledStudentsNumber = courses.get(position).getEnrolledStudentsNumber();
            String formattedEnrolledStudentsNumber = String.format("%d", enrolledStudentsNumber);
            institutionInfo.add(formattedEnrolledStudentsNumber);

            int studentsNumber = courses.get(position).getStudentsNumber();
            String formattedStudentsNumber = String.format("%d", studentsNumber);
			institutionInfo.add(formattedStudentsNumber);
		}
		catch (IndexOutOfBoundsException e)
		{
			Log.e(this.getClass().toString(), "courses IndexOutOfBounds");
			throw new Error("Instituicao inexistente nessa posicao");
		}
		return institutionInfo;

	}

	/** This method is responsible to compare two different Universities's ENADE
	 * grades from two different States with the Course's ID. It gets all the
	 * ENADES' grades from one specific course on two different States and
	 * calculates the ENADE's average grades on these two different chosen
	 * States */
	public List<Float> compareState(String firstState, String secondState, int courseCode)
	{
		float stateGrade = 0;
		List<Float> compareStateResult = new ArrayList<Float>();
		List<Course> firstCourseState = new ArrayList<Course>();
		List<Course> secondCourseState = new ArrayList<Course>();

		firstCourseState = this.searchCourse(courseCode, firstState);
		secondCourseState = this.searchCourse(courseCode, secondState);

		stateGrade = this.calculateEnadeGrade(firstCourseState);
		compareStateResult.add(stateGrade);

		stateGrade = this.calculateEnadeGrade(secondCourseState);
		compareStateResult.add(stateGrade);

		return compareStateResult;
	}

	/* This method is responsible to compare two different Universities's ENADE
	 * grades from two different Cities (from two different States) with the
	 * Course's ID. It gets all the ENADES' grades from one specific course on
	 * two different Cities and calculates the ENADE's average grades on these
	 * two different chosen Cities */
	public List<Float> compareCity(int courseCode, String firstState, String firstCity, String secondState, String secondCity)
	{
		float cityGrade = 0;
		List<Float> compareCityResult = new ArrayList<Float>();
		List<Course> firstCourseCity = new ArrayList<Course>();
		List<Course> secondCourseCity = new ArrayList<Course>();

		firstCourseCity = this.searchCourse(courseCode, firstState, firstCity);
		secondCourseCity = this.searchCourse(courseCode, secondState, secondCity);

		cityGrade = this.calculateEnadeGrade(firstCourseCity);
		compareCityResult.add(cityGrade);

		cityGrade = this.calculateEnadeGrade(secondCourseCity);
		compareCityResult.add(cityGrade);

		return compareCityResult;
	}

	/* This method is responsible to compare two different (with different
	 * Universities Types - Public and Private) Universities's ENADE grades from
	 * two different States with the Course's ID. It gets all the ENADES' grades
	 * from one specific course on two different States and calculates the
	 * ENADE's average grades on these two different chosen States */
	public List<Float> compareType(int courseCode, String firstState, String firstType, String secondState, String secondType)
	{
		float typeGrade = 0;
		List<Float> compareTypeResult = new ArrayList<Float>();
		List<Course> firstStateType = new ArrayList<Course>();
		List<Course> secondStateType = new ArrayList<Course>();

        final int TYPE_UNIVERSITY_PRIVATE = 1;
        final int TYPE_UNIVERSITY_PUBLIC = 2;

		if (firstType.equalsIgnoreCase("Privada"))
		{
			firstStateType = this.searchCourse(courseCode, firstState, TYPE_UNIVERSITY_PRIVATE);
		}
		else if (firstType.equalsIgnoreCase("Publica"))
		{
			firstStateType = this.searchCourse(courseCode, firstState, TYPE_UNIVERSITY_PUBLIC);
		}
        else
        {
            // Nothing to do
        }

		if (secondType.equalsIgnoreCase("Privada"))
		{
			secondStateType = this.searchCourse(courseCode, secondState, TYPE_UNIVERSITY_PRIVATE);
		}
		else if (secondType.equalsIgnoreCase("Publica"))
		{
			secondStateType = this.searchCourse(courseCode, secondState, TYPE_UNIVERSITY_PUBLIC);
		}
        else
        {
            // Nothing to do
        }

		typeGrade = this.calculateEnadeGrade(firstStateType);
		compareTypeResult.add(typeGrade);

		typeGrade = this.calculateEnadeGrade(secondStateType);
		compareTypeResult.add(typeGrade);

		return compareTypeResult;
	}

	// This method is responsible to calculate the ENADE's grades average from the Courses' array
	public float calculateEnadeGrade(List<Course> courses)
	{
		float courseGrade = 0;

		if (courses.size() == 1)
		{
			courseGrade = courses.get(0).getCourseGrade();
		}
		else
		{
            int i;
			for (i = 0; i < courses.size() - 1; i++)
			{
				courseGrade += courses.get(i).getCourseGrade();
			}

			courseGrade = courseGrade / i;
		}

		return courseGrade;
	}

	// This method is responsible to search the Courses' IDs with the Courses' names from the Database
	public int searchCourseCode(String courseName)
	{
		int courseCode = this.databaseOperations.getCodCurso(courseName);

		return courseCode;
	}

	/* This method is responsible to search the Courses' names with the Courses'
	 * IDs and Universities' States from the Database */
	public ArrayList<Course> searchCourse(int courseCode, String state)
	{
		this.courses = this.databaseOperations.getCourses(courseCode, state);

		return courses;
	}

	/* This method is responsible to search the Courses' names with the Courses'
	 * IDs, Universities' States and Universities' Types from the Database */
	private List<Course> searchCourse(int courseCode, String state, int integerUniversityType)
	{
		this.courses = this.databaseOperations.getCursos(courseCode, state, integerUniversityType);

		return courses;
	}

	/* This method is responsible to search the Courses' names with the Courses'
	 * IDs, Universities' States and Universities' Cities from the Database */
	public ArrayList<Course> searchCourse(int courseCode, String state, String city)
	{
		this.courses = this.databaseOperations.getCursos(courseCode, state, city);

		return courses;
	}

	/* This method is responsible to search the Courses' names with the Courses'
	 * IDs, Universities' States, Universities' Cities and Universities' Types
	 * from the Database */
	public ArrayList<Course> searchCourse(int courseCode, String state,
	        String city, String type)
	{
		this.courses = this.databaseOperations.getCursos(courseCode, state, city, type);

		return courses;
	}

	/* This method is responsible to search the University's Cites with the
	 * Courses' IDs and Universities' States from the Database */
	public List<String> searchCities(int courseCode, String state)
	{
		List<String> cities = new ArrayList<String>();
		cities = this.databaseOperations.getCidades(courseCode, state);
		return cities;
	}

	/* This method is responsible to search the Universities' Types with the
	 * Courses' IDs and Universities' Cities from the Database */
	public List<String> searchTypes(int courseCode, String city)
	{
		List<String> types = new ArrayList<String>();
		types = this.databaseOperations.getTipoMunicipio(courseCode, city);
		return types;
	}

	/* This method is responsible to search the Universities' Types with the
	 * Courses' ID and Universities' States from the Database */
	public List<String> searchStateTypes(int courseCode, String state)
	{
		List<String> types = new ArrayList<String>();
		types = this.databaseOperations.getTipoEstado(courseCode, state);
		return types;
	}

	/* This method is responsible to search the Universities' States with the
	 * Courses' IDs from the Database */
	public List<String> searchState(int courseCode)
	{
		List<String> states = new ArrayList<String>();
		states = this.databaseOperations.getUfs(courseCode);
		return states;
	}

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States and Universities' Brazilian
	 * Cities. This method lists all the Universities' names on the screen */
	public List<String> searchCityInstitutions(int secondCourseCode, String secondState, String secondCity)
	{

		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(secondCourseCode, secondState, secondCity);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
            String formattedInstitutionName = String.format("%s", institutionName);
			courses.add(formattedInstitutionName);
		}

		return courses;
	}

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs and Universities' Brazilian States. This method lists all
	 * the Universities' names (with different Brazilian States chosen) with
	 * their respectives ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState)
	{
		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(secondCourseCode, secondState);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
            float courseGrade = coursesList.get(i).getCourseGrade();
            String formattedInstitutionCourse = String.format("%s - %f", institutionName, courseGrade);
			courses.add(formattedInstitutionCourse);
		}

		return courses;
	}

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States, Universities' Brazilian
	 * Cities and Universities' Types. This method lists all the Universities'
	 * names (with different Brazilian States, Brazilian Cities and Types
	 * chosen) with their respectives ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState, String secondCity, String secondType)
	{
		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(secondCourseCode, secondState, secondCity, secondType);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
            float courseGrade = coursesList.get(i).getCourseGrade();
            String formattedInstitutionCourse = String.format("%s - %f", institutionName, courseGrade);
            courses.add(formattedInstitutionCourse);
		}

		return courses;
	}

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States and Universities' Brazilian
	 * Cities. This method lists all the Universities' names (with different
	 * Brazilian States and Brazilian Cities chosen) with their respectives
	 * ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState, String secondCity)
	{
		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(secondCourseCode, secondState, secondCity);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
            float courseGrade = coursesList.get(i).getCourseGrade();
            String formattedInstitutionCourse = String.format("%s - %f", institutionName, courseGrade);
            courses.add(formattedInstitutionCourse);
		}

		return courses;
	}

	/* This method is responsible to search the Universities' names with
	 * Universities' Brazilian States, Courses' IDs and Universities' Brazilian
	 * Cities. This method lists all the Courses' names (with Brazilian States
	 * and Brazilian Cities chosen) */
	public List<String> searchCoursesNames(String secondState, int secondCourseCode, String secondCity)
	{
		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(secondCourseCode, secondState, secondCity);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
			courses.add(institutionName);
		}

		return courses;
	}

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States and Universities' Types.
	 * This method lists all the Universities' names (with different Brazilian
	 * States and Types chosen) with their respectives ENADE' grades */
	public List<String> searchCoursesNames(int courseCode, String state, int integerType)
	{
		List<String> courses = new ArrayList<String>();
		List<Course> coursesList = new ArrayList<Course>();

		coursesList = this.searchCourse(courseCode, state, integerType);

		for (int i = 0; i < coursesList.size(); i++)
		{
            String institutionName = coursesList.get(i).getIES().getName();
            float courseGrade = coursesList.get(i).getCourseGrade();
            String formattedInstitutionCourse = String.format("%s - %f", institutionName, courseGrade);
            courses.add(formattedInstitutionCourse);
		}

		return courses;
	}

	/* This method is responsible to calculate the ENADE's grades average on
	 * each Brazilian State with the Universities' Brazilian States and Courses'
	 * IDs */
	public float calculateStateGrade(String state, int courseCode)
	{
		List<Course> courses = new ArrayList<Course>();
		courses = searchCourse(courseCode, state);

		return calculateEnadeGrade(courses);
	}
}
