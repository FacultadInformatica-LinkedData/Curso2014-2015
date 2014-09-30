package rdfapi;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

/**
 * Task 01: Reading and writing RDF files
 * 
 * @author elozano
 * 
 */
public class Task01Jorge
{
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		String filename = "rdf examples/example1.rdf";
		
		// Create an empty model
		Model model = ModelFactory.createDefaultModel();
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);

		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");

		// Read the RDF/XML file
		model.read(in, null);

		// Write it to standard out
		model.write(System.out);

		// ** TASK 1.1: Now write the model in Turtle form **
		model.write(System.out, "TURTLE");
		
		// ** TASK 1.2: Read a new model and merge it with the previous one **
		String filename2 = "rdf examples/example2.rdf";
		
		Model model2 = ModelFactory.createDefaultModel();
		InputStream in2 = FileManager.get().open(filename2);
		if (in2 == null)
			throw new IllegalArgumentException("File: "+filename2+" not found");

		// Read the RDF/XML file
		model2.read(in2, null);
		
		// Merge the models
		Model union = model2.union(model);
		
		// print the merged model
		union.write(System.out, "TURTLE");
		
		
	}
}
