package ingSw_beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class ScanItDB {
	private static final String DB_URL = "jdbc:sqlite:" + "C:\\Users\\aleor\\eclipse-workspace\\IngSW\\ScanIt\\web\\DBs\\ScanItDB.db";
	private static ScanItDB instance = null;
    private static Connection connection;

    public static ScanItDB getInstance() {
        if (instance == null) {
            instance = new ScanItDB();
        }
        return instance;
    }
    
    protected ScanItDB() {
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
    
    /*
    private void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

    
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
                    } else if (dbUsername.startsWith("g")){
                        return Attore.GESTORELOG;
                    }else {
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
	
	public List<GestoreLog> getGestoriLog() {
		List<GestoreLog> gestoriLog = new ArrayList<GestoreLog>();
    	String selectSQL = "SELECT * FROM utenti WHERE username LIKE 'g%'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {	
            	gestoriLog.add(new GestoreLog(resultSet.getString("nome"), 
        				resultSet.getString("cognome"), resultSet.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gestoriLog;
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
	
	public GestoreLog getGestoreLogFromUsername(String username)
	{
		for(GestoreLog g : getGestoriLog())
		{
			if(g.getUsername().equals(username))
				return g;
		}
		return null;
	}
	
	public  boolean isInDB(String username) {
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
    
    public  List<Prodotto> listaProdotti(){
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
    
    private  Prodotto getProdotto(String id) {
    	for (Prodotto p : listaProdotti()) {
    		if (p.getId().equals(id))
    			return p;
    	}
    	return null;
    }
    
    public  boolean aggiungiScansione(String username, String idProdotto, String quantita, String timestamp) {
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
    public List<Ordine> listaOrdini() {
        List<Ordine> ordini = new ArrayList<>();

        String selectSQL = "SELECT * FROM ordini";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String idOrdine = resultSet.getString("id");
                String dataOrdine = resultSet.getString("data");

                Map<Prodotto, Integer> prodotti = new HashMap<>();

                // Query per recuperare le righe dell'ordine dalla tabella righe_ordine
                String selectRigheSQL = "SELECT id_prodotto, quantita FROM righe_ordine WHERE id_ordine = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectRigheSQL)) {
                    preparedStatement.setString(1, idOrdine);
                    ResultSet righeResultSet = preparedStatement.executeQuery();

                    while (righeResultSet.next()) {
                        String idProdotto = righeResultSet.getString("id_prodotto");
                        int quantita = righeResultSet.getInt("quantita");

                        Prodotto prodotto = getProdotto(idProdotto); // Metodo per recuperare il prodotto dal DB
                        if (prodotto != null) {
                            prodotti.put(prodotto, quantita);
                        }
                    }
                }

                Ordine ordine = new Ordine(idOrdine, prodotti, dataOrdine);
                ordini.add(ordine);
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
    
    public boolean aggiungiOrdine(Ordine ordine) {
        String insertOrdineSQL = "INSERT INTO ordini (id, data) VALUES (?, ?)";
        String insertRigheOrdineSQL = "INSERT INTO righe_ordine (id_ordine, id_prodotto, quantita) VALUES (?, ?, ?)";

        try (PreparedStatement insertOrdineStmt = connection.prepareStatement(insertOrdineSQL);
             PreparedStatement insertRigheOrdineStmt = connection.prepareStatement(insertRigheOrdineSQL)) {

            // Inserimento dell'ordine nella tabella ordini
            insertOrdineStmt.setString(1, ordine.getId());
            insertOrdineStmt.setString(2, ordine.getData());
            insertOrdineStmt.executeUpdate();

            // Inserimento delle righe ordine nella tabella righe_ordine
            for (Map.Entry<Prodotto, Integer> entry : ordine.getProdotti().entrySet()) {
                Prodotto prodotto = entry.getKey();
                int quantita = entry.getValue();

                insertRigheOrdineStmt.setString(1, ordine.getId());
                insertRigheOrdineStmt.setString(2, prodotto.getId());
                insertRigheOrdineStmt.setInt(3, quantita);
                insertRigheOrdineStmt.executeUpdate();
            }

            return true; // Operazione completata con successo
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean rimuoviOrdine(String ordineId) {
        String selectRigheOrdineSQL = "SELECT id_prodotto, quantita FROM righe_ordine WHERE id_ordine = ?";
        String deleteRigheOrdineSQL = "DELETE FROM righe_ordine WHERE id_ordine = ?";
        String deleteOrdineSQL = "DELETE FROM ordini WHERE id = ?";
        String updateProdottoSQL = "UPDATE prodotti SET quantita = quantita + ? WHERE id = ?";

        try (PreparedStatement selectRigheOrdineStmt = connection.prepareStatement(selectRigheOrdineSQL);
             PreparedStatement deleteRigheOrdineStmt = connection.prepareStatement(deleteRigheOrdineSQL);
             PreparedStatement deleteOrdineStmt = connection.prepareStatement(deleteOrdineSQL);
             PreparedStatement updateProdottoStmt = connection.prepareStatement(updateProdottoSQL)) {

            // Recupero delle righe ordine per ottenere i prodotti e le quantità
            selectRigheOrdineStmt.setString(1, ordineId);
            ResultSet resultSet = selectRigheOrdineStmt.executeQuery();
            Map<String, Integer> prodottiQuantita = new HashMap<>();

            while (resultSet.next()) {
                String idProdotto = resultSet.getString("id_prodotto");
                int quantita = resultSet.getInt("quantita");
                prodottiQuantita.put(idProdotto, quantita);
            }

            // Rimozione delle righe ordine dalla tabella righe_ordine
            deleteRigheOrdineStmt.setString(1, ordineId);
            deleteRigheOrdineStmt.executeUpdate();

            // Rimozione dell'ordine dalla tabella ordini
            deleteOrdineStmt.setString(1, ordineId);
            int rowsAffected = deleteOrdineStmt.executeUpdate();

            // Aggiornamento delle quantità dei prodotti nel database
            for (Map.Entry<String, Integer> entry : prodottiQuantita.entrySet()) {
                String idProdotto = entry.getKey();
                int quantita = entry.getValue();
                updateProdottoStmt.setInt(1, quantita);
                updateProdottoStmt.setString(2, idProdotto);
                updateProdottoStmt.executeUpdate();
            }

            return rowsAffected > 0; // Operazione completata con successo se almeno una riga è stata rimossa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    public int getQuantitaOrdinataFromOrdine(String idOrdine, String idProdotto) {
        int quantitaOrdinata = 0;
        String selectSQL = "SELECT quantita FROM righe_ordine WHERE id_ordine = ? AND id_prodotto = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, idOrdine);
            preparedStatement.setString(2, idProdotto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                quantitaOrdinata = resultSet.getInt("quantita");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantitaOrdinata;
    }


    
    public String getNomeProdottoFromFornitori(String id) {
    	String nome = null;
    	String selectSQL = "SELECT p.nome FROM fornitori f JOIN prodotti p ON f.id = p.id WHERE f.id = ?";
    	
    	try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)){
    			preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
           	
               if (resultSet.next()) {
               		nome = resultSet.getString("nome");	
               }
               	
               
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return nome;
    }
    
    public Prodotto getProdottoByID(String id) {
        Prodotto p = null;
        String selectSQL = "SELECT * FROM prodotti WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                p = new Prodotto(
                    resultSet.getString("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("descrizione"),
                    resultSet.getDouble("prezzo"),
                    resultSet.getInt("quantita"),
                    resultSet.getString("idFornitore")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }




//SEZIONE RESOCONTI
    
    public List<Resoconto> listaResoconti() {
        List<Resoconto> resoconti = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM resoconti")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (rs.next()) {
                int id = rs.getInt("id");
                String dataStr = rs.getString("data");
                LocalDate data = LocalDate.parse(dataStr, formatter); // Conversione da stringa a LocalDate

                String scansioniJson = rs.getString("scansioni");
                List<Scansione> scansioni = convertiJsonInScansioni(scansioniJson);

                Resoconto resoconto = new Resoconto();
                resoconto.setId(id);
                resoconto.setData(data);
                resoconto.setScansioni(scansioni);

                resoconti.add(resoconto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resoconti;
    }


	public boolean aggiungiResocontoGiornaliero(LocalDate dataGiorno, List<Scansione> scansioniDelGiorno) {
	    String insertResocontoSQL = "INSERT INTO resoconti (data, scansioni) VALUES (?, ?)";
	
	    try (PreparedStatement insertResocontoStmt = connection.prepareStatement(insertResocontoSQL)) {
	        // Converti la lista di scansioni in una rappresentazione JSON o strutturata per il database
	        String scansioniJson = convertiScansioniInJson(scansioniDelGiorno);
	
	        insertResocontoStmt.setString(1, dataGiorno.toString());
	        insertResocontoStmt.setString(2, scansioniJson);
	        insertResocontoStmt.executeUpdate();
	
	        return true; // Operazione completata con successo
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	private String convertiScansioniInJson(List<Scansione> scansioni) {
	    // Esempio di conversione in JSON utilizzando Gson
	    Gson gson = new Gson();
	    return gson.toJson(scansioni);
	}
	
	public List<Scansione> getScansioniPerGiornoDaResoconto(LocalDate dataGiorno) {
	    String selectScansioniSQL = "SELECT scansioni FROM resoconti WHERE data = ?";

	    try (PreparedStatement selectScansioniStmt = connection.prepareStatement(selectScansioniSQL)) {
	        selectScansioniStmt.setString(1, dataGiorno.toString());
	        ResultSet resultSet = selectScansioniStmt.executeQuery();

	        // Processa il risultato per recuperare le scansioni
	        List<Scansione> scansioni = new ArrayList<>();
	        while (resultSet.next()) {
	            String scansioniJson = resultSet.getString("scansioni");
	            List<Scansione> scansioniDelGiorno = convertiJsonInScansioni(scansioniJson);
	            scansioni.addAll(scansioniDelGiorno);
	        }

	        return scansioni;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
	 public List<Scansione> getScansioniFromData(LocalDate dataCercata) {
	        List<Scansione> scansioni = new ArrayList<>();
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {

	            String query = "SELECT username, id_prodotto, quantita, timestamp FROM scansioni WHERE DATE(timestamp) = ?";
	            stmt = connection.prepareStatement(query);
	            stmt.setString(1, dataCercata.toString());

	            // Esecuzione della query
	            rs = stmt.executeQuery();

	            // Iterazione sui risultati e creazione degli oggetti Scansione
	            while (rs.next()) {
	                String username = rs.getString("username");
	                String id = rs.getString("id_prodotto");
	                int quantita = rs.getInt("quantita");
	                String timestampStr = rs.getString("timestamp");
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
	                long timestamp = dateFormat.parse(timestampStr).getTime();
	                

	                Scansione scansione = new Scansione(timestamp, quantita, username, id);
	                scansioni.add(scansione);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (ParseException e2) {
	        	e2.printStackTrace();
	        }

	        return scansioni;
	    }


	private List<Scansione> convertiJsonInScansioni(String scansioniJson) {
	    Gson gson = new Gson();
	    Type listType = new TypeToken<List<Scansione>>() {}.getType();
	    return gson.fromJson(scansioniJson, listType);
	}

	
	
	public List<Scansione> listaScansioni() {
        List<Scansione> scansioni = new ArrayList<>();

        // Connessione al database
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM scansioni");
             ResultSet rs = stmt.executeQuery()) {

            // Iterazione sui risultati della query
            while (rs.next()) {
                // Leggi i valori dal ResultSet
                String username = rs.getString("username");
                String idProdotto = rs.getString("id_prodotto");
                int quantita = rs.getInt("quantita");
                String timestampStr = rs.getString("timestamp");

                // Converti il timestamp dalla stringa al formato long
                long timestamp = parseTimestamp(timestampStr);

                // Crea l'oggetto Scansione e aggiungilo alla lista
                Scansione scansione = new Scansione(timestamp, quantita, username, idProdotto);
                scansioni.add(scansione);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scansioni;
    }
	
	private long parseTimestamp(String timestampStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");

        try {
            // Parsifica la stringa di timestamp nel formato specificato
            return dateFormat.parse(timestampStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Gestione dell'errore, eventualmente altro comportamento
        }
    }
	
	
	public void cancellaResocontoPiuVecchioSeNecessario() {
        String countQuery = "SELECT COUNT(*) AS count FROM resoconti";
        String deleteQuery = "DELETE FROM resoconti WHERE id = (SELECT id FROM resoconti ORDER BY data ASC LIMIT 1)";

        try (PreparedStatement countStmt = connection.prepareStatement(countQuery);
            PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)
        ) {
            // Esegui la query per contare il numero di resoconti
            ResultSet rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("count");
            }

            // Se ci sono più di 30 resoconti, cancella il più vecchio
            if (count > 30) {
                deleteStmt.executeUpdate();
                System.out.println("Resoconto più vecchio cancellato con successo.");
            } else {
                System.out.println("Non sono presenti più di 30 resoconti, nessuna cancellazione necessaria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }    
  }
	public void writeLog(Log log) {


        // SQL per l'inserimento del log
        String sql = "INSERT INTO log (username, operazione, esito, timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            // Imposta i parametri nello statement SQL
            stmt.setString(1, log.getUsername());
            stmt.setString(2, log.getOperazione());
            stmt.setString(3, log.getEsito());
            stmt.setString(4, log.getTimestamp());

            // Esegue l'inserimento
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Log inserito correttamente nel database.");
            } else {
                System.out.println("Errore durante l'inserimento del log nel database.");
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL: " + e.getMessage());
        }
    }

}