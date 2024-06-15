package ingSw_beans.interfaces;

public interface IScanItDB {
    void registraDipente(String username, String password, String nome, String cognome, String dataNascita, String telefono, String indirizzo, String codiceFiscale);
    boolean cambiaPassword(String username, String nuovaPassword);
    void licenziaDipendente(String username);
    void listaUtenti();
}
