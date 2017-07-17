package com.foromtb.luroga.lugares.vistas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.foromtb.luroga.lugares.utils.BaseView;
import com.foromtb.luroga.lugares.MainActivity;
import com.foromtb.luroga.lugares.Presenter.VolleyPresenter;
import com.foromtb.luroga.lugares.R;
import com.foromtb.luroga.lugares.modelo.Lugar;
import com.foromtb.luroga.lugares.modelo.Lugares;

import java.util.UUID;

public class Lugar_Fragment extends Fragment implements BaseView {

    private EditText mNuevoNombre, mNuevoDescripcion, mNuevoLongitud, mNuevoLatitud;
    private TextView mNuevoLugarId;
    private RatingBar mRatingBar;
    private VolleyPresenter mVolleyPresenter;
    private final static String TAG ="Luis ->" + Lugar_Fragment.class.getSimpleName();
    private static final String ARG_LUGAR_ID="lugar_id";
    private UUID mLugarId;
    private Lugar mLugar;
    private Lugares mLugares = Lugares.getInstance();
    private boolean mHayCambiosFormulario = false;

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
        mLugar = mLugares.getLugar(mLugarId);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lugar_fragment,container,false);
        mVolleyPresenter= new VolleyPresenter(this,getContext());

        mNuevoLugarId = (TextView)v.findViewById(R.id.add_lugarid);
        mNuevoLugarId.setText(mLugar.getId().toString());

        mNuevoNombre = (EditText)v.findViewById(R.id.add_nombre);
        mNuevoNombre.addTextChangedListener(cambioTextoFormulario);
        mNuevoNombre.setText(mLugar.getNombre());

        mNuevoDescripcion= (EditText)v.findViewById(R.id.add_descripcion);
        mNuevoDescripcion.addTextChangedListener(cambioTextoFormulario);
        mNuevoDescripcion.setText(mLugar.getDescripcion());

        mNuevoLongitud = (EditText)v.findViewById(R.id.add_longitud);
        mNuevoLongitud.addTextChangedListener(cambioTextoFormulario);
        mNuevoLongitud.setText(mLugar.getLongitud());

        mNuevoLatitud = (EditText)v.findViewById(R.id.add_latitud);
        mNuevoLatitud.addTextChangedListener(cambioTextoFormulario);
        mNuevoLatitud.setText(mLugar.getLatitud());

        mRatingBar = (RatingBar)v.findViewById(R.id.add_valoracion);
        mRatingBar.setRating(mLugar.getValoracion());

        return v;
    }

    private TextWatcher cambioTextoFormulario = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHayCambiosFormulario = true;
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lugar_menu,menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_borrar:
                new VolleyPresenter(this,getContext()).deleteLugar(mLugar);
                getActivity().finish();
                //Intent i = MainActivity.newIntent(getContext());
                //startActivity(i);
                return true;
            case R.id.item_guardar:
                Lugar lugarDatosFormulario = generaLugarDatosFormulario(mLugar.getId().toString());
                if (mHayCambiosFormulario) {
                    mVolleyPresenter.updateLugar(mLugar.getNombre(), lugarDatosFormulario);
                }
                else{
                    mVolleyPresenter.addLugar(lugarDatosFormulario);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private Lugar generaLugarDatosFormulario(@Nullable String uuid){
        Lugar lugarNuevoDesdeFormulario = new Lugar();
        if (uuid == null){
            lugarNuevoDesdeFormulario.setId(UUID.randomUUID());
        }else{
            lugarNuevoDesdeFormulario.setId(UUID.fromString(uuid));
        }
        lugarNuevoDesdeFormulario.setNombre(mNuevoNombre.getText().toString());
        lugarNuevoDesdeFormulario.setDescripcion(mNuevoDescripcion.getText().toString());
        lugarNuevoDesdeFormulario.setLongitud(mNuevoLongitud.getText().toString());
        lugarNuevoDesdeFormulario.setLatitud(mNuevoLatitud.getText().toString());
        lugarNuevoDesdeFormulario.setValoracion(mRatingBar.getRating());

        return lugarNuevoDesdeFormulario;
    }


    //TODO: Los codigos de completado. Añadir switch case
    @Override
    public void completeExito(Object object, int codigo) {

        if (codigo==105){
            Bitmap imagen = (Bitmap)object;
        }


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
