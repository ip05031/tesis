/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class TipoUsuarioJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<TipoUsuario> tipousuario = new ArrayList<>();

    public TipoUsuarioJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<TipoUsuario> getTipoUsuario() {
        try {
            tipousuario = em.createNamedQuery("TipoUsuario.findAll", TipoUsuario.class).getResultList();
            return tipousuario;
        } catch (Exception e) {
            return tipousuario;
        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("TipoUsuario.clave").getSingleResult();
            if (claveId != null) {
                return claveId;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId;
        }
    }

    public void savePantalla(TipoUsuario pant) {
        try {

            em.persist(pant);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.close();
        }
    }

    public void mergeTipoUsuario(TipoUsuario pant) {
        try {

            em.merge(pant);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.close();
        }
    }

    public boolean deleteTipoUsuario(TipoUsuario tipo) {
        try {
            em.remove(em.merge(tipo));
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.close();
            return false;
        }
    }

    public boolean searchTipo(String categoria2) {
        boolean existe = false;
        List<TipoUsuario> tipo = new ArrayList<>();
        try {
            tipo = em.createNamedQuery("TipoUsuario.findByNombretp", TipoUsuario.class)
                    .setParameter("nombretp", categoria2)
                    .getResultList();
            existe = tipo.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en categoria JPA");
            System.out.println(e.getMessage());
            return existe;
        }
    }

}
