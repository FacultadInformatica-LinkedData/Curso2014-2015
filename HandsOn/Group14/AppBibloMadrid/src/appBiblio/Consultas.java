package appBiblio;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.vocabulary.OntEventsVocab;;


/**
 * Task 04: Graph querying
 * @author elozano
 *
 */
public class Consultas
{
	
	private static String ns = "http://exmple.com/biblioteca/";
	private static String filename;
	private static Model model;
	private static InputStream in;
	private static final int CONSULTA_FECHA = 2;
	
	private static final int CONSULTA_TIPO = 0;
	
	private static final int CONSULTA_BIBLIOTECA = 1;
//	public static String foafNS = "http://xmlns.com/foaf/0.1#";
//	public static String foafEmailURI = foafNS+"email";
//	public static String foafKnowsURI = foafNS+"knows";
//	
	public Consultas(String file){
		 filename =file;
		
		// Create an empty model
				 model = ModelFactory.createDefaultModel();
						
				// Use the FileManager to find the input file
				 in = FileManager.get().open(filename);

				if (in == null)
					throw new IllegalArgumentException("File: "+filename+" not found");

				// Read the RDF/XML file
			//	    model.read(new FileInputStream(filename),null,"TTL");
				model.read(in, null);
	}
	
	public static void main(String args[]) throws FileNotFoundException  //main de prueba
	{  
		 filename = "/Users/Sanchez/Documents/workspace/consultas/src/eventosBiblioteca1.rdf";
		
		// Create an empty model
		 model = ModelFactory.createDefaultModel();
				
		// Use the FileManager to find the input file
		 in = FileManager.get().open(filename);

		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");

		// Read the RDF/XML file
	//	    model.read(new FileInputStream(filename),null,"TTL");
		model.read(in, null);
		
		List<String> resultados= consulta("/contenido/actividades/CursosTalleres", 0);
		
		System.out.println("Resultados Array devuelto : ");
		for(int i=0; i<resultados.size(); i++){
			System.out.println(resultados.get(i));
		}
		
	}
	public static List<String> mostrarTodo(){
		
		
		String queryString = 
				"PREFIX vcard: <" + VCARD.getURI() + "> " +
				"SELECT ?Subject ?title "+
				"WHERE { ?Subject vcard:TITLE ?title. } ";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		ResultSet results = qexec.execSelect() ;
		
		
		ArrayList<String> resultadoConsulta = new ArrayList<String>();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			String categoria=subj.getProperty(VCARD.CATEGORIES).getLiteral().toString(); 
			if(categoria.length()>22)
				categoria=categoria.substring(23);
			else 
				categoria="actividad";
			resultadoConsulta.add("Evento: "+subj.getProperty(VCARD.TITLE).getLiteral()+"\n Fecha: "+subj.getProperty(VCARD.Other).getLiteral()+"  Categoría: "+categoria+"\n Biblioteca: "+subj.getProperty(VCARD.Locality).getLiteral());
		}
		
		return resultadoConsulta;
		
	}
	
	public static List<String> consulta(String campo, int tipo){
		
		ArrayList<String> resultadoConsulta = new ArrayList<String>();
		String queryString="";
		switch(tipo){
			case CONSULTA_FECHA: 
				queryString="PREFIX vcard: <" + VCARD.getURI() + "> " +
				"SELECT ?Subject  "+
				"WHERE { ?Subject vcard:Other '"+campo +"'. } ";
				break;
			case CONSULTA_BIBLIOTECA:
				queryString="PREFIX vcard: <" + VCARD.getURI() + "> " +
						"SELECT ?Subject ?title "+
						"WHERE { ?Subject vcard:Locality '" +campo+ "'. } ";
				break;
			case CONSULTA_TIPO:
				queryString="PREFIX vcard: <" + VCARD.getURI() + "> " +
						"SELECT ?Subject ?title "+
						"WHERE { ?Subject vcard:CATEGORIES '"+campo+"'. } ";
				break;
		}
			
				
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
			ResultSet results = qexec.execSelect() ;

		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			String categoria=subj.getProperty(VCARD.CATEGORIES).getLiteral().toString(); 
			if(categoria.length()>22)
				categoria=categoria.substring(23);
			else 
				categoria="actividad";
			resultadoConsulta.add("Evento: "+subj.getProperty(VCARD.TITLE).getLiteral()+"\n Fecha: "+subj.getProperty(VCARD.Other).getLiteral()+"  Categoría: "+categoria+"\n Biblioteca: "+subj.getProperty(VCARD.Locality).getLiteral());
		}
		
		return resultadoConsulta; 
	}
	
	}