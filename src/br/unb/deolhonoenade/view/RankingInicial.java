package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.ControllerCurso;

public class RankingInicial extends Activity implements
		ActionBar.OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	private Spinner tipUniv, spinnerEstados, spinnerCidades;
	private List<String> tipos = new ArrayList<String>();
	private ArrayList<String> cursos = new ArrayList<String>();
	private String curso, tipo, estado, municipio;
	private int codCurso;
	private static ControllerCurso controller = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_inicial);
		
		if(controller == null)
			controller = new ControllerCurso(this);

		TextView cursoSelecionado = (TextView) findViewById(R.id.stringCurso);

		
		curso = getIntent().getExtras().getString("cursoSelecionado");
		
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		
		this.codCurso = controller.buscaCodCurso(curso);
		
		addItensOnSpinnerEstado(codCurso);
		addListenerOnButtonBuscar();		
		
		if (savedInstanceState == null) {
		} 
	}

	private void addItensOnSpinnerEstado(int codCurso) {
		
		spinnerEstados = (Spinner) findViewById(R.id.SpinnerEstados);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerEstados.setAdapter(dataAdapter);
			
			spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						// Pega o nome pela posicao
						estado = parent.getItemAtPosition(posicao).toString();
						// Imprime um Toast na tela com o nome que foi selecionado
						Toast.makeText(RankingInicial.this, "Estado Selecionado: " + estado, Toast.LENGTH_LONG).show();
						
						addItensOnSpinnerTipo();
						addItensOnSpinnerMunicipio(estado);
						
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}
	
	private void addItensOnSpinnerMunicipio(String uf) {
		
		this.spinnerCidades = (Spinner) findViewById(R.id.spinnerCidade);
		List<String> list;
		list = controller.buscaCidades(codCurso, uf);
		
		
			list.add(0, "Todas");
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidades.setAdapter(dataAdapter);
				
				this.spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						municipio = parent.getItemAtPosition(posicao).toString();
						if(!municipio.equalsIgnoreCase("Todas")){
							addItensOnSpinnerTipo(municipio);
						}
					}
		 
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		
		 		
		
	}
	
	private void addItensOnSpinnerTipo() {
		tipos.clear();
				
		tipos = controller.buscaTiposEstado(codCurso, estado);
		
		if(tipos.size()>=2)
			tipos.add(0,"Ambas");
		
		// Identificando o Spinner
		tipUniv = (Spinner) findViewById(R.id.spinnerTipo);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipUniv.setAdapter(spinnerArrayAdapter);
	
		// Metodo do Spinner para capturar o item selecionado
		tipUniv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				// Pega nome pela posicao
				tipo = parent.getItemAtPosition(posicao).toString();
				// Imprime um Toast na tela com o nome que foi selecionado
				Toast.makeText(RankingInicial.this, "Opcao Selecionada: " + tipo, Toast.LENGTH_LONG).show();
			}
	
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	
	}
	
	private void addItensOnSpinnerTipo(String municipio) {
		tipos.clear();
		tipos = controller.buscaTipos(codCurso, municipio);
	
		// Identificando o Spinner
		tipUniv = (Spinner) findViewById(R.id.spinnerTipo);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipUniv.setAdapter(spinnerArrayAdapter);
	
		// Metodo do Spinner para capturar o item selecionado
		tipUniv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				// Pega nome pela posicao
				tipo = parent.getItemAtPosition(posicao).toString();
				// Imprime um Toast na tela com o nome que foi selecionado
				Toast.makeText(RankingInicial.this, "Opcao Selecionada: " + tipo, Toast.LENGTH_LONG).show();
			}
	
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}
	
	private void addListenerOnButtonBuscar() {

		
		// Botao Buscar
				Button buscar = (Button) findViewById(R.id.buscar);
				buscar.setOnClickListener (new OnClickListener(){
					
					@Override
			    	public void onClick(View v) {
			    		Intent intent = new Intent(RankingInicial.this, RankingResult.class);
			    		
			    		intent.putExtra("CodigoCurso", curso);
			            intent.putExtra("Estado", estado);
			            intent.putExtra("Municipio", municipio);
			            intent.putExtra("Tipo", tipo);

			    		startActivity(intent);
			    	}
				});
		
			
	}


	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ranking_inicial, menu);
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

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		return false;
	}
}
