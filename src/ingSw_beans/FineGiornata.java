package ingSw_beans;


import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;


public class FineGiornata extends TimerTask {

  public void run() {
    ScanItDB db = ScanItDB.getInstance();
    LocalDate currentDate = LocalDate.now();
    List<Scansione> scansioni = db.getScansioniFromData(currentDate);
    boolean check = db.aggiungiResocontoGiornaliero(currentDate, scansioni);
    if(check) {
        LogController.getInstance().writeLog(new Log("SISTEMA", "Evento fine giornata","Creato resoconto giornaliero con successo", System.currentTimeMillis()));

    }else {
        LogController.getInstance().writeLog(new Log("SISTEMA", "Evento fine giornata","Impossibile creare resoconto giornaliero", System.currentTimeMillis()));

    }
    db.cancellaResocontoPiuVecchioSeNecessario();

  }

}