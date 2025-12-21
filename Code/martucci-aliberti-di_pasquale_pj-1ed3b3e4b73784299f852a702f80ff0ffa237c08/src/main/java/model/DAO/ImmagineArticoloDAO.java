package model.DAO;

import model.JavaBeans.*;
import model.ConPool;
import java.sql.*;
import java.util.*;

public class ImmagineArticoloDAO {

    public void doSave(ImmagineArticolo img){
        String sql = "INSERT INTO ImmagineArticolo (codice_articolo, url, is_principale, descrizione) VALUES (?, ?, ?, ?)";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, img.getCodice_articolo());
            ps.setString(2, img.getUrl());
            ps.setBoolean(3, img.isIs_principale());
            ps.setString(4, img.getDescrizione());

            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<ImmagineArticolo> doRetrieveByArticolo(int codiceArticolo){
        List<ImmagineArticolo> immagini=new ArrayList<>();

        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM ImmagineArticolo WHERE codice_articolo=?");

            ps.setInt(1, codiceArticolo);
            ResultSet rs=ps.executeQuery();

            while (rs.next()) {
                ImmagineArticolo img = new ImmagineArticolo(
                        rs.getInt("id_immagine"),
                        rs.getInt("codice_articolo"),
                        rs.getString("url"),
                        rs.getBoolean("is_principale"),
                        rs.getString("descrizione")
                );
                immagini.add(img);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return immagini;
    }

    public void doDeleteByArticolo(int codiceArticolo) throws SQLException {
        String sql = "DELETE FROM ImmagineArticolo WHERE codice_articolo = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codiceArticolo);
            ps.executeUpdate();
        }
    }

    public void doDelete(int idImmagine) throws SQLException {
        String sql = "DELETE FROM ImmagineArticolo WHERE id_immagine = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idImmagine);
            ps.executeUpdate();
        }
    }

    public ImmagineArticolo findMainImage(int codiceArticolo) {
        String sql = "SELECT * FROM ImmagineArticolo WHERE codice_articolo = ? AND is_principale = true LIMIT 1";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codiceArticolo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ImmagineArticolo(
                        rs.getInt("id_immagine"),
                        rs.getInt("codice_articolo"),
                        rs.getString("url"),
                        rs.getBoolean("is_principale"),
                        rs.getString("descrizione")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}