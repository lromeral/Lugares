package com.foromtb.luroga.lugares;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.foromtb.luroga.lugares.utils.SingleFragmentLoader;
import com.foromtb.luroga.lugares.vistas.ListadoLugares_Fragment;

public class MainActivity extends SingleFragmentLoader {

    @Override
    protected Fragment createFragment() {
        return ListadoLugares_Fragment.newInstance();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        return intent;

    }
}
