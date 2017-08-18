package com.xyz.debdipta.wikisearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchDisplayActivity extends AppCompatActivity {
 private ProgressDialog pDialog;
    private String searchData="";
    private final static String TAG=SearchDisplayActivity.class.getSimpleName();
    ArrayList<HashMap<String,String>> data;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);
        Intent intent=getIntent();
        searchData=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        data = new ArrayList<>();
        lv=(ListView) findViewById(R.id.list);
        new getWikiData().execute();
    }

    private class getWikiData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchDisplayActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... args) {
            HttpHandler newConn = new HttpHandler(searchData);
            String jsonData = newConn.makeServiceCall();
            Log.e(TAG, "Response from URL: " + jsonData);
            if (jsonData != null) {
                try {
                    JSONObject wikiDataObject = new JSONObject(jsonData);
                    JSONArray dataArray = wikiDataObject.getJSONArray("Values");
                    JSONArray titles = dataArray.getJSONArray(1);
                    JSONArray description = dataArray.getJSONArray(2);
                    JSONArray links = dataArray.getJSONArray(3);
                    for (int i = 0; i < titles.length(); i++) {
//                        WikiDataDomain newObj = new WikiDataDomain(titles.getString(i), description.getString(i), links.getString(i));
                        HashMap<String,String> wikiDetails=new HashMap<>();
                        wikiDetails.put("title",titles.getString(i));
                        wikiDetails.put("description",description.getString(i));
                        wikiDetails.put("link",links.getString(i));
                        data.add(wikiDetails);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
             super.onPostExecute(result);
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
           ListAdapter adapter=new SimpleAdapter(  SearchDisplayActivity.this, data,
                   R.layout.list_item, new String[]{"title", "description",
                   "link"}, new int[]{R.id.title,
                   R.id.description, R.id.link});
            lv.setAdapter(adapter);
        }

    }
}
