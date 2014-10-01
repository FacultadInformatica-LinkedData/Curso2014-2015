//Autor: Marco López de Miguel (s100077)

package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author elozano
 *
 */
public class Task07
{
	public static String ns = "http://somewhere#";
	
	public static void main(String args[])
	{
		String filename = "rdf examples/example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		OntClass person = model.createClass(ns+"Person");
		ExtendedIterator<Individual> individuals = model.listIndividuals();
		
		while (individuals.hasNext()) {
			Individual individual = individuals.next();
			System.out.println("Person: " + individual.getLocalName());
		}		
		
		// ** TASK 7.2: List all subclasses of "Person" **
        ExtendedIterator<OntClass> subClases = person.listSubClasses();
		 while (subClases.hasNext()) {
             OntClass essaClasse = (OntClass) subClases.next();
             System.out.println("Subclass: "+essaClasse.getURI());
             
         }
		
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		ExtendedIterator<OntClass> listaSubclases = person.listSubClasses();

		//Imprimiremos las subclases y las instancias de cada subclase
		while (listaSubclases.hasNext()){
            OntClass clase = (OntClass) listaSubclases.next();
    		ExtendedIterator<Individual> listaInstancias = (ExtendedIterator<Individual>) clase.listInstances();
    		
    		while(listaInstancias.hasNext()){
    			System.out.println("Clase: "+clase.getURI()+" Nombre Instancia: "+ listaInstancias.next().getURI());
    		}

			
		}
	}
}
