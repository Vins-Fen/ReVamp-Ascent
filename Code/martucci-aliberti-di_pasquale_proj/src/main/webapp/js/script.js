//serve per eseguire queste funzioni solo dopo che il DOM è stato caricato
document.addEventListener("DOMContentLoaded", () => {
    inizializzaLoginForm();
    inizializzaFormModificaCredenziali();
    inizialiizaFormRegistrazione();
    inizializzaFormNuovoIndirizzo();
    inizializzaGestionePagamenti()
    if (typeof aggiornaListaMetodiPagamento === "function") {
        aggiornaListaMetodiPagamento();
    }});
// === VALIDAZIONE FORM LOGIN ===
function inizializzaLoginForm() {
    const form = document.getElementById('formLogin');
    if (form) {
        form.addEventListener("submit", function (event) {
            if (!validazioneCampiLogin()) {
                event.preventDefault();
                mostraErroreLogin();
            } else {
                nascondiErroreLogin();
            }
        });
    }
}

function validazioneCampiLogin() {
    const username = document.getElementById('username')?.value.trim();
    const password = document.getElementById('password')?.value.trim();
    return username !== "" && password !== "";
}

function mostraErroreLogin() {
    const errore = document.getElementById('errorLogin');
    if (errore) {
        errore.style.display = "inline";
    }
}

function nascondiErroreLogin() {
    const errore = document.getElementById('errorLogin');
    if (errore) {
        errore.style.display = "none";
    }
}

// === VALIDAZIONE FORM MODIFICA CREDENZIALI ===
function inizializzaFormModificaCredenziali() {
    const formModifica = document.getElementById('formModificaCredenziali');
    if (formModifica) {
        formModifica.addEventListener("submit", function (event) {
            if (!validazioneModificaCredenziali()) {
                event.preventDefault();
            }
        });
    }
}
function validazioneModificaCredenziali() {
    const username = document.getElementById('username').value.trim();
    const oldpass = document.getElementById('oldpass').value.trim();
    const newpass = document.getElementById('newpass').value.trim();
    const confpass = document.getElementById('confpass').value.trim();

    if (username === '') {
        alert('Username non può essere vuoto');
        return false;
    }
    if (oldpass === '') {
        alert('Inserisci la password attuale');
        return false;
    }

    // Se l'utente ha inserito una nuova password, allora validiamola
    if (newpass !== '') {
        if (newpass.length < 3 || !(/[a-zA-Z]/.test(newpass)) || !(/\d/.test(newpass))) {
            alert('La password deve contenere almeno 3 caratteri, inclusa almeno una lettera e un numero');
            return false;
        }
        if (newpass !== confpass) {
            alert('Le password nuove non corrispondono');
            return false;
        }
    }

    return true;
}



// === CHIUSURA MESSAGGIO SERVER ===
function nascondiMessaggio() {
    const container = document.querySelector('.containerMessaggio');
    if (container) {
        container.style.display = 'none';
    }
}

function confermaCambioPreferito() {
    return confirm("Sei sicuro di voler cambiare l'indirizzo preferito?");
}



function inizialiizaFormRegistrazione(){
    const form=document.getElementById('formRegistrazione');
    if(form){
        form.addEventListener("submit",function (event){
            if(!validazioneFormRegistrazione()){
                event.preventDefault();  //blocca l invio del form
            }
        });
    }
}


function validazioneFormRegistrazione() {
    // Recupero valori (ho aggiunto nome e cognome)
    const nome = document.getElementById('nome').value.trim();
    const cognome = document.getElementById('cognome').value.trim();
    const email = document.getElementById('email').value.trim();
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const confpass = document.getElementById('confpass').value.trim();

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // --- CONTROLLI LUNGHEZZA MASSIMA ---

    // NUOVO: Controllo Nome e Cognome
    if (nome.length > 30) {
        alert("Il nome è troppo lungo (massimo 30 caratteri).");
        return false;
    }
    if (cognome.length > 30) {
        alert("Il cognome è troppo lungo (massimo 30 caratteri).");
        return false;
    }

    if (username.length > 30) { // Username a 20
        alert("L'username è troppo lungo (massimo 30 caratteri).");
        return false;
    }

    if (email.length > 30) { // Email a 25
        alert("L'email è troppo lunga (massimo 30 caratteri).");
        return false;
    }

    if (password.length > 30) {
        alert("La password è troppo lunga (massimo 30 caratteri).");
        return false;
    }
    // ------------------------------------------

    if (username === '') {
        alert('Username obbligatorio');
        return false;
    }

    if (!emailRegex.test(email)) {
        alert('Inserisci un\'email valida');
        return false;
    }

    if (password.length < 3 || !(/[a-zA-Z]/.test(password)) || !(/\d/.test(password))) {
        alert('La password deve contenere almeno 3 caratteri, inclusa una lettera e un numero');
        return false;
    }

    if (password !== confpass) {
        alert('Le password non corrispondono');
        return false;
    }

    return true;
}
function inizializzaFormNuovoIndirizzo() {
    const form = document.getElementById('form_nuovoIndirizzo');
    if (!form) return;

    const inputVia = document.getElementById('via');
    const inputCitta = document.getElementById('citta');
    const inputCap = document.getElementById('cap');
    const inputProvincia = document.getElementById('provincia');
    const inputPaese = document.getElementById('paese');

    // Funzioni helper interne
    const bloccaNumeri = (e) => { e.target.value = e.target.value.replace(/[0-9]/g, ''); };

    // Non taglia più la stringa, pulisce solo i caratteri non validi
    const bloccaLettere = (e) => {
        e.target.value = e.target.value.replace(/\D/g, '');
    };

    const formattaProvincia = (e) => {
        // UpperCase automatico
        e.target.value = e.target.value.replace(/[0-9]/g, '').toUpperCase();
    };

    // Assegnazione Eventi "Live"
    if(inputCitta) inputCitta.addEventListener('input', bloccaNumeri);
    if(inputPaese) inputPaese.addEventListener('input', bloccaNumeri);
    if(inputCap) inputCap.addEventListener('input', bloccaLettere);
    if(inputProvincia) inputProvincia.addEventListener('input', formattaProvincia);

    // Validazione al Submit
    form.addEventListener('submit', function(event) {
        let errori = [];

        // Controlli lunghezza massima (AGGIORNATI)
        if (inputVia && inputVia.value.length > 30) {  // VIA a 30
            errori.push("La via inserita è troppo lunga (massimo 30 caratteri).");
        }
        if (inputCitta && inputCitta.value.length > 30) {
            errori.push("Il nome della città è troppo lungo (massimo 30 caratteri).");
        }
        if (inputPaese && inputPaese.value.length > 30) {
            errori.push("Il nome del paese è troppo lungo (massimo 30 caratteri).");
        }

        // Controlli lunghezza esatta
        if (inputCap && inputCap.value.length !== 5) {
            errori.push("Il CAP deve essere composto da 5 cifre esatte.");
        }
        if (inputProvincia && inputProvincia.value.length !== 2) {
            errori.push("La provincia deve essere di 2 lettere (es. RM).");
        }

        if (errori.length > 0) {
            event.preventDefault();
            alert("Attenzione:\n- " + errori.join("\n- "));
        }
    });
}

