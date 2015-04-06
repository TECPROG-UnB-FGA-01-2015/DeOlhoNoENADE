/**********************************************************
 * File: Instituicao.java
 * Purpose: Holds all the attributes and methods of the
 * 		    superclass Instituicao.
 *********************************************************/

package br.unb.deolhonoenade.model;

import java.util.ArrayList;

public class Instituicao
{

	private ArrayList<Curso> Cursos = new ArrayList<Curso>();

	private String nome;
	private String organizacaoAcademica;
	private String tipo;
	private int codIES;

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Instituicao*/
	public Instituicao(	String nome,
						String organizacaoAcademica,
						String tipo,
						int codIES)
	{
		super();
		this.nome = nome;
		this.organizacaoAcademica = organizacaoAcademica;
		this.tipo = tipo;
		this.codIES = codIES;
	}

	// Method to insert a class to attribute Cursos
	public void adicionaCurso(Curso curso)
	{
		this.Cursos.add(curso);
	}

	// Method to return the content of attribute Cursos
	public ArrayList<Curso> getCursos()
	{
		return Cursos;
	}

	// Method to set a content on variable Cursos
	public void setCursos(ArrayList<Curso> cursos)
	{
		Cursos = cursos;
	}

	// Method to return the content of attribute nome
	public String getNome()
	{
		return nome;
	}

	// Method to set a content on variable nome
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	// Method to return the content of attribute organizacaoAcademica
	public String getOrganizacaoAcademica()
	{
		return organizacaoAcademica;
	}

	// Method to set a content on variable organizacaoAcademica
	public void setOrganizacaoAcademica(String organizacaoAcademica)
	{
		this.organizacaoAcademica = organizacaoAcademica;
	}

	// Method to return the content of attribute tipo
	public String getTipo()
	{
		return tipo;
	}

	// Method to set a content on variable tipo
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	// Method to return the content of attribute codIES
	public int getCodIES()
	{
		return codIES;
	}

	// Method to set a content on variable codIES
	public void setCodIES(int codIES)
	{
		this.codIES = codIES;
	}

}