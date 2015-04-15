/***********************************************************
 * File: Map.java
 * Purpose: Responsible to show a map with all the
 * 			institutions
***********************************************************/
package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.CourseController;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Map extends Activity
{
	@Override
	// Method to initialize the activity activity_mapa	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		// Holds the information that will be showed on view
		WebView webview = (WebView) findViewById(R.id.webView1);
        
		// Stores the curso's name
		String course = getIntent().getExtras().getString("cursoSelecionado");
		
		// ControllerCurso type object
        CourseController objectCourseController = new CourseController(this);
        
        // Code of the course selected
        int courseCode = objectCourseController.buscaCodCurso(course);
        
        // Holds all federation units that exists on the application
        ArrayList<String> allUfs = (ArrayList<String>) objectCourseController.buscaUf(1);
        
    	// Holds all the average grade of all federal units
        List<String> stateAverageGradeList = new ArrayList<String>();
        
        for(int i = 0; i<allUfs.size(); i++) // For parameter; (0 =< i < todasUfs)
        {	
        	// Holds the average grade of each federal unit
        	String stateAverageGrade = new String();
        	
        	try
        	{
				stateAverageGrade = String.valueOf( objectCourseController.mediaEstado(allUfs.get(i),
						courseCode));
			}
        	
        	catch (Exception e)
        	{
				Log.e(this.getClass().toString(), allUfs.get(i)+
						"Estado nao existe");
				stateAverageGrade="0.0";
			}
        	
        	stateAverageGradeList.add(stateAverageGrade);
        }
        
        String content = "<html>"
                + "<head>"
                + "<script type='text/javascript'"
                + "src='https://www.google.com/jsapi'></script>"
                + "<script type='text/javascript'>"
                + "google.load('visualization', '1', {'packages': ['geochart']});"
                + "google.setOnLoadCallback(drawRegionsMap);"
                + "function drawRegionsMap() {"
                + "var data = google.visualization.arrayToDataTable(["
                + "['Estado', 'Nota Média'],"
                + "['Acre', " + stateAverageGradeList.get(0) + "],"
                + "['Alagoas', " + stateAverageGradeList.get(1) + "],"
                + "['Amazonas', " + stateAverageGradeList.get(2) + "],"
                + "['Amapa', " + stateAverageGradeList.get(3) + "],"
                + "['Bahia', " + stateAverageGradeList.get(4) + "],"
                + "['Ceara', " + stateAverageGradeList.get(5) + "],"
                + "['Distrito Federal', " + stateAverageGradeList.get(6) + "],"
                + "['Espirito Santo', " + stateAverageGradeList.get(7) + "],"
                + "['Goias', " + stateAverageGradeList.get(8) + "],"
                + "['Maranhao', " + stateAverageGradeList.get(9) + "],"
                + "['Minas Gerais', " + stateAverageGradeList.get(10) + "],"
                + "['Mato Grosso do Sul', " + stateAverageGradeList.get(11) + "],"
                + "['Mato Grosso', " + stateAverageGradeList.get(12) + "],"
                + "['Para', " + stateAverageGradeList.get(13) + "],"
                + "['Paraiba', " + stateAverageGradeList.get(14) + "],"
                + "['Pernambuco', " + stateAverageGradeList.get(15) + "],"
                +"['Piaui', " + stateAverageGradeList.get(16) + "],"
                + "['Parana', " + stateAverageGradeList.get(17) + "],"
                + "['Rio de Janeiro', " + stateAverageGradeList.get(18) + "],"
                + "['Rio Grande do Norte', " + stateAverageGradeList.get(19) + "],"
                + "['Rio Grande do Sul', " + stateAverageGradeList.get(20) + "],"
                + "['Rondonia', " + stateAverageGradeList.get(21) + "],"
                + "['Roraima', " + stateAverageGradeList.get(22) + "],"
                + "['Santa Catarina', " + stateAverageGradeList.get(23) + "],"
                + "['Sao Paulo', " + stateAverageGradeList.get(24) + "],"
                + "['Sergipe', " + stateAverageGradeList.get(25) + "],"
                + "['Tocantins', " + stateAverageGradeList.get(26) + "],"            
                + "]);"
                + "var chart = new google.visualization.GeoChart("
                + "document.getElementById('chart_div'));"
                + "chart.draw(data, {width: 1280, height: 720, region: 'BR',"
                + "resolution: 'provinces'});"
                + "};"
                + "</script>"
                + "</head>"
                + "<body>"
                + "<div id='chart_div' style='width: 1024px; height: 768px;'></div>"
                + "</body>" 
                + "</html>";;
        
        // Android settings of the web
        WebSettings webSettings = webview.getSettings();
        
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html",
        		"utf-8", null );
        
        //webview.loadUrl("file:///android_asset/Map.html"); // Can be used in this
        //way too.
	}

	@Override
	// Method to specify the options menu for the activity activity_mapa
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mapa, menu);
		return true;
	}

	@Override
	// Method to recognize when an option on menu is selected	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
			View rootView = inflater.inflate(R.layout.fragment_mapa, container,
					false);
			return rootView;
		}
	}
}
