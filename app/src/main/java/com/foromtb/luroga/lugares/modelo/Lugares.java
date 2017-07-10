package com.foromtb.luroga.lugares.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuisR on 10/07/2017.
 */

public abstract class Lugares {
    private static List<Lugar> sLugares = new ArrayList<>();

    public static List<Lugar> getLugares() {

        for (int i=0; i<20;i++){
            sLugares.add(new Lugar("NombreLugar_" + String.valueOf(i)));
        }
        return sLugares;
    }
}
