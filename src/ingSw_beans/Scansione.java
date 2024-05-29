package ingSw_beans;


public class Scansione {
	
	private String username;
	private String idProdotto;
	private int quantita;
	private long timestamp;

	
	public Scansione(long timestamp, int quantita, String username, String idProdotto) {
		super();
		this.timestamp = timestamp;
		this.quantita = quantita;
		this.username = username;
		this.idProdotto = idProdotto;
	}

	public Scansione() {}

	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public int getQuantita() {
		return quantita;
	}


	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getIdProdotto() {
		return idProdotto;
	}


	public void setIdProdotto(String idProdotto) {
		this.idProdotto = idProdotto;
	}
	
	
	
}
