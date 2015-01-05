package Entities;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import UI.UIGraphic;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Controller {
	private static int nSitio = 1;
	private static Map<Integer,Sitio> sitiosCercanos;
	private static ValueComparator vcp;
	private static TreeMap<Integer, Sitio> sortedMap;

	public Controller() {
		this.sitiosCercanos = new HashMap<Integer,Sitio>();
		// Ordenar sitios por cercania
		vcp = new ValueComparator(sitiosCercanos);
		sortedMap = new TreeMap<Integer,Sitio>(vcp);

	}

	public static TreeMap<Integer, Sitio> getSortedMap() {
		return sortedMap;
	}

	public static void readingRDF(){
		nSitio = 1;
		String tipo, nombre, telefono, direccion, codigoPostal;
		boolean accesible;
		// create an empty model
		Model model = ModelFactory.createDefaultModel();
		// read the RDF/XML file
		model.read("resources/Museos-updated.ttl", "TTL");
		model.read("resources/Monumentos-updated.ttl", "TTL");
		model.read("resources/PuntosInfoTuristica-updated.ttl", "TTL");
		// write it to standard out
		//		model.write(System.out,"TTL");
		// List all the resources with the properties "geo:lat and geo:long"
		String queryString =
				"PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
						"PREFIX schema: <http://schema.org/> " +
						"PREFIX base: <http://www.example.org/ontology/TourismMadrid#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"SELECT DISTINCT ?Type ?Name ?Accessible ?Telephone ?Email ?Address ?PostalCode ?Latitude ?Longitude "+
						"WHERE {" +
						"?x a ?Type .\n"+
						"?x rdfs:label ?Name .\n"+
						"?x base:accessibility ?Accessible .\n"+
						"?x schema:telephone ?Telephone .\n"+
						"?x schema:streetAddress ?Address .\n"+
						"?x schema:postalCode ?PostalCode. \n" +
						"?x geo:lat ?Latitude .\n"+
						"?x geo:long ?Longitude . } ";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		ResultSet results = qexec.execSelect() ;
		int i = 1;

		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			double latitud = binding.getLiteral("Latitude").getDouble();
			double longitud = binding.getLiteral("Longitude").getDouble();
			double distancia = getDistance(latitud, longitud);
			String name = binding.getLiteral("Name").toString();
			Resource type = binding.getResource("Type");
			//System.out.println(i + ". " + name +" "+ type.toString());
			if(distancia < 20){
				tipo = binding.getResource("Type").toString();
				nombre = binding.getLiteral("Name").getString();
				//horario = binding.getLiteral("Schedule").getString();
				accesible = binding.getLiteral("Accessible").getBoolean();
				//email = binding.getLiteral("Email").getString();
				telefono = binding.getLiteral("Telephone").getString();
				direccion = binding.getLiteral("Address").getString();
				codigoPostal = binding.getLiteral("PostalCode").getString();

				//Crear objeto
				sitiosCercanos.put(nSitio, new Sitio(tipo
						, nombre
						, direccion
						, accesible
						, latitud
						, longitud
						, codigoPostal
						, telefono
						//, email
						, distancia));
				nSitio++;
			}
			
		}
		sortedMap.putAll(sitiosCercanos);
		//Print del sortedMap
		int t=0;
		for (Entry<Integer, Sitio> elemento : sortedMap.entrySet()) {
		    System.out.println(elemento.getKey() + ". \t" + elemento.getValue().getDistance());
		    if(t<6){
		    	UIGraphic.buttons[t].setText((elemento.getValue().name));
		    }
		    t++;
		}
	}

	public static double getDistance(double lat1, double long1){
		//Recogemos los campos
		double latitude = Double.parseDouble(UIGraphic.getLatitude().getText());
		double longitude = Double.parseDouble(UIGraphic.getLongitud().getText());
		return geoDistanceInKm(lat1, long1, latitude, longitude);
	}

	/**
	 * Distancia entre dos puntos geográficas. Debe meterse en grados
	 * 
	 * @param firstLatitude
	 *            Latitude del primer punto
	 * @param firstLongitude
	 *            Longitude del primer punto
	 * @param secondLatitude
	 *            Latitude del primer punto
	 * @param secondLongitude
	 *            Longitude del primer punto
	 * 
	 * @return Distancia en Km entre dos puntos
	 */
	public static double geoDistanceInKm(double firstLatitude,
			double firstLongitude, double secondLatitude, double secondLongitude) {

		//System.out.println(firstLatitude+" "+firstLongitude+" "+secondLatitude+" "+secondLongitude);
		// Conversion de grados a radianes
		double firstLatToRad = Math.toRadians(firstLatitude);
		double secondLatToRad = Math.toRadians(secondLatitude);

		// Diferencia de longitudes
		double deltaLongitudeInRad = Math.toRadians(secondLongitude
				- firstLongitude);

		// Distancia entre dos puntos teniendo en cuenta el radio de la tierra 
		//en kilometros -> 6371
		return Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
				* Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
				* Math.sin(secondLatToRad))
				* 6371;
	}

	public static void main(String[] args){
		System.out.println(Controller.geoDistanceInKm(40.2836326, -3.7935657, 40.4074732, -3.827858));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIGraphic frame = new UIGraphic();
					frame.setVisible(true);
					Controller nuevo = new Controller();
					//nuevo.readingRDF();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}