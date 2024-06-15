package ingSw_beans;

import java.sql.Connection;
import ingSw_beans.interfaces.IScanItDB;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    public void registraDipente(String username, String password, String nome, String cognome, String dataNascita, String telefono, String indirizzo, String codiceFiscale) {
        String insertSQL = "INSERT INTO utenti (username, password, nome, cognome, data_nascita, telefono, indirizzo, codice_fiscale) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setString(4, cognome);
            preparedStatement.setString(5, dataNascita);
            preparedStatement.setString(6, telefono);
            preparedStatement.setString(7, indirizzo);
            preparedStatement.setString(8, codiceFiscale);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void licenziaDipendente(String username) {
        String deleteSQL = "DELETE FROM utenti WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listaUtenti() {
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
                System.out.println("Codice Fiscale: " + resultSet.getString("codice_fiscale"));
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
