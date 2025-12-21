package model.DAO;

import model.JavaBeans.*;
import java.sql.*;
import model.ConPool;
import java.util.*;


public class IndirizzoDAO {

    public void doSave(Indirizzo indirizzo) {
        try(Connection con=ConPool.getConnection()){
            if (indirizzo.isPreferito()) {
                PreparedStatement ps1 = con.prepareStatement(
                        "UPDATE indirizzo SET preferito = false WHERE nome_utente = ?");
                ps1.setString(1, indirizzo.getNome_utente());
                ps1.executeUpdate();
            }
            PreparedStatement ps=con.prepareStatement("INSERT INTO indirizzo (via, citta, provincia, cap, paese, preferito, nome_utente) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,indirizzo.getVia());
            ps.setString(2, indirizzo.getCitta());
            ps.setString(3, indirizzo.getProvincia());
            ps.setString(4, indirizzo.getCap());
            ps.setString(5, indirizzo.getPaese());
            ps.setBoolean(6, indirizzo.isPreferito());
            ps.setString(7, indirizzo.getNome_utente());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                indirizzo.setId_indirizzo(rs.getInt(1));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Indirizzo doRetrieveById(int id_indirizzo) {
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM indirizzo WHERE id_indirizzo = ?");
            ps.setInt(1,id_indirizzo);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new Indirizzo(
                        rs.getInt("id_indirizzo"),
                        rs.getString("via"),
                        rs.getString("citta"),
                        rs.getString("provincia"),
                        rs.getString("cap"),
                        rs.getString("paese"),
                        rs.getBoolean("preferito"),
                        rs.getString("nome_utente")
                );
            }
            return null;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Indirizzo> doRetrieveByUser(String nome_utente) {
        List<Indirizzo> lista = new ArrayList<Indirizzo>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM indirizzo WHERE nome_utente = ?");
            ps.setString(1,nome_utente);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                lista.add(new Indirizzo(
                        rs.getInt("id_indirizzo"),
                        rs.getString("via"),
                        rs.getString("citta"),
                        rs.getString("provincia"),
                        rs.getString("cap"),
                        rs.getString("paese"),
                        rs.getBoolean("preferito"),
                        rs.getString("nome_utente")
                ));
            }
            return lista;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doUpdate(Indirizzo indirizzo){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE indirizzo SET via = ?, citta = ?, provincia = ?, cap = ?, paese = ?, preferito = ?, nome_utente = ? WHERE id_indirizzo = ?"
            );
            ps.setString(1, indirizzo.getVia());
            ps.setString(2, indirizzo.getCitta());
            ps.setString(3, indirizzo.getProvincia());
            ps.setString(4, indirizzo.getCap());
            ps.setString(5, indirizzo.getPaese());
            ps.setBoolean(6, indirizzo.isPreferito());
            ps.setString(7, indirizzo.getNome_utente());
            ps.setInt(8, indirizzo.getId_indirizzo());

            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doDelete(int id_indirizzo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM indirizzo WHERE id_indirizzo = ?");
            ps.setInt(1, id_indirizzo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setIndirizzoPreferito(String nome_utente,int id_indirizzoPreferito){
        try(Connection con=ConPool.getConnection()){
            //AZZERIAMO PRIMA I PREFERITI perché ogni utente deve avere al massimo un solo indirizzo preferito alla volta.
            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE indirizzo SET preferito = false WHERE LOWER(nome_utente) = LOWER(?)");
            ps1.setString(1, nome_utente);
            ps1.executeUpdate();


            //IMPOSTIAMO QUELLO SELEZIONATO COME PREFERITO

            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE indirizzo SET preferito = true WHERE id_indirizzo = ? AND LOWER(nome_utente) = LOWER(?)");
            ps2.setInt(1, id_indirizzoPreferito);
            ps2.setString(2, nome_utente);
            ps2.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }



    public Indirizzo getPreferito(String nomeUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM indirizzo WHERE nome_utente = ? AND preferito = true LIMIT 1"
            );
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Indirizzo(
                        rs.getInt("id_indirizzo"),
                        rs.getString("via"),
                        rs.getString("citta"),
                        rs.getString("provincia"),
                        rs.getString("cap"),
                        rs.getString("paese"),
                        rs.getBoolean("preferito"),
                        rs.getString("nome_utente")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
