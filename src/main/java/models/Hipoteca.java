package models;

import java.util.ArrayList;
import java.util.List;

import domain.Financiacion;

public class Hipoteca implements Financiacion 
{
	private double Importe;
	private double Interes;
	private int Plazo;
	public ResultadoFinanciacion Resultado;
	
	public Amortizacion Asiento;
	public List<Amortizacion> AmortizacionHipoteca;

    public Hipoteca(double importe, double interes, int plazo) {
        Importe = importe;
        Interes = interes;
        Plazo = plazo;
        Resultado = new ResultadoFinanciacion();
        Asiento = new Amortizacion();
        AmortizacionHipoteca = new ArrayList<>();
    }
	
	
	@Override
	public void CalculoFinanciacion() {
        double i = Interes / 12 / 100; // Convierte el interés anual a mensual y a porcentaje
        double cuota = Importe * (i / (1 - Math.pow(1 + i, -Plazo))); // Calcula la cuota de la hipoteca
        double totalPagos = cuota * Plazo;
        double totalIntereses = totalPagos - Importe;
        
        Resultado.Cuota = cuota;
        Resultado.Pagos = Plazo;
        Resultado.Principal = Importe;
        Resultado.TotalIntereses = totalIntereses;
        Resultado.TotalPagos = totalPagos; 
        
        GeneraTablaAmortizacion(cuota, i);
	}

	@Override
	public void GeneraTablaAmortizacion(double cuota, double interesFijo) {

        double capitalPendiente = Importe;
        for (int n = 1; n <= Plazo; n++) {
            double intereses = capitalPendiente * interesFijo;
            double capitalAmortizado = Resultado.Cuota - intereses;
            capitalPendiente -= capitalAmortizado;
            Asiento = new Amortizacion();
            Asiento.setPagoNumero(n);
            Asiento.setCapitalPendiente(capitalPendiente);
            Asiento.setCapitalAmortizado(capitalAmortizado);
            Asiento.setIntereses(intereses);
            Asiento.setCuota(cuota);
            AmortizacionHipoteca.add(Asiento);
        }
	}
	
	/**
	 * @return the importe
	 */
	public double getImporte() {
		return Importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(double importe) {
		Importe = importe;
	}

	/**
	 * @return the interes
	 */
	public double getInteres() {
		return Interes;
	}

	/**
	 * @param interes the interes to set
	 */
	public void setInteres(double interes) {
		Interes = interes;
	}

	/**
	 * @return the plazo
	 */
	public int getPlazo() {
		return Plazo;
	}

	/**
	 * @param plazo the plazo to set
	 */
	public void setPlazo(int plazo) {
		Plazo = plazo;
	}


}
