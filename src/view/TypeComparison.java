/***********************************************************
 * File: TypeComparison.java
 * Purpose: Responsible to get the informations be compared
***********************************************************/
package view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import br.unb.deolhonoenade.R;
import controller.CourseController;

public class TypeComparison extends Activity
{	
	private Spinner firstStateSpinner; // Contains a list of states to be chosen to the first institution
	private Spinner firstTypeSpinner; // First institution type (public or private)
	private Spinner secondStateSpinner; // Contains a list of states to be chosen to the second institution
	private Spinner secondTypeSpinner; // second institution type (public or private)
	private CourseController objectCourseController; // CourseController type object
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
		this.objectCourseController = new CourseController(this);
		
		// Recognize which course was selected
		TextView selectedCourse = (TextView) findViewById(
				R.id.nomeCursoSelecionado);
		
		Bundle extras = getIntent().getExtras(); // Retrieves and gets a map of extended data from the intent
		selectedCourse.setText(extras.getString("selectedCourse")); // Gets the Selected Course's Name
		
		// Takes the code of the course that was selected
		courseCode = objectCourseController.searchCourseCode(
				extras.getString("selectedCourse")); 
		
		addItensOnSpinnerFirstStateType(courseCode);
		addListenerOnButtonSearch();
		
		Log.d(this.getClass().toString(), "activity_comparacao_tipo called!");
		Log.d(this.getClass().toString(), "1 Type Comparison Button and its respective method created.");
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
	private void addItensOnSpinnerFirstStateType(int courseCode)
	{
		firstStateSpinner = (Spinner) findViewById(R.id.SpinnerEstado1);
		List<String> list = new ArrayList<String>(); // Holds a list of institutions by the course code
		
		list = objectCourseController.searchState(courseCode);

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
				addItensOnSpinnerFirstType(firstState);						
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");	
			}
		});	
	}

	/* Method to add items on spinner Type1 to compare University's
	 * types (Public and Private)
	 */
	private void addItensOnSpinnerFirstType(String state)
	{
		this.firstTypeSpinner = (Spinner) findViewById(R.id.SpinnerEstado1Tipo);
		List<String> list;
		list = objectCourseController.searchStateTypes(courseCode, state);

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
				
				addItensOnSpinnerSecondStateType(courseCode, false);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");	
			}
		});
	}
	
	// Method to add item on spinner secondStateSpinner
	private void addItensOnSpinnerSecondStateType(int courseCode, boolean removeRepeatedState)
	{
		secondStateSpinner = (Spinner) findViewById(R.id.SpinnerEstado2);
		List<String> list = new ArrayList<String>();
		
		list = objectCourseController.searchState(courseCode);
		
		if(removeRepeatedState)
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
						
				addItensOnSpinnerSecondType(secondState, false);
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");	
			}
		});	
	}
	
	/* Method to add items on spinner secondTypeSpinner to compare University's
	 * types (Public and Private)
	 */
	private void addItensOnSpinnerSecondType(String state, // Which federation unit was selected
										 boolean removeInstitutionPosition)
	{
		this.secondTypeSpinner = (Spinner) findViewById(R.id.SpinnerEstado2Tipo);
		secondTypeList = objectCourseController.searchStateTypes(courseCode, state);
		
		if(removeInstitutionPosition)
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
			// Method to select item from the view when newly selected position is different from the previously selected position
			public void onItemSelected(AdapterView<?> parent,
										View v, // Holds which view is called
										int posicao, // Gets the position of the selected line on database
										long id) // Holds the id of the container's selected item
			{
				secondType = parent.getItemAtPosition(posicao).toString();
				
				boolean firstStateEquals = firstState.equalsIgnoreCase(secondState);
				boolean firstTypeEquals = firstType.equalsIgnoreCase(secondType);
				int secondUniverityType = 1;
				
				if(firstStateEquals && firstTypeEquals)
				{
					if(secondTypeList.size() <= secondUniverityType)
					{
						addItensOnSpinnerSecondStateType(courseCode, true);
					}
					else
					{
						addItensOnSpinnerSecondType(secondState, true);
					}
				}
				else
				{
					// Nothing to do
				}
			}
		 
			@Override
			// Method to be called when nothing is selected
			public void onNothingSelected(AdapterView<?> parent)
			{
				// Nothing to do
			}
		});
	}

	// Method to recognize the button "Search"
	private void addListenerOnButtonSearch()
	{
		// Button to start the comparison
		Button compare = (Button) findViewById(R.id.comparaT1);
		compare.setOnClickListener (new OnClickListener()
		{
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * TypeResultComparison view
			 */
	    	public void onClick(View v)
			{
				try
				{
					// Holds the result of the comparison about the institution type (public or private)
					Intent result =  new Intent(TypeComparison.this,
							TypeResultComparison.class);
					
					// Holds the results of the comparison between the two institutions
					comparisonResults = objectCourseController.compareType(courseCode, firstState, firstType,
							secondState, secondType);
					result.putExtra("courseCode", courseCode);
					result.putExtra("firstResult", comparisonResults.get(0));
					result.putExtra("secondResult", comparisonResults.get(1));
					result.putExtra("firstState", firstState);
					result.putExtra("firstTypeSpinner", firstType);
					result.putExtra("secondState", secondState);
					result.putExtra("secondTypeSpinner", secondType);
		
		    		startActivity(result);
		    		
		    		Log.i(this.getClass().toString(), "TypeResultComparison called successfully!");
				}
				catch (Exception e)
				{
					Log.e(this.getClass().toString(), "Error when calling TypeResultComparison view. Exception: ", e);
				}
	    	}
		});
		
		Log.i(this.getClass().toString(), "TypeComparison accomplished successfully!");
	}

	@Override
	// Method to recognize when an option on menu is selected
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    /* Handle action bar item clicks here. The action bar will
		 * automatically handle clicks on the Home/Up button, so long
		 * as you specify a parent activity in AndroidManifest.xml
		 */
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
