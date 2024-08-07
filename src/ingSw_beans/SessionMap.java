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
	private Map<HttpSession, GestoreLog> GSessions;

	public SessionMap() {
		super();
		this.ASessions = new HashMap<HttpSession, Amministratore>();
		this.DSessions = new HashMap<HttpSession, Dipendente>();
		this.GSessions = new HashMap<HttpSession, GestoreLog>();
	}
	
	public Map<HttpSession, Dipendente> getDSessions() {
		return DSessions;
	}
	
	public Map<HttpSession, Amministratore> getASessions() {
		return ASessions;
	}
	
	public Map<HttpSession, GestoreLog> getGSessions() {
		return GSessions;
	}
	
	public String getAdminUsernameFromSessionID(HttpSession s) {
		for(HttpSession h : getASessions().keySet()) {
			if(h.getId().equals(s.getId())) {
				return getASessions().get(h).getUsername();
			}
		}
		return null;
	}
	
}

