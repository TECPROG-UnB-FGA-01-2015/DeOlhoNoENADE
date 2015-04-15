/***********************************************************
 * File: TypeComparison.java
 * Purpose: Responsible to get the informations be compared
***********************************************************/
package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.controller.CourseController;
import br.unb.deolhonoenade.model.Course;
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
import android.os.Build;

public class TypeComparison extends Activity
{
	// Contains a list of states to be chosen to the first institution
	private Spinner firstStateSpinner;
	
	private Spinner firstTypeSpinner; // First institution type (public or private)
	
	// Contains a list of states to be chosen to the second institution
	private Spinner secondStateSpinner;
	private Spinner secondTypeSpinner; // second institution type (public or private)
	private ControllerCurso objectCourseController; // ControllerCurso type object
	private String firstState; // Holds the first institution state (DF, MG, RJ,...)
	private String firstType; // Holds the state of first institution (AC, AM, SP,...)
	private String secondState; // Holds the second institution state (public or private)
	private String secondType; // Holds the second institution type (public or private)
	private int courseCode; // Code of the course selected
	private List<Float> comparisonResults; // Holds the average grade of the institution type
	
	// Holds a list of institutions by the course code and the federal unit
	private List<String> secondTypeList;
	
	@Override
	// Method to initialize the activity activity_comparacao_tipo	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_tipo);
		this.objectCourseController = new ControllerCurso(this);
		
		// Recognize which course was selected
		TextView cursoSelecionado = (TextView) findViewById(
				R.id.nomeCursoSelecionado);
		
		cursoSelecionado.setText(getIntent().getExtras().getString(
				"cursoSelecionado"));
		
		// Takes the code of the course that was selected
		courseCode = objectCourseController.buscaCodCurso(
				getIntent().getExtras().getString("cursoSelecionado"));
		
		addItensOnSpinnerEstadoT1(courseCode);
		//addItensOnSpinnerEstadoT2(courseCode, false);
		addListenerOnButtonBuscar();
	}

	@Override
	// Method to initialize the contents of the Activity's standard options menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_tipo, menu);
		return true;
	}
	
	// Method to add item on spinner firstStateSpinner
	private void addItensOnSpinnerEstadoT1(int courseCode)
	{
		firstStateSpinner = (Spinner) findViewById(R.id.SpinnerEstado1);
		List<String> list = new ArrayList<String>(); // Holds a list of institutions by the course code
		
		list = objectCourseController.buscaUf(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		
		dataAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		firstStateSpinner.setAdapter(dataAdapter);
		
		firstStateSpinner.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent,
										View v, // Holds which view is called
										int posicao, // Gets the position of the selected line on database
										long id) // Holds the id of the container's selected item 
			{
				firstState = parent.getItemAtPosition(posicao).toString();
				addItensOnSpinnerTipo1(firstState);						
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});	
	}

	/* Method to add items on spinner Type1 to compare University's
	 * types (Public and Private)
	 */
	private void addItensOnSpinnerTipo1(String uf)
	{
		this.firstTypeSpinner = (Spinner) findViewById(R.id.SpinnerEstado1Tipo);
		List<String> list;
		list = objectCourseController.buscaTiposEstado(courseCode, uf);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		this.firstTypeSpinner.setAdapter(dataAdapter);
		
		this.firstTypeSpinner.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent,
										View v, // Holds which view is called
										int posicao, // Gets the position of the selected line on database
										long id) // Holds the id of the container's selected item
			{
				firstType = parent.getItemAtPosition(posicao).toString();
				
				addItensOnSpinnerEstadoT2(courseCode, false);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}
	
	// Method to add item on spinner secondStateSpinner
	private void addItensOnSpinnerEstadoT2(int courseCode, boolean retira)
	{
		secondStateSpinner = (Spinner) findViewById(R.id.SpinnerEstado2);
		List<String> list = new ArrayList<String>();
		
		list = objectCourseController.buscaUf(courseCode);
		
		if(retira)
		{
			list.remove(firstState);
		}
		
		else
		{
			// Nothing to do
		}
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		secondStateSpinner.setAdapter(dataAdapter);
		
		secondStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			
			public void onItemSelected(AdapterView<?> parent,
										View v, // Holds which view is called
										int posicao, // Gets the position of the selected line on database
										long id) // Holds the id of the container's selected item
			{
				secondState = parent.getItemAtPosition(posicao).toString();
						
				addItensOnSpinnerTipo2(secondState, false);
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});	
	}
	
	/* Method to add items on spinner secondTypeSpinner to compare University's
	 * types (Public and Private)
	 */
	private void addItensOnSpinnerTipo2(String uf, // Which federation unit was selected
										 boolean retira)
	{
		this.secondTypeSpinner = (Spinner) findViewById(R.id.SpinnerEstado2Tipo);
		secondTypeList = objectCourseController.buscaTiposEstado(courseCode, uf);
		
		if(retira)
		{
			secondTypeList.remove(firstType);
		}
		
		else
		{
			// Nothing to do
		}
				
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, secondTypeList);
		dataAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
				
		this.secondTypeSpinner.setAdapter(dataAdapter);
				
		this.secondTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			// Method to 
			public void onItemSelected(AdapterView<?> parent,
										View v, // Holds which view is called
										int posicao, // Gets the position of the selected line on database
										long id) // Holds the id of the container's selected item
			{
				secondType = parent.getItemAtPosition(posicao).toString();
				
				if(firstState.equalsIgnoreCase(secondState))
				{
					if(firstType.equalsIgnoreCase(secondType))
					{
						if(secondTypeList.size()<=1)
						{
							addItensOnSpinnerEstadoT2(courseCode, true);
						}
						else
						{
							addItensOnSpinnerTipo2(secondState, true);
						}
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
			// Method to be called when nothing be selected
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}

	// Method to recognize the button "Buscar"
	private void addListenerOnButtonBuscar()
	{
		// Button to start the comparison
		Button comparar = (Button) findViewById(R.id.comparaT1);
		comparar.setOnClickListener (new OnClickListener()
		{
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * ComparacaoResultTipo view
			 */
	    	public void onClick(View v)
			{
				// Holds the result of the comparison about the institution type (public or private)
				Intent result =  new Intent(TypeComparison.this,
						ComparacaoResultTipo.class);
				
				// Holds the results of the comparison between the two institutions
				comparisonResults = objectCourseController.comparacaoTipo(courseCode, firstState, firstType,
						secondState, secondType);
				result.putExtra("CodCurso", courseCode);
				result.putExtra("resultado1", comparisonResults.get(0));
				result.putExtra("resultado2", comparisonResults.get(1));
				result.putExtra("Estado1", firstState);
				result.putExtra("firstTypeSpinner", firstType);
				result.putExtra("Estado2", secondState);
				result.putExtra("secondTypeSpinner", secondType);
	
	    		startActivity(result);
	    	}
		});
	}

	@Override
	// Method to recognize when an option on menu is selected
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		// Java default constructor
		public PlaceholderFragment()
		{
			// Nothing to do
		}

		@Override
		/* Method to create and return the view hierarchy associated with the
		 * fragment
		 */
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			// Hosts all other views on the same place
			View rootView = inflater.inflate(R.layout.fragment_comparacao_tipo,
					container, false);
			return rootView;
		}
	}
}
