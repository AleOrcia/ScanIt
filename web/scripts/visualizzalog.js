function visualizza(index) {
    // Seleziona tutti gli elementi che iniziano con un determinato pattern di ID
    var us = document.querySelectorAll('[id^="username' + index + '"]');
    var op = document.querySelectorAll('[id^="operazione' + index + '"]');
    var es = document.querySelectorAll('[id^="esito' + index + '"]');
    var tm = document.querySelectorAll('[id^="timestamp' + index + '"]');

    // Itera su tutti gli elementi selezionati e inverte lo stato di visualizzazione
    us.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    op.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    es.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    tm.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    
}