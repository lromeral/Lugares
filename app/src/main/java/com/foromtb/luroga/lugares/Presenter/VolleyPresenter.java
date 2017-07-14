package com.foromtb.luroga.lugares.Presenter;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.foromtb.luroga.lugares.utils.BaseView;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by LuisR on 11/07/2017.
 */

public class VolleyPresenter {
    private final static String TAG ="Luis->" + VolleyPresenter.class.getSimpleName();
    private BaseView mBaseView;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private final String URL_BASE_PUERTOS ="https://lugares-2b3ff.firebaseio.com/lugares/puertos/";
    private final int JSON_DELETE = 103;

    public VolleyPresenter(BaseView v, Context context) {
        mContext = context;
        mBaseView = v;
        mRequestQueue = Volley.newRequestQueue(mContext);

    }

    public void getLugares(String urlJson){
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.GET,
                urlJson, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"Array");
                        procesaGetLugares(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error " + error);

                    }
                });

        mRequestQueue.add(jsonObject);
        return;
    }

    private void procesaGetLugares(JSONObject jsonObcject){
        List<Lugar> lugares = new ArrayList<>();

        try {
            JSONObject jsonPadre = jsonObcject.getJSONObject("puertos");
            JSONArray jsonNombres = jsonPadre.names();
            JSONObject jsonHijo;

            for (int i = 0; i < jsonNombres.length();i++){
                jsonHijo = jsonPadre.getJSONObject(jsonNombres.get(i).toString());
                Lugar l = new Lugar();
                l.setId(UUID.fromString(jsonHijo.getString("id")));
                l.setNombre(jsonNombres.get(i).toString());
                l.setDescripcion(jsonHijo.getString("descripcion"));
                l.setImagen(jsonHijo.getString("imagen"));
                l.setLatitud(jsonHijo.getString("latitud"));
                l.setLongitud(jsonHijo.getString("longitud"));

                lugares.add(l);

                mBaseView.completeExito(lugares,100);
                Lugares.setLugares(lugares);
            }



            Log.d(TAG,String.valueOf(jsonPadre.length()));


        }
        catch (JSONException e){
            Log.d(TAG,e.toString());

        }

    }

    public void addLugar (Lugar lugar) {
        String urlPut =URL_BASE_PUERTOS + lugar.getNombre()+ ".json";

        JSONObject jsonObject = new JSONObject(lugar.toMap());
        JsonObjectRequest jsonObjectPut = new JsonObjectRequest(
                Request.Method.PUT,
                urlPut,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"VolleyPresenter.addLugar.onResponse -> " + response.toString());
                        mBaseView.completeExito(response,101);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"VolleyPresenter.addLugar.onError-> " + error.toString());

                    }
                }
        );
        mRequestQueue.add(jsonObjectPut);
    }

    public void deleteLugar (Lugar lugar){
        String urlDel =URL_BASE_PUERTOS + lugar.getNombre()+ ".json";

        JSONObject jsonObject = new JSONObject(lugar.toMap());
        JsonObjectRequest jsonObjectPut = new JsonObjectRequest(
                Request.Method.DELETE,
                urlDel,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"VolleyPresenter.delete.onResponse -> " + response.toString());
                        mBaseView.completeExito(response,JSON_DELETE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"VolleyPresenter.delete.onError-> " + error.toString());

                    }
                }
        );
        mRequestQueue.add(jsonObjectPut);

    }
}
