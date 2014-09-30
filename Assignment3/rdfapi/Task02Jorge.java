package rdfapi;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * Task 02: Managing statements
 * @author elozano
 *
 */
public class Task02Jorge
{
	public static String ns = "http://somewhere#";
	public static String foafNS = "http://xmlns.com/foaf/0.1#";
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		// Create an empty model
		Model model = ModelFactory.createDefaultModel();
				
		String fullName = "John Smith";
		String johnURI = ns+"JohnSmith";
		
		// ** TASK 2.1: Create the resource John Smith
		Resource johnSmith = model.createResource(johnURI);

		// Add to johnSmith the datatype property full name (from the VCARD vocabulary)
		johnSmith.addProperty(VCARD.NAME, fullName);
		//johnSmith.addLiteral(VCARD.NAME, fullName);
		
		// ** TASK 2.2: Create a new resource for Jane Smith, specifying her full name and email **
		String fullName2 = "Jane Smith";
		String email = "janesmith@email.com";
		String janeURI = ns+"JaneSmith";
		
		// ** TASK 2.3: Add Jane as a person who John knows through an object property from the FOAF vocabulary**
		Resource janeSmith = model.createResource(janeURI);
		
		janeSmith.addProperty(VCARD.FN,fullName2);
		janeSmith.addProperty(VCARD.EMAIL,email);
		
		janeSmith.addProperty(FOAF.knows,fullName);
		
		//model.write(System.out, "RDF/XML-ABBREV");
		model.write(System.out, "TURTLE" );
	}
}
