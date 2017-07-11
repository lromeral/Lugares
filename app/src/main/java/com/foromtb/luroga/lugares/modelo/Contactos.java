package com.foromtb.luroga.lugares.modelo;

import java.util.List;

/**
 * Created by LuisR on 11/07/2017.
 */

public abstract class Contactos {
    private static List<Contacto> contactos;

    public static List<Contacto> getContactos() {
        return contactos;
    }

    public static void setContactos(List<Contacto> contactos) {
        Contactos.contactos = contactos;
    }
}
