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
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.controller.ControllerCurso;

public class ComparacaoCidades extends Activity {

	private String curso;
	private int codCurso;
	private Spinner spinnerEstado1, spinnerEstado2, spinnerCidade1, spinnerCidade2;
	private String estado1, estado2, cidade1, cidade2;
	private ControllerCurso controller;
	private int posicaoC;

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_cidades);

		controller = new ControllerCurso(this);
		TextView cursoSelecionado = (TextView) findViewById(R.id.stringCurso1);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		curso = getIntent().getExtras().getString("cursoSelecionado");
			
		
		this.codCurso = controller.buscaCodCurso(curso);
		addItensOnSpinnerEstado1(codCurso);
		addItensOnSpinnerEstado2(codCurso);
		addListenerOnButtonComparar();

	}

			private void addItensOnSpinnerEstado1(int codCurso) {
					
					spinnerEstado1 = (Spinner) findViewById(R.id.spinnerEstado1);
					List<String> list = new ArrayList<String>();
					
					list = controller.buscaUf(codCurso);
								
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
							android.R.layout.simple_spinner_item, list);
						dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinnerEstado1.setAdapter(dataAdapter);
						
						spinnerEstado1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					 
								@Override
								public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
									// Pega o nome pela posicao
									estado1 = parent.getItemAtPosition(posicao).toString();										
									addItensOnSpinnerCidade1(estado1);												
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> parent) {
								}	
							});	
				}

			private void addItensOnSpinnerEstado2(int codCurso) {
				
				spinnerEstado2 = (Spinner) findViewById(R.id.spinnerEstado2);
				List<String> list = new ArrayList<String>();				
				list = controller.buscaUf(codCurso);
							
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, list);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinnerEstado2.setAdapter(dataAdapter);					
					spinnerEstado2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				 
							@Override
							public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
								// Pega o nome pela posicao
								estado2 = parent.getItemAtPosition(posicao).toString();									
								addItensOnSpinnerCidade2(estado2, cidade1);
							}
							
							@Override
							public void onNothingSelected(AdapterView<?> parent) {
							}
						});	
			}
			
			private void addItensOnSpinnerCidade1(String uf) {
			this.spinnerCidade1 = (Spinner) findViewById(R.id.spinnerCidade1);
			List<String> list = new ArrayList<String>();
			list = controller.buscaCidades(codCurso, uf);
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidade1.setAdapter(dataAdapter);
				
				this.spinnerCidade1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						cidade1 = parent.getItemAtPosition(posicao).toString();
						
						addItensOnSpinnerCidade2(estado2, cidade1);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}					
				});			
		}
		
		private void addItensOnSpinnerCidade2(String uf,String cidade) {
			this.spinnerCidade2 = (Spinner) findViewById(R.id.spinnerCidade2);
			List<String> list = new ArrayList<String>();
			list = controller.buscaCidades(codCurso, uf);
			list.remove(cidade);
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidade2.setAdapter(dataAdapter);
				
				this.spinnerCidade2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {						
						cidade2 = parent.getItemAtPosition(posicao).toString();
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		}
		
		private void addListenerOnButtonComparar() {
			Button compareInstituicao = (Button) findViewById(R.id.buttonComparacaoCidades);
			compareInstituicao.setOnClickListener(new OnClickListener(){
				
				@Override
		    	public void onClick(View v) {
		    		Intent intent = new Intent(ComparacaoCidades.this, ComparacaoResultC.class);
		    		intent.putExtra("cursoSelecionado", curso);
		    		intent.putExtra("estado1", estado1);
		    		intent.putExtra("estado2", estado2);
		    		intent.putExtra("cidade1", cidade1);
		    		intent.putExtra("cidade2", cidade2);
		    		startActivity(intent);
		    	}	
			});
		}
		
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_cidades, menu);
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

		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_cidades, container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
