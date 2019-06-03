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
import com.example.capacitaciones.Modelos.Usuarios;

import java.util.ArrayList;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolderUsuarios> {
    private Context context;
    private ArrayList<Usuarios> usuarios_array;
    private String is_admin, id_usuario, nombreCompleto;

    public AdapterUsuarios(Context context, ArrayList<Usuarios> usuarios_array,String is_admin, String id_usuario, String nombreCompleto) {
        this.context = context;
        this.usuarios_array = usuarios_array;
        this.is_admin = is_admin;
        this.id_usuario = id_usuario;
        this.nombreCompleto = nombreCompleto;
    }

    @NonNull
    @Override
    public ViewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_usuarios,
                viewGroup,
                false);
        return new ViewHolderUsuarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUsuarios viewHolderUsuarios, int i) {
        viewHolderUsuarios.asignarDatos(usuarios_array.get(i));
    }

    @Override
    public int getItemCount() {
        return usuarios_array.size();
    }

    public class ViewHolderUsuarios extends RecyclerView.ViewHolder {

        TextView tv_usuario_nombre,tv_usuario_apellido,tv_usuario_correos;
        ImageView iv_editar, iv_eliminar;

        public ViewHolderUsuarios(@NonNull View itemView) {
            super(itemView);

            tv_usuario_nombre = (TextView) itemView.findViewById(R.id.tv_usuario_nombre);
            tv_usuario_apellido = (TextView) itemView.findViewById(R.id.tv_usuario_apellido);
            tv_usuario_correos = (TextView) itemView.findViewById(R.id.tv_usuario_correos);
            iv_editar = (ImageView) itemView.findViewById(R.id.iv_editar);
            iv_eliminar = (ImageView) itemView.findViewById(R.id.iv_eliminar);
        }

        public void asignarDatos(final Usuarios usuario) {
            tv_usuario_nombre.setText(usuario.getNombres());
            tv_usuario_apellido.setText(usuario.getApellidos());
            tv_usuario_correos.setText(usuario.getCorreo());

            iv_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddUsuario.class);
                    intent.putExtra("id_usuario_enviado", usuario.getId_usuario());
                    intent.putExtra("control", "edit");
                    context.startActivity(intent);
                }
            });

            iv_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(context, "rhinoSystems", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                    int delete = BaseDeDatos.delete("usuarios", "id_usuario = "+ usuario.getId_usuario(), null);
                    if(delete == 1) {
                        Toast.makeText(context, "Usuario eliminado\nexitosamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
                    }
                    BaseDeDatos.close();
                    Intent intent = new Intent(v.getContext(), ListUsuario.class);
                    intent.putExtra("is_admin", is_admin);
                    intent.putExtra("id_usuario", id_usuario);

                    v.getContext().startActivity(intent);

                    //EL CÓDIGO DE LA LÍNEA DE ABAJO ES EL EQUIVALENTE A this.finish()
                    //PARA CERRAR EL ACTIVITY ACTUAL DESDE EL ADAPTER
                    ((Activity) v.getContext()).finish();
                }
            });

        }
    }
}
