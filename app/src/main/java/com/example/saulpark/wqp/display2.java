package com.example.saulpark.wqp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;


public class display2 extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {


    private TextView location4_text;  //location4 TEXT
    private Button datepicker1; //날짜선택1버튼
    private Button datepicker2; //날짜선택1버튼
    String date1,date2,which_date,location4;

    // data 값 arrayList로 저장하기 위해
    ArrayList<String> al_criteria = new ArrayList<String>();
    ArrayList<String> al_timestamp = new ArrayList<String>();
    ArrayList<String> al_value = new ArrayList<String>();

    LineGraphSeries<DataPoint> series;

    //문자열 어댑터 선언 (spinner를 위한)
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        //xml view 가져오기
        location4_text = (TextView)findViewById(R.id.location4);
        datepicker1 = (Button) findViewById(R.id.datepicker1);
        datepicker2 = (Button)findViewById(R.id.datepicker2);

        //intent로 값 받아오기
        Intent incomingIntent = getIntent();
        date1 = incomingIntent.getStringExtra("date1");
        date2 = incomingIntent.getStringExtra("date2");
        which_date = incomingIntent.getStringExtra("which_date");
        al_criteria = getIntent().getStringArrayListExtra("criteria");
        al_value = getIntent().getStringArrayListExtra("value");
        al_timestamp = getIntent().getStringArrayListExtra("timestamp");
        location4 = incomingIntent.getStringExtra("location4");

        //location4를 텍스트뷰에 표현
        location4_text.setText(location4);

        //날짜 값을 unix timestamp로 저장하기 위한 변수 두개
        long timestamp1=0;
        long timestamp2=0;

        //날짜 스트링을 timestamp로 변환하는 형식
        DateFormat dfm = new SimpleDateFormat("yyyy/MM/dd");

        //첫 번째 날짜를 지정한 경우 해당 버튼의 text를 날짜 정보로 변경
        if(!date1.equals("null"))
        {
            try {
                timestamp1 = dfm.parse(date1).getTime();    //string 시간 정보를 unix timestamp로 변경
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("DBG",""+timestamp1);
            datepicker1.setText(date1);     //시간 정보를 button text에 표현
        }

        //두 번째 날짜를 지정한 경우 해당 버튼의 text를 날짜 정보로 변경
        if(!date2.equals("null"))
        {
            try {
                timestamp2= dfm.parse(date2).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("DBG",""+timestamp2);
            datepicker2.setText(date2);
        }

        // 첫 번째 날짜 변경 버튼 누른 경우 달력 화면으로 이동
        datepicker1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                // 달력 갔다가 돌아온 이후에도 변수 값을 가지고 있기 위해 변수 값도 같이 보내줌
                Intent calintent = new Intent(display2.this, Calendar.class);
                calintent.putExtra("date1", date1);
                calintent.putExtra("date2", date2);
                calintent.putExtra("which_date", "date1");
                calintent.putExtra("timestamp", al_timestamp);
                calintent.putExtra("value", al_value);
                calintent.putExtra("criteria", al_criteria);
                calintent.putExtra("location4",location4);
                startActivity(calintent);
            }



        });

        // 두 번째 날짜 변경 버튼 누른 경우 달력 화면으로 이동
        datepicker2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){


                Intent calintent = new Intent(display2.this, Calendar.class);
                calintent.putExtra("date1", date1);
                calintent.putExtra("date2", date2);
                calintent.putExtra("which_date", "date2");
                calintent.putExtra("timestamp", al_timestamp);
                calintent.putExtra("value", al_value);
                calintent.putExtra("criteria", al_criteria);
                calintent.putExtra("location4",location4);
                startActivity(calintent);
            }


        });

        //스피터에 어댑터를 연결하는 과정
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        Spinner sp = (Spinner) findViewById (R.id.criteria);
        sp.setAdapter(adapter);

        //스피너에 카테고리 목록을 넣는 과정
        for(int i=0;i<al_criteria.size();i++)
        {
            adapter.add(al_criteria.get(i));
        }

        //스피너가 선택 됐을 때 이벤트 처리
        sp.setOnItemSelectedListener(this);



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

    //뒤로가기 버튼 누른 경우
    public void back_clicked(View v){
        onBackPressed();
    }

    //카테고리 선택된 경우
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
