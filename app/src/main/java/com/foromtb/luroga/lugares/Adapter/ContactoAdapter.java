package com.foromtb.luroga.lugares.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.foromtb.luroga.lugares.modelo.Contacto;

import java.util.List;


/**
 * Created by luisromeral on 11/07/2017.
 */

public class ContactoAdapter extends RecyclerView.Adapter<ContactoViewHolder> {

    private List<Contacto> mContactos;

    public ContactoAdapter (List<Contacto> contactoList){
        mContactos = contactoList;
    }
    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactoViewHolder(LayoutInflater.from(parent.getContext()),parent,viewType);
    }

    @Override
    public void onBindViewHolder(ContactoViewHolder holder, int position) {
        Contacto contacto = mContactos.get(position);
        holder.bind(contacto);

    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }
}
