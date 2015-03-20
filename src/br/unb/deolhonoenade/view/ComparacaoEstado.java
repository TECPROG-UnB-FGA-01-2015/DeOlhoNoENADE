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
import br.unb.deolhonoenade.controller.ControllerCurso;
public class ComparacaoEstado extends Activity {
	
	private String curso;
	private int codCurso;
	private ControllerCurso controller;
	private Spinner spinnerEstado1, spinnerEstado2;
	private String estado1, estado2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_estado);
		
		controller = new ControllerCurso(this);
		
		TextView cursoSelecionado = (TextView) findViewById(R.id.stringCurso);
		
		curso = getIntent().getExtras().getString("cursoSelecionado");
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));	
		
		this.codCurso = controller.buscaCodCurso(curso);
		addItensOnSpinnerEstado1(codCurso);
		
		addListenerOnButtonComparar();	
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	private void addItensOnSpinnerEstado1(int codCurso) {
		
		spinnerEstado1 = (Spinner) findViewById(R.id.Estado1);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
			spinnerEstado1.setAdapter(dataAdapter);
			
			spinnerEstado1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						estado1 = parent.getItemAtPosition(posicao).toString();
						addItensOnSpinnerEstado2();						
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}
	
	private void addItensOnSpinnerEstado2() {
		
		spinnerEstado2 = (Spinner) findViewById(R.id.Estado2);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
		
		list.remove(estado1);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerEstado2.setAdapter(dataAdapter);
			
			spinnerEstado2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						estado2 = parent.getItemAtPosition(posicao).toString();						
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}

	private void addListenerOnButtonComparar() {
		
		Button comparar = (Button) findViewById(R.id.Comparar);
		comparar.setOnClickListener (new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(ComparacaoEstado.this, ComparacaoResult.class);
	    		
	    		intent.putExtra("cursoSelecionado", curso);
	            intent.putExtra("Estado1", estado1);
	            intent.putExtra("Estado2", estado2);
	    		startActivity(intent);
	    	}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_estado, menu);
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
					R.layout.fragment_comparacao_estado, container, false);
			return rootView;
		}
	}
}
