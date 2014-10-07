package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

/**
 * Task 05: Reading and writing ontologies (RDFs)
 * @author elozano
 *
 */
public class Task05
{
	public static void main(String args[])
	{
		String filename = "example4.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// ** TASK 5.1: Read the ontology from the given file **
		// Use the FileManager to find the input file
		
	
		// Read the RDF/XML file
		
		
		// ** TASK 5.2: Write the ontology **
		
	}
}
