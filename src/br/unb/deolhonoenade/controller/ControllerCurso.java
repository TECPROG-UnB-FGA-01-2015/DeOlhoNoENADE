package br.unb.deolhonoenade.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.unb.deolhonoenade.DAO.ImportarBancoDeDados;
import br.unb.deolhonoenade.DAO.OperacoesBancoDeDados;
import br.unb.deolhonoenade.model.Curso;
import br.unb.deolhonoenade.model.Instituicao;

public class ControllerCurso {
	private Instituicao IES, instituicao;
	private ArrayList<Curso> cursos = new ArrayList<Curso>();
	
	private SQLiteDatabase db;
	private OperacoesBancoDeDados opBD;

	public ControllerCurso(Context context) {
		ImportarBancoDeDados bdados = new ImportarBancoDeDados(context);
		
		SQLiteDatabase database = bdados.openDataBase();
		
		this.opBD = new OperacoesBancoDeDados(database);
		
		this.db=database;
	}
	
	public SQLiteDatabase getDatabase(){
		return this.db;
	}
	
	public boolean removeIes(int posicao){
		try{
			cursos.remove(posicao);			
			return true;
		}catch(IndexOutOfBoundsException e){
			Log.e(this.getClass().toString(), "cursos IndexOutOfBounds, returning false");
			return false;			
		}
	}
	
	public int getCodIESDoArrayCursos(int posicao) {
		try{
			return cursos.get(posicao).getIES().getCodIES();
		}catch(IndexOutOfBoundsException e){
			Log.e(this.getClass().toString(), "cursos IndexOutOfBounds, returning -1");
			return -1;
		}
	}
	
	public float getConceitoDoArrayCursos(int posicao) {
		try{
			return cursos.get(posicao).getConceitoEnade();
		}catch(IndexOutOfBoundsException e){
			Log.e(this.getClass().toString(), "cursos IndexOutOfBounds, returning -1");
			return -1;
		}
	}
	
	public Instituicao buscaInstituicao(int codIES){
		
		this.instituicao = this.opBD.getIES(codIES);
		
		return instituicao;
	}
	
	public List<String> getDadosIES (int posicao) {
		List<String> dados = new ArrayList<String>();
		try{

			dados.add(cursos.get(posicao).getIES().getNome());
			dados.add(cursos.get(posicao).getIES().getOrganizacaoAcademica());
			dados.add(cursos.get(posicao).getIES().getTipo());
			dados.add(cursos.get(posicao).getMunicipio());
			dados.add(String.format("%d", cursos.get(posicao).getNumEstudantesInscritos()) );
		    dados.add(String.format("%d", cursos.get(posicao).getNumEstudantes()) );
		}catch(IndexOutOfBoundsException e){
			Log.e(this.getClass().toString(), "cursos IndexOutOfBounds");
			throw new Error("IES inexistente nessa posicao");
		}
		return dados;
		
	}
	
	
	public List<Float> comparaEstado(String estado1,String estado2,int codCurso) {
		float media = 0;
		List<Float> Resultado = new ArrayList<Float>();
		List<Curso> cursosEstado2 = new ArrayList<Curso>();
		List<Curso> cursosEstado1 = new ArrayList<Curso>();
		
		cursosEstado1 = this.buscaCurso(codCurso, estado1);
		cursosEstado2 = this.buscaCurso(codCurso, estado2);
		
		media = this.fazMediaConceitoEnade(cursosEstado1);
		
		Resultado.add(media);
		
		media = this.fazMediaConceitoEnade(cursosEstado2);
		
		Resultado.add(media);
		
		return Resultado;
	}
	
	public List<Float> comparacaoCidade(int codCurso, String estado1, String cidade1, String estado2, String cidade2)
	{
		float media = 0;
		List<Float> Resultado = new ArrayList<Float>();
		List<Curso> cursosCidade1 = new ArrayList<Curso>();
		List<Curso> cursosCidade2 = new ArrayList<Curso>();
		
		cursosCidade1 = this.buscaCurso(codCurso, estado1, cidade1);
		cursosCidade2 = this.buscaCurso(codCurso, estado2, cidade2);
		
		media = this.fazMediaConceitoEnade(cursosCidade1);
		
		Resultado.add(media);
		
		media = this.fazMediaConceitoEnade(cursosCidade2);
		
		Resultado.add(media);
		
		return Resultado;
	}
	
	public List<Float> comparacaoTipo(int codCurso, String estado1, String Tipo1, String estado2, String Tipo2)
	{
		float media = 0;
		List<Float> Resultado = new ArrayList<Float>();
		List<Curso> cursosEstadoTipo1 = new ArrayList<Curso>();
		List<Curso> cursosEstadoTipo2 = new ArrayList<Curso>();
		
		if(Tipo1.equalsIgnoreCase("Privada")){
			cursosEstadoTipo1 = this.buscaCurso(codCurso, estado1, 1);
		}else if(Tipo1.equalsIgnoreCase("Publica")){
			cursosEstadoTipo1 = this.buscaCurso(codCurso, estado1, 2);
		}
		
		if(Tipo2.equalsIgnoreCase("Privada")){
			cursosEstadoTipo2 = this.buscaCurso(codCurso, estado2, 1);
		}else if(Tipo2.equalsIgnoreCase("Publica")){
			cursosEstadoTipo2 = this.buscaCurso(codCurso, estado2, 2);
		}
		
			
		media = this.fazMediaConceitoEnade(cursosEstadoTipo1);
		
		Resultado.add(media);
		
		media = this.fazMediaConceitoEnade(cursosEstadoTipo2);
		
		Resultado.add(media);
		
		return Resultado;
	}
	
