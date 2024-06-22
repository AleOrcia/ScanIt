package ingSw_beans;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Resoconto {
	private int id;
	private List<Scansione> scansioni;
	private double prezzoTotale;
	private LocalDate data;
	
	
	public Resoconto(int id, List<Scansione> scansioni, double prezzoTotale, LocalDate data) {
		super();
		this.id = id;
		this.scansioni = scansioni;
		this.prezzoTotale = prezzoTotale;
		this.data = data;
	}
	
	public Resoconto() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Scansione> getScansioni() {
		return scansioni;
	}
	public void setScansioni(List<Scansione> scansioni) {
		this.scansioni = scansioni;
	}
	public double getPrezzoTotale() {
		return prezzoTotale;
	}
	public void setPrezzoTotale(double prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	 public String calcolaGuadagnoTotale() {
	        double guadagnoTotale = 0.0;

	        for (Scansione scansione : getScansioni()) {
	            String idProdotto = scansione.getIdProdotto();
	            ScanItDB db = ScanItDB.getInstance();
	    		Prodotto p = db.getProdottoByID(idProdotto);

	            int quantitaVenduta = scansione.getQuantita();

	            String prezzoString = p.getPrezzo(); 
	            NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
	            double prezzoProdotto = 0.0;
	            try {
	                Number parsed = formatoEuro.parse(prezzoString);
	                prezzoProdotto = parsed.doubleValue();
	            } catch (ParseException e) {
	                e.printStackTrace(); // Gestire l'eccezione in modo appropriato
	            }

	            // Calcolare il guadagno per questa scansione
	            double guadagnoScansione = prezzoProdotto * quantitaVenduta;

	            // Aggiungere al guadagno totale
	            guadagnoTotale += guadagnoScansione;
	        }
	        DecimalFormat df = new DecimalFormat("#.00");

	        // Applicazione del formato al guadagno della giornata
	        String guadagnoFormattato = df.format(guadagnoTotale);

	        return guadagnoFormattato;
	    }
	
}
