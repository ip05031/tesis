/*
 */
package controller;

import entity.Estado;
import entity.Parametro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ParametroJPA {
    EntityManager em;
    EntityManagerFactory emf;
    private List<Parametro> parametroList = new ArrayList<>();

    public ParametroJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public List<Parametro> getParametro() {
        try {
            parametroList = em.createNamedQuery("Parametro.findAll", Parametro.class).getResultList();
            return parametroList;
        } catch (Exception e) {
            return parametroList;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Parametro leerIdParametro(Parametro cat) {
        Parametro pa = new Parametro();
        try {
            pa = em.createNamedQuery("Parametro.findByIdParametro", Parametro.class)
                    .setParameter("idParametro", cat.getIdParametro())
                    .getSingleResult();
            return pa;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return pa;
        }
    }
    
    public Parametro leerIdParametroString(String cat) {
        Parametro pa = new Parametro();
        try {
            pa = em.createNamedQuery("Parametro.findByIdParametro", Parametro.class)
                    .setParameter("idParametro", cat)
                    .getSingleResult();
            return pa;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return pa;
        }
    }
    
     public Boolean leerParametro(String cat) {
        Boolean paV = false;
        Parametro pa= new Parametro();
        try {
            pa = em.createNamedQuery("Parametro.findByIdParametro", Parametro.class)
                    .setParameter("idParametro", cat)
                    .getSingleResult();
            paV = !pa.equals(null);
            return paV;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return paV;
        }
    }
         
    

    public void saveParametroJPA(Parametro parametro) {
        try {

            em.persist(parametro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void mergeParametro(Parametro parametro) {
        try {

            em.merge(parametro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
     

    public boolean eliminarParametroJPA(Parametro parametro) {
        try {
            em.remove(em.merge(parametro));
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

           
   
}
