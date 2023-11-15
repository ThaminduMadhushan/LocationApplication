package com.app.locationapplication.utils;

import android.os.AsyncTask;
import java.net.URL;

public class DOTask extends AsyncTask<URL, Void, String> {
    @Override
    protected String doInBackground(URL... urls){
        URL url = urls[0];
        String data = null;
        try {
            data = NetworkUtils.makeHTTPRequests(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
