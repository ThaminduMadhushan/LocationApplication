package com.app.locationapplication.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // method to make HTTP requests
    public static String makeHTTPRequests(URL url) throws Exception {

        // open connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //get the input data stream
        InputStream inputStream = connection.getInputStream();

        // handle runtime exceptions using try-catch
        try {
            Scanner scanner = new Scanner(inputStream);
            //check for delimiter (Scanner)
            scanner.useDelimiter("\\A");

            // check if there is an input, and handle accordingly
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }
        } finally {
            // close connection at the end
            connection.disconnect();
        }
    }
}
