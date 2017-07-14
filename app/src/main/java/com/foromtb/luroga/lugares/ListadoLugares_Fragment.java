package com.foromtb.luroga.lugares;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
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
    private List<Lugar> mLugarList;
    private RecyclerView.Adapter mLugaresAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipelayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_SHORT).show();
                updateUI();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        updateUI();

        return v;
    }

    public void updateUI() {
        mVolleyPresenter.getLugares(FIREBASE_LUGARES);
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
        mLugarList = (List<Lugar>)object;
        mLugaresAdapter = new LugarAdapter(mLugarList);
        mRecyclerView.setAdapter(mLugaresAdapter);

    }

    @Override
    public void completeError(Object object, int codigo) {

    }
}
