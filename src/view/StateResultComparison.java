/**********************************************************
 * File: StateResultComparison.java
 * Purpose: Show the result of the comparison of two states
 *********************************************************/

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

public class StateResultComparison extends Activity
{
	private String course; // Receives the name of course
	private String firstState, secondState; // Receives the value of the selected state
	private CourseController objectCourseController; // Object from the ControllerCurso Class
	private int courseCode; // Receives the result of the ControllerCurso's method "searchCourseCode" 

	@Override
	
	// Method to initialize the activity activity_comparacao_result
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result);
		
		Log.d(this.getClass().toString(), "activity_comparacao_result called!");

		objectCourseController = new CourseController(this);

		course = getIntent().getExtras().getString("selectedCourse");
		firstState = getIntent().getExtras().getString("firstState");
		secondState = getIntent().getExtras().getString("secondState");
		courseCode = objectCourseController.searchCourseCode(course);

		// Receives the info of the title from the view (search by ID)
		TextView titulo = (TextView) findViewById(R.id.stringCurso);

		titulo.setText(String.format("Comparacao do curso de\n\t%s", course));

		List<Float> list = null;

		try
        {
	        list = objectCourseController.compareState(firstState, secondState, courseCode);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }

		if(savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container,
					new PlaceholderFragment()).commit();
		}
		else
		{
			// Nothing to do
		}

		// Contains the value of the index 0 of the list
		String list1String = String.valueOf(list.get(0));
		
		// Contains the value of the index 1 of the list
		String list2String = String.valueOf(list.get(1));

		// Stores the average graphic's information
		ArrayList<Bar> points = new ArrayList<Bar>();
		
		Bar d = new Bar();
		d.setColor(Color.parseColor("#99CC00"));
		d.setName(firstState);
		d.setValue(Float.parseFloat(list1String.substring(0, 5)));
		
		Bar d2 = new Bar();
		d2.setColor(Color.parseColor("#FFBB33"));
		d2.setName(secondState);
		d2.setValue(Float.parseFloat(list2String.substring(0, 5)));
		
		points.add(d);
		
		Log.d(this.getClass().toString(), "Bar " + d + " added successfully!");

		points.add(d2);
		
		Log.d(this.getClass().toString(), "Bar " + d2 + " added successfully!");

		BarGraph g = (BarGraph) findViewById(R.id.graph2);
		g.setBars(points);
		g.setUnit(" ");
		g.setContentDescription(firstState + ".   nota: "
				+ String.format("%.3f", list.get(0)) + ". E " + secondState
				+ ".   nota: " + String.format("%.3f", list.get(1)));
		
		Log.i(this.getClass().toString(), "State's TypesComparison info: First Institution's State " + firstState + ", First Institution's State Grade: " 
		         + String.format("%.3f", list.get(0)) + ", Second Institution's State: " + secondState + 
		         ", Second Institution's State Grade: " +  String.format("%.3f", list.get(1)));
	}

	@Override
	
	// Method to initialize the contents of the Activity's standard options menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_result, menu);
		
		return true;
	}

	@Override
	
	// Method to recognize when an option on menu is selected
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
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
					R.layout.fragment_comparacao_result, container, false);
			
			return rootView;
		}
	}
}
