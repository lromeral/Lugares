package com.foromtb.luroga.lugares.Presenter;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foromtb.luroga.lugares.ListadoLugares_Fragment;
import com.foromtb.luroga.lugares.modelo.Contacto;
import com.foromtb.luroga.lugares.modelo.Contactos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LuisR on 11/07/2017.
 */

public class VolleyPresenter {
    private final static String TAG ="Luis->" + VolleyPresenter.class.getSimpleName();
    private static Context sContext;
    private RequestQueue mRequestQueue;
    private static VolleyPresenter sInstance;

    public static VolleyPresenter getInstance(Context context
    ) {
        if(sInstance == null){
            sInstance = new VolleyPresenter(context);
        }
        return sInstance ;
    }

    private VolleyPresenter(Context context){
        sContext = context;
        mRequestQueue = Volley.newRequestQueue(sContext);
        
    }

    private RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(sContext);
        }
        return  mRequestQueue;
    }


    public void VolleyRequest (String stringUrl){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, stringUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d (TAG,"Conectado" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error " + error.toString());
            }
        });
        getRequestQueue().add(stringRequest);
    }

    public void jsonRequest (String urlJson){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlJson, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"Array");
                        getContactos(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error " + error);

                    }
                });

        getRequestQueue().add(jsonObjectRequest);
    }

    public void getContactos(JSONObject jsonObject){

        Gson gson = new Gson();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
            for (int i =0; i< jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                Contacto c = gson.fromJson(j.toString(),Contacto.class);
                Contactos.getInstance().addContacto(c);
                Log.d(TAG,c.getName());

            }

            Log.d (TAG,String.valueOf(Contactos.getContactos().size()));
        }
        catch (JSONException e){
            Log.d(TAG,e.toString());

        }

        return;


    }
}
