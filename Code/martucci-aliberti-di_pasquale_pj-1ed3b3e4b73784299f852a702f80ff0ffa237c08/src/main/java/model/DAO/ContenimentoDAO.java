package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ConPool;
import model.JavaBeans.Contenimento;

public class ContenimentoDAO {

    // Salva una nuova riga ordine con snapshot
    public boolean doSave(Contenimento contenimento) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO contenimento (codice, id_ordine, quantita, nome_articolo, prezzo_unitario, sconto) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, contenimento.getCodice());
            ps.setInt(2, contenimento.getId_ordine());
            ps.setInt(3, contenimento.getQuantita());
            ps.setString(4, contenimento.getNomeArticolo());
            ps.setDouble(5, contenimento.getPrezzoUnitario());
            ps.setDouble(6, contenimento.getSconto());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera tutte le righe ordine di un dato ordine
    public List<Contenimento> doRetrieveByOrdine(int id_ordine) {
        List<Contenimento> contenuti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM contenimento WHERE id_ordine = ?"
            );
            ps.setInt(1, id_ordine);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contenimento c = new Contenimento();
                c.setCodice(rs.getInt("codice"));
                c.setId_ordine(rs.getInt("id_ordine"));
                c.setQuantita(rs.getInt("quantita"));
                c.setNomeArticolo(rs.getString("nome_articolo"));
                c.setPrezzoUnitario(rs.getDouble("prezzo_unitario")); 
                c.setSconto(rs.getDouble("sconto"));
                contenuti.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contenuti;
    }

    // Elimina una riga ordine
    public boolean doDelete(int codice, int id_ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM contenimento WHERE codice = ? AND id_ordine = ?"
            );
            ps.setInt(1, codice);
            ps.setInt(2, id_ordine);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Aggiorna la quantità
    public boolean doUpdateQuantita(int codice, int id_ordine, int nuovaQuantita) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE contenimento SET quantita = ? WHERE codice = ? AND id_ordine = ?"
            );
            ps.setInt(1, nuovaQuantita);
            ps.setInt(2, codice);
            ps.setInt(3, id_ordine);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
