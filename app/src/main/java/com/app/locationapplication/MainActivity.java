package com.app.locationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText cityEd;
    TextView resultTv;
    Button btnFetch;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding findViewById to relevant fields
        cityEd = findViewById(R.id.city_et);
        resultTv = findViewById(R.id.result_tv);
        btnFetch = findViewById(R.id.btn_fet);

        //Add an "onclick" event listener for the button that will fetch the data ( Execute Fetch() )
        btnFetch.setOnClickListener(this);

    }
    //Method to parse the Json data
    public void parseJson(String data) throws JSONException{
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray cityArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < cityArray.length(); i++) {
            JSONObject city0 = cityArray.getJSONObject(i);
            String cityn = city0.get("State").toString();
            if(cityn.equals(cityName)){
                String population = city0.get("Population").toString();
                resultTv.setText(population);
                break;
            }else{
                resultTv.setText("Not Found");
            }

        }
    }

    protected void onPostExecute(String s){
        try {
            parseJson(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onClick(View v){
        if(v.getId()==R.id.btn_fet){
            cityName = cityEd.getText().toString();
            Toast.makeText(this, cityName, Toast.LENGTH_SHORT);
            getData();
        }
    }

    //method to get the data
    public void getData(){
        Uri uri = Uri.parse("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest").buildUpon().build();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}