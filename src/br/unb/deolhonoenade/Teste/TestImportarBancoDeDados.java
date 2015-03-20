package br.unb.deolhonoenade.Teste;

import android.test.AndroidTestCase;
import br.unb.deolhonoenade.DAO.ImportarBancoDeDados;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

public class TestImportarBancoDeDados extends AndroidTestCase{
	
	public void testCreateDataBase() {
		ImportarBancoDeDados importarDB = new ImportarBancoDeDados(getContext());
		
		boolean bool;
		
		importarDB.createDataBase();
		try {
			InputStream is = new FileInputStream(ImportarBancoDeDados.DB_PATH+ImportarBancoDeDados.DB_NAME);
			bool = true;
		}catch(FileNotFoundException e) {
			bool = false;
		}
		
		importarDB.close();
		Assert.assertEquals(true, bool);
		
	}
	
	public void testOpenDataBase() {
		ImportarBancoDeDados importarDB = new ImportarBancoDeDados(getContext());
		SQLiteDatabase database = null;
		
		database = importarDB.openDataBase();
		Assert.assertNotNull(database);
	}
	
	public void testGetDB() {
		ImportarBancoDeDados importarDB = new ImportarBancoDeDados(getContext());
		
		Assert.assertEquals(importarDB.database, importarDB.getDb());
	}

}
