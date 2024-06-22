package ingSw_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import ingSw_beans.ScanItDB;


public class AggiornaInventarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

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

		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		req.setCharacterEncoding(StandardCharsets.UTF_8.name());
		
		String ordine = req.getReader().readLine();
		
		boolean check = db.rimuoviOrdine(ordine);
		PrintWriter out = res.getWriter();
		
		if(check) {
			res.setStatus(HttpServletResponse.SC_OK);
			out.print("ok");
			out.flush();
			out.close();
		}else {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("Aggiornamento DB non riuscito");
			out.flush();
			out.close();
		}
	}
	
}
