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

public class InstitutionComparison extends Activity
{
	private Spinner institutionSpinner; // Spinner variable of the Institutions
	private Spinner statesSpinner; // Spinner variable of the States
	private Spinner citiesSpinner; // Spinner variable of the Cities
	private ControllerCurso objectCourseController; // Instantiates an object of the controller
	private String stateName; // Holds the name of the state
	private String cityName; // Holds the name of the city
	private String institutionName; // Holds the name of the institution
	private int courseCode; // Holds the code of the course being compared
	private List<String> institutionData; // Holds data of the institution being compared
	private float selectedGrade; // Holds the grade of the selected item

    @Override
    // Method to initialize the activity activity_comparacao_instituicao
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao);
		this.objectCourseController = new ControllerCurso(this);
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));

		courseCode = objectCourseController.buscaCodCurso(getIntent().getExtras().getString("cursoSelecionado"));

		addItensOnSpinnerEstado(courseCode);
		addListenerOnButtonBuscar();
	}
    
    // Method to list the State options in a spinner
	private void addItensOnSpinnerEstado(int courseCode)
	{
		statesSpinner = (Spinner) findViewById(R.id.estados);
		List<String> ufNameList = new ArrayList<String>();

		// Store all States of a given course
		ufNameList = objectCourseController.buscaUf(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ufNameList);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		statesSpinner.setAdapter(dataAdapter);

		statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				stateName = parent.getItemAtPosition(posicao).toString();

				addItensOnSpinnerMunicipio(stateName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}
    
    // Method to list the Municipio options in a spinner
	private void addItensOnSpinnerMunicipio(String uf)
	{
		this.citiesSpinner = (Spinner) findViewById(R.id.cidades);
		
		// Store all cities of a given course
		List<String> cityNameList;
		cityNameList = objectCourseController.buscaCidades(courseCode, uf);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNameList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.citiesSpinner.setAdapter(dataAdapter);

		this.citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				cityName = parent.getItemAtPosition(posicao).toString();
				addItensOnSpinnerIES(stateName, cityName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});

	}
    
    // Method to list the IES options in a spinner
	private void addItensOnSpinnerIES(String stateName, String cityName)
	{
		List<String> cursos = objectCourseController.buscaIesComUfMun(courseCode, stateName, cityName);
		this.institutionSpinner = (Spinner) findViewById(R.id.institutionSpinner);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cursos);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		this.institutionSpinner.setAdapter(dataAdapter);

		this.institutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id)
			{

				institutionData = objectCourseController.getDadosIES(posicao);
				selectedGrade = objectCourseController.getConceitoDoArrayCursos(posicao);
				institutionName = institutionData.get(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});

	}
    
    // Method for the confirmation button for the search between the two institutions
	private void addListenerOnButtonBuscar()
	{
		Button comparar = (Button) findViewById(R.id.institutionName);
		comparar.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent result = new Intent(InstitutionComparison.this, FinalInstitutionComparison.class);
				result.putStringArrayListExtra("dadosIes", (ArrayList<String>) institutionData);
				result.putExtra("courseCode", courseCode);
				result.putExtra("municipio1", cityName);
				result.putExtra("estado1", stateName);
				result.putExtra("selectedGrade", selectedGrade);
				result.putExtra("institutionName", institutionName);

				startActivity(result);
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
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_instituicao, container, false);
			return rootView;
		}
	}
}
