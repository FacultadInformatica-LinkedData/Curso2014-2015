package rdfapi;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * Task 03: Statement-based query
 * @author elozano
 *
 */
public class Task03Jorge
{
	public static String ns = "http://somewhere#";
	public static String foafNS = "http://xmlns.com/foaf/0.1#";
	public static String foafEmailURI = foafNS+"email";
	public static String foafKnowsURI = foafNS+"knows";
	
	public static void main(String args[])
	{
		String filename = "rdf examples/example3.rdf";
		
		// Create an empty model
		Model model = ModelFactory.createDefaultModel();
				
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);

		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");

		// Read the RDF/XML file
		model.read(in, null);
		
		// List all the resources with the property "vcard:FN"
		ResIterator rIter = model.listSubjectsWithProperty(VCARD.FN);
		
		while (rIter.hasNext())
		{
		    Resource r = rIter.nextResource();
		    System.out.println("Subject: "+r.getURI()+"\t"+ "Full name: "+r.getLocalName());
		}
		
		// ** TASK 3.1: List all the resources with the property "vcard:FN" and their full names **
		// ** TASK 3.2: Query all the resources with the family name "Smith" **
		ResIterator rIterFamily = model.listSubjectsWithProperty(VCARD.Family);
		while (rIterFamily.hasNext())
		{
		    Resource r = rIterFamily.nextResource();
		    if(r.getProperty(VCARD.Family).toString().contains("Smith")){
		    	System.out.println("Resources with family name Smith: "+r.getURI());
		    }
		}
		
		// ** TASK 3.3: Query all the resources with an email  **
		ResIterator rIterEmail = model.listSubjectsWithProperty(VCARD.EMAIL);
		while (rIterEmail.hasNext())
		{
		    Resource r = rIterEmail.nextResource();
		    System.out.println("Resources with email: "+r.getURI());
		}
		
		// ** TASK 3.4 (advanced): Query all the subjects knowing "Jane Smith" and list their given names  **
		StmtIterator sIterKnows = model.listStatements(FOAF.knows, arg1, arg2);
		while (rIterKnows.hasNext())
		{
		    Resource r = rIterKnows.nextResource();
		    System.out.println("Resources who knows Jane Smith: "+r.getPropertyResourceValue(FOAF.knows).equals("Jane Smith"));
		}
		
		
	}
}
