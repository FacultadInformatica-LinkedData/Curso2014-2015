package com.linkeddata.vktrsm.planesmadrid;
//Adapter designed to paint plan's cardviews

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class CardViewAppointmentAdapter extends RecyclerView.Adapter<CardViewAppointmentAdapter.ViewHolder> {
  public ArrayList<Appointment> mDataset;
  public ArrayList<Integer> mTime;

  public CardViewAppointmentAdapter(ArrayList<Appointment> myDataset, ArrayList<Integer> myTime) {
    mDataset = myDataset;
    mTime = myTime;
  }

  //Create new views
  @Override
  public CardViewAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_appointment, null);
    ViewHolder viewHolder = new ViewHolder(itemLayoutView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {

    viewHolder.AppointmentName.setText(mDataset.get(position).getName());
    switch (mDataset.get(position).getType()){
      case 0:
        viewHolder.BackgroundAppointmentImage.setImageResource(R.drawable.bg_gym);
        break;
      case 1:
        viewHolder.BackgroundAppointmentImage.setImageResource(R.drawable.bg_library);
        break;
      case 2:
        viewHolder.BackgroundAppointmentImage.setImageResource(R.drawable.bg_shop);
        break;
    }
    viewHolder.LocationName.setText(mDataset.get(position).getLocationName());

    int minutes = mDataset.get(position).getDuration();

    if(minutes < 60)
      viewHolder.Duration.setText(minutes+" minutos");
    else{
      int hours = minutes/60;
      minutes = minutes%60;
      viewHolder.Duration.setText(hours + " h");
      if(minutes > 0)
        viewHolder.Duration.setText(viewHolder.Duration.getText()+" "+minutes+" m");
    }
    if(position < mDataset.size()-1){
      viewHolder.TransportCardView.setVisibility(View.VISIBLE);
      if(mDataset.get(position+1).getTransportType() == 0) {
        viewHolder.TransportType.setText("Transporte Publico");
        viewHolder.BackgroundTransportImage.setImageResource(R.drawable.bg_publictransport);
      }else {
        viewHolder.TransportType.setText("Transporte Privado");
        viewHolder.BackgroundTransportImage.setImageResource(R.drawable.bg_privatetransport);

      }
      viewHolder.TransportTime.setText(mTime.get(position)+" minutos");

    }

  }

  @Override
  public int getItemCount() {

    return mDataset.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView AppointmentName, TransportType, TransportTime, LocationName, Duration;
    public CardView TransportCardView;
    public ImageView BackgroundAppointmentImage, BackgroundTransportImage;

    public ViewHolder(View itemLayoutView) {
      super(itemLayoutView);
      AppointmentName = (TextView) itemLayoutView.findViewById(R.id.appointment_name_text);
      TransportType = (TextView) itemLayoutView.findViewById(R.id.tvTransporType);
      TransportTime = (TextView) itemLayoutView.findViewById(R.id.tvTransporTime);
      LocationName = (TextView) itemLayoutView.findViewById(R.id.tvLocationName);
      Duration = (TextView) itemLayoutView.findViewById(R.id.tvDuration);
      TransportCardView = (CardView) itemLayoutView.findViewById(R.id.transport_card);
      BackgroundAppointmentImage = (ImageView) itemLayoutView.findViewById(R.id.imageAppointmentBG);
      BackgroundTransportImage = (ImageView) itemLayoutView.findViewById(R.id.imageTransportBG);



    }
  }

}