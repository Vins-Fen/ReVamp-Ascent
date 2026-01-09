package dataAccess.JavaBeans;

import java.util.GregorianCalendar;

public class Ordine {
    private int id_ordine;
    private int num_articoli;
    private GregorianCalendar data;
    private double importo_totale;
    private int id_indirizzo;
    private String nome_utente;

    public Ordine(int id_ordine, int num_articoli, GregorianCalendar data, double importo_totale, int id_indirizzo, String nome_utente) {
        this.id_ordine = id_ordine;
        this.num_articoli = num_articoli;
        this.data = data;
        this.importo_totale = importo_totale;
        this.id_indirizzo = id_indirizzo;
        this.nome_utente = nome_utente;
    }

    // Getter e setter
    public int getId_ordine() {
        return id_ordine;
    }

    public void setId_ordine(int id_ordine) {
        this.id_ordine = id_ordine;
    }

    public int getNum_articoli() {
        return num_articoli;
    }

    public void setNum_articoli(int num_articoli) {
        this.num_articoli = num_articoli;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public double getImporto_totale() {
        return importo_totale;
    }

    public void setImporto_totale(double importo_totale) {
        this.importo_totale = importo_totale;
    }

    public int getId_indirizzo() {
        return id_indirizzo;
    }

    public void setId_indirizzo(int id_indirizzo) {
        this.id_indirizzo = id_indirizzo;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public void setNome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
    }
}
