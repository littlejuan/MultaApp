package org.littlejuan.multaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehiculo {

    @SerializedName("id_placa")
    @Expose
    private String idPlaca;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("ano")
    @Expose
    private Integer ano;
    @SerializedName("id_cedulapro")
    @Expose
    private Integer idCedulapro;

    public String getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(String idPlaca) {
        this.idPlaca = idPlaca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdCedulapro() {
        return idCedulapro;
    }

    public void setIdCedulapro(Integer idCedulapro) {
        this.idCedulapro = idCedulapro;
    }

}
