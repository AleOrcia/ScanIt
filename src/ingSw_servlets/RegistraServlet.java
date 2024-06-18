package ingSw_servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import ingSw_beans.ScanItDB;
import ingSw_beans.SessionMap;

public class RegistraServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() {
		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		if(gson == null) {
			gson = new Gson();
			this.getServletContext().setAttribute("gson", gson);
		}
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		if(sessionMap == null)
		{
			sessionMap = new SessionMap();
			this.getServletContext().setAttribute("sessionMap", sessionMap);
		}

		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		if(db == null)
		{
			db = new ScanItDB();
			this.getServletContext().setAttribute("db", db);
		}
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
        // Leggi i parametri dal form
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String data = req.getParameter("data");
        String telefono = req.getParameter("telefono");
        String indirizzo = req.getParameter("indirizzo");
        String documento = req.getParameter("documento");
        String username = req.getParameter("username");
		
        //Dipendente d = new Dipendente(nome, cognome, data, telefono, indirizzo, documento, username);
        boolean check = db.registraDipendente(username, username, nome, cognome, data, telefono, indirizzo, documento);
        		
		if (check) {
			
			res.sendRedirect("admin_pages/gestionepersonale.jsp");
		}
		else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.getWriter().write("error");
			res.getWriter().flush();
			res.getWriter().close();
		}
		//System.out.println(nome + cognome + data + telefono + indirizzo + documento + username);
		/*
		
		//UserDb userDb = (UserDb) this.getServletContext().getAttribute("userDb");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		UserDb userDb = (UserDb) this.getServletContext().getAttribute("userDb");
		
		Dipendente d = gson.fromJson(req.getReader().readLine(), Dipendente.class);
		System.out.println("TUTTO OK SERVLET");
		//db.stampaListaUtenti();
		//boolean check = db.registraDipendente(d.getUsername(), d.getUsername(), d.getNome(), d.getCognome(), d.getDataNascita(), d.getNumeroTelefono(), d.getIndirizzo(), d.getIdDocumento());
		userDb.getDipendenti().add(d);
		for(Dipendente dip : userDb.getDipendenti()) {
			System.out.println(dip.getUsername());
		}
		this.getServletContext().setAttribute("userDb", userDb);
		
		res.setCharacterEncoding("UTF-8");
		boolean check = true;
		if (check) {
			res.setStatus(HttpServletResponse.SC_OK);
			res.getWriter().write("ok");
			res.getWriter().flush();
			res.getWriter().close();
		}
		else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.getWriter().write("error");
			res.getWriter().flush();
			res.getWriter().close();
		}
		*/
	}

}
