var carrello = [];

function add(index) {
    var idFornitore = document.getElementById("idFornitore" + index).innerText;
    var id = document.getElementById("id" + index).innerText;
    var costo = document.getElementById("costo" + index).innerText;
    var nome = document.getElementById("nome" + index).innerText;

    // Rimuovi simbolo di valuta, separatore delle migliaia e sostituisci virgola decimale con punto
    costo = costo.replace(/\./g, '').replace(',', '.').replace(/[^\d.-]/g, '');

    // Verifica se il prodotto è già nel carrello
    var trovato = false;
    for (var i = 0; i < carrello.length; i++) {
        if (carrello[i].id === id) {
            carrello[i].quantita++; // Incrementa la quantità
            trovato = true;
            break;
        }
    }

    // Se il prodotto non è stato trovato, aggiungilo al carrello
    if (!trovato) {
        carrello.push({
            id: id,
            nome: nome,
            idFornitore: idFornitore,
            costo: parseFloat(costo), // Converte il costo in numero
            quantita: 1
        });
    }

    // Aggiorna la visualizzazione del carrello
    aggiornaCarrello();
}

function aggiornaCarrello() {
    var tbody = document.getElementById("corpoTabellaCarrello");
    tbody.innerHTML = ""; // Pulisce solo il tbody

    var costoTotale = 0;

    // Ricostruisce solo il tbody con i nuovi dati
    for (var i = 0; i < carrello.length; i++) {
        var item = carrello[i];
        var costoProdotto = parseFloat(item.costo) * item.quantita; // Calcola il costo totale per il prodotto
        costoTotale += costoProdotto; // Aggiunge al costo totale

        var row = "<tr>" +
                    "<td class='border-b p-2'>" + item.nome + "</td>" +
                    "<td class='border-b p-2'>" + item.id + "</td>" +
                    "<td class='border-b p-2'>" + item.idFornitore + "</td>" +
                    "<td class='border-b p-2'>" + item.costo + "</td>" +
                    "<td class='border-b p-2'><input type='text' size='1' value='" + item.quantita + "' onchange='aggiornaQuantita(" + i + ", this.value)'></td>" +
                  "</tr>";
        tbody.innerHTML += row;
    }

    // Aggiorna il costo totale visualizzato
    var costoTotaleElement = document.getElementById("costoTotale");
    costoTotaleElement.innerText = costoTotale.toFixed(2); // Formatta il costo totale a due decimali
}

function aggiornaQuantita(index, quantita) {
    quantita = parseInt(quantita);
    if (quantita <= 0) {
        carrello.splice(index, 1); // Rimuove l'elemento dal carrello se la quantità è zero o meno
    } else {
        carrello[index].quantita = quantita;
    }
    aggiornaCarrello();
}




function cerca() {
            var nomeInput = document.getElementById("nomeInput").value.toLowerCase();
            var idInput = document.getElementById("idInput").value.toLowerCase();
            var idFornitoreInput = document.getElementById("idFornitoreInput").value.toLowerCase();
            var table = document.getElementById("tabellaFornitori");
            var tr = table.getElementsByTagName("tr");

            for (var i = 2; i < tr.length; i++) { // Start from 2 to skip header rows
                var nomeTd = tr[i].getElementsByTagName("td")[2];
                var idTd = tr[i].getElementsByTagName("td")[1];
                var idFornitoreTd = tr[i].getElementsByTagName("td")[0];
                if (nomeTd && idTd && idFornitoreTd) {
                    var nomeValue = nomeTd.textContent || nomeTd.innerText;
                    var idValue = idTd.textContent || idTd.innerText;
                    var idFornitoreValue = idFornitoreTd.textContent || idFornitoreTd.innerText;

                    if (nomeValue.toLowerCase().indexOf(nomeInput) > -1 &&
                        idValue.toLowerCase().indexOf(idInput) > -1 &&
                        idFornitoreValue.toLowerCase().indexOf(idFornitoreInput) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }       
            }
        }
        
        
        
function invia() {
	console.log(carrello)
    fetch('/ScanIt/OrdinaServlet', { // Sostituisci 'tuoURLServlet' con l'URL reale della tua servlet
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(carrello)
    })
    .then(response => {
		if (!response.ok) {
    	  	throw new Error("Codice errato!: " + response.status);
        }
       	return response.text();
		})
    .then(data => {
        console.log('Success:', data);
        alert("Ordine avvenuto con successo!");
        window.location.reload();
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
