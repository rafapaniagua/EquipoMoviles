package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterInicio extends RecyclerView.Adapter<AdapterInicio.ViewHolderInicio> {

    private Context context;
    private ArrayList<String> cursos;

    public AdapterInicio(Context context, ArrayList<String> nombres) {
        this.context = context;
        this.cursos = nombres;
    }

    @NonNull
    @Override
    public ViewHolderInicio onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_inicio,
                viewGroup,
                false);
        return new ViewHolderInicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInicio viewHolderCursos, int i) {
        viewHolderCursos.asignarDatos(cursos.get(i));
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public class ViewHolderInicio extends RecyclerView.ViewHolder {

        TextView nombre, responsable;

        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.tv_list_inicio_nombre);
            responsable = (TextView) itemView.findViewById(R.id.tv_list_inicio_responsable);
        }

        public void asignarDatos(String dato) {
            responsable.setText(dato);
        }
    }
}
