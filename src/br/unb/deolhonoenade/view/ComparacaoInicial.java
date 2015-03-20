package br.unb.deolhonoenade.view;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.R.string;
import br.unb.deolhonoenade.view.RankingInicial;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ComparacaoInicial extends Activity implements
		ActionBar.OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private String curso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_inicial);		
		curso = getIntent().getExtras().getString("cursoSelecionado");
		addListenerOnButtonBotaoEstado();
		addListenerOnButtonBotaoInstituicao();
		addListenerOnButtonBotaoCidade();
		addListenerOnButtonBotaoTipo();
		
	}
	
	private void addListenerOnButtonBotaoCidade() {
		Button compareInstituicao = (Button) findViewById(R.id.BotaoCidades);
		compareInstituicao.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(ComparacaoInicial.this, ComparacaoCidades.class);
	    		intent.putExtra("cursoSelecionado", curso);
	    		startActivity(intent);
	    	}	
		});
		
	}

	private void addListenerOnButtonBotaoInstituicao() {
		Button compareInstituicao = (Button) findViewById(R.id.BotaoIES);
		compareInstituicao.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(ComparacaoInicial.this, ComparacaoInstituicao.class);
	    		intent.putExtra("cursoSelecionado", curso);
	    		startActivity(intent);
	    	}	
		});
		
	}

	private void addListenerOnButtonBotaoEstado(){
		Button compareEstado = (Button) findViewById(R.id.BotaoEstado);
		compareEstado.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(ComparacaoInicial.this, ComparacaoEstado.class);
	    		intent.putExtra("cursoSelecionado", curso);
	    		startActivity(intent);
	    	}	
		});
		
	}
	
	private void addListenerOnButtonBotaoTipo(){
		Button compareTipo = (Button) findViewById(R.id.BotaoTipo);
		compareTipo.setOnClickListener(new OnClickListener(){
			
			@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(ComparacaoInicial.this, ComparacaoTipo.class);
	    		intent.putExtra("cursoSelecionado", curso);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comparacao_inicial, menu);
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

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		return true;
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
					R.layout.fragment_comparacao_inicial, container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
