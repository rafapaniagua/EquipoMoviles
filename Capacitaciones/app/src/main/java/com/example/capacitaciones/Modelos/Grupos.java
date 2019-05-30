package com.example.capacitaciones.Modelos;

public class Grupos {

    private int id_grupo;
    private String clave;
    private String status;
    private String fecha_inicio;
    private String fecha_fin;
    private int no_integrantes;
    private int id_curso;

    public Grupos(int id_grupo, String clave, String status, String fecha_inicio, String fecha_fin, int no_integrantes, int id_curso) {
        this.id_grupo = id_grupo;
        this.clave = clave;
        this.status = status;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.no_integrantes = no_integrantes;
        this.id_curso = id_curso;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getNo_integrantes() {
        return no_integrantes;
    }

    public void setNo_integrantes(int no_integrantes) {
        this.no_integrantes = no_integrantes;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
}
