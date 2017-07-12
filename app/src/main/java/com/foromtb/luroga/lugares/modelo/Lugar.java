package com.foromtb.luroga.lugares.modelo;

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LuisR on 10/07/2017.
 */

public class Lugar implements Lugarable{
    private UUID id;
    private String nombre;
    private String imagen;
    private String descripcion;
    private String longitud;
    private String latitud;

    public Lugar() {
        this.id = UUID.randomUUID();
    }

    public Lugar(String nombre) {
        this();
        this.nombre = nombre;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    @Override
    public Map<String, String> toMap() {
        Map<String,String> mapa = new HashMap<>();
        mapa.put("id",getId().toString());
        mapa.put("descripcion",getDescripcion());
        mapa.put("longitud",getLongitud());
        mapa.put("latitud",getLatitud());
        mapa.put("imagen","URL a la imagen - cambiar-");
        return mapa;
    }
}
