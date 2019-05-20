package com.example.capacitaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class addRegistroUsuarios extends AppCompatActivity {
    Spinner et_select_puesto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grupo);
        Spinner spinner = (Spinner) findViewById(R.id.et_select_puesto);
        String[] letra = {"Analista","Diseñador","Desarrollador"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));
    }
}

