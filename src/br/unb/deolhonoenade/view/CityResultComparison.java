package br.unb.deolhonoenade.view;

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
import br.unb.br.deolhonoenade.graphs.holographlibrary.Bar;
import br.unb.br.deolhonoenade.graphs.holographlibrary.BarGraph;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.ControllerCurso;

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
	private ControllerCurso objectCourseController; // Instantiates an object of the controller

    @Override
    // Method to initialize the activity activity_comparacao_instituicao
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_c);

		objectCourseController = new ControllerCurso(this);

		nameCourse = getIntent().getExtras().getString("cursoSelecionado");
		nameFirstState = getIntent().getExtras().getString("nameFirstState");
		nameSecondState = getIntent().getExtras().getString("nameSecondState");
		nameFirstCity = getIntent().getExtras().getString("nameFirstCity");
		nameSecondCity = getIntent().getExtras().getString("nameSecondCity");
		courseCode = objectCourseController.buscaCodCurso(nameCourse);

		// Stores the course searched
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado1);
		
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		// cursoSelecionado.setText(String.format("%d" , courseCode));

		List<Float> gradeList; // Stores the average grade of the course in the two given cities.

		gradeList = objectCourseController.comparacaoCidade(courseCode, nameFirstState, nameFirstCity, nameSecondState, nameSecondCity);

		float firstAverage = gradeList.get(0); // Stores the average grade of the course in the first city (example: 5,0)
		float secondAverage = gradeList.get(1); // Stores the average grade of the course in the second city (example: 5,0)

		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		String media1String = String.valueOf(firstAverage); // Stores the average grade of the course in the second (example: cinco)
		String media2String = String.valueOf(secondAverage); // Stores the average grade of the course in the second (example: cinco)

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
		points.add(d2);

		BarGraph graph = (BarGraph) findViewById(R.id.graph1);
		graph.setBars(points);
		graph.setUnit(" ");

		graph.setContentDescription("Cidade " + nameFirstCity + " nota: " + String.format("%.3f", firstAverage) + ". E cidade "
				+ nameSecondCity + " nota: " + String.format("%.3f", secondAverage));
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
			View rootView = inflater.inflate(R.layout.fragment_comparacao_result_c, container, false);
			return rootView;
		}
	}
}