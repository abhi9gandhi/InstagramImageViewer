package com.codepath.myapplication;

import android.net.http.AndroidHttpClient;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageActivity extends ActionBarActivity {

    public static final String CLIENT_ID="4e777513a14b4f6287b5822da47588b1";
    private ArrayList<InstagramPhotp> photoArray;
    InstagramPhotosAdapter aphoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        photoArray = new ArrayList<>();
        aphoto = new InstagramPhotosAdapter(this, photoArray);
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aphoto);

        // Send API request to get Popular Photos
        fetchPhotos();

    }

    public void fetchPhotos() {
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(popularUrl,null, new JsonHttpResponseHandler(){
            // On success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG",response.toString());


                try {
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0 ;i < photosJSON.length();i ++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhotp photo = new InstagramPhotp();
                        if (photoJSON.getJSONObject("user").getString("username") != null) {
                            photo.username = photoJSON.getJSONObject("user").getString("username");
                        }
                        if (photoJSON.getJSONObject("caption").getString("text") != null) {
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        }
                        if (photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url") != null) {
                            photo.url = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        }
                        if (photoJSON.getJSONObject("user").getString("profile_picture") != null) {
                            photo.profilePicUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        }

                        photo.comments = photoJSON.getString("comments");

                        photo.height = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.width = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");


                        photoArray.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aphoto.notifyDataSetChanged();
            }

            // On Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
