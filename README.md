# ReVamp-Ascent
**ReVamp Ascent** è una piattaforma e-commerce per la vendita di arredi e design, sviluppata in **Java** e progettata secondo una **Three-Tier Architecture (3 livelli)**, in modo da separare chiaramente presentazione, logica applicativa e accesso ai dati.
Questa repository contiene **sia l’implementazione completa** del sistema sia **l’intera documentazione di progetto**, utile per ricostruire con precisione come è stato analizzato, progettato, realizzato e testato l’e-commerce.

### Architettura a 3 livelli

* **Presentation Tier**

  * Interfaccia utente web (JSP/HTML/CSS/JS) e pagine di navigazione
* **Application / Business Tier**

  * Gestione della logica applicativa (Servlet/Controller, servizi, validazioni, workflow: login, catalogo, carrello, checkout, ordini, ecc.)
* **Data Tier**

  * Persistenza e accesso ai dati (DAO, query SQL, schema relazionale MySQL, vincoli, mapping logico)

### Contenuti della repository

* **Codice sorgente**

  * UI web, backend Java, layer di accesso al DB, configurazioni, script SQL
* **Documentazione completa**

  * Requisiti (funzionali/non funzionali, priorità, vincoli)
  * Use Case Specification
  * Diagrammi UML (Use Case, Sequence, Class, Activity, Deployment, ecc.)
  * Documenti di design/architettura (decomposizione, responsabilità dei tier, scelte progettuali, pattern se adottati)
  * Test Plan + Test Case Specification (strategie, coperture, test di unità/integrazione/sistema e regressione)
* **Setup**

  * Istruzioni per esecuzione in locale (Tomcat + DB) e linee guida per import IDE

### Obiettivo

Fornire un progetto **tracciabile e verificabile**, in cui la documentazione mostra in modo esplicito **come il sistema è costruito e come i tre livelli collaborano**, dai requisiti fino al codice e al piano di test.

