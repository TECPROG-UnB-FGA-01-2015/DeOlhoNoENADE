/*****************************************************************************
 * File: InstitutionComparison.java
 * Purpose: Shows a list of institution to be compared. User should
 * 			select the second institution to compare the ENADE institution grade
 ****************************************************************************/

package view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import controller.CourseController;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.os.Build;
import android.util.Log;
import java.util.logging.Logger;

public class FinalInstitutionComparison extends Activity
{

	private Spinner statesSpinner; // Describes the spinner of states
	private Spinner institutionSpinner; // Describes the spinner of institution
	private CourseController objectCourseController; // Describes the controller of courses
	private String stateName; // Describes the name of the state
	private String cityName; // Describes the name of the city
	private String secondInstitutionName; // Describes the name of the second institution being compared
	private String firstStateName; // Describes the name of the first state
	private String firstCityName; // Describes the name of the first city
	private String firstInstitutionName; // Describes the name of the first institution being compared
	private Spinner citiesSpinner; // Describes the spinner of cities
	private int courseCode; // Describes the code of the course
	private float firstInstitutionGrade; // Describes the grade of the first institution
	private float secondInstitutionGrade; // Describes the grade of the second institution
	private List<String> firstInstitutionInfo; // Receives information of the first institution to be compared
	private List<String> secondInstitutionInfo; // Receives information of the second institution to be compared
	private List<String> institutionList; // Receives a list of institutions
	private List<String> cityList; // Receives a list of cities
	private List<String> stateList; // Receives a list of states
	private int institutionPosition; // Describes the position of the institution on ENADE ranking

