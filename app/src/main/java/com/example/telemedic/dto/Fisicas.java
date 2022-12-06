package com.example.telemedic.dto;

public class Fisicas {

    String nombre;
    String recomendacion;
    String preguntas;
    String errores;
    String etiqueta;




    public Fisicas() {
    }

    public Fisicas(String nombre, String recomendacion, String preguntas, String errores, String etiqueta) {
        this.nombre = nombre;
        this.recomendacion = recomendacion;
        this.preguntas = preguntas;
        this.errores = errores;
        this.etiqueta = etiqueta;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
