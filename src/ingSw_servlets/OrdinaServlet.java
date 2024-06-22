package ingSw_servlets;


import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ingSw_beans.Ordine;
import ingSw_beans.Prodotto;
import ingSw_beans.RandomStringGenerator;
import ingSw_beans.ScanItDB;


public class OrdinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static class Data{
		private String id; private String nome; private String idFornitore; private String costo; private String quantita;
	}

	public void init() // Inizializzo eventuali bean di applicazione
	{
		
		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		if(gson == null) {
			gson = new Gson();
			this.getServletContext().setAttribute("gson", gson);
		}
		
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		if(db == null)
		{
			db = new ScanItDB();
			this.getServletContext().setAttribute("db", db);
		}

	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		req.setCharacterEncoding(StandardCharsets.UTF_8.name());
		StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        // Converte il corpo della richiesta in JSON
        String json = sb.toString();
        
        Type listType = new TypeToken<List<Data>>(){}.getType();
        List<Data> carrello = gson.fromJson(json, listType);

        //List<Prodotto> prodotti = new ArrayList<Prodotto>();
        
        Map<Prodotto, Integer> mappa = new HashMap<Prodotto, Integer>();
        
        for(Data d : carrello) {
        	//String id, String nome, String descrizione, double prezzo, int quantita, String idFornitore
        	Prodotto p = db.getProdottoByID(d.id);
        	mappa.put(p, Integer.parseInt(d.quantita));
        }
        
        LocalDate d = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        String formattedDate = d.format(formatter);
        Ordine o = new Ordine(RandomStringGenerator.generateRandomString(), mappa, formattedDate);
        System.out.println("ID Ordine: "+o.getId());
        for(Prodotto p : o.getProdotti().keySet()) {
        	System.out.println(p.toString());
        }
        
        boolean check = db.aggiungiOrdine(o);
        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");
        if(check) {
            res.getWriter().print("ok");

        }else {
        	res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
		
	}
	
}
