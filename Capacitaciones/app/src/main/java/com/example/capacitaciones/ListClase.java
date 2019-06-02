package com.example.capacitaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListClase extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_clases;
    String is_admin, id_usuario, id_grupo, nombreCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clase);

        //RECIBIMOS LOS PARAMETROS
        is_admin = this.getIntent().getExtras().getString("is_admin");
        id_usuario = this.getIntent().getExtras().getString("id_usuario");
        nombreCompleto = this.getIntent().getExtras().getString("nombreCompleto");
        id_grupo = this.getIntent().getExtras().getString("id_grupo");

        Toast.makeText(this, "Admin: "+is_admin+"\nUsuario: "+id_usuario+"\nNombre: "+nombreCompleto+"\nGrupo: "+id_grupo, Toast.LENGTH_SHORT).show();

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

        rv_clases = (RecyclerView) findViewById(R.id.rv_clases);
        AdapterClases adapter = new AdapterClases(this, nombres);
        rv_clases.setAdapter(adapter);
        rv_clases.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
