package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.controller.ControllerCurso;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Mapa extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		WebView webview = (WebView) findViewById(R.id.webView1);
        String curso = getIntent().getExtras().getString("cursoSelecionado");
        ControllerCurso controller = new ControllerCurso(this);
        
        int codCurso = controller.buscaCodCurso(curso);
        
        ArrayList<String> todasUfs = (ArrayList<String>) controller.buscaUf(1);
        
        List<String> medias = new ArrayList<String>();
        
        
        for(int i = 0; i<todasUfs.size(); i++){
        	
        	String mediasE = new String();
        	try {
				mediasE = String.valueOf( controller.mediaEstado(todasUfs.get(i), codCurso));
        		
			} catch (Exception e) {
				Log.e(this.getClass().toString(), todasUfs.get(i)+"Estado nao existe");
				mediasE="0.0";
			}
        	
        	medias.add(mediasE);
        	
        }
        
        String content = "<html>"
                + "<head>"
                + "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
                + "<script type='text/javascript'>"
                + "google.load('visualization', '1', {'packages': ['geochart']});"
                + "google.setOnLoadCallback(drawRegionsMap);"
                + "function drawRegionsMap() {"
                + "var data = google.visualization.arrayToDataTable(["
                + "['Estado', 'Nota Média'],"
                + "['Acre', " + medias.get(0) + "],"
                + "['Alagoas', " + medias.get(1) + "],"
                + "['Amazonas', " + medias.get(2) + "],"
                + "['Amapa', " + medias.get(3) + "],"
                + "['Bahia', " + medias.get(4) + "],"
                + "['Ceara', " + medias.get(5) + "],"
                + "['Distrito Federal', " + medias.get(6) + "],"
                + "['Espirito Santo', " + medias.get(7) + "],"
                + "['Goias', " + medias.get(8) + "],"
                + "['Maranhao', " + medias.get(9) + "],"
                + "['Minas Gerais', " + medias.get(10) + "],"
                + "['Mato Grosso do Sul', " + medias.get(11) + "],"
                + "['Mato Grosso', " + medias.get(12) + "],"
                + "['Para', " + medias.get(13) + "],"
                + "['Paraiba', " + medias.get(14) + "],"
                + "['Pernambuco', " + medias.get(15) + "],"
                +"['Piaui', " + medias.get(16) + "],"
                + "['Parana', " + medias.get(17) + "],"
                + "['Rio de Janeiro', " + medias.get(18) + "],"
                + "['Rio Grande do Norte', " + medias.get(19) + "],"
                + "['Rio Grande do Sul', " + medias.get(20) + "],"
                + "['Rondonia', " + medias.get(21) + "],"
                + "['Roraima', " + medias.get(22) + "],"
                + "['Santa Catarina', " + medias.get(23) + "],"
                + "['Sao Paulo', " + medias.get(24) + "],"
                + "['Sergipe', " + medias.get(25) + "],"
                + "['Tocantins', " + medias.get(26) + "],"            
                + "]);"
                + "var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));"
                + "chart.draw(data, {width: 1280, height: 720, region: 'BR', resolution: 'provinces'});"
                + "};"
                + "</script>"
                + "</head>"
                + "<body>"
                + "<div id='chart_div' style='width: 1024px; height: 768px;'></div>"
                + "</body>" 
                + "</html>";;
        
        
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        
        //Usando o método abaixo (loadDataWithBaseURL) não é necessário o arquivo .html da pasta assets.
        //Sendo assim é executado um "html" em tempo real usando a string 'content' acima.
        //Só que não consegui fazer este funcionar.
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );
        
        //webview.loadUrl("file:///android_asset/Mapa.html"); // Can be used in this way too.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mapa, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_mapa, container,
					false);
			return rootView;
		}
	}

}
