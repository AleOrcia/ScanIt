package ingSw_servlets;

import java.io.IOException; 
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import ingSw_beans.ScanItDB;
import ingSw_beans.SessionMap;

public class ChangePWServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static class OTP{
		private String otp1; private String otp2; private String otp3; private String otp4; private String otp5;
	}

	public void init() // Inizializzo eventuali bean di applicazione
	{
		
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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		String username = req.getParameter("username");
		String nuovaPassword = req.getParameter("nuovapassword");
		String confermaNuovaPassword = req.getParameter("confermanuovapassword");

		boolean check = db.isInDB(username);
		
		if(check && nuovaPassword.equals(confermaNuovaPassword)) {
			this.getServletContext().setAttribute("np", nuovaPassword);
			this.getServletContext().setAttribute("u", username);
            res.sendRedirect("changepw.html?OTP=true");

		}else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Imposta lo stato della risposta a 401 (Non autorizzato)
			out.print("Impossibile cambiare password!");
			out.flush();
			out.close();
		}
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		PrintWriter out = res.getWriter();
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		
		OTP otp = gson.fromJson(req.getReader().readLine(), OTP.class);
		//Controlli per correttezza OTP
		//...
		
		String nuovaPassword = (String) this.getServletContext().getAttribute("np");
		String username = (String) this.getServletContext().getAttribute("u");
		
		boolean check = db.cambiaPassword(username, nuovaPassword);
		if(check) {
			out.print("OK!");
			out.flush();
			out.close();
		}else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Imposta lo stato della risposta a 401 (Non autorizzato)
			out.print("Impossibile cambiare la password!");
			out.flush();
			out.close();
		}
	}

}
