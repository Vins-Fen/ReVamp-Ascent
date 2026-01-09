package dataAccess.JavaBeans;

public class Cliente{
    private String nome_utente;
    private String pass;
    private String nome;
    private String cognome;
    private String email;
    private String sesso;
    private int eta;
    private String numTelefono;


    public Cliente(String nome_utente, String pass, String nome, String cognome, String email, String sesso, int eta, String numTelefono) {
        this.nome_utente = nome_utente;
        this.pass = pass;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.sesso = sesso;
        this.eta = eta;
        this.numTelefono = numTelefono;
    }


    public String getNomeUtente() {
        return nome_utente;
    }
    public void setNomeUtente(String nomeUtente) {
        this.nome_utente = nomeUtente;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSesso() {
        return sesso;
    }
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public int getEta() {
        return eta;
    }
    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getNumTelefono() {
        return numTelefono;
    }
    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }
}
