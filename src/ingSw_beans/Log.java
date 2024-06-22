package ingSw_beans;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	private String username;
	private String operazione;
	private String esito;
	private long timestamp;
	
	public Log(String username, String operazione, String esito, long timestamp) {
		super();
		this.username = username;
		this.operazione = operazione;
		this.esito = esito;
		this.timestamp = timestamp;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOperazione() {
		return operazione;
	}
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
		return dateFormat.format(this.timestamp);
	}
	
	public String getTimestampAMD() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // Parsare la stringa in un oggetto LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(getTimestamp(), formatter);

        // Creare un nuovo formatter per il formato desiderato "yyyy-MM-dd"
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formattare LocalDateTime in una stringa nel formato desiderato
        String formattedDate = dateTime.format(newFormatter);
        return formattedDate;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return getUsername()+"|"+getOperazione()+"|"+getEsito()+"|"+getTimestamp();
	}
	
	
}
