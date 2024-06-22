package ingSw_beans;


import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;


public class FineGiornata extends TimerTask {

  public void run() {
	ScanItDB db = ScanItDB.getInstance();
	LocalDate currentDate = LocalDate.now();
	List<Scansione> scansioni = db.getScansioniFromData(currentDate);
    db.aggiungiResocontoGiornaliero(currentDate, scansioni);
    db.cancellaResocontoPiuVecchioSeNecessario();
    
  }

}
