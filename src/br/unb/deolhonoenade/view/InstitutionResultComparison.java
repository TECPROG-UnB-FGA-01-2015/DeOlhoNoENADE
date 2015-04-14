/***********************************************************
 * File: InstitutionResultComparison.java
 * Purpose: Responsible to show the result of IES comparison
***********************************************************/
package br.unb.deolhonoenade.view;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.unb.br.deolhonoenade.graphs.holographlibrary.Bar;
import br.unb.br.deolhonoenade.graphs.holographlibrary.BarGraph;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.CourseController;

public class InstitutionResultComparison extends Activity
{
	private CourseController objectCourseController;
	private List<String> firstInstitutionData, secondInstitutionData;
	private float firstGrade, secondGrade;
	private String firstInstitution, secondInstitution;
	
	@Override
	// Method to initialize the activity activity_comparacao_result_ies
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_ies);
		
		objectCourseController = new CourseController(this);
			
		firstInstitutionData = getIntent().getExtras().getStringArrayList("dadosIes1");
		secondInstitutionData = getIntent().getExtras().getStringArrayList("dadosIes2");
		
		firstInstitution = getIntent().getExtras().getString("firstInstitution");
		firstGrade = getIntent().getExtras().getFloat("firstGrade");
		secondInstitution = getIntent().getExtras().getString("secondInstitution");
		secondGrade = getIntent().getExtras().getFloat("secondGrade");
		
		TextView instiruicao1 = (TextView) findViewById(R.id.nomeIES1);
		instiruicao1.setText(firstInstitution);
		
		TextView instiruicao2 = (TextView) findViewById(R.id.nomeIES2);
		instiruicao2.setText(secondInstitution);
		
		String nota1String = String.valueOf(firstGrade);
		String nota2String = String.valueOf(secondGrade);
		
		ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d = new Bar();
		d.setColor(Color.parseColor("#99CC00"));
		d.setName("Instituicao 1");
		d.setValue(Float.parseFloat(nota1String.substring(0, 5)));
		Bar d2 = new Bar();
		d2.setColor(Color.parseColor("#FFBB33"));
		d2.setName("Instituicao 2");
		d2.setValue(Float.parseFloat(nota2String.substring(0, 5)));
		points.add(d);
		points.add(d2);

		BarGraph g = (BarGraph)findViewById(R.id.graph);
		g.setBars(points);
		g.setUnit(" ");
		g.setContentDescription("Instituição " + firstInstitution + " nota: " +
				String.format("%.3f", firstGrade) + ". E Instituição " + secondInstitution + " nota: "
					+ String.format("%.3f", secondGrade));
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
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_ie, container, false);
			return rootView;
		}
	}
}
