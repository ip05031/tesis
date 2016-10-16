/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Articulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Flever
 */
public class ArticulosJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Articulo> listadoArticulos = new ArrayList<>();
    

    public ArticulosJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public List<Articulo> getArticulo() {
        try {
            listadoArticulos = em.createNamedQuery("Articulo.findAll", Articulo.class).getResultList();
            return listadoArticulos;
        } catch (Exception e) {
            return listadoArticulos;
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
    public Integer getClave(){
        Integer claveId = 0;
     try {
            claveId = (Integer) em.createNamedQuery("Articulo.clave").getSingleResult();
            if(claveId != null)
            return claveId;
            else
            return 0 ;
        } catch (Exception e) {
            System.out.println(e);
            return claveId;
        }
     finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void savePantalla(Articulo articulo) {
        try {         
            
            em.persist(articulo);
            em.getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void mergeTipoUsuario(Articulo articulo) {
        try {         
            
            em.merge(articulo);
            em.getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e.getMessage());            
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public boolean deleteTipoUsuario(Articulo artiluco){
        try {
            em.remove(em.merge(artiluco));
            em.getTransaction().commit();            
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());           
            return false;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
