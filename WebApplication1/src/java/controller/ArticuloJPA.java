/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Articulo;
import entity.Revista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class ArticuloJPA {
    EntityManager em;
    EntityManagerFactory emf;
    private List<Articulo> listArticulo = new ArrayList<>();

    public ArticuloJPA() {
     emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public List<Articulo> getArticulos() {
        try {
            listArticulo = em.createNamedQuery("Articulo.findAll", Articulo.class).getResultList();
            return listArticulo;
        } catch (Exception e) {
            return listArticulo;
        }
    }
    
    
    public List<Articulo> getListArticulos() {
        try {
            listArticulo = em.createNamedQuery("Articulo.findListArticulo", Articulo.class).getResultList();
            return listArticulo;
        } catch (Exception e) {
            return listArticulo;
        }
    }
    
    

    
}
