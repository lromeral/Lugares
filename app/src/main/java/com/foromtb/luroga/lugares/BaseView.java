package com.foromtb.luroga.lugares;

import android.content.Context;

/**
 * Created by LuisR on 11/07/2017.
 */

public interface BaseView {
    Context getContext();

    void completeExito(Object object, int codigo);

    void completeError(Object object, int codigo);
}
