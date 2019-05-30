package com.example.capacitaciones.Modelos;

public class Clases {
    private int id_clase;
    private String fecha;
    private String hora;
    private String descripcion;
    private String status;
    private int id_grupo;

    public Clases(int id_clase, String fecha, String hora, String descripcion, String status, int id_grupo) {
        this.id_clase = id_clase;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.status = status;
        this.id_grupo = id_grupo;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
}
