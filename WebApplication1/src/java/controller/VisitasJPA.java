/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Visitas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */
public class VisitasJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Visitas> visitas = new ArrayList<>();

    public VisitasJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Visitas> getVisitas() {
        try {
            visitas = em.createNamedQuery("Visitas.findAll", Visitas.class).getResultList();
            return visitas;
        } catch (Exception e) {
            return visitas;
        }
    }

    public Visitas LeerFechaVisitas(Visitas vis) {
        Visitas visit = new Visitas();
        try {
            visit = em.createNamedQuery("Visitas.findByFechav", Visitas.class)
                    .setParameter("idDonacion", vis.getFechav())
                    .getSingleResult();
            return visit;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return visit;
        }
    }

    public Date MaximaFechaVisitas() {
        Date visit = new Date();
        try {

            visit = (Date) em.createNamedQuery("Visitas.findByFechaMaxima", Date.class).getSingleResult();

            return visit;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return visit;
        } finally {
            em.close();
        }
    }

    public List<Visitas> FechaVisitas(Integer periodo) {
        try {
            visitas = em.createNativeQuery("Visitas.busquedaFecha", Visitas.class).setParameter("periodo", periodo)
                    .getResultList();
            return visitas;
        } catch (Exception e) {
            return visitas;
        }
    }

    public List<Visitas> FechaVisitasBetwen(Date fechaInicio, Date fechaFin) {
        try {
            visitas = em.createNamedQuery("Visitas.betwen", Visitas.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
            return visitas;
        } catch (Exception e) {
            System.out.println("Error : ---");
            System.out.println(e.getMessage());
            return visitas;
        }
    }

    public void saveVisitas(Visitas vis) {
        try {
            em.persist(vis);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDonaciones(Visitas vis) {
        try {
            em.merge(vis);
            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
