package com.example.ex_i_garg;

/** Contacto que aparece en el listado*/
public class Usuario {

    private final String nombre;
    private final String usuario;
    private final int color;
    private final boolean enLinea;

    public Usuario(String nombre, String usuario, int color, boolean enLinea) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.color = color;
        this.enLinea = enLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getColor() {
        return color;
    }

    public boolean isEnLinea() {
        return enLinea;
    }

    public String getInicial() {
        return nombre.substring(0, 1).toUpperCase();
    }
}
