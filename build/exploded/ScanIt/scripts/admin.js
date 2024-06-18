   function redirect(e){
	   if(e.id === "ai"){
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

var username = "";
var idx = "";


function licenzia(usr, index){
	username = usr;
	idx = index;
	if (document.getElementById("confermaLicenziamento" + index).innerHTML == ""){
		document.getElementById("confermaLicenziamento" + index).innerHTML += 
			"<button class='px-4 py-2 text-sm font-medium text-white bg-red-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 dark:bg-red-400 dark:hover:bg-red-500 dark:focus:ring-offset-gray-800' onclick='confermaLicenziamento()'>Conferma</button>" + 
			"<button class='px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800' onclick='annullaLicenziamento()'>Annulla</button>";
	
	}
	
}

function annullaLicenziamento(){
	document.getElementById("confermaLicenziamento" + idx).innerHTML = "";
	return;
}

function confermaLicenziamento(){
	fetch("/ScanIt/LicenziaServlet", {
		method: 'POST',
		headers: {'Content-Type': 'text/plain'},
		body: username
	})
	.then(response => {
	    if (!response.ok) {
	        throw new Error("Codice errato!: " + response.status);
	    }
	    return response.text();
	})
	.then(data => {
	    alert("Dipendente licenziato correttamente!");
	    console.log(data);
	    window.location.replace("gestionepersonale.jsp");
	})
	.catch(error => {
	    alert(error.message); 
	    console.error('Errore:', error);
	    document.getElementById('error').style.display = 'block';
	    location.reload();
	});
}

function cambiaSchermata(button){
	button.setAttribute("hidden", "hidden");
	document.getElementById("tabellaDipendenti").setAttribute("hidden", "hidden");
	document.getElementById("tabellaAssunzione").removeAttribute("hidden");
	document.getElementById("h2list").setAttribute("hidden", "hidden");
	document.getElementById("h2new").removeAttribute("hidden");
}

function checkData(){
	const errorMessage = document.getElementById('error-message');
	const button = document.getElementById("submitButton");
	if (document.getElementById("nome").value.trim() == "" ||
		document.getElementById("cognome").value.trim() == "" ||
		document.getElementById("data").value.trim() == "" ||
		document.getElementById("telefono").value.trim() == "" ||
		document.getElementById("indirizzo").value.trim() == "" ||
		document.getElementById("documento").value.trim() == "" ||
		document.getElementById("username").value.trim() == ""){
		errorMessage.classList.remove('hidden');
		button.setAttribute("disabled", "disabled");
		return;

		}
	
	if (!document.getElementById("username").value.startsWith("d") || 
		isNaN(document.getElementById("telefono").value)){
		alert("Inseriti valori errati. Riprovare");
		document.getElementById("nome").value = "";
		document.getElementById("cognome").value = "";
		document.getElementById("data").value = "";
		document.getElementById("telefono").value = "";
		document.getElementById("indirizzo").value = "";
		document.getElementById("documento").value = "";
		document.getElementById("username").value = "";
		errorMessage.classList.remove('hidden');
		button.setAttribute("disabled", "disabled");
		return;
	}else{
		errorMessage.classList.add('hidden');
		button.removeAttribute("disabled");
		return;
	}
	
	
	
}




