package org.littlejuan.multaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multa {

    @SerializedName("id_placa")
    @Expose
    private String idPlaca;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("fecha_multa")
    @Expose
    private String fechaMulta;
    @SerializedName("descripcion_multa")
    @Expose
    private String descripcionMulta;
    @SerializedName("valor_multa")
    @Expose
    private Integer valorMulta;
    @SerializedName("propietario")
    @Expose
    private String propietario;

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

    public String getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(String fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public String getDescripcionMulta() {
        return descripcionMulta;
    }

    public void setDescripcionMulta(String descripcionMulta) {
        this.descripcionMulta = descripcionMulta;
    }

    public Integer getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Integer valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

}