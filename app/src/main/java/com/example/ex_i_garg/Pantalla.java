package com.example.ex_i_garg;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public final class Pantalla {

    private Pantalla() {
    }
    public static void configurar(ComponentActivity activity, View raiz, View superior) {
        EdgeToEdge.enable(activity,
                SystemBarStyle.dark(Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));

        final int superiorTop = superior.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(raiz, (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets teclado = insets.getInsets(WindowInsetsCompat.Type.ime());
            superior.setPadding(superior.getPaddingLeft(), superiorTop + barras.top,
                    superior.getPaddingRight(), superior.getPaddingBottom());
            v.setPadding(barras.left, 0, barras.right, Math.max(barras.bottom, teclado.bottom));
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
