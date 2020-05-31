package com.example.firebaseproject.modelo;

/*
    Clase donde instancio los datos de mi layout popUp al presionar sobre una fecha
    y los guardo dentro de mi base de datos firebase
 */
public class FechasDeCumpleanios {

    private String claseId;
    private String userId;
    private String nombrePersona;
    private String edadPersona;
    private String fechaCumpleanios;
    private String ideasParaRegalar;

    public FechasDeCumpleanios() {
        super();
    }

    public FechasDeCumpleanios(String claseId, String userId, String nombrePersona, String edadPersona, String fechaCumpleanios, String ideasParaRegalar) {
        this.claseId = claseId;
        this.userId = userId;
        this.nombrePersona = nombrePersona;
        this.edadPersona = edadPersona;
        this.fechaCumpleanios = fechaCumpleanios;
        this.ideasParaRegalar = ideasParaRegalar;
    }

    public String getClaseId() {
        return claseId;
    }

    public void setClaseId(String claseId) {
        this.claseId = claseId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getEdadPersona() {
        return edadPersona;
    }

    public void setEdadPersona(String edadPersona) {
        this.edadPersona = edadPersona;
    }

    public String getFechaCumpleanios() {
        return fechaCumpleanios;
    }

    public void setFechaCumpleanios(String fechaCumpleanios) {
        this.fechaCumpleanios = fechaCumpleanios;
    }

    public String getIdeasParaRegalar() {
        return ideasParaRegalar;
    }

    public void setIdeasParaRegalar(String ideasParaRegalar) {
        this.ideasParaRegalar = ideasParaRegalar;
    }
}
