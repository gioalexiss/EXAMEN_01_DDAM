package com.example.ex_i_garg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/** listado de 20 usuarios */
public class UsuariosActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_SESION = "sesion";

    private UsuarioAdapter adapter;
    private TextView tvTotal;
    private View tvSinResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        Pantalla.configurar(this, findViewById(R.id.main), toolbar);

        List<Usuario> usuarios = crearUsuarios();
        String sesion = getIntent().getStringExtra(EXTRA_SESION);
        toolbar.setSubtitle("Sesión: " + (sesion != null ? sesion : CuentaRepositorio.USUARIO_ADMIN));
        tvTotal = findViewById(R.id.tvTotal);
        tvSinResultados = findViewById(R.id.tvSinResultados);

        adapter = new UsuarioAdapter(this, usuarios);
        ListView lista = findViewById(R.id.lvUsuarios);
        lista.setAdapter(adapter);
        actualizarTotal();
        configurarBusqueda(toolbar);
        configurarNavegacion();

        lista.setOnItemClickListener((parent, view, position, id) -> {
            Usuario u = adapter.getItem(position);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(EXTRA_NOMBRE, u.getNombre());
            intent.putExtra(EXTRA_COLOR, u.getColor());
            startActivity(intent);
        });
    }

    private void configurarBusqueda(MaterialToolbar toolbar) {
        MenuItem buscar = toolbar.getMenu().findItem(R.id.accion_buscar);
        SearchView searchView = (SearchView) buscar.getActionView();
        searchView.setQueryHint("Buscar por nombre o usuario");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String texto) {
                adapter.filtrar(texto);
                actualizarTotal();
                return true;
            }
        });
    }

    private void actualizarTotal() {
        int total = adapter.getCount();
        tvTotal.setText(total == 1 ? "1 contacto" : total + " contactos");
        tvSinResultados.setVisibility(total == 0 ? View.VISIBLE : View.GONE);
    }

    private void configurarNavegacion() {
        BottomNavigationView nav = findViewById(R.id.navInferior);
        nav.setSelectedItemId(R.id.nav_usuarios);
        // Chat y Perfil solo son de diseño: al tocarlos no pasa nada
        nav.setOnItemSelectedListener(item -> item.getItemId() == R.id.nav_usuarios);
    }

    private List<Usuario> crearUsuarios() {
        String[][] datos = {
                {"Emiliano Chairez", "emi123"},
                {"Sol Fernandez", "solf"},
                {"Job", "job_22"},
                {"Lamine Yamal", "yamal99"},
                {"Lionel Messi", "messi_l"},
                {"Diego Yael", "diegoy"},
                {"Esau Flores", "esau.flr"},
                {"Luis Carrillo", "luisc"},
                {"Susana Buendia", "susi_fl"},
                {"Andres Ventura", "andres_v"},
                {"Uriel Villegas", "uri.vil"},
                {"Fermin", "fermin_v"},
                {"Daniela Marquez", "dani_o"},
                {"Jordy Alejandro", "jordy.x"},
                {"Paola Iza", "pao_iza"},
                {"Emilio Flores", "emi.flr"},
                {"Leo Garcia", "leo_gar"},
                {"Héctor Acuña", "hector.acuña"},
                {"Pablo Gerardo", "pablo_g"},
                {"Federico Sanchez", "fedesan"},
        };
        int[] colores = {
                Color.parseColor("#7B1E3A"), Color.parseColor("#A33A5B"),
                Color.parseColor("#C0577A"), Color.parseColor("#5A1229"),
                Color.parseColor("#8E4B5E"), Color.parseColor("#B5654A"),
                Color.parseColor("#6B4E71"),
        };

        List<Usuario> lista = new ArrayList<>();
        for (int i = 0; i < datos.length; i++) {
            lista.add(new Usuario(datos[i][0], datos[i][1], colores[i % colores.length], i % 3 != 2));
        }
        return lista;
    }
}
