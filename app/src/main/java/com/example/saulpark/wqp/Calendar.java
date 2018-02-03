package com.example.saulpark.wqp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

/**
 * Created by SEORA on 2018-02-01.
 */

public class Calendar extends AppCompatActivity {

    private static final String TAG = "Calendar";
    private CalendarView mCalendarView;
    String date1,date2,which_date,location4,which_criteria;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();

        Intent incomingIntent = getIntent();
        date1 = incomingIntent.getStringExtra("date1");
        date2 = incomingIntent.getStringExtra("date2");
        which_date = incomingIntent.getStringExtra("which_date");
        location4 = incomingIntent.getStringExtra("location4");
        which_criteria = incomingIntent.getStringExtra("which_criteria");

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "/" + (i1+1) + "/" + i2 ;
                Log.d(TAG, "onSelectedDayChange : mm/dd/yyyy" + date);

                Intent calintent = new Intent(getApplicationContext(), display2.class);

                if(which_date.equals("date1"))
                {
                    calintent.putExtra("date1", date);
                    calintent.putExtra("date2", date2);
                    calintent.putExtra("which_date", date1);
                    calintent.putExtra("location4",location4);
                    calintent.putExtra("timestamp", getIntent().getStringArrayListExtra("timestamp"));
                    calintent.putExtra("value", getIntent().getStringArrayListExtra("value"));
                    calintent.putExtra("criteria", getIntent().getStringArrayListExtra("criteria"));
                    calintent.putExtra("criteria_list", getIntent().getStringArrayListExtra("criteria_list"));
                    calintent.putExtra("which_criteria",which_criteria);

                }
                else
                {
                    calintent.putExtra("date1", date1);
                    calintent.putExtra("date2", date);
                    calintent.putExtra("which_date", date2);
                    calintent.putExtra("location4",location4);
                    calintent.putExtra("timestamp", getIntent().getStringArrayListExtra("timestamp"));
                    calintent.putExtra("value", getIntent().getStringArrayListExtra("value"));
                    calintent.putExtra("criteria", getIntent().getStringArrayListExtra("criteria"));
                    calintent.putExtra("criteria_list", getIntent().getStringArrayListExtra("criteria_list"));
                    calintent.putExtra("which_criteria",which_criteria);
                }
                startActivity(calintent);

            }
        });


    }

    public void back_clicked(View v){
        onBackPressed();
    }

}