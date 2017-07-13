package com.foromtb.luroga.lugares.modelo;

import java.util.List;
import java.util.UUID;

/**
 * Created by LuisR on 13/07/2017.
 */

public class Lugares

{
    public static List<Lugar> sLugares;
    private static final Lugares ourInstance = new Lugares();

    public static Lugares getInstance() {
        return ourInstance;
    }

    private Lugares() {
    }

    public static List<Lugar>  getLugares(){
        return sLugares;
    }

    public static void setLugares(List<Lugar> lugarList){
        sLugares = lugarList;
    }

    public static Lugar getLugar (UUID lugarId){
        for(Lugar l: getLugares()){
            if (l.getId().equals(lugarId)){
                return l;
            }
        }
        return null;
    }
}
