

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;


/**
 * Created by viktor on 15/12/14.
 *
 * This class is designed to abstract access to model data
 */

public class LibraryDAO {
	
	private String rdf_file;
	private Model model;
	public static String ns = "http://localhost:3333/mibarrio/";
	public static String schema = "http://schema.org/";
	public static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	public static String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static String owl = "http://www.w3.org/2002/07/owl#";
	public static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String admingeo = "http://data.ordnancesurvey.co.uk/ontology/admingeo/";
	public static String mibarrio = "http://www.semanticweb.org/root/ontologies/2014/11/mibarrio#";

  public LibraryDAO(){
	this.rdf_file = "./rdf examples/rdf_final.ttl"; //HARD_CODED!!!!!
	// Create an empty model
	this.model = ModelFactory.createDefaultModel();
	// Read model in TTL format.
	this.model.read(this.rdf_file, "TURTLE");
  }
  
  public String getRDF(){
	  return this.rdf_file;
  }
  
  public Model getModel(){
	  return this.model;
  }

  //This function returns list of libraries starting by name specified
  public ArrayList<PlaceDTO> getListByName(String nombre){
    ArrayList<PlaceDTO> result = new ArrayList<>();
    
    Resource mibarrio_Library = model.createResource(mibarrio + "Library");
    ResIterator rIter = this.model.listSubjectsWithProperty(RDF.type,mibarrio_Library);
    

	while (rIter.hasNext())
	{
	    Resource r = rIter.nextResource();
	    Property schema_name = this.model.createProperty(schema+"name");
	    Property schema_description = this.model.createProperty(schema+"description");
	    if (r.hasProperty(schema_name)){
	    	String placeName = r.getRequiredProperty( schema_name ).getObject().asLiteral().getString();
	    	if (placeName.contains(nombre)){
	    		double[] location = this.getPlaceCoordinates(placeName.replace(" ",""));
	    		String placeDescription = "";
	    		if(r.hasProperty(schema_description)){
	    			placeDescription = r.getRequiredProperty( schema_description).getObject().asLiteral().getString();
	    		}
	    		PlaceDTO place = new PlaceDTO(placeName,location[0],location[1],placeDescription);
	    		result.add(place);
	    	}
	    }
	}
    return result;
  }
  
  public double[] getPlaceCoordinates(String place){
	double[] location = new double[2];
	location[0] = 0;
	location[1] = 0;
    Resource spatial = this.model.getResource(ns + "Spatial/" + place);
    Property geo_latitude = this.model.createProperty(geo + "lat");
    Property geo_longitude = this.model.createProperty(geo + "long");
    if (spatial.hasProperty(geo_latitude)){
    	location[0] = spatial.getRequiredProperty( geo_latitude ).getObject().asLiteral().getDouble();
    }
    if (spatial.hasProperty(geo_longitude)){
    	location[1] = spatial.getRequiredProperty( geo_longitude).getObject().asLiteral().getDouble();
    }
	return location;
  }
  
  public static void main(String args[])
	{	
		// Create an empty model
		LibraryDAO dao = new LibraryDAO();
		ArrayList<PlaceDTO> places = new ArrayList<PlaceDTO>();
		places = dao.getListByName("Bibli");
		System.out.println("Places size: "+places.size());
		Iterator<PlaceDTO> it = places.iterator();
		while(it.hasNext()){
			PlaceDTO place = it.next();
			System.out.println("Poideportivo. name: "+place.getName()+" lat: "+
		place.getLocation()[0]+" long: "+place.getLocation()[1]+ " description: " + place.getDescription());
		}
	}
}
