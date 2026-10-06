package com.example.ex_i_garg;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/** Adaptador personalizado del ListView de usuarios. */
public class UsuarioAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Usuario> todos;
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioAdapter(Context context, List<Usuario> usuarios) {
        this.inflater = LayoutInflater.from(context);
        this.todos = usuarios;
        this.usuarios.addAll(usuarios);
    }

    /** para buscar */
    public void filtrar(String texto) {
        String buscado = normalizar(texto);
        usuarios.clear();
        for (Usuario u : todos) {
            if (normalizar(u.getNombre()).contains(buscado)
                    || normalizar(u.getUsuario()).contains(buscado)) {
                usuarios.add(u);
            }
        }
        notifyDataSetChanged();
    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Usuario getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_usuario, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Usuario usuario = getItem(position);
        holder.avatar.setText(usuario.getInicial());
        holder.avatar.setBackgroundTintList(ColorStateList.valueOf(usuario.getColor()));
        holder.nombre.setText(usuario.getNombre());
        holder.usuario.setText("@" + usuario.getUsuario());
        holder.enLinea.setVisibility(usuario.isEnLinea() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private static class ViewHolder {
        final TextView avatar;
        final TextView nombre;
        final TextView usuario;
        final View enLinea;

        ViewHolder(View v) {
            avatar = v.findViewById(R.id.tvAvatar);
            nombre = v.findViewById(R.id.tvNombre);
            usuario = v.findViewById(R.id.tvUsuario);
            enLinea = v.findViewById(R.id.vEnLinea);
        }
    }
}
