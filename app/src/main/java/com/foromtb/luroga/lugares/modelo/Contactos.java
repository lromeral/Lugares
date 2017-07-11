package com.foromtb.luroga.lugares.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by LuisR on 11/07/2017.
 */

public class Contactos {
    private static List<Contacto> listadoContactos;
    private static Contactos sInstancia;

    private Contactos(){
        listadoContactos = new ArrayList<>();

    }

    public static Contactos getInstance() {
        if (sInstancia==null){
            sInstancia = new Contactos();
        }
        return sInstancia;
    }

    public static List<Contacto> getContactos() {
        return listadoContactos;
    }

    public static void addContacto(Contacto c){

        if(listadoContactos!=null){
            listadoContactos.add(c);
        }
    }
}
