package com.app.locationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.locationapplication.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText cityEd;
    TextView resultTv;
    Button btnFetch;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEd = findViewById(R.id.city_et);
        resultTv = findViewById(R.id.result_tv);
        btnFetch = findViewById(R.id.btn_fet);
        btnFetch.setOnClickListener(this);
    }

    public void parseJson(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray cityArray = jsonObject.getJSONArray("data");
        boolean cityFound = false;

        for (int i = 0; i < cityArray.length(); i++) {
            JSONObject city0 = cityArray.getJSONObject(i);
            String cityn = city0.getString("State");

            if (cityn.equals(cityName)) {
                String population = city0.getString("Population");
                resultTv.setText(population);
                cityFound = true;
                break;
            }
        }

        if (!cityFound) {
            resultTv.setText("Not Found");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_fet) {
            cityName = cityEd.getText().toString();
            try {
                // Execute AsyncTask to fetch data
                new DOTask().execute(getData());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public URL getData() throws MalformedURLException {
        return new URL("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest");
    }

    // AsyncTask to make the HTTP request in the background
    private static class DOTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequests(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            // Handle the result after the AsyncTask is done
            try {
                // Assuming MainActivity is the outer class
                MainActivity mainActivity = new MainActivity();
                mainActivity.parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
