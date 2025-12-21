package model.DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.JavaBeans.*;
import model.ConPool;

public class MetodiPagamentoDAO {

    public MetodiPagamento doRetrieveByKey(String numCarta){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Metodi_pagamento WHERE num_carta = ?");
            ps.setString(1, numCarta);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                GregorianCalendar dataScadenza = new GregorianCalendar();
                Date sqlDate = rs.getDate("data_scadenza");
                if (sqlDate != null) {
                    dataScadenza.setTime(sqlDate);
                }
                return new MetodiPagamento(
                        rs.getString("num_carta"),
                        rs.getString("intestatario"),
                        dataScadenza,
                        rs.getString("nome_utente")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MetodiPagamento> doRetrieveAll() {
        List<MetodiPagamento> lista = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Metodi_pagamento");
            while (rs.next()) {
                GregorianCalendar dataScadenza = new GregorianCalendar();
                Date sqlDate = rs.getDate("data_scadenza");
                if (sqlDate != null) {
                    dataScadenza.setTime(sqlDate);
                }
                MetodiPagamento metodo = new MetodiPagamento(
                        rs.getString("num_carta"),
                        rs.getString("intestatario"),
                        dataScadenza,
                        rs.getString("nome_utente")
                );
                lista.add(metodo);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doSave(MetodiPagamento metodo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Metodi_pagamento (num_carta, intestatario, data_scadenza, nome_utente) VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, metodo.getNumCarta());
            ps.setString(2, metodo.getIntestatario());
            ps.setDate(3, new java.sql.Date(metodo.getDataScadenza().getTimeInMillis()));
            ps.setString(4, metodo.getNomeUtente());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doDelete(String numCarta, String nomeUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM Metodi_pagamento WHERE num_carta = ? AND nome_utente = ?"
            );
            ps.setString(1, numCarta);
            ps.setString(2, nomeUtente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doUpdate(MetodiPagamento metodo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Metodi_pagamento SET intestatario = ?, data_scadenza = ?, nome_utente = ? WHERE num_carta = ?"
            );
            ps.setString(1, metodo.getIntestatario());
            ps.setDate(2, new java.sql.Date(metodo.getDataScadenza().getTimeInMillis()));
            ps.setString(3, metodo.getNomeUtente());
            ps.setString(4, metodo.getNumCarta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MetodiPagamento> doRetrieveByUser(String nome_utente){
        List<MetodiPagamento> lista=new ArrayList<>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Metodi_pagamento WHERE nome_utente= ?");
            ps.setString(1, nome_utente);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                GregorianCalendar dataScadenza = new GregorianCalendar();
                Date sqlDate=rs.getDate("data_scadenza");
                if (sqlDate != null) {
                    dataScadenza.setTime(sqlDate);
                }
                MetodiPagamento carta = new MetodiPagamento(
                        rs.getString("num_carta"),
                        rs.getString("intestatario"),
                        dataScadenza,
                        rs.getString("nome_utente")
                );
                lista.add(carta);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean esisteMetodoPagamento(String numCarta, String nomeUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement( //se esiste almeno una riga
                    "SELECT 1 FROM Metodi_pagamento WHERE num_carta = ? AND nome_utente = ?");
            ps.setString(1, numCarta);
            ps.setString(2, nomeUtente);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // true se esiste almeno una riga
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
