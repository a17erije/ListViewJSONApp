package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// Create a new class, Mountain, that can hold your JSON data

// Create a ListView as in "Assignment 1 - Toast and ListView"

// Retrieve data from Internet service using AsyncTask and the included networking code

// Parse the retrieved JSON and update the ListView adapter

// Implement a "refresh" functionality using Android's menu system


public class MainActivity extends AppCompatActivity {

    Mountain[] theMountains = new Mountain[20];
    Integer[] mountainHeights = new Integer[20];
    Integer[] mountainIds = new Integer[20];
    String[] mountainNames = new String[20];
    String[] mountainLocations = new String[20];
    String[] mountainImageURLs = new String[20];
    String[] mountainWikiURLs = new String[20];

    String[] placeholderMountain = new String[11];

    ArrayAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.themenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh) {
            new FetchData().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < placeholderMountain.length; i++)
            placeholderMountain[i] = "Mount Shoehorn";

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item,R.id.my_item_listview,placeholderMountain);

        ListView myListView = (ListView)findViewById(R.id.my_listview);
        myListView.setAdapter(adapter);

        new FetchData().execute();


 /*       for (int i = 0; i < theMountains.length; i++) {
            theMountains[i] = new Mountain();
        }

        for (int i = 0; i < theMountains.length; i++) {
            mountainHeights[i] = theMountains[i].getHeight();
            mountainIds[i] = theMountains[i].getId();
            mountainImageURLs[i] = theMountains[i].getImageURL();
            mountainLocations[i] = theMountains[i].getLocation();
            mountainNames[i] = theMountains[i].getName();
            mountainWikiURLs[i] = theMountains[i].getWikiURL();
        }*/
        /*
        Mountain[] theMountains = null;
        theMountains[0] = new Mountain();
        Integer[] mountainHeights = null;
        Integer[] mountainIds = null;
        String[] mountainNames = null;
        String[] mountainLocations = null;
        String[] mountainImageURLs = null;
        String[] mountainWikiURLs = null;

        for (int i = 0; i < theMountains.length; i++) {
            mountainHeights[i] = theMountains[i].getHeight();
            mountainIds[i] = theMountains[i].getId();
            mountainImageURLs[i] = theMountains[i].getImageURL();
            mountainLocations[i] = theMountains[i].getLocation();
            mountainNames[i] = theMountains[i].getName();
            mountainWikiURLs[i] = theMountains[i].getWikiURL();
        }
        */


    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //Mountain[] theMountains = null;
            //theMountains[0] = new Mountain();

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            try {
                JSONArray webMountains = new JSONArray(o);

                for (int i = 0; i < webMountains.length(); i++) {
                    theMountains[i] = new Mountain();
                    JSONObject webMountain = webMountains.getJSONObject(i);
                    theMountains[i].setHeight(webMountain.getInt("size"));
                    theMountains[i].setName(webMountain.getString("name"));
                    theMountains[i].setLocation(webMountain.getString("location"));
                    mountainHeights[i] = theMountains[i].getHeight();
                    mountainIds[i] = theMountains[i].getId();
                    mountainImageURLs[i] = theMountains[i].getImageURL();
                    mountainLocations[i] = theMountains[i].getLocation();
                    mountainNames[i] = theMountains[i].getName();
                    mountainWikiURLs[i] = theMountains[i].getWikiURL();
                }

                String[] mountainNamesList = new String[webMountains.length()];

                for (int i = 0; i < webMountains.length(); i++ ) {
                    mountainNamesList[i] = mountainNames[i];
                }

                adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item,R.id.my_item_listview,mountainNamesList);

                ListView myListView = (ListView)findViewById(R.id.my_listview);
                myListView.setAdapter(adapter);
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(position) + " is " + mountainHeights[position] + " meters tall and its location is: " + mountainLocations[position], Toast.LENGTH_SHORT).show();
                        //Intent myIntent = new Intent(view.getContext(),MountainDetailsActivity.class);
                        //myIntent.putExtra("mountainName", mountainNames[position]);
                        //myIntent.putExtra("mountainHeight", mountainHeights[position]);
                        //myIntent.putExtra("mountainLocation", mountainLocations[position]);
                        //startActivityForResult(myIntent,0);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

