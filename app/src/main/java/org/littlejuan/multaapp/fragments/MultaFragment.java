package org.littlejuan.multaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.littlejuan.multaapp.R;
import org.littlejuan.multaapp.model.Multa;
import org.littlejuan.multaapp.model.MultaInforme;
import org.littlejuan.multaapp.model.Propietario;
import org.littlejuan.multaapp.rest.APIService;
import org.littlejuan.multaapp.rest.APIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MultaFragment extends Fragment {

    private APIService mAPIService;

    @BindView(R.id.edit_multa_placa)
    EditText mPlaca;

    @BindView(R.id.edit_multa_descripcion)
    EditText mDescripcion;

    @BindView(R.id.edit_multa_valor)
    EditText mValor;

    @BindView(R.id.edit_multa_cedula)
    EditText mCedula;

    public MultaFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multa_fragment, container, false);
        ButterKnife.bind(this, view);
        mAPIService = APIUtils.getAPIService();
        return view;

    }

    @OnClick(R.id.btn_multa_alterar)
    void onAlterarMulta(View view) {
        String placa = mPlaca.getEditableText().toString().trim();
        String descripcion = mDescripcion.getEditableText().toString().trim();
        String valor = mValor.getEditableText().toString().trim();
        String cedula = mCedula.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(placa) && !TextUtils.isEmpty(descripcion) &&
                !TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(valor)) {
            putMulta(createMulta(placa, descripcion, Integer.parseInt(valor), Integer.parseInt(cedula)));
        } else {
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_multa_eliminar)
    void onEliminarMulta(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(placa)) {
            deleteMulta(placa);
        } else {
            Toast.makeText(getActivity(), "Campo vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_multa_insertar)
    void onInsertarMulta(View view) {
        String placa = mPlaca.getEditableText().toString().trim();
        String descripcion = mDescripcion.getEditableText().toString().trim();
        String valor = mValor.getEditableText().toString().trim();
        String cedula = mCedula.getEditableText().toString().trim();

        if (!TextUtils.isEmpty(placa) && !TextUtils.isEmpty(descripcion)
                && !TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(valor)) {
            sendMulta(createMulta(placa, descripcion, Integer.parseInt(valor), Integer.parseInt(cedula)));
        } else {
            Toast.makeText(getActivity(), "Campos vacíos!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_multa_buscar)
    void onBuscarMulta(View view){
        String placa = mPlaca.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(placa)){
            getMulta(placa);
        }else{
            Toast.makeText(getActivity(), "Campo vacío!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getMulta(String placa){
        Call<MultaInforme> call = mAPIService.getMultaV2(placa);
        call.enqueue(new Callback<MultaInforme>() {
            @Override
            public void onResponse(Call<MultaInforme> call, Response<MultaInforme> response) {
                mDescripcion.setText(response.body().getDescripcionMulta());
                mValor.setText(String.valueOf(response.body().getValorMulta().intValue()));
                mCedula.setText(String.valueOf(response.body().getCedula().intValue()));
            }
            @Override
            public void onFailure(Call<MultaInforme> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Error al conseguir datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    private void deleteMulta(String placa) {
        Call<MultaInforme> call = mAPIService.deleteMulta(placa);
        call.enqueue(new Callback<MultaInforme>() {
            @Override
            public void onResponse(Call<MultaInforme> call, Response<MultaInforme> response) {
                Toast.makeText(getActivity().getBaseContext(), "Multa eliminada", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<MultaInforme> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Error al eliminar", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public MultaInforme createMulta(String placa, String descripcion, int valor, int cedula) {
        MultaInforme multaInforme = new MultaInforme();
        multaInforme.setIdPlaca(placa);
        multaInforme.setDescripcionMulta(descripcion);
        multaInforme.setValorMulta(valor);
        multaInforme.setCedula(cedula);

        return multaInforme;
    }

    public void putMulta(MultaInforme multaInforme) {
        mAPIService.putMulta(multaInforme.getIdPlaca(), multaInforme)
                .enqueue(new Callback<MultaInforme>() {
                    @Override
                    public void onResponse(Call<MultaInforme> call, Response<MultaInforme> response) {
                        if (response.isSuccessful()) {
                            mPlaca.setText("");
                            mDescripcion.setText("");
                            mValor.setText("");
                            mCedula.setText("");
                            Toast.makeText(getActivity(), "Actualizado correctamente", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MultaInforme> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error al enviar datos", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void sendMulta(MultaInforme multaInforme) {
        mAPIService.saveMulta(multaInforme).enqueue(new Callback<MultaInforme>() {
            @Override
            public void onResponse(Call<MultaInforme> call, Response<MultaInforme> response) {
                if (response.isSuccessful()) {
                    mPlaca.setText("");
                    mDescripcion.setText("");
                    mValor.setText("");
                    mCedula.setText("");
                    Toast.makeText(getActivity(), "Eviado correctamente", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<MultaInforme> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al enviar datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
