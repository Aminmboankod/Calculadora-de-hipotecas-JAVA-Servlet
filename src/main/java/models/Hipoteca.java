package models;

import domain.CalculadoraHipoteca;

public class Hipoteca {
    private double importe;
    private double interes;
    private int plazo;
    private int usuario_id;
    private int id;

    public Hipoteca() {}

    public Hipoteca(CalculadoraHipoteca hipoteca) {
        this.importe = hipoteca.getImporte();
        this.interes = hipoteca.getInteres();
        this.plazo = hipoteca.getPlazo();this.usuario_id = hipoteca.getUsuario_id();
    }

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
