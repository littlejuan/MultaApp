package org.littlejuan.multaapp.rest;

import org.littlejuan.multaapp.model.Multa;
import org.littlejuan.multaapp.model.MultaInforme;
import org.littlejuan.multaapp.model.Propietario;
import org.littlejuan.multaapp.model.Vehiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("/multas/{id}")
    Call<List<Multa>> getMulta( @Path("id") String id );

    @GET("/multas/v2/{id_placa}")
    Call<MultaInforme> getMultaV2( @Path("id_placa") String id );

    @GET("/propietarios/{id}")
    Call<Propietario> getPropietario( @Path("id") String id );

    @GET("/vehiculos/{id_placa}")
    Call<Vehiculo> getVehiculo(@Path("id_placa") String placa);

    @POST("/propietarios")
    Call<Propietario> savePropietario(@Body Propietario propietario);

    @POST("/multas")
    Call<MultaInforme> saveMulta(@Body MultaInforme multaInforme);

    @POST("/vehiculos")
    Call<Vehiculo> saveVehiculo(@Body Vehiculo vehiculo);

    @PUT("/propietarios/{id}")
    Call<Propietario> putPropietario(@Path("id") String productId, @Body Propietario propietario);

    @PUT("/multas/{id_placa}")
    Call<MultaInforme> putMulta(@Path("id_placa") String placa_id, @Body MultaInforme multaInforme);

    @PUT("/vehiculos/{id_placa}")
    Call<Vehiculo> putVehiculo(@Path("id_placa") String placa, @Body Vehiculo vehiculo);

    @DELETE("/propietarios/{id}")
    Call<Propietario> deletePropietario(@Path("id") String id);

    @DELETE("/multas/{id_placa}")
    Call<MultaInforme> deleteMulta(@Path("id_placa") String placa_id);

    @DELETE("/vehiculos/{id_placa}")
    Call<Vehiculo> deleteVehiculo(@Path("id_placa") String placa_id);

}
