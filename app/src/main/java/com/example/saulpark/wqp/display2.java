package com.example.saulpark.wqp;

        import android.app.DatePickerDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.jjoe64.graphview.GraphView;
        import com.jjoe64.graphview.series.DataPoint;
        import com.jjoe64.graphview.series.LineGraphSeries;

        import java.util.Calendar;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;


public class display2 extends AppCompatActivity {

    private CalendarPickerView calendar;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    Button date1, date2;


    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.highlightDates(getHolidays());

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.action_settings:
                    return true;
                case R.id.action_next:
                    ArrayList<Date> selectedDates = (ArrayList<Date>)calendar
                            .getSelectedDates();
                    Toast.makeText(MainActivity.this, selectedDates.toString(),
                            Toast.LENGTH_LONG).show();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private ArrayList<Date> getHolidays(){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            String dateInString = "21-04-2015";
            Date date = null;
            try {
                date = sdf.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ArrayList<Date> holidays = new ArrayList<>();
            holidays.add(date);
            return holidays;
        }





        double y,x;
        x = -5.0;

        GraphView graph = (GraphView)findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i=0; i<500;i++){

            x= x+0.1;
            y= Math.sin(x);
            series.appendData(new DataPoint(x,y), true, 500);
        }

        graph.addSeries(series);



    }





}
