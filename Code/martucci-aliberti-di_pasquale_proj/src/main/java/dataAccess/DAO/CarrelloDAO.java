package dataAccess.DAO;

import dataAccess.JavaBeans.*;
import dataAccess.ConPool;
import java.sql.*;
import java.util.*;

public class CarrelloDAO {

    public void aggiungiAlCarrello(String nome_utente,int codiceArticolo,int quantita) {
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("INSERT INTO Carrello (nome_utente, codice_articolo, quantita) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantita = quantita + ?");
            ps.setString(1, nome_utente);
            ps.setInt(2, codiceArticolo);
            ps.setInt(3, quantita);
            ps.setInt(4, quantita);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Carrello> getCarrelloByUtente(String nome_utente) {
        List<Carrello> carrello = new ArrayList<>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Carrello WHERE nome_utente = ?");
            ps.setString(1, nome_utente);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Carrello item = new Carrello(
                        rs.getInt("id_carrello"),
                        rs.getString("nome_utente"),
                        rs.getInt("codice_articolo"),
                        rs.getInt("quantita")
                );
                carrello.add(item);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return carrello;
    }

    public void aggiornaQuantita(String nome_utente,int codiceArticolo,int nuovaQuantita){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("UPDATE Carrello SET quantita = ? WHERE nome_utente = ? AND codice_articolo = ?");
            ps.setInt(1, nuovaQuantita);
            ps.setString(2, nome_utente);
            ps.setInt(3,codiceArticolo);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void rimuoviArticoloCarrello(String nome_utente,int codiceArticolo){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("DELETE FROM Carrello WHERE nome_utente = ? AND codice_articolo = ?");
            ps.setString(1, nome_utente);
            ps.setInt(2, codiceArticolo);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void pulisciCarrello(String nome_utente){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("DELETE FROM Carrello WHERE nome_utente = ?");
            ps.setString(1, nome_utente);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int contaArticoliCarrello(String nome_utente){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT COUNT(*) FROM Carrello WHERE nome_utente = ?");
            ps.setString(1, nome_utente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int calcolaQuantitaTotale(String nome_utente){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT SUM (quantita) FROM Carrello WHERE nome_utente = ?");
            ps.setString(1, nome_utente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public double calcolaPrezzoTotale(String nome_utente){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT SUM((a.prezzo - IFNULL(a.sconto,0)) * c.quantita) AS totale FROM Carrello c JOIN Articolo a ON c.codice_articolo = a.codice WHERE c.nome_utente = ?");
            ps.setString(1, nome_utente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("totale");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return 0.0;
    }
}

