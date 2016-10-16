/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.ArticuloJPA;
import entity.Articulo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IPalacios
 */
@Named(value = "articuloBean")
@SessionScoped
public class articuloBean implements Serializable {

    private ArticuloJPA articuloJPA;
    private List<Articulo> lArticulos = new ArrayList<>();

    /**
     * Creates a new instance of articuloBean
     */
    public articuloBean() {

    }

    public List<Articulo> cargarArticulos() {
        articuloJPA = new ArticuloJPA();
        lArticulos = articuloJPA.getListArticulos();
        return lArticulos;
    }

}
