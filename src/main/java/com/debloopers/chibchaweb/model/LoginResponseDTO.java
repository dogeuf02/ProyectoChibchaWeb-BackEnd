package com.debloopers.chibchaweb.model;

public class LoginResponseDTO {
    private boolean autenticado;
    private String mensaje;
    private String rol;

    // Constructor
    public LoginResponseDTO(boolean autenticado, String mensaje, String rol) {
        this.autenticado = autenticado;
        this.mensaje = mensaje;
        this.rol = rol;
    }

    // Getters y setters
    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}