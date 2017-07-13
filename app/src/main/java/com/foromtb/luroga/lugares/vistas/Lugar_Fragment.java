package com.foromtb.luroga.lugares.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.foromtb.luroga.lugares.BaseView;
import com.foromtb.luroga.lugares.MainActivity;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;

import java.util.UUID;

public class Lugar_Fragment extends Fragment implements BaseView {

    private EditText mNuevoNombre, mNuevoDescripcion, mNuevoLongitud, mNuevoLatitud;
    private Button mBotonAnadir;
    private VolleyPresenter mVolleyPresenter;
    private final static String TAG ="Luis ->" + Lugar_Fragment.class.getSimpleName();
    private static final String EXTRA_LUGAR_ID ="com.foromtb.luroga.lugar_id";
    private static final String ARG_LUGAR_ID="lugar_id";
    private UUID mLugarId;
    private Lugar mLugar;

    public Lugar_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static Lugar_Fragment newInstance(UUID lugarId) {
        Lugar_Fragment fragment = new Lugar_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LUGAR_ID,lugarId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLugarId= (UUID)getArguments().get(ARG_LUGAR_ID);
        mLugar = Lugares.getLugar(mLugarId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lugar_fragment,container,false);
        mVolleyPresenter= new VolleyPresenter(this,getContext());
        mNuevoNombre = (EditText)v.findViewById(R.id.add_nombre);
        mNuevoNombre.setText(mLugar.getNombre());

        mNuevoDescripcion= (EditText)v.findViewById(R.id.add_descripcion);
        mNuevoDescripcion.setText(mLugar.getDescripcion());

        mNuevoLongitud = (EditText)v.findViewById(R.id.add_longitud);
        mNuevoLongitud.setText(mLugar.getLongitud());

        mNuevoLatitud = (EditText)v.findViewById(R.id.add_latitud);
        mNuevoLatitud.setText(mLugar.getLatitud());

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
        Intent i = new Intent();

        getActivity().setResult(Activity.RESULT_OK,null);
        Log.d(TAG,"Nuevo-> exito");
        startActivity(MainActivity.newIntent(getContext()));

    }

    @Override
    public void completeError(Object object, int codigo) {
        Log.d(TAG,"Nuevo-> Error");

    }
}
