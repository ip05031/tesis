/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.PalabraClave;
import entity.Titulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Flever
 */
public class TituloJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Titulo> estadoList = new ArrayList<>();

    public TituloJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Titulo> getTitulos() {
        try {
            estadoList = em.createNamedQuery("Titulo.findAll", Titulo.class).getResultList();
            return estadoList;
        } catch (Exception e) {
            return estadoList;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<String> getListatitulos(Integer idTitulo) {
        List<String> titulos = null;
        try {
            titulos = em.createNamedQuery("Titulo.finlistaNombre", String.class).setParameter("idTitulo", idTitulo).getResultList();
            return titulos;
        } catch (Exception e) {
            return titulos;
        }
    }

    public List<String> getTitulosNombres() {
        List<String> titulos = null;
        try {
            titulos = em.createNamedQuery("Titulo.finlistaNombre", String.class).getResultList();
            return titulos;
        } catch (Exception e) {
            return titulos;
        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Titulo.clave").getSingleResult();
            if (claveId != null) {
                return claveId;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void saveTitulo(Titulo titulo) {
        try {

            em.persist(titulo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void mergeTipoUsuario(Titulo titulo) {
        try {

            em.merge(titulo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editituloJPA(Titulo titulo) {
        try {
            em.merge(titulo);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean deleteTituloJPA(Titulo titulo) {
        try {
            em.remove(em.merge(titulo));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean searchTitulo(String titulo) {
        boolean existe = false;
        System.out.println("searchTitulo");
        try {
            estadoList = em.createNamedQuery("Titulo.findTitulo", Titulo.class)
                    .setParameter("titulo", titulo)
                    .getResultList();
            existe = estadoList.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en Palabra clave JPA");
            System.out.println(e.getMessage());
            return existe;
        }

    }
}
