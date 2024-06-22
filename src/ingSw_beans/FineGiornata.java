package ingSw_beans;


import java.io.IOException;
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
		try {
			LogController.getInstance().writeLog(new Log("SISTEMA", "Evento fine giornata","Creato resoconto giornaliero con successo", System.currentTimeMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}

    }else {
		try {
			LogController.getInstance().writeLog(new Log("SISTEMA", "Evento fine giornata","Impossibile creare resoconto giornaliero", System.currentTimeMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    db.cancellaResocontoPiuVecchioSeNecessario();
    
  }

}
