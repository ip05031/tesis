/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Inventario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mario Sanchez
 */
public class InventarioJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Inventario> inventarioList = new ArrayList<>();

    public InventarioJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Inventario> getInventarios() {
        try {
            inventarioList = em.createNamedQuery("Inventario.findAll", Inventario.class).getResultList();
            return inventarioList;
        } catch (Exception e) {
            return inventarioList;
        }
    }

    public void guardarInventario(Inventario inv) {
        try {
            em.persist(inv);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Inventario.findMaxId").getSingleResult();
            if (claveId != null) {
                return claveId + 1;
            } else {
                return 0 + 1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId + 1;
        }

    }

    public void eliminarinventJPA(Inventario Elemento) {
        try {
            em.remove(em.merge(Elemento));
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer minIdDisp(int idRev) {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Inventario.findMinIdDisp")
                    .setParameter("idRevista", idRev)
                    .getSingleResult();          
            if (claveId != null) {
                return claveId;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }
    
    
    public void updateInventario(Inventario inv) {
        try {

            em.merge(inv);
            //em.remove(em.merge(pant))
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
