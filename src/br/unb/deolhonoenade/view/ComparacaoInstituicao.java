package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.controller.ControllerCurso;
import android.app.Activity;
import android.app.ActionBar;
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
import android.os.Build;

public class ComparacaoInstituicao extends Activity {
	
	private Spinner spinnerEstados, spinnerCidades, spinnerIES;
	private ControllerCurso controller;
	private String estado, municipio, ies1;
	private int codCurso, conceitoEnade;
	private List<String> dados;
	private float nota1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao);
		this.controller = new ControllerCurso(this);
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		
		codCurso = controller.buscaCodCurso(getIntent().getExtras().getString("cursoSelecionado"));
		
		addItensOnSpinnerEstado(codCurso);
		addListenerOnButtonBuscar();
	}
		
	private void addItensOnSpinnerEstado(int codCurso) {
		
		spinnerEstados = (Spinner) findViewById(R.id.estados);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinnerEstados.setAdapter(dataAdapter);
			
			spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						estado = parent.getItemAtPosition(posicao).toString();
						
						addItensOnSpinnerMunicipio(estado);						
					}					
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}
	
	private void addItensOnSpinnerMunicipio(String uf) {
		
		this.spinnerCidades = (Spinner) findViewById(R.id.cidades);
		List<String> list;
		list = controller.buscaCidades(codCurso, uf);
		
				
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidades.setAdapter(dataAdapter);
				
				this.spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						municipio = parent.getItemAtPosition(posicao).toString();
						addItensOnSpinnerIES(estado, municipio);						
					}					

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		
	}
	
	private void addItensOnSpinnerIES(String estado, String municipio) {
		List<String> cursos = controller.buscaIesComUfMun(codCurso, estado, municipio);
		this.spinnerIES = (Spinner) findViewById(R.id.spinnerIES);
						
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, cursos);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerIES.setAdapter(dataAdapter);
				
				this.spinnerIES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						dados = controller.getDadosIES(posicao);
						nota1 = controller.getConceitoDoArrayCursos(posicao);
						ies1 = dados.get(0);
					}
		 
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				
	}

	private void addListenerOnButtonBuscar() {

				Button comparar = (Button) findViewById(R.id.ies1);
				comparar.setOnClickListener (new OnClickListener(){
					
					@Override
			    	public void onClick(View v) {
						Intent result =  new Intent(ComparacaoInstituicao.this, ComparacaoInstituicaoFinal.class);
						result.putStringArrayListExtra("dadosIes", (ArrayList<String>) dados);
						result.putExtra("codCurso", codCurso);
						result.putExtra("municipio1", municipio);
						result.putExtra("estado1", estado);
						result.putExtra("nota1", nota1);
						result.putExtra("ies1", ies1);

			    		startActivity(result);
			    	}
				});
		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_instituicao, menu);
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
					R.layout.fragment_comparacao_instituicao, container, false);
			return rootView;
		}
	}
}
