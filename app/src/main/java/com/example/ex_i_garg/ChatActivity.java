package com.example.ex_i_garg;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** conversacion con 20 mensajes en un ListView  */
public class ChatActivity extends AppCompatActivity {

    private final List<Mensaje> mensajes = new ArrayList<>();
    private MensajeAdapter adapter;
    private EditText etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Pantalla.configurar(this, findViewById(R.id.main), findViewById(R.id.barraChat));

        String nombre = getIntent().getStringExtra(UsuariosActivity.EXTRA_NOMBRE);
        if (nombre == null) {
            nombre = "Contacto";
        }
        int color = getIntent().getIntExtra(UsuariosActivity.EXTRA_COLOR,
                ContextCompat.getColor(this, R.color.guinda_claro));

        TextView tvAvatar = findViewById(R.id.tvAvatar);
        tvAvatar.setText(nombre.substring(0, 1).toUpperCase());
        tvAvatar.setBackgroundTintList(ColorStateList.valueOf(color));
        ((TextView) findViewById(R.id.tvNombreChat)).setText(nombre);
        findViewById(R.id.btnAtras).setOnClickListener(v -> finish());

        cargarConversacion(nombre.split(" ")[0]);
        adapter = new MensajeAdapter(this, mensajes);
        ListView lista = findViewById(R.id.lvMensajes);
        lista.setAdapter(adapter);

        etMensaje = findViewById(R.id.etMensaje);
        findViewById(R.id.btnEnviar).setOnClickListener(v -> enviarMensaje());
    }

    private void enviarMensaje() {
        String texto = etMensaje.getText().toString().trim();
        if (texto.isEmpty()) {
            return;
        }
        String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        mensajes.add(new Mensaje(texto, hora, true));
        adapter.notifyDataSetChanged();
        etMensaje.setText("");
    }

    private void cargarConversacion(String contacto) {
        mensajes.add(new Mensaje("Hola " + contacto + ", ¿cómo estás?", "09:30", true));
        mensajes.add(new Mensaje("¡Hola! Muy bien, gracias. ¿Y tú?", "09:31", false));
        mensajes.add(new Mensaje("Todo bien por acá", "09:31", true));
        mensajes.add(new Mensaje("¿Como te fue ayer?", "09:32", false));
        mensajes.add(new Mensaje("Muy mal, llegue tarde a mi casa", "09:33", true));
        mensajes.add(new Mensaje("y eso?", "09:33", false));
        mensajes.add(new Mensaje("Regrese a mi casa hasta tarde", "09:34", true));
        mensajes.add(new Mensaje("¿Y que hicieron?", "09:35", false));
        mensajes.add(new Mensaje("Era la fiesta de Jordy y acabo tarde", "09:36", true));
        mensajes.add(new Mensaje("Ahh con razon. ¿quieres que nos veamos mas tarde?", "09:37", false));
        mensajes.add(new Mensaje("Va, ¿a qué hora?", "09:38", true));
        mensajes.add(new Mensaje("¿Te parece a las 5?", "09:38", false));
        mensajes.add(new Mensaje("Va a las 5 está bien", "09:39", true));
        mensajes.add(new Mensaje("Te veo en la bice?", "09:40", false));
        mensajes.add(new Mensaje("Sí, ahi cerca de la fuente", "09:40", true));
        mensajes.add(new Mensaje("Va, ahí nos vemos", "09:41", false));
        mensajes.add(new Mensaje("¿Le aviso a los demás?", "09:42", true));
        mensajes.add(new Mensaje("Sí, diles que también vengan", "09:42", false));
        mensajes.add(new Mensaje("Listo, ya les mandé mensaje", "09:44", true));
        mensajes.add(new Mensaje("Arre Nos vemos entonces", "09:45", false));
    }
}
