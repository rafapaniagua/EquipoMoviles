package com.example.capacitaciones;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capacitaciones.BaseDeDatos.AdminSQliteOpenHelper;
import com.example.capacitaciones.Modelos.Cursos;

import java.util.ArrayList;

public class AdapterCursos extends RecyclerView.Adapter<AdapterCursos.ViewHolderCursos> {

    private Context context;
    private ArrayList<Cursos> cursos_array;

    public AdapterCursos(Context context, ArrayList<Cursos> cursos_array) {
        this.context = context;
        this.cursos_array = cursos_array;
    }

    @NonNull
    @Override
    public ViewHolderCursos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_cursos,
                viewGroup,
                false);
        return new ViewHolderCursos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCursos viewHolderCursos, int i) {
        viewHolderCursos.asignarDatos(cursos_array.get(i));
    }

    @Override
    public int getItemCount() {
        return cursos_array.size();
    }

    public class ViewHolderCursos extends RecyclerView.ViewHolder {

        TextView nombre, duracion;
        ImageView iv_ver, iv_editar, iv_eliminar;

        public ViewHolderCursos(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.tv_list_usuario_nombre);
            duracion = (TextView) itemView.findViewById(R.id.tv_list_curso_duracion);
            iv_ver = (ImageView) itemView.findViewById(R.id.iv_list_curso_ver);
            iv_editar = (ImageView) itemView.findViewById(R.id.iv_list_curso_editar);
            iv_eliminar = (ImageView) itemView.findViewById(R.id.iv_list_curso_eliminar);
        }

        public void asignarDatos(final Cursos curso) {
            nombre.setText(curso.getNombre());
            duracion.setText(curso.getDuracion());

            iv_ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetalleCurso.class);
                    intent.putExtra("id_curso", curso.getId_curso());
                    context.startActivity(intent);
                }
            });

            iv_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddCurso.class);
                    intent.putExtra("id_curso", curso.getId_curso());
                    intent.putExtra("control", "edit");
                    context.startActivity(intent);
                }
            });

            iv_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(context, "rhinoSystems", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                    int delete = BaseDeDatos.delete("cursos", "id_curso = "+curso.getId_curso(), null);
                    if(delete == 1) {
                        Toast.makeText(context, "El curso fue eliminado\nExitosamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Error al eliminar curso", Toast.LENGTH_SHORT).show();
                    }
                    BaseDeDatos.close();
                }
            });

        }
    }
}
