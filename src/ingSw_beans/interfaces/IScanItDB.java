package ingSw_beans.interfaces;

import java.util.List;

import ingSw_beans.Amministratore;
import ingSw_beans.Dipendente;
import ingSw_beans.Prodotto;

public interface IScanItDB {
    boolean registraDipendente(String username, String password, String nome, String cognome, String dataNascita, String telefono, String indirizzo, String documento);
    boolean cambiaPassword(String username, String nuovaPassword);
    boolean licenziaDipendente(String username);
    void stampaListaUtenti();
    List<Amministratore> listaAmministratori();
    List<Dipendente> listaDipendenti();
    String trovaPassword(String username);
    List<Prodotto> listaProdotti();
}
