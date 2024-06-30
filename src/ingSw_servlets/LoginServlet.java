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
import ingSw_beans.FineGiornata;
import ingSw_beans.GestoreLog;
import ingSw_beans.Log;
import ingSw_beans.LogController;
import ingSw_beans.ScanItDB;
import ingSw_beans.SessionMap;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() // Inizializzo eventuali bean di applicazione
	{

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
		
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		if(db == null)
		{
			db = ScanItDB.getInstance();
			this.getServletContext().setAttribute("db", db);
		}
		
		//Evento FineGiornata
        Timer fineGiornata = new Timer();
        Calendar giorno = Calendar.getInstance();

        // Configura l'orario per la mezzanotte del giorno successivo
        giorno.set(Calendar.HOUR_OF_DAY, 0);
        giorno.set(Calendar.MINUTE, 0);
        giorno.set(Calendar.SECOND, 0);
        giorno.set(Calendar.MILLISECOND, 0);
        giorno.add(Calendar.DAY_OF_MONTH, 1);

        // Programma il compito per eseguirsi ogni giorno alla mezzanotte
        fineGiornata.scheduleAtFixedRate(new FineGiornata(), giorno.getTime(), 1000 * 60 * 60 * 24);
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(true); // Recupero session	
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		Timer timer = (Timer) this.getServletContext().getAttribute("timer");
		String username = req.getParameter("username");
		String pw = req.getParameter("password");
		Attore attore = db.access(username, pw);
		
		if (attore.equals(Attore.AMMINISTRATORE)) {			
			LogController.getInstance().writeLog(new Log(username,"Login","Login eseguito correttamente",System.currentTimeMillis()));
		    Amministratore a = db.getAmministratoreFromUsername(username);
			if(!sessionMap.getASessions().containsKey(session)) {
				sessionMap.getASessions().put(session, a); 
			}
			startSessionTimeoutAmministratore(timer, session, 30 * 60 * 1000, sessionMap, a);
			this.getServletContext().setAttribute("sessionMap", sessionMap);
			res.sendRedirect("admin_pages/admin.jsp"); // Pagina per l'admin
			
			
		}else if(attore.equals(Attore.DIPENDENTE)) {
			LogController.getInstance().writeLog(new Log(username,"Login","Login eseguito correttamente",System.currentTimeMillis()));
			Dipendente d = db.getDipendenteFromUsername(username);
			if(!sessionMap.getDSessions().containsKey(session)) {
				sessionMap.getDSessions().put(session, d); 
			}
			startSessionTimeoutDipendente(timer, session, 30 * 60 * 1000, sessionMap, d);
			this.getServletContext().setAttribute("sessionMap", sessionMap);
			
			if(d.getUsername().equals(db.trovaPassword(d.getUsername()))) {
				res.sendRedirect("changepw.html");
			}else {
				res.sendRedirect("scan.jsp");
			}
			
		}else if(attore.equals(Attore.GESTORELOG)){
			LogController.getInstance().writeLog(new Log(username,"Login","Login eseguito correttamente",System.currentTimeMillis()));
            
			GestoreLog g = db.getGestoreLogFromUsername(username);
			if(!sessionMap.getGSessions().containsKey(session)) {
				sessionMap.getGSessions().put(session, g); 
			}
			
			res.sendRedirect("visualizzalog.jsp");
		}else {
			LogController.getInstance().writeLog(new Log(username,"Login","Login errato",System.currentTimeMillis()));
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
