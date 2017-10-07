package org.littlejuan.multaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.littlejuan.multaapp.R;
import org.littlejuan.multaapp.model.Multa;
import org.littlejuan.multaapp.model.Propietario;
import org.littlejuan.multaapp.rest.APIService;
import org.littlejuan.multaapp.rest.APIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropietarioFragment extends Fragment {

    private APIService mAPIService;

    @BindView(R.id.edit_cedulapro)
    EditText mCedulaPro;

    @BindView(R.id.edit_nombre)
    EditText mNombre;

    @BindView(R.id.edit_apellido)
    EditText mApellido;

    public PropietarioFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.propietario_fragment, container, false);
        ButterKnife.bind(this, view);
        mAPIService = APIUtils.getAPIService();
        return view;
    }

    @OnClick(R.id.btn_insertar)
    void onInsertar(View view) {
        String cedula = mCedulaPro.getEditableText().toString().trim();
        String nombre = mNombre.getEditableText().toString().trim();
        String apellido = mApellido.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(cedula) ) {
            sendPropietario(Integer.parseInt(cedula), nombre, apellido);
        } else{
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_propietario_buscar)
    void onBuscar(View view) {
        String cedula = mCedulaPro.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(cedula)) {
            getPropietario();
        }else {
            Toast.makeText(getActivity(), "Campo vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_propietario_alterar)
    void onAlterar(View view){
        int cedula = Integer.parseInt(mCedulaPro.getEditableText().toString().trim());
        String nombre = mNombre.getEditableText().toString().trim();
        String apellido = mApellido.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && cedula != 0) {
            putPropietario(cedula, nombre, apellido);
        } else{
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_propietario_eliminar)
    void onEliminar(View view){
        String cedula = mCedulaPro.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(cedula)) {
            deletePropietario(cedula);
        } else{
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePropietario(String cedula) {
        Call<Propietario> call = mAPIService
                .deletePropietario(cedula);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Toast.makeText(getActivity().getBaseContext(), "Propietario eliminado", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Error al eliminar", Toast.LENGTH_SHORT)
                        .show();
                Log.d("RETROFIT", t.toString());
            }
        });
    }

    public void getPropietario(){

        Call<Propietario> call = mAPIService
                .getPropietario(mCedulaPro.getEditableText().toString().trim());
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                mNombre.setText(response.body().getNombre());
                mApellido.setText(response.body().getApellido());
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Error al conseguir datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void putPropietario(int cedula, String nombre, String apellido){
        Propietario propietario = new Propietario();
        propietario.setIdCedulapro(cedula);
        propietario.setNombre(nombre);
        propietario.setApellido(apellido);

        mAPIService.putPropietario(mCedulaPro.getEditableText().toString().trim(), propietario)
                .enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Toast.makeText(getActivity().getBaseContext(), "Campos actualizados", Toast.LENGTH_SHORT)
                        .show();
                mNombre.setText("");
                mApellido.setText("");
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Error al actualizar", Toast.LENGTH_SHORT)
                        .show();
                Log.d("RETROFIT", t.toString());
            }
        });
    }

    public void sendPropietario(int cedula, String nombre, String apellido) {
        Propietario propietario = new Propietario();
        propietario.setIdCedulapro(cedula);
        propietario.setNombre(nombre);
        propietario.setApellido(apellido);

        mAPIService.savePropietario(propietario).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mCedulaPro.setText("");
                    mNombre.setText("");
                    mApellido.setText("");
                    Toast.makeText(getActivity(), "Eviado correctamente", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al enviar datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
