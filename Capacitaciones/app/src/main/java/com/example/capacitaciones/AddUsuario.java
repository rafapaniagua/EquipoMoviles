package com.example.capacitaciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;

public class AddUsuario extends AppCompatActivity {
    EditText et_nombre_registro,et_apellido,et_correo,et_contrasena;
    Button btn_agregar,btn_regresar;
    TextView tv_usuario_titulo;
    int id_usuario_enviado;
    private String control, is_admin, id_usuario,nombreCompleto;
    TextView tv_nombre_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuario);

        et_nombre_registro = (EditText) findViewById(R.id.et_nombre_registro);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_contrasena = (EditText) findViewById(R.id.et_contrasena);

        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        tv_usuario_titulo = (TextView) findViewById(R.id.tv_usuario_titulo);

        btn_agregar = (Button) findViewById(R.id.btn_usuario_agregar);
        btn_regresar= (Button) findViewById(R.id.btn_regresar);

        //RECIBIMOS LOS PARAMETROS
        if(getIntent().hasExtra("control")) {
            id_usuario_enviado = this.getIntent().getExtras().getInt("id_usuario_enviado");
            control = this.getIntent().getExtras().getString("control");
        }

        if (getIntent().hasExtra("is_admin")){
            is_admin = this.getIntent().getExtras().getString("is_admin");
            id_usuario = this.getIntent().getExtras().getString("id_usuario");
            control = "new";
        }

        if(control.equals("edit")){
            btn_agregar.setText("Actualizar");
            tv_usuario_titulo.setText("Editar usuario");
            mostrarUsuario();
        }
    }

    public void mostrarUsuario(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_usuario,nombres,apellidos,correo,contrasena from usuarios where id_usuario = "+id_usuario_enviado+";", null);

        if(fila.moveToFirst()){
            et_nombre_registro.setText(fila.getString(1));
            et_apellido.setText(fila.getString(2));
            et_correo.setText(fila.getString(3));
            et_contrasena.setText(fila.getString(4));
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, no se encontraron los datos", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }

    public void cancelar(View v){
        if(control.equals("edit")){
            this.finish();
        }else{
            Intent intent = new Intent(this, ListCurso.class);
            intent.putExtra("is_admin", is_admin);
            intent.putExtra("id_usuario", id_usuario);
            startActivity(intent);
            this.finish();
        }
    }

    public void agregar(View v){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        if (control.equals("edit")){
            //Codigo para actualizar la información de un usuario
            registro.put("nombres", et_nombre_registro.getText().toString());
            registro.put("apellidos", et_apellido.getText().toString());
            registro.put("correo",et_correo.getText().toString());
            registro.put("contrasena", et_contrasena.getText().toString());

            int a = BaseDeDatos.update("usuarios", registro, "id_usuario = " + id_usuario_enviado,null);
            BaseDeDatos.close();
            if(a == 1 ){
                Toast.makeText(this, "Los datos han sido actualizados exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Codigo para agregar nuevos usuarios
            if(!et_nombre_registro.getText().toString().isEmpty() && !et_apellido.getText().toString().isEmpty() &&
                    !et_correo.getText().toString().isEmpty() && !et_contrasena.getText().toString().isEmpty()){

                registro.put("nombres", et_nombre_registro.getText().toString());
                registro.put("apellidos", et_apellido.getText().toString());
                registro.put("correo",et_correo.getText().toString());
                registro.put("contrasena", et_contrasena.getText().toString());

                BaseDeDatos.insert("usuarios", null, registro);
                BaseDeDatos.close();
                Toast.makeText(this, "Usuario  agregado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Por favor completa la información", Toast.LENGTH_SHORT).show();
            }
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
        Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        startActivity(intent);

        this.finish();

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }
}
