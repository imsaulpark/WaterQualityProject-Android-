package com.example.saulpark.wqp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    String choice_l1="";
    String choice_l2="";
    String choice_l3="";
    String choice_l4="";



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

    String myJSON;
    JSONArray locations = null;
    Vector<String> vector1 = new Vector<String>();
    Vector<String> vector2 = new Vector<String>();
    Vector<String> vector3 = new Vector<String>();
    Vector<String> vector4 = new Vector<String>();
    Vector<String> vector_criteria = new Vector<String>();
    Vector<String> vector_data = new Vector<String>();
    ArrayList<String> al_timestamp = new ArrayList<String>();
    ArrayList<String> al_value = new ArrayList<String>();
    ArrayList<String> al_criteria = new ArrayList<String>();
    String[][] data;
    String location4;

    LinearLayout parent_layout;

    int index1,index2,index3,index4;


    //문자열 어댑터 선언
    ArrayAdapter<String> adapter1,adapter2,adapter3,adapter4;

    //스피너 클릭 시 보여질 문자 배열 할당
    ArrayList<String> entries1,entries2,entries3,entries4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display1);

        getData("http://165.194.35.138/allcontents_data.php");

    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            locations = jsonObj.getJSONArray(TAG_RESULTS);
            data = new String[locations.length()][13];

            entries1 =  new ArrayList<String>(Arrays.asList(""));
            entries2 =  new ArrayList<String>(Arrays.asList(""));
            entries3 =  new ArrayList<String>(Arrays.asList(""));
            entries4 =  new ArrayList<String>(Arrays.asList(""));


            //어댑터 객체를 생성하고 보여질 아이템 리소스와 문자열 지정
            adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, entries1);
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, entries2);
            adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, entries3);
            adapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, entries4);
            adapter1.clear();

            for (int i = 0; i < locations.length(); i++) {
                JSONObject c = locations.getJSONObject(i);
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

                if(!vector1.contains(data[i][1]))
                {
                    vector1.add(data[i][1]);
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


            //스피너가 선택 됫을 때 이벤트 처리
            sp1.setOnItemSelectedListener(this);
            sp2.setOnItemSelectedListener(this);
            sp3.setOnItemSelectedListener(this);
            sp4.setOnItemSelectedListener(this);

            parent_layout = (LinearLayout) findViewById(R.id.parent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
            case R.id.spinner1:
                vector2.clear();
                adapter2.clear();
                for (int i = 0; i < locations.length(); i++) {
                    if (data[i][1].equals(vector1.get(arg2))) {
                        if (!vector2.contains(data[i][2])) {
                            vector2.add(data[i][2]);
                            adapter2.add(data[i][2]);
                        }
                    }
                }
                adapter2.notifyDataSetChanged();
                index1=arg2;
                arg2=0;

            case R.id.spinner2:
                vector3.clear();
                adapter3.clear();

                for (int i = 0; i < locations.length(); i++) {
                    if (data[i][2].equals(vector2.get(arg2))) {
                        if (!vector3.contains(data[i][3])) {
                            vector3.add(data[i][3]);
                            adapter3.add(data[i][3]);
                        }
                    }
                }
                adapter3.notifyDataSetChanged();
                arg2=0;
                index2=arg2;


            case R.id.spinner3:
                vector4.clear();
                adapter4.clear();
                for (int i = 0; i < locations.length(); i++) {
                    if (arg2 >= 0 && !vector3.isEmpty()) {
                        if (data[i][3].equals(vector3.get(arg2))) {
                            if (!vector4.contains(data[i][4])) {
                                vector4.add(data[i][4]);
                                adapter4.add(data[i][4]);
                            }
                        }
                    }
                }
                adapter4.notifyDataSetChanged();
                index3=arg2;
                arg2=0;


            case R.id.spinner4:
                al_timestamp.clear();
                al_value.clear();
                al_criteria.clear();
                index4=arg2;
                location4=vector4.get(index4);
                for (int i = 0; i < vector_data.size(); i++) {
                    parent_layout.removeView(parent_layout.findViewWithTag("new"));
                }

                vector_criteria.clear();
                vector_data.clear();
                for (int i = 0; i < locations.length(); i++) {
                    if (!vector_criteria.contains(data[i][5])) {
                        vector_criteria.add(data[i][5]);
                        al_criteria.add(data[i][5]);

                        Log.d("CT",al_criteria.get(0));
                    }
                }

                for(int i=0;i<vector_criteria.size();i++)
                {
                    for(int j=0;j<locations.length();j++)
                    {
                        if(vector_criteria.get(i).equals(data[j][5]) && vector1.get(index1).equals(data[j][1]) && vector2.get(index2).equals(data[j][2]) && vector3.get(index3).equals(data[j][3]) && vector4.get(index4).equals(data[j][4]))
                        {
                            vector_data.add(data[j][11]);
                            al_timestamp.add(data[j][12]);
                            al_value.add(data[j][8]);
                            Log.d("TTTT",al_timestamp.get(0));

                            LinearLayout newLayout= new LinearLayout(this);
                            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lparams.setMargins(0,60,0,0);
                            newLayout.setLayoutParams(lparams);
                            newLayout.setTag("new");
                            newLayout.setWeightSum(4);

                            final int dpzero = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());

                            TextView criteriaText = new TextView(this);
                            criteriaText.setText(data[j][5]);
                            criteriaText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                            criteriaText.setLayoutParams(new LinearLayout.LayoutParams(dpzero,LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                            TextView rangeText = new TextView(this);
                            rangeText.setText(data[j][6] + " ~ " + data[j][7]);
                            rangeText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                            rangeText.setLayoutParams(new LinearLayout.LayoutParams(dpzero,LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                            TextView valueText = new TextView(this);
                            valueText.setText(data[j][8] + " " + data[j][10]);
                            valueText.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                            valueText.setLayoutParams(new LinearLayout.LayoutParams(dpzero,LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                            ImageView flagImage = new ImageView(this);
                            if(data[j][9].equals("0"))
                                flagImage.setImageResource(R.drawable.flag_0);
                            else
                                flagImage.setImageResource(R.drawable.flag_1);
                            LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(dpzero,LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                            imageParam.gravity=Gravity.CENTER;
                            flagImage.setLayoutParams(imageParam);

                            newLayout.addView(criteriaText);
                            newLayout.addView(rangeText);
                            newLayout.addView(valueText);
                            newLayout.addView(flagImage);

                            parent_layout.addView(newLayout);
                            Log.d("SIZE", vector_data.size()+"");
                            Log.d("TTTT",al_timestamp.get(0));

                            Log.d("CT",al_criteria.get(0));
                            break;
                        }
                    }
                }

                break;
        }

        // String request[] = {choice_l1, choice_l2, choice_l3, choice_l4}; //spinner로 조회되는 내용 불러올꺼

    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

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
    public void back_clicked(View v){
        onBackPressed();
    }
    public void category_clicked(View v){

        Intent i = new Intent(getApplicationContext(), display2.class);

        i.putExtra("date1", "null");
        i.putExtra("date2", "null");
        i.putExtra("which_date", "null");
        i.putExtra("timestamp", al_timestamp);
        i.putExtra("value", al_value);
        i.putExtra("criteria", al_criteria);
        i.putExtra("location4",location4);

        Log.d("CT",al_criteria.get(0));

        startActivity(i);
    }
}


