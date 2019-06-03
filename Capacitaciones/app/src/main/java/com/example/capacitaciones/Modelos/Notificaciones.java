package com.example.capacitaciones.Modelos;

public class Notificaciones {
    private int id_notificacion;
    private String fecha_hora;
    private String descripcion;
    private String estatus;
    private int id_usuario;
    private int id_clase;
    private int id_grupo;
    public Notificaciones(){

    }

    public Notificaciones(int id_notificacion, String fecha_hora, String descripcion, String estatus, int id_usuario) {
        this.id_notificacion = id_notificacion;
        this.fecha_hora = fecha_hora;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.id_usuario = id_usuario;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
}
