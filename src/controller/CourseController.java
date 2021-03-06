/***********************************************************
 * File: CourseController.java
 * Purpose: Responsible to get the Universities' and Courses' info
 * 			to be listed and compared to each other.
 ***********************************************************/

package controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import DAO.ImportarBancoDeDados;
import DAO.OperacoesBancoDeDados;
import model.Course;
import model.Institution;

public class CourseController
{
    private ArrayList<Course> courses = new ArrayList<Course>(); // Describes a list of courses

	private SQLiteDatabase database; // Describes the database of this class
	private final OperacoesBancoDeDados databaseOperations; // Describes the operations of the database

	// This method is responsible to import two DAO's classes to make Database's operations
	public CourseController(Context context)
	{
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(context);

		SQLiteDatabase database = bdados.openDataBase();

		this.databaseOperations = new OperacoesBancoDeDados(database);

		this.database = database;
	}

    // Access and returns the property database
	public SQLiteDatabase getDatabase()
	{
		return this.database;
	}

    // Removes an institution of the list
	public boolean removeInstitution(int position)
	{
		try
		{
			courses.remove(position);
            Log.i("Course removed!", CourseController.class.getName());
			return true;
		}
		catch (IndexOutOfBoundsException e)
		{
			Log.e(this.getClass().toString(), "courses IndexOutOfBounds, returning false");
			return false;
		}
	}

    // Access and returns the property institutionCode
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

    // Access and returns the property name
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

    // Search for an institution in the database with a given institutionCode
	public Institution searchInstitution(int institutionCode)
	{
		try
		{
            Institution institution = this.databaseOperations.getIES(institutionCode);

			return institution;
		}
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error on searching institution. Exception: ", e);
		}
        return null;
    }

    // Access and returns the property institutionInfo
	public List<String> getInstitutionInfo(int position) throws Exception
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(),"Error on getting institution information. Exception: ", e);
		}
		return institutionInfo;

	}

	/** This method is responsible to compare two different Universities's ENADE
	 * grades from two different States with the Course's ID. It gets all the
	 * ENADES' grades from one specific course on two different States and
	 * calculates the ENADE's average grades on these two different chosen
	 * States */
	public List<Float> compareState(String firstState, String secondState, int courseCode) throws Exception
    {
		float stateGrade = 0;
		List<Float> compareStateResult = new ArrayList<Float>();
		List<Course> firstCourseState = new ArrayList<Course>();
		List<Course> secondCourseState;

		firstCourseState = this.searchCourse(courseCode, firstState);
		secondCourseState = this.searchCourse(courseCode, secondState);

		stateGrade = this.calculateEnadeGrade(firstCourseState);
		compareStateResult.add(stateGrade);

		stateGrade = this.calculateEnadeGrade(secondCourseState);
		compareStateResult.add(stateGrade);
		
		Log.i(this.getClass().toString(), "Comparison between states '" + firstState + "' and '" +  secondState + "' was sucessfully made");

		return compareStateResult;
	}

	/* This method is responsible to compare two different Universities's ENADE
	 * grades from two different Cities (from two different States) with the
	 * Course's ID. It gets all the ENADES' grades from one specific course on
	 * two different Cities and calculates the ENADE's average grades on these
	 * two different chosen Cities */
	public List<Float> compareCity(int courseCode, String firstState, String firstCity, String secondState, String secondCity) throws Exception
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
		
		Log.i(this.getClass().toString(), "Comparison between cities '" + firstCity + "' and '" +  secondCity + "' was sucessfully made");

		return compareCityResult;
	}

	/* This method is responsible to compare two different (with different
	 * Universities Types - Public and Private) Universities's ENADE grades from
	 * two different States with the Course's ID. It gets all the ENADES' grades
	 * from one specific course on two different States and calculates the
	 * ENADE's average grades on these two different chosen States */
	public List<Float> compareType(int courseCode, String firstState, String firstType, String secondState, String secondType) throws Exception {
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
		
		Log.i(this.getClass().toString(), "Comparison between types '" + firstType + "' and '" +  secondType + "' was sucessfully made");

		return compareTypeResult;
	}

	// This method is responsible to calculate the ENADE's grades average from the Courses' array
	public float calculateEnadeGrade(List<Course> courses) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when calculating average grade of courses");
		}
        return -1;
	}

	// This method is responsible to search the Courses' IDs with the Courses' names from the Database
	public int searchCourseCode(String courseName)
	{
		int courseCode = this.databaseOperations.getCodCurso(courseName);
		
		if (courseCode > 0)
		{
			Log.d(this.getClass().toString(), "Course code of course '" + courseName + "' was found");
		}
		else
		{
			Log.e(this.getClass().toString(),"Course code of course '" + courseName + "' was not found");
		}

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
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions of the course code " + Integer.toString(secondCourseCode));
		}
        return null;
    }

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs and Universities' Brazilian States. This method lists all
	 * the Universities' names (with different Brazilian States chosen) with
	 * their respectives ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions' grades of the course code " + Integer.toString(secondCourseCode));
		}
        return null;
    }

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States, Universities' Brazilian
	 * Cities and Universities' Types. This method lists all the Universities'
	 * names (with different Brazilian States, Brazilian Cities and Types
	 * chosen) with their respectives ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState, String secondCity, String secondType) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions of the course code " + Integer.toString(secondCourseCode));
		}
        return null;
    }

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States and Universities' Brazilian
	 * Cities. This method lists all the Universities' names (with different
	 * Brazilian States and Brazilian Cities chosen) with their respectives
	 * ENADE' grades */
	public List<String> searchCoursesNames(int secondCourseCode, String secondState, String secondCity) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions of the course code " + Integer.toString(secondCourseCode) + " from the city '" + secondCity + "'.");
		}
        return null;
    }

	/* This method is responsible to search the Universities' names with
	 * Universities' Brazilian States, Courses' IDs and Universities' Brazilian
	 * Cities. This method lists all the Courses' names (with Brazilian States
	 * and Brazilian Cities chosen) */
	public List<String> searchCoursesNames(String secondState, int secondCourseCode, String secondCity) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions of the course code " + Integer.toString(secondCourseCode) + " from the city '" + secondCity + "'.");
		}
        return null;
    }

	/* This method is responsible to search the Universities' names with
	 * Courses' IDs, Universities' Brazilian States and Universities' Types.
	 * This method lists all the Universities' names (with different Brazilian
	 * States and Types chosen) with their respectives ENADE' grades */
	public List<String> searchCoursesNames(int courseCode, String state, int integerType) throws Exception
	{
		try
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
		catch(Exception e)
		{
			Log.e(this.getClass().toString(), "Error when looking for institutions of the course code " + Integer.toString(courseCode) + " from the state '" + state + "'.");
		}
        return null;
    }

	/* This method is responsible to calculate the ENADE's grades average on
	 * each Brazilian State with the Universities' Brazilian States and Courses'
	 * IDs */
	public float calculateStateGrade(String state, int courseCode) throws Exception
	{
		List<Course> courses = new ArrayList<Course>();
		courses = searchCourse(courseCode, state);

        float averageGrade = calculateEnadeGrade(courses);
		
		return averageGrade;
	}
}
