package ingSw_beans;

import java.util.List;

public class Ordine {
	private String id;
	private List<Prodotto> prodotti;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Prodotto> getProdotti() {
		return prodotti;
	}
	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	public Ordine(String id, List<Prodotto> prodotti) {
		super();
		this.id = id;
		this.prodotti = prodotti;
	}
	
}
