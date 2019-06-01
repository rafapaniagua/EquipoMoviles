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
import com.example.capacitaciones.Modelos.Clases;
import com.example.capacitaciones.Modelos.Grupos;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    private ArrayList<Clases> clases_array;
    private ArrayList<Grupos> grupos_array;
    private RecyclerView rv_grupos, rv_clases;
    private String is_admin, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //INICIALIZAMOS LOS ARREGLOS
        clases_array = new ArrayList<Clases>();
        grupos_array = new ArrayList<Grupos>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");

        mostrarClases();
        mostrarGrupos();

        rv_clases = (RecyclerView) findViewById(R.id.rv_inicio_clases);
        AdapterInicioClases adapter1 = new AdapterInicioClases(this, clases_array);
        rv_clases.setAdapter(adapter1);
        rv_clases.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rv_grupos = (RecyclerView) findViewById(R.id.rv_inicio_grupos);
        AdapterInicioGrupos adapter = new AdapterInicioGrupos(this, grupos_array);
        rv_grupos.setAdapter(adapter);
        rv_grupos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void mostrarClases(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("" +
                        "select clases.fecha, clases.hora, clases.status, cursos.nombre from clases\n" +
                        "join confirmacion on confirmacion.id_clase = clases.id_clase\n" +
                        "join grupos on clases.id_grupo = grupos.id_grupo\n" +
                        "join cursos on cursos.id_curso = grupos.id_curso\n" +
                        "where confirmacion.id_usuario = "+id_usuario+";"
                , null);

        if(fila.moveToFirst()){

            Clases c = new Clases();

            c.setFecha(fila.getString(0));
            c.setHora(fila.getString(1));
            c.setStatus(fila.getString(2));
            c.setNombre_curso(fila.getString(3));

            clases_array.add(c);

            while(fila.moveToNext()){
                c = new Clases();
                c.setFecha(fila.getString(0));
                c.setHora(fila.getString(1));
                c.setStatus(fila.getString(2));
                c.setNombre_curso(fila.getString(3));

                clases_array.add(c);
            }
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, no tienes clases disponibles", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }

    private void mostrarGrupos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select grupos.clave, cursos.nombre, grupos.status, usuarios.nombres from grupos\n" +
                        "join cursos on cursos.id_curso = grupos.id_curso\n" +
                        "join rol on rol.id_grupo = grupos.id_grupo\n" +
                        "join usuarios on usuarios.id_usuario = rol.id_usuario\n" +
                        "where grupos.id_grupo = (select grupos.id_grupo from grupos\n" +
                        "join rol on rol.id_grupo = grupos.id_grupo\n" +
                        "join usuarios on usuarios.id_usuario = rol.id_usuario\n" +
                        "where usuarios.id_usuario = "+id_usuario+" AND rol.rol = 'Alumno') and rol.rol = 'Docente';"
                , null);

        if(fila.moveToFirst()){
            Grupos g = new Grupos();

            g.setClave(fila.getString(0));
            g.setNombre_curso(fila.getString(1));
            g.setStatus(fila.getString(2));
            g.setDocente(fila.getString(3));

            grupos_array.add(g);

            while(fila.moveToNext()){
                g = new Grupos();
                g.setClave(fila.getString(0));
                g.setNombre_curso(fila.getString(1));
                g.setStatus(fila.getString(2));
                g.setDocente(fila.getString(3));

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void irInicio(View v){
        Intent intent = new Intent(this, Inicio.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();
    }

    public void irGrupos(View v){
        Intent intent = new Intent(this, ListGrupo.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();
    }

    public void irCursos(View v){
        if(is_admin.equals("true")) {
            Intent intent = new Intent(this, ListCurso.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            startActivity(intent);

            this.finish();
        }else{
            Toast.makeText(this, "Lo siento, pero no tienes acceso", Toast.LENGTH_SHORT).show();
        }
    }

    public void irUsuarios(View v){
        if(is_admin.equals("true")) {
        /*Intent intent = new Intent(this, ListUsuario.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();*/

            Toast.makeText(this, "Ir a Menú Usuarios", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Lo siento, pero no tienes acceso", Toast.LENGTH_SHORT).show();
        }
    }

    public void irNotificaciones(View v){
        /*Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }
}

