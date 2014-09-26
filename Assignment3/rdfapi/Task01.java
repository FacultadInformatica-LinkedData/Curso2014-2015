package rdfapi;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import java.io.File;
import java.io.InputStream;

/**
 * Task 01: Reading and writing RDF files
 *
 * @author elozano
 */
public class Task01 {
    /**
     * @param args
     */
    public static void main(String args[]) {
        String filename = "rdf examples" + File.separator + "example1.rdf";

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        //model.read(in, null);


        // Write it to standard out
        // model.write(System.out);

        // ** TASK 1.1: Now write the model in Turtle form **
        model.read(in, "TURTLE");


        // ** TASK 1.2: Read a new model and merge it with the previous one **
        String filename2 = "rdf examples" + File.separator + "example2.rdf";

        // Use the FileManager to find the input file
        InputStream in2 = FileManager.get().open(filename2);

        if (in2 == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in2, "TURTLE");

        // Write it to standard out
        model.write(System.out);

    }
}
