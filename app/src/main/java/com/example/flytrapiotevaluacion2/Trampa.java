
package com.example.flytrapiotevaluacion2;

public class Trampa {

    private int id;
    private String codigoSector;
    private String tipoTrampa;
    private int nivelCaptura;
    private String observaciones;

    public Trampa(int id, String codigoSector,
                  String tipoTrampa, int nivelCaptura,
                  String observaciones) {

        this.id = id;
        this.codigoSector = codigoSector;
        this.tipoTrampa = tipoTrampa;
        this.nivelCaptura = nivelCaptura;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public String getCodigoSector() {
        return codigoSector;
    }

    public String getTipoTrampa() {
        return tipoTrampa;
    }

    public int getNivelCaptura() {
        return nivelCaptura;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
