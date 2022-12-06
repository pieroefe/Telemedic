package com.example.telemedic.dto;

public class Usuario {

    private String nombre;
    private String codigo;

    private String correo;
    private String rol;
    private String key;

    public Usuario() {
    }

    public Usuario(String nombre, String codigo, String correo, String rol, String key) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.correo = correo;
        this.rol = rol;
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
