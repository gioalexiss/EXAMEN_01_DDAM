package com.example.ex_i_garg;

import android.content.Context;
import android.content.SharedPreferences;

/** Guarda en el teléfono las cuentas creadas en el registro */
public class CuentaRepositorio {

    public static final String USUARIO_ADMIN = "admin";
    private static final String CLAVE_ADMIN = "123456";

    private final SharedPreferences prefs;

    public CuentaRepositorio(Context context) {
        prefs = context.getSharedPreferences("cuentas", Context.MODE_PRIVATE);
    }

    public boolean existeUsuario(String usuario) {
        String u = usuario.toLowerCase();
        return u.equals(USUARIO_ADMIN) || prefs.contains("clave_" + u);
    }

    public boolean existeCorreo(String correo) {
        return prefs.contains("correo_" + correo.toLowerCase());
    }

    public void guardar(String nombre, String correo, String usuario, String clave) {
        String u = usuario.toLowerCase();
        prefs.edit()
                .putString("clave_" + u, clave)
                .putString("nombre_" + u, nombre)
                .putString("correo_" + correo.toLowerCase(), u)
                .apply();
    }

    /**
     * Valida el inicio de sesión con usuario o correo
     */
    public String validar(String usuarioOCorreo, String clave) {
        String u = usuarioOCorreo.toLowerCase();
        if (u.equals(USUARIO_ADMIN)) {
            return clave.equals(CLAVE_ADMIN) ? USUARIO_ADMIN : null;
        }
        if (u.contains("@")) {
            u = prefs.getString("correo_" + u, "");
        }
        String guardada = prefs.getString("clave_" + u, null);
        if (guardada == null || !guardada.equals(clave)) {
            return null;
        }
        return prefs.getString("nombre_" + u, u);
    }
}
