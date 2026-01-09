<%--
  Created by IntelliJ IDEA.
  User: fenix
  Date: 14/07/25
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="css/faq.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta charset="UTF-8">
    <title>FAQ - ReVamp Ascent</title>
</head>
<body>
<%-- Include header --%>
<jsp:include page="header.jsp" />

<div class="container-faq">
    <h1>Domande Frequenti (FAQ)</h1>
    <section>
        <h2>Spedizione e Tempi di Consegna</h2>
        <div class="faq-item">
            <h3>In quanto tempo arrivano i mobili?</h3>
            <p>Il tempo di consegna standard è di 2-5 giorni lavorativi dall'evadimento dell'ordine. Riceverai un'email di conferma con il numero di tracking non appena il pacco sarà affidato al corriere.</p>
        </div>
        <div class="faq-item">
            <h3>La spedizione è inclusa nell'acquisto del mobile?</h3>
            <p>Sì, la spedizione standard in Italia è sempre inclusa nel prezzo del mobile. Per spedizioni espresse o internazionali potrebbero essere applicati costi aggiuntivi, indicati nella fase di checkout.</p>
        </div>
    </section>

    <section>
        <h2>Pagamenti e Resi</h2>
        <div class="faq-item">
            <h3>Quali metodi di pagamento accettate?</h3>
            <p>Accettiamo carte di credito (Visa, Mastercard, American Express) bo enifico bancario.</p>
        </div>
        <div class="faq-item">
            <h3>Posso restituire un mobile se non mi soddisfa?</h3>
            <p>Sì, hai 14 giorni di tempo dalla consegna per richiedere il reso. Il mobile dovrà essere restituito nelle condizioni originali con imballaggio integro. </p>
        </div>
    </section>


    <section>
        <h2>Installazione e Garanzia</h2>
        <div class="faq-item">
            <h3>Offrite il servizio di montaggio?</h3>
            <p>No, al momento non offriamo un servizio di montaggio a domicilio. Tuttavia, ogni mobile è fornito con istruzioni dettagliate e tutti gli strumenti necessari per il montaggio fai-da-te.</p>
        </div>
        <div class="faq-item">
            <h3>Che garanzia hanno i vostri prodotti?</h3>
            <p>Tutti i mobili sono coperti da una garanzia di 2 anni contro difetti di fabbricazione. Per attivarla, conserva la ricevuta d'acquisto e contatta il nostro servizio clienti.</p>
        </div>
    </section>

    <section>
        <h2>Domande Generali</h2>
        <div class="faq-item">
            <h3>È possibile modificare un ordine già effettuato?</h3>
            <p>Se l'ordine non è ancora stato evaso, puoi contattarci entro 24 ore dalla conferma per modificare quantità o indirizzo. Dopo l'evasione non è possibile apportare modifiche.</p>
        </div>
        <div class="faq-item">
            <h3>Come posso seguire il mio ordine?</h3>
            <p>Una volta spedito, riceverai un'email con il tracking. Puoi anche accedere al tuo account e consultare la sezione "I miei ordini".</p>
        </div>
    </section>

    <section>
        <h2>Contatti</h2>
        <p>Per ulteriori domande, puoi contattarci:</p>
        <ul>
            <li><strong>Email:</strong> info@revampascent.it</li>
            <li><strong>Telefono:</strong> +39 081 123 4567 (lun-ven 9:00-18:00)</li>
        </ul>
    </section>
</div>

<%-- Include footer --%>
<jsp:include page="footerAreaUtente.jsp" />
</body>
</html>
