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