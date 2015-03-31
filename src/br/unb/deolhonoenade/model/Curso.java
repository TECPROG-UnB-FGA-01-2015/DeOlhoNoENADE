
package br.unb.deolhonoenade.model;

public class Curso
{

	private Instituicao IES;

	private int id;
	private int id_ies;
	private String nome;
	private int numEstudantes;
	private int numEstudantesInscritos;
	private String municipio;
	private float conceitoEnade;
	private String uf;

	/* Constructor to receive all the attributes as parameters and initialize the
	 * object Curso*/
	public Curso(int id,
				 int id_ies,
				 String nome,
				 int numEstudantes,
				 int numEstudantesInscritos,
				 String municipio,
				 float conceitoEnade,
				 String uf,
				 Instituicao IES)
	{
		this.id = id;
		this.nome = nome;
		this.numEstudantes = numEstudantes;
		this.numEstudantesInscritos = numEstudantesInscritos;
		this.municipio = municipio;
		this.conceitoEnade = conceitoEnade;
		this.uf = uf;
		this.IES = IES;
	}

	// Method to return the content of attribute uf
	public String getUf()
	{
		return uf;
	}

	// Method to set a content on variable uf
	public void setUf(String uf)
	{
		this.uf = uf;
	}

	// Method to return the content of attribute id_ies
	public int getId_ies()
	{
		return id_ies;
	}

	// Method to return the content of attribute id
	public int getId()
	{
		return id;
	}

	// Method to set a content on variable id
	public void setId(int id)
	{
		this.id = id;
	}

	// Method to return the content of attribute IES
	public Instituicao getIES()
	{
		return IES;
	}

	// Method to set a content on variable IES
	public void setIES(Instituicao iES)
	{
		IES = iES;
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

	// Method to return the content of attribute numEstudantes
	public int getNumEstudantes()
	{
		return numEstudantes;
	}

	// Method to set a content on variable numEstudantes
	public void setNumEstudantes(int numEstudantes)
	{
		this.numEstudantes = numEstudantes;
	}

	// Method to return the content of attribute numEstudantesInscritos
	public int getNumEstudantesInscritos()
	{
		return numEstudantesInscritos;
	}

	// Method to set a content on variable numEstudantesInscritos
	public void setNumEstudantesInscritos(int numEstudantesInscritos)
	{

		if(numEstudantesInscritos < this.numEstudantes)
		{
			this.numEstudantesInscritos = numEstudantesInscritos;
		}
		else
		{
			System.err.println("N Estudantes inscritos maior que N Estudantes");
		}

	}

	// Method to return the content of attribute municipio
	public String getMunicipio()
	{
		return municipio;
	}

	// Method to set a content on variable municipio
	public void setMunicipio(String municipio)
	{
		this.municipio = municipio;
	}

	// Method to return the content of attribute conceitoEnade
	public float getConceitoEnade()
	{
		return conceitoEnade;
	}

	// Method to set a content on variable conceitoEnade
	public void setConceitoEnade(float conceitoEnade)
	{
		this.conceitoEnade = conceitoEnade;
	}

}