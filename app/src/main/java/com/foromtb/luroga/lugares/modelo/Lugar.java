package com.foromtb.luroga.lugares.modelo;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LuisR on 10/07/2017.
 */

public class Lugar implements Lugarable, Serializable{
    private UUID id;
    private String nombre;
    private String imagenUrl;
    private String descripcion;
    private String longitud;
    private String latitud;
    @SerializedName("rating")
    private float valoracion;
    private Bitmap imagen;

    public Lugar() {
    }

    public Lugar (UUID idLugar){
        this.id = idLugar;
    }


    public Lugar(String nombre) {
        this.nombre = nombre;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String,String> mapa = new HashMap<>();
        mapa.put("id",getId().toString());
        mapa.put("nombre", getNombre());
        mapa.put("descripcion",getDescripcion());
        mapa.put("longitud",getLongitud());
        mapa.put("latitud",getLatitud());
        mapa.put("imagenUrl","http://www.altimetrias.net/Navarra/Artesiaga1.gif");
        mapa.put ("rating",String.valueOf(valoracion));
        return mapa;
    }
}