    @Override
    // Method to initialize the activity activity_comparacao_instituicao_final
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao_final);

		Log.d(this.getClass().toString(), "activity_comparacao_instituicao_final called!");

		this.objectCourseController = new CourseController(this);

		// Receives the selected course. Type = TextView
		TextView selectedCourse = (TextView) findViewById(R.id.cursoSelecionado);
		
		selectedCourse.setText(getIntent().getExtras().getString("selectedCourse"));

		courseCode = getIntent().getExtras().getInt("courseCode");
		firstInstitutionInfo = getIntent().getExtras().getStringArrayList("InstitutionInfo");
		firstStateName = getIntent().getExtras().getString("firstStateName");
		firstCityName = getIntent().getExtras().getString("firstCityName");
		firstInstitutionName = getIntent().getExtras().getString("firstInstitutionName");
		firstInstitutionGrade = getIntent().getExtras().getFloat("firstInstitutionGrade");

		addItensOnSpinnerEstado(courseCode, false);
		addListenerOnButtonBuscar();

		Log.d(this.getClass().toString(), "Load FinalInstitutionComparison");

	}
    
    // Method to list the State options in a spinner
	private void addItensOnSpinnerEstado(int courseCode, boolean delete)
	{
		statesSpinner = (Spinner) findViewById(R.id.estados);
		stateList = new ArrayList<String>();

		stateList = objectCourseController.searchState(courseCode);

		if (delete)
		{
			stateList.remove(firstStateName);
			Log.i(this.getClass().toString(), "State: " + firstStateName + " deleted from list");
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	stateList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statesSpinner.setAdapter(dataAdapter);

		statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				stateName = parent.getItemAtPosition(posicao).toString();

				addItensOnSpinnerMunicipio(stateName, false);
				Log.d(this.getClass().toString(), "State add on State Spinner.");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");
			}
		});
	}
    
    // Method to list the Municipio options in a spinner
	private void addItensOnSpinnerMunicipio(String uf, boolean delete)
	{
		this.citiesSpinner = (Spinner) findViewById(R.id.cidades);
		cityList = objectCourseController.searchCities(courseCode, uf);

		if (delete)
		{
			if (cityList.remove(firstCityName))
			{
				Log.i(this.getClass().toString(), "City: " + firstCityName + " deleted from list");
			}
			
			else
			{
				// Nothing to do
			}
		}
		
		else
		{
			// Nothing to do
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	cityList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.citiesSpinner.setAdapter(dataAdapter);

		this.citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				cityName = parent.getItemAtPosition(posicao).toString();

				addItensOnSpinnerIES(stateName, cityName, false);
				Log.d(this.getClass().toString(), "City add on Cities Spinner.");

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");
			}
		});

	}
    
    // Method to list the IES options in a spinner
	private void addItensOnSpinnerIES(String uf, String cidade, boolean delete)
	{
		try
		{
			institutionList = objectCourseController.searchCoursesNames(courseCode, uf, cidade);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e(this.getClass().toString(), "Error on searching courses names. Exception: ", e);
		}

		this.institutionSpinner = (Spinner) findViewById(R.id.spinnerIES);

		if (delete)
		{
			if (institutionList.remove(firstInstitutionName))
			{
				objectCourseController.removeInstitution(institutionPosition);
				Log.i(this.getClass().toString(), "Institution: " + firstInstitutionName + " deleted from list");
			}
			
			else
			{
				//Nothing to do
			}
			
		}
		
		else
		{
			// Nothing to do
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, institutionList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.institutionSpinner.setAdapter(dataAdapter);

		this.institutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{
				secondInstitutionInfo = new ArrayList<String>();

				try
				{
					secondInstitutionInfo = objectCourseController.getInstitutionInfo(posicao);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Log.e(this.getClass().toString(), "Error on get institution info. Exception: ", e);
				}

				secondInstitutionInfo.add(String.format("%.2f", objectCourseController.getCourseGrade(posicao)));
				secondInstitutionGrade = objectCourseController.getCourseGrade(posicao);
				secondInstitutionName = new String();
				secondInstitutionName = secondInstitutionInfo.get(0);

				if (secondInstitutionName.equalsIgnoreCase(firstInstitutionName))
				{
					Log.i(this.getClass().toString(),
						  secondInstitutionName + " / " + firstInstitutionName + " secondInstitutionName=firstInstitutionName");
					
					if (institutionList.size() == 1)
					{

						Log.i(this.getClass().toString(), "institutionList 1");

						if (cityList.size() == 1)
						{

							Log.i(this.getClass().toString(), "cityList 1");

							addItensOnSpinnerEstado(courseCode, true);
						}
						
						else if (cityList.size() > 1)
						{
							Log.i(this.getClass().toString(), "cityList > 1");
							addItensOnSpinnerMunicipio(stateName, true);
						}
						
						else
						{
							// Nothing to do
						}

					}
					
					else if (institutionList.size() > 1)
					{
						Log.i(this.getClass().toString(), "institutionList > 1");
						institutionPosition = posicao;
						addItensOnSpinnerIES(stateName, cityName, true);
					}
					
					else
					{
						// Nothing to do
					}
				}
				
				else
				{
					// Nothing to do
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
				Log.i(this.getClass().toString(), "No Item selected!");
			}
		});

	}

	private void addListenerOnButtonBuscar()
	{
		Button comparar = (Button) findViewById(R.id.Comparar);
		comparar.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent result = new Intent(FinalInstitutionComparison.this, InstitutionResultComparison.class);
				result.putStringArrayListExtra("firstInstitutionInfo", (ArrayList<String>) firstInstitutionInfo);
				result.putStringArrayListExtra("secondInstitutionInfo", (ArrayList<String>) secondInstitutionInfo);
				result.putExtra("courseCode", courseCode);
				result.putExtra("firstInstitutionGrade", firstInstitutionGrade);
				result.putExtra("firstInstitutionName", firstInstitutionName);
				result.putExtra("secondInstitutionGrade", secondInstitutionGrade);
				result.putExtra("secondInstitutionName", secondInstitutionName);

				startActivity(result);
				Log.d(this.getClass().toString(), "The button Comparar was clicked.");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_instituicao_final, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int idItem = item.getItemId(); // Receives the id of the selected item
		
		if (idItem == R.id.action_settings)
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
		public PlaceholderFragment()
		{
			// Nothing to do
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_instituicao_final, container, false);
			Log.d(this.getClass().toString(), "The view was loaded.");
			return rootView;

		}
	}
}
