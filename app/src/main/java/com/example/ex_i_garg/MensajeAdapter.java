package com.example.ex_i_garg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Adaptador personalizado del ListView del chat.
 */
public class MensajeAdapter extends BaseAdapter {

    private static final int TIPO_RECIBIDO = 0;
    private static final int TIPO_ENVIADO = 1;

    private final LayoutInflater inflater;
    private final List<Mensaje> mensajes;

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        this.inflater = LayoutInflater.from(context);
        this.mensajes = mensajes;
    }

    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Mensaje getItem(int position) {
        return mensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isEnviado() ? TIPO_ENVIADO : TIPO_RECIBIDO;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            int layout = getItemViewType(position) == TIPO_ENVIADO
                    ? R.layout.item_mensaje_enviado
                    : R.layout.item_mensaje_recibido;
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Mensaje mensaje = getItem(position);
        holder.texto.setText(mensaje.getTexto());
        holder.hora.setText(mensaje.getHora());
        return convertView;
    }

    private static class ViewHolder {
        final TextView texto;
        final TextView hora;

        ViewHolder(View v) {
            texto = v.findViewById(R.id.tvTexto);
            hora = v.findViewById(R.id.tvHora);
        }
    }
}
