package com.linkeddata.vktrsm.planesmadrid;

/**
 * Created by viktor on 15/12/14.
 */
public class Transport {
  private int duration, type; //Type: 0 public transport, 1 private transport

  public int getType(){
    return this.type;
  }

  public void setType(int type){
    this.type = type;
  }

  public int getDuration(){
    return this.duration;
  }

  public void setDuration(int duration){
    this.duration = duration;
  }
}
