package Assigment3;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

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
		String filename = "C:\\Users\\MerY\\201409\\WebLinked_SematicData\\src\\Assigment3\\example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		System.out.println("7.1");
		OntClass IndvPerson = model.getOntClass(ns + "Person");
	    ExtendedIterator<Individual> personIter = model.listIndividuals(IndvPerson);
        while (personIter.hasNext()) {
            Individual indiv = personIter.next();
            System.out.println(indiv.getURI()+"\n");
        }

		
		// ** TASK 7.2: List all subclasses of "Person" **
        System.out.println("7.2");
        ExtendedIterator<OntClass> subcPerson = IndvPerson.listSubClasses();
        while (subcPerson.hasNext()) {
            OntClass subclass = subcPerson.next();
            System.out.println(subclass.getURI()+"\n");
        }

		
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
        System.out.println("7.3");
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        reasoner = reasoner.bindSchema(model);

        OntModelSpec ontol = OntModelSpec.RDFS_MEM;
        ontol.setReasoner(reasoner);

        OntModel ontModel = ModelFactory.createOntologyModel(ontol, model);
        OntClass infer = ontModel.getOntClass(ns + "Person");
        
        ExtendedIterator<? extends OntResource> iterator = infer.listInstances();
        ExtendedIterator<OntClass> iterPerson = infer.listSubClasses();
        
        System.out.println("Indirect instances: ");

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        System.out.println("Subclasses: ");

        while (iterPerson.hasNext()) {
            System.out.println(iterPerson.next());
        }

	}
}
