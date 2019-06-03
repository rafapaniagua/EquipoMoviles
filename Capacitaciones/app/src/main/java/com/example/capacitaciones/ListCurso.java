package com.example.capacitaciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Cursos;
import com.example.capacitaciones.Modelos.Grupos;

import java.util.ArrayList;

public class ListCurso extends AppCompatActivity {

    private ArrayList<Cursos> cursos_array;
    private RecyclerView rv_cursos;
    private String is_admin, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_curso);

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");

        cursos_array = new ArrayList<Cursos>();

        mostrarCursos();

        rv_cursos = (RecyclerView) findViewById(R.id.rv_cursos);
        AdapterCursos adapter = new AdapterCursos(this, cursos_array);
        rv_cursos.setAdapter(adapter);
        rv_cursos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void mostrarCursos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select nombre, duracion, id_curso from cursos;", null);

        if(fila.moveToFirst()){
            Cursos c = new Cursos();

            c.setNombre(fila.getString(0));
            c.setDuracion(fila.getString(1));
            c.setId_curso(fila.getInt(2));

            cursos_array.add(c);

            while(fila.moveToNext()){
                c = new Cursos();

                c.setNombre(fila.getString(0));
                c.setDuracion(fila.getString(1));
                c.setId_curso(fila.getInt(2));
                cursos_array.add(c);
            }
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, pero no hay Cursos actualmente", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(this, Inicio.class);
        i.putExtra("is_admin", is_admin);
        i.putExtra("id_usuario", id_usuario);
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
            Intent intent = new Intent(this, ListCurso.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
            startActivity(intent);

            this.finish();
    }

    public void irUsuarios(View v){
        Intent intent = new Intent(this, ListUsuario.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();

            Toast.makeText(this, "Ir a Menú Usuarios", Toast.LENGTH_SHORT).show();
    }

    public void irNotificaciones(View v){
        /*Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }

    public void newCurso(View v){
        Intent intent = new Intent(this, AddCurso.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();
    }
}
