package org.littlejuan.multaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.littlejuan.multaapp.R;
import org.littlejuan.multaapp.model.MultaInforme;
import org.littlejuan.multaapp.model.Vehiculo;
import org.littlejuan.multaapp.rest.APIService;
import org.littlejuan.multaapp.rest.APIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VehiculoFragment extends Fragment{

    private APIService mAPIService;

    @BindView(R.id.edit_vehiculo_placa)
    EditText mPlaca;

    @BindView(R.id.edit_vehiculo_modelo)
    EditText mModelo;

    @BindView(R.id.edit_vehiculo_año)
    EditText mAno;

    @BindView(R.id.edit_vehiculo_cedula)
    EditText mCedula;


    public VehiculoFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vehiculo_fragment, container, false);
        ButterKnife.bind(this, view);
        mAPIService = APIUtils.getAPIService();
        return view;

    }

    @OnClick(R.id.btn_vehiculo_insertar)
    void onInsertarVehiculo(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        String modelo = mModelo.getEditableText().toString().trim();
        String ano = mAno.getEditableText().toString().trim();
        String cedula = mCedula.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(placa) && !TextUtils.isEmpty(modelo)
                && !TextUtils.isEmpty(ano) && !TextUtils.isEmpty(cedula)) {
            sendVehiculo(createVehiculo(placa, modelo, Integer.parseInt(ano), Integer.parseInt(cedula)));
        } else {
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_vehiculo_buscar)
    void onBuscarVehiculo(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(placa)) {
            getVehiculo(placa);
        } else {
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_vehiculo_alterar)
    void onAlterarVehiculo(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        String modelo = mModelo.getEditableText().toString().trim();
        String ano = mAno.getEditableText().toString().trim();
        String cedula = mCedula.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(placa) && !TextUtils.isEmpty(modelo)
                && !TextUtils.isEmpty(ano) && !TextUtils.isEmpty(cedula)) {
            putVehiculo(createVehiculo(placa, modelo, Integer.parseInt(ano), Integer.parseInt(cedula)));
        } else {
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_vehiculo_eliminar)
    void onEliminarVehiculo(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(placa)) {
            deleteVehiculo(placa);
        } else {
            Toast.makeText(getActivity(), "Campo vacíos!", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteVehiculo(String placa){
        mAPIService.deleteVehiculo(placa)
                .enqueue(new Callback<Vehiculo>() {
                    @Override
                    public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                        Toast.makeText(getActivity().getBaseContext(), "Vehículo eliminada", Toast.LENGTH_SHORT)
                                .show();

                    }

                    @Override
                    public void onFailure(Call<Vehiculo> call, Throwable t) {
                        Toast.makeText(getActivity().getBaseContext(), "Error al eliminar", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void getVehiculo(String placa){
        mAPIService.getVehiculo(placa)
                .enqueue(new Callback<Vehiculo>() {
                    @Override
                    public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                        mModelo.setText(response.body().getModelo());
                        mAno.setText(String.valueOf(response.body().getAno().intValue()));
                        mCedula.setText(String.valueOf(response.body().getIdCedulapro().intValue()));
                    }

                    @Override
                    public void onFailure(Call<Vehiculo> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error al conseguir datos", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void putVehiculo(Vehiculo vehiculo){
        mAPIService.putVehiculo(vehiculo.getIdPlaca(), vehiculo)
                .enqueue(new Callback<Vehiculo>() {
                    @Override
                    public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                        if (response.isSuccessful()) {
                            mPlaca.setText("");
                            mModelo.setText("");
                            mAno.setText("");
                            mCedula.setText("");
                            Toast.makeText(getActivity(), "Actualizado correctamente", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Vehiculo> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error al enviar datos", Toast.LENGTH_SHORT)
                                .show();

                    }
                });
    }

    private void sendVehiculo(Vehiculo vehiculo) {
        mAPIService.saveVehiculo(vehiculo).enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                if (response.isSuccessful()) {
                    mPlaca.setText("");
                    mModelo.setText("");
                    mAno.setText("");
                    mCedula.setText("");
                    Toast.makeText(getActivity(), "Eviado correctamente", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al enviar datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private Vehiculo createVehiculo(String placa, String modelo, int ano, int cedula) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdPlaca(placa);
        vehiculo.setModelo(modelo);
        vehiculo.setAno(ano);
        vehiculo.setIdCedulapro(cedula);

        return vehiculo;
    }

}
