package ingSw_beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;

public class UserDb implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Dipendente> dipendenti;
	private List<Amministratore> amministratori;
	File userDb = new File("C:\\Users\\aleor\\eclipse-workspace\\IngSW\\ScanIt\\web\\DBs\\UserDb.txt");


	public UserDb() {
		super();
		this.dipendenti = new ArrayList<Dipendente>();
		this.amministratori = new ArrayList<Amministratore>();	
		String line;
		String username;
		String[] params;
		Dipendente d = new Dipendente();
		Amministratore a = new Amministratore();

		
		try (BufferedReader br = new BufferedReader(new FileReader(userDb))){
					
		while((line = br.readLine()) != null) {
			//System.out.println("USERDB LINE "+line);
			params = line.split(":");
			//System.out.println("USERDB PARAMS "+params);
			username = params[0];

			if(username.startsWith("d")) {
				d.setUsername(username);
				d.setNome(params[2]);
				d.setCognome(params[3]);
				d.setDataNascita(params[4]);
				d.setNumeroTelefono(params[5]);
				d.setIndirizzo(params[6]);
				d.setIdDocumento(params[7]);
				this.dipendenti.add(d);
				d = new Dipendente();
				
			}else if(username.startsWith("a")) {
				a.setUsername(username);
				a.setNome(params[2]);
				a.setCognome(params[3]);
				this.amministratori.add(a);
				a = new Amministratore();
			}
			
		}
		}catch (FileNotFoundException e){
			System.err.println("File non trovato: " + e.getMessage());
		}catch (IOException e2) {
			System.err.println("Errore di I/O: " + e2.getMessage());
		}
		
	}
	
	public List<Dipendente> getDipendenti() {
		return this.dipendenti;
	}
	
	public List<Amministratore> getAmministratori() {
		return this.amministratori;
	}
	
	public Attore access(String user, String password) // Funzione di login
	{		
		String line;
		String username;
		String[] params;


				try (BufferedReader br = new BufferedReader(new FileReader(userDb))){
					
					while((line = br.readLine()) != null) {
						params = line.split(":");
						username = params[0];
						
						if(username.equals(user) && password.equals(params[1])) {
							if(user.startsWith("d")) {
								return Attore.DIPENDENTE;
							}else if(user.startsWith("a")) {
								return Attore.AMMINISTRATORE;
							}
						}
						
					}
					}catch (FileNotFoundException e){
						System.err.println("File non trovato: " + e.getMessage());
					}catch (IOException e2) {
						System.err.println("Errore di I/O: " + e2.getMessage());
					}
		return Attore.NONE;
	}
	
	public Dipendente getDipendenteFromUsername(String username)
	{
		for(Dipendente d : getDipendenti())
		{
			if(d.getUsername().equals(username)) {
				return d;
			}
		}
		return null;
	}
	
	public Amministratore getAmministratoreFromUsername(String username)
	{
		for(Amministratore a : getAmministratori())
		{
			if(a.getUsername().equals(username))
				return a;
		}
		return null;
	}
	
	public synchronized boolean changePW(Dipendente d, String nuovaPw) {
		List<String> fileContent = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
        try {
            List<String> lines = Files.readAllLines(userDb.toPath());
            for (String line : lines) {
                if (line.startsWith(d.getUsername() + ":")) {
                	String[] parts = line.split(":");
                	
                	int index = parts.length;
                	for(int i = 0; i < index; i++) {
                		if(i==1) {
                			sb.append(nuovaPw);
                			sb.append(":");
                		}else if(i == (index-1)){
                    		sb.append(parts[i]);
                		}else {
                			sb.append(parts[i]);
                			sb.append(":");
                		}
                	}
                	line = sb.toString();
                }
                fileContent.add(line);
            }
            
            Files.write(userDb.toPath(), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
