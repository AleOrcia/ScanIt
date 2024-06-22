function visualizza(index) {
    // Seleziona tutti gli elementi che iniziano con un determinato pattern di ID
    var us = document.querySelectorAll('[id^="username' + index + '"]');
    var id = document.querySelectorAll('[id^="idProdotto' + index + '"]');
    var qnt = document.querySelectorAll('[id^="quantita' + index + '"]');
    var time = document.querySelectorAll('[id^="timestamp' + index + '"]');
    var guadagno = document.querySelectorAll('[id^="guadagno' + index + '"]');

    // Itera su tutti gli elementi selezionati e inverte lo stato di visualizzazione
    us.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    id.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    qnt.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    time.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    guadagno.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    
}

function scarica(index) {
  const id = document.getElementById(`id${index}`).textContent.trim();
  const data = document.getElementById(`data${index}`).textContent.trim();
  const guadagno = document.getElementById(`guadagno${index}`).textContent.split(": ")[1].trim();
  const { jsPDF } = window.jspdf;
  const doc = new jsPDF();

  doc.text(`${id}: Resoconto del ${data}`, 10, 10);
  doc.text(`Guadagno totale in Euro: ${guadagno}`, 10, 20);
  doc.text("SCANSIONI:", 10, 30);

  let yOffset = 40; // Offset per la posizione verticale del testo

  // Ottieni tutte le righe di scansioni per questo resoconto
  const usernameElements = document.querySelectorAll(`tr[id^="username${index}"]`);
  const idProdottoElements = document.querySelectorAll(`tr[id^="idProdotto${index}"]`);
  const quantitaElements = document.querySelectorAll(`tr[id^="quantita${index}"]`);
  const timestampElements = document.querySelectorAll(`tr[id^="timestamp${index}"]`);

  for (let i = 0; i < usernameElements.length; i++) {
    const username = usernameElements[i].querySelector('td:last-child').textContent.trim();
    const idProdotto = idProdottoElements[i].querySelector('td:last-child').textContent.trim();
    const quantita = quantitaElements[i].querySelector('td:last-child').textContent.trim();
    const timestamp = timestampElements[i].querySelector('td:last-child').textContent.trim();

    doc.text(`Username: ${username}`, 10, yOffset);
    yOffset += 10;
    doc.text(`ID Prodotto: ${idProdotto}`, 10, yOffset);
    yOffset += 10;
    doc.text(`Quantità: ${quantita}`, 10, yOffset);
    yOffset += 10;
    doc.text(`Timestamp: ${timestamp}`, 10, yOffset);
    yOffset += 20; // Aggiunge uno spazio tra le scansioni
  }

  doc.save(`resoconto_${data}.pdf`);
}
