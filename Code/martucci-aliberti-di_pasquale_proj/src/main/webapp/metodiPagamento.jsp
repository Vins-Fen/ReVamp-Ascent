<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 26/06/2025
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dataAccess.JavaBeans.Cliente" %>
<html>
<head>
    <title>I Tuoi Metodi Di Pagamento</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .messaggio {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            display: none;
        }
        .messaggio.successo {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .messaggio.errore {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .contnuovopag{
            margin-bottom: 150px;
        }
        .iconaMenuadd_Btn{
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            white-space: nowrap;

        }

    </style>
</head>
<body>
<%
    Cliente u = (Cliente) session.getAttribute("utenteLoggato");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<jsp:include page="header.jsp" />
<div id="messaggio" class="messaggio" style="display:none;"></div>

<div id="listaMetodiPagamento">
    <jsp:include page="listaMetodiPagamento.jsp" />
</div>

<div class="contnuovopag">
    <h3>Aggiungi un nuovo metodo di pagamento</h3>
    <form id="formAggiungiPagamento">
        <div class="contenitore">
            <div class="immagine1">
                <img src="img/logo.webp" class="immagine">
                <img src="img/chip.png" class="immagine">
            </div>
            <div class="daticarta">
                <label>Numero di carta</label><br>
                <input type="text" name="numcarta" id="numcarta" placeholder="1234 1234 1234 1234" class="dat" required><br>
                <label>Scadenza (MM/YY)</label><br>
                <input type="text" name="scadenza" placeholder="MM/YY" class="dat" required id="scadenzacarta"><br>
                <label>Intestatario</label><br>
                <input type="text" name="proprietario" placeholder="Intestatario" class="dat" required><br>
                <label>CVV <small></small></label><br>
                <input type="password" name="CVV" id="numcvv" placeholder="***" class="dat" required><br>
                <button type="submit" class="iconaMenu_addBtn">Aggiungi</button>
            </div>
        </div>
    </form>
</div>

<jsp:include page="footerAreaUtente.jsp" />

<script>
    const nomeUtente = "<%= u.getNomeUtente() %>";

    function mostraMessaggio(msg, tipo) {
        const messaggio = document.getElementById('messaggio');
        messaggio.innerText = msg;                  //aggiorna il testo
        messaggio.className = 'messaggio ' + tipo;
        messaggio.style.display = 'block';
        setTimeout(() => messaggio.style.display = 'none', 4000);  //dopo 4 secondi lo nasconde
    }

    function validaNumeroCarta(numero) {
        const numeroPulito = numero.replace(/\s+/g, '');
        const soloNumeri = /^\d{16}$/;
        return soloNumeri.test(numeroPulito);
    }

    function validaCVV(cvv) {
        return /^\d{3}$/.test(cvv);
    }

    function validaScadenza(scadenza) {
        const regex = /^(0[1-9]|1[0-2])\/\d{2}$/;
        if (!regex.test(scadenza)) return false;

        const [mese, anno] = scadenza.split('/').map(Number);  //Divide la stringa scadenza (es. "09/26") in due parti, poi li converte in numeri
        const oggi = new Date();
        const annoCorrente = oggi.getFullYear() % 100;   //Ricava l'anno corrente in formato a due cifre.
        const meseCorrente = oggi.getMonth() + 1;  //getMonth() restituisce un valore da 0 (gennaio) a 11 (dicembre), quindi bisogna sommare +1.


        return (anno > annoCorrente) || (anno === annoCorrente && mese >= meseCorrente);
    }

    function eliminaMetodoPagamento(numCarta) {
        if (!confirm('Sei sicuro di voler eliminare questo metodo di pagamento?')) return;  //Mostra un popup di conferma all'utente.


        fetch('RimuoviMetodoPagamentoAjax', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },  //indica come saranno codificati i dati nel body
            body: 'numcarta=' + encodeURIComponent(numCarta) + '&nome_utente=' + encodeURIComponent(nomeUtente) //Crea il corpo della richiesta POST
        })
        .then(response => response.json()) //Quando il server risponde, tenta di convertire la risposta da JSON a oggetto JavaScript.
                .then(data => { if (data.success) {  //data è l oggetto javascript che rappresenta la risposta del server
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
        fetch('ListaMetodiPagamentoAjax?nome_utente=' + encodeURIComponent(nomeUtente))
        .then(response => response.text())
        .then(html => {
            document.getElementById('listaMetodiPagamento').innerHTML = html;  //inserisce il codice html ricevuto nel form con quel id. aggiorna i metodi
        })
        .catch(error => {
            console.error('Error:', error);
            mostraMessaggio('Errore nel caricamento dei metodi di pagamento', 'errore');
        });
    }

    document.getElementById("formAggiungiPagamento").addEventListener('submit', function(event) {
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
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(data).toString()  //converte data in formato compatibile
        })
        .then(response => {
            if (!response.ok) {     //200
                throw new Error('Errore nella risposta del server');
            }
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

    document.getElementById("numcarta").addEventListener("input", function (e) {
        let value = e.target.value.replace(/\D/g, '');  //Rimuove tutti i caratteri non numerici
        if (value.length > 16) value = value.slice(0, 16); //Limita l'input a massimo 16 cifre
        let formatted = value.match(/.{1,4}/g);  //Divide le cifre in gruppi di 4
        e.target.value = formatted ? formatted.join(' ') : ''; //Se formatted non è nul unisce i gruppi con uno spazio tra di loro
    });

    document.getElementById("numcvv").addEventListener("input", function (e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 3) value = value.slice(0, 3);  //Verifica se l’utente ha digitato più di 3 caratteri.Se sì, taglia la stringa per prendere solo i primi 3 caratteri
        e.target.value = value;  //Aggiorna il contenuto del campo input con il valore eventualmente troncato, così da mostrare all’utente solo le prime 3 cifre.

    });
</script>
</body>
</html>
