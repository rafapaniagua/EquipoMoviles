package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClases extends RecyclerView.Adapter<AdapterClases.ViewHolderClases> {

    private Context context;
    private ArrayList<String> nombres;

    public AdapterClases(Context context, ArrayList<String> nombres) {
        this.context = context;
        this.nombres = nombres;
    }

    @NonNull
    @Override
    public AdapterClases.ViewHolderClases onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_clases,
                viewGroup,
                false);
        return new ViewHolderClases(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClases.ViewHolderClases viewHolderClases, int i) {
        viewHolderClases.asignarDatos(nombres.get(i));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ViewHolderClases extends RecyclerView.ViewHolder {

        TextView fecha, hora, status;

        public ViewHolderClases(@NonNull View itemView) {
            super(itemView);

            fecha = (TextView) itemView.findViewById(R.id.tv_list_clase_fecha);
            hora = (TextView) itemView.findViewById(R.id.tv_list_clase_hora);
            status = (TextView) itemView.findViewById(R.id.tv_list_clase_status);
        }

        public void asignarDatos(String dato) {
            fecha.setText(dato);
        }
    }
}
