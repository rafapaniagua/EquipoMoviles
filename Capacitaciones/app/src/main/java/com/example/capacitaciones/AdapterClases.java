package com.example.capacitaciones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Clases;

import java.util.ArrayList;

public class AdapterClases extends RecyclerView.Adapter<AdapterClases.ViewHolderClases> {

    private Context context;
    private ArrayList<Clases> clases_array;
    private String is_admin, id_usuario, nombreCompleto, id_grupo, rol, info_grupo;

    public AdapterClases(Context context, ArrayList<Clases> clases_array, String is_admin, String id_usuario, String nombreCompleto, String id_grupo, String rol, String info_grupo) {
        this.context = context;
        this.clases_array = clases_array;
        this.is_admin = is_admin;
        this.id_usuario = id_usuario;
        this.nombreCompleto = nombreCompleto;
        this.id_grupo = id_grupo;
        this.rol = rol;
        this.info_grupo = info_grupo;
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
        viewHolderClases.asignarDatos(clases_array.get(i));
    }

    @Override
    public int getItemCount() {
        return clases_array.size();
    }

    public class ViewHolderClases extends RecyclerView.ViewHolder {

        TextView fecha, hora, status;
        ImageView ver, editar, eliminar;

        public ViewHolderClases(@NonNull View itemView) {
            super(itemView);

            fecha = (TextView) itemView.findViewById(R.id.tv_list_clase_fecha);
            hora = (TextView) itemView.findViewById(R.id.tv_list_clase_hora);
            status = (TextView) itemView.findViewById(R.id.tv_list_clase_status);

            ver = (ImageView) itemView.findViewById(R.id.iv_list_clase_ver);
            editar = (ImageView) itemView.findViewById(R.id.iv_list_clase_editar);
            eliminar = (ImageView) itemView.findViewById(R.id.iv_list_clase_eliminar);
        }

        public void asignarDatos(final Clases clase) {
            fecha.setText(clase.getFecha());
            hora.setText(clase.getHora());
            status.setText(clase.getStatus());

            //EVENTO PARA BOTÓN DE VER DETALLE DE CLASE
            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetalleClase.class);

                    intent.putExtra("is_admin", is_admin);
                    intent.putExtra("id_usuario", id_usuario);
                    intent.putExtra("nombreCompleto", nombreCompleto);
                    intent.putExtra("id_grupo", id_grupo);
                    intent.putExtra("rol", rol);
                    intent.putExtra("info_grupo", info_grupo);
                    intent.putExtra("id_clase", String.valueOf(clase.getId_clase()));

                    v.getContext().startActivity(intent);

                    //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                    //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                    ((Activity) v.getContext()).finish();

                    Toast.makeText(((Activity) v.getContext()), "IR A DETALLE DE CLASE", Toast.LENGTH_SHORT).show();
                }
            });

            //EVENTO PARA BOTÓN DE EDITAR CLASE
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SI ES ADMINISTRADOR O EL DOCENTE DEL GRUPO SE PERMITE EDITAR
                    if(is_admin.equals("true") || rol.equals("Docente")) {
                        Intent intent = new Intent(v.getContext(), AddClase.class);

                        intent.putExtra("is_admin", is_admin);
                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("nombreCompleto", nombreCompleto);
                        intent.putExtra("id_grupo", id_grupo);
                        intent.putExtra("rol", rol);
                        intent.putExtra("info_grupo", info_grupo);
                        intent.putExtra("id_clase", String.valueOf(clase.getId_clase()));

                        v.getContext().startActivity(intent);

                        //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                        //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                        ((Activity) v.getContext()).finish();

                        //SI NO ES ADMINISTRADOR NI ES EL DOCENTE DEL GRUPO SE DENIEGA EL ACCESO
                    }else{
                        Toast.makeText(((Activity) v.getContext()), "No tiene permisos para editar la clase", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //EVENTO PARA BOTÓN DE ELIMINAR CLASE
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SI ES ADMINISTRADOR O EL DOCENTE DEL GRUPO SE PERMITE ELIMINAR
                    if(is_admin.equals("true") || rol.equals("Docente")) {
                        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(((Activity) v.getContext()), "rhinoSystems", null, 1);

                        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                        BaseDeDatos.delete("clases", "id_clase="+clase.getId_clase(), null);
                        BaseDeDatos.close();

                        Toast.makeText(((Activity) v.getContext()), "Clase eliminada exitosamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(v.getContext(), ListClase.class);

                        intent.putExtra("is_admin", is_admin);
                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("nombreCompleto", nombreCompleto);
                        intent.putExtra("id_grupo", id_grupo);
                        intent.putExtra("rol", rol);
                        intent.putExtra("info_grupo", info_grupo);

                        v.getContext().startActivity(intent);

                        //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                        //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                        ((Activity) v.getContext()).finish();

                        //SI NO ES ADMINISTRADOR NI ES EL DOCENTE DEL GRUPO SE DENIEGA EL ACCESO
                    }else{
                        Toast.makeText(((Activity) v.getContext()), "No tiene permisos para eliminar la clase", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
