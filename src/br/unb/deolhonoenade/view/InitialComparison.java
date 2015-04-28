/***********************************************************
 * File: InitialComparison.java
 * Purpose: Responsible to show the initial comparison 
***********************************************************/
package br.unb.deolhonoenade.view;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.R.string;
import br.unb.deolhonoenade.view.RankingInicial;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class InitialComparison extends Activity implements
		ActionBar.OnNavigationListener
{
	//String that receives the previous selected variable to make actions on the next screens
	private static final String STATE_SELECTED_NAVIGATION_ITEM =
			"selected_navigation_item";
	
	private String course; // Stores the course's name
	
	@Override
	// Method to initialize the activity activity activity_comparacao_inicial
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_inicial);		
		course = getIntent().getExtras().getString("selectedCourse");
		addListenerOnButtonState();
		addListenerOnButtonInstitution();
		addListenerOnButtonCity();
		addListenerOnButtonType();
	}
	
	// Method to recognize the button "City"
	private void addListenerOnButtonCity()
	{
		// ID Button called "City" that compare 2 different ENADE's average grade from 2 Cities
		Button compareInstitution = (Button) findViewById(R.id.BotaoCidades);
		compareInstitution.setOnClickListener(new OnClickListener()
		{
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * CityComparison view
			 */
			public void onClick(View v) // View variable that is called when a view has been clicked
			{
	    		Intent intent = new Intent(InitialComparison.this,
	    				CityComparison.class);
	    		intent.putExtra("selectedCourse", course);
	    		startActivity(intent);
	    	}	
		});
	}

	// Method to recognize the button "Institution"
	private void addListenerOnButtonInstitution()
	{
		// ID Button called "Institution" that compare 2 different ENADE's average grade from 2 Universities
		Button compareInstitution = (Button) findViewById(R.id.BotaoIES);
		compareInstitution.setOnClickListener(new OnClickListener()
		{
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * InstitutionComparison view
			 */
	    	public void onClick(View v) // View variable that is called when a view has been clicked
			{
				// Creates a new Intent object that passes from one screen to another screen
	    		Intent intent = new Intent(InitialComparison.this,
	    				InstitutionComparison.class);
	    		
	    		intent.putExtra("selectedCourse", course);
	    		startActivity(intent);
	    	}	
		});
	}
	
	// Method to recognize the button "State"
	private void addListenerOnButtonState()
	{
		// ID Button called "State" that compare 2 different ENADE's average grade from 2 States
		Button compareState = (Button) findViewById(R.id.BotaoEstado);
		compareState.setOnClickListener(new OnClickListener()
		{	
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * StateComparison view
			 */
	    	public void onClick(View v) // View variable that is called when a view has been clicked
			{
	    		Intent intent = new Intent(InitialComparison.this,
					StateComparison.class);
	    		intent.putExtra("selectedCourse", course);
	    		startActivity(intent);
	    	}	
		});
	}
	
	// Method to recognize the button "Type"
	private void addListenerOnButtonType()
	{
		/* ID Button called "Type" that compare 2 different ENADE's average grade from
		 * 2 University' Types
		 */
		Button compareType = (Button) findViewById(R.id.BotaoTipo);
		compareType.setOnClickListener(new OnClickListener()
		{	
			@Override
			/* Method to confirm the the mouse click and redirect to
			 * TypeComparison view
			 */
	    	public void onClick(View v) // View variable that is called when a view has been clicked
			{
	    		Intent intent = new Intent(InitialComparison.this,
					TypeComparison.class);
	    		intent.putExtra("selectedCourse", course);
	    		startActivity(intent);
	    	}	
		});
	}
	@Override
	// Method to restore an instance state
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
		{
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
		
		else
		{
			// Nothing to do
		}
	}

	@Override
	// Method to initialize the contents of the Activity's standard options menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comparacao_inicial, menu);
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

	@Override
	// Method to recognize whenever a navigation item on action bar is selected
	public boolean onNavigationItemSelected(int position, long id)
	{
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		return true;
	}

	public static class PlaceholderFragment extends Fragment
	{
		private static final String ARG_SECTION_NUMBER = "section_number";
		public static PlaceholderFragment newInstance(int sectionNumber)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle(); // Creates a new Bundle Instance State
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		// Java default constructor
		public PlaceholderFragment()
		{
			// Nothing to do
		}

		@Override
		/* Method to create and return the view hierarchy associated with the
		 * fragment
		 */
		public View onCreateView(LayoutInflater inflater,
								  ViewGroup container,
								  Bundle savedInstanceState)
		{
			// Hosts all other views on the same place
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_inicial, container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
