package ingSw_servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ingSw_beans.Amministratore;
import ingSw_beans.Attore;
import ingSw_beans.Dipendente;
import ingSw_beans.SessionMap;
import ingSw_beans.UserDb;

import java.util.Timer;
import java.util.TimerTask;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() // Inizializzo eventuali bean di applicazione
	{
		UserDb userDb = (UserDb) this.getServletContext().getAttribute("userDb");
		if(userDb == null)
		{
			userDb = new UserDb();
			this.getServletContext().setAttribute("userDb", userDb);
		}
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		if(sessionMap == null)
		{
			sessionMap = new SessionMap();
			this.getServletContext().setAttribute("sessionMap", sessionMap);
		}
		Timer timer = (Timer) this.getServletContext().getAttribute("timer");
		if (timer == null) {
			timer = new Timer();
			this.getServletContext().setAttribute("timer", timer);
		}
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(true); // Recupero session	
		UserDb userDb = (UserDb) this.getServletContext().getAttribute("userDb"); // Recupero database utenti
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		Timer timer = (Timer) this.getServletContext().getAttribute("timer");
		String username = req.getParameter("username");
		String pw = req.getParameter("password");
		
		Attore attore = userDb.access(username, pw);
					
		if (attore.equals(Attore.AMMINISTRATORE)) {			
			
		    Amministratore a = userDb.getAmministratoreFromUsername(username); // Recupero specifico user		
			if(!sessionMap.getASessions().containsKey(session)) {
				sessionMap.getASessions().put(session, a); 
			}
			startSessionTimeoutAmministratore(timer, session, 30 * 60 * 1000, sessionMap, a);
			this.getServletContext().setAttribute("sessionMap", sessionMap);
			res.sendRedirect("admin_pages/admin.jsp"); // Pagina per l'admin
			
			
		}else if(attore.equals(Attore.DIPENDENTE)) {
			
			Dipendente d = userDb.getDipendenteFromUsername(username);
			if(!sessionMap.getDSessions().containsKey(session)) {
				sessionMap.getDSessions().put(session, d); // Se non è presente inizializzo un nuovo counter a 1
			}
			startSessionTimeoutDipendente(timer, session, 30 * 60 * 1000, sessionMap, d);
			this.getServletContext().setAttribute("sessionMap", sessionMap);
			res.sendRedirect("scan.jsp");
		}else {
            res.sendRedirect("login.html?loginFailed=true");
		}
	}
	
	private void startSessionTimeoutAmministratore(Timer timer, HttpSession session, long timeout, SessionMap sessionMap, Amministratore a) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidateSessionAmministratore(sessionMap, session, a);
            }
        }, timeout);
    }
	
	private void startSessionTimeoutDipendente(Timer timer, HttpSession session, long timeout, SessionMap sessionMap, Dipendente d) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidateSessionDipendente(sessionMap, session, d);
            }
        }, timeout);
    }
	
	private void invalidateSessionAmministratore(SessionMap sessionMap, HttpSession session, Amministratore a) {
		try {
            session.invalidate(); 
        }catch (IllegalStateException e) {
        	System.err.println("Sessione già invalidata: " + e.getMessage());
        }
    }
	
	private void invalidateSessionDipendente(SessionMap sessionMap, HttpSession session, Dipendente d) {
		try {
            session.invalidate(); 
        }catch (IllegalStateException e) {
        	System.err.println("Sessione già invalidata: " + e.getMessage());
        }
    }
}
