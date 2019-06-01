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
import com.example.capacitaciones.Modelos.Cursos;
import com.example.capacitaciones.Modelos.Usuarios;

import java.util.ArrayList;

public class AddGrupo extends AppCompatActivity {

    ArrayList<Cursos> cursos_array;
    ArrayList<Usuarios> usuarios_array;
    TextView tv_nombre_usuario, tv_titulo;
    Button btn_agregar;
    Spinner sp_grupo_curso, sp_grupo_docente, sp_grupo_status;
    EditText et_clave, et_no_integrantes, et_fecha_inicio, et_fecha_fin;
    String is_admin, id_usuario, id_grupo, nombreCompleto;
    ArrayAdapter<String> adapter_cursos, adapter_docentes, adapter_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grupo);

        //INICIALIZAMOS LOS ARREGLOS
        cursos_array = new ArrayList<Cursos>();
        usuarios_array = new ArrayList<Usuarios>();

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");

        //MOSTRAMOS EN LA PANTALLA EL NOMBRE DEL USUARIO ACTUAL
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        //RELACIONAMOS LOS ELEMENTOS DE LA VISTA CON SUS VARIABLES
        tv_titulo = (TextView) findViewById(R.id.tv_grupo_titulo);
        btn_agregar = (Button) findViewById(R.id.btn_grupo_agregar);
        sp_grupo_curso =(Spinner) findViewById(R.id.sp_grupo_curso);
        sp_grupo_docente =(Spinner) findViewById(R.id.sp_grupo_docente);
        sp_grupo_status =(Spinner) findViewById(R.id.sp_grupo_status);
        et_clave = (EditText) findViewById(R.id.et_clave);
        et_no_integrantes = (EditText) findViewById(R.id.et_no_integrantes);
        et_fecha_inicio = (EditText) findViewById(R.id.et_fecha_inicio);
        et_fecha_fin = (EditText) findViewById(R.id.et_fecha_fin);

        //LLENAR LOS SPINNER(COMBOBOX)
        llenarCursos();
        llenarDocentes();
        llenarStatus();

        //SI NO SE RECIBE UN ID DE GRUPO ES PORQUE SE QUIERE CREAR UNO NUEVO
        if(id_grupo == null){
            //TITULO DEPENDIENDO DE LA ACCIÓN QUE SE VA A REALIZAR
            tv_titulo.setText("Nuevo Grupo");

            //TEXTO Y ACCION AL CREAR
            btn_agregar.setText("Crear Grupo");
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertarGrupo();
                }
            });

        //SI SE RECIBE UN ID DE GRUPO ES PORQUE SE QUIERE EDITAR UNO EXISTENTE
        }else{
            //TITULO DEPENDIENDO DE LA ACCIÓN QUE SE VA A REALIZAR
            tv_titulo.setText("Actualizar Grupo");

            //SE LLENAN LOS CAMPOS CON LOS DATOS ACTUALES DE ESE GRUPO EN LA BD
            cargarDatos();

            //TEXTO Y ACCION AL EDITAR
            btn_agregar.setText("Actualizar Grupo");
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizarGrupo();
                }
            });
        }

        //Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario+"\nGrupo: "+id_grupo, Toast.LENGTH_SHORT).show();
    }

    private void llenarCursos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //TRAEMOS DE LA BD EL ID Y EL NOMBRE DE CADA CURSO
        Cursor fila = BaseDeDatos.rawQuery("select id_curso, nombre from cursos;", null);

        if(fila.getCount() > 0){
            while(fila.moveToNext()){
                //GUARDAMOS ESOS DATOS EN UN ARRAYLIST DE CURSOS
                Cursos c = new Cursos();

                c.setId_curso(fila.getInt(0));
                c.setNombre(fila.getString(1));

                cursos_array.add(c);
            }
            BaseDeDatos.close();

            //CREAMOS UN ARREGLO ESTÁTICO DEL NUMERO DE ELEMENTOS QUE TIENE EL ARRAYLIST
            String [] opciones_cursos = new String[cursos_array.size()];

            //LLENAMOS EL ARREGLO ESTÁTICO CON CADA UNO DE LOS ELEMENTOS DEL ARRAYLIST
            for(int i = 0; i < cursos_array.size(); i++){
                //INDICAMOS EL ID, LUEGO UNA COMA "," Y AL FINAL EL NOMBRE
                opciones_cursos[i] = cursos_array.get(i).getId_curso()+","+cursos_array.get(i).getNombre();
            }

            //CREAMOS UN ADAPTADOR PARA INGRESAR AL SPINNER LA LISTA CREADA ANTERIORMENTE
            adapter_cursos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones_cursos);
            sp_grupo_curso.setAdapter(adapter_cursos);
        }else{
            Toast.makeText(this, "No hay ningún curso registrado", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }

    private void llenarDocentes(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //TRAEMOS DE LA BD EL ID Y EL NOMBRE DE CADA CURSO
        Cursor fila = BaseDeDatos.rawQuery("select id_usuario, nombres, apellidos from usuarios;", null);

        if(fila.getCount() > 0){
            while(fila.moveToNext()){
                //GUARDAMOS ESOS DATOS EN UN ARRAYLIST DE CURSOS
                Usuarios u = new Usuarios();

                u.setId_usuario(fila.getInt(0));
                u.setNombres(fila.getString(1));
                u.setApellidos(fila.getString(2));

                usuarios_array.add(u);
            }
            BaseDeDatos.close();

            //CREAMOS UN ARREGLO ESTÁTICO DEL NUMERO DE ELEMENTOS QUE TIENE EL ARRAYLIST
            String [] opciones_docentes = new String[usuarios_array.size()];

            //LLENAMOS EL ARREGLO ESTÁTICO CON CADA UNO DE LOS ELEMENTOS DEL ARRAYLIST
            for(int i = 0; i < usuarios_array.size(); i++){
                //INDICAMOS EL ID, LUEGO UNA COMA "," Y AL FINAL EL NOMBRE
                opciones_docentes[i] = usuarios_array.get(i).getId_usuario()+","+usuarios_array.get(i).getNombres()+" "+usuarios_array.get(i).getApellidos();
            }

            //CREAMOS UN ADAPTADOR PARA INGRESAR AL SPINNER LA LISTA CREADA ANTERIORMENTE
            adapter_docentes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones_docentes);
            sp_grupo_docente.setAdapter(adapter_docentes);
        }else{
            Toast.makeText(this, "No hay ningún usuario registrado", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }

    private void llenarStatus(){
        String [] opciones_status = {"Activo", "Finalizado"};

        adapter_status = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones_status);
        sp_grupo_status.setAdapter(adapter_status);
    }

    private void insertarGrupo(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String clave = et_clave.getText().toString();
        String status = sp_grupo_status.getSelectedItem().toString();
        String no_integrantes = et_no_integrantes.getText().toString();
        String fecha_inicio = et_fecha_inicio.getText().toString();
        String fecha_fin = et_fecha_fin.getText().toString();
        //DEL SPINNER DE CURSO SE RECUPERA EL ID Y EL NOMBRE SEPARADOS POR UNA COMA
        //SE SEPARAN CON EL SPLIT Y SE GUARDAN EN UN ARREGLO
        String [] curso_compuesto = sp_grupo_curso.getSelectedItem().toString().split(",");
        //DEBEMOS RECUPERAR EL ID, QUE ES LA PRIMERA POSICIÓN DEL ARREGLO
        String id_curso = curso_compuesto[0];

        if(!clave.isEmpty() && !status.isEmpty() && !no_integrantes.isEmpty() && !fecha_inicio.isEmpty() && !fecha_fin.isEmpty() && !id_curso.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("clave", clave);
            registro.put("status", status);
            registro.put("no_integrantes", Integer.parseInt(no_integrantes));
            registro.put("fecha_inicio", fecha_inicio);
            registro.put("fecha_fin", fecha_fin);
            registro.put("id_curso", Integer.parseInt(id_curso));

            //INSERTAMOS EL REGISTRO DEL GRUPO
            BaseDeDatos.insert("grupos", null, registro);

            //SE TIENE QUE INSERTAR UN REGISTRO EN LA TABLA ROL PARA DEFINIR AL DOCENTE DEL GRUPO

            //OBTENEMOS EL ID DE ESE NUEVO GRUPO INSERTADO (EL PRIMERO ORDENADO DESCENDENTEMENTE)
            Cursor fila = BaseDeDatos.rawQuery("select id_grupo from grupos order by id_grupo desc;", null);

            if(fila.moveToFirst()){
                String id_grupo = fila.getString(0);

                String rol = "Docente";
                //DEL SPINNER DE DOCENTE SE RECUPERA EL ID Y EL NOMBRE SEPARADOS POR UNA COMA
                //SE SEPARAN CON EL SPLIT Y SE GUARDAN EN UN ARREGLO
                String [] docente_compuesto = sp_grupo_docente.getSelectedItem().toString().split(",");
                //DEBEMOS RECUPERAR EL ID, QUE ES LA PRIMERA POSICIÓN DEL ARREGLO
                String id_usuario = docente_compuesto[0];

                ContentValues registro1 = new ContentValues();

                registro1.put("rol", rol);
                registro1.put("id_grupo", Integer.parseInt(id_grupo));
                registro1.put("id_usuario", Integer.parseInt(id_usuario));

                //INSERTAMOS EL REGISTRO DEL ROL
                BaseDeDatos.insert("rol", null, registro1);

                BaseDeDatos.close();

                Toast.makeText(this, "Grupo agregado exitosamente", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListGrupo.class);
                intent.putExtra("is_admin", is_admin);
                intent.putExtra("id_usuario", this.id_usuario);
                intent.putExtra("nombreCompleto", nombreCompleto);
                startActivity(intent);

                this.finish();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarGrupo(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String clave = et_clave.getText().toString();
        String status = sp_grupo_status.getSelectedItem().toString();
        String no_integrantes = et_no_integrantes.getText().toString();
        String fecha_inicio = et_fecha_inicio.getText().toString();
        String fecha_fin = et_fecha_fin.getText().toString();
        //DEL SPINNER DE CURSO SE RECUPERA EL ID Y EL NOMBRE SEPARADOS POR UNA COMA
        //SE SEPARAN CON EL SPLIT Y SE GUARDAN EN UN ARREGLO
        String [] curso_compuesto = sp_grupo_curso.getSelectedItem().toString().split(",");
        //DEBEMOS RECUPERAR EL ID, QUE ES LA PRIMERA POSICIÓN DEL ARREGLO
        String id_curso = curso_compuesto[0];

        if(!clave.isEmpty() && !status.isEmpty() && !no_integrantes.isEmpty() && !fecha_inicio.isEmpty() && !fecha_fin.isEmpty() && !id_curso.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("clave", clave);
            registro.put("status", status);
            registro.put("no_integrantes", Integer.parseInt(no_integrantes));
            registro.put("fecha_inicio", fecha_inicio);
            registro.put("fecha_fin", fecha_fin);
            registro.put("id_curso", Integer.parseInt(id_curso));

            //ACTUALIZAMOS EL REGISTRO DEL GRUPO
            BaseDeDatos.update("grupos", registro, "id_grupo="+id_grupo, null);

            //SE TIENE QUE ACTUALIZAR EL REGISTRO EN LA TABLA ROL PARA DEFINIR AL DOCENTE DEL GRUPO

            String rol = "Docente";
            //DEL SPINNER DE DOCENTE SE RECUPERA EL ID Y EL NOMBRE SEPARADOS POR UNA COMA
            //SE SEPARAN CON EL SPLIT Y SE GUARDAN EN UN ARREGLO
            String [] docente_compuesto = sp_grupo_docente.getSelectedItem().toString().split(",");
            //DEBEMOS RECUPERAR EL ID, QUE ES LA PRIMERA POSICIÓN DEL ARREGLO
            String id_usuario = docente_compuesto[0];

            ContentValues registro1 = new ContentValues();

            registro1.put("rol", rol);
            registro1.put("id_grupo", Integer.parseInt(id_grupo));
            registro1.put("id_usuario", Integer.parseInt(id_usuario));

            //ACTUALIZAMOS EL REGISTRO DEL ROL
            BaseDeDatos.update("rol", registro1, "id_grupo="+id_grupo+" and rol='Docente'", null);

            BaseDeDatos.close();

            Toast.makeText(this, "Grupo actualizado exitosamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ListGrupo.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", this.id_usuario);
            intent.putExtra("nombreCompleto", nombreCompleto);
            startActivity(intent);

            this.finish();
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select gru.clave, cur.id_curso, cur.nombre, usu.id_usuario, usu.nombres, usu.apellidos, gru.no_integrantes, gru.status, gru.fecha_inicio, gru.fecha_fin from grupos as gru " +
                                                "join cursos as cur on gru.id_curso = cur.id_curso " +
                                                "join rol as ro on ro.id_grupo = gru.id_grupo " +
                                                "join usuarios as usu on ro.id_usuario = usu.id_usuario " +
                                                "where gru.id_grupo = "+id_grupo+" and ro.rol = 'Docente';", null);

        if(fila.moveToFirst()) {
            //EN LOS EDIT TEXT SE LES ASIGNA NORMAL EL VALOR CON setText()
            et_clave.setText(fila.getString(0));
            //EN LOS SPINNER SE USA setSelection() PARA DEFINIR UN ELEMENTO SELECCIONADO DESDE QUE CARGA (COMO UN SELECTED)
            //SE UTILIZAN LOS DATOS DEL ADAPTER CREADO AL LLENAR EL SPINNER CON INFORMACIÓN
            //Y SE USA EL MÉTODO getPosition() PARA OBTENER EL ÍNDICE DE UN ELEMENTO ESPECÍFICO DENTRO DE ESA LISTA
            //EL ATRIBUTO PASADO ES LA CONCATENACIÓN DEL ID, CON UNA COMA "'" Y EL NOMBRE TAL COMO APARECE EN EL SPINNER
            sp_grupo_curso.setSelection(adapter_cursos.getPosition(fila.getInt(1)+","+fila.getString(2)));
            sp_grupo_docente.setSelection(adapter_docentes.getPosition(fila.getInt(3)+","+fila.getString(4)+" "+fila.getString(5)));
            et_no_integrantes.setText(String.valueOf(fila.getInt(6)));
            sp_grupo_status.setSelection(adapter_status.getPosition(fila.getString(7)));
            et_fecha_inicio.setText(fila.getString(8));
            et_fecha_fin.setText(fila.getString(9));
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
}
