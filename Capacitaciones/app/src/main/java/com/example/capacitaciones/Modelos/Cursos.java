package com.example.capacitaciones.Modelos;

public class Cursos {

    private int id_curso;
    private String nombre;
    private String descripcion;
    private String fecha_creacion;
    private String duracion;

    public Cursos() {
    }

    public Cursos(int id_curso, String nombre, String descripcion, String fecha_creacion, String duracion) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_creacion = fecha_creacion;
        this.duracion = duracion;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
