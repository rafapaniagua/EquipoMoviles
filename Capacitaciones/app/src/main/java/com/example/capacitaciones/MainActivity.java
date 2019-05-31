package com.example.capacitaciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;

public class MainActivity extends AppCompatActivity {

    //VARIABLES PARA COMPONENTES DE VISTA
    private EditText et_correo, et_contrasena;
    private TextView tv_mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_correo = (EditText) findViewById(R.id.et_correo);
        et_contrasena = (EditText) findViewById(R.id.et_contrasena);
        tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);
    }

    public void InicioSesion(View v){
        //CREAMOS LA CONEXIÓN CON NUESTRA CLASE DE BASE DE DATOS
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "rhinoSystems", null, 1);

        //PONEMOS LA BASE DE DATOS EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //OBTENEMOS LOS DATOS DE LOS CAMPOS
        String correo = et_correo.getText().toString();
        String contrasena = et_contrasena.getText().toString();

        //VERFICAMOS QUE LOS CAMPOS NO ESTEN VACIOS
        if(!correo.isEmpty() && !contrasena.isEmpty()){
            //EJECUTAMOS CONSULTA DE DATOS
            Cursor fila = BaseDeDatos.rawQuery("select contrasena, is_admin, id_usuario from usuarios where correo = '" + correo + "';", null);

            //VERIFICAMOS QUE LA CONSULTA REGRESE ALGO
            if(fila.moveToFirst()){

                //VERIFICAMOS QUE LA CONTRASEÑA SEA CORRECTA
                if(contrasena.equals(fila.getString(0))){
                    //CERRAMOS LA BASE DE DATOS
                    BaseDeDatos.close();

                    //Abrimos la vista de inicio
                    Intent intent = new Intent(this, Inicio.class);
                    intent.putExtra("is_admin", fila.getString(1));
                    intent.putExtra("id_usuario", fila.getString(2));
                    startActivity(intent);

                    //CERRAMOS EL ACTIVITY ACTUAL
                    this.finish();
                }else{
                    //SI LA CONTRASEÑA NO COINCIDE, MOSTRAMOS EL ERROR
                    tv_mensaje.setVisibility(View.VISIBLE);
                    //CERRAMOS LA BASE DE DATOS
                    BaseDeDatos.close();
                }

            }else{
                tv_mensaje.setVisibility(View.VISIBLE);
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
        }

    }
}
