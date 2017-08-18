package com.xyz.debdipta.wikisearch;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by debdipta on 14-08-2017.
 */

public class HttpHandler {
    private String url = "https://en.wikipedia.org/w/api.php?action=opensearch&search=\"%s\"&format=json&callback=?";
    private static final String TAG = HttpHandler.class.getSimpleName();
    private String finalUrl;

    public HttpHandler(String searchTerm) {
        finalUrl = String.format(url, searchTerm);
    }

    public String makeServiceCall() {
        String response = null;
        try {
            URL url = new URL(this.finalUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertInputStreamToString(in);
            response=formatData(response);
        }
        catch(MalformedURLException e){
            Log.e(TAG,"MalformedUrlException occured:"+e.getMessage());
        }
        catch (IOException e) {
            Log.e(TAG,"IOException occured: "+e.getMessage());

        }
        return response;
    }

    private String formatData(String data){
        String formattedData=data.replaceAll("/\\*\\*/\\(","{\"Values\":");
        StringBuilder newBuilder=new StringBuilder(formattedData);
        newBuilder.deleteCharAt(newBuilder.length()-2);
        newBuilder.append("}");
        return newBuilder.toString();

    }

    private String convertInputStreamToString(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder newBuild = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                newBuild.append(line).append("\n");

            }
        }catch(IOException e){
           Log.e(TAG,"[convertInputStreamToString] IO Exception occured: "+e.getMessage());
        }
        return newBuild.toString();
    }
}