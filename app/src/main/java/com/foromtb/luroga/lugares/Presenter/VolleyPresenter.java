package com.foromtb.luroga.lugares.Presenter;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foromtb.luroga.lugares.BaseView;
import com.foromtb.luroga.lugares.modelo.Contactos;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by LuisR on 11/07/2017.
 */

public class VolleyPresenter {
    private final static String TAG ="Luis->" + VolleyPresenter.class.getSimpleName();
    private static VolleyPresenter sInstance;
    private Context mContext;
    private BaseView mBaseView;
    private RequestQueue mRequestQueue;

    private VolleyPresenter(BaseView view, Context context) {
        mContext = context;
        mBaseView = view;


    }

    public static VolleyPresenter getInstance(BaseView view, Context context) {
        if(sInstance == null){
            sInstance = new VolleyPresenter(view, context);
        }
        return sInstance ;
    }

    private RequestQueue getRequestQueue(){
        mRequestQueue = Volley.newRequestQueue(mContext);
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return  mRequestQueue;
    }


    public void volleyRequest(String stringUrl){

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

        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.GET,
                urlJson, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"Array");
                        getLugares(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error " + error);

                    }
                });

        getRequestQueue().add(jsonObject);
    }


    public void getLugares(JSONObject jsonObject){

        Gson gson = new Gson();


      try {
          JSONObject jsonPadre = jsonObject.getJSONObject("puertos");
          JSONArray jsonNombres = jsonPadre.names();
          JSONObject jsonHijo;
          for (int i = 0; i < jsonNombres.length();i++){
              jsonHijo = jsonPadre.getJSONObject(jsonNombres.get(i).toString());
              Lugar l = new Lugar();
              l.setNombre(jsonNombres.get(i).toString());
              l.setDescripcion(jsonHijo.getString("descripcion"));
              l.setImagen(jsonHijo.getString("imagen"));
              l.setLatitud(jsonHijo.getString("latitud"));
              l.setLongitud(jsonHijo.getString("longitud"));

              Lugares.getInstance().getLugares().add(l);
          }



          Log.d(TAG,String.valueOf(jsonPadre.length()));


           mBaseView.completeExito(Lugares.getLugares(), 100);
        }
        catch (JSONException e){
            Log.d(TAG,e.toString());

        }
        return;


    }
    public void addLugar (Lugar lugar) {
        String urlPut ="https://lugares-2b3ff.firebaseio.com/lugares/puertos/" + lugar.getNombre()+ ".json";

        JSONObject jsonObject = new JSONObject(lugar.toMap());
        JsonObjectRequest jsonObjectPut = new JsonObjectRequest(
                Request.Method.PUT,
                urlPut,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.toString());

                    }
                }
        );
        getRequestQueue().add(jsonObjectPut);
    }
}
