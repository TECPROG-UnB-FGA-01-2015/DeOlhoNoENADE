package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.controller.ControllerCurso;
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

public class FinalInstitutionComparison extends Activity
{

	private Spinner statesSpinner; // Spinner variable of the States
	private Spinner institutionSpinner; // Spinner variable of the Institutions
	private ControllerCurso objectCourseController; // Instantiates an object of the controller
	private String stateName; // Holds the name of the state
	private String cityName; // Holds the name of the city
	private String secondInstitutionName; // Holds the name of the second institution to be compared
	private String firstStateName; // Holds the name of the first state to be compared
	private String firstCityName; // Holds the name of the first city to be compared
	private String firstInstitutionName; // Holds the name of the first institution to be compared
	private Spinner citiesSpinner; // Spinner variable of the cities
	private int courseCode; // Describes the code of the course being avaliated
	private float firstInstitutionGrade; // Holds the grade of the first institution
	private float secondInstitutionGrade; // Holds the grade of the second institution
	private List<String> firstInstitutionData; // Receives information of the first institution to be compared
	private List<String> secondInstitutionData; // Receives information of the second institution to be compared
	private List<String> institutionList; // Receives a list of institutions
	private List<String> cityList; // Receives a list of cities
	private List<String> stateList; // Receives a list of states
	private int institutionPosition; // Holds the position of the institution on ENADE ranking

    @Override
    // Method to initialize the activity activity_comparacao_instituicao_final
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao_final);
		this.objectCourseController = new ControllerCurso(this);

		// Receives the selected course. Type = TextView
		TextView selectedCourse = (TextView) findViewById(R.id.cursoSelecionado);
		
		selectedCourse.setText(getIntent().getExtras().getString("cursoSelecionado"));

		courseCode = getIntent().getExtras().getInt("courseCode");
		firstInstitutionData = getIntent().getExtras().getStringArrayList("dadosIes");
		firstStateName = getIntent().getExtras().getString("firstStateName");
		firstCityName = getIntent().getExtras().getString("firstCityName");
		firstInstitutionName = getIntent().getExtras().getString("firstInstitutionName");
		firstInstitutionGrade = getIntent().getExtras().getFloat("firstInstitutionGrade");

		addItensOnSpinnerEstado(courseCode, false);
		addListenerOnButtonBuscar();

	}
    
    // Method to list the State options in a spinner
	private void addItensOnSpinnerEstado(int courseCode, boolean delete)
	{
		statesSpinner = (Spinner) findViewById(R.id.stateList);
		stateList = new ArrayList<String>();

		stateList = objectCourseController.buscaUf(courseCode);

		if (delete)
		{
			stateList.remove(firstStateName);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statesSpinner.setAdapter(dataAdapter);

		statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				stateName = parent.getItemAtPosition(posicao).toString();

				addItensOnSpinnerMunicipio(stateName, false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}
    
    // Method to list the Municipio options in a spinner
	private void addItensOnSpinnerMunicipio(String uf, boolean delete)
	{
		this.citiesSpinner = (Spinner) findViewById(R.id.cidades);
		cityList = objectCourseController.buscaCidades(courseCode, uf);

		if (delete)
		{
			if (cityList.remove(firstCityName))
			{
				Log.e(this.getClass().toString(), "retirou m");
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

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
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

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});

	}
    
    // Method to list the IES options in a spinner
	private void addItensOnSpinnerIES(String uf, String cidade, boolean delete)
	{
		institutionList = objectCourseController.buscaIesComUfMun(courseCode, uf, cidade);
		this.institutionSpinner = (Spinner) findViewById(R.id.institutionSpinner);

		if (delete)
		{
			if (institutionList.remove(firstInstitutionName))
			{
				objectCourseController.removeIes(institutionPosition);
				Log.e(this.getClass().toString(), "retirou");
			}
			else
			{
				Log.e(this.getClass().toString(), "nao retirou");
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
				secondInstitutionData = new ArrayList<String>();
				secondInstitutionData = objectCourseController.getDadosIES(posicao);
				secondInstitutionData.add(String.format("%.2f", objectCourseController.getConceitoDoArrayCursos(posicao)));
				secondInstitutionGrade = objectCourseController.getConceitoDoArrayCursos(posicao);
				secondInstitutionName = new String();
				secondInstitutionName = secondInstitutionData.get(0);

				if (secondInstitutionName.equalsIgnoreCase(firstInstitutionName))
				{
					Log.e(this.getClass().toString(), secondInstitutionName + " / " + firstInstitutionName + " secondInstitutionName=firstInstitutionName");
					if (institutionList.size() == 1)
					{

						Log.e(this.getClass().toString(), "institutionList 1");

						if (cityList.size() == 1)
						{

							Log.e(this.getClass().toString(), "cityList 1");

							addItensOnSpinnerEstado(courseCode, true);
						}
						else if (cityList.size() > 1)
						{
							Log.e(this.getClass().toString(), "cityList > 1");
							addItensOnSpinnerMunicipio(stateName, true);
						}
						else
						{
							// Nothing to do
						}

					}
					else if (institutionList.size() > 1)
					{
						Log.e(this.getClass().toString(), "institutionList > 1");
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
				Intent result = new Intent(FinalInstitutionComparison.this, ComparacaoResultIES.class);
				result.putStringArrayListExtra("dadosIes1", (ArrayList<String>) firstInstitutionData);
				result.putStringArrayListExtra("dadosIes2", (ArrayList<String>) secondInstitutionData);
				result.putExtra("CodCurso", courseCode);
				result.putExtra("firstInstitutionGrade", firstInstitutionGrade);
				result.putExtra("firstInstitutionName", firstInstitutionName);
				result.putExtra("secondInstitutionGrade", secondInstitutionGrade);
				result.putExtra("secondInstitutionName", secondInstitutionName);

				startActivity(result);
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
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_instituicao_final, container, false);
			return rootView;
		}
	}
}