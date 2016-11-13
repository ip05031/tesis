/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Autor;
import entity.Categoria;
import entity.Inventario;
import entity.Prestamo;
import entity.Titulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 * <>
 */
public class PrestamoJPA {

    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Titulo> titulos = new ArrayList<>();
    private List<Prestamo> listPrestamo = new ArrayList<>();

    EntityManager em;
    EntityManagerFactory emf;

    public PrestamoJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Autor> getAutores() {
        try {
            autores = em.createNamedQuery("Autor.findAll", Autor.class).getResultList();
            return autores;
        } catch (Exception e) {
            System.out.println("Error en JPA prestamo");
            return autores;
        }
    }

    public List<Categoria> getCategorias() {
        try {
            categorias = em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
            return categorias;
        } catch (Exception e) {
            System.out.println("Error en JPA prestamo");
            return categorias;
        }
    }

    public List<Titulo> getTitulos() {
        try {
            titulos = em.createNamedQuery("Titulo.findAll", Titulo.class).getResultList();
            return titulos;
        } catch (Exception e) {
            System.out.println("Error en JPA prestamo");
            return titulos;
        }
    }

    public Integer aumentarId() {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Prestamo.findAll").getSingleResult();
            if (temp != null) {
                return temp;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return temp;
        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Prestamo.findMaxId").getSingleResult();
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

    public void savePrestamo(Prestamo presta) {
        try {

            em.persist(presta);
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println("Error en Save Prestamo JPA");
            System.out.println(e.getMessage());
        } finally {

        }
    }

    public List<Prestamo> listaPrestamos() {
        try {
            listPrestamo = em.createNamedQuery("Prestamo.findPrestamos", Prestamo.class).getResultList();
            return listPrestamo;
        } catch (Exception e) {
            return listPrestamo;
        }
    }
    //////falta cambiar////
    public List<Prestamo> getPrestamoReporte() {
        try {
            listPrestamo = em.createNamedQuery("Prestamo.findPrestamos", Prestamo.class).getResultList();
            return listPrestamo;
        } catch (Exception e) {
            return listPrestamo;
        }
    }
    
    
    public List<Prestamo> listaDevolucion() {
        try {
            listPrestamo = em.createNamedQuery("Prestamo.findDevolucion", Prestamo.class).getResultList();
            return listPrestamo;
        } catch (Exception e) {
            return listPrestamo;
        }
    }

    
    
    public void updatePrestamo(Prestamo pres) {
        try {

            em.merge(pres);
            //em.remove(em.merge(pant))
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
