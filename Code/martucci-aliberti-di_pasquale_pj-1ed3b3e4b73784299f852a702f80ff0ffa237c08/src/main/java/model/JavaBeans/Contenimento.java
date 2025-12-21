package model.JavaBeans;

public class Contenimento {
    private int codice;
    private int id_ordine;
    private int quantita;
    private String nomeArticolo;
    private double prezzoUnitario;
    private double sconto;

    // Costruttore completo
    public Contenimento(int codice, int id_ordine, int quantita, String nomeArticolo, double prezzoUnitario, double sconto) {
        this.codice = codice;
        this.id_ordine = id_ordine;
        this.quantita = quantita;
        this.nomeArticolo = nomeArticolo;
        this.prezzoUnitario = prezzoUnitario;
        this.sconto = sconto;
    }

    // Costruttore vuoto (necessario per alcune librerie o servlet)
    public Contenimento() {}

    // Getter e Setter
    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public void setId_ordine(int id_ordine) {
        this.id_ordine = id_ordine;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public double getSconto() {
        return sconto;
    }

    public void setSconto(double sconto) {
        this.sconto = sconto;
    }
}

