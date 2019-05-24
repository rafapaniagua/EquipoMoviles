package com.example.capacitaciones.BaseDeDatos;

public class Config_BD {


    public static final String name_bd = "rhinoSystems";
    public static final int version_bd = 1;

    public static final String [] script_db = {
            "CREATE TABLE cursos(\n" +
                    "\tid_curso INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    nombre VARCHAR(50),\n" +
                    "    descripcion VARCHAR(200),\n" +
                    "    fecha_creacion DATE,\n" +
                    "    duracion VARCHAR(50)\n" +
                    ");",
            "CREATE TABLE usuarios(\n" +
                    "\tid_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    nombres VARCHAR(60),\n" +
                    "    apellidos VARCHAR(60),\n" +
                    "    correo VARCHAR(20),\n" +
                    "    contrasena VARCHAR(15),\n" +
                    "    is_admin BOOLEAN\n" +
                    ");",
            "CREATE TABLE grupos(\n" +
                    "\tid_grupo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    clave VARCHAR(20),\n" +
                    "    status VARCHAR(20),\n" +
                    "    no_integrantes INT,\n" +
                    "    fecha_inicio DATE,\n" +
                    "    fecha_fin DATE,\n" +
                    "    id_curso INT NOT null,\n" +
                    "    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)\n" +
                    ");",
            "CREATE TABLE clases(\n" +
                    "\tid_clase INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    fecha DATE,\n" +
                    "    hora TIME,\n" +
                    "    descripcion VARCHAR(200),\n" +
                    "    status VARCHAR(20),\n" +
                    "    id_grupo INT,\n" +
                    "    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo)\n" +
                    ");",
            "CREATE TABLE notificaciones(\n" +
                    "\tid_notificacion INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    fecha_hora_creacion DATETIME,\n" +
                    "    descripcion VARCHAR(200),\n" +
                    "    status VARCHAR(20),\n" +
                    "    id_grupo INT,\n" +
                    "    id_clase INT,\n" +
                    "    id_usuario INT,\n" +
                    "    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo),\n" +
                    "    FOREIGN KEY (id_clase) REFERENCES clases(id_clase),\n" +
                    "    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)\n" +
                    ");",
            "CREATE TABLE rol(\n" +
                    "\trol VARCHAR(25),\n" +
                    "    id_grupo INT,\n" +
                    "    id_usuario INT,\n" +
                    "    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo),\n" +
                    "    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),\n" +
                    "    PRIMARY KEY (id_grupo, id_usuario)\n" +
                    ");",
            "CREATE TABLE confirmacion(\n" +
                    "\tasistencia BOOLEAN,\n" +
                    "    id_clase INT,\n" +
                    "    id_usuario INT,\n" +
                    "    FOREIGN KEY (id_clase) REFERENCES clases(id_clase),\n" +
                    "    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),\n" +
                    "    PRIMARY KEY (id_clase, id_usuario)\n" +
                    ");",
            "INSERT INTO cursos VALUES(null,'JavaScript', 'Curso de JavaScript desde cero para principiantes', '2019-05-24', 'Un mes');\n" +
                "INSERT INTO cursos VALUES(null,'React', 'Curso Avanzado, utilizando herramientas avanzadas', '2019-05-24', 'Un mes');\n" +
                "INSERT INTO cursos VALUES(null,'CSS Bootstrap', 'Como implementar Bootstrap en nuestros proyectos', '2019-05-24', 'Un mes');\n" +
                "INSERT INTO cursos VALUES(null,'Laravel', 'Aprende a crear y ejecutar Web Services con Laravel', '2019-05-24', 'Un mes');\n" +
                "INSERT INTO cursos VALUES(null,'Code Igniter', 'Implementación del Frameqork dentro de cualquier proyecto', '2019-05-24', 'Un mes');",
            "INSERT INTO usuarios VALUES(null, 'Rafael', 'Paniagua', 'rafa@gmail.com', '12345', true);\n" +
                    "INSERT INTO usuarios VALUES(null, 'Cinthia', 'Nava', 'cinthia@gmail.com', '12345', true);\n" +
                    "INSERT INTO usuarios VALUES(null, 'Pablo', 'Gallardo', 'pablo@gmail.com', '12345', true);\n" +
                    "INSERT INTO usuarios VALUES(null, 'Saul', 'Ornelas', 'saul@gmail.com', '12345', false);\n" +
                    "INSERT INTO usuarios VALUES(null, 'Juda', 'Vallejo', 'juda@gmail.com', '12345', false);",
            "INSERT INTO grupos VALUES(null, 'G-001', 'Activo', 10, '2019-05-24', '2019-06-24', 1);\n" +
                    "INSERT INTO grupos VALUES(null, 'G-002', 'Finalizado', 5, '2019-05-24', '2019-06-24', 2);\n" +
                    "INSERT INTO grupos VALUES(null, 'G-003', 'Activo', 15, '2019-05-24', '2019-06-24', 3);\n" +
                    "INSERT INTO grupos VALUES(null, 'G-004', 'Cerrado', 20, '2019-05-24', '2019-06-24', 4);\n" +
                    "INSERT INTO grupos VALUES(null, 'G-005', 'Activo', 8, '2019-05-24', '2019-06-24', 5);",
            "INSERT INTO clases VALUES(null, '2019-05-30', '16:00:00', 'Inducción al Curso', 'Activa', 1);\n" +
                    "INSERT INTO clases VALUES(null, '2019-05-26', '16:00:00', 'Inducción al Curso', 'Terminada', 2);\n" +
                    "INSERT INTO clases VALUES(null, '2019-05-28', '16:00:00', 'Inducción al Curso', 'Activa', 3);\n" +
                    "INSERT INTO clases VALUES(null, '2019-05-25', '16:00:00', 'Inducción al Curso', 'Pospuesta', 4);\n" +
                    "INSERT INTO clases VALUES(null, '2019-06-1', '16:00:00', 'Inducción al Curso', 'Activa', 5);",
            "INSERT INTO notificaciones VALUES(null, '2019-05-24 13:52:23', 'Se ha cargado una nueva clase', 'Pendiente', 1, 1, 1);\n" +
                    "INSERT INTO notificaciones VALUES(null, '2019-05-24 14:25:00', 'Se ha cargado una nueva clase', 'Pendiente', 2, 2, 2);\n" +
                    "INSERT INTO notificaciones VALUES(null, '2019-05-24 10:31:45', 'Se ha cargado una nueva clase', 'Leida', 3, 3, 3);\n" +
                    "INSERT INTO notificaciones VALUES(null, '2019-05-24 16:14:23', 'Se ha cargado una nueva clase', 'Pendiente', 4, 4, 4);\n" +
                    "INSERT INTO notificaciones VALUES(null, '2019-05-24 12:10:01', 'Se ha cargado una nueva clase', 'Leida', 5, 5, 5);",
            "INSERT INTO rol VALUES('Docente', 1, 1);\n" +
                    "INSERT INTO rol VALUES('Docente', 2, 2);\n" +
                    "INSERT INTO rol VALUES('Docente', 3, 3);\n" +
                    "INSERT INTO rol VALUES('Docente', 4, 4);\n" +
                    "INSERT INTO rol VALUES('Docente', 5, 5);\n" +
                    "INSERT INTO rol VALUES('Alumno', 1, 2);\n" +
                    "INSERT INTO rol VALUES('Alumno', 2, 3);\n" +
                    "INSERT INTO rol VALUES('Alumno', 3, 4);\n" +
                    "INSERT INTO rol VALUES('Alumno', 4, 5);\n" +
                    "INSERT INTO rol VALUES('Alumno', 5, 1);",
            "INSERT INTO confirmacion VALUES(true, 1, 2);\n" +
                    "INSERT INTO confirmacion VALUES(false, 1, 3);\n" +
                    "INSERT INTO confirmacion VALUES(true, 1, 4);\n" +
                    "INSERT INTO confirmacion VALUES(false, 1, 5);\n" +
                    "INSERT INTO confirmacion VALUES(true, 1, 1);"
    };
}
