package models;

import domain.Interface.Identidad;

public class Usuario implements Identidad {

	private int id;
    private String nombre;
    private String contraseña;

    public Usuario() {}
    
    // Constructor
    public Usuario( String username, String password) {    
        this.nombre = username;
        this.contraseña = password;
    }
    
    // Constructor
    public Usuario( String username, String password, int userId) {    
        this.nombre = username;
        this.contraseña = password;
        this.id = userId;
    }
    
    @Override
    public int getId()
    {
    	return this.id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setUsername(String username) {
        this.nombre = username;
    }

    public String getPassword() {
        return contraseña;
    }

    public void setPassword(String password) {
        this.contraseña = password;
    }


}
