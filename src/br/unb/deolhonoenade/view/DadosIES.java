package br.unb.deolhonoenade.view;

import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.ControllerCurso;


public class DadosIES extends Activity implements
		ActionBar.OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private ControllerCurso controller;
	private int codIES;
	private List<String> dados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_ies);
		
		controller = new ControllerCurso(this);
		codIES = Integer.parseInt(getIntent().getExtras().get("codIES").toString());
		dados = getIntent().getExtras().getStringArrayList("dadosIes");
		TextView nomeIES = (TextView) findViewById(R.id.nomeIES);
		nomeIES.setText(dados.get(0));
		TextView orgAca = (TextView) findViewById(R.id.OrganizacaoAcademica);
		orgAca.setText(dados.get(1));
		TextView tipo = (TextView) findViewById(R.id.TipoIES);
		tipo.setText(dados.get(2));
		TextView cidade = (TextView) findViewById(R.id.Cidade);
		cidade.setText(dados.get(3));
		TextView noEstP = (TextView) findViewById(R.id.NoEstudantesP);
		noEstP.setText(dados.get(4));
		TextView noEstI = (TextView) findViewById(R.id.NoEstudantesI);
		noEstI.setText(dados.get(5));
		
		
		/*ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dados);
		
		ListView dadosIES = (ListView) findViewById(R.id.DadosIES);
		
		dadosIES.setAdapter(dataAdapter);*/
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dados_ie, menu);
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
