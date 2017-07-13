package com.foromtb.luroga.lugares.Adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.vistas.Lugar_Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisromeral on 12/07/2017.
 */

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugaresViewHolder> {

    List<Lugar> mLugares = new ArrayList<>();

    public LugarAdapter(List<Lugar> listaLugares){
        mLugares = listaLugares;
    }

    @Override
    public LugaresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LugaresViewHolder(LayoutInflater.from(parent.getContext()),parent,viewType);
    }

    @Override
    public void onBindViewHolder(LugaresViewHolder holder, int position) {
        Lugar l = mLugares.get(position);
        holder.bind(l);

    }

    @Override
    public int getItemCount() {
        return mLugares.size();
    }

    public class LugaresViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private static final String EXTRA_LUGAR_ID ="com.foromtb.luroga.lugar_id";
        private TextView lugarNombre, lugarDescripcion, lugarLongitud, lugarLatitud, lugarId;
        private Lugar mLugar;


        public LugaresViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType){
            super(inflater.inflate(R.layout.item_list_lugares,parent,false));
            lugarNombre = (TextView)itemView.findViewById(R.id.item_lugar_nombre);
            lugarDescripcion = (TextView)itemView.findViewById(R.id.item_lugar_descripcion);
            lugarLatitud = (TextView)itemView.findViewById(R.id.item_lugar_latitud);
            lugarLongitud = (TextView)itemView.findViewById(R.id.item_lugar_longitud);
            lugarId =(TextView)itemView.findViewById(R.id.item_lugar_id);

            itemView.setOnClickListener(this);
        }

        protected void bind (Lugar lugar){
            mLugar = lugar;

            lugarNombre.setText(lugar.getNombre());
            lugarId.setText(lugar.getId().toString());
            lugarDescripcion.setText(lugar.getDescripcion());
            lugarLatitud.setText(lugar.getLatitud());
            lugarLongitud.setText(lugar.getLongitud());
        }


        @Override
        public void onClick(View v) {
            //TODO: onClickListener del RecyclerView para cargar
            Intent i = Lugar_Activity.newIntent(itemView.getContext(),mLugar.getId());
            i.putExtra(EXTRA_LUGAR_ID,mLugar.getId());
            itemView.getContext().startActivity(i);
            Toast.makeText(itemView.getContext(),mLugar.getNombre(),Toast.LENGTH_SHORT).show();
        }
    }
}
