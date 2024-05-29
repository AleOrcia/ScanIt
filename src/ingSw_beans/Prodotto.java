package ingSw_beans;

public class Prodotto {
	private String id;
	private String nome;
	private String descrizione;
	private double prezzo;
	private int quantita;
	private String idFornitore;
	
	
	public Prodotto(String id, String nome, String descrizione, double prezzo, int quantita, String idFornitore) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantita = quantita;
		this.idFornitore = idFornitore;
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


	public double getPrezzo() {
		return prezzo;
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
	
	
	
	
}
