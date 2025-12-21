package model.JavaBeans;

public class ImmagineArticolo {
    private int id_immagine;
    private int codice_articolo;
    private String url;
    private boolean is_principale;
    private String descrizione;

    public ImmagineArticolo(int id_immagine, int codice_articolo, String url, boolean is_principale, String descrizione) {
        this.id_immagine = id_immagine;
        this.codice_articolo = codice_articolo;
        this.url = url;
        this.is_principale = is_principale;
        this.descrizione = descrizione;
    }

    public ImmagineArticolo() {
    }

    public int getId_immagine() {
        return id_immagine;
    }


    public void setId_immagine(int id_immagine) {
        this.id_immagine = id_immagine;
    }

    public int getCodice_articolo() {
        return codice_articolo;
    }

    public void setCodice_articolo(int codice_articolo) {
        this.codice_articolo = codice_articolo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_principale() {
        return is_principale;
    }

    public void setIs_principale(boolean is_principale) {
        this.is_principale = is_principale;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}

