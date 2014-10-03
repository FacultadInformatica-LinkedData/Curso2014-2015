package ontologyapi;

import java.io.InputStream;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**Task7
 * @author Alex Pilicita
 *
 */
public class Task7
{
	
	public static String ns = "http://somewhere#";
	
	public static void main(String args[])
	{
		String filename = "example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
		
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
		
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		OntClass person = model.getOntClass(ns+"Person");
		ExtendedIterator<Individual> it=model.listIndividuals(person);
		
		if(it.hasNext())
		{
			
			Individual auxind = it.next();
			System.out.println("Person: "+auxind.getLocalName());
		}
		
		// ** TASK 7.2: List all subclasses of "Person" ** //
		System.out.println("TASK 7.2: List all subclasses of ");
		
		ExtendedIterator<OntClass> it2 =person.listSubClasses();
		
		 
		if(it2.hasNext())
		{
			OntClass auxclass = it2.next();
			System.out.println("Lista de Subclasses "+auxclass.getSubClass().getLocalName());
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		//model.write(System.out, "TURTLE");
	}
}