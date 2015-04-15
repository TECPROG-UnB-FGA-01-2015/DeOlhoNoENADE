/**********************************************************
 * File: StateComparison.java
 * Purpose: Show a list of states to be compared in pairs
 *********************************************************/

package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.widget.Toast;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.CourseController;

public class StateComparison extends Activity
{

	private String course;
	private int courseCode;
	private CourseController objectCourseController;
	private Spinner firstStateSpinner, secondStateSpinner;
	private String firstState, secondState;

	@Override
	// Method to initialize the activity activity_comparacao_estado
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_estado);

		objectCourseController = new CourseController(this);

		TextView selectedCourse = (TextView) findViewById(R.id.stringCurso);

		course = getIntent().getExtras().getString("cursoSelecionado");
		selectedCourse.setText(getIntent().getExtras().getString(
				"cursoSelecionado"));

		this.courseCode = objectCourseController.buscaCodCurso(course);
		addItensOnSpinnerEstado1(courseCode);

		addListenerOnButtonComparar();

		if(savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container,
					new PlaceholderFragment()).commit();
		}
	}

	// Method to list the State 1 options in a spinner
	private void addItensOnSpinnerEstado1(int courseCode)
	{

		firstStateSpinner = (Spinner) findViewById(R.id.Estado1);
		List<String> stateList = new ArrayList<String>();

		stateList = objectCourseController.searchState(courseCode);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stateList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		firstStateSpinner.setAdapter(dataAdapter);

		firstStateSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id)
					{
						firstState = parent.getItemAtPosition(position).toString();
						addItensOnSpinnerEstado2();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent)
					{
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
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secondStateSpinner.setAdapter(dataAdapter);

		secondStateSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
				Intent intent = new Intent(StateComparison.this,
						ComparacaoResult.class);

				intent.putExtra("cursoSelecionado", course);
				intent.putExtra("Estado1", firstState);
				intent.putExtra("Estado2", secondState);
				startActivity(intent);
			}
		});

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
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{
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
