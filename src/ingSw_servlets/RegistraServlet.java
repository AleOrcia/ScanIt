package ingSw_servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import ingSw_beans.Log;
import ingSw_beans.LogController;
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
			db = ScanItDB.getInstance();
			this.getServletContext().setAttribute("db", db);
		}
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");

        // Leggi i parametri dal form
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String data = req.getParameter("data");
        String telefono = req.getParameter("telefono");
        String indirizzo = req.getParameter("indirizzo");
        String documento = req.getParameter("documento");
        String username = req.getParameter("username");
		
        boolean check = db.registraDipendente(username, username, nome, cognome, data, telefono, indirizzo, documento);
        		
		if (check) {
			LogController.getInstance().writeLog(new Log(sessionMap.getAdminUsernameFromSessionID(req.getSession(false)),"Registrazione","Dipendente "+username+" registrato correttamente",System.currentTimeMillis()));

			res.sendRedirect("admin_pages/gestionepersonale.jsp");
		}
		else {
			LogController.getInstance().writeLog(new Log(sessionMap.getAdminUsernameFromSessionID(req.getSession(false)),"Registrazione","Impossibile registrare il dipendente "+username,System.currentTimeMillis()));

			res.sendRedirect("admin_pages/gestionepersonale.jsp?registrazione=false");
		}
	}

}
