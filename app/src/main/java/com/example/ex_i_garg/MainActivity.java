package com.example.ex_i_garg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
// inicio de sesion
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USUARIO_NUEVO = "usuario_nuevo";

    private CuentaRepositorio cuentas;
    private TextInputLayout tilUsuario;
    private TextInputLayout tilClave;
    private TextInputEditText etUsuario;
    private TextInputEditText etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Pantalla.configurar(this, findViewById(R.id.main), findViewById(R.id.header));

        tilUsuario = findViewById(R.id.tilUsuario);
        tilClave = findViewById(R.id.tilClave);
        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        cuentas = new CuentaRepositorio(this);
        rellenarUsuarioNuevo(getIntent());
        MaterialButton btnIniciar = findViewById(R.id.btnIniciar);
        MaterialButton btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnIniciar.setOnClickListener(v -> validarLogin());
        btnCrearCuenta.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
        findViewById(R.id.tvOlvide).setOnClickListener(v ->
                Toast.makeText(this, "Funcion no disponible", Toast.LENGTH_SHORT).show());
    }

    private void validarLogin() {
        String usuario = texto(etUsuario);
        String clave = texto(etClave);
        tilUsuario.setError(null);
        tilClave.setError(null);

        if (usuario.isEmpty()) {
            tilUsuario.setError("Ingresa tu usuario");
            return;
        }
        if (clave.isEmpty()) {
            tilClave.setError("Ingresa tu contraseña");
            return;
        }
        String nombre = cuentas.validar(usuario, clave);
        if (nombre == null) {
            tilClave.setError("Usuario o contraseña incorrectos");
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "¡Bienvenido, " + nombre + "!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UsuariosActivity.class);
        intent.putExtra(UsuariosActivity.EXTRA_SESION, nombre);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        rellenarUsuarioNuevo(intent);
    }
    private void rellenarUsuarioNuevo(Intent intent) {
        String nuevo = intent.getStringExtra(EXTRA_USUARIO_NUEVO);
        if (nuevo != null) {
            etUsuario.setText(nuevo);
            etClave.setText("");
            etClave.requestFocus();
        }
    }

    private static String texto(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }
}
