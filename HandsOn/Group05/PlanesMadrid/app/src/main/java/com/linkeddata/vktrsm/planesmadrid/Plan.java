package com.linkeddata.vktrsm.planesmadrid;

import com.linkeddata.vktrsm.planesmadrid.daos.TransportDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by viktor on 10/12/14.
 */
public class Plan  implements Serializable {
  private int[] start = {-1,-1};
  private String name;
  private ArrayList<Appointment> appointments;
  private ArrayList<Integer> transport;


  Plan(){
    this.name = "";
    this.appointments = new ArrayList<Appointment>();
    this.transport = new ArrayList<>();
  }

  public String getName(){
    return this.name;
  }

  public void setName(String name){
    this.name = name;
  }

  public void setStart(int Hours, int Minutes){
    this.start[0] = Hours;
    this.start[1] = Minutes;
  }

  public int[] getStart(){
    return start;
  }

  public ArrayList<Appointment> getAppointments(){
      return this.appointments;
  }

  public ArrayList<Integer> getTransport(){
    return this.transport;
  }

  public void calculateTransport(){
    transport.clear();
    TransportDAO transportDAO = new TransportDAO();
    double[] lastUbication = null;
    for(Appointment appointment:appointments){
      if(lastUbication != null){
        if(appointment.getTransportType() == 0)
          transport.add(transportDAO.getPublicTransportTime(lastUbication, appointment.getLocation()));
        else
          transport.add(transportDAO.getPrivateTransportTime(lastUbication, appointment.getLocation()));
      }

      lastUbication = appointment.getLocation();
    }
  }

  public int[] calculateEnd(){
    int result[] = new int[2];
    result[0] = this.start[0];
    result[1] = this.start[1];

    for(Appointment appointment:appointments){
      result[1] += appointment.getDuration();
      result[0] += result[1]/60;
      result[1] = result[1]%60;
    }
    return result;
  }

}
