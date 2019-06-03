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

import java.util.ArrayList;

public class ListClase extends AppCompatActivity {

    ArrayList<Clases> clases_array;
    RecyclerView rv_clases;
    TextView tv_nombre_usuario, tv_titulo_list_clase;
    String is_admin, id_usuario, id_grupo, nombreCompleto, rol, info_grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clase);

        //INICIALIZAMOS LOS ARREGLOS
        clases_array = new ArrayList<Clases>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");
        rol = this.getIntent().getExtras().getString("rol");
        info_grupo = this.getIntent().getExtras().getString("info_grupo");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        //MOSTRAMOS LA INFORMACIÓN DEL GRUPO DEL QUE SE MANEJARÁN SUS CLASES
        tv_titulo_list_clase = (TextView) findViewById(R.id.tv_titulo_list_clase);
        tv_titulo_list_clase.setText("Clases del Grupo "+info_grupo);

        mostrarClases();

        rv_clases = (RecyclerView) findViewById(R.id.rv_clases);
        AdapterClases adapter = new AdapterClases(this, clases_array, is_admin, id_usuario, nombreCompleto, id_grupo, rol, info_grupo);
        rv_clases.setAdapter(adapter);
        rv_clases.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario+"\nNombre: "+nombreCompleto+"\nGrupo: "+id_grupo, Toast.LENGTH_SHORT).show();
    }

    private void mostrarClases(){
        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_clase, fecha, hora, status from clases " +
                                                "where id_grupo = "+id_grupo+";", null);

        if(fila.getCount() > 0){
            while(fila.moveToNext()){
                Clases c = new Clases();

                c.setId_clase(fila.getInt(0));
                c.setFecha(fila.getString(1));
                c.setHora(fila.getString(2));
                c.setStatus(fila.getString(3));

                clases_array.add(c);
            }
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, no hay clases disponibles en este momento", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        startActivity(intent);

        this.finish();

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }

    public void irNuevaClase(View v){
        //SI ES ADMINISTRADOR O EL DOCENTE DEL GRUPO SE PERMITE CREAR NUEVA CLASE
        if(is_admin.equals("true") || rol.equals("Docente")) {
            Intent intent = new Intent(v.getContext(), AddClase.class);

            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);
            intent.putExtra("id_grupo", id_grupo);
            intent.putExtra("rol", rol);
            intent.putExtra("info_grupo", info_grupo);

            startActivity(intent);

            this.finish();

            //SI NO ES ADMINISTRADOR NI ES EL DOCENTE DEL GRUPO SE DENIEGA EL ACCESO
        }else{
            Toast.makeText(this, "No tiene permisos para agregar una nueva clase", Toast.LENGTH_SHORT).show();
        }
    }

    public void irDetalleGrupo(View v){
        Intent intent = new Intent(v.getContext(), DetalleGrupo.class);

        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        intent.putExtra("id_grupo", id_grupo);

        startActivity(intent);

        this.finish();
    }
}
