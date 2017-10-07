package org.littlejuan.multaapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.littlejuan.multaapp.model.Multa;
import org.littlejuan.multaapp.rest.APIService;
import org.littlejuan.multaapp.rest.APIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.edit_cedula) EditText mCedula;
    @BindView(R.id.edit_propietario) EditText mPropietario;
    @BindView(R.id.edit_fecha) EditText mFecha;
    @BindView(R.id.edit_descripcion) EditText mDescripcion;
    @BindView(R.id.edit_valor) EditText mValor;
    @BindView(R.id.edit_placa) EditText mPlaca;
    @BindView(R.id.edit_modelo) EditText mModelo;

    List<Multa> multas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        multas = new ArrayList<>();

        disableEditText(mPropietario);
        disableEditText(mFecha);
        disableEditText(mDescripcion);
        disableEditText(mValor);
        disableEditText(mPlaca);
        disableEditText(mModelo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    @OnClick(R.id.btn_informe)
    public void onBuscar(View view)
    {
        String cedula = mCedula.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(cedula)) {
            loadData();
        } else {
            Toast.makeText(getBaseContext(), "Campo vacío!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(){
        final APIService apiService = APIUtils.getAPIService();
        Call<List<Multa>> call = apiService.getMulta(mCedula.getEditableText().toString());
        call.enqueue(new Callback<List<Multa>>() {
            @Override
            public void onResponse(Call<List<Multa>> call, Response<List<Multa>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    multas = response.body();
                    fillData();
                }else{
                    Toast.makeText(getBaseContext(), "No hay registros", Toast.LENGTH_SHORT)
                            .show();
                }

            }
            @Override
            public void onFailure(Call<List<Multa>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error al conseguir datos", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void fillData(){
        Multa multa = multas.get(0);
        mPropietario.setText(multa.getPropietario());
        mFecha.setText(multa.getFechaMulta());
        mDescripcion.setText(multa.getDescripcionMulta());
        mValor.setText(multa.getValorMulta().toString());
        mPlaca.setText(multa.getIdPlaca());
        mModelo.setText(multa.getModelo());
    }

}
