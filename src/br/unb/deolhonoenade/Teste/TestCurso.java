
package br.unb.deolhonoenade.Teste;

import android.test.AndroidTestCase;
import br.unb.deolhonoenade.model.Curso;
import br.unb.deolhonoenade.model.Instituicao;
import junit.framework.Assert;

public class TestCurso extends AndroidTestCase
{

	// This method is responsible to signal the Test Startup. It's executed before each Test Method
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	// This method is responsible to signal the Test Ending. It's executed after each Test Method
	protected void tearDown() throws Exception
	{
		super.tearDown();

	}

	// Method to test if class is not null
	public void testCurso()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		Assert.assertNotNull(curso);
	}

	// Method for testing the return of attribute uf from class Curso
	public void testGetUf()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		Assert.assertEquals(curso.getUf(), "DF");
	}

	// Method to test the assignment of uf attribute from Class Curso
	public void testSetUf()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		curso.setUf("AC");
		Assert.assertEquals(curso.getUf(), "AC");
	}

	// Method for testing the return of attribute IES from class Curso
	public void testGetIES()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		Instituicao ies = new Instituicao("UnB", "Universidade", "Publica", 1);

		curso.setIES(ies);

		Assert.assertEquals(ies, curso.getIES());
	}

	// Method to test the assignment of IES attribute from Class Curso
	public void testSetIES()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		Instituicao ies = new Instituicao("UFBA", "Universidade", "Publica", 10);

		curso.setIES(ies);

		Assert.assertSame(ies, curso.getIES());
	}

	// Method for testing the return of attribute id_ies from class Curso
	public void testGetId_ies()
	{
		Instituicao ies = new Instituicao("UFBA", "Universidade", "Publica", 10);

		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", ies);

		Assert.assertEquals(curso.getId_ies(), 0);
	}

	// Method for testing the return of attribute id from class Curso
	public void testGetId()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		Assert.assertEquals(curso.getId(), 3);
	}

	// Method to test the assignment of id attribute from Class Curso
	public void testSetId()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		curso.setId(5);
		Assert.assertEquals(curso.getId(), 5);
	}

	// Method for testing the return of attribute nome from class Curso
	public void testGetNome()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		assertEquals("Direito", curso.getNome());
	}

	// Method to test the assignment of nome attribute from Class Curso
	public void testSetNome()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		curso.setNome("medicina");
		assertSame("medicina", curso.getNome());
	}

	// Method for testing the return of attribute numEstudantes from class Curso
	public void testGetNumEstudantes()
	{
		Curso curso = new Curso(3, 6, "Direito", 100, 15, "portoalegre",
				(float) 2.45, "DF", null);
		assertEquals(100, curso.getNumEstudantes());
	}

	// Method to test the assignment of numEstudantes attribute from Class Curso
	public void testSetNumEstudantes()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		curso.setNumEstudantes(500);
		assertEquals(500, curso.getNumEstudantes());
	}

	// Method for testing the return of attribute numEstudantesInscritos from class Curso
	public void testGetNumEstudantesInscritos()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 5, "portoalegre",
				(float) 2.45, "DF", null);

		assertSame(5, curso.getNumEstudantesInscritos());
	}

	// Method to test the assignment of numEstudantesInscritos attribute from Class Curso
	public void testSetNumEstudantesInscritos()
	{
		Curso curso = new Curso(3, 6, "Direito", 20, 15, "portoalegre",
				(float) 2.45, "DF", null);

		curso.setNumEstudantesInscritos(20);

		assertNotSame(20, curso.getNumEstudantesInscritos());
	}

	// Method for testing the return of attribute municipio from class Curso
	public void testGetMunicipio()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "curitiba",
				(float) 2.45, "DF", null);
		assertEquals("curitiba", curso.getMunicipio());
	}

	// Method to test the assignment of municipio attribute from Class Curso
	public void testSetMunicipio()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);

		curso.setMunicipio("BeloHorizonte");
		assertSame("BeloHorizonte", curso.getMunicipio());
	}

	// Method for testing the return of attribute conceitoEnade from class Curso
	public void testGetConceitoEnade()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 5, "DF", null);
		assertEquals((float) 5, curso.getConceitoEnade());
	}

	// Method to test the assignment of conceitoEnade attribute from Class Curso
	public void testSetConceitoEnade()
	{
		Curso curso = new Curso(3, 6, "Direito", 25, 15, "portoalegre",
				(float) 2.45, "DF", null);
		curso.setConceitoEnade((float) 4);
		assertEquals((float) 4, curso.getConceitoEnade());
	}

}
