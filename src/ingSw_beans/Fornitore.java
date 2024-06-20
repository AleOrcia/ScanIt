package ingSw_beans;


public class Fornitore {
	private String id;
	private String idFornitore;
	private double costo;
	
	public Fornitore(String id, String idFornitore, double costo) {
		this.id = id;
		this.idFornitore = idFornitore;
		this.costo = costo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(String idFornitore) {
		this.idFornitore = idFornitore;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	
}
