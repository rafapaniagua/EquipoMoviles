package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterGrupos extends RecyclerView.Adapter<AdapterGrupos.ViewHolderGrupos> {

    private Context context;
    private ArrayList<String> nombres;

    public AdapterGrupos(Context context, ArrayList<String> nombres) {
        this.context = context;
        this.nombres = nombres;
    }

    @NonNull
    @Override
    public AdapterGrupos.ViewHolderGrupos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_grupos,
                viewGroup,
                false);
        return new ViewHolderGrupos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrupos.ViewHolderGrupos viewHolderGrupos, int i) {
        viewHolderGrupos.asignarDatos(nombres.get(i));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ViewHolderGrupos extends RecyclerView.ViewHolder {

        TextView clave, curso, docente, integrantes;

        public ViewHolderGrupos(@NonNull View itemView) {
            super(itemView);

            clave = (TextView) itemView.findViewById(R.id.tv_list_grupo_clave);
            curso = (TextView) itemView.findViewById(R.id.tv_list_grupo_curso);
            docente = (TextView) itemView.findViewById(R.id.tv_list_grupo_docente);
            integrantes = (TextView) itemView.findViewById(R.id.tv_list_grupo_integrantes);
        }

        public void asignarDatos(String dato) {
            clave.setText(dato);
        }
    }
}
