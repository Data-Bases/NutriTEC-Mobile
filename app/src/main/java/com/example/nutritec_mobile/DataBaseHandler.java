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

import java.text.DecimalFormat;
public class DataBaseHandler{
    private String baseURL = "https://nutritecapi.azurewebsites.net";
    private boolean state = false;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String response;
    private RequestQueue requestQueue;

    public DataBaseHandler(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getRecipes(final VerificationBooleanCallback callback){
        try {
            Recipe.recipes.clear();
            String url = baseURL + "/nutritec/recipe/GetRecipes";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray JSON_Recipes) {
                    try {
                        for (int i = 0; i < JSON_Recipes.length(); i++) {
                            JSONObject JSON_Recipe = JSON_Recipes.getJSONObject(i);
                            ObjectMapper objectMapper = new ObjectMapper();
                            Recipe recipe = objectMapper.readValue(JSON_Recipe.toString(), Recipe.class);
                            Recipe.recipes.add(recipe);
                        }
                        callback.onVerificationResult(true);
                    } catch (JSONException | JsonProcessingException e) {
                        callback.onVerificationResult(true);
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            Log.e("ERROR CONECTING RECIPES", e.getMessage());
        }
    }

    public void getProducts(final VerificationBooleanCallback callback){
        try {
            Product.products.clear();
            String url = baseURL + "/nutritec/product/GetAllProducts";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray JSON_Products) {
                    try {
                        for (int i = 0; i < JSON_Products.length(); i++) {
                            JSONObject JSON_Product = JSON_Products.getJSONObject(i);
                            ObjectMapper objectMapper = new ObjectMapper();
                            Product product = objectMapper.readValue(JSON_Product.toString(), Product.class);
                            Product.products.add(product);
                        }
                        callback.onVerificationResult(true);
                    } catch (JSONException | JsonProcessingException e) {
                        callback.onVerificationResult(true);
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            Log.e("ERROR CONECTING PRODUCTS", e.getMessage());
        }

    }
    public void getRecipeByID(int ID, int servings, final VerificationStringCallback callback){
        try {
            String url = baseURL + "/nutritec/recipe/GetRecipesByIdAndServings?id="+Integer.toString(ID)+"&servings="+Integer.toString(servings);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Response) {
                    try {
                        response = JSON_Response.getString("recipeName")+"\n"+
                                "Energy: "+df.format(JSON_Response.getDouble("energy"))+"\n"+
                                "Fat: "+df.format(JSON_Response.getDouble("fat"))+"\n"+
                                "Sodium: "+df.format(JSON_Response.getDouble("sodium"))+"\n"+
                                "Carbs: "+df.format(JSON_Response.getDouble("carbs"))+"\n"+
                                "Protein: "+df.format(JSON_Response.getDouble("protein"))+"\n"+
                                "Calcium: "+df.format(JSON_Response.getDouble("calcium"))+"\n"+
                                "Iron: "+ df.format(JSON_Response.getDouble("iron"))+"\n"+
                                "Products: ";
                        JSONArray products = JSON_Response.getJSONArray("products");
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject JSON_Product = products.getJSONObject(i);
                            response = response + JSON_Product.getString("name")+", ";
                        }
                        callback.onVerificationResult(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onVerificationResult("");
                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            Log.e("ERROR CONECTING LOGIN", e.getMessage());

        }
    }

    public void getProductByID(int ID, int servings, final VerificationStringCallback callback){
        try {
            String url = baseURL + "/nutritec/product/GetProductByIdAndServings?id="+Integer.toString(ID)+"&servings="+Integer.toString(servings);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Response) {
                    try {
                        response = JSON_Response.getString("name")+"\n"+
                                "Portionsize: "+JSON_Response.getString("portionsize")+"\n"+
                                "Servings: "+df.format(JSON_Response.getDouble("servings"))+"\n"+
                                "Energy: "+df.format(JSON_Response.getDouble("energy"))+"\n"+
                                "Fat: "+df.format(JSON_Response.getDouble("fat"))+"\n"+
                                "Sodium: "+df.format(JSON_Response.getDouble("sodium"))+"\n"+
                                "Carbs: "+df.format(JSON_Response.getDouble("carbs"))+"\n"+
                                "Protein: "+df.format(JSON_Response.getDouble("protein"))+"\n"+
                                "Calcium: "+df.format(JSON_Response.getDouble("calcium"))+"\n"+
                                "Iron: "+ df.format(JSON_Response.getDouble("iron"));
                        callback.onVerificationResult(response);
                    } catch (JSONException e) {
                        callback.onVerificationResult("");
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            Log.e("ERROR CONECTING LOGIN", e.getMessage());

        }
    }
    public void verifyPassword(String email, String password, final VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/credentials/LogIn?email=" + email + "&password=" + password;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Response) {
                    callback.onVerificationResult(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onVerificationResult(false);
                }
            });
            requestQueue.add(request);
        } catch (Exception e) {
            Log.e("ERROR CONECTING LOGIN", e.getMessage());
            callback.onVerificationResult(false);
        }
    }

}


