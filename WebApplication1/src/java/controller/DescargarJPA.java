/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Descarga;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class DescargarJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Descarga> horas_descargas = new ArrayList<>();

    public DescargarJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public void saveDescarga(Descarga descarga) {
        try {

            em.persist(descarga);
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ha ocurrido un error!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("Error en save descarga JPA");
            System.out.println(e.getMessage());
        } finally {

        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Descarga.findMaxId").getSingleResult();
            if (claveId != null) {
                return claveId + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId + 1;
        }
    }

    public List<Descarga> getHoras() {
        try {
            horas_descargas = em.createNamedQuery("Descarga.findhoras", Descarga.class).getResultList();
            return horas_descargas;
        } catch (Exception e) {
            System.out.println("Error en JPA horas_descargas");
            return horas_descargas;
        }
    }

    public List<Descarga> getHoras_descargas() {
        return horas_descargas;
    }

    public void setHoras_descargas(List<Descarga> horas_descargas) {
        this.horas_descargas = horas_descargas;
    }

}
