package org.littlejuan.multaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Propietario {

    @SerializedName("id_cedulapro")
    @Expose
    private Integer idCedulapro;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;

    public Integer getIdCedulapro() {
        return idCedulapro;
    }

    public void setIdCedulapro(Integer idCedulapro) {
        this.idCedulapro = idCedulapro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}