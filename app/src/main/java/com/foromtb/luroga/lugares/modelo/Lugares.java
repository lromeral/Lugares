package com.foromtb.luroga.lugares.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by LuisR on 13/07/2017.
 */

public class Lugares

{
    private static List<Lugar> sLugares;
    private static Lugares ourInstance;

    public static Lugares getInstance() {
        if (ourInstance == null){
            ourInstance = new Lugares();
        }
        return ourInstance;
    }

    private Lugares() {
        sLugares = new ArrayList<>();
    }

    public List<Lugar>  getLugares(){
        return sLugares;
    }

    public void setLugares(List<Lugar> lugarList){
        sLugares = lugarList;
    }

    public Lugar getLugar (UUID lugarId){
        for(Lugar l: getLugares()){
            if (l.getId().equals(lugarId)){
                return l;
            }
        }
        return null;
    }
}
