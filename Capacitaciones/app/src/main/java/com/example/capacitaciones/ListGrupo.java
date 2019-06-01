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
import com.example.capacitaciones.Modelos.Grupos;

import java.util.ArrayList;
import java.util.List;

public class ListGrupo extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_grupos;
    String is_admin, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grupo);

        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);

        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor filas = BaseDeDatos.rawQuery("select gru.clave, cur.nombre, usu.nombres, usu.apellidos, gru.no_integrantes from grupos as gru " +
                                                "join cursos as cur on gru.id_curso = cur.id_curso " +
                                                "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                "where ro.rol = 'Docente';", null);

        String test = "";

        if(filas.getCount() > 0){
            while(filas.moveToNext()){


                /*grupo.setId_grupo(filas.getInt(0));
                grupo.setId_curso();*/

                test += filas.getString(0)+", ";
                test += filas.getString(1)+", ";
                test += filas.getString(2)+" "+filas.getString(3)+", ";
                test += filas.getInt(4)+"\n";

            }
        }

        Toast.makeText(this, test, Toast.LENGTH_LONG).show();

        nombres = new ArrayList<String>();

        nombres.add("Pablo");
        nombres.add("Juan");
        nombres.add("Juana");
        nombres.add("Cinthia");
        nombres.add("Lizbeth");
        nombres.add("Rafael");
        nombres.add("Judith");
        nombres.add("Elizabeth");
        nombres.add("Murillo");

        rv_grupos = (RecyclerView) findViewById(R.id.rv_grupos);
        AdapterGrupos adapter = new AdapterGrupos(this, nombres);
        rv_grupos.setAdapter(adapter);
        rv_grupos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");

        //Toast.makeText(this, "Admin?: "+is_admin, Toast.LENGTH_SHORT).show();
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
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }
}
