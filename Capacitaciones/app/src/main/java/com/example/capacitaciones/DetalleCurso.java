package com.example.capacitaciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Clases;

import java.util.List;

public class DetalleCurso extends AppCompatActivity {

    private int id_curso;
    private TextView tv_nombre, tv_descripcion, tv_duracion, tv_fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curso);

        //RECIBIMOS LOS PARAMETROS
        id_curso = this.getIntent().getExtras().getInt("id_curso");

        tv_nombre = (TextView) findViewById(R.id.tv_detalle_curso_nombre);
        tv_descripcion = (TextView) findViewById(R.id.tv_detalle_curso_descripcion);
        tv_duracion = (TextView) findViewById(R.id.tv_detalle_curso_duracion);
        tv_fecha = (TextView) findViewById(R.id.tv_detalle_curso_fecha);

        mostrarDatos();
    }

    public void mostrarDatos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select * from cursos where id_curso = "+id_curso+";", null);

        if(fila.moveToFirst()){
            tv_nombre.setText(fila.getString(1));
            tv_descripcion.setText("Descripcion:\n"+fila.getString(2));
            tv_fecha.setText("Fecha del curso:\n" + fila.getString(3));
            tv_duracion.setText("Duración del Curso:\n" + fila.getString(4));
            BaseDeDatos.close();
        }else{
            Toast.makeText(this, "Lo siento, no se encontraron los datos", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }
    }

    public void regresar(View v){
        this.finish();
    }
}
