package com.foromtb.luroga.lugares.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.foromtb.luroga.lugares.Adapter.LugarAdapter;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;
import com.foromtb.luroga.lugares.utils.BaseView;

import java.util.List;
import java.util.UUID;

/**
 * Created by LuisR on 10/07/2017.
 */

public class ListadoLugares_Fragment extends Fragment implements BaseView {


    private final static String FIREBASE_LUGARES = "https://lugares-2b3ff.firebaseio.com/lugares.json";
    public final static int REQUEST_NUEVO_LUGAR=100;
    private final static String TAG ="Luis-> " + ListadoLugares_Fragment.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private Button mBotonAnadir;
    private VolleyPresenter mVolleyPresenter;
    private List<Lugar> mLugarList;
    private RecyclerView.Adapter mLugaresAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;



    private Lugares mLugares = Lugares.getInstance();

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
            updateUI();
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
                Lugar nuevoLugar = new Lugar(UUID.randomUUID());
                mLugares.getLugares().add(nuevoLugar);
                Intent i = Lugar_Activity.newIntent(getContext(),nuevoLugar.getId());
                startActivityForResult(i,REQUEST_NUEVO_LUGAR);

            }
        });
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewListado);
        linearLayoutManager =new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipelayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_SHORT).show();
                updateData();
                updateUI();
            }
        });

        updateData();
        updateUI();



        return v;
    }

    public void updateData(){
        mVolleyPresenter.getLugares(FIREBASE_LUGARES);
    }

    public void updateUI() {
        if(mSwipeRefreshLayout.isRefreshing()) {

            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(mLugaresAdapter != null) {
            mLugaresAdapter.notifyDataSetChanged();
            mRecyclerView.invalidate();
        }
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
        switch (codigo){
            case VolleyPresenter.CODIGO_IMAGEN: //Imagen
                updateUI();
                break;
            case VolleyPresenter.CODIGO_GET: //ProcesaLugares
                mLugarList = (List<Lugar>)object;
                mLugares.setLugares(mLugarList);
                mLugaresAdapter = new LugarAdapter(mLugarList);
                mRecyclerView.setAdapter(mLugaresAdapter);
                break;
            case VolleyPresenter.CODIGO_DELETE:
                updateUI();
                break;
            default: return;
        }





    }

    @Override
    public void completeError(Object object, int codigo) {

    }
}
