package ingSw_servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import ingSw_beans.Dipendente;
import ingSw_beans.Log;
import ingSw_beans.LogController;
import ingSw_beans.ScanItDB;
import ingSw_beans.Scansione;
import ingSw_beans.SessionMap;


public class BarcodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

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
			db = ScanItDB.getInstance();
			this.getServletContext().setAttribute("db", db);
		}
		
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Gson gson = (Gson) this.getServletContext().getAttribute("gson");
		SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
		ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
		Timer timer = (Timer) this.getServletContext().getAttribute("timer");
		
		Scansione scansione = gson.fromJson(req.getReader().readLine(), Scansione.class);
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
		Date date = new Date(scansione.getTimestamp());
        String formattedTimestamp = f.format(date);
        
		PrintWriter out = res.getWriter();
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");

		
		HttpSession session = req.getSession(false);
		
		if(session == null) {
			
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Imposta lo stato della risposta a 401 (Non autorizzato)
			out.print("Sessione scaduta!");
			out.flush();
			out.close();		
		}else {
			
			timer.cancel();
			timer = new Timer();
            this.getServletContext().setAttribute("timer", timer);
            
            
            
            Dipendente dipendente = sessionMap.getDSessions().get(session);
			startSessionTimeoutDipendente(timer, session, 30 * 60 * 1000, sessionMap, dipendente);
			boolean checkScan = false;
			boolean checkDec = false;
			synchronized (this) {
	            checkScan = db.aggiungiScansione(dipendente.getUsername(), scansione.getIdProdotto(), String.valueOf(scansione.getQuantita()), formattedTimestamp);
	            checkDec = db.decrementaQuantita(scansione.getIdProdotto(), scansione.getQuantita());
	        }
			
			if(checkScan && checkDec) {
				LogController.getInstance().writeLog(new Log(dipendente.getUsername(),"Scansione","Scansione eseguita correttamente",System.currentTimeMillis()));
				out.print("ID: "+scansione.getIdProdotto()+"\nQuantità: "+ scansione.getQuantita()+"\nTimestamp: "+scansione.getTimestamp()+ "\nUsername: " + scansione.getUsername()+"\n\n");
				out.flush();
				out.close();
			}else {
				LogController.getInstance().writeLog(new Log(dipendente.getUsername(),"Scansione","Scansione fallita",System.currentTimeMillis()));
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Imposta lo stato della risposta a 500
				out.print("Fallimento nell'aggiunta al Database!");
				out.flush();
				out.close();
			}
			
		}
		
	}
	
	private void startSessionTimeoutDipendente(Timer timer, HttpSession session, long timeout, SessionMap sessionMap, Dipendente d) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidateSessionDipendente(sessionMap, session, d);
            }
        }, timeout);
    }
	

	private void invalidateSessionDipendente(SessionMap sessionMap, HttpSession session, Dipendente d) {
        sessionMap.getDSessions().remove(session);
        
        try {
            session.invalidate(); 
        }catch (IllegalStateException e) {
        	System.err.println("Sessione già invalidata: " + e.getMessage());
        }
    }
}
