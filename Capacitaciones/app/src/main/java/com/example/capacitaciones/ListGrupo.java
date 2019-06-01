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

import java.util.ArrayList;

public class ListGrupo extends AppCompatActivity {

    ArrayList<Grupos> grupos_array;
    RecyclerView rv_grupos;
    TextView tv_nombre_usuario;
    String is_admin, id_usuario, nombreCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grupo);

        //INICIALIZAMOS LOS ARREGLOS
        grupos_array = new ArrayList<Grupos>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        mostrarGrupos();

        rv_grupos = (RecyclerView) findViewById(R.id.rv_grupos);
        AdapterGrupos adapter = new AdapterGrupos(this, grupos_array, is_admin, id_usuario, nombreCompleto);
        rv_grupos.setAdapter(adapter);
        rv_grupos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario, Toast.LENGTH_SHORT).show();
    }

    private void mostrarGrupos(){
        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select gru.id_grupo, gru.clave, cur.nombre, usu.nombres, usu.apellidos, gru.no_integrantes from grupos as gru " +
                                                "join cursos as cur on gru.id_curso = cur.id_curso " +
                                                "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                "where ro.rol = 'Docente';", null);

        if(fila.getCount() > 0){
            while(fila.moveToNext()){
                Grupos g = new Grupos();

                g.setId_grupo(fila.getInt(0));
                g.setClave(fila.getString(1));
                g.setNombre_curso(fila.getString(2));
                g.setDocente(fila.getString(3)+" "+fila.getString(4));
                g.setNo_integrantes(fila.getInt(5));

                grupos_array.add(g);
            }
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, no tienes grupos disponibles", Toast.LENGTH_SHORT).show();
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
            /*Intent intent = new Intent(this, ListUsuario.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);
            startActivity(intent);

            this.finish();*/

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

    public void irNuevoGrupo(View v){
        //SI ES ADMINISTRADOR SE PERMITE CREAR NUEVO GRUPO
        if(is_admin.equals("true")) {
            Intent intent = new Intent(v.getContext(), AddGrupo.class);

            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);

            startActivity(intent);

            this.finish();

            //SI NO ES ADMINISTRADOR SE DENIEGA EL ACCESO
        }else{
            Toast.makeText(this, "No tiene permisos para agregar un nuevo grupo", Toast.LENGTH_SHORT).show();
        }
    }
}
