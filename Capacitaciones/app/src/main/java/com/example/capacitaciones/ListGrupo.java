package com.example.capacitaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListGrupo extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_grupos;

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
    }
}
