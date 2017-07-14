package com.foromtb.luroga.lugares.vistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;

import java.util.List;
import java.util.UUID;

public class Lugar_Activity extends AppCompatActivity{

    private static final String EXTRA_LUGAR_ID ="com.foromtb.luroga.lugar_id";
    private ViewPager mViewPager;
    private UUID mLugarId;
    private List<Lugar> mLugarList;

    public static Intent newIntent(Context context, UUID lugarId){
        Intent intent = new Intent(context,Lugar_Activity.class);
        intent.putExtra(EXTRA_LUGAR_ID, lugarId);
        return intent;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lugar_viewpager);
        mViewPager = (ViewPager)findViewById(R.id.lugar_viewpager);

        mLugarId = (UUID)getIntent().getSerializableExtra(EXTRA_LUGAR_ID);
        mLugarList = Lugares.getLugares();


        FragmentManager fm = getSupportFragmentManager();
        Fragment fg = new Lugar_Fragment();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Lugar l = mLugarList.get(position);
                return Lugar_Fragment.newInstance(l.getId());
            }

            @Override
            public int getCount() {
                return Lugares.getLugares().size();
            }
        });

        //Coloca el pager en el lugarid adecuado
        for (int i=0;i < Lugares.getLugares().size(); i++){
            if(Lugares.getLugares().get(i).getId().equals(mLugarId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
