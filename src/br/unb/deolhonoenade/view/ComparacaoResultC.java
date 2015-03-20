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

public class ComparacaoResultC extends Activity {
	
	private String curso, estado1, estado2, cidade1, cidade2;
	private int media1, media2, codCurso;
	private ControllerCurso controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_result_c);
		
		controller = new ControllerCurso(this);
		
		curso = getIntent().getExtras().getString("cursoSelecionado");
		estado1 = getIntent().getExtras().getString("estado1");
		estado2 = getIntent().getExtras().getString("estado2");
		cidade1 = getIntent().getExtras().getString("cidade1");
		cidade2 = getIntent().getExtras().getString("cidade2");
		codCurso = controller.buscaCodCurso(curso);
		
		
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado1);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		//cursoSelecionado.setText(String.format("%d" , codCurso));
		
		List<Float> list;
		
		list = controller.comparacaoCidade(codCurso, estado1, cidade1, estado2, cidade2);
		
		float media1 = list.get(0);
		float media2 = list.get(1);		

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		String media1String = String.valueOf(media1);
		String media2String = String.valueOf(media2);
		
		ArrayList<Bar> points = new ArrayList<Bar>();
		Bar d = new Bar();
		d.setColor(Color.parseColor("#99CC00"));
		d.setName(cidade1);
		d.setValue(Float.parseFloat(media1String.substring(0, 5)));
		Bar d2 = new Bar();
		d2.setColor(Color.parseColor("#FFBB33"));
		d2.setName(cidade2);
		d2.setValue(Float.parseFloat(media2String.substring(0, 5)));
		points.add(d);
		points.add(d2);

		BarGraph g = (BarGraph)findViewById(R.id.graph1);
		g.setBars(points);
		g.setUnit(" ");
		
		g.setContentDescription("Cidade " + cidade1 + " nota: " + String.format("%.3f", media1)
				+ ". E cidade " + cidade2 + " nota: " + String.format("%.3f", media2));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_result_c, menu);
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
					R.layout.fragment_comparacao_result_c, container, false);
			return rootView;
		}
	}
}
