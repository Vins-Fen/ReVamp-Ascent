package dataAccess.DAO;

import dataAccess.JavaBeans.*;
import java.sql.*;
import java.util.*;
import dataAccess.ConPool;

public class RecensioneDAO {

    public Recensione doRetrieveById(int id){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Recensione WHERE id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                GregorianCalendar data=new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                return new Recensione(
                        rs.getInt("id"),
                        rs.getInt("valutazione"),
                        rs.getString("titolo"),
                        rs.getString("testo"),
                        data,
                        rs.getInt("codice"),
                        rs.getString("nome_utente")
                        );
            }
            return null;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doSave(Recensione r){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("INSERT INTO Recensione (valutazione, titolo, testo, data, codice, nome_utente) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, r.getValutazione());
            ps.setString(2, r.getTitolo());
            ps.setString(3, r.getTesto());
            ps.setDate(4, new java.sql.Date(r.getData().getTimeInMillis()));
            ps.setInt(5, r.getCodice());
            ps.setString(6, r.getNome_utente());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Recensione WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recensione> doRetrieveByCodiceArticolo(int codice){
        List<Recensione> recensioni=new ArrayList<>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Recensione WHERE codice=? ORDER BY data DESC");
            ps.setInt(1,codice);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                GregorianCalendar data = new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                Recensione r = new Recensione(
                        rs.getInt("id"),
                        rs.getInt("valutazione"),
                        rs.getString("titolo"),
                        rs.getString("testo"),
                        data,
                        rs.getInt("codice"),
                        rs.getString("nome_utente")
                );
                recensioni.add(r);
            }
            return recensioni;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recensione> doRetrieveByNomeUtente(String nomeUtente) {
        List<Recensione> recensioni = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Recensione WHERE nome_utente = ?");
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GregorianCalendar data = new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                Recensione r = new Recensione(
                        rs.getInt("id"),
                        rs.getInt("valutazione"),
                        rs.getString("titolo"),
                        rs.getString("testo"),
                        data,
                        rs.getInt("codice"),
                        rs.getString("nome_utente")
                );
                recensioni.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recensioni;
    }

}
