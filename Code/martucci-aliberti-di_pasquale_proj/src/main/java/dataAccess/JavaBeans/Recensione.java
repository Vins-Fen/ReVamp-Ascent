package dataAccess.JavaBeans;

import java.util.GregorianCalendar;

public class Recensione {
    private int id;
    private int valutazione;
    private String titolo;
    private String testo;
    private GregorianCalendar data;
    private int codice; // codice articolo
    private String nome_utente;

    // Costruttore completo
    public Recensione(int id, int valutazione, String titolo, String testo, GregorianCalendar data, int codice, String nome_utente) {
        this.id = id;
        setValutazione(valutazione); // uso il setter per applicare il controllo
        this.titolo = titolo;
        this.testo = testo;
        this.data = data;
        this.codice = codice;
        this.nome_utente = nome_utente;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 1 e 5.");
        }
        this.valutazione = valutazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public void setNome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
    }
}




