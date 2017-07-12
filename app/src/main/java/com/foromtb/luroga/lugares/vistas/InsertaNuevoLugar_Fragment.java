package com.foromtb.luroga.lugares.vistas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.foromtb.luroga.lugares.BaseView;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;

public class InsertaNuevoLugar_Fragment extends Fragment implements BaseView {

    private EditText mNuevoNombre, mNuevoDescripcion, mNuevoLongitud, mNuevoLatitud;
    private Button mBotonAnadir;
    private VolleyPresenter mVolleyPresenter;

    public InsertaNuevoLugar_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static InsertaNuevoLugar_Fragment newInstance() {
        InsertaNuevoLugar_Fragment fragment = new InsertaNuevoLugar_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.inserta_nuevo_lugar_fragment,container,false);
        mVolleyPresenter= VolleyPresenter.getInstance(this,getContext());

        mNuevoNombre = (EditText)v.findViewById(R.id.add_nombre);
        mNuevoDescripcion= (EditText)v.findViewById(R.id.add_descripcion);
        mNuevoLongitud = (EditText)v.findViewById(R.id.add_longitud);
        mNuevoLatitud = (EditText)v.findViewById(R.id.add_latitud);
        mBotonAnadir = (Button)v.findViewById(R.id.add_boton_anadir);
        mBotonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lugar l = new Lugar();
                l.setNombre(mNuevoNombre.getText().toString());
                l.setDescripcion(mNuevoDescripcion.getText().toString());
                l.setLongitud(mNuevoLongitud.getText().toString());
                l.setLatitud(mNuevoLatitud.getText().toString());
                mVolleyPresenter.addLugar(l);

            }
        });
        return v;
    }

    @Override
    public void completeExito(Object object, int codigo) {

    }

    @Override
    public void completeError(Object object, int codigo) {

    }
}
