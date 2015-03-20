package br.unb.deolhonoenade.model;

import java.util.ArrayList;

public class Instituicao {
	
	private ArrayList<Curso> Cursos = new ArrayList<Curso>();	
	
	private String nome;
	private String organizacaoAcademica;
	private String tipo;
	private int codIES;
	
	public Instituicao(String nome, String organizacaoAcademica,
			String tipo, int codIES) {
		super();
		this.nome = nome;
		this.organizacaoAcademica = organizacaoAcademica;
		this.tipo = tipo;
		this.codIES = codIES;
	}
	
	public void adicionaCurso (Curso curso){
		this.Cursos.add(curso);
	}

	public ArrayList<Curso> getCursos() {
		return Cursos;
	}

	public void setCursos(ArrayList<Curso> cursos) {
		Cursos = cursos;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getOrganizacaoAcademica() {
		return organizacaoAcademica;
	}


	public void setOrganizacaoAcademica(String organizacaoAcademica) {
		this.organizacaoAcademica = organizacaoAcademica;
	}



	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public int getCodIES() {
		return codIES;
	}


	public void setCodIES(int codIES) {
		this.codIES = codIES;
	}	

}
