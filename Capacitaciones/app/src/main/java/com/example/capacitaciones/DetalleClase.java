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
import com.example.capacitaciones.Modelos.Clases;

public class DetalleClase extends AppCompatActivity {
    TextView tv_nombre_usuario,tv_detalle_curso_title,tv_info_detalle_clase_fecha,tv_info_detalle_clase_descripcion,
            tv_info_detalle_clase_estatus;
    Button btn_regresar,btn_asistencia;
    String is_admin, id_usuario, id_clase, nombreCompleto;
    String id_grupo,rol, info_grupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_clase);

        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_clase = this.getIntent().getExtras().getString("id_clase");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");
        rol = this.getIntent().getExtras().getString("rol");
        info_grupo = this.getIntent().getExtras().getString("info_grupo");

        tv_nombre_usuario = (TextView) findViewById(R.id.tv_nombre_usuario);
        tv_nombre_usuario.setText(nombreCompleto);

        tv_detalle_curso_title = (TextView) findViewById(R.id.tv_detalle_curso_title);
        tv_info_detalle_clase_fecha = (TextView) findViewById(R.id.tv_info_detalle_clase_fecha);
        tv_info_detalle_clase_descripcion = (TextView) findViewById(R.id.tv_info_detalle_clase_descripcion);
        tv_info_detalle_clase_estatus = (TextView) findViewById(R.id.tv_info_detalle_clase_estatus);

        btn_regresar =(Button) findViewById(R.id.btn_regresar);
        btn_asistencia =(Button) findViewById(R.id.btn_asistencia);

        cargarDatos();
        btn_asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asistencia();
            }
        });

    }
    public void cargarDatos(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_clase, fecha, hora, descripcion, status from clases where id_clase="+id_clase, null);

        if(fila.moveToFirst()) {
            //ASIGNAMOS LA INFORMACIÓN CONSULTADA EN LOS TEXTVIEW
            tv_detalle_curso_title.setText("Detalle clase ("+fila.getString(0)+")");
            tv_info_detalle_clase_fecha.setText(fila.getString(1)+" "+fila.getString(2));
            tv_info_detalle_clase_descripcion.setText(fila.getString(3));
            tv_info_detalle_clase_estatus.setText(fila.getString(4));
        }
    }
    private void asistencia(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_clase, fecha, hora, descripcion, status from clases where id_clase="+id_clase, null);
        //Cursor fila1 = BaseDeDatos.rawQuery("select asistencia, id_clase,id_usuario, from confirmacion where id_usuario="+id_usuario+" and id_clase="+id_clase, null);
        ContentValues registro = new ContentValues();
        if(fila.moveToFirst()) {
            Clases clase = new Clases();
            clase.setStatus(fila.getString(4));
            if(clase.getStatus() != "Terminada"){
                        registro.put("asistencia","true");
                        registro.put("id_clase",id_clase);
                        registro.put("id_usuario",id_usuario);
                        BaseDeDatos.insert("confirmacion", null, registro);
                        BaseDeDatos.close();
                        Toast.makeText(this, "Usted ha confirmado su asistencia exitosamente", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(getApplicationContext(), "La clase ha terminado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void  regresar(View view){
        Intent intent = new Intent(this, ListClase.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", this.id_usuario);
        intent.putExtra("nombreCompleto", nombreCompleto);
        intent.putExtra("id_grupo", id_grupo);
        intent.putExtra("rol", rol);
        intent.putExtra("info_grupo", info_grupo);
        startActivity(intent);
        this.finish();
    }
}
