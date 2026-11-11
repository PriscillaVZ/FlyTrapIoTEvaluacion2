
package com.example.flytrapiotevaluacion2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrampaAdapter extends RecyclerView.Adapter<TrampaAdapter.TrampaViewHolder> {

    private List<Trampa> listaTrampas;

    // Interfaz para detectar cuando se selecciona una trampa
    public interface OnItemClickListener {
        void onItemClick(Trampa trampa);
    }

    private OnItemClickListener listener;

    public TrampaAdapter(List<Trampa> listaTrampas, OnItemClickListener listener) {
        this.listaTrampas = listaTrampas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrampaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trampa, parent, false);

        return new TrampaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TrampaViewHolder holder, int position) {

        Trampa trampa = listaTrampas.get(position);

        holder.tvCodigo.setText("Código: " + trampa.getCodigoSector());
        holder.tvTipo.setText("Tipo: " + trampa.getTipoTrampa());
        holder.tvNivel.setText("Capturas: " + trampa.getNivelCaptura());
        holder.tvObservaciones.setText("Observaciones: " + trampa.getObservaciones());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(trampa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTrampas.size();
    }

    public static class TrampaViewHolder extends RecyclerView.ViewHolder {

        TextView tvCodigo, tvTipo, tvNivel, tvObservaciones;

        public TrampaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCodigo = itemView.findViewById(R.id.tvItemCodigo);
            tvTipo = itemView.findViewById(R.id.tvItemTipo);
            tvNivel = itemView.findViewById(R.id.tvItemNivel);
            tvObservaciones = itemView.findViewById(R.id.tvItemObservaciones);
        }
    }
}

