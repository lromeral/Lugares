package com.foromtb.luroga.lugares.Presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foromtb.luroga.lugares.utils.BaseView;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static final int CODIGO_GET = 100;
    public static final int CODIGO_PUT = 101;
    public static final int CODIGO_DELETE = 103;
    public static final int CODIGO_UPDATE = 104;
    public static final int CODIGO_IMAGEN = 105;
    private Lugares mLugares = Lugares.getInstance();
    private List<Lugar> lugaresNuevos = new ArrayList<>();



    public VolleyPresenter(BaseView v, Context context) {
        mContext = context;
        mBaseView = v;
        mRequestQueue = Volley.newRequestQueue(mContext);
        // Instantiate the cache
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();


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
        lugaresNuevos = new ArrayList<>();

        Gson gson = new Gson();

        try {
            JSONObject jsonPadre = jsonObcject.getJSONObject("puertos");
            JSONArray jsonNombres = jsonPadre.names();
            JSONObject jsonHijo;
            Lugar lugarDesdeDataBase;

            for (int i = 0; i < jsonNombres.length();i++){
                jsonHijo = jsonPadre.getJSONObject(jsonNombres.get(i).toString());
                lugarDesdeDataBase = gson.fromJson(jsonHijo.toString(),Lugar.class);
                //getImageLugar(lugarDesdeDataBase);
                lugaresNuevos.add(lugarDesdeDataBase);
            }
            mBaseView.completeExito(lugaresNuevos, CODIGO_GET);
            Log.d(TAG,String.valueOf(jsonPadre.length()));
        }
        catch (JSONException e){
            Log.d(TAG,e.toString());

        }

    }

    public void getImageLugar (final Lugar lugar){
        ImageRequest imageRequest = new ImageRequest(
                lugar.getImagenUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        lugar.setImagen(response);
                        mBaseView.completeExito(response, CODIGO_IMAGEN);
                    }
                },
                0,
                0,
                null,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"GetImage error");

                    }
                }
        );
        mRequestQueue.add(imageRequest);
    }

    public void addLugar (Lugar lugar) {
        String urlPut =URL_BASE_PUERTOS + lugar.getId().toString() + ".json";

        JSONObject jsonObject = new JSONObject(lugar.toMap());
        JsonObjectRequest jsonObjectPut = new JsonObjectRequest(
                Request.Method.PUT,
                urlPut,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"VolleyPresenter.addLugar.onResponse -> " + response.toString());
                        mBaseView.completeExito(response, CODIGO_PUT);
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
        String urlDel =URL_BASE_PUERTOS + lugar.getId()+ ".json";

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                urlDel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mBaseView.completeExito(response, CODIGO_DELETE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"VolleyPresenter.delete.onError-> " + error.toString());
                    }
                }

        );
        mRequestQueue.add(request);

    }

    public void updateLugar(Lugar lugar){
        String urlUpdate =URL_BASE_PUERTOS + lugar.getId().toString()+ ".json";

        JSONObject jsonObject = new JSONObject(lugar.toMap());
        JsonObjectRequest jsonObjectPut = new JsonObjectRequest(
                Request.Method.PATCH,
                urlUpdate,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"VolleyPresenter.update.onResponse -> " + response.toString());
                        mBaseView.completeExito(response, CODIGO_UPDATE);
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
