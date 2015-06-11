
package view;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.unb.deolhonoenade.R;
import controller.CourseController;

public class RankingResult extends Activity implements ActionBar.OnNavigationListener
{

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private String uf, municipio, tipo, curso, ies;
	private int codCurso;
	private int codIES;
	private CourseController controller;
	private List<String> cursos;
	private boolean comparacao;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_result);

		cursos = new ArrayList<String>();

		if (controller == null)
		{	
			
			controller = new CourseController(this);
	
			curso = getIntent().getExtras().getString("CodigoCurso");
	
			comparacao = getIntent().getExtras().getBoolean("BoolComp");
	
			codCurso = controller.searchCourseCode(curso);
	
			uf = getIntent().getExtras().getString("Estado");
			municipio = getIntent().getExtras().getString("Municipio");
			tipo = getIntent().getExtras().getString("Tipo");

			int tipoInt;
		
		if (tipo.equalsIgnoreCase("Ambas"))
		{
			tipoInt = 0;
		}
		else if (tipo.equalsIgnoreCase("Privada"))
		{
			tipoInt = 1;
		}
		else
		{
			tipoInt = 2;
		}

		if (municipio.equalsIgnoreCase("Todas"))
		{
			if (tipo.equalsIgnoreCase("Ambas"))
			{
				try
				{
					this.getStringCurso(codCurso, uf);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					this.getStringCurso(codCurso, uf, tipoInt);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			if (tipo.equalsIgnoreCase("Ambas"))
			{
				try
				{
					this.getStringCurso(codCurso, uf, municipio);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					this.getStringCurso(codCurso, uf, municipio, tipo);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		}
		else
		{
			// Nothing to do
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		                                                            android.R.layout.simple_list_item_1,
		                                                            cursos);

		ListView listaIES = (ListView) findViewById(R.id.listResult);

		listaIES.setAdapter(dataAdapter);
		listaIES.setTextFilterEnabled(true);
		listaIES.setContentDescription(getString(R.string.title_section1));

		listaIES.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int posicao,
			        long id)
			{
				Intent chamarDados = new Intent(RankingResult.this, DadosIES.class);
				codIES = controller.getInstitutionCode(posicao);
				List<String> dados = null;
				
				try
				{
					dados = controller.getInstitutionInfo(posicao);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				chamarDados.putStringArrayListExtra("dadosIes", (ArrayList<String>) dados);
				chamarDados.putExtra("codIES", codIES);
				startActivity(chamarDados);
			}

		});
	}

	private void getStringCurso(int codCurso2, String uf2, int tipoInt)
	        throws Exception
	{
		cursos = controller.searchCoursesNames(codCurso2, uf2, tipoInt);

	}

	private void getStringCurso(int codCurso2, String uf2) throws Exception
	{
		cursos = controller.searchCoursesNames(codCurso2, uf2);

	}

	private void getStringCurso(int codCurso2, String uf2, String municipio2,
	        String tipo2) throws Exception
	{
		cursos = controller.searchCoursesNames(codCurso2, uf2, municipio2, tipo2);

	}

	private void getStringCurso(int codCurso2, String uf2, String municipio2)
	        throws Exception
	{
		cursos = controller.searchCoursesNames(codCurso2, uf2, municipio2);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
		{
			getActionBar()
			        .setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
		else
		{
			// Nothing to do
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		else
		{
			// Nothing to do			
		}
		return super.onOptionsItemSelected(item);
		
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1)
	{
		return false;
	}
}
