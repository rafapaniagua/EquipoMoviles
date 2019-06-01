package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capacitaciones.Modelos.Grupos;

import java.util.ArrayList;

public class AdapterInicioGrupos extends RecyclerView.Adapter<AdapterInicioGrupos.ViewHolderInicio> {

    private Context context;
    ArrayList<Grupos> grupos_array;

    public AdapterInicioGrupos(Context context, ArrayList<Grupos> grupos_array) {
        this.context = context;
        this.grupos_array = grupos_array;
    }

    @NonNull
    @Override
    public ViewHolderInicio onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_inicio_grupos,
                viewGroup,
                false);
        return new ViewHolderInicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInicio viewHolderCursos, int i) {
        viewHolderCursos.asignarDatos(grupos_array.get(i));
    }

    @Override
    public int getItemCount() {
        return grupos_array.size();
    }

    public class ViewHolderInicio extends RecyclerView.ViewHolder {

        TextView nombre, docente, clave, status;

        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);

            clave = (TextView) itemView.findViewById(R.id.tv_list_inicio_g_clave);
            status = (TextView) itemView.findViewById(R.id.tv_list_inicio_g_status);
            nombre = (TextView) itemView.findViewById(R.id.tv_list_inicio_g_nombre);
            docente = (TextView) itemView.findViewById(R.id.tv_list_inicio_g_responsable);
        }

        public void asignarDatos(Grupos grupo) {
            clave.setText(grupo.getClave());
            status.setText(grupo.getStatus());
            nombre.setText(grupo.getNombre_curso());
            docente.setText(grupo.getDocente());
        }
    }
}
