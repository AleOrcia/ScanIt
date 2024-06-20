package ingSw_beans;

import java.sql.Connection;
import ingSw_beans.interfaces.IScanItDB;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScanItDB implements IScanItDB{
	private static final String DB_URL = "jdbc:sqlite:" + "C:\\Users\\aleor\\eclipse-workspace\\IngSW\\ScanIt\\web\\DBs\\ScanItDB.db";
    private Connection connection;

    public ScanItDB() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //SEZIONE PERSONALE

    public boolean registraDipendente(String username, String password, String nome, String cognome, String dataNascita, String telefono, String indirizzo, String documento) {
        String insertSQL = "INSERT INTO utenti (username, password, nome, cognome, data_nascita, telefono, indirizzo, documento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setString(4, cognome);
            preparedStatement.setString(5, dataNascita);
            preparedStatement.setString(6, telefono);
            preparedStatement.setString(7, indirizzo);
            preparedStatement.setString(8, documento);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Attore access(String username, String password) {
        String selectSQL = "SELECT * FROM utenti WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Ottieni l'username dal risultato del database
                    String dbUsername = resultSet.getString("username");
                    
                    // Determina l'attore in base all'username
                    if (dbUsername.startsWith("d")) {
                        return Attore.DIPENDENTE;
                    } else if (dbUsername.startsWith("a")) {
                        return Attore.AMMINISTRATORE;
                    } else {
                        return Attore.NONE;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Attore.NONE;
    }


    public boolean cambiaPassword(String username, String nuovaPassword) {
        String updateSQL = "UPDATE utenti SET password = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, nuovaPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean licenziaDipendente(String username) {
        String deleteSQL = "DELETE FROM utenti WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stampaListaUtenti() {
        String selectSQL = "SELECT * FROM utenti";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getString("username"));
                System.out.println("Nome: " + resultSet.getString("nome"));
                System.out.println("Cognome: " + resultSet.getString("cognome"));
                System.out.println("Data di Nascita: " + resultSet.getString("data_nascita"));
                System.out.println("Telefono: " + resultSet.getString("telefono"));
                System.out.println("Indirizzo: " + resultSet.getString("indirizzo"));
                System.out.println("Id documento: " + resultSet.getString("documento"));
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Dipendente> listaDipendenti() {
    	List<Dipendente> dipendenti = new ArrayList<Dipendente>();
    	String selectSQL = "SELECT * FROM utenti";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
            	if (resultSet.getString("username").startsWith("d")) {
            		Dipendente d = new Dipendente(resultSet.getString("nome"), 
            				resultSet.getString("cognome"), resultSet.getString("data_nascita"),
            				resultSet.getString("telefono"), resultSet.getString("indirizzo"),
            				resultSet.getString("documento"), resultSet.getString("username"));
            		dipendenti.add(d);
            	}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dipendenti;
    }
    
    public List<Amministratore> listaAmministratori() {
    	List<Amministratore> amministratori = new ArrayList<Amministratore>();
    	String selectSQL = "SELECT * FROM utenti";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
            	if (resultSet.getString("username").startsWith("a")) {
            		Amministratore a = new Amministratore(resultSet.getString("nome"), 
            				resultSet.getString("cognome"), resultSet.getString("username"));
            		amministratori.add(a);
            	}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amministratori;
    }
    
    public String trovaPassword(String username) {
    	String pwd = null;
        String selectSQL = "SELECT password FROM utenti WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            pwd = (String) result.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pwd;
    }
    
	public List<Dipendente> getDipendenti() {
		List<Dipendente> dipendenti = new ArrayList<Dipendente>();
    	String selectSQL = "SELECT * FROM utenti WHERE username LIKE 'd%'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {	
            	dipendenti.add(new Dipendente(resultSet.getString("nome"), 
        				resultSet.getString("cognome"), resultSet.getString("data_nascita"),
        				resultSet.getString("telefono"), resultSet.getString("indirizzo"),
        				resultSet.getString("documento"), resultSet.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dipendenti;
	}
	
	public List<Amministratore> getAmministratori() {
		List<Amministratore> amministratori = new ArrayList<Amministratore>();
    	String selectSQL = "SELECT * FROM utenti WHERE username LIKE 'a%'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {	
            	amministratori.add(new Amministratore(resultSet.getString("nome"), 
        				resultSet.getString("cognome"), resultSet.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amministratori;
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
	
	public boolean isInDB(String username) {
		for (Dipendente d : getDipendenti()) {
			if(d.getUsername().equals(username)) {
				return true;
			}
		}
		for(Amministratore a : getAmministratori()) {
			if(a.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}    
    
    
    
    
    
    
    
    
    
    
    
    
//SEZIONE PRODOTTI
    
    public List<Prodotto> listaProdotti(){
    	List<Prodotto> prodotti = new ArrayList<Prodotto>();
    	String selectSQL = "SELECT * FROM prodotti";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
            	Prodotto p = new Prodotto(resultSet.getString("id"), resultSet.getString("nome"),
            			resultSet.getString("descrizione"), resultSet.getDouble("prezzo"), 
            			resultSet.getInt("quantita"), resultSet.getString("idFornitore"));
            	prodotti.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodotti;
    }
    
    private Prodotto getProdotto(String id) {
    	for (Prodotto p : listaProdotti()) {
    		if (p.getId().equals(id))
    			return p;
    	}
    	return null;
    }
    
    public boolean aggiungiScansione(String username, String idProdotto, String quantita, String timestamp) {
        String sql = "INSERT INTO scansioni(username, id_prodotto, quantita, timestamp) VALUES(?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, username);
        	statement.setString(2, idProdotto);
        	statement.setString(3, quantita);
        	statement.setString(4, timestamp);
        	statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean decrementaQuantita(String idProdotto, int decremento) {
        String selectSql = "SELECT quantita FROM prodotti WHERE id = ?";
        String updateSql = "UPDATE prodotti SET quantita = quantita - ? WHERE id = ?";

        try (
             PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Verifica la quantità attuale
            selectStmt.setString(1, idProdotto);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int quantitaAttuale = rs.getInt("quantita");

                // Controlla se la quantità è sufficiente
                if (quantitaAttuale >= decremento) {
                    // Aggiorna la quantità
                    updateStmt.setInt(1, decremento);
                    updateStmt.setString(2, idProdotto);
                    updateStmt.executeUpdate();
                    return true;
                } else {
                    System.out.println("Errore: Quantità insufficiente per decrementare.");
                    return false;
                }
            } else {
                System.out.println("Errore: Prodotto non trovato.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
//SEZIONE ORDINI
    public List<Ordine> listaOrdini(){
    	List<Ordine> ordini = new ArrayList<Ordine>();
    	String selectSQL = "SELECT * FROM ordini";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
            	String idProdotti[] = resultSet.getString("prodotti").split(",");
            	List<Prodotto> prodotti = new ArrayList<Prodotto>();
            	for (String id : idProdotti) {
            		if (getProdotto(id) != null) {
                		prodotti.add(getProdotto(id));
                	}
            	}
            	
            	ordini.add(new Ordine(resultSet.getString("id"), prodotti));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }
    
    
    public List<Fornitore> listaFornitori(){
    	List<Fornitore> fornitori = new ArrayList<Fornitore>();
    	String selectSQL = "SELECT * FROM fornitori";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
        	
            while (resultSet.next()) {
            	String id = resultSet.getString("id");
            	String idFornitore = resultSet.getString("idFornitore");
            	double costo = resultSet.getDouble("costo");
            	fornitori.add(new Fornitore(id, idFornitore, costo));
            }
            	
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fornitori;
    }
    

}
