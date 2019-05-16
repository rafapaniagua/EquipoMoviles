package com.example.capacitaciones;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterCursos extends RecyclerView.Adapter<AdapterCursos.ViewHolderCursos> {

    private Context context;
    private ArrayList<String> nombres;

    public AdapterCursos(Context context, ArrayList<String> nombres) {
        this.context = context;
        this.nombres = nombres;
    }

    @NonNull
    @Override
    public ViewHolderCursos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_cursos,
                viewGroup,
                false);
        return new ViewHolderCursos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCursos viewHolderCursos, int i) {
        viewHolderCursos.asignarDatos(nombres.get(i));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ViewHolderCursos extends RecyclerView.ViewHolder {

        TextView nombre, duracion;

        public ViewHolderCursos(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.tv_list_curso_nombre);
            duracion = (TextView) itemView.findViewById(R.id.tv_list_curso_duracion);
        }

        public void asignarDatos(String dato) {
            nombre.setText(dato);
        }
    }
}
