package br.unb.deolhonoenade.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.ControllerCurso;

public class DadosInstituicoes extends Fragment {

	/*public View DadosInstituicoes() {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			{
			View view = inflater.inflate(R.layout.fragment_dados_ies,
			        container, false);
		    return view;
			}
			setContentView(R.layout.activity_dados_ies);
			
			controller = new ControllerCurso(this);
			codIES = Integer.parseInt(getIntent().getExtras().get("codIES").toString());
			dados = this.controller.getDadosIES(codIES);
			TextView nomeIES = (TextView) findViewById(R.id.nomeIES);
			nomeIES.setText(dados.get(0));
			
			dados.remove(0);
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, dados);
			
			ListView dadosIES = (ListView) findViewById(R.id.DadosIES);
			
			dadosIES.setAdapter(dataAdapter);
	}
} */

}
