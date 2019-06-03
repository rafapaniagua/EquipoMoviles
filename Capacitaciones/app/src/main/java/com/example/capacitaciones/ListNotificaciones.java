package com.example.capacitaciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Notificaciones;
import com.example.capacitaciones.Modelos.Usuarios;

import java.util.ArrayList;

public class ListNotificaciones extends AppCompatActivity {

    ArrayList<Notificaciones> notificaciones_array;
    RecyclerView rv_notificaciones;
    TextView tv_nombre_usuario;
    String is_admin, id_usuario, nombreCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notificaciones);

        notificaciones_array = new ArrayList<Notificaciones>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        mostrarNotificaciones();

        rv_notificaciones = (RecyclerView) findViewById(R.id.rv_notificaciones);
        AdapterNotificacion adapter = new AdapterNotificacion(this, notificaciones_array,is_admin, id_usuario, nombreCompleto);
        rv_notificaciones.setAdapter(adapter);
        rv_notificaciones.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario, Toast.LENGTH_SHORT).show();
    }
    private void mostrarNotificaciones() {
        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_notificacion, fecha_hora_creacion, descripcion, status,id_usuario from notificaciones where status='Pendiente' and id_usuario="+id_usuario, null);

        if (fila.getCount() > 0) {
            while (fila.moveToNext()) {
                Notificaciones n = new Notificaciones();

                n.setId_notificacion(fila.getInt(0));
                n.setFecha_hora(fila.getString(1));
                n.setDescripcion(fila.getString(2));
                n.setEstatus(fila.getString(3));
                n.setId_usuario(fila.getInt(4));

                notificaciones_array.add(n);
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(this, "No tiene notificaciones pendientes", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }
    public void salir(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void irInicio(View v){
        Intent intent = new Intent(this, Inicio.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        startActivity(intent);

        this.finish();
    }

    public void irGrupos(View v){
        Intent intent = new Intent(this, ListGrupo.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        startActivity(intent);

        this.finish();
    }

    public void irCursos(View v){
        if(is_admin.equals("true")) {
            Intent intent = new Intent(this, ListCurso.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);
            startActivity(intent);

            this.finish();
        }else{
            Toast.makeText(this, "No tiene permisos para acceder a este menú", Toast.LENGTH_SHORT).show();
        }
    }

    public void irUsuarios(View v){
        if(is_admin.equals("true")) {
            Intent intent = new Intent(this, ListUsuario.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);
            startActivity(intent);

            this.finish();

            Toast.makeText(this, "Ir a Menú Usuarios", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No tiene permisos para acceder a este menú", Toast.LENGTH_SHORT).show();
        }
    }

    public void irNotificaciones(View v){
        /*Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }
  }

