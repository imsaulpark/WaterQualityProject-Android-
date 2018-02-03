package com.example.saulpark.wqp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import android.content.Intent;
import android.widget.TextView;

import java.util.Vector;


public class display1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // json 데이터 tag값
    private static final String TAG_RESULTS = "result";
    private static final String TAG_IDLOCATION = "idlocation";
    private static final String TAG_LOCATION1 = "location1";
    private static final String TAG_LOCATION2= "location2";
    private static final String TAG_LOCATION3= "location3";
    private static final String TAG_LOCATION4= "location4";
    private static final String TAG_VALUE1 = "value1";
    private static final String TAG_VALUE2 = "value2";
    private static final String TAG_VALUE = "value";
    private static final String TAG_FLAG = "flag";
    private static final String TAG_CRITERIA_DESC = "criteria_desc";
    private static final String TAG_UNIT = "unit";
    private static final String TAG_IDDATAPOINT = "iddatapoint";
    private static final String TAG_TIMESTAMP = "timestamp";

    String myJSON;  //받아온 json값
    JSONArray braught_data = null;  //받아온 json string을 이 어레이에 저장

    //location1~4 정보를 벡터로 저장
    Vector<String> location1 = new Vector<String>();
    Vector<String> location2 = new Vector<String>();
    Vector<String> location3 = new Vector<String>();
    Vector<String> location4 = new Vector<String>();

    Vector<String> vector_criteria = new Vector<String>();  //중복되지 않은 criteria 정보
    ArrayList<String> al_timestamp = new ArrayList<String>();   //해당로케이션에 존재하는 데이터값 ( 다음 화면으로 넘겨주는 데이터)
    ArrayList<String> al_value = new ArrayList<String>();   //해당로케이션에 존재하는 데이터값 ( 다음 화면으로 넘겨주는 데이터)
    ArrayList<String> al_criteria = new ArrayList<String>();   //해당로케이션에 존재하는 데이터값 ( 다음 화면으로 넘겨주는 데이터)

    String[][] data;    //json으로 받아온 데이터를 2차원 배열로 저장
    String location4_text;  //location4정보만 다음 화면으로 넘겨준다.

    LinearLayout parent_layout; //부모 레이아웃을 저장하는 변수

    int index1,index2,index3,index4;    // 로케이션1~4에서 선택된 스피너의 index번호

    //문자열 어댑터 선언
    ArrayAdapter<String> adapter1,adapter2,adapter3,adapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display1);

        getData("http://165.194.35.138/allcontents_data.php");  //json정보를 받아올 웹 페이지

    }

    //해동 로케이션에서 보여줄 정보 보여주기
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            braught_data = jsonObj.getJSONArray(TAG_RESULTS);
            data = new String[braught_data.length()][13];

            //어댑터 객체를 생성하고 보여질 아이템 리소스와 문자열 지정
            adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
            adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
            adapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
            adapter1.clear();

            //받아온 데이터를 배열에 저장하기.
            for (int i = 0; i < braught_data.length(); i++) {
                JSONObject c = braught_data.getJSONObject(i);
                data[i][0] = c.getString(TAG_IDLOCATION);
                data[i][1] = c.getString(TAG_LOCATION1);
                data[i][2] = c.getString(TAG_LOCATION2);
                data[i][3] = c.getString(TAG_LOCATION3);
                data[i][4] = c.getString(TAG_LOCATION4);
                data[i][5] = c.getString(TAG_CRITERIA_DESC);
                data[i][6] = c.getString(TAG_VALUE1);
                data[i][7] = c.getString(TAG_VALUE2);
                data[i][8] = c.getString(TAG_VALUE);
                data[i][9] = c.getString(TAG_FLAG);
                data[i][10] = c.getString(TAG_UNIT);
                data[i][11] = c.getString(TAG_IDDATAPOINT);
                data[i][12] = c.getString(TAG_TIMESTAMP);

                //location1 스피너에 중복되지 않게 location 추가
                if(!location1.contains(data[i][1]))
                {
                    location1.add(data[i][1]);
                    adapter1.add(data[i][1]);
                }

            }

            //xml에 선언한 스피너를 id값으로 불러옴
            Spinner sp1 = (Spinner) findViewById (R.id.spinner1);
            Spinner sp2 = (Spinner) findViewById (R.id.spinner2);
            Spinner sp3 = (Spinner) findViewById (R.id.spinner3);
            Spinner sp4 = (Spinner) findViewById (R.id.spinner4);

            //스피너에 adapter 연결
            sp1.setAdapter(adapter1);
            sp2.setAdapter(adapter2);
            sp3.setAdapter(adapter3);
            sp4.setAdapter(adapter4);


            //스피너가 선택 됐을 때 이벤트 처리
            sp1.setOnItemSelectedListener(this);
            sp2.setOnItemSelectedListener(this);
            sp3.setOnItemSelectedListener(this);
            sp4.setOnItemSelectedListener(this);

            parent_layout = (LinearLayout) findViewById(R.id.parent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //스피너가 선택된 경우
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        // 어떤 스피너가 선택되었는지를 스위치문으로 판단
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
            // 첫 번째 스피너가 선택된 경우.
            case R.id.spinner1:

                // 두 번째 스피너를 초기화 한다.
                location2.clear();
                adapter2.clear();
                // 첫 번째 스피너에서 선택된 값에 해당하는 두 번째 스피너 값 추가
                for (int i = 0; i < braught_data.length(); i++) {
                    if (data[i][1].equals(location1.get(arg2))) {
                        if (!location2.contains(data[i][2])) {
                            location2.add(data[i][2]);
                            adapter2.add(data[i][2]);
                        }
                    }
                }
                // 어댑터에 변경사항 알리기
                adapter2.notifyDataSetChanged();
                // 해당 index 정보를 저장하서 이후에 사용한다.
                index1=arg2;
                arg2=0;

            case R.id.spinner2:
                location3.clear();
                adapter3.clear();

                for (int i = 0; i < braught_data.length(); i++) {
                    if (data[i][2].equals(location2.get(arg2))) {
                        if (!location3.contains(data[i][3])) {
                            location3.add(data[i][3]);
                            adapter3.add(data[i][3]);
                        }
                    }
                }
                adapter3.notifyDataSetChanged();
                arg2=0;
                index2=arg2;


            case R.id.spinner3:
                location4.clear();
                adapter4.clear();
                for (int i = 0; i < braught_data.length(); i++) {
                    if (arg2 >= 0 && !location3.isEmpty()) {
                        if (data[i][3].equals(location3.get(arg2))) {
                            if (!location4.contains(data[i][4])) {
                                location4.add(data[i][4]);
                                adapter4.add(data[i][4]);
                            }
                        }
                    }
                }
                adapter4.notifyDataSetChanged();
                index3=arg2;
                arg2=0;

            // 네 번째 스피너가 선택된 경우에는 자동으로 데이터를 보여주두록 하며, 다음 화면으로 넘겨줄 데이터도 담는다.
            case R.id.spinner4:
                al_timestamp.clear();
                al_value.clear();
                al_criteria.clear();
                index4=arg2;
                location4_text=location4.get(index4);
                for (int i = 0; i < vector_criteria.size(); i++) {
                    parent_layout.removeView(parent_layout.findViewWithTag("new"));
                }

                vector_criteria.clear();

                    for(int j=0;j<braught_data.length();j++)
                    {
                        if(vector_criteria.contains(data[j][5]))
                        {
                            if(location1.get(index1).equals(data[j][1]) && location2.get(index2).equals(data[j][2]) && location3.get(index3).equals(data[j][3]) && location4.get(index4).equals(data[j][4])) {
                                al_timestamp.add(data[j][12]);
                                al_value.add(data[j][8]);
                                al_criteria.add(data[j][5]);
                            }
                        }
                        else
                        {
                            if(location1.get(index1).equals(data[j][1]) && location2.get(index2).equals(data[j][2]) && location3.get(index3).equals(data[j][3]) && location4.get(index4).equals(data[j][4]))
                            {

                                vector_criteria.add(data[j][5]);
                                al_timestamp.add(data[j][12]);
                                al_value.add(data[j][8]);
                                al_criteria.add(data[j][5]);

                                LinearLayout newLayout = new LinearLayout(this);
                                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lparams.setMargins(0, 60, 0, 0);
                                newLayout.setLayoutParams(lparams);
                                newLayout.setTag("new");
                                newLayout.setWeightSum(4);

                                final int dpzero = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());

                                TextView criteriaText = new TextView(this);
                                criteriaText.setText(data[j][5]);
                                criteriaText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                                criteriaText.setLayoutParams(new LinearLayout.LayoutParams(dpzero, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                TextView rangeText = new TextView(this);
                                rangeText.setText(data[j][6] + " ~ " + data[j][7]);
                                rangeText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                                rangeText.setLayoutParams(new LinearLayout.LayoutParams(dpzero, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                TextView valueText = new TextView(this);
                                valueText.setText(data[j][8] + " " + data[j][10]);
                                valueText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                                valueText.setLayoutParams(new LinearLayout.LayoutParams(dpzero, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                ImageView flagImage = new ImageView(this);
                                if (data[j][9].equals("0"))
                                    flagImage.setImageResource(R.drawable.flag_0);
                                else
                                    flagImage.setImageResource(R.drawable.flag_1);
                                LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(dpzero, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                                imageParam.gravity = Gravity.CENTER;
                                flagImage.setLayoutParams(imageParam);

                                newLayout.addView(criteriaText);
                                newLayout.addView(rangeText);
                                newLayout.addView(valueText);
                                newLayout.addView(flagImage);

                                parent_layout.addView(newLayout);
                            }

                        }


                        }
                }


    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    //데이터를 받아오는 함수
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);


    }

    //뒤로가기 버튼 눌린 경우
    public void back_clicked(View v){
        Intent calintent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(calintent);

    }

    //항목별 보기 누른 경우
    public void category_clicked(View v){

        Intent i = new Intent(getApplicationContext(), display2.class);

        i.putExtra("date1", "null");
        i.putExtra("date2", "null");
        i.putExtra("which_date", "null");
        i.putExtra("timestamp", al_timestamp);
        i.putExtra("value", al_value);
        i.putExtra("criteria", al_criteria);
        i.putExtra("location4",location4_text);
        i.putExtra("criteria_list",vector_criteria);
        i.putExtra("which_criteria","null");
        startActivity(i);
    }
}


