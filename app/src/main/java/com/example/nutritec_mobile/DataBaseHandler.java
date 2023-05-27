package com.example.nutritec_mobile;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBaseHandler {
    private RequestQueue requestQueue;

    public DataBaseHandler(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getVitamins(){
        String url = "https://nutritec.azurewebsites.net/nutritec/vitamin/GetVitamins";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray JSON_Vitamins) {
                try {
                    for (int i = 0; i < JSON_Vitamins.length(); i++){
                        JSONObject JSON_Vitamin = JSON_Vitamins.getJSONObject(i);
                        ObjectMapper objectMapper = new ObjectMapper();
                        Vitamin vitamin = objectMapper.readValue(JSON_Vitamin.toString(), Vitamin.class);
                        Vitamin.vitamins.add(vitamin);
                    }
                } catch (JSONException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR CONECTING",error.getMessage());
            }
        });

        requestQueue.add(request);
    }

    public boolean verifyIDAvailability(int clientID){
        return true;
    }

    public boolean verifyPassword(int clientID, String password){
        return true;
    }
}


