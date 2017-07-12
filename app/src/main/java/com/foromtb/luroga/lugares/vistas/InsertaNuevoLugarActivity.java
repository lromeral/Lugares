package com.foromtb.luroga.lugares.vistas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.SingleFragmentLoader;

public class InsertaNuevoLugarActivity extends SingleFragmentLoader{

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,InsertaNuevoLugarActivity.class);
        return intent;

    }

    @Override
    protected Fragment createFragment() {
        return InsertaNuevoLugar_Fragment.newInstance();
    }
}
