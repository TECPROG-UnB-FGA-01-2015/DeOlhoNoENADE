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

public class ComparacaoResultTipo extends Activity {

	private ControllerCurso controller;
	private List<String> result = new ArrayList<String>();
	private float resultado1, resultado2;
	private String estado1, estado2, tipo1, tipo2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_tipo);
		
		controller = new ControllerCurso(this);
		resultado1 = getIntent().getExtras().getFloat("resultado1");
		resultado2 = getIntent().getExtras().getFloat("resultado2");
		
		estado1 = getIntent().getExtras().getString("Estado1");
		tipo1 = getIntent().getExtras().getString("Tipo1");
		estado2 = getIntent().getExtras().getString("Estado2");
		tipo2 = getIntent().getExtras().getString("Tipo2");
		
		String eT1 = (estado1 + " " + tipo1);
		String eT2 = (estado2 + " " + tipo2);
		
			TextView estadoTipo1 = (TextView) findViewById(R.id.estadoTipo1);
			estadoTipo1.setText(eT1);
			
			TextView estadoTipo2 = (TextView) findViewById(R.id.estadoTipo2);
			estadoTipo2.setText(eT2);
			
			String resultado1String = String.valueOf(resultado1);
			String resultado2String = String.valueOf(resultado2);
			
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

			g.setContentDescription("Tipo de Instituição " + tipo1 + " no estado " + estado1 + ".   nota: " + String.format("%.3f", resultado1)
					+ ". E tipo de instituição " + tipo2 + " no estado " + estado2 + ".   nota: " + String.format("%.3f", resultado2));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_result_tipo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_result_tipo, container, false);
			return rootView;
		}
	}

}
