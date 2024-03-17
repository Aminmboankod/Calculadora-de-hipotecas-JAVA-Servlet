package domain;

public class Amortizacion {

	private int PagoNumero;
	private double CapitalPendiente;
	private double CapitalAmortizado;
	private double Intereses;
	private double Cuota;
	
	/**
	 * @return the pagoNumero
	 */
	public int getPagoNumero() {
		return PagoNumero;
	}
	/**
	 * @param pagoNumero the pagoNumero to set
	 */
	public void setPagoNumero(int pagoNumero) {
		PagoNumero = pagoNumero;
	}
	/**
	 * @return the capitalPendiente
	 */
	public double getCapitalPendiente() {
		return CapitalPendiente;
	}
	/**
	 * @param capitalPendiente the capitalPendiente to set
	 */
	public void setCapitalPendiente(double capitalPendiente) {
		CapitalPendiente = capitalPendiente;
	}
	/**
	 * @return the capitalAmortizado
	 */
	public double getCapitalAmortizado() {
		return CapitalAmortizado;
	}
	/**
	 * @param capitalAmortizado the capitalAmortizado to set
	 */
	public void setCapitalAmortizado(double capitalAmortizado) {
		CapitalAmortizado = capitalAmortizado;
	}
	/**
	 * @return the intereses
	 */
	public double getIntereses() {
		return Intereses;
	}
	/**
	 * @param intereses the intereses to set
	 */
	public void setIntereses(double intereses) {
		Intereses = intereses;
	}
	/**
	 * @return the cuota
	 */
	public double getCuota() {
		return Cuota;
	}
	/**
	 * @param cuota the cuota to set
	 */
	public void setCuota(double cuota) {
		Cuota = cuota;
	}
	
}
