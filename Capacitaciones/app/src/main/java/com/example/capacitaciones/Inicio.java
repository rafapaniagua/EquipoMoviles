package com.example.capacitaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_grupos, rv_clases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

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

        rv_grupos = (RecyclerView) findViewById(R.id.rv_inicio_grupos);
        AdapterInicioGrupos adapter = new AdapterInicioGrupos(this, nombres);
        rv_grupos.setAdapter(adapter);
        rv_grupos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rv_clases = (RecyclerView) findViewById(R.id.rv_inicio_clases);
        AdapterInicioClases adapter1 = new AdapterInicioClases(this, nombres);
        rv_clases.setAdapter(adapter1);
        rv_clases.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
}

