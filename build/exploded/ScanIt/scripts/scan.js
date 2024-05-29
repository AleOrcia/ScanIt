function scanBarcode() {
	document.documentElement.style.overflow = 'hidden';
    document.body.scroll = 'no';
            Quagga.init({
                inputStream : {
                    name : "Live",
                    type : "LiveStream",
                    target: document.querySelector('#barcode-scanner'),
                    constraints: {
		                width: {min: 640},   // Larghezza del feed video
		                height: {min: 480},  // Altezza del feed video
		                facingMode: "environment", // Utilizza la fotocamera posteriore se disponibile
		                aspectRatio: {min: 1, max: 2}
		            }
                },
                decoder : {
            readers: ["code_128_reader", "ean_reader", "ean_8_reader", "code_39_reader", "code_39_vin_reader", "codabar_reader", "upc_reader", "upc_e_reader", "i2of5_reader"]                }
            }, function(err) {
                if (err) {
                    console.error(err);
                    return
                }
                console.log("Quagga initialization succeeded");
                Quagga.start();
            });

            Quagga.onDetected(function(data) {
                var barcode = data.codeResult.code;
                console.log("Codice a barre scansionato:", barcode);
                Quagga.stop();
                Quagga.offDetected();
                showPrompt(barcode);
            });
        }
        


function sendDataToServer(barcode, quantity) {
	
	var obj = {};
	var timestamp = new Date().getTime();
	obj.username = document.getElementById("username").innerHTML
	obj.idProdotto = barcode;
	obj.quantita = quantity;
	obj.timestamp = timestamp;
	
    fetch('/ScanIt/BarcodeServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(obj)
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 401) {
                throw new Error("Scansione effettuata ma sessione scaduta, rieffettuare il login!");
            } else {
                throw new Error("Errore nella richiesta: " + response.status);
            }
        }
        return response.text();
    })
    .then(data => {
        alert("Scansione effettuata correttamente!");
        console.log(data);
    })
    .catch(error => {
        alert(error.message); 
        console.error('Errore:', error);
        location.reload();
    });
}

function showPrompt(barcode) {
    const readOnlyValue = barcode;
    
    Swal.fire({
        title: 'Inserisci i dati',
        html: `
            <label for="readonlyInput">ID Prodotto:</label>
            <input type="text" id="readonlyInput" readonly value="${readOnlyValue}" class="swal2-input" style="width: 12ch;">
            <br>
            <label for="editableInput">Quantit&agrave:</label>
            <input type="text" id="editableInput" class="swal2-input" style="width: 12ch;">
        `,
        focusConfirm: false,
        preConfirm: () => {
            const editableValue = Swal.getPopup().querySelector('#editableInput').value;
            return { readOnlyValue, editableValue };
        },
        showCancelButton: true,
        confirmButtonText: 'Invia',
        cancelButtonText: 'Annulla',
        customClass: {
            popup: 'prompt-popup-class',
            confirmButton: 'prompt-button-class',
            cancelButton: 'prompt-button-class'
        },
        width: '300px' // Imposta la larghezza desiderata
    }).then((result) => {
		
        if (result.isConfirmed) {
            const inputValue = result.value.editableValue;
            if (inputValue !== null) {
                if (!isNaN(inputValue) && parseInt(inputValue) > 0) {
                    if (parseInt(inputValue) >= 100) {
                        alert("Impossibile inserire valori maggiori di 100 unit&agrave");
                        showPrompt(barcode);
                    } else {
                        console.log("Valore inserito:", inputValue);
                        sendDataToServer(barcode, inputValue);
                    }
                } else {
                    console.log("Nessun valore valido inserito.");
                    alert("Valore non valido. Riprova");
                    showPrompt(barcode);
                }
            } else {
                console.log("Prompt chiuso. Nessun valore inserito.");
                showPrompt(barcode);
            }
            console.log('ID Prodotto:', result.value.readOnlyValue);
            console.log('Quantit&agrave:', result.value.editableValue);
            
        }else{
			alert("Invio annullato!");
		}
    });
}