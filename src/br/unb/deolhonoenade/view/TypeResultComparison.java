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
import br.unb.deolhonoenade.controller.ControllerCurso;

public class TypeResultComparison extends Activity
{
	private CourseController objectCourseController;
	private float firstResult, secondResult;
	private String firstState, secondState, firstType, secondType;
	
	@Override
	// Method to initialize the activity activity_comparacao_result_tipo
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_tipo);
		
		objectCourseController = new CourseController(this);
		firstResult = getIntent().getExtras().getFloat("firstResult");
		secondResult = getIntent().getExtras().getFloat("secondResult");
		
		firstState = getIntent().getExtras().getString("Estado1");
		firstType = getIntent().getExtras().getString("Tipo1");
		secondState = getIntent().getExtras().getString("Estado2");
		secondType = getIntent().getExtras().getString("Tipo2");
		
		String eT1 = (firstState + " " + firstType);
		String eT2 = (secondState + " " + secondType);
		
		TextView estadoTipo1 = (TextView) findViewById(R.id.estadoTipo1);
		estadoTipo1.setText(eT1);
		
		TextView estadoTipo2 = (TextView) findViewById(R.id.estadoTipo2);
		estadoTipo2.setText(eT2);
		
		String resultado1String = String.valueOf(firstResult);
		String resultado2String = String.valueOf(secondResult);
		
		ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d = new Bar();
		d.setColor(Color.parseColor("#99CC00"));
		d.setName("Estado/Tipo 1:");
		d.setValue(Float.parseFloat(resultado1String.substring(0, 5)));
		Bar d2 = new Bar();
		d2.setColor(Color.parseColor("#FFBB33"));
		d2.setName("Estado/Tipo 2:");
		d2.setValue(Float.parseFloat(resultado2String.substring(0, 5)));
		points.add(d);
		points.add(d2);
		
		BarGraph g = (BarGraph)findViewById(R.id.graph3);
		g.setBars(points);
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
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_tipo, container, false);
			return rootView;
		}
	}
}
