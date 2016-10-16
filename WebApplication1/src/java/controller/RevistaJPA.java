/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

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
public class RevistaJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Revista> listRevista = new ArrayList<>();

    public List<Revista> getListRevista() {
        return listRevista;
    }

    public void setListRevista(List<Revista> listRevista) {
        this.listRevista = listRevista;
    }

    public RevistaJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Revista> getRevistas() {
        try {
            listRevista = em.createNamedQuery("Revista.findAll", Revista.class).getResultList();
            return listRevista;
        } catch (Exception e) {
            return listRevista;
        }
    }

    public List<Revista> getRevistaLetra(String letra) {
        try {
            listRevista = em.createNamedQuery("Revista.findByLetter", Revista.class)
                    .setParameter(":letra", letra)
                    .getResultList();
            return listRevista;
        } catch (Exception e) {
            System.out.println("error:");
            System.out.println(e.getMessage());
            return listRevista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
