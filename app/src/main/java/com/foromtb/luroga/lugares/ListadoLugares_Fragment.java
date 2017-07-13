package com.foromtb.luroga.lugares;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.foromtb.luroga.lugares.Adapter.LugarAdapter;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;
import com.foromtb.luroga.lugares.vistas.Lugar_Activity;

import java.util.List;

/**
 * Created by LuisR on 10/07/2017.
 */

public class ListadoLugares_Fragment extends Fragment implements BaseView{


    private final static String FIREBASE_LUGARES = "https://lugares-2b3ff.firebaseio.com/lugares.json";
    public final static int REQUEST_NUEVO_LUGAR=100;
    private final static String TAG ="Luis-> " + ListadoLugares_Fragment.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private Button mBotonAnadir;
    private VolleyPresenter mVolleyPresenter;
    private Lugar lugarPrueba;
    private RecyclerView.Adapter mLugaresAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static ListadoLugares_Fragment newInstance() {

        Bundle args = new Bundle();

        ListadoLugares_Fragment fragment = new ListadoLugares_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mLugaresAdapter!=null){
            mLugaresAdapter.notifyDataSetChanged();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listado_lugares_fragment,container,false);

        mVolleyPresenter = new VolleyPresenter(this,getContext());
        mBotonAnadir = (Button)v.findViewById(R.id.btn_anadir);
        mBotonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Lugar nuevoLugar = new Lugar();
                Lugares.getLugares().add(nuevoLugar);
                Intent i = Lugar_Activity.newIntent(getContext(),nuevoLugar.getId());
                startActivityForResult(i,REQUEST_NUEVO_LUGAR);

            }
        });
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewListado);

        linearLayoutManager =new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int inicio =linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (inicio ==0){
                   Toast.makeText(getContext(),"Arriba " + String.valueOf(dy),Toast.LENGTH_SHORT).show();
                }

            }
        });





        init();

        return v;
    }

    public void updateUI(List<Lugar> lugares) {
        mLugaresAdapter = new LugarAdapter(lugares);
        mRecyclerView.setAdapter(mLugaresAdapter);
    }

    private void init(){
        mVolleyPresenter.getLugares(FIREBASE_LUGARES);

        //updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NUEVO_LUGAR){
            Log.d(TAG, "onActivityResult->" + requestCode);
        }
    }

    @Override
    public void completeExito(Object object, int codigo) {
        updateUI((List<Lugar>)object);

    }

    @Override
    public void completeError(Object object, int codigo) {

    }
}
