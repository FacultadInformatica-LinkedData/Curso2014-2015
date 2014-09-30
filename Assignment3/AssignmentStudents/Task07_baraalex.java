package ontologyapi;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.io.InputStream;

/**
 * Task 07: Querying ontologies (RDFs)
 *
 * @author elozano
 */
public class Task07_baraalex {
    public static String ns = "http://somewhere#";

    public static void main(String args[]) {
        String filename = "/home/alex/GitHub/Curso2014-2015/Assignment3/rdf examples/example6.rdf";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in, null);


        // ** TASK 7.1: List all individuals of "Person" **
        OntClass person = model.getOntClass(ns + "Person");
        ExtendedIterator<Individual> personIter = model.listIndividuals(person);
        while (personIter.hasNext()) {
            Individual indiv = personIter.next();
            System.out.println("Individual uri = " + indiv.getURI());
        }

        // ** TASK 7.2: List all subclasses of "Person" **
        ExtendedIterator<OntClass> personsubclasses = person.listSubClasses();
        while (personsubclasses.hasNext()) {
            OntClass subclass = personsubclasses.next();
            System.out.println("Subclass uri = " + subclass.getURI());
        }


        // ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some iRnference... **

        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        reasoner = reasoner.bindSchema(model);

        OntModelSpec ontol = OntModelSpec.RDFS_MEM;
        ontol.setReasoner(reasoner);

        OntModel ontModel = ModelFactory.createOntologyModel(ontol, model);

        OntClass infer = ontModel.getOntClass(ns + "Person");

        System.out.println("Instances of person class: ");

        ExtendedIterator<? extends OntResource> iter = infer.listInstances();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        ExtendedIterator<OntClass> iterPerson = infer.listSubClasses();

        System.out.println("Subclasses of person: ");

        while (iterPerson.hasNext()) {
            System.out.println(iterPerson.next());
        }

    }
}
