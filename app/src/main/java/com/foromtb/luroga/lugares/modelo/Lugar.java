package com.foromtb.luroga.lugares.modelo;

import android.support.v7.widget.RecyclerView;

import java.util.UUID;

/**
 * Created by LuisR on 10/07/2017.
 */

public class Lugar {
    private UUID mId;
    private String mNombre;

    public Lugar() {
        mId = UUID.randomUUID();
    }

    public Lugar(String nombre){
        this();
        mNombre = nombre;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public UUID getId() {
        return mId;
    }
}
