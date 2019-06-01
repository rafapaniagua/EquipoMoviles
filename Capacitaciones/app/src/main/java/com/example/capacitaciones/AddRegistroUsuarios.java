package com.example.capacitaciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;

public class AddRegistroUsuarios extends AppCompatActivity {

    EditText et_nombre_registro,et_apellido,et_correo,et_contrasena;
    Button btn_agregar,btn_regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        //Relacionamos los elementos de las vistas con sus variables
        et_nombre_registro = (EditText) findViewById(R.id.et_nombre_registro);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_contrasena = (EditText) findViewById(R.id.et_contrasena);
        btn_agregar = (Button) findViewById(R.id.btn_agregar);
        btn_regresar = (Button) findViewById(R.id.btn_regresar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }


    private void registrarUsuario(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "rhinoSystems", null, 1);
        SQLiteDatabase  BaseDeDatos = admin.getWritableDatabase();

        String nombre = et_nombre_registro.getText().toString();
        String apellido = et_apellido.getText().toString();
        String correo = et_correo.getText().toString();
        String contrasena = et_contrasena.getText().toString();
        String is_admin = "false";
        if(!nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("id","0");
            registro.put("nombre",nombre);
            registro.put("apellido",apellido);
            registro.put("corro",correo);
            registro.put("contrasena",contrasena);
            registro.put("is_admin",is_admin);
            //Ingresamos registro en la tabla de usuarios
            BaseDeDatos.insert("usuarios",null,registro);

            BaseDeDatos.close();
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this, "Debes ingresar todos los campos", Toast.LENGTH_SHORT).show();

        }
    }
    public void cancelar(View v){
        Toast.makeText(this, "No se guardo su informacion", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

