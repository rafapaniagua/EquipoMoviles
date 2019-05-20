package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterInicioClases extends RecyclerView.Adapter<AdapterInicioClases.ViewHolderInicioClases> {

private Context context;
private ArrayList<String> cursos;

public AdapterInicioClases(Context context, ArrayList<String> nombres) {
        this.context = context;
        this.cursos = nombres;
        }

@NonNull
@Override
public ViewHolderInicioClases onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
        R.layout.list_item_inicio_clases,
        viewGroup,
        false);
        return new ViewHolderInicioClases(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolderInicioClases viewHolderCursos, int i) {
        viewHolderCursos.asignarDatos(cursos.get(i));
        }

@Override
public int getItemCount() {
        return cursos.size();
        }

public class ViewHolderInicioClases extends RecyclerView.ViewHolder {

    TextView nombre, fecha, hora, status;

    public ViewHolderInicioClases(@NonNull View itemView) {
        super(itemView);

        fecha = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_fecha);
        status = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_status);
        nombre = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_nombre);
        hora = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_hora);
    }

    public void asignarDatos(String dato) {
        nombre.setText(dato);
    }
}
}
