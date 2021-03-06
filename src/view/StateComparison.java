/**********************************************************
 * File: StateComparison.java
 * Purpose: Show a list of states to be compared in pairs
 *********************************************************/

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

public class StateComparison extends Activity
{
	private String course; // Describes the name of course
	private int courseCode; // Describes the result of the CourseController's method "SearchCourseCode" 
	private CourseController objectCourseController; // Describes the controller of courses
	private Spinner firstStateSpinner; // Describes the spinner of first state
	private Spinner secondStateSpinner; // Describes the spinner of second state
	private String firstState; // Describes the name of the first state
	private String secondState; // Describes the name of the second state
	
	@Override
	// Method to initialize the activity activity_comparacao_estado
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_estado);
		
		Log.d(this.getClass().toString(), "activity_comparacao_estado called!");

		objectCourseController = new CourseController(this);

		// Describes the info of the selected course from the view (search by ID)
		TextView selectedCourse = (TextView) findViewById(R.id.stringCurso);

		course = getIntent().getExtras().getString("selectedCourse");
		selectedCourse.setText(getIntent().getExtras().getString(
				"selectedCourse"));

		this.courseCode = objectCourseController.searchCourseCode(course);
		addItensOnSpinnerEstado1(courseCode);

		addListenerOnButtonComparar();

		if(savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container,
					new PlaceholderFragment()).commit();
		}
		else
		{
			// Nothing to do
		}
		
		Log.d(this.getClass().toString(), "Load StateComparison");
	}

	// Method to list the State 1 options in a spinner
	private void addItensOnSpinnerEstado1(int courseCode)
	{
		firstStateSpinner = (Spinner) findViewById(R.id.Estado1);
		
		// Describes a list of states by the course code
		List<String> stateList = new ArrayList<String>();

		stateList = objectCourseController.searchState(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stateList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		firstStateSpinner.setAdapter(dataAdapter);

		firstStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				firstState = parent.getItemAtPosition(position).toString();
				addItensOnSpinnerEstado2();
				
				Log.i(this.getClass().toString(), "Item " + firstState + " added successfully!");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				Log.i(this.getClass().toString(), "No Item selected!");				
			}
		});
	}

	// Method to list the State 2 options in a spinner
	private void addItensOnSpinnerEstado2()
	{
		secondStateSpinner = (Spinner) findViewById(R.id.Estado2);
		List<String> stateList = new ArrayList<String>();

		stateList = objectCourseController.searchState(courseCode);

		stateList.remove(firstState);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stateList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secondStateSpinner.setAdapter(dataAdapter);

		secondStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				secondState = parent.getItemAtPosition(position).toString();
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
		Button comapare = (Button) findViewById(R.id.Comparar);
		
		comapare.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view) 
			{
				try
				{
					Intent intent = new Intent(StateComparison.this, StateResultComparison.class);
	
					intent.putExtra("selectedCourse", course);
					intent.putExtra("firstState", firstState);
					intent.putExtra("secondState", secondState);
					startActivity(intent);
					
					Log.d(this.getClass().toString(), "StateResultComparison called successfully!");				
				}
				catch (Exception e)
				{
					Log.e(this.getClass().toString(), "Error when calling StateResultComparison view. Exception: ", e);				
				}
			}
		});
		
		Log.i(this.getClass().toString(), "Comparison accomplished successfully!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_estado, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if(id == R.id.action_settings)
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_estado, container, false);
			
			return rootView;
		}
	}
}
