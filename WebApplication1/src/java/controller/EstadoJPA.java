/*
 */
package controller;

import entity.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EstadoJPA {
    EntityManager em;
    EntityManagerFactory emf;
    private List<Estado> estadoList = new ArrayList<>();

    public EstadoJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public List<Estado> getEstado() {
        try {
            estadoList = em.createNamedQuery("Estado.findAll", Estado.class).getResultList();
            return estadoList;
        } catch (Exception e) {
            return estadoList;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public List <String> getListanombresestado(Integer idEstado){
    List<String> estador=null;
        try {
            estador = em.createNamedQuery("Estado.finlistaNombre",String.class).setParameter("idEstado", idEstado).getResultList();
            return estador;
        } catch (Exception e) {
            return estador;
        }
    }
    
    
    public List<String> getEstadosNombres() {
        List<String> estador=null;
        try {
            estador = em.createNamedQuery("Estado.finlistaNombre",String.class).getResultList();
            return estador;
        } catch (Exception e) {
            return estador;
        }
    } 
     
    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Estado.clave").getSingleResult();
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

    public void saveEstadoJPA(Estado estado) {
        try {

            em.persist(estado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void mergeTipoUsuario(Estado estado) {
        try {

            em.merge(estado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
     public void editestadoJPA(Estado estado){
    try{
        em.merge(estado);
        em.getTransaction().commit();
        em.close();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }

    public boolean eliminarestadoJPA(Estado estado) {
        try {
            em.remove(em.merge(estado));
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

   
    
    public Integer aumentarIdestado()
    {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Estado.aumentarId").getSingleResult();
            if (temp != null) {
                return temp;
            }else{
            return 0;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return temp;
        }
    }  
     public boolean searchEstado(String categoria2) {
        boolean existe = false;

        try {
            estadoList = em.createNamedQuery("Estado.findByNombreEstado", Estado.class)
                    .setParameter("nombreEstado", categoria2)
                    .getResultList();
            existe = estadoList.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en estado JPA");
            System.out.println(e.getMessage());
            return existe;
        }

    }
   
}
