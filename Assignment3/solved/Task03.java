package rdfapi;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
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
public class Task03
{
	public static String ns = "http://somewhere#";
	public static String foafNS = "http://xmlns.com/foaf/0.1#";
	public static String foafEmailURI = foafNS+"email";
	public static String foafKnowsURI = foafNS+"knows";
	
	public static void main(String args[])
	{
		String filename = "/Users/isantana/Dropbox/DOCTORADO/docencia/linkeddata2014/JenaTutorial/example3.rdf";
		
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
		    System.out.println("Subject: "+r.getURI());
		}
		
		// ** TASK 3.1: List all the resources with the property "vcard:FN" and their full names **
		StmtIterator stIter = model.listStatements(null, VCARD.FN, (RDFNode)null);
		
		while (stIter.hasNext())
		{
			Statement st = stIter.next();
			Resource subj = st.getSubject();
			RDFNode fn = st.getObject();
			System.out.println(subj.getURI()+" "+VCARD.FN.getURI()+" "+fn.asLiteral().getString());
		}
		
		// ** TASK 3.2: Query all the resources with the family name "Smith" **
		
		
		// ** TASK 3.3: Query all the resources with an email  **
		
		
		// ** TASK 3.4 (advanced): Query all the subjects knowing "Jane Smith" and list their given names  **
		stIter = model.listStatements(null, VCARD.FN, "Jane Smith");
		
		while (stIter.hasNext())
		{
			Statement st = stIter.next();
			Resource janeSmith = st.getSubject();
			
			Property foafKnows = model.getProperty(foafKnowsURI);
			StmtIterator stIter2 = model.listStatements(null, foafKnows, janeSmith);
			//stIter2 = model.listStatements(null, FOAF.knows, janeSmith);

			while (stIter2.hasNext())
			{
				Statement st2 = stIter2.next();
				Resource subj = st2.getSubject();
				
				StmtIterator stIter3 = model.listStatements(subj, VCARD.Given, (RDFNode)null);
				
				while (stIter3.hasNext())
				{
					Statement st3 = stIter3.next();
					RDFNode given = st3.getObject();
					System.out.println(">>" + subj.getURI()+" "+VCARD.Given.getURI()+" "+given.asLiteral());
				}			
															}		
														}
		
		
	}
}
