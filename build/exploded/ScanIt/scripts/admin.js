   function redirect(e){
	   if(e.id === "av"){
		   window.location.href = 'aggiornainventario.jsp';
	   }else if(e.id === "gp"){
		   window.location.href = 'gestionepersonale.jsp';
	   }else if(e.id === "os"){
		   window.location.href = 'ordinascorte.jsp';
	   }else if(e.id === "vr"){
		   window.location.href = 'visualizzaresoconti.jsp';
	   }else if(e.id === "vs"){
		   window.location.href = 'visualizzascorte.jsp';
	   }else if (e.id === "gm"){
		   window.location.href = 'gestionemagazzino.jsp';
	   }
   }
/*   
function showDipendente(button) {
    // Trova il padre tr del bottone
    var row = button.closest('tr');

    // Trova tutte le righe nascoste successive direttamente
    var hiddenRows = Array.from(row.parentElement.children).filter(function(row) {
        return row.classList.contains('hidden-info');
    });

    // Alterna la visibilità di ciascuna riga nascosta
    hiddenRows.forEach(function(hiddenRow) {
        hiddenRow.style.display = (hiddenRow.style.display === 'none' || hiddenRow.style.display === '') ? 'table-row' : 'none';
    });

    // Modifica la classe del triangolo per indicare lo stato attuale di visualizzazione
    button.classList.toggle('triangle-up');
}
*/
function showDipendente(button, index) {

	var indirizzo = document.getElementById('indirizzo'+index);
	var cellulare = document.getElementById('cellulare'+index);
	var data = document.getElementById('data'+index);
	var codice = document.getElementById('codice'+index);
	
	if(indirizzo.style.display != 'none'){
		indirizzo.style.display = 'none';
	}else{
		indirizzo.style.display = 'block';
	}
	
	if(cellulare.style.display != 'none'){
		cellulare.style.display = 'none';
	}else{
		cellulare.style.display = 'block';
	}
	
	if(data.style.display != 'none'){
		data.style.display = 'none';
	}else{
		data.style.display = 'block';
	}
	
	if(codice.style.display != 'none'){
		codice.style.display = 'none';
	}else{
		codice.style.display = 'block';
	}
	
	button.classList.toggle('triangle-up');
}


