package model.DAO;




import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.JavaBeans.*;
import model.ConPool;

public class ArticoloDAO {

    public Articolo doRetrieveById(int codice){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM articolo WHERE codice= ?");
            ps.setInt(1,codice);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Articolo a = new Articolo(
                        rs.getInt("codice"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getString("colore"),
                        rs.getDouble("sconto"),
                        rs.getDouble("prezzo"),
                        rs.getDouble("peso"),
                        rs.getString("dimensione"),
                        rs.getInt("id_categoria")
                );
                return a;
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Articolo> doRetriveByAll(){
        List<Articolo> articoli=new ArrayList<>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT * FROM articolo");
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Articolo a = new Articolo(
                        rs.getInt("codice"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getString("colore"),
                        rs.getDouble("sconto"),
                        rs.getDouble("prezzo"),
                        rs.getDouble("peso"),
                        rs.getString("dimensione"),
                        rs.getInt("id_categoria")
                );
                articoli.add(a);
            }
            return articoli;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void doSave(Articolo a){
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO articolo (codice, nome, descrizione, colore, sconto, prezzo, peso, dimensione, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1,a.getCodice());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getDescrizione());
            ps.setString(4, a.getColore());
            ps.setDouble(5, a.getSconto());
            ps.setDouble(6, a.getPrezzo());
            ps.setDouble(7, a.getPeso());
            ps.setString(8, a.getDimensione());
            ps.setInt(9, a.getId_categoria());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void doUpdate(Articolo a) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE articolo SET nome=?, descrizione=?, colore=?, sconto=?, prezzo=?, peso=?, dimensione=?, id_categoria=? WHERE codice=?");
            ps.setString(1, a.getNome());
            ps.setString(2, a.getDescrizione());
            ps.setString(3, a.getColore());
            ps.setDouble(4, a.getSconto());
            ps.setDouble(5, a.getPrezzo());
            ps.setDouble(6, a.getPeso());
            ps.setString(7, a.getDimensione());
            ps.setInt(8, a.getId_categoria());
            ps.setInt(9, a.getCodice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(int codice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM articolo WHERE codice = ?");
            ps.setInt(1, codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Articolo> cercaArticoloPerNome(String nome){
        List<Articolo> articoli=new ArrayList<>();
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM articolo WHERE LOWER(nome) LIKE ?");
            ps.setString(1, "%" + nome.toLowerCase() + "%");

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Articolo a = new Articolo(
                        rs.getInt("codice"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getString("colore"),
                        rs.getDouble("sconto"),
                        rs.getDouble("prezzo"),
                        rs.getDouble("peso"),
                        rs.getString("dimensione"),
                        rs.getInt("id_categoria")
                );
            articoli.add(a);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }


    public List<Articolo> doRetrieveConSconto() {
        List<Articolo> articoliScontati = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM articolo WHERE sconto IS NOT NULL AND sconto > 0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Articolo a = new Articolo(
                        rs.getInt("codice"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getString("colore"),
                        rs.getDouble("sconto"),
                        rs.getDouble("prezzo"),
                        rs.getDouble("peso"),
                        rs.getString("dimensione"),
                        rs.getInt("id_categoria")
                );
                articoliScontati.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articoliScontati;
    }

}