
/**
 * Created by viktor on 15/12/14.
 */
public class PlaceDTO {
  private String name;
  private double[] location;
  private String description;

  public PlaceDTO(String name, double latitude, double longitude, String description){
    this.name = name;
    this.description = description;
    this.location = new double[2];
    this.location[0] = latitude;
    this.location[1] = longitude;
  }

  public String getName(){
    return this.name;
  }

  public double[] getLocation(){
    return this.location;
  }
  
  public String getDescription(){
	  return this.description;
  }

}
