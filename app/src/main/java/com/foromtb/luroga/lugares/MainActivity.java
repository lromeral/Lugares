package com.foromtb.luroga.lugares;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmentLoader {

    @Override
    protected Fragment createFragment() {
        return ListadoLugares_Fragment.newInstance();
    }
}
