package ingSw_beans;

import java.util.Map;

public class Ordine {
	private String id;
	//private List<Prodotto> prodotti;
	private Map<Prodotto, Integer> prodotti;
	private String data;

   
	
	public String getData() {
		return this.data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*
	public List<Prodotto> getProdotti() {
		return prodotti;
	}
	
	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	*/
	
	public Map<Prodotto, Integer> getProdotti() {
		return prodotti;
	}
	
	public void setProdotti(Map<Prodotto, Integer> prodotti) {
		this.prodotti = prodotti;
	}
	
	public Ordine(String id, Map<Prodotto, Integer> prodotti, String data) {
		super();
		this.id = id;
		this.prodotti = prodotti;
		this.data = data;
	}
	
}
