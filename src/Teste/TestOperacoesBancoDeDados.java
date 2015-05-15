package br.unb.deolhonoenade.Teste;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import br.unb.deolhonoenade.DAO.ImportarBancoDeDados;
import br.unb.deolhonoenade.DAO.OperacoesBancoDeDados;
import br.unb.deolhonoenade.controller.CourseController;
import br.unb.deolhonoenade.model.Course;
import br.unb.deolhonoenade.model.Institution;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestOperacoesBancoDeDados extends AndroidTestCase {
	private OperacoesBancoDeDados opBD;
	private CourseController controller;

	public TestOperacoesBancoDeDados() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCodCurso() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		int codCurso;

		codCurso = opBD.getCodCurso("ADMINISTRACAO");
		Assert.assertEquals(1, codCurso);
	}
	


	public void testGetCodCursoException() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		int codCurso = -1;

		try{
			codCurso = opBD.getCodCurso("ADMINISTRASAO");
		}catch(Error e){
			e.printStackTrace();
		}
		
		Assert.assertEquals(-1,codCurso);
	}

	public void testGetIES() {
		controller = new CourseController(getContext());
		opBD = new OperacoesBancoDeDados(controller.getDatabase());

		Institution ies = new Institution(
				"UNIVERSIDADE FEDERAL DE MATO GROSSO", "UNIVERSIDADES",
				"PUBLICA", 1);
		assertEquals(opBD.getIES(1).getName(), ies.getName());
		assertEquals(opBD.getIES(1).getAcademicOrganization(),
				ies.getAcademicOrganization());
		assertEquals(opBD.getIES(1).getType(), ies.getType());
	}

	public void testGetIESException() {
		controller = new CourseController(getContext());
		opBD = new OperacoesBancoDeDados(controller.getDatabase());

		Institution ies = new Institution(
				"UNIVERSIDADE FEDERAL DE MATO GROSSO", "UNIVERSIDADES",
				"PUBLICA", 1);
		
		String nome;
		
		try{
			nome=opBD.getIES(9859).getName();
		}catch(Error e){
			nome="vazio";
		}
		
		assertEquals("vazio",nome);
		
	}

	public void testGetCursosIntString() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		SQLiteDatabase database = bdados.openDataBase();

		opBD = new OperacoesBancoDeDados(database);

		ArrayList<Course> cursosT = new ArrayList<Course>();
		ArrayList<Course> cursos = new ArrayList<Course>();

		Institution ies1 = new Institution("FACULDADE BARAO DO RIO BRANCO",
				"FACULDADES", "PRIVADA", 2132);
		Institution ies2 = new Institution("FACULDDE DA AMAZONIA OCIDENTAL",
				"FACULDADES", "PRIVADA", 2343);
		Institution ies3 = new Institution(
				"FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
				"FACULDADES", "PRIVADA", 2072);

		Course curso1 = new Course(1, 2072, "ADMINISTRACAO", 29, 26,
				"CRUZEIRO DO SUL", (float) 0.785, "AC", ies3);
		Course curso2 = new Course(1, 2132, "ADMINISTRACAO", 147, 125,
				"RIO BRANCO", (float) 1.605, "AC", ies1);
		Course curso3 = new Course(1, 2343, "ADMINISTRACAO", 49, 48,
				"RIO BRANCO", (float) 1.432, "AC", ies2);

		cursosT.add(curso2);
		cursosT.add(curso3);
		cursosT.add(curso1);

		cursos = opBD.getCourses(1, "AC");
		
		
		for (int i = 0; i < 3; i++)
        {
			Assert.assertEquals(cursosT.get(i).getCourseGrade(), cursos
					.get(i).getCourseGrade());
			
			Assert.assertEquals(cursosT.get(i).getId(), cursos.get(i).getId());
			
			Assert.assertEquals(cursosT.get(i).getId_institution(), cursos.get(i)
					.getId_institution());
			
			Assert.assertEquals(cursosT.get(i).getStudentsNumber(), cursos
					.get(i).getStudentsNumber());
		}
		
	}
	
	public void testGetCursosIntStringException() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		SQLiteDatabase database = bdados.openDataBase();

		opBD = new OperacoesBancoDeDados(database);

		ArrayList<Course> cursos = new ArrayList<Course>();

		try{
			cursos = opBD.getCourses(1, "AS");
		}catch(Error e){
			e.printStackTrace();
			cursos=null;
		}
		Assert.assertNull(cursos);
	}

	public void testGetCursosIntStringString() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		SQLiteDatabase database = bdados.openDataBase();

		opBD = new OperacoesBancoDeDados(database);

		ArrayList<Course> cursosT = new ArrayList<Course>();
		ArrayList<Course> cursos = new ArrayList<Course>();

		Institution ies3 = new Institution(
				"FACULDADE DE DESENVOLVIMENTO SUSTENTAVEL DE CRUZEIRO DO SUL",
				"FACULDADES", "PRIVADA", 2072);

		Course curso1 = new Course(1, 2072, "ADMINISTRACAO", 29, 26,
				"CRUZEIRO DO SUL", (float) 0.785, "AC", ies3);

		cursosT.add(curso1);

		cursos = opBD.getCursos(1, "AC", "CRUZEIRO DO SUL");

		Assert.assertEquals(cursosT.get(0).getCourseGrade(), cursos.get(0)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_institution(), cursos.get(0)
				.getId_institution());
		Assert.assertEquals(cursosT.get(0).getStudentsNumber(), cursos.get(0)
				.getStudentsNumber());

	}

	public void testGetCursosIntStringStringString() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);

		ArrayList<Course> cursosT = new ArrayList<Course>();
		ArrayList<Course> cursos = new ArrayList<Course>();

		Institution ies = new Institution("UNIVERSIDADE DE BRASILIA",
				"UNIVERSIDADES", "PUBLICA", 2);
		Course curso1 = new Course(1, 2, "ADMINISTRACAO", 141, 89, "BRASILIA",
				(float) 3.735, "DF", ies);

		cursosT.add(curso1);
		cursos = opBD.getCursos(1, "DF", "BRASILIA", "PUBLICA");

		Assert.assertEquals(cursosT.get(0).getCourseGrade(), cursos.get(0)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_institution(), cursos.get(0)
				.getId_institution());
		Assert.assertEquals(cursosT.get(0).getStudentsNumber(), cursos.get(0)
				.getStudentsNumber());
	}

	public void testGetCursosIntStringIntForPrivadas() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		ArrayList<Course> cursosT = new ArrayList<Course>();
		ArrayList<Course> cursos = new ArrayList<Course>();

		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);

		Course curso1 = new Course(1, 2132, "ADMINISTRACAO", 147, 125,
				"RIO BRANCO", (float) 1.605, "AC", null);
		Course curso2 = new Course(1, 2343, "ADMINISTRACAO", 49, 48,
				"RIO BRANCO", (float) 1.432, "AC", null);
		Course curso3 = new Course(1, 2072, "ADMINISTRACAO", 29, 26,
				"CRUZEIRO DO SUL", (float) 0.785, "AC", null);

		cursosT.add(curso1);
		cursosT.add(curso2);
		cursosT.add(curso3);

		cursos = opBD.getCursos(1, "AC", 1);

		// compara curso1
		Assert.assertEquals(cursosT.get(0).getCourseGrade(), cursos.get(0)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_institution(), cursos.get(0)
				.getId_institution());
		Assert.assertEquals(cursosT.get(0).getStudentsNumber(), cursos.get(0)
				.getStudentsNumber());

		// compara curso2
		Assert.assertEquals(cursosT.get(1).getCourseGrade(), cursos.get(1)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(1).getId(), cursos.get(1).getId());
		Assert.assertEquals(cursosT.get(1).getId_institution(), cursos.get(1)
				.getId_institution());
		Assert.assertEquals(cursosT.get(1).getStudentsNumber(), cursos.get(1)
				.getStudentsNumber());

		// compara curso3
		Assert.assertEquals(cursosT.get(2).getCourseGrade(), cursos.get(2)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(2).getId(), cursos.get(2).getId());
		Assert.assertEquals(cursosT.get(2).getId_institution(), cursos.get(2)
				.getId_institution());
		Assert.assertEquals(cursosT.get(2).getStudentsNumber(), cursos.get(2)
				.getStudentsNumber());
	}

	public void testGetCursosIntStringIntForPublicas() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());

		ArrayList<Course> cursosT = new ArrayList<Course>();
		ArrayList<Course> cursos = new ArrayList<Course>();

		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);

		Course curso1 = new Course(2, 549, "DIREITO", 64, 47, "RIO BRANCO",
				(float) 3.398, "AC", null);

		cursosT.add(curso1);

		cursos = opBD.getCursos(2, "AC", 2);

		Assert.assertEquals(cursosT.get(0).getCourseGrade(), cursos.get(0)
				.getCourseGrade());
		Assert.assertEquals(cursosT.get(0).getId(), cursos.get(0).getId());
		Assert.assertEquals(cursosT.get(0).getId_institution(), cursos.get(0)
				.getId_institution());
		Assert.assertEquals(cursosT.get(0).getStudentsNumber(), cursos.get(0)
				.getStudentsNumber());
	}

	public void testGetCidades() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		List<String> cidadesT = new ArrayList<String>();
		List<String> cidades = new ArrayList<String>();
		
		
		String cidade1 = new String("CRUZEIRO DO SUL");
		String cidade2 = new String("RIO BRANCO");
		
		
		cidadesT.add(cidade1);
		cidadesT.add(cidade2);
		
		cidades = opBD.getCidades(1, "AC");
		Assert.assertEquals(cidadesT, cidades);
	}

	public void testGetUfs() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
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

		ufs = opBD.getUfs(67);
		
		Assert.assertEquals(ufsT, ufs);
	}

	/*
	 * Teste deve retornar os tipos 'Ambas, PRIVADA e Publica' pois na cidade
	 * Brasilia existem os dois tipos de instituicoes que oferecem o curso de
	 * ADMINISTRACAO
	 */
	public void testGetTipoMunicipioForAllTypes() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();
		
		String tipo1 = new String("Ambas");
		String tipo2 = new String("PRIVADA");
		String tipo3 = new String("PUBLICA");

		tiposT.add(tipo1);
		tiposT.add(tipo2);
		tiposT.add(tipo3);
		
		tipos = opBD.getTipoMunicipio(1, "BRASILIA");
		Assert.assertEquals(tiposT, tipos);
	}
	
	/*
	 * Teste deve retornar somente o tipo 'PRIVADA' pois na cidade
	 * RIO BRANCO existe somente o tipo de instituição PRIVADA que oferece 
	 * o curso de ADMINISTRACAO
	 */
	public void testGetTipoMunicipioForTypePrivada() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();
		
		String tipo1 = new String("PRIVADA");
		
		tiposT.add(tipo1);
		
		tipos = opBD.getTipoMunicipio(1, "RIO BRANCO");
		Assert.assertEquals(tiposT, tipos);
	}

	/*
	 * Teste deve retornar os tipos 'Ambas, PRIVADA e Publica' pois no estado
	 * DF existem os dois tipos de instituicoes que oferecem o curso de
	 * ADMINISTRACAO
	 */
	public void testGetTipoEstadoForAllTypes() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();
		
		String tipo2 = new String("PRIVADA");
		String tipo3 = new String("PUBLICA");

		tiposT.add(tipo2);
		tiposT.add(tipo3);
		
		tipos = opBD.getTipoEstado(1, "DF");
		Assert.assertEquals(tiposT, tipos);
	}
	
	/*
	 * Teste deve retornar somente o tipo 'PRIVADA' pois no estado AC existe 
	 * somente o tipo de instituição PRIVADA que oferece 
	 * o curso de ADMINISTRACAO
	 */
	public void testGetTipoEstadoForTypePrivada() {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = bdados.openDataBase();
		opBD = new OperacoesBancoDeDados(database);
		
		List<String> tiposT = new ArrayList<String>();
		List<String> tipos = new ArrayList<String>();
		
		String tipo1 = new String("PRIVADA");
		
		tiposT.add(tipo1);

		
		tipos = opBD.getTipoEstado(1, "AC");
		Assert.assertEquals(tiposT, tipos);
	}

}
