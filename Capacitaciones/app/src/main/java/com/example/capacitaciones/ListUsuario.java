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
import com.example.capacitaciones.Modelos.Grupos;
import com.example.capacitaciones.Modelos.Usuarios;

import java.util.ArrayList;

public class ListUsuario extends AppCompatActivity {


    ArrayList<Usuarios> usuarios_array;
    RecyclerView rv_usuarios;
    TextView tv_nombre_usuario;
    String is_admin, id_usuario, nombreCompleto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_usuario);

        usuarios_array = new ArrayList<Usuarios>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        mostrarUsuarios();

        rv_usuarios = (RecyclerView) findViewById(R.id.rv_usuarios);
        AdapterUsuarios adapter = new AdapterUsuarios(this, usuarios_array,is_admin, id_usuario, nombreCompleto);
        rv_usuarios.setAdapter(adapter);
        rv_usuarios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario, Toast.LENGTH_SHORT).show();
    }


    private void mostrarUsuarios() {
        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_usuario, nombres, apellidos, correo from usuarios", null);

        if (fila.getCount() > 0) {
            while (fila.moveToNext()) {
                Usuarios u = new Usuarios();

                u.setId_usuario(fila.getInt(0));
                u.setNombres(fila.getString(1));
                u.setApellidos(fila.getString(2));
                u.setCorreo(fila.getString(3));

                usuarios_array.add(u);
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show();
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

    public void irNuevoUsuario(View v){
        //SI ES ADMINISTRADOR SE PERMITE CREAR NUEVO Usuario
        if(is_admin.equals("true")) {
            Intent intent = new Intent(v.getContext(), AddUsuario.class);

            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);

            startActivity(intent);

            this.finish();

            //SI NO ES ADMINISTRADOR SE DENIEGA EL ACCESO
        }else{
            Toast.makeText(this, "No tiene permisos para agregar un nuevo usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
