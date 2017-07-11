package com.foromtb.luroga.lugares;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;

import java.util.List;

/**
 * Created by LuisR on 10/07/2017.
 */

public class ListadoLugares_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Lugar> mLugares;
    private final static String POKEMON_JSON="https://raw.githubusercontent.com/BrunnerLivio/PokemonDataGraber/master/output.json";
    private final static String ANDROIDHIVE_CONTACTS="http://api.androidhive.info/contacts/";

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

        mLugares = Lugares.getLugares();
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewListado);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new LugarAdaptater(mLugares));
        init();
        return v;
    }

    private void init(){
        //VolleyPresenter.getInstance(getActivity()).VolleyRequest("https://raw.githubusercontent.com/BrunnerLivio/PokemonDataGraber/master/output.json");
        VolleyPresenter.getInstance(getActivity()).jsonRequest(ANDROIDHIVE_CONTACTS);
    }

    private class LugarHolder extends RecyclerView.ViewHolder{

        private TextView itemLugarNombre;

        //El constructor de RecyclerView.ViewHolder recibe un View
        public LugarHolder(LayoutInflater layoutInflater, ViewGroup parent, int viewType){
            super(layoutInflater.inflate(viewType,parent,false));
            //Items de la lista
            //itemLugarNombre= (TextView)itemView.findViewById(R.id.item_list_lugar_nombre);
        }

        private void bind(Lugar l){
            //itemLugarNombre.setText(l.getNombre());
        }
    }


    private class LugarAdaptater extends RecyclerView.Adapter<LugarHolder>{

        private List<Lugar> mLugares;

        public LugarAdaptater(List<Lugar> lugares) {
            mLugares = lugares;
        }

        @Override
        public LugarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LugarHolder(layoutInflater,parent,viewType);
        }

        @Override
        public void onBindViewHolder(LugarHolder holder, int position) {
            Lugar lugar = mLugares.get(position);
            holder.bind(lugar);
        }

        @Override
        public int getItemCount() {
            return mLugares.size();
        }

        @Override
        public int getItemViewType(int position) {
            //return R.layout.item_list_lugares;
            return R.layout.item_list_contacto;
        }
    }
}
