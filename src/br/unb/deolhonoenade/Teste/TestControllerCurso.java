/***********************************************************
 * File: TestControllerCurso.java
 * Purpose: Responsible to make unit tests in all the ControllerCurso's methods.
 ***********************************************************/

package br.unb.deolhonoenade.Teste;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.DAO.OperacoesBancoDeDados;
import br.unb.deolhonoenade.controller.ControllerCurso;
import br.unb.deolhonoenade.model.Curso;
import br.unb.deolhonoenade.model.Instituicao;
import junit.framework.Assert;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TestControllerCurso extends AndroidTestCase
{

	// This method is responsible to call the parent constructor with no arguments
	public TestControllerCurso()
	{
		super();
	}

	/* This method is responsible to signal the Test Startup.
	 * It's executed before each Test Method */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/* This method is responsible to signal the Test Ending. 
	 * It's executed after each Test Method */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	// This method is responsible to test if the Controller Object isn't Null
	public void testControllerCurso()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		Assert.assertNotNull(controller);
	}

	// This method is responsible to test if the Controller Object from the Database isn't Null
	public void testGetDatabase()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		Assert.assertNotNull(controller.getDatabase());

	}

	/* This method is responsible to test if Universities' names removal was
	 * successful on the Database It ensures that you can't choose the same
	 * University twice with the same Brazilian State to compare them */
	public void testRemoveIesTrue()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		int codCurso = controller.buscaCodCurso("Administracao");

		controller.buscaCurso(codCurso, "SP");

		assertTrue(controller.removeIes(1));

	}

	/* This method is responsible to test if Universities' names removal wasn't
	 * successful on the Database */
	public void testRemoveIesFalse()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		int codCurso = controller.buscaCodCurso("Administracao");

		controller.buscaCurso(codCurso, "SP");

		assertFalse(controller.removeIes(999));

	}

	/* This method is responsible to test if the University's ID (position) is
	 * registered on the correct Brazilian State, based on the Course's ID and
	 * the University's Brazilian State, on the Database */
	public void testGetCodIESDoArrayCursos()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(1, "SP");
		Assert.assertEquals(322, controller.getCodIESDoArrayCursos(2));
	}

	/* This method is responsible to test if the University's ID (position)
	 * isn't registered on the incorrect Brazilian State, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testGetCodIESDoArrayCursosIndexOutOfBounds()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(1, "AC");
		Assert.assertEquals(-1, controller.getCodIESDoArrayCursos(200));
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State has the correct ENADE's grade, based on the Course's ID
	 * and the University's Brazilian State, on the Database */
	public void testGetConceitoDoArrayCursos()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(1, "SP");

		Assert.assertEquals((float) 4.882,
		                    controller.getConceitoDoArrayCursos(2));
	}

	/* This method is responsible to test if the University on a specific
	 * Brazilian State hasn't the correct ENADE's grade, based on the Course's
	 * ID and the University's Brazilian State, on the Database */
	public void testGetConceitoDoArrayCursosIndexOutOfBounds()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(1, "AC");

		Assert.assertEquals((float) -1,
		                    controller.getConceitoDoArrayCursos(200));
	}

	/* This method is responsible to test if the University's name is registered
	 * correctly on the Database */
	public void testBuscaInstituicao()
	{
		Instituicao instituicao;
		ControllerCurso controller = new ControllerCurso(getContext());

		instituicao = controller.buscaInstituicao(1);
		Assert.assertEquals("UNIVERSIDADE FEDERAL DE MATO GROSSO",
		                    instituicao.getNome());
	}

	/* This method is responsible to test if the University's info has the
	 * correct info compared with University's course info, based on Course's ID
	 * and University's Brazilian State, on the Database */
	public void testDadosIES()
	{

		ControllerCurso controller = new ControllerCurso(getContext());
		Instituicao ies = controller.buscaInstituicao(2);
		controller.buscaCurso(1, "DF");
		Curso curso = new Curso(1,
		                        2,
		                        "UNIVERSIDADE DE BRASILIA",
		                        141,
		                        89,
		                        "BRASILIA",
		                        (float) 3.735,
		                        "DF",
		                        ies);
		List<String> dadosIES = controller.getDadosIES(1);

		Assert.assertEquals(dadosIES.get(0), ies.getNome());
		Assert.assertEquals(dadosIES.get(1), ies.getOrganizacaoAcademica());
		Assert.assertEquals(dadosIES.get(2), ies.getTipo());
		Assert.assertEquals(dadosIES.get(3), curso.getMunicipio());
		Assert.assertEquals(dadosIES.get(4),
		                    String.format("%d",
		                                  curso.getNumEstudantesInscritos()));
		Assert.assertEquals(dadosIES.get(5),
		                    String.format("%d", curso.getNumEstudantes()));
	}

	/* This method is responsible to test if the University's course info
	 * (University Name, Academic Organization, University Type - Public/Private
	 * Universities, University City, Registered Students - who participated on
	 * the ENADE's test - and Course's number of students) has null University's
	 * info */
	public void testDadosIESIndexOutOfBounds()
	{

		ControllerCurso controller = new ControllerCurso(getContext());
		Instituicao ies = controller.buscaInstituicao(2);
		controller.buscaCurso(1, "DF");
		Curso curso = new Curso(1,
		                        2,
		                        "UNIVERSIDADE DE BRASILIA",
		                        141,
		                        89,
		                        "BRASILIA",
		                        (float) 3.735,
		                        "DF",
		                        ies);

		List<String> dadosIES = null;

		try
		{
			dadosIES = controller.getDadosIES(9893);
		}
		catch (Error e)
		{
			e.printStackTrace();
		}

		Assert.assertNull(dadosIES);

	}

	/* This method is responsible to test if the comparison between two
	 * different Universities (with two different Brazilian States) was
	 * successful */
	public void testComparaEstado()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		String estado1 = "DF", estado2 = "AM";

		assertEquals(controller.comparaEstado(estado1, estado2, 1).get(0),
		             (float) 1.9448332);
	}

	/* This method is responsible to test if the Course's ID is registered
	 * correctly on the University's name on the Database */
	public void testBuscaCodCurso()
	{
		int codCurso;
		ControllerCurso controller = new ControllerCurso(getContext());

		codCurso = controller.buscaCodCurso("Administracao");
		Assert.assertEquals(1, codCurso);
	}

	/* This method is responsible to test if three different Universities (on
	 * the same Brazilian State) with the same course info (Universities' Names,
	 * Academic Organizations, Universities Type - Public/Private Universities,
	 * Universities' Cities, Registered Students - who participated on the
	 * ENADE's test - and Courses' number of students) are registered correctly
	 * based on Course's ID and University's Brazilian State on the Database */
	public void testBuscaCursoIntString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		ArrayList<Curso> cursosT = new ArrayList<Curso>();
		ArrayList<Curso> cursos = new ArrayList<Curso>();

		Instituicao ies1 = new Instituicao("FACULDADE BARAO DO RIO BRANCO",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2132);
		Instituicao ies2 = new Instituicao("FACULDDE DA AMAZONIA OCIDENTAL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2343);
		Instituicao ies3 = new Instituicao("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2072);

		Curso curso1 = new Curso(1,
		                         2072,
		                         "ADMINISTRACAO",
		                         29,
		                         26,
		                         "CRUZEIRO DO SUL",
		                         (float) 0.785,
		                         "AC",
		                         ies3);
		Curso curso2 = new Curso(1,
		                         2132,
		                         "ADMINISTRACAO",
		                         147,
		                         125,
		                         "RIO BRANCO",
		                         (float) 1.605,
		                         "AC",
		                         ies1);
		Curso curso3 = new Curso(1,
		                         2343,
		                         "ADMINISTRACAO",
		                         49,
		                         48,
		                         "RIO BRANCO",
		                         (float) 1.432,
		                         "AC",
		                         ies2);

		cursosT.add(curso2);
		cursosT.add(curso3);
		cursosT.add(curso1);

		cursos = controller.buscaCurso(1, "AC");

		Assert.assertEquals(cursosT.get(0).getConceitoEnade(), cursos.get(0)
		        .getConceitoEnade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_ies(), cursos.get(0)
		        .getId_ies());
		Assert.assertEquals(cursosT.get(0).getNumEstudantes(), cursos.get(0)
		        .getNumEstudantes());

		Assert.assertEquals(cursosT.get(1).getConceitoEnade(), cursos.get(1)
		        .getConceitoEnade());
		Assert.assertEquals(cursosT.get(1).getId(), cursos.get(1).getId());
		Assert.assertEquals(cursosT.get(1).getId_ies(), cursos.get(1)
		        .getId_ies());
		Assert.assertEquals(cursosT.get(1).getNumEstudantes(), cursos.get(1)
		        .getNumEstudantes());

		Assert.assertEquals(cursosT.get(2).getConceitoEnade(), cursos.get(2)
		        .getConceitoEnade());
		Assert.assertEquals(cursosT.get(2).getId(), cursos.get(2).getId());
		Assert.assertEquals(cursosT.get(2).getId_ies(), cursos.get(2)
		        .getId_ies());
		Assert.assertEquals(cursosT.get(2).getNumEstudantes(), cursos.get(2)
		        .getNumEstudantes());

	}

	/* This method is responsible to test if one University with one course info
	 * (Universities' Names, Academic Organizations, Universities Type -
	 * Public/Private Universities, Universities' Cities, Registered Students -
	 * who participated on the ENADE's test - and Courses' number of students)
	 * are registered correctly based on Course's ID, University's Brazilian
	 * State and University's name on the Database */
	public void testBuscaCursoIntStringString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		ArrayList<Curso> cursosT = new ArrayList<Curso>();
		ArrayList<Curso> cursos = new ArrayList<Curso>();

		Instituicao ies3 = new Instituicao("FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
		                                   "FACULDADES",
		                                   "PRIVADA",
		                                   2072);

		Curso curso1 = new Curso(1,
		                         2072,
		                         "ADMINISTRACAO",
		                         29,
		                         26,
		                         "CRUZEIRO DO SUL",
		                         (float) 0.785,
		                         "AC",
		                         ies3);

		cursosT.add(curso1);

		cursos = controller.buscaCurso(1, "AC", "CRUZEIRO DO SUL");

		Assert.assertEquals(cursosT.get(0).getConceitoEnade(), cursos.get(0)
		        .getConceitoEnade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_ies(), cursos.get(0)
		        .getId_ies());
		Assert.assertEquals(cursosT.get(0).getNumEstudantes(), cursos.get(0)
		        .getNumEstudantes());

	}

	/* This method is responsible to test if one University with one course info
	 * (Universities' Names, Academic Organizations, Universities Type -
	 * Public/Private Universities, Universities' Cities, Registered Students -
	 * who participated on the ENADE's test - and Courses' number of students)
	 * are registered correctly based on Course's ID, University's Brazilian
	 * State, University's City and University's Type on the Database */
	public void testBuscaCursoIntStringStringString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		ArrayList<Curso> cursosT = new ArrayList<Curso>();
		ArrayList<Curso> cursos = new ArrayList<Curso>();

		Instituicao ies = new Instituicao("UNIVERSIDADE DE BRASILIA",
		                                  "UNIVERSIDADES",
		                                  "PUBLICA",
		                                  2);

		Curso curso1 = new Curso(1,
		                         2,
		                         "ADMINISTRACAO",
		                         141,
		                         89,
		                         "BRASILIA",
		                         (float) 3.735,
		                         "DF",
		                         ies);

		cursosT.add(curso1);

		cursos = controller.buscaCurso(1, "DF", "BRASILIA", "PUBLICA");

		Assert.assertEquals(cursosT.get(0).getConceitoEnade(), cursos.get(0)
		        .getConceitoEnade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_ies(), cursos.get(0)
		        .getId_ies());
		Assert.assertEquals(cursosT.get(0).getNumEstudantes(), cursos.get(0)
		        .getNumEstudantes());
	}

	/* This method is responsible to test if two different Brazilian Cities
	 * (with the same Brazilian State) are registered correctly based on
	 * Course's ID and University's Brazilian State on the Database */
	public void testBuscaCidades()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> cidadesT = new ArrayList<String>();
		List<String> cidades = new ArrayList<String>();

		String cidade1 = new String("CRUZEIRO DO SUL");
		String cidade2 = new String("RIO BRANCO");

		cidadesT.add(cidade1);
		cidadesT.add(cidade2);

		cidades = controller.buscaCidades(1, "AC");
		Assert.assertEquals(cidadesT, cidades);
	}

	/* This method is responsible to test if the Acre's University Type is
	 * Private, based on the Course's ID and the University's Type */
	public void testBuscaTiposAC()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();

		String tipo1 = new String("PRIVADA");

		tiposT.add(tipo1);

		tipos = controller.buscaTipos(1, "RIO BRANCO");
		Assert.assertEquals(tiposT, tipos);
	}

	/* This method is responsible to test Brasilia's three Universities Types
	 * (Public, Private and "Both") based on the Course's ID and the
	 * University's Type */
	public void testBuscaTiposDF()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();

		String tipo1 = new String("Ambas");
		String tipo2 = new String("PRIVADA");
		String tipo3 = new String("PUBLICA");

		tiposT.add(tipo1);
		tiposT.add(tipo2);
		tiposT.add(tipo3);

		tipos = controller.buscaTipos(1, "BRASILIA");
		Assert.assertEquals(tiposT, tipos);
	}

	/* This method is responsible to test two Universities' Types (Public and
	 * Private) based on the Course's ID and the University's Brazilian State */
	public void testBuscaTiposEstado()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();

		String tipo2 = new String("PRIVADA");
		String tipo3 = new String("PUBLICA");

		tiposT.add(tipo2);
		tiposT.add(tipo3);

		tipos = controller.buscaTiposEstado(1, "DF");
		Assert.assertEquals(tiposT, tipos);
	}

	/* This method is responsible to test if the Acre's University Type is
	 * Private, based on the Course's ID and the University's Brazilian State */
	public void testBuscaTiposEstadoAC()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();

		String tipo1 = new String("PRIVADA");

		tiposT.add(tipo1);

		tipos = controller.buscaTiposEstado(1, "AC");
		Assert.assertEquals(tiposT, tipos);
	}

	/* This method is responsible to test if all the Brazilian States are
	 * registered correctly on the Database */
	public void testBuscaUf()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> ufsT = new ArrayList<String>();
		List<String> ufs = new ArrayList<String>();

		String uf1 = new String("AL");
		String uf2 = new String("AM");
		String uf3 = new String("AP");
		String uf4 = new String("BA");
		String uf5 = new String("CE");
		String uf6 = new String("DF");
		String uf7 = new String("GO");
		String uf8 = new String("MA");
		String uf9 = new String("MG");
		String uf10 = new String("MS");
		String uf11 = new String("MT");
		String uf12 = new String("PA");
		String uf13 = new String("PB");
		String uf14 = new String("PE");
		String uf15 = new String("PI");
		String uf16 = new String("PR");
		String uf17 = new String("RJ");
		String uf18 = new String("RN");
		String uf19 = new String("RR");
		String uf20 = new String("RS");
		String uf21 = new String("SC");
		String uf22 = new String("SE");
		String uf23 = new String("SP");

		ufsT.add(uf1);
		ufsT.add(uf2);
		ufsT.add(uf3);
		ufsT.add(uf4);
		ufsT.add(uf5);
		ufsT.add(uf6);
		ufsT.add(uf7);
		ufsT.add(uf8);
		ufsT.add(uf9);
		ufsT.add(uf10);
		ufsT.add(uf11);
		ufsT.add(uf12);
		ufsT.add(uf13);
		ufsT.add(uf14);
		ufsT.add(uf15);
		ufsT.add(uf16);
		ufsT.add(uf17);
		ufsT.add(uf18);
		ufsT.add(uf19);
		ufsT.add(uf20);
		ufsT.add(uf21);
		ufsT.add(uf22);
		ufsT.add(uf23);

		ufs = controller.buscaUf(67);
		Assert.assertEquals(ufsT, ufs);
	}

	/* This method is responsible to test if fourteen Universities' names
	 * together with their respectives ENADE's grades are registered correctly,
	 * based on the Courses' ID and the Universities' Brazilian States, on the
	 * Database */
	public void testBuscaStringCursoIntString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> cursosT = new ArrayList<String>();
		List<String> cursos = new ArrayList<String>();

		cursosT.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		cursosT.add("FACULDADE DE CIENCIAS HUMANAS, ECONOMICAS E DA SAUDE DE ARAGUAINA - 2,460000");
		cursosT.add("FACULDADE DE ADMINISTRACAO DE EMPRESAS DE PARAISO DO TOCANTINS - 2,357000");
		cursosT.add("CENTRO UNIVERSITARIO UNIRG - 2,290000");
		cursosT.add("CENTRO UNIVERSITARIO LUTERANO DE PALMAS - 2,185000");
		cursosT.add("INSTITUTO DE ENSINO E PESQUISA OBJETIVO - 2,185000");
		cursosT.add("FACULDADE INTEGRADA DE ARAGUATINS - 2,103000");
		cursosT.add("FACULDADE GUARAI - 2,014000");
		cursosT.add("FACULDADE CATOLICA DOM ORIONE - 1,502000");
		cursosT.add("FACULDADE SERRA DO CARMO - 1,443000");
		cursosT.add("FACULDADE CATOLICA DO TOCANTINS - 1,433000");
		cursosT.add("UNIVERSIDADE DO TOCANTINS - 1,366000");
		cursosT.add("FACULDADE ITOP - 1,202000");
		cursosT.add("FACULDADE SAO MARCOS - 0,209000");

		cursos = controller.buscaStringCurso(1, "TO");

		for (int i = 0; i < 14; i++)
		{
			Assert.assertEquals(cursosT.get(i), cursos.get(i));
		}

	}

	/* This method is responsible to test if two Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States, Universities' Cities and
	 * Universities' Types, on the Database */
	public void testBuscaStringCursoIntStringStringString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> cursosT = new ArrayList<String>();
		List<String> cursos = new ArrayList<String>();

		cursosT.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		cursosT.add("UNIVERSIDADE DO TOCANTINS - 1,366000");

		cursos = controller.buscaStringCurso(1, "TO", "PALMAS", "PUBLICA");

		for (int i = 1; i < 2; i++)
		{
			Assert.assertEquals(cursosT.get(i), cursos.get(i));
		}
	}

	/* This method is responsible to test if seven Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States and Universities' Cities,
	 * on the Database */
	public void testBuscaStringCursoIntStringString()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> cursosT = new ArrayList<String>();
		List<String> cursos = new ArrayList<String>();

		cursosT.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		cursosT.add("CENTRO UNIVERSITARIO LUTERANO DE PALMAS - 2,185000");
		cursosT.add("INSTITUTO DE ENSINO E PESQUISA OBJETIVO - 2,185000");
		cursosT.add("FACULDADE SERRA DO CARMO - 1,443000");
		cursosT.add("FACULDADE CATOLICA DO TOCANTINS - 1,433000");
		cursosT.add("UNIVERSIDADE DO TOCANTINS - 1,366000");
		cursosT.add("FACULDADE ITOP - 1,202000");

		cursos = controller.buscaStringCurso(1, "TO", "PALMAS");
		for (int i = 1; i < 7; i++)
		{
			Assert.assertEquals(cursosT.get(i), cursos.get(i));
		}
	}

	/* This method is responsible to test if five Universities' names together
	 * with their respectives ENADE's grades are registered correctly, based on
	 * the Courses' ID, Universities' Brazilian States and Universities' Types,
	 * on the Database */
	public void testBuscaStringCursoIntStringInt()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<String> cursosT = new ArrayList<String>();
		List<String> cursos = new ArrayList<String>();

		cursosT.add("FUNDACAO UNIVERSIDADE FEDERAL DO TOCANTINS - 2,468000");
		cursosT.add("CENTRO UNIVERSITARIO UNIRG - 2,290000");
		cursosT.add("FACULDADE INTEGRADA DE ARAGUATINS - 2,103000");
		cursosT.add("FACULDADE GUARAI - 2,014000");
		cursosT.add("UNIVERSIDADE DO TOCANTINS - 1,366000");

		cursos = controller.buscaStringCurso(1, "TO", 2);
		for (int i = 1; i < 5; i++)
		{
			Assert.assertEquals(cursosT.get(i), cursos.get(i));
		}

	}

	/* This method is responsible to test if Universities' IDs together with
	 * their respectives Course's IDs are registered correctly, based on the
	 * Courses' ID and Universities' Brazilian States, on the Database */
	public void testCodIESDoArrayCursos()
	{

		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(2, "DF");
		Assert.assertEquals(2, controller.getCodIESDoArrayCursos(0));

	}

	/* This method is responsible to test if Universities' ENADE grades together
	 * with their respectives Courses are registered correctly, based on the
	 * Courses' ID and Universities' Brazilian States, on the Database */
	public void testConceitoDoArrayCursos()
	{

		ControllerCurso controller = new ControllerCurso(getContext());

		controller.buscaCurso(2, "DF");
		Assert.assertEquals((float) 4.482,
		                    controller.getConceitoDoArrayCursos(0));

	}

	/* This method is responsible to test if two Universities's from two Cities
	 * (from two Brazilian States) aren't the same. It ensures that they're from
	 * different Cities and Brazilian States so that you can make comparisons
	 * between them */
	public void testComparacaoCidade()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		String estado1 = "DF", cidade1 = "BRASILIA";
		String estado2 = "AM", cidade2 = "MANAUS";

		assertNotSame(controller.comparacaoCidade(1,
		                                          estado1,
		                                          cidade1,
		                                          estado2,
		                                          cidade2).get(0),
		              "1.900000");

	}

	/* This method is responsible to test the Universities's Types based on
	 * Course's ID, First University's Brazilian State, First University's Type,
	 * Second University's Brazilian State and Second University's Type have
	 * different ENADE's grades average, on the Database */
	public void testComparacaoTipo()
	{
		ControllerCurso controller = new ControllerCurso(getContext());
		List<Float> resultado = controller.comparacaoTipo(1,
		                                                  "AC",
		                                                  "Privada",
		                                                  "AL",
		                                                  "Privada");

		Assert.assertEquals((float) 1.5185001, resultado.get(0));
		Assert.assertEquals((float) 2.285000, resultado.get(1));
	}

	/* This method is responsible to test the Brazilian State's ENADE grade
	 * average, based on Universities's Brazilian State, and Course's ID, on the
	 * Database */
	public void testMediaEstado()
	{
		ControllerCurso controller = new ControllerCurso(getContext());

		Assert.assertEquals((float) 2.249236, controller.mediaEstado("SP", 1));

	}

}
