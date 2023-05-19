package com.saurabh.fetchapplication.Service;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.saurabh.fetchapplication.MainActivity;
import com.saurabh.fetchapplication.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchData {
    private static MainActivity mainActivity;
    private static RequestQueue queue;
    private static final String dataUrl = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void fetchUserData(MainActivity activity) {
        mainActivity = activity;
        queue = Volley.newRequestQueue(mainActivity);
        Uri.Builder buildURL = Uri.parse(dataUrl).buildUpon();
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONArray> listener = response -> parseJSON(response);
        Response.ErrorListener error = error1 -> {
            mainActivity.updateData(new ArrayList<>());
        };
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, urlToUse, null, listener, error);
        queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void parseJSON(JSONArray jsonArray) {
        try {
            List<User> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                list.add(new User(jsonObject.getInt("id"), jsonObject.getInt("listId"), jsonObject.getString("name")));
            }
            mainActivity.updateData(list);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
