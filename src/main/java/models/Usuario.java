package models;

import domain.Identidad;

public class Usuario implements Identidad {
    private int id;
    private String nombre;
    private String apellido;

    // Constructor, getters y setters

    @Override
    public int getId() {
        return id;
    }
}
