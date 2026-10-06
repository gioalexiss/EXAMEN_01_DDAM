package com.example.ex_i_garg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** pantalla de registro de una nueva cuenta */
public class RegistroActivity extends AppCompatActivity {

    private TextInputLayout tilNombre, tilCorreo, tilUsuario, tilClave, tilConfirmar;
    private TextInputEditText etNombre, etCorreo, etUsuario, etClave, etConfirmar;
    private CuentaRepositorio cuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        Pantalla.configurar(this, findViewById(R.id.main), toolbar);

        tilNombre = findViewById(R.id.tilNombre);
        tilCorreo = findViewById(R.id.tilCorreo);
        tilUsuario = findViewById(R.id.tilUsuario);
        tilClave = findViewById(R.id.tilClave);
        tilConfirmar = findViewById(R.id.tilConfirmar);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        etConfirmar = findViewById(R.id.etConfirmar);
        cuentas = new CuentaRepositorio(this);

        toolbar.setNavigationOnClickListener(v -> irAlLogin());
        findViewById(R.id.tvYaTengoCuenta).setOnClickListener(v -> irAlLogin());
        findViewById(R.id.btnRegistrar).setOnClickListener(v -> validarRegistro());
    }

    private void validarRegistro() {
        tilNombre.setError(null);
        tilCorreo.setError(null);
        tilUsuario.setError(null);
        tilClave.setError(null);
        tilConfirmar.setError(null);

        String nombre = texto(etNombre);
        String correo = texto(etCorreo);
        String usuario = texto(etUsuario);
        String clave = texto(etClave);
        String confirmar = texto(etConfirmar);

        boolean valido = true;
        if (nombre.length() < 3) {
            tilNombre.setError("El nombre debe tener mínimo 3 caracteres");
            valido = false;
        }
        if (correo.isEmpty()) {
            tilCorreo.setError("Ingresa tu correo");
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError("Ingresa un correo válido");
            valido = false;
        } else if (cuentas.existeCorreo(correo)) {
            tilCorreo.setError("Este correo ya está registrado");
            valido = false;
        }
        if (usuario.isEmpty()) {
            tilUsuario.setError("Ingresa un usuario");
            valido = false;
        } else if (usuario.contains(" ") || usuario.contains("@")) {
            tilUsuario.setError("El usuario no puede tener espacios ni @");
            valido = false;
        } else if (cuentas.existeUsuario(usuario)) {
            tilUsuario.setError("Este usuario ya existe");
            valido = false;
        }
        if (clave.isEmpty()) {
            tilClave.setError("Ingresa una contraseña");
            valido = false;
        }
        if (!clave.equals(confirmar)) {
            tilConfirmar.setError("Las contraseñas no coinciden");
            valido = false;
        }
        if (!valido) {
            return;
        }

        cuentas.guardar(nombre, correo, usuario, clave);
        Toast.makeText(this, "Cuenta creada. Inicia sesión", Toast.LENGTH_SHORT).show();
        irAlLogin(usuario);
    }

    private void irAlLogin() {
        irAlLogin(null);
    }

    private void irAlLogin(String usuarioNuevo) {
        Intent intent = new Intent(this, MainActivity.class);
        if (usuarioNuevo != null) {
            intent.putExtra(MainActivity.EXTRA_USUARIO_NUEVO, usuarioNuevo);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private static String texto(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }
}
