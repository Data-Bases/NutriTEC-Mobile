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

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                        callback.onVerificationResult(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(request);
        }catch(Exception e) {
            callback.onVerificationResult(false);
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
                        callback.onVerificationResult(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(request);
        }catch(Exception e) {
            callback.onVerificationResult(false);
        }
    }
    public void getRecipeByID(int ID, Double servings, final VerificationStringCallback callback){
        try {
            String url = baseURL + "/nutritec/recipe/GetRecipesByIdAndServings?id="+Integer.toString(ID)+"&servings="+Double.toString(servings);
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
                            if (i == products.length()-1){
                                response = response + JSON_Product.getString("name");

                            } else {
                                response = response + JSON_Product.getString("name")+", ";
                            }
                        }
                        callback.onVerificationResult(response);
                    } catch (JSONException e) {
                        callback.onVerificationResult("");
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
            callback.onVerificationResult("");
        }
    }

    public void getProductByID(int ID, Double servings, final VerificationStringCallback callback){
        try {
            String url = baseURL + "/nutritec/product/GetProductByIdAndServings?id="+Integer.toString(ID)+"&servings="+Double.toString(servings);
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
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            callback.onVerificationResult("");

        }
    }

    public void addRecipe(String name, final VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/recipe/CreateRecipe";

            JSONObject recipeObject = new JSONObject();
            recipeObject.put("recipeName", name);

            JSONArray productsArray = new JSONArray();
            for (ProductToAdd product : ProductToAdd.products_to_add) {
                JSONObject productObject = new JSONObject();
                productObject.put("id", product.getId());
                productObject.put("servings", product.getServings());
                productsArray.put(productObject);
            }

            recipeObject.put("products", productsArray);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, recipeObject, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }

    public void deleteRecipe(int id, final VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/recipe/DeleteRecipe?recipeId="+Integer.toString(id);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }
    public void verifyPassword(String email, String password, final VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/credentials/LogIn?email=" + email + "&password=" + password;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Response) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Client client = null;
                    try {
                        client = objectMapper.readValue(JSON_Response.toString(), Client.class);
                    } catch (JsonProcessingException e) {
                        callback.onVerificationResult(false);
                    }
                    Client.currentClient = client;
                    callback.onVerificationResult(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError  error) {
                    callback.onVerificationResult(false);
                }
            });
            requestQueue.add(request);
        } catch (Exception e) {
            callback.onVerificationResult(false);
        }
    }

    public void getDaily(int meal, VerificationBooleanCallback callback) {
        try {
            DailyMeal.breakfast.clear();
            DailyMeal.lunch.clear();
            DailyMeal.dinner.clear();
            DailyMeal.snacks.clear();

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String mDate = formatter.format(currentTime);

            String url = baseURL + "/nutritec/patient/GetDailyConsumptionByPatient?patientId="+Client.currentClient.getId()+"&dateConsumed="+ URLEncoder.encode(mDate, "UTF-8");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Daily) {
                    try {
                        if (meal == 0){
                            DailyMeal.breakfast.clear();
                        } else if (meal == 1){
                            DailyMeal.lunch.clear();
                        } else if (meal == 2){
                            DailyMeal.dinner.clear();
                        } else if (meal == 3){
                            DailyMeal.snacks.clear();
                        }
                        JSONArray JSON_Meals = JSON_Daily.getJSONArray("meals");
                        JSONObject JSON_Mealtime = JSON_Meals.getJSONObject(meal);
                        JSONArray JSON_Products = JSON_Mealtime.getJSONArray("products");
                        JSONArray JSON_Recipes = JSON_Mealtime.getJSONArray("recipes");
                        for (int i = 0; i < JSON_Products.length(); i++) {
                            JSONObject JSON_Product = JSON_Products.getJSONObject(i);
                            DailyMeal product = new DailyMeal(
                                    JSON_Product.getInt("id"),
                                    JSON_Product.getString("name"),
                                    "P",
                                    JSON_Product.getDouble("servings"));
                            if (meal == 0){
                                DailyMeal.breakfast.add(product);
                            } else if (meal == 1){
                                DailyMeal.lunch.add(product);
                            } else if (meal == 2){
                                DailyMeal.dinner.add(product);
                            } else if (meal == 3){
                                DailyMeal.snacks.add(product);
                            }
                        }
                        for (int i = 0; i < JSON_Recipes.length(); i++) {
                            JSONObject JSON_Product = JSON_Recipes.getJSONObject(i);
                            DailyMeal product = new DailyMeal(
                                    JSON_Product.getInt("id"),
                                    JSON_Product.getString("name"),
                                    "R",
                                    JSON_Product.getDouble("servings"));

                            if (meal == 0){
                                DailyMeal.breakfast.add(product);
                            } else if (meal == 1){
                                DailyMeal.lunch.add(product);
                            } else if (meal == 2){
                                DailyMeal.dinner.add(product);
                            } else if (meal == 3){
                                DailyMeal.snacks.add(product);
                            }
                        }
                        if (meal == 0){
                            DailyMeal.breakfast_cal = JSON_Mealtime.getDouble("calories");
                        } else if (meal == 1){
                            DailyMeal.lunch_cal = JSON_Mealtime.getDouble("calories");
                        } else if (meal == 2){
                            DailyMeal.dinner_cal = JSON_Mealtime.getDouble("calories");
                        } else if (meal == 3){
                            DailyMeal.snacks_cal = JSON_Mealtime.getDouble("calories");
                        }
                        callback.onVerificationResult(true);
                    } catch (JSONException e) {
                        callback.onVerificationResult(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onVerificationResult(false);
                }
            });
            requestQueue.add(request);
        }catch(Exception e) {
            callback.onVerificationResult(false);
        }
    }

    public void getProductsAndRecipes(VerificationBooleanCallback callback) {
        DailyMeal.recipes_products.clear();
        getRecipes(new VerificationBooleanCallback() {
            @Override
            public void onVerificationResult(boolean state) {
                getProducts(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        for (Recipe recipe : Recipe.recipes){
                            DailyMeal new_recipe= new DailyMeal(recipe.getId(),recipe.getName(),"R",1.0);
                            DailyMeal.recipes_products.add(new_recipe);
                        }
                        for (Product product : Product.products){
                            DailyMeal new_product = new DailyMeal(product.getId(),product.getName(),"P",1.0);
                            DailyMeal.recipes_products.add(new_product);
                        }
                        callback.onVerificationResult(true);
                    }
                });
            }
        });
    }
    public void addOneDaily(DailyMeal product, VerificationBooleanCallback callback) {
        try {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String mDate = formatter.format(currentTime);

            String url;
            JSONObject recipeObject = new JSONObject();
            if (product.getType().equals("R")) {
                url = baseURL + "/nutritec/patient/AddRecipeToPatient";
                recipeObject.put("recipeid", product.getId());
                recipeObject.put("patientid", Client.currentClient.getId());
            } else {
                url = baseURL + "/nutritec/patient/AddProductToPatient";
                recipeObject.put("productId", product.getId());
                recipeObject.put("patientId", Client.currentClient.getId());
            }
            recipeObject.put("mealtime", DailyMeal.currentDaily);
            recipeObject.put("consumedate", mDate);
            recipeObject.put("servings", product.getServings());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, recipeObject, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }

    public void addDaily(VerificationBooleanCallback callback) {
        try {
            for(DailyMeal product: DailyMeal.add_recipes_products){
                addOneDaily(product, new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {

                    }
                });
            }
            callback.onVerificationResult(true);
        } catch (Exception e) {
            callback.onVerificationResult(false);
        }
    }

    public void deleteProductFromRecipe(int id, VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/recipe/DeleteProductInRecipe?recipeId="+ProductToAdd.currentRecipe+"&productId="+id;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }

    public void addProductToRecipe(int id, double servings, VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/recipe/InsertProductToRecipe?recipeId="+ProductToAdd.currentRecipe+"&productId="+id+"&servings="+servings;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }

    public void editProductServing(int id, double servings, VerificationBooleanCallback callback) {
        try {
            String url = baseURL + "/nutritec/recipe/EditProductInRecipe?recipeId="+ProductToAdd.currentRecipe+"&productId="+id+"&servings="+servings;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
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
            callback.onVerificationResult(false);
        }
    }

    public void getRecipe(int id, VerificationBooleanCallback callback) {
        try {
            ProductToAdd.products_to_edit.clear();
            String url = baseURL + "/nutritec/recipe/GetRecipesByIdAndServings?id="+id+"&servings=1.0";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject JSON_Response) {
                    try {
                        JSONArray JSON_Products = JSON_Response.getJSONArray("products");
                        for (int i = 0; i < JSON_Products.length(); i++) {
                            JSONObject JSON_Product = JSON_Products.getJSONObject(i);
                            ProductToAdd product = new ProductToAdd(
                                    JSON_Product.getInt("id"),
                                    JSON_Product.getString("name"),
                                    JSON_Product.getDouble("servings")
                            );
                            ProductToAdd.products_to_edit.add(product);
                        }
                        callback.onVerificationResult(true);
                    } catch (JSONException e) {
                        callback.onVerificationResult(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onVerificationResult(false);
                }
            });
            requestQueue.add(request);
        }catch(Exception e){
            callback.onVerificationResult(false);
        }
    }
}


