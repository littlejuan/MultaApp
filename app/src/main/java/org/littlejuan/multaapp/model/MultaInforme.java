package org.littlejuan.multaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultaInforme {

    @SerializedName("id_multa")
    @Expose
    private Integer idMulta;
    @SerializedName("fecha_multa")
    @Expose
    private String fechaMulta;
    @SerializedName("id_placa")
    @Expose
    private String idPlaca;
    @SerializedName("descripcion_multa")
    @Expose
    private String descripcionMulta;
    @SerializedName("estado_multa")
    @Expose
    private Integer estadoMulta;
    @SerializedName("valor_multa")
    @Expose
    private Integer valorMulta;
    @SerializedName("cedula")
    @Expose
    private Integer cedula;

    public Integer getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public String getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(String fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public String getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(String idPlaca) {
        this.idPlaca = idPlaca;
    }

    public String getDescripcionMulta() {
        return descripcionMulta;
    }

    public void setDescripcionMulta(String descripcionMulta) {
        this.descripcionMulta = descripcionMulta;
    }

    public Integer getEstadoMulta() {
        return estadoMulta;
    }

    public void setEstadoMulta(Integer estadoMulta) {
        this.estadoMulta = estadoMulta;
    }

    public Integer getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Integer valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

}
