/***********************************************************
 * File: CityResultComparison.java
 * Purpose: Shows the result of city comparison
 ***********************************************************/

package view;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

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
import graphs.holographlibrary.Bar;
import graphs.holographlibrary.BarGraph;
import br.unb.deolhonoenade.R;
import controller.CourseController;

import org.apache.log4j.Logger;

public class CityResultComparison extends Activity
{
	private String nameCourse; // Describes the name of the course being compared
	private String nameFirstState; // Describes the name of the first state to be compared
	private String nameSecondState; // Describes the name of the second state to be compared
	private String nameFirstCity; // Describes the name of the first city to be compared
	private String nameSecondCity; // Describes the name of the second city to be compared
	private int firstAverage; // Stores the average score of the first city
	private int secondAverage; // Stores the average score of the second city
	private int courseCode; // Describes the code of the course being compared
	private CourseController objectCourseController; // Instantiates an object of the controller
	
	static Logger log = Logger.getLogger(CityResultComparison.class.getName());

    @Override
    // Method to initialize the activity activity_comparacao_instituicao
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_c);
		
		log.debug("activity_comparacao_result_c called!");

		objectCourseController = new CourseController(this);

		nameCourse = getIntent().getExtras().getString("selectedCourse");
		nameFirstState = getIntent().getExtras().getString("nameFirstState");
		nameSecondState = getIntent().getExtras().getString("nameSecondState");
		nameFirstCity = getIntent().getExtras().getString("nameFirstCity");
		nameSecondCity = getIntent().getExtras().getString("nameSecondCity");
		courseCode = objectCourseController.searchCourseCode(nameCourse);

		// Stores the course searched
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado1);
		
		cursoSelecionado.setText(getIntent().getExtras().getString("selectedCourse"));
		// cursoSelecionado.setText(String.format("%d" , courseCode));

		List<Float> gradeList; // Stores the average grade of the course in the two given cities.

		/* Variable receives the average grade of the course in both cities.
		 * Position [0] has the average grade of the first city
		 * Position [1] has the average grade of the second city
		 */
		gradeList = objectCourseController.compareCity(courseCode,
													   nameFirstState, 
													   nameFirstCity, 
													   nameSecondState, 
													   nameSecondCity);

		// Stores the average grade of the course in the first city (example: 6,0)
		float firstAverage = gradeList.get(0); 
		
		// Stores the average grade of the course in the second city (example: 5,0)
		float secondAverage = gradeList.get(1); 

		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		// Stores the average grade of the course in the first (example: cinco)
		String media1String = String.valueOf(firstAverage);
		
		// Stores the average grade of the course in the second (example: cinco)
		String media2String = String.valueOf(secondAverage); 

		ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d = new Bar();
		d.setColor(Color.parseColor("#99CC00"));
		d.setName(nameFirstCity);
		d.setValue(Float.parseFloat(media1String.substring(0, 5)));
		Bar d2 = new Bar();
		d2.setColor(Color.parseColor("#FFBB33"));
		d2.setName(nameSecondCity);
		d2.setValue(Float.parseFloat(media2String.substring(0, 5)));
		points.add(d);
		log.info("Bar " + d + " added successfully!");
		points.add(d2);
		log.info("Bar " + d2 + " added successfully!");

		BarGraph graph = (BarGraph) findViewById(R.id.graph1);
		graph.setBars(points);
		graph.setUnit(" ");

		graph.setContentDescription("Cidade " + nameFirstCity + " nota: " + String.format("%.3f", firstAverage)
				+ ". E cidade " + nameSecondCity + " nota: " + String.format("%.3f", secondAverage));
		
		log.debug("Graph setted successfully!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_result_c, menu);
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
			// Nothing to do
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_comparacao_result_c, container, false);
			log.debug("The view was loaded.");
			return rootView;
		}
	}
}