	public float fazMediaConceitoEnade(List<Curso> cursos){
		float media=0;
		int cont;
		
		if(cursos.size() == 1)
		{
			media = cursos.get(0).getConceitoEnade();
		}else{
			for(cont = 0;cont < cursos.size()-1;cont++){
				media += cursos.get(cont).getConceitoEnade();	
			}
			
			media = media/cont;
		}
		
		
		return media;
	}
	
	public int buscaCodCurso(String nomeCurso){
		int codCurso;
		
		codCurso = this.opBD.getCodCurso(nomeCurso);
		
		return codCurso;
	}
	
	public ArrayList<Curso> buscaCurso(int codCurso, String uf){
		
		this.cursos = this.opBD.getCursos(codCurso, uf);
		
		return cursos;
	}
	
	private List<Curso> buscaCurso(int codCurso, String uf, int tipoInt) {
		this.cursos = this.opBD.getCursos(codCurso, uf, tipoInt);
		
		return cursos;
	}
	
	public ArrayList<Curso> buscaCurso(int codCurso, String uf, String municipio){
		
		this.cursos = this.opBD.getCursos(codCurso, uf, municipio);
		
		return cursos;
	}
	
	public ArrayList<Curso> buscaCurso(int codCurso, String uf, String municipio, String tipo){
		
		this.cursos = this.opBD.getCursos(codCurso, uf, municipio, tipo);
		
		return cursos;
	}
	
	public List<String> buscaCidades(int codCurso, String uf){
		List<String> cidades = new ArrayList<String>();
		cidades = this.opBD.getCidades(codCurso, uf);
		return cidades;
	}
	
	public List<String> buscaTipos(int codCurso, String municipio){
		List<String> tipos = new ArrayList<String>();
		tipos = this.opBD.getTipoMunicipio(codCurso, municipio);
		return tipos;
	}
	
	public List<String> buscaTiposEstado(int codCurso, String estado) {
		List<String> tipos = new ArrayList<String>();
		tipos = this.opBD.getTipoEstado(codCurso, estado);
		return tipos;
	}

	public List<String> buscaUf(int codCurso) {
		List<String> ufs = new ArrayList<String>();
		ufs = this.opBD.getUfs(codCurso);
		return ufs;
	}
	
	public List<String> buscaIesComUfMun(int codCurso2, String uf2,
			String municipio2) {
		
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		listaCursos = this.buscaCurso(codCurso2, uf2, municipio2);
		
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(String.format("%s", listaCursos.get(i).getIES().getNome()));
		}
		
		return cursos;
	}
	
	public List<String> buscaStringCurso(int codCurso2, String uf2) {
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		listaCursos = this.buscaCurso(codCurso2, uf2);
					
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(String.format("%s - %f", listaCursos.get(i).getIES().getNome(),
					listaCursos.get(i).getConceitoEnade()));
		}
		
		return cursos;
	}

	public List<String> buscaStringCurso(int codCurso2, String uf2,
			String municipio2, String tipo2) {
		
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		listaCursos = this.buscaCurso(codCurso2, uf2, municipio2, tipo2);
		
		
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(String.format("%s - %f", listaCursos.get(i).getIES().getNome(),
					listaCursos.get(i).getConceitoEnade()));
		}
		
		return cursos;
	}

	public List<String> buscaStringCurso(int codCurso2, String uf2,
			String municipio2) {
		
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		listaCursos = this.buscaCurso(codCurso2, uf2, municipio2);
		
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(String.format("%s - %f", listaCursos.get(i).getIES().getNome(),
					listaCursos.get(i).getConceitoEnade()));
		}
		
		return cursos;
	}
	
	public List<String> buscaStringCurso(String uf2, int codCurso2, String municipio2) {
		
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		listaCursos = this.buscaCurso(codCurso2, uf2, municipio2);
		
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(listaCursos.get(i).getIES().getNome());
					
		}
		
		return cursos;
	}

	public List<String> buscaStringCurso(int codCurso, String uf, int tipoInt) {
		List<String> cursos = new ArrayList<String>();
		List<Curso> listaCursos = new ArrayList<Curso>();
		
		
		listaCursos = this.buscaCurso(codCurso, uf, tipoInt);
		
		for (int i = 0; i <listaCursos.size(); i++) {
			cursos.add(String.format("%s - %f", listaCursos.get(i).getIES().getNome(),
					listaCursos.get(i).getConceitoEnade()));
		}
		
		return cursos;
	}

	public float mediaEstado(String uf, int codCurso){
		
		List<Curso> cursos = new ArrayList<Curso>();
		
		cursos = buscaCurso(codCurso, uf);
		
		
		return fazMediaConceitoEnade(cursos);
		
	}

}
