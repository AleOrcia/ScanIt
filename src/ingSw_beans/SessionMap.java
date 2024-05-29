package ingSw_beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

// VERSIONE <User, AtomicInteger> e List<LocalDateTime>
// con getDateInMins()
public class SessionMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<HttpSession, Dipendente> DSessions; // Sessioni attive: utente + numero richieste
	private Map<HttpSession, Amministratore> ASessions;
	//private List<LocalDateTime> date; // Timestamps delle richieste

	public SessionMap() {
		super();
		this.ASessions = new HashMap<HttpSession, Amministratore>();
		this.DSessions = new HashMap<HttpSession, Dipendente>();
		//this.date = new ArrayList<LocalDateTime>();
	}
	
	public Map<HttpSession, Dipendente> getDSessions() {
		return DSessions;
	}
	
	public Map<HttpSession, Amministratore> getASessions() {
		return ASessions;
	}
	
}

