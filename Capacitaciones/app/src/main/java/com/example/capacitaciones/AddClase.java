package com.example.capacitaciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddClase extends AppCompatActivity {

    TextView tv_nombre_usuario, tv_titulo;
    Button btn_agregar;
    Spinner sp_clase_status;
    EditText et_fecha, et_hora, et_descripcion;
    String is_admin, id_usuario, id_grupo, nombreCompleto, rol, info_grupo, id_clase;
    ArrayAdapter<String> adapter_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clase);

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");
        rol = this.getIntent().getExtras().getString("rol");
        info_grupo = this.getIntent().getExtras().getString("info_grupo");
        id_clase = this.getIntent().getExtras().getString("id_clase");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        //RELACIONAMOS LOS ELEMENTOS DE LA VISTA CON SUS VARIABLES
        tv_titulo = (TextView) findViewById(R.id.tv_clase_titulo);
        btn_agregar = (Button) findViewById(R.id.btn_clase_agregar);
        sp_clase_status =(Spinner) findViewById(R.id.sp_clase_status);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        et_hora = (EditText) findViewById(R.id.et_hora);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);

        //LLENAR LOS SPINNER(COMBOBOX)
        llenarStatus();

        //SI NO SE RECIBE UN ID DE CLASE ES PORQUE SE QUIERE CREAR UNO NUEVO
        if(id_clase == null){
            //TITULO DEPENDIENDO DE LA ACCIÓN QUE SE VA A REALIZAR
            tv_titulo.setText("Nueva Clase");

            //TEXTO Y ACCION AL CREAR
            btn_agregar.setText("Crear Clase");
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertarClase();
                }
            });

        //SI SE RECIBE UN ID DE CLASE ES PORQUE SE QUIERE EDITAR UNA EXISTENTE
        }else{
            //TITULO DEPENDIENDO DE LA ACCIÓN QUE SE VA A REALIZAR
            tv_titulo.setText("Actualizar Clase");

            //SE LLENAN LOS CAMPOS CON LOS DATOS ACTUALES DE ESA CLASE EN LA BD
            cargarDatos();

            //TEXTO Y ACCION AL EDITAR
            btn_agregar.setText("Actualizar Clase");
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizarClase();
                }
            });
        }
    }

    private void llenarStatus(){
        String [] opciones_status = {"Activa", "Pospuesta", "Terminada"};

        adapter_status = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones_status);
        sp_clase_status.setAdapter(adapter_status);
    }

    private void insertarClase(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fecha = et_fecha.getText().toString();
        String hora = et_hora.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String status = sp_clase_status.getSelectedItem().toString();

        if(!fecha.isEmpty() && !hora.isEmpty() && !descripcion.isEmpty() && !status.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("fecha", fecha);
            registro.put("hora", hora);
            registro.put("descripcion", descripcion);
            registro.put("status", status);
            registro.put("id_grupo", Integer.parseInt(id_grupo));

            //INSERTAMOS EL REGISTRO DE LA CLASE
            BaseDeDatos.insert("clases", null, registro);

            //SE CREA UNA NOTIFICACION DE LA NUEVA CLASE CREADA POR CADA ALUMNO DENTRO DEL GRUPO

            //OBTENEMOS EL ID DE ESA NUEVA CLASE INSERTADA (LA PRIMERA ORDENADA DESCENDENTEMENTE)
            Cursor fila = BaseDeDatos.rawQuery("select id_clase from clases order by id_clase desc;", null);

            if(fila.moveToFirst()){
                String id_clase = fila.getString(0);

                //OBTENEMOS LA FECHA Y HORA ACTUAL
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                String fecha_hora_creacion = hourdateFormat.format(date);

                String descripcion_notificacion = "Nueva Clase agregada para el Grupo "+info_grupo;
                String status_notificacion = "Pendiente";

                String asistencia = "false";

                //OBTENEMOS TODOS LOS USUARIOS REGISTRADOS DENTRO DE ESE GRUPO
                Cursor fila1 = BaseDeDatos.rawQuery("select usu.id_usuario from grupos as gru " +
                                                        "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                        "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                        "where gru.id_grupo = "+id_grupo+" and ro.rol = 'Alumno';", null);

                if(fila1.getCount() > 0){
                    //SE HACE UNA INSERCION POR CADA ALUMNO EN EL GRUPO
                    while(fila1.moveToNext()){
                        ContentValues registro1 = new ContentValues();

                        registro1.put("fecha_hora_creacion", fecha_hora_creacion);
                        registro1.put("descripcion", descripcion_notificacion);
                        registro1.put("status", status_notificacion);
                        registro1.put("id_grupo", Integer.parseInt(id_grupo));
                        registro1.put("id_clase", Integer.parseInt(id_clase));
                        registro1.put("id_usuario", fila1.getInt(0));

                        //INSERTAMOS EL REGISTRO DE LA NOTIFICACION
                        BaseDeDatos.insert("notificaciones", null, registro1);

                        //SE CREA UNA CONFIRMACION DE ASISTENCIA A LA NUEVA CLASE CREADA POR CADA ALUMNO DENTRO DEL GRUPO

                        ContentValues registro2 = new ContentValues();

                        registro2.put("asistencia", asistencia);
                        registro2.put("id_clase", Integer.parseInt(id_clase));
                        registro2.put("id_usuario", fila1.getInt(0));

                        //INSERTAMOS EL REGISTRO DE LA CONFIRMACION
                        BaseDeDatos.insert("confirmacion", null, registro2);
                    }
                    BaseDeDatos.close();

                    Toast.makeText(this, "Clase agregada exitosamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, ListClase.class);
                    intent.putExtra("is_admin", is_admin);
                    intent.putExtra("id_usuario", id_usuario);
                    intent.putExtra("nombreCompleto", nombreCompleto);
                    intent.putExtra("id_grupo", id_grupo);
                    intent.putExtra("rol", rol);
                    intent.putExtra("info_grupo", info_grupo);
                    startActivity(intent);

                    this.finish();
                }
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarClase(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fecha = et_fecha.getText().toString();
        String hora = et_hora.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String status = sp_clase_status.getSelectedItem().toString();

        if(!fecha.isEmpty() && !hora.isEmpty() && !descripcion.isEmpty() && !status.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("fecha", fecha);
            registro.put("hora", hora);
            registro.put("descripcion", descripcion);
            registro.put("status", status);
            registro.put("id_grupo", Integer.parseInt(id_grupo));

            //ACTUALIZAMOS EL REGISTRO DE LA CLASE
            BaseDeDatos.update("clases", registro, "id_clase="+id_clase, null);

            //SE TIENE QUE CREAR UNA NUEVA NOTIFICACION CON LA NUEVA INFORMACION DE LA CLASE POR CADA ALUMNO DENTRO DEL GRUPO

            //OBTENEMOS LA FECHA Y HORA ACTUAL
            DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            String fecha_hora_creacion = hourdateFormat.format(date);

            String descripcion_notificacion = "Información de la Clase "+id_clase+" del Grupo "+info_grupo+" actualizada";
            String status_notificacion = "Pendiente";

            String asistencia = "false";

            //OBTENEMOS TODOS LOS USUARIOS REGISTRADOS DENTRO DE ESE GRUPO
            Cursor fila = BaseDeDatos.rawQuery("select usu.id_usuario from grupos as gru " +
                                                    "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                    "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                    "where gru.id_grupo = "+id_grupo+" and ro.rol = 'Alumno';", null);

            if(fila.getCount() > 0){
                //SE HACE UNA INSERCION POR CADA ALUMNO EN EL GRUPO
                while(fila.moveToNext()){
                    ContentValues registro1 = new ContentValues();

                    registro1.put("fecha_hora_creacion", fecha_hora_creacion);
                    registro1.put("descripcion", descripcion_notificacion);
                    registro1.put("status", status_notificacion);
                    registro1.put("id_grupo", Integer.parseInt(id_grupo));
                    registro1.put("id_clase", Integer.parseInt(id_clase));
                    registro1.put("id_usuario", fila.getInt(0));

                    //INSERTAMOS EL REGISTRO DE LA NOTIFICACION
                    BaseDeDatos.insert("notificaciones", null, registro1);
                }
                //SE ACTUALIZA LA CONFIRMACION DE ASISTENCIA A FALSE EN TODOS LOS REGISTROS DE LA CLASE MODIFICADA

                ContentValues registro2 = new ContentValues();

                registro2.put("asistencia", asistencia);
                registro2.put("id_clase", Integer.parseInt(id_clase));

                //ACTUALIZAMOS EL REGISTRO DE LA CONFIRMACION
                BaseDeDatos.update("confirmacion", registro2, "id_clase="+id_clase, null);

                BaseDeDatos.close();

                Toast.makeText(this, "Clase actualizada exitosamente", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListClase.class);
                intent.putExtra("is_admin", is_admin);
                intent.putExtra("id_usuario", id_usuario);
                intent.putExtra("nombreCompleto", nombreCompleto);
                intent.putExtra("id_grupo", id_grupo);
                intent.putExtra("rol", rol);
                intent.putExtra("info_grupo", info_grupo);
                startActivity(intent);

                this.finish();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select fecha, hora, descripcion, status from clases " +
                                                "where id_clase = "+id_clase+";", null);

        if(fila.moveToFirst()) {
            //EN LOS EDIT TEXT SE LES ASIGNA NORMAL EL VALOR CON setText()
            et_fecha.setText(fila.getString(0));
            et_hora.setText(fila.getString(1));
            et_descripcion.setText(fila.getString(2));
            sp_clase_status.setSelection(adapter_status.getPosition(fila.getString(3)));
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

    public void irListClase(View v){
        Intent intent = new Intent(this, ListClase.class);

        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        intent.putExtra("id_grupo", id_grupo);
        intent.putExtra("rol", rol);
        intent.putExtra("info_grupo", info_grupo);

        startActivity(intent);

        this.finish();
    }
}