function mostraErroreIndirizzo() {
    const errore = document.getElementById('errorIndirizzo');
    if (errore) {
        errore.style.display = "inline";
    }
}

function nascondiErroreIndirizzo() {
    const errore = document.getElementById('errorIndirizzo');
    if (errore) {
        errore.style.display = "none";
    }
}


function eliminaMetodoPagamento(numCarta) {
    if (!confirm('Sei sicuro di voler eliminare questo metodo di pagamento?')) return;

    fetch('RimuoviMetodoPagamentoAjax', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'numcarta=' + encodeURIComponent(numCarta) + '&nome_utente=' + encodeURIComponent(nomeUtente)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                mostraMessaggio('Metodo di pagamento eliminato!', 'successo');
                aggiornaListaMetodiPagamento();
            } else {
                mostraMessaggio('Errore: ' + (data.message || 'Impossibile eliminare il metodo'), 'errore');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            mostraMessaggio('Errore di comunicazione col server', 'errore');
        });
}

function aggiornaListaMetodiPagamento() {
    const container = document.getElementById('listaMetodiPagamento');
    if(!container) return;

    fetch('ListaMetodiPagamentoAjax?nome_utente=' + encodeURIComponent(nomeUtente))
        .then(response => response.text())
        .then(html => {
            container.innerHTML = html;
        })
        .catch(error => {
            console.error('Error:', error);
            mostraMessaggio('Errore nel caricamento dei metodi di pagamento', 'errore');
        });
}

function inizializzaGestionePagamenti() {
    const formPagamento = document.getElementById("formAggiungiPagamento");
    if (formPagamento) {
        formPagamento.addEventListener('submit', function(event) {
            event.preventDefault();

            const numCarta = this.numcarta.value.trim();
            const scadenza = this.scadenza.value.trim();
            const cvv = this.CVV.value.trim();

            if (!validaNumeroCarta(numCarta)) {
                mostraMessaggio('Numero di carta non valido: devono essere 16 cifre.', 'errore');
                return;
            }

            if (!validaScadenza(scadenza)) {
                mostraMessaggio('Data di scadenza non valida o già passata.', 'errore');
                return;
            }

            if (!validaCVV(cvv)) {
                mostraMessaggio('Il CVV deve essere composto da 3 cifre.', 'errore');
                return;
            }

            const data = {
                numcarta: numCarta,
                scadenza: scadenza,
                proprietario: this.proprietario.value.trim(),
                nome_utente: nomeUtente
            };

            fetch('InserisciMetodoPagamentoAjax', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams(data).toString()
            })
                .then(response => {
                    if (!response.ok) throw new Error('Errore nella risposta del server');
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        mostraMessaggio('Metodo di pagamento aggiunto con successo!', 'successo');
                        aggiornaListaMetodiPagamento();
                        this.reset();
                    } else {
                        mostraMessaggio('Errore: ' + (data.message || 'Impossibile aggiungere il metodo'), 'errore');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    mostraMessaggio('Errore nel server: ' + error.message, 'errore');
                });
        });
    }

    const inputCarta = document.getElementById("numcarta");
    if(inputCarta) {
        inputCarta.addEventListener("input", function (e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 16) value = value.slice(0, 16);
            let formatted = value.match(/.{1,4}/g);
            e.target.value = formatted ? formatted.join(' ') : '';
        });
    }

    const inputCVV = document.getElementById("numcvv");
    if(inputCVV) {
        inputCVV.addEventListener("input", function (e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 3) value = value.slice(0, 3);
            e.target.value = value;
        });
    }
}





