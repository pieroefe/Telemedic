package com.example.telemedic.dto;

public class Psicologicas {

    String nombre;
    String recomendacion;
    String preguntas;
    String errores;

    public Psicologicas() {
    }


    public Psicologicas(String nombre, String recomendacion, String preguntas, String errores) {
        this.nombre = nombre;
        this.recomendacion = recomendacion;
        this.preguntas = preguntas;
        this.errores = errores;
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
}
