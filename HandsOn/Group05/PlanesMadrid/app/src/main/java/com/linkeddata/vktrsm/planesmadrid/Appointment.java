package com.linkeddata.vktrsm.planesmadrid;

import com.linkeddata.vktrsm.planesmadrid.dtos.PlaceDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by viktor on 10/12/14.
 */
public class Appointment implements Serializable{
  private String name, locationName;
  private int[] start = {-1,-1};
  private int duration;
  private double[] location;
  private int transportType = 0;
  private int type; //0-gym, 1-library, 2-shop


  Appointment(){

  }

  public void setName(String name){
    this.name = name;
  }

  public String getName (){
    return this.name;
  }

  public void setStart(int Hours, int Minutes){
    this.start[0] = Hours;
    this.start[1] = Minutes;
  }
  public int[] getStart(){
      return this.start;
  }

  public void setDuration(int Minutes){
    this.duration = Minutes;
  }
  public int getDuration (){
    return this.duration;
  }

  public double[] getLocation(){
    return this.location;
  }
  public void parsePlaceDTO (PlaceDTO input){
    this.locationName = input.getName();
    this.location = input.getLocation();

  }

  public int getTransportType() {
    return transportType;
  }

  public void setTransportType(int transportType) {
    this.transportType = transportType;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getLocationName() {
    return locationName;
  }
}
