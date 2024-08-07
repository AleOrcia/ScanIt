package ingSw_beans;

import java.text.NumberFormat;
import java.util.Locale;

public class Prodotto {
	private String id;
	private String nome;
	private String descrizione;
	private double prezzo;
	private int quantita;
	private String idFornitore;
    NumberFormat formatoEuro;
	
	
	public Prodotto(String id, String nome, String descrizione, double prezzo, int quantita, String idFornitore) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantita = quantita;
		this.idFornitore = idFornitore;
		this.formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getPrezzo() {
		return this.formatoEuro.format(prezzo);
	}


	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	public int getQuantita() {
		return quantita;
	}


	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}


	public String getIdFornitore() {
		return idFornitore;
	}


	public void setIdFornitore(String idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("----------------------");
		sb.append("\n");
		sb.append("ID: "+getId());
		sb.append("\n");
		sb.append("Nome: "+getNome());
		sb.append("\n");
		sb.append("Descrizione: "+getDescrizione());
		sb.append("\n");
		sb.append("Prezzo: "+getPrezzo());
		sb.append("\n");
		sb.append("Quantità: "+getQuantita());
		sb.append("\n");
		sb.append("Fornitore: "+getIdFornitore());
		sb.append("\n");
		sb.append("----------------------");
		sb.append("\n");
		sb.append("\n");
		
		return sb.toString();
	}
	
	
}
