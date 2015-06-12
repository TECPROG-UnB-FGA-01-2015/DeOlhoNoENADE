/***********************************************************
 * File: InstitutionResultComparison.java
 * Purpose: Responsible to show the result of Institution comparison
***********************************************************/
package view;

import graphs.holographlibrary.Bar;
import graphs.holographlibrary.BarGraph;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.unb.deolhonoenade.R;
import controller.CourseController;

public class InstitutionResultComparison extends Activity
{
	private CourseController objectCourseController; // Describes the controller of courses
	private List<String> firstInstitutionInfo; // Describes data of the first institution being compared
	private List<String> secondInstitutionInfo; // Describes data of the second institution being compared
	private float firstGrade; // Describes the grade of the first institution being compared
	private float secondGrade; // Describes the grade of the second institution being compared
	private String firstInstitution; // Describes the name of the first institution being compared
	private String secondInstitution; // Describes the name of the second institution being compared
	
	@Override
	// Method to initialize the activity activity_comparacao_result_ies
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_ies);
		
		Log.d(this.getClass().toString(), "activity_comparacao_result_ies called!");
		
		objectCourseController = new CourseController(this);
		
		Bundle extras = getIntent().getExtras(); // Retrieves and gets a map of extended data from the intent
		
		firstInstitutionInfo = extras.getStringArrayList("firstInstitutionInfo");  
		secondInstitutionInfo = extras.getStringArrayList("secondInstitutionInfo");
		
		firstInstitution = extras.getString("firstInstitution");
		firstGrade = extras.getFloat("firstGrade");
		secondInstitution = extras.getString("secondInstitution");
		secondGrade = extras.getFloat("secondGrade");
		
		// Presents the name of first institution on a textView
		TextView institutionOne = (TextView) findViewById(R.id.nomeIES1);
		institutionOne.setText(firstInstitution);
		
		// Presents the name of second institution on a textView
		TextView institutionTwo = (TextView) findViewById(R.id.nomeIES2);
		institutionTwo.setText(secondInstitution);
		
		String firstInstitutionGrade = String.valueOf(firstGrade); // The grade of the first institution
		String secondInstitutionGrade = String.valueOf(secondGrade); // The grade of the second institution
		
		ArrayList<Bar> institutionGrades = new ArrayList<Bar>(); // Holds the average grade of institution
		Bar firstInstitutionGraph = new Bar(); // Contains the first institution data
		firstInstitutionGraph.setColor(Color.parseColor("#99CC00"));
		firstInstitutionGraph.setName("Instituicao 1");
		firstInstitutionGraph.setValue(Float.parseFloat(firstInstitutionGrade.substring(0, 5)));
		Bar secondInstitutionGraph = new Bar(); // Contains the second institution data
		secondInstitutionGraph.setColor(Color.parseColor("#FFBB33"));
		secondInstitutionGraph.setName("Instituicao 2");
		secondInstitutionGraph.setValue(Float.parseFloat(secondInstitutionGrade.substring(0, 5)));
		institutionGrades.add(firstInstitutionGraph);
		
		Log.i(this.getClass().toString(), "First Institution Grade Graph created!");
		
		institutionGrades.add(secondInstitutionGraph);
		
		Log.i(this.getClass().toString(), "Second Institution Grade Graph created!");

		// Holds the graphical bar with the first institution and second institution names and grades
		BarGraph graph = (BarGraph)findViewById(R.id.graph);
		graph.setBars(institutionGrades);
		graph.setUnit(" ");
		graph.setContentDescription("Instituição " + firstInstitution + " nota: " +
				String.format("%.3f", firstGrade) + ". E Instituição " + secondInstitution + " nota: "
					+ String.format("%.3f", secondGrade));
		
		Log.i(this.getClass().toString(), "Institution's Comparison info: First Institution " + firstInstitution + ", First Institution Grade: " 
		         + firstGrade + ", Second Institution: " + secondInstitution + ", Second Institution Grade: " + secondGrade);
	}

	@Override
	// Method to initialize the contents of the Activity's standard options menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_result_ie, menu);
		
		return true;
	}

	@Override
	// Method to recognize when an option on menu is selected
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId(); // Holds the id of the container's selected item 
		
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
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_ie, container, false);
			
			return rootView;
		}
	}
}
