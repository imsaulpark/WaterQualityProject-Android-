package com.example.saulpark.wqp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class display2 extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {


    private TextView location4_text;  //location4 TEXT
    private Button datepicker1; //날짜선택1버튼
    private Button datepicker2; //날짜선택1버튼
    private Button graphbtn;    //그래프 조회 버튼

    long timestamp1=0;  // 날짜 1 을 unix 시간 정보로
    long timestamp2=0;  // 날짜 1 을 unix 시간 정보로

    String date1,date2,which_date,location4,which_criteria;    //날짜1,2를 스트링으로, 날짜1을 선택했는지 2를 선택했는지, 첫 화면에서 받아온 location4정보, 선택된 criteria 정보

    // data 값 arrayList로 저장하기 위해
    ArrayList<String> al_criteria = new ArrayList<String>();    //받아온 criteria 목록
    ArrayList<String> al_timestamp = new ArrayList<String>();   //받아온 timestamp 목록
    ArrayList<Long> al_unix_timestamp = new ArrayList<Long>();  //받아온 timestamp를 unix timestamp로
    ArrayList<String> al_value = new ArrayList<String>();   //받아온 value목록
    ArrayList<String> criteria_list= new ArrayList<String>();   //중복없는 criteria 목록

    //문자열 어댑터 선언 (spinner를 위한)
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        graphbtn = (Button)findViewById(R.id.inquire);


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
        criteria_list= getIntent().getStringArrayListExtra("criteria_list");
        location4 = incomingIntent.getStringExtra("location4");
        which_criteria = incomingIntent.getStringExtra("which_criteria");

        //location4를 텍스트뷰에 표현
        location4_text.setText(location4);


        //날짜 스트링을 timestamp로 변환하는 형식
        DateFormat dfm = new SimpleDateFormat("yyyy/MM/dd");    //for calendar
        DateFormat dfm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    //for db data

        //받아온 timestamp string 정보를 unix time으로 변환.
        for(int i=0;i<al_timestamp.size();i++)
        {
            try {
                al_unix_timestamp.add(dfm2.parse(al_timestamp.get(i)).getTime());
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }

        //첫 번째 날짜를 지정한 경우 해당 버튼의 text를 날짜 정보로 변경
        if(!date1.equals("null"))
        {
            try {
                timestamp1 = dfm.parse(date1).getTime();    //string 시간 정보를 unix timestamp로 변경
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
                calintent.putExtra("criteria_list",criteria_list);
                calintent.putExtra("which_criteria",which_criteria);
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
                calintent.putExtra("criteria_list",criteria_list);
                calintent.putExtra("which_criteria",which_criteria);
                startActivity(calintent);
            }


        });

        //스피터에 어댑터를 연결하는 과정
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        Spinner sp = (Spinner) findViewById (R.id.criteria);
        sp.setAdapter(adapter);

        //스피너에 카테고리 목록을 넣는 과정
        for(int i=0;i<criteria_list.size();i++)
        {
            adapter.add(criteria_list.get(i));
        }

        //스피너가 선택 됐을 때 이벤트 처리
        sp.setOnItemSelectedListener(this);


        graphbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                //그래프로 사용할 라인 그래프
                LineChart lineChart = (LineChart) findViewById(R.id.graph);

                //라인그래프에 넣을 데이터
                List<Entry> entries = new ArrayList<>();

                lineChart.setNoDataText("날짜와 항목을 입력하세요.");

                // 유닉스 시간을 6만으로 나눠 분단위로 변경
                int time_unix1 = (int)(long)(timestamp1/60000);
                int time_unix2 = (int)(long)(timestamp2/60000);

                //해당 로케이션, 해당 크리테리아에 포함된 목록을 시간순으로 그래프에 삽입
                for (int j = (al_unix_timestamp.size()-1); j >= 0; j--) {
                    if (al_criteria.get(j).equals(which_criteria) && al_unix_timestamp.get(j)/60000 >= time_unix1 && al_unix_timestamp.get(j)/60000 < time_unix2) {
                        entries.add(new Entry(al_unix_timestamp.get(j), Float.parseFloat(al_value.get(j))));
                    }
                }
                if(entries.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //그래프 속성 설정하기
                    LineDataSet lineDataSet = new LineDataSet(entries, which_criteria);
                    lineDataSet.setLineWidth(2);
                    lineDataSet.setCircleRadius(6);
                    lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
                    lineDataSet.setCircleColorHole(Color.BLUE);
                    lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
                    lineDataSet.setDrawCircleHole(true);
                    lineDataSet.setDrawCircles(true);
                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
                    lineDataSet.setDrawHighlightIndicators(false);
                    lineDataSet.setDrawValues(false);

                    LineData lineData = new LineData(lineDataSet);
                    lineChart.setData(lineData);

                    //그래프 x축 속성 설정하기
                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setLabelCount(4);
                    xAxis.setValueFormatter(new MyXAxisValueFormatter());
                    xAxis.enableGridDashedLine(8, 24, 0);

                    //Y축 속성 설정하기
                    YAxis yLAxis = lineChart.getAxisLeft();
                    yLAxis.setTextColor(Color.BLACK);
                    YAxis yRAxis = lineChart.getAxisRight();
                    yRAxis.setDrawLabels(false);
                    yRAxis.setDrawAxisLine(false);
                    yRAxis.setDrawGridLines(false);

                    Description description = new Description();
                    description.setText("");

                    lineChart.setDoubleTapToZoomEnabled(false);
                    lineChart.setDrawGridBackground(false);
                    lineChart.setDescription(description);
                    lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
                    lineChart.invalidate();

                }
            }});

    }

    //뒤로가기 버튼 누른 경우
    public void back_clicked(View v){
        Intent calintent = new Intent(getApplicationContext(), display1.class);
        startActivity(calintent);

    }

    //카테고리 선택된 경우
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        which_criteria  = criteria_list.get(arg2);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
