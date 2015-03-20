package br.unb.deolhonoenade.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import br.unb.deolhonoenade.R;

import java.util.ArrayList;
import java.util.List;

public class MenuInicial extends Activity {

	private Spinner spinnerCurso;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_inicial);
		
		addItensOnSpinnerCurso();
		addListenerOnButtonComparacao();
		addListenerOnButtonRanking();
		addListenerOnButtonMapa();
		
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	private void addListenerOnButtonMapa() {

		final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerCurso);
		Button rank = (Button) findViewById(R.id.Mapa);
		
		rank.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(MenuInicial.this, Mapa.class);
	    		intent.putExtra("cursoSelecionado", String.valueOf(spinner1.getSelectedItem()));
	    		intent.putExtra("BoolComp", false);
	    		startActivity(intent);
	    	}
		});

	}

	private void addItensOnSpinnerCurso() {
		spinnerCurso = (Spinner) findViewById(R.id.spinnerCurso);
		List<String> list = new ArrayList<String>();
		list.add("Administracao");
		list.add("Ciencias Contabeis");
		list.add("Ciencias Economicas");
		list.add("Design");
		list.add("Direito");
		list.add("Jornalismo");
		list.add("Psicologia");
		list.add("Publicidade e Propaganda");
		list.add("Relacoes Internacionais");
		list.add("Secretariado Executivo");
		list.add("Tecnologia em Gestao Comercial");
		list.add("Tecnologia em Gestao de Recursos Humanos");
		list.add("Tecnologia em Gestao Financeira");
		list.add("Tecnologia em Logistica");
		list.add("Tecnologia em Marketing");
		list.add("Tecnologia em Processos Gerenciais");
		list.add("Turismo");
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerCurso.setAdapter(dataAdapter);
		
	}
	
	private void addListenerOnButtonRanking() {

		final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerCurso);
		Button rank = (Button) findViewById(R.id.Ranking);
		
		rank.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(MenuInicial.this, RankingInicial.class);
	    		intent.putExtra("cursoSelecionado", String.valueOf(spinner1.getSelectedItem()));
	    		intent.putExtra("BoolComp", false);
	    		startActivity(intent);
	    	}
		});
		
				
	}

	private void addListenerOnButtonComparacao() {
		
		final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerCurso);
		Button compare = (Button) findViewById(R.id.Comparacao);
				
		compare.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
	    		Intent intent = new Intent(MenuInicial.this, ComparacaoInicial.class);
	    // A ideia aqui era mandar o curso selecionado para a tela comparacao estado.     
				intent.putExtra("cursoSelecionado", String.valueOf(spinner1.getSelectedItem()));
	    		startActivity(intent);
	    	}
		});	
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_inicial2, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intentManual = new Intent(MenuInicial.this, ManualUsuario.class);
			startActivity(intentManual);
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
			View rootView = inflater.inflate(R.layout.fragment_menu_inicial,
					container, false);
			return rootView;
		}
	}

}
