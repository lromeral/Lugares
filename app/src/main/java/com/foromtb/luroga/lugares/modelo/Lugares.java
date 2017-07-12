package com.foromtb.luroga.lugares.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuisR on 10/07/2017.
 */

public class Lugares {
    private static List<Lugar> sLugares;
    private static Lugares sInstance;

    private Lugares(){
        sLugares = new ArrayList<>();
    }

     public static Lugares getInstance() {
         if (sInstance == null) {
             sInstance = new Lugares();
         }
        return sInstance;
    }

    public static List<Lugar> getLugares() {
        return sLugares;
    }

}
