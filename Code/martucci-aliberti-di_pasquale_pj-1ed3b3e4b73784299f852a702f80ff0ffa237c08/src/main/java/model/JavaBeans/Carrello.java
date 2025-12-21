package model.JavaBeans;

public class Carrello {
    private int idCarrello;
    private String nomeUtente;
    private int codiceArticolo;
    private int quantita;

    public Carrello(int idCarrello,String nomeUtente, int codiceArticolo, int quantita) {
        this.idCarrello = idCarrello;
        this.nomeUtente = nomeUtente;
        this.codiceArticolo = codiceArticolo;
        this.quantita = quantita;
    }

    public int getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(int idCarrello) {
        this.idCarrello = idCarrello;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public int getCodiceArticolo() {
        return codiceArticolo;
    }

    public void setCodiceArticolo(int codiceArticolo) {
        this.codiceArticolo = codiceArticolo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}