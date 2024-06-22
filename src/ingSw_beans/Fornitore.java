package ingSw_beans;

import java.text.NumberFormat;
import java.util.Locale;

public class Fornitore {
	private String id;
	private String idFornitore;
	private double costo;
	NumberFormat formatoEuro;

	
	public Fornitore(String id, String idFornitore, double costo) {
		this.id = id;
		this.idFornitore = idFornitore;
		this.costo = costo;
		this.formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
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

	public String getCosto() {
		return this.formatoEuro.format(costo);
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	
}
