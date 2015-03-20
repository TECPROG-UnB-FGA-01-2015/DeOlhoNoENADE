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
import android.util.Log;
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
import android.os.Build;

public class ComparacaoInstituicaoFinal extends Activity {

	private Spinner spinnerEstados, spinnerIES;
	private ControllerCurso controller;
	private String estado, municipio, ies2;
	private String estado1, municipio1, ies1;
	private Spinner spinnerCidades;
	private int codCurso;
	private float nota1, nota2;
	private List<String> dados, dados2;
	private List<String> cursos, municipios, estados;
	private int posicaoIes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao_final);
		this.controller = new ControllerCurso(this);		
		
		TextView cursoSelecionado = (TextView) findViewById(R.id.cursoSelecionado);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		
		codCurso = getIntent().getExtras().getInt("codCurso");
		dados = getIntent().getExtras().getStringArrayList("dadosIes");
		estado1 = getIntent().getExtras().getString("estado1");
		municipio1 = getIntent().getExtras().getString("municipio1");
		ies1 = getIntent().getExtras().getString("ies1");
		nota1 = getIntent().getExtras().getFloat("nota1");
		
		addItensOnSpinnerEstado(codCurso, false);
		addListenerOnButtonBuscar();
		
	}
	
private void addItensOnSpinnerEstado(int codCurso, boolean retira) {
		
		spinnerEstados = (Spinner) findViewById(R.id.estados);
		estados = new ArrayList<String>();
		
		estados = controller.buscaUf(codCurso);
	
		if(retira)
			estados.remove(estado1);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, estados);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerEstados.setAdapter(dataAdapter);
			
			spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
					
						estado = parent.getItemAtPosition(posicao).toString();
						
						addItensOnSpinnerMunicipio(estado, false);
					}		
	
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}
	
	private void addItensOnSpinnerMunicipio(String uf, boolean retira) {
		
		this.spinnerCidades = (Spinner) findViewById(R.id.cidades);
		municipios = controller.buscaCidades(codCurso, uf);
		
		if(retira){
			
			if(municipios.remove(municipio1))
				Log.e(this.getClass().toString(), "retirou m");
		}
				
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, municipios);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidades.setAdapter(dataAdapter);
				
				this.spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						municipio = parent.getItemAtPosition(posicao).toString();
						addItensOnSpinnerIES(estado, municipio, false);
						
					}					

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		
	}
	
	private void addItensOnSpinnerIES(String uf, String cidade, boolean retira) {
		cursos = controller.buscaIesComUfMun(codCurso, uf, cidade);
		this.spinnerIES = (Spinner) findViewById(R.id.spinnerIES);
		
		if(retira){
			if(cursos.remove(ies1)){
				controller.removeIes(posicaoIes);
				Log.e(this.getClass().toString(), "retirou");
			}else
				Log.e(this.getClass().toString(), "nao retirou");
		}
		
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, cursos);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerIES.setAdapter(dataAdapter);
				
				this.spinnerIES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						dados2 = new ArrayList<String>();
						dados2 = controller.getDadosIES(posicao);
						dados2.add(String.format("%.2f",controller.getConceitoDoArrayCursos(posicao)));
						nota2 = controller.getConceitoDoArrayCursos(posicao);
						ies2 = new String();
						ies2 = dados2.get(0);
					
						if(ies2.equalsIgnoreCase(ies1)){
							Log.e(this.getClass().toString(), ies2+" / "+ies1+" ies2=ies1");
							if(cursos.size()==1){
								
								Log.e(this.getClass().toString(), "cursos 1");	
								
								if(municipios.size()==1){
									
									Log.e(this.getClass().toString(), "municipios 1");
									
									addItensOnSpinnerEstado(codCurso, true);
								}else if(municipios.size()>1){
									Log.e(this.getClass().toString(), "municipios > 1");
									addItensOnSpinnerMunicipio(estado, true);
								}
								
							}else if(cursos.size()>1){
								Log.e(this.getClass().toString(), "cursos > 1");	
								posicaoIes = posicao;
								addItensOnSpinnerIES(estado, municipio, true);
							}
						}
						
						
					}
		 
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				
	}

	private void addListenerOnButtonBuscar() {

				Button comparar = (Button) findViewById(R.id.Comparar);
				comparar.setOnClickListener (new OnClickListener(){
					
					@Override
			    	public void onClick(View v) {
						Intent result =  new Intent(ComparacaoInstituicaoFinal.this, ComparacaoResultIES.class);
						result.putStringArrayListExtra("dadosIes1", (ArrayList<String>) dados);
						result.putStringArrayListExtra("dadosIes2", (ArrayList<String>) dados2);
						result.putExtra("CodCurso", codCurso);
						result.putExtra("nota1", nota1);
						result.putExtra("ies1", ies1);
						result.putExtra("nota2", nota2);
						result.putExtra("ies2", ies2);
						
			    		startActivity(result);
			    	}
				});
		
			
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_instituicao_final, menu);
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
					R.layout.fragment_comparacao_instituicao_final, container,
					false);
			return rootView;
		}
	}
}
