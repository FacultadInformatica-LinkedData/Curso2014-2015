package com.linkeddata.vktrsm.planesmadrid;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;
import java.util.Calendar;


public class AddPlanActivity extends ActionBarActivity {
  private Toolbar toolbar;
  private Plan PlanResult = new Plan();
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private ArrayList<Appointment> myDataset;
  private FloatingActionButton fab;

  private void collapseToolbar(){
    /*Should be collapsed? Only if both fields are filled*/
    EditText tName = (EditText) findViewById(R.id.etName);
    EditText tTime = (EditText) findViewById(R.id.etTime);

    if(tName.getText().length() == 0 || tTime.getText().length() == 0) return; //Should not be collapsed

    LinearLayout tCollapsed = (LinearLayout) findViewById(R.id.toolbarCollapsed);
    final LinearLayout tExpanded = (LinearLayout) findViewById(R.id.toolbarExpanded);

    TextView collapsedText = (TextView) findViewById(R.id.tCollapsed);
    collapsedText.setText(tName.getText() + " @ " + tTime.getText());
    PlanResult.setName(tName.getText().toString());
    tCollapsed.setVisibility(View.VISIBLE);
    tExpanded.setVisibility(View.GONE);
    fab.setVisibility(View.VISIBLE);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_plan);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    if (toolbar != null) {
      setSupportActionBar(toolbar);

      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xfff44336));
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getWindow().setStatusBarColor(0xffd50000);
    }

    final EditText nameField = (EditText) findViewById(R.id.etName);
    final EditText timePickerField = (EditText) findViewById(R.id.etTime);
    TextView collapsedText = (TextView) findViewById(R.id.tCollapsed);

    fab = (FloatingActionButton) findViewById(R.id.fab_newAppointment);
    fab.setVisibility(View.INVISIBLE);

    if (getIntent().getExtras() != null && !getIntent().getExtras().getSerializable("Plan").equals(null)){
      Plan savedPlan = (Plan) getIntent().getExtras().getSerializable("Plan");
      if(savedPlan != null) {
        PlanResult = savedPlan;
        nameField.setText(savedPlan.getName());
        String selectedMinuteText = (PlanResult.getStart()[1] < 10) ? "0"+PlanResult.getStart()[1]:""+PlanResult.getStart()[1];
        timePickerField.setText(PlanResult.getStart()[0] + ":" + selectedMinuteText);
        collapsedText.setText(nameField.getText() + " @ " + timePickerField.getText());
        LinearLayout tCollapsed = (LinearLayout) findViewById(R.id.toolbarCollapsed);
        LinearLayout tExpanded = (LinearLayout) findViewById(R.id.toolbarExpanded);
        tExpanded.setVisibility(View.GONE);
        tCollapsed.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
      }
    }




    timePickerField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
          TimePickerDialog mTimePicker;
          Calendar mcurrentTime = Calendar.getInstance();
          int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
          int minute = mcurrentTime.get(Calendar.MINUTE);

          mTimePicker = new TimePickerDialog(AddPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
              String selectedMinuteText = (selectedMinute < 10) ? "0"+selectedMinute:""+selectedMinute;
              timePickerField.setText(selectedHour + ":" + selectedMinuteText);
              PlanResult.setStart(selectedHour, selectedMinute);
              collapseToolbar();
            }
          }, hour, minute, true);//Yes 24 hour time
          mTimePicker.setTitle("Selecciona Hora de Inicio");
          mTimePicker.show();
          timePickerField.clearFocus();
        }
      }
    });

    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_appointments);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent addAppointment = new Intent(v.getContext(), AddAppointmentActivity.class);
        startActivityForResult(addAppointment,2);
      }
    });

    //mRecyclerView.setOnTouchListener(new ShowHideOnScroll(fab));
    mRecyclerView.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);

    myDataset = PlanResult.getAppointments();
    mAdapter = new CardViewAppointmentAdapter(myDataset, PlanResult.getTransport());
    mRecyclerView.setAdapter(mAdapter);

    /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
    {
      @Override
      public void onItemClick(View view, int position) {
        YoYo.with(Techniques.Shake).duration(100).playOn(view);
      }

      @Override
      public void onItemLongClick(View view, int position) {
      }
    }));*/

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_add_plan, menu);
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
  @Override
  public void onBackPressed() {
    //Check if current plan is valid!
    if(PlanResult.getName().length() > 0 && PlanResult.getStart()[0] > -1 && PlanResult.getStart()[1] > -1)
      finishWithResult();
    else
      finish();

    return;
  }

  private void finishWithResult()
  {
    Bundle conData = new Bundle();
    conData.putSerializable("Plan", PlanResult);
    if(getIntent().getExtras() != null)
      conData.putInt("position", getIntent().getExtras().getInt("position"));

    Intent intent = new Intent();
    intent.putExtras(conData);
    setResult(RESULT_OK, intent);
    finish();
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) { //New appointment
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                Appointment newAppointment = (Appointment) res.getSerializable("Appointment");
                myDataset.add(newAppointment);
                PlanResult.calculateTransport();
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
