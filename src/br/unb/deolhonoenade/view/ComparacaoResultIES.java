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
import br.unb.deolhonoenade.controller.ControllerCurso;

public class ComparacaoResultIES extends Activity {

	private ControllerCurso controller;
	private List<String> result, aux;
	private float nota1, nota2;
	private String ies1, ies2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_ies);
		
		controller = new ControllerCurso(this);
		
		
		
		result = getIntent().getExtras().getStringArrayList("dadosIes1");
		aux = getIntent().getExtras().getStringArrayList("dadosIes2");
		
		ies1 = getIntent().getExtras().getString("ies1");
		nota1 = getIntent().getExtras().getFloat("nota1");
		ies2 = getIntent().getExtras().getString("ies2");
		nota2 = getIntent().getExtras().getFloat("nota2");
		
		
		TextView instiruicao1 = (TextView) findViewById(R.id.nomeIES1);
		instiruicao1.setText(ies1);
		
		TextView instiruicao2 = (TextView) findViewById(R.id.nomeIES2);
		instiruicao2.setText(ies2);
		
		String nota1String = String.valueOf(nota1);
		String nota2String = String.valueOf(nota2);
		
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
		g.setContentDescription("Instituição " + ies1 + " nota: " + String.format("%.3f", nota1)
				+ ". E Instituição " + ies2 + " nota: " + String.format("%.3f", nota2));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_result_ie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_ie, container, false);
			return rootView;
		}
	}
	
	

}
