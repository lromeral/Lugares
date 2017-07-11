package com.foromtb.luroga.lugares.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Contacto;

import java.util.List;


/**
 * Created by luisromeral on 11/07/2017.
 */

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Contacto> mContactos;

    public ContactoAdapter(List<Contacto> contactoList) {
        mContactos = contactoList;
    }

    @Override
    public ContactoAdapter.ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactoAdapter.ContactoViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
    }

    @Override
    public void onBindViewHolder(ContactoAdapter.ContactoViewHolder holder, int position) {
        Contacto contacto = mContactos.get(position);
        holder.bind(contacto);

    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, email, gender;

        public ContactoViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(R.layout.item_list_contacto, parent, false));

            nombre = (TextView) itemView.findViewById(R.id.item_list_nombre);
            email = (TextView) itemView.findViewById(R.id.item_list_email);
            gender = (TextView) itemView.findViewById(R.id.item_list_gender);
        }

        protected void bind(Contacto c) {
            nombre.setText(c.getName());
            email.setText(c.getEmail());
            gender.setText(c.getGender());


        }
    }
}
