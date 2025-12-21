package model.JavaBeans;

import java.util.*;
import java.lang.*;

public class Categoria{
    private int id_categoria;
    private String tipologia;

    public Categoria(int id_categoria, String tipologia) {
        this.id_categoria = id_categoria;
        this.tipologia = tipologia;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }


}