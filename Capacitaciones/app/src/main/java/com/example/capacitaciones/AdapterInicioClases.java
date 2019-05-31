package com.example.capacitaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capacitaciones.Modelos.Clases;

import java.util.ArrayList;

public class AdapterInicioClases extends RecyclerView.Adapter<AdapterInicioClases.ViewHolderInicioClases> {

private Context context;
    ArrayList<Clases> clases_array;

public AdapterInicioClases(Context context, ArrayList<Clases>  clases_array) {
        this.context = context;
        this.clases_array = clases_array;
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
        viewHolderCursos.asignarDatos(clases_array.get(i));
        }

@Override
public int getItemCount() {
        return clases_array.size();
        }

public class ViewHolderInicioClases extends RecyclerView.ViewHolder {

    TextView nombre, fecha, hora, status;

    public ViewHolderInicioClases(@NonNull View itemView) {
        super(itemView);

        fecha = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_fecha);
        hora = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_hora);
        status = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_status);
        nombre = (TextView) itemView.findViewById(R.id.tv_list_inicio_c_nombre);

    }

    public void asignarDatos(Clases clase) {
        hora.setText(clase.getHora());
        fecha.setText(clase.getFecha());
        status.setText(clase.getStatus());
        nombre.setText(clase.getNombre_curso());
    }
}
}
