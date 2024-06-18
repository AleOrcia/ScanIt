package ingSw_servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ingSw_beans.Dipendente;
import ingSw_beans.ScanItDB;
import ingSw_beans.SessionMap;

public class LicenziaServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() {
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
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		
		String username = req.getReader().readLine();
		boolean check = db.licenziaDipendente(username);
		PrintWriter out = res.getWriter();
		if (check) {
			Dipendente d = db.getDipendenteFromUsername(username);
			db.getDipendenti().remove(d);
			for (HttpSession h : sessionMap.getDSessions().keySet()) {
				if (sessionMap.getDSessions().get(h).equals(d))
					sessionMap.getDSessions().remove(h);
			}
			
			out.write("ok");
		}
		else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("error");
		}
		out.flush();
		out.close();
		
	}

}
