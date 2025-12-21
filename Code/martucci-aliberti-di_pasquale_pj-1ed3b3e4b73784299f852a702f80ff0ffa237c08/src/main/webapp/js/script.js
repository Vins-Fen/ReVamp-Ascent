//serve per eseguire queste funzioni solo dopo che il DOM è stato caricato
document.addEventListener("DOMContentLoaded", () => {
    inizializzaLoginForm();
    inizializzaFormModificaCredenziali();
    inizialiizaFormRegistrazione();
    aggiornaListaMetodiPagamento();
});
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
    const email = document.getElementById('email').value.trim();
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const confpass = document.getElementById('confpass').value.trim();

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

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






