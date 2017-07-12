package com.foromtb.luroga.lugares;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foromtb.luroga.lugares.Adapter.ContactoAdapter;
import com.foromtb.luroga.lugares.Adapter.LugarAdapter;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.modelo.Contacto;
import com.foromtb.luroga.lugares.modelo.Contactos;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;
import com.foromtb.luroga.lugares.vistas.InsertaNuevoLugarActivity;

import java.util.List;

/**
 * Created by LuisR on 10/07/2017.
 */

public class ListadoLugares_Fragment extends Fragment implements BaseView {

    private final static String POKEMON_JSON="https://raw.githubusercontent.com/BrunnerLivio/PokemonDataGraber/master/output.json";
    private final static String ANDROIDHIVE_CONTACTS="http://api.androidhive.info/contacts/";
    private final static String OBJETO_JSON="https://graph.facebook.com/me";
    private final static String FIREBASE_LUGARES = "https://lugares-2b3ff.firebaseio.com/lugares.json";
    private RecyclerView mRecyclerView;
    private Button mBotonAnadir;
    private List<Lugar> mLugares;
    private VolleyPresenter mVolleyPresenter;
    private Lugar lugarPrueba;

    public static ListadoLugares_Fragment newInstance() {

        Bundle args = new Bundle();

        ListadoLugares_Fragment fragment = new ListadoLugares_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listado_lugares_fragment,container,false);

        lugarPrueba = new Lugar("PuertoPrueba");
        lugarPrueba.setDescripcion("Descripcion puerto prueba");
        lugarPrueba.setLongitud("40.2");
        lugarPrueba.setLatitud("1.1");
        lugarPrueba.setImagen("Imagen falsa");

        mVolleyPresenter = VolleyPresenter.getInstance(this, getContext());

        mLugares = Lugares.getLugares();
        mBotonAnadir = (Button)v.findViewById(R.id.btn_anadir);
        mBotonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mVolleyPresenter.addLugar(lugarPrueba);
                Intent i = InsertaNuevoLugarActivity.newIntent(getContext());
                startActivity(i);

            }
        });
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewListado);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.setAdapter(new LugarAdaptater(mLugares));
        init();
        return v;
    }

    public void updateUI(List<Lugar> lugares) {
        mRecyclerView.setAdapter(new LugarAdapter(lugares));
    }

    private void init(){
        //VolleyPresenter.getInstance(getActivity()).volleyRequest("https://raw.githubusercontent.com/BrunnerLivio/PokemonDataGraber/master/output.json");
        mVolleyPresenter.jsonRequest(FIREBASE_LUGARES);

        //updateUI();
    }

    @Override
    public void completeExito(Object object, int codigo) {
        updateUI((List<Lugar>) object);
    }

    @Override
    public void completeError(Object object, int codigo) {

    }
}
