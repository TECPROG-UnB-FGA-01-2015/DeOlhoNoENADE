package br.unb.deolhonoenade.Teste;

import android.test.AndroidTestCase;
import br.unb.deolhonoenade.model.Curso;
import br.unb.deolhonoenade.model.Instituicao;
import junit.framework.Assert;

public class TestCurso extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	
	}
	
	public void testCurso() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45, "DF", null);
		Assert.assertNotNull(curso);
	}
	
	public void testGetUf(){
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45, "DF", null);
		
		Assert.assertEquals(curso.getUf(), "DF");
	}
	
	public void testSetUf(){
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45, "DF", null);
		curso.setUf("AC");
		Assert.assertEquals(curso.getUf(), "AC");
	}

	public void testGetIES() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		
		Instituicao ies = new Instituicao("UnB", "Universidade", "Publica", 1);
		
		curso.setIES(ies);
		
		Assert.assertEquals(ies, curso.getIES());
	}

	public void testSetIES() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		
		Instituicao ies = new Instituicao("UFBA", "Universidade", "Publica", 10);
		
		curso.setIES(ies);
		
		Assert.assertSame(ies, curso.getIES());
	}
	
	public void testGetId_ies(){
		Instituicao ies = new Instituicao("UFBA", "Universidade", "Publica", 10);
		
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", ies);
		
		Assert.assertEquals(curso.getId_ies(), 0);
	}
	
	public void testGetId(){
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		Assert.assertEquals(curso.getId(), 3);
	}
	
	public void testSetId(){
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		curso.setId(5);
		Assert.assertEquals(curso.getId(), 5);
	}

	public void testGetNome() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		 		assertEquals("Direito",curso.getNome());
	}

	public void testSetNome() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
 		
 		curso.setNome("medicina");
 		assertSame("medicina", curso.getNome());
	}

	public void testGetNumEstudantes() {
		Curso curso = new Curso(3, 6, "Direito", 100, 15, "portoalegre", (float)2.45,"DF", null);
 		assertEquals(100,curso.getNumEstudantes());
	}

	public void testSetNumEstudantes() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null); 		
 		curso.setNumEstudantes(500);
 		assertEquals(500, curso.getNumEstudantes());
	}

	public void testGetNumEstudantesInscritos() {
		Curso curso = new Curso(3, 6, "Direito", 25, 5, "portoalegre", (float)2.45,"DF", null);
 		
 		assertSame(5,curso.getNumEstudantesInscritos());
	}

	public void testSetNumEstudantesInscritos() {
		Curso curso = new Curso(3, 6, "Direito", 20, 15, "portoalegre", (float)2.45,"DF", null);
		 		
		 		curso.setNumEstudantesInscritos(20);
		 		
		 		assertNotSame(20,curso.getNumEstudantesInscritos());
	}

	public void testGetMunicipio() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "curitiba", (float)2.45,"DF", null);
 		assertEquals("curitiba",curso.getMunicipio());
	}

	public void testSetMunicipio() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
		 		
		 		curso.setMunicipio("BeloHorizonte");
		 		assertSame("BeloHorizonte", curso.getMunicipio());
	}

	public void testGetConceitoEnade() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)5,"DF", null);
 		assertEquals((float)5,curso.getConceitoEnade());
	}

	public void testSetConceitoEnade() {
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre", (float)2.45,"DF", null);
 		curso.setConceitoEnade((float)4);
		assertEquals((float)4,curso.getConceitoEnade());
	}

}
