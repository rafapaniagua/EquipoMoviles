package com.example.capacitaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListClase extends AppCompatActivity {

    ArrayList<String> nombres;
    RecyclerView rv_clases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clase);

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
