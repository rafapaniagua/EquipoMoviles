package com.example.capacitaciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;

public class DetalleGrupo extends AppCompatActivity {

    TextView tv_nombre_usuario, tv_titulo, tv_info_detalle_grupo_docente, tv_info_detalle_grupo_status, tv_info_detalle_grupo_descripcion_curso;
    Button btn_inscribirme, btn_clases, btn_regresar;
    String is_admin, id_usuario, id_grupo, nombreCompleto, rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_grupo);

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        //RELACIONAMOS LOS ELEMENTOS DE LA VISTA CON SUS VARIABLES
        tv_titulo = (TextView) findViewById(R.id.tv_detalle_grupo_nombre);
        tv_info_detalle_grupo_docente = (TextView) findViewById(R.id.tv_info_detalle_grupo_docente);
        tv_info_detalle_grupo_status = (TextView) findViewById(R.id.tv_info_detalle_grupo_status);
        tv_info_detalle_grupo_descripcion_curso = (TextView) findViewById(R.id.tv_info_detalle_grupo_descripcion_curso);
        btn_inscribirme = (Button) findViewById(R.id.btn_inscribirme);
        btn_clases = (Button) findViewById(R.id.btn_clases);

        //CARGAMOS LOS DETALLES DEL GRUPO EN LA PANTALLA
        cargarDatos();

        //COMPROBAMOS SI EL USUARIO YA ESTÁ INSCRITO EN EL GRUPO
        comprobarInscripcion();
        if(rol.equals("Docente")){
            btn_inscribirme.setText("Docente");
        }else if(rol.equals("Alumno")){
            btn_inscribirme.setText("Ya Inscrito");
        }else{
            btn_inscribirme.setText("Inscribirme");
        }

        btn_clases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irClases();
            }
        });

        btn_inscribirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rol.equals("Docente")){
                    Toast.makeText(getApplicationContext(), "Eres Docente de este Grupo", Toast.LENGTH_SHORT).show();
                }else if(rol.equals("Alumno")){
                    Toast.makeText(getApplicationContext(), "Ya estás inscrito en este Grupo Inscrito", Toast.LENGTH_SHORT).show();
                }else{
                    //SI NO ES DOCENTE NI ALUMNO DEL GRUPO, SE PUEDE INSCRIBIR COMO ALUMNO
                    inscribir();
                }
            }
        });
    }

    private void cargarDatos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select cur.nombre, gru.clave, usu.nombres, usu.apellidos, gru.status, cur.descripcion from grupos as gru " +
                                                "join cursos as cur on gru.id_curso = cur.id_curso " +
                                                "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                "where gru.id_grupo = "+id_grupo+" and ro.rol = 'Docente';", null);

        if(fila.moveToFirst()) {
            //ASIGNAMOS LA INFORMACIÓN CONSULTADA EN LOS TEXTVIEW
            tv_titulo.setText(fila.getString(0)+" ("+fila.getString(1)+")");
            tv_info_detalle_grupo_docente.setText(fila.getString(2)+" "+fila.getString(3));
            tv_info_detalle_grupo_status.setText(fila.getString(4));
            tv_info_detalle_grupo_descripcion_curso.setText(fila.getString(5));
        }
    }

    private void comprobarInscripcion(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select rol from rol " +
                                                "where id_grupo = "+id_grupo+" and id_usuario = "+id_usuario+";", null);

        if(fila.moveToFirst()){
            rol = fila.getString(0);
        }else{
            rol = "";
        }
    }

    private void inscribir(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String rol = "Alumno";

        ContentValues registro = new ContentValues();

        registro.put("rol", rol);
        registro.put("id_grupo", Integer.parseInt(id_grupo));
        registro.put("id_usuario", Integer.parseInt(id_usuario));

        //INSERTAMOS EL REGISTRO DEL ROL
        BaseDeDatos.insert("rol", null, registro);

        BaseDeDatos.close();

        Toast.makeText(this, "Inscrito exitosamente en el Grupo", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DetalleGrupo.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", this.id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        intent.putExtra("id_grupo", id_grupo);
        startActivity(intent);

        this.finish();
    }

    private void irClases(){
        Intent intent = new Intent(this, ListClase.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", this.id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        intent.putExtra("id_grupo", id_grupo);
        startActivity(intent);

        this.finish();
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
}
