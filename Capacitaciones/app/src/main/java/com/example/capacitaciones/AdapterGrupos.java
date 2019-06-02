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
import com.example.capacitaciones.Modelos.Grupos;

import java.util.ArrayList;

public class AdapterGrupos extends RecyclerView.Adapter<AdapterGrupos.ViewHolderGrupos> {

    private Context context;
    private ArrayList<Grupos> grupos_array;
    private String is_admin, id_usuario, nombreCompleto;

    public AdapterGrupos(Context context, ArrayList<Grupos> grupos_array, String is_admin, String id_usuario, String nombreCompleto) {
        this.context = context;
        this.grupos_array= grupos_array;
        this.is_admin = is_admin;
        this.id_usuario = id_usuario;
        this.nombreCompleto = nombreCompleto;
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
        viewHolderGrupos.asignarDatos(grupos_array.get(i));
    }

    @Override
    public int getItemCount() {
        return grupos_array.size();
    }

    public class ViewHolderGrupos extends RecyclerView.ViewHolder {

        TextView clave, curso, docente, integrantes;
        ImageView ver, editar, eliminar;


        public ViewHolderGrupos(@NonNull View itemView) {
            super(itemView);

            clave = (TextView) itemView.findViewById(R.id.tv_list_grupo_clave);
            curso = (TextView) itemView.findViewById(R.id.tv_list_grupo_curso);
            docente = (TextView) itemView.findViewById(R.id.tv_list_grupo_docente);
            integrantes = (TextView) itemView.findViewById(R.id.tv_list_grupo_integrantes);

            ver = (ImageView) itemView.findViewById(R.id.iv_list_grupo_ver);
            editar = (ImageView) itemView.findViewById(R.id.iv_list_grupo_editar);
            eliminar = (ImageView) itemView.findViewById(R.id.iv_list_grupo_eliminar);
        }

        public void asignarDatos(final Grupos grupo) {
            clave.setText(grupo.getClave());
            curso.setText(grupo.getNombre_curso());
            docente.setText(grupo.getDocente());
            integrantes.setText(String.valueOf(grupo.getNo_integrantes()));

            //EVENTO PARA BOTÓN DE VER DETALLE DE GRUPO
            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetalleGrupo.class);

                    intent.putExtra("is_admin", is_admin);
                    intent.putExtra("id_usuario", id_usuario);
                    intent.putExtra("nombreCompleto", nombreCompleto);
                    intent.putExtra("id_grupo", String.valueOf(grupo.getId_grupo()));

                    v.getContext().startActivity(intent);

                    //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                    //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                    ((Activity) v.getContext()).finish();
                }
            });

            //EVENTO PARA BOTÓN DE EDITAR GRUPO
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SI ES ADMINISTRADOR SE PERMITE EDITAR
                    if(is_admin.equals("true")) {
                        Intent intent = new Intent(v.getContext(), AddGrupo.class);

                        intent.putExtra("is_admin", is_admin);
                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("nombreCompleto", nombreCompleto);
                        intent.putExtra("id_grupo", String.valueOf(grupo.getId_grupo()));

                        v.getContext().startActivity(intent);

                        //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                        //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                        ((Activity) v.getContext()).finish();

                    //SI NO ES ADMINISTRADOR SE DENIEGA EL ACCESO
                    }else{
                        Toast.makeText(((Activity) v.getContext()), "No tiene permisos para editar el grupo", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //EVENTO PARA BOTÓN DE ELIMINAR GRUPO
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SI ES ADMINISTRADOR SE PERMITE ELIMINAR
                    if(is_admin.equals("true")) {
                        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(((Activity) v.getContext()), "rhinoSystems", null, 1);

                        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                        BaseDeDatos.delete("grupos", "id_grupo="+grupo.getId_grupo(), null);
                        BaseDeDatos.close();

                        Toast.makeText(((Activity) v.getContext()), "Grupo eliminado exitosamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(v.getContext(), ListGrupo.class);

                        intent.putExtra("is_admin", is_admin);
                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("nombreCompleto", nombreCompleto);

                        v.getContext().startActivity(intent);

                        //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                        //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                        ((Activity) v.getContext()).finish();

                        //SI NO ES ADMINISTRADOR SE DENIEGA EL ACCESO
                    }else{
                        Toast.makeText(((Activity) v.getContext()), "No tiene permisos para eliminar el grupo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
