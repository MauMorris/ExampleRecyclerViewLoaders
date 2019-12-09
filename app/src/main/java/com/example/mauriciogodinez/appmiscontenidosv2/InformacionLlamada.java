package com.example.mauriciogodinez.appmiscontenidosv2;

public class InformacionLlamada {
    private String numero;
    private String fecha;
    private String tipo;
    private String duracion;
    private String geocode;
    private String descripcionTipo;

    public InformacionLlamada() {
        this.numero = "";
        this.fecha = "";
        this.tipo = "";
        this.duracion = "";
        this.geocode = "";
        this.descripcionTipo = "";
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getNumero() { return numero; }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }
}