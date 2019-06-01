package com.example.capacitaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListGrupo extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_grupos;
    String is_admin, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grupo);

        nombres = new ArrayList<String>();

        nombres.add("Pablo");
        nombres.add("Juan");
        nombres.add("Juana");
        nombres.add("Cinthia");
        nombres.add("Lizbeth");
        nombres.add("Rafael");
        nombres.add("Judith");
        nombres.add("Elizabeth");
        nombres.add("Murillo");

        rv_grupos = (RecyclerView) findViewById(R.id.rv_grupos);
        AdapterGrupos adapter = new AdapterGrupos(this, nombres);
        rv_grupos.setAdapter(adapter);
        rv_grupos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");

        Toast.makeText(this, "Admin?: "+is_admin, Toast.LENGTH_SHORT).show();
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
        startActivity(intent);

        this.finish();
    }

    public void irGrupos(View v){
        Intent intent = new Intent(this, ListGrupo.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();
    }

    public void irCursos(View v){
        Intent intent = new Intent(this, ListCurso.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();
    }

    public void irUsuarios(View v){
        /*Intent intent = new Intent(this, ListUsuario.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Menú Usuarios", Toast.LENGTH_SHORT).show();
    }

    public void irNotificaciones(View v){
        /*Intent intent = new Intent(this, ListNotificaciones.class);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("id_usuario", id_usuario);
        startActivity(intent);

        this.finish();*/

        Toast.makeText(this, "Ir a Notificaciones", Toast.LENGTH_SHORT).show();
    }
}
