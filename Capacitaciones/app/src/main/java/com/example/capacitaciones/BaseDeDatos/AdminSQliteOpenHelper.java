package com.example.capacitaciones.BaseDeDatos;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQliteOpenHelper extends SQLiteOpenHelper {

    private String tablas [] = {
            "CREATE TABLE cursos(id_curso INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(50), descripcion VARCHAR(200), fecha_creacion VARCHAR(20), duracion VARCHAR(50));",
            "CREATE TABLE usuarios( id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, nombres VARCHAR(60), apellidos VARCHAR(60), correo VARCHAR(20), contrasena VARCHAR(15), is_admin VARCHAR(15));",
            "CREATE TABLE grupos( id_grupo INTEGER PRIMARY KEY AUTOINCREMENT, clave VARCHAR(20), status VARCHAR(20),  no_integrantes INT, fecha_inicio VARCHAR(15), fecha_fin VARCHAR(15), id_curso INTEGER, FOREIGN KEY (id_curso) REFERENCES cursos(id_curso) ON DELETE CASCADE ON UPDATE CASCADE);",
            "CREATE TABLE clases(id_clase INTEGER PRIMARY KEY AUTOINCREMENT, fecha VARCHAR(15), hora VARCHAR(15), descripcion VARCHAR(200), status VARCHAR(20), id_grupo INTEGER, FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE ON UPDATE CASCADE);",
            "CREATE TABLE notificaciones(id_notificacion INTEGER PRIMARY KEY AUTOINCREMENT, fecha_hora_creacion VARCHAR(20), descripcion VARCHAR(200), status VARCHAR(20), id_grupo INTEGER, id_clase INTEGER, id_usuario INTEGER, FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (id_clase) REFERENCES clases(id_clase) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE);",
            "CREATE TABLE rol( rol VARCHAR(25), id_grupo INTEGER, id_usuario INTEGER, FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE, PRIMARY KEY (id_grupo, id_usuario));",
            "CREATE TABLE confirmacion( asistencia VARCHAR(15), id_clase INTEGER, id_usuario INTEGER, FOREIGN KEY (id_clase) REFERENCES clases(id_clase) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE, PRIMARY KEY (id_clase, id_usuario));"
    };

    private String inserts [] = {
            "INSERT INTO cursos VALUES(null,'JavaScript', 'Curso de JavaScript desde cero para principiantes', '2019-05-24', 'Un mes');",
            "INSERT INTO cursos VALUES(null,'React', 'Curso Avanzado, utilizando herramientas avanzadas', '2019-05-24', 'Un mes');",
            "INSERT INTO cursos VALUES(null,'CSS Bootstrap', 'Como implementar Bootstrap en nuestros proyectos', '2019-05-24', 'Un mes');",
            "INSERT INTO cursos VALUES(null,'Laravel', 'Aprende a crear y ejecutar Web Services con Laravel', '2019-05-24', 'Un mes');",
            "INSERT INTO cursos VALUES(null,'Code Igniter', 'Implementación del Frameqork dentro de cualquier proyecto', '2019-05-24', 'Un mes');",

            "INSERT INTO usuarios VALUES(null, 'Rafael', 'Paniagua', 'rafa@gmail.com', '12345', 'true');",
            "INSERT INTO usuarios VALUES(null, 'Cinthia', 'Nava', 'cinthia@gmail.com', '12345', 'true');",
            "INSERT INTO usuarios VALUES(null, 'Pablo', 'Gallardo', 'pablo@gmail.com', '12345', 'true');",
            "INSERT INTO usuarios VALUES(null, 'Saul', 'Ornelas', 'saul@gmail.com', '12345', 'false');",
            "INSERT INTO usuarios VALUES(null, 'Juda', 'Vallejo', 'juda@gmail.com', '12345', 'false');",

            "INSERT INTO grupos VALUES(null, 'G-001', 'Activo', 10, '2019-05-24', '2019-06-24', 1);",
            "INSERT INTO grupos VALUES(null, 'G-002', 'Finalizado', 5, '2019-05-24', '2019-06-24', 2);",
            "INSERT INTO grupos VALUES(null, 'G-003', 'Activo', 15, '2019-05-24', '2019-06-24', 3);",
            "INSERT INTO grupos VALUES(null, 'G-004', 'Finalizado', 20, '2019-05-24', '2019-06-24', 4);",
            "INSERT INTO grupos VALUES(null, 'G-005', 'Activo', 8, '2019-05-24', '2019-06-24', 5);",

            "INSERT INTO clases VALUES(null, '2019-05-30', '16:00:00', 'Inducción al Curso', 'Activa', 1);",
            "INSERT INTO clases VALUES(null, '2019-05-26', '16:00:00', 'Inducción al Curso', 'Terminada', 2);",
            "INSERT INTO clases VALUES(null, '2019-05-28', '16:00:00', 'Inducción al Curso', 'Activa', 3);",
            "INSERT INTO clases VALUES(null, '2019-05-25', '16:00:00', 'Inducción al Curso', 'Pospuesta', 4);",
            "INSERT INTO clases VALUES(null, '2019-06-1', '16:00:00', 'Inducción al Curso', 'Activa', 5);",

            "INSERT INTO notificaciones VALUES(null, '2019-05-24 13:52:23', 'Se ha cargado una nueva clase', 'Pendiente', 1, 1, 1);",
            "INSERT INTO notificaciones VALUES(null, '2019-05-24 14:25:00', 'Se ha cargado una nueva clase', 'Pendiente', 2, 2, 2);",
            "INSERT INTO notificaciones VALUES(null, '2019-05-24 10:31:45', 'Se ha cargado una nueva clase', 'Leida', 3, 3, 3);",
            "INSERT INTO notificaciones VALUES(null, '2019-05-24 16:14:23', 'Se ha cargado una nueva clase', 'Pendiente', 4, 4, 4);",
            "INSERT INTO notificaciones VALUES(null, '2019-05-24 12:10:01', 'Se ha cargado una nueva clase', 'Leida', 5, 5, 5);",

            "INSERT INTO rol VALUES('Docente', 1, 1);",
            "INSERT INTO rol VALUES('Docente', 2, 2);",
            "INSERT INTO rol VALUES('Docente', 3, 3);",
            "INSERT INTO rol VALUES('Docente', 4, 4);",
            "INSERT INTO rol VALUES('Docente', 5, 5);",
            "INSERT INTO rol VALUES('Alumno', 1, 2);",
            "INSERT INTO rol VALUES('Alumno', 2, 3);",
            "INSERT INTO rol VALUES('Alumno', 3, 4);",
            "INSERT INTO rol VALUES('Alumno', 4, 5);",
            "INSERT INTO rol VALUES('Alumno', 5, 1);",

            "INSERT INTO confirmacion VALUES('true', 1, 2);",
            "INSERT INTO confirmacion VALUES('false', 2, 3);",
            "INSERT INTO confirmacion VALUES('true', 3, 4);",
            "INSERT INTO confirmacion VALUES('false', 4, 5);",
            "INSERT INTO confirmacion VALUES('true', 5, 1);",
            "INSERT INTO confirmacion VALUES('true', 1, 1);",
            "INSERT INTO confirmacion VALUES('true', 2, 2);",
            "INSERT INTO confirmacion VALUES('true', 3, 3);",
            "INSERT INTO confirmacion VALUES('true', 4, 4);",
            "INSERT INTO confirmacion VALUES('true', 5, 5);"
    };

    public AdminSQliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        /*BaseDeDatos.execSQL("CREATE TABLE usuarios( id_usuario INT PRIMARY KEY AUTOINCREMENT NOT NULL, nombres VARCHAR(60), apellidos VARCHAR(60), correo VARCHAR(20), contrasena VARCHAR(15), is_admin BOOLEAN);");
        BaseDeDatos.execSQL("INSERT INTO usuarios VALUES(null, 'Rafael', 'Paniagua', 'rafa@gmail.com', '12345', true);");*/

        for (String query: tablas) {
            BaseDeDatos.execSQL(query);
        }

        for (String query: inserts) {
            BaseDeDatos.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
