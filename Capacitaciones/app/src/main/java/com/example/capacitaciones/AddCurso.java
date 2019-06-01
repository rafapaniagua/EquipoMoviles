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

public class AddCurso extends AppCompatActivity {

    private EditText et_nombre, et_duracion, et_descripcion, et_fecha;
    private TextView tv_titulo;
    private Button btn_agregar;
    private int id_curso;
    private String control, is_admin, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curso);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_duracion = (EditText) findViewById(R.id.et_duracion);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        btn_agregar = (Button) findViewById(R.id.btn_curso_agregar);
        tv_titulo = (TextView) findViewById(R.id.tv_curso_titulo);

        //RECIBIMOS LOS PARAMETROS
        if(getIntent().hasExtra("control")) {
            id_curso = this.getIntent().getExtras().getInt("id_curso");
            control = this.getIntent().getExtras().getString("control");
        }

        if (getIntent().hasExtra("is_admin")){
            is_admin = this.getIntent().getExtras().getString("is_admin");
            id_usuario = this.getIntent().getExtras().getString("id_usuario");
            control = "new";
        }

        if(control.equals("edit")){
            btn_agregar.setText("Actualizar");
            tv_titulo.setText("Editar Curso");
            mostrarCurso();
        }
    }

    public void mostrarCurso(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select * from cursos where id_curso = "+id_curso+";", null);

        if(fila.moveToFirst()){
            et_nombre.setText(fila.getString(1));
            et_descripcion.setText(fila.getString(2));
            et_fecha.setText(fila.getString(3));
            et_duracion.setText(fila.getString(4));
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
            //Codigo para actualizar la información de un curso
            registro.put("nombre", et_nombre.getText().toString());
            registro.put("descripcion", et_descripcion.getText().toString());
            registro.put("fecha_creacion",et_fecha.getText().toString());
            registro.put("duracion", et_duracion.getText().toString());

            int a = BaseDeDatos.update("cursos", registro, "id_curso = " + id_curso,null);
            BaseDeDatos.close();
            if(a == 1 ){
                Toast.makeText(this, "Los datos han sido actualizados exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Codigo para agregar nuevos cursos
            if(!et_nombre.getText().toString().isEmpty() && !et_descripcion.getText().toString().isEmpty() &&
                !et_fecha.getText().toString().isEmpty() && !et_duracion.getText().toString().isEmpty()){

                registro.put("nombre", et_nombre.getText().toString());
                registro.put("descripcion", et_descripcion.getText().toString());
                registro.put("fecha_creacion",et_fecha.getText().toString());
                registro.put("duracion", et_duracion.getText().toString());

                BaseDeDatos.insert("cursos", null, registro);
                BaseDeDatos.close();
                Toast.makeText(this, "El curso fue agregado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Por favor completa la información", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
