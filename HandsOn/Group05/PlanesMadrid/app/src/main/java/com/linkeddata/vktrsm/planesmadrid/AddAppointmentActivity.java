package com.linkeddata.vktrsm.planesmadrid;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.linkeddata.vktrsm.planesmadrid.daos.GymsDAO;
import com.linkeddata.vktrsm.planesmadrid.daos.LibrariesDAO;
import com.linkeddata.vktrsm.planesmadrid.daos.ShopsDAO;
import com.linkeddata.vktrsm.planesmadrid.dtos.PlaceDTO;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;
import java.util.Calendar;


public class AddAppointmentActivity extends ActionBarActivity {

  private Appointment appointmentResult = new Appointment();
  private ArrayList<PlaceDTO> myDataset;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_appointment);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff673ab7));
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getWindow().setStatusBarColor(0xff512da8);
    }

    SeekBar durationBar = (SeekBar) findViewById(R.id.seekBarDuration);
    durationBar.setMax((5*60)/20); //5hours in steps of 20 minutes

    durationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //Toast.makeText(getApplicationContext(),"Seleccionado "+progress, Toast.LENGTH_SHORT).show();
        TextView tMinutes = (TextView) findViewById(R.id.tvMinutes);
        int minutes = (progress+1) * 20;
        appointmentResult.setDuration(minutes);

        if(minutes < 60)
          tMinutes.setText(minutes+" minutos");
        else{
          int hours = minutes/60;
          minutes = minutes%60;
          tMinutes.setText(hours + " horas");
          if(minutes > 0)
            tMinutes.setText(tMinutes.getText()+" y "+minutes+" minutos");
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {}

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {}
    });
    durationBar.setProgress(6);

    ImageButton gymButton = (ImageButton) findViewById(R.id.GymButton);
    ImageButton libraryButton = (ImageButton) findViewById(R.id.LibraryButton);
    ImageButton shopButton = (ImageButton) findViewById(R.id.LocalButton);

    gymButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog = new Dialog(AddAppointmentActivity.this);
        dialog.setContentView(R.layout.dialog_appointment);
        appointmentResult.setType(0);
        dialog.setTitle("Selecciona Polideportivo:");
        SearchView searchView = (SearchView) dialog.findViewById(R.id.searchView);
        searchView.setIconified(false);

        RecyclerView.LayoutManager mLayoutManager;
        final GymsDAO gymsDAO = new GymsDAO();
        myDataset = gymsDAO.getListByName("");

        final RecyclerView.Adapter mAdapter = new CardViewPlaceAdapter(myDataset);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            myDataset.clear();
            myDataset.addAll(gymsDAO.getListByName(newText));
            mAdapter.notifyDataSetChanged();
            return false;
          }
        });

        RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_options);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(dialog.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(dialog.getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
        {
          @Override
          public void onItemClick(View view, int position) {
            YoYo.with(Techniques.Shake).duration(100).playOn(view);
            appointmentResult.parsePlaceDTO(myDataset.get(position));
            dialog.dismiss();
          }

          @Override
          public void onItemLongClick(View view, int position) {
          }
        }));

        dialog.show();
      }
    });

    libraryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog = new Dialog(AddAppointmentActivity.this);
        dialog.setContentView(R.layout.dialog_appointment);
        appointmentResult.setType(1);
        dialog.setTitle("Selecciona Biblioteca:");
        SearchView searchView = (SearchView) dialog.findViewById(R.id.searchView);
        searchView.setIconified(false);

        RecyclerView.LayoutManager mLayoutManager;
        final LibrariesDAO librariesDAO = new LibrariesDAO();
        myDataset = librariesDAO.getListByName("");

        final RecyclerView.Adapter mAdapter = new CardViewPlaceAdapter(myDataset);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            myDataset.clear();
            myDataset.addAll(librariesDAO.getListByName(newText));
            mAdapter.notifyDataSetChanged();
            return false;
          }
        });

        RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_options);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(dialog.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(dialog.getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
        {
          @Override
          public void onItemClick(View view, int position) {
            YoYo.with(Techniques.Shake).duration(100).playOn(view);
            appointmentResult.parsePlaceDTO(myDataset.get(position));
            dialog.dismiss();
          }

          @Override
          public void onItemLongClick(View view, int position) {
          }
        }));

        dialog.show();
      }
    });

    shopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog = new Dialog(AddAppointmentActivity.this);
        dialog.setContentView(R.layout.dialog_appointment);
        appointmentResult.setType(2);
        dialog.setTitle("Selecciona Local:");
        SearchView searchView = (SearchView) dialog.findViewById(R.id.searchView);
        searchView.setIconified(false);

        RecyclerView.LayoutManager mLayoutManager;
        final ShopsDAO shopsDAO = new ShopsDAO();
        myDataset = shopsDAO.getListByName("");

        final RecyclerView.Adapter mAdapter = new CardViewPlaceAdapter(myDataset);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            myDataset.clear();
            myDataset.addAll(shopsDAO.getListByName(newText));
            mAdapter.notifyDataSetChanged();
            return false;
          }
        });

        RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_options);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(dialog.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(dialog.getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
          @Override
          public void onItemClick(View view, int position) {
            YoYo.with(Techniques.Shake).duration(100).playOn(view);
            appointmentResult.parsePlaceDTO(myDataset.get(position));
            dialog.dismiss();
          }

          @Override
          public void onItemLongClick(View view, int position) {
          }
        }));

        dialog.show();
      }
    });
    Button createButton = (Button) findViewById(R.id.buttonCreate);
    createButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText nameField = (EditText) findViewById(R.id.etName);
        appointmentResult.setName(nameField.getText().toString());
        RadioButton rbPublicTransport = (RadioButton) findViewById(R.id.rbPublicTransport);
        if(rbPublicTransport.isChecked())
          appointmentResult.setTransportType(0);
        else
          appointmentResult.setTransportType(1);

        //Check if current plan is valid!
        if(appointmentResult.getName().length() > 0 && appointmentResult.getLocation() != null)
          finishWithResult();
        else
          Toast.makeText(getApplicationContext(),"Rellena todos los campos", Toast.LENGTH_SHORT).show();

        return;
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_add_appointment, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void finishWithResult()
  {
    Bundle conData = new Bundle();
    conData.putSerializable("Appointment", appointmentResult);
    Intent intent = new Intent();
    intent.putExtras(conData);
    setResult(RESULT_OK, intent);
    finish();
  }
}
