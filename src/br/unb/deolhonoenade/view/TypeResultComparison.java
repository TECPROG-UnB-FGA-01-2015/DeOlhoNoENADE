/***********************************************************
 * File: TypeResultComparison.java
 * Purpose: Responsible to show the result of institution
 * 			type comparison
***********************************************************/
package br.unb.deolhonoenade.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.unb.br.deolhonoenade.graphs.holographlibrary.Bar;
import br.unb.br.deolhonoenade.graphs.holographlibrary.BarGraph;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.CourseController;

public class TypeResultComparison extends Activity
{
	private CourseController objectCourseController; // CourseController type object
	private float firstResult; // Holds the average grade of the first course type
	private float secondResult; // Holds the average grade of the second course type
	private String firstState; // Holds the state of first institution (DF, MG, RJ,...)
	private String secondState; // Holds the state of second institution (AC, AM, SP,...)
	private String firstType; // Holds the first institution type (public or private)
	private String secondType; // Holds the second institution type (public or private)
	
	@Override
	// Method to initialize the activity activity_comparacao_result_tipo
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_tipo);
		
		objectCourseController = new CourseController(this);
		firstResult = getIntent().getExtras().getFloat("firstResult");
		secondResult = getIntent().getExtras().getFloat("secondResult");
		
		firstState = getIntent().getExtras().getString("firstState");
		firstType = getIntent().getExtras().getString("firstType");
		secondState = getIntent().getExtras().getString("secondState");
		secondType = getIntent().getExtras().getString("secondType");
		
		// Holds the first state name and the type of the first institution
		String firstStateType = (firstState + " " + firstType);
		
		// Holds the second state name and the type of the second institution
		String secondStateType = (secondState + " " + secondType);
		
		// Presents the first institution state on a textView
		TextView stateTypeOne = (TextView) findViewById(R.id.estadoTipo1);
		stateTypeOne.setText(firstStateType);
		
		// Presents the second institution state on a textView
		TextView stateTypeTwo = (TextView) findViewById(R.id.estadoTipo2);
		stateTypeTwo.setText(secondStateType);
		
		String firstTypeGrade = String.valueOf(firstResult); // Holds the first institution grade
		String secondTypeGrade = String.valueOf(secondResult); // Holds the second institution grade
		
		ArrayList<Bar> institutionGrades = new ArrayList<Bar>(); // Holds the average grade of institution
		Bar firstInstitutionGraph = new Bar(); // Contains the first institution data
		firstInstitutionGraph.setColor(Color.parseColor("#99CC00"));
		firstInstitutionGraph.setName("Estado/Tipo 1:");
		firstInstitutionGraph.setValue(Float.parseFloat(firstTypeGrade.substring(0, 5)));
		Bar secondInstitutionGraph = new Bar(); // Contains the second institution data
		secondInstitutionGraph.setColor(Color.parseColor("#FFBB33"));
		secondInstitutionGraph.setName("Estado/Tipo 2:");
		secondInstitutionGraph.setValue(Float.parseFloat(secondTypeGrade.substring(0, 5)));
		institutionGrades.add(firstInstitutionGraph);
		institutionGrades.add(secondInstitutionGraph);
		
		// Holds the graphical bar with the first institution and second institution names and grades
		BarGraph g = (BarGraph)findViewById(R.id.graph3);
		
		g.setBars(institutionGrades);
		g.setUnit(" ");

		g.setContentDescription("Tipo de Instituição " + firstType + " no estado " +
				firstState + ".   nota: " + String.format("%.3f", firstResult)
						+ ". E tipo de instituição " + secondType + " no estado " +
								secondState + ".   nota: " + String.format("%.3f",
										secondResult));
	}

	@Override
	// Method to initialize the contents of the Activity's standard options menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_result_tipo, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_tipo, container, false);
			return rootView;
		}
	}
}
