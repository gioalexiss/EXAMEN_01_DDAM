package com.example.ex_i_garg;

/** mensaje de la conversación - indica si lo escribió el usuario actual. */
public class Mensaje {

    private final String texto;
    private final String hora;
    private final boolean enviado;

    public Mensaje(String texto, String hora, boolean enviado) {
        this.texto = texto;
        this.hora = hora;
        this.enviado = enviado;
    }

    public String getTexto() {
        return texto;
    }

    public String getHora() {
        return hora;
    }

    public boolean isEnviado() {
        return enviado;
    }
}
