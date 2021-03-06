/*****************************************************************************
 * File: InstitutionComparison.java
 * Purpose: Shows a list of institution to be compared. User should
 * 			select the first institution to compare the ENADE institution grade
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
import android.widget.Toast;
import android.os.Build;

import android.util.Log;
import java.util.logging.Logger;



public class InstitutionComparison extends Activity
{
	private Spinner institutionSpinner; // Describes the spinner of institutions
	private Spinner statesSpinner; // Describes the spinner of states
	private Spinner citiesSpinner; // Describes the spinner of cities
	private CourseController objectCourseController; // Describes the controller of courses
	private String stateName; // Describes the name of the state
	private String cityName; // Describes the name of the city
	private String institutionName; // Describes the name of the institution
	private int courseCode; // Describes the code of the course being compared
	private List<String> institutionInfo; // Describes data of the institution being compared
	private float selectedGrade; // Describes the grade of the selected item
	


    @Override
    // Method to initialize the activity activity_comparacao_instituicao
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao);
		this.objectCourseController = new CourseController(this);

		// Receives the selected course. Type = TextView
		TextView selectedCourse = (TextView) findViewById(R.id.cursoSelecionado);

		selectedCourse.setText(getIntent().getExtras().getString("selectedCourse"));

		courseCode = objectCourseController.searchCourseCode(getIntent().getExtras().getString("selectedCourse"));

		addItensOnSpinnerEstado(courseCode);
		addListenerOnButtonBuscar();
		
		Log.d(this.getClass().toString(), "Load InstitutionComparison");
	}

    // Method to list the State options in a spinner
	private void addItensOnSpinnerEstado(int courseCode)
	{
		statesSpinner = (Spinner) findViewById(R.id.estados);
		List<String> ufNameList = new ArrayList<String>();

		// Describes all States of a given course
		ufNameList = objectCourseController.searchState(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	ufNameList);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		statesSpinner.setAdapter(dataAdapter);

		statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				stateName = parent.getItemAtPosition(posicao).toString();

				addItensOnSpinnerMunicipio(stateName);
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
	private void addItensOnSpinnerMunicipio(String uf)
	{
		this.citiesSpinner = (Spinner) findViewById(R.id.cidades);

		// Describes all cities of a given course
		List<String> cityNameList;
		cityNameList = objectCourseController.searchCities(courseCode, uf);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	cityNameList);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.citiesSpinner.setAdapter(dataAdapter);

		this.citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				cityName = parent.getItemAtPosition(posicao).toString();
				addItensOnSpinnerIES(stateName, cityName);
				Log.d(this.getClass().toString(), "City add on City Spinner.");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");
			}
		});

	}

    // Method to list the IES options in a spinner
	private void addItensOnSpinnerIES(String stateName, String cityName)
	{
		List<String> cursos = null;

		try
		{
			cursos = objectCourseController.searchCoursesNames(courseCode, stateName, cityName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e(this.getClass().toString(), "Error on searching courses names. Exception: ", e);
		}
		this.institutionSpinner = (Spinner) findViewById(R.id.spinnerIES);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
																	android.R.layout.simple_spinner_item,
																	cursos);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.institutionSpinner.setAdapter(dataAdapter);

		this.institutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				try
				{
					institutionInfo = objectCourseController.getInstitutionInfo(posicao);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Log.e(this.getClass().toString(), "Error on get institution info. Exception: ", e);
				}

				selectedGrade = objectCourseController.getCourseGrade(posicao);
				institutionName = institutionInfo.get(0);
				
				Log.d(this.getClass().toString(), "Institution add on IES Spinner.");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");
			}
		});

	}

    // Method for the confirmation button for the search between the two institutions
	private void addListenerOnButtonBuscar()
	{
		Button comparar = (Button) findViewById(R.id.nomeIES);
		comparar.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent result = new Intent(InstitutionComparison.this, FinalInstitutionComparison.class);
				result.putStringArrayListExtra("institutionInfo", (ArrayList<String>) institutionInfo);
				result.putExtra("courseCode", courseCode);
				result.putExtra("firstCity", cityName);
				result.putExtra("firstState", stateName);
				result.putExtra("selectedGrade", selectedGrade);
				result.putExtra("institutionName", institutionName);

				startActivity(result);
				
				Log.d(this.getClass().toString(), "The button Comparar was clicked.");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_instituicao, menu);
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
		public PlaceholderFragment()
		{
			// Nothing to do
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_instituicao, container, false);
			Log.d(this.getClass().toString(), "The view was loaded.");
			return rootView;
		}
	}
}
