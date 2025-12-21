package model.JavaBeans;

public class Indirizzo {
    private int id_indirizzo;
    private String via;
    private String citta;
    private String provincia;
    private String cap;
    private String paese;
    private boolean preferito;
    private String nome_utente;

    public Indirizzo(int id_indirizzo, String via, String citta, String provincia, String cap, String paese, boolean preferito, String nome_utente) {
        this.id_indirizzo = id_indirizzo;
        this.via = via;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.paese = paese;
        this.preferito = preferito;
        this.nome_utente = nome_utente;
    }

    public int getId_indirizzo() {
        return id_indirizzo;
    }

    public void setId_indirizzo(int id_indirizzo) {
        this.id_indirizzo = id_indirizzo;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public boolean isPreferito() {
        return preferito;
    }

    public void setPreferito(boolean preferito) {
        this.preferito = preferito;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public void setNome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
    }
}

