function visualizza(index) {
    // Seleziona tutti gli elementi che iniziano con un determinato pattern di ID
    var idP = document.querySelectorAll('[id^="idProdotto' + index + '"]');
    var nP = document.querySelectorAll('[id^="nomeProdotto' + index + '"]');
    var dP = document.querySelectorAll('[id^="descrizioneProdotto' + index + '"]');
    var idF = document.querySelectorAll('[id^="idFornitore' + index + '"]');
    var qP = document.querySelectorAll('[id^="quantitaProdotto' + index + '"]');

    // Itera su tutti gli elementi selezionati e inverte lo stato di visualizzazione
    idP.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    nP.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    dP.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    idF.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
    qP.forEach(function(element) {
        var isHidden = element.style.display === "none";
        element.style.display = isHidden ? "table-row" : "none";
    });
}


function confermaArrivo(index){
	
	var ordine = document.getElementById("idOrdine"+index).textContent;
	
	fetch("/ScanIt/AggiornaInventarioServlet", {
		method: 'POST',
		headers: {'Content-Type': 'text/plain'},
		body: ordine
	})
	.then(response => {
	    if (!response.ok) {
	        throw new Error("Impossibile aggiornare l'inventario!: " + response.status);
	    }
	    return response.text();
	})
	.then(data => {
	    alert("Inventario aggiornato correttamente!");
	    console.log(data);
	    window.location.replace("aggiornainventario.jsp");
	})
	.catch(error => {
	    alert(error.message); 
	    console.error('Errore:', error);
	    document.getElementById('error').style.display = 'block';
	    location.reload();
	});
}