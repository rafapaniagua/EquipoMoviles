package com.example.capacitaciones;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Notificaciones;

import java.util.ArrayList;

public class AdapterNotificacion extends RecyclerView.Adapter<AdapterNotificacion.ViewHolderNotificacion> {
    private Context context;
    private ArrayList<Notificaciones> notificaciones_array;
    private String is_admin, id_usuario, nombreCompleto;

    public AdapterNotificacion(Context context, ArrayList<Notificaciones> notificaciones_array,String is_admin, String id_usuario, String nombreCompleto) {
        this.context = context;
        this.notificaciones_array = notificaciones_array;
        this.is_admin = is_admin;
        this.id_usuario = id_usuario;
        this.nombreCompleto = nombreCompleto;
    }

    @NonNull
    @Override
    public ViewHolderNotificacion onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_notificacion,
                viewGroup,
                false);
        return new ViewHolderNotificacion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotificacion viewHolderNotificacion, int i) {
        viewHolderNotificacion.asignarDatos(notificaciones_array.get(i));
    }

    @Override
    public int getItemCount() {
        return notificaciones_array.size();
    }

    public class ViewHolderNotificacion extends RecyclerView.ViewHolder {

        TextView tv_notificacion_fecha,tv_notificacion_descripcion;
        ImageView iv_ver;

        public ViewHolderNotificacion(@NonNull View itemView) {
            super(itemView);

            tv_notificacion_fecha = (TextView) itemView.findViewById(R.id.tv_notificacion_fecha);
            tv_notificacion_descripcion = (TextView) itemView.findViewById(R.id.tv_notificacion_descripcion);
            iv_ver = (ImageView) itemView.findViewById(R.id.iv_ver);
        }

        public void asignarDatos(final Notificaciones notificacion) {
            tv_notificacion_fecha.setText(notificacion.getFecha_hora());
            tv_notificacion_descripcion.setText(notificacion.getDescripcion());

            iv_ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(context, "rhinoSystems", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                    ContentValues registro = new ContentValues();
                    registro.put("status","Leida");
                    int a = BaseDeDatos.update("notificaciones", registro, "id_notificacion = " + notificacion.getId_notificacion(),null);
                    BaseDeDatos.close();
                    if(a == 1 ){
                        Toast.makeText(context, "Notificacion leida", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                    }
                    BaseDeDatos.close();
                    Intent intent = new Intent(v.getContext(), ListNotificaciones.class);
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
