package rdfapi;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * Task 02: Managing statements
 * @author elozano
 *
 */
public class Task02
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
		Resource johnSmith = ResourceFactory.createResource("John Smith");

		// Add to johnSmith the datatype property full name (from the VCARD vocabulary)
		johnSmith.addProperty(VCARD.FN, fullName);
		
		// ** TASK 2.2: Create a new resource for Jane Smith, specifying her full name and email **
		Resource janeSmith = ResourceFactory.createResource("Jane Smith");
		janeSmith.addProperty(VCARD.FN, "Jane Smith");
		janeSmith.addProperty(VCARD.EMAIL, "janeSmith2@mail.com");		
		
		// ** TASK 2.3: Add Jane as a person who John knows through an object property from the FOAF vocabulary**
		johnSmith.addProperty(FOAF.knows, janeSmith);
		
		
		model.write(System.out, "RDF/XML-ABBREV");
	}
}
