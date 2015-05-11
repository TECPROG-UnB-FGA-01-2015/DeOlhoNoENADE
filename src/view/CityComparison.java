/************************************************************************
 * File: CityComparison.java
 * Purpose: Shows a list of cities to be compared. User should
 * 			select two cities to compare the city's institutions grades
 ************************************************************************/

package view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import controller.CourseController;

import org.apache.log4j.Logger;

public class CityComparison extends Activity
{
	private String courseName; // Holds the name of the course being compared
	private int courseCode; // Holds the code of the course being compared
	private Spinner firstStateSpinner; // Spinner variable of the first State
	private Spinner secondStateSpinner; // Spinner variable of the second State
	private Spinner firstCitySpinner;  // Spinner variable of the first City
	private Spinner secondCitySpinner; // Spinner variable of the second City
	private String firstStateName; // Holds the name of the first State
	private String secondStateName; // Holds the name of the second State
	private String firstCityName; // Holds the name of the first City
	private String secondCityName; // Holds the name of the second City
	private CourseController objectCourseController; // Instantiates the controller
	static Logger log = Logger.getLogger(CityComparison.class.getName());
	
	// Const to input selected navigation item
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    // Method to initialize the activity activity_comparacao_cidades
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_cidades);

		objectCourseController = new CourseController(this);
		
		// Receives the selected course of the search by id
		TextView selectedCourse = (TextView) findViewById(R.id.stringCurso1);
		
		selectedCourse.setText(getIntent().getExtras().getString("selectedCourse"));
		courseName = getIntent().getExtras().getString("selectedCourse");

		this.courseCode = objectCourseController.searchCourseCode(courseName);
		addItemsOnFirstStateSpinner(courseCode);
		addItemsOnSecondStateSpinner(courseCode);
		addListenerOnButtonComparar();
	}

    // Method to list the First State options in a spinner
	private void addItemsOnFirstStateSpinner(int courseCode)
	{
		firstStateSpinner = (Spinner) findViewById(R.id.spinnerEstado1);
		
		// Stores all states of a given course
		List<String> ufNameList = new ArrayList<String>();

		ufNameList = objectCourseController.searchState(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	ufNameList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		firstStateSpinner.setAdapter(dataAdapter);

		firstStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{
				// Gets the name by the position
				firstStateName = parent.getItemAtPosition(posicao).toString();
				addItemsOnFirstCitySpinner(firstStateName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}
    
    // Method to list the Second State options in a spinner
	private void addItemsOnSecondStateSpinner(int courseCode)
	{
		secondStateSpinner = (Spinner) findViewById(R.id.spinnerEstado2);

		// Stores all states of a given course
		List<String> ufNameList = new ArrayList<String>();
		
		ufNameList = objectCourseController.searchState(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	ufNameList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secondStateSpinner.setAdapter(dataAdapter);
		secondStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{
				// Gets the name by the position
				secondStateName = parent.getItemAtPosition(posicao).toString();
				addItemsOnSecondCitySpinner(secondStateName, firstCityName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}
    
    // Method to list the First City options in a spinner
	private void addItemsOnFirstCitySpinner(String uf)
	{
		this.firstCitySpinner = (Spinner) findViewById(R.id.spinnerCidade1);

		// Stores all cities of a given course
		List<String> cityNameList = new ArrayList<String>();
		
		cityNameList = objectCourseController.searchCities(courseCode, uf);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	cityNameList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.firstCitySpinner.setAdapter(dataAdapter);

		this.firstCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{
				firstCityName = parent.getItemAtPosition(posicao).toString();

				addItemsOnSecondCitySpinner(secondStateName, firstCityName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}
    
    // Method to list the Second City options in a spinner
	private void addItemsOnSecondCitySpinner(String uf, String cidade)
	{
		this.secondCitySpinner = (Spinner) findViewById(R.id.spinnerCidade2);

		// Stores all cities of a given course
		List<String> cityNameList = new ArrayList<String>();
		
		cityNameList = objectCourseController.searchCities(courseCode, uf);
		cityNameList.remove(cidade);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	cityNameList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.secondCitySpinner.setAdapter(dataAdapter);

		this.secondCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{
				secondCityName = parent.getItemAtPosition(posicao).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}
    
    // Method for the confirmation button for the comparison between the two states
	private void addListenerOnButtonComparar()
	{
		Button compareInstituicao = (Button) findViewById(R.id.buttonComparacaoCidades);
		compareInstituicao.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CityComparison.this, CityResultComparison.class);
				intent.putExtra("selectedCourse", courseName);
				intent.putExtra("firstStateName", firstStateName);
				intent.putExtra("secondStateName", secondStateName);
				intent.putExtra("firstCityName", firstCityName);
				intent.putExtra("secondCityName", secondCityName);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
		{
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
		
		else
		{
			// Nothing to do
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_cidades, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		
		if (id == R.id.action_settings)
		{
			return true;
		}
		
		else
		{
			// Nothing to do
		}
		
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment
	{

		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment()
		{
			// Nothing to do
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_cidades, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
