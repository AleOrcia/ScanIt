// Definisci un array per tenere traccia degli elementi nel carrello
var carrello = [];

function add(index) {
    var idFornitore = document.getElementById("idFornitore" + index).innerText;
    var id = document.getElementById("id" + index).innerText;
    var costo = document.getElementById("costo" + index).innerText;

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
            idFornitore: idFornitore,
            costo: costo,
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
                    "<td class='border-b p-2'>" + item.idFornitore + "</td>" +
                    "<td class='border-b p-2'>" + item.id + "</td>" +
                    "<td class='border-b p-2'>" + item.costo + "</td>" +
                    "<td class='border-b p-2'><input type='text' size='1' value='" + item.quantita + "' readonly></td>" +
                  "</tr>";
        tbody.innerHTML += row;
    }

    // Aggiorna il costo totale visualizzato
    var costoTotaleElement = document.getElementById("costoTotale");
    costoTotaleElement.innerText = costoTotale.toFixed(2); // Formatta il costo totale a due decimali
}

