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
public class ImportarJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Articulo> listadoArticulos = new ArrayList<>();

    public ImportarJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

    }

    public List<Articulo> getListaUnArticulo(int idRevista) {
        try {
            listadoArticulos = em.createNamedQuery("Articulo.findListArticulo", Articulo.class)
                    .setParameter("idRevista", idRevista)
                    .getResultList();
            return listadoArticulos;
        } catch (Exception e) {
            return listadoArticulos;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulo> getArticulo() {
        try {
            listadoArticulos = em.createNamedQuery("Articulo.findAll", Articulo.class).getResultList();
            return listadoArticulos;
        } catch (Exception e) {
            return listadoArticulos;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Revista leerId(int idrevista) {

        Revista rev = new Revista();
        try {
            rev = em.createNamedQuery("Revista.findByIdRevista", Revista.class)
                    .setParameter("idRevista", idrevista)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rev;
    }

}
