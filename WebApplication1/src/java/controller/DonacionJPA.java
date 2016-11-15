/*
 
 */
package controller;


import entity.Donaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class DonacionJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Donaciones> donaciones = new ArrayList<>();

    public DonacionJPA() {

        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Donaciones> getDonaciones() {
        try {
            donaciones = em.createNamedQuery("Donaciones.findAll", Donaciones.class).getResultList();
            return donaciones;
        } catch (Exception e) {
            return donaciones;
        }
    }
    
    public List<Donaciones> getDonacionReporte() {
        try {
            donaciones = em.createNamedQuery("Donaciones.reporte", Donaciones.class).getResultList();
            return donaciones;
        } catch (Exception e) {
            return donaciones;
        }
    }
    
    
    
    public Donaciones LeerIdDonaciones(Donaciones don)
    {
        Donaciones donaciones = new Donaciones();
        try {
            donaciones = em.createNamedQuery("Donaciones.findByIdDonacion", Donaciones.class)
                    .setParameter("idDonacion",don.getIdDonacion())
                    .getSingleResult();
            return donaciones;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return donaciones;
        }
    }
    
     public Integer aumentarIdDonaciones()
    {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Donaciones.aumentarId").getSingleResult();
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
    
    
    
    
    
        public void saveDonaciones(Donaciones don ){
        try {
            em.persist(don);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void updateDonaciones(Donaciones don ){
        try {
            em.merge(don);
            em.getTransaction().commit();
            em.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void deleteDonaciones(Donaciones don ){
        try {
            em.remove(em.merge(don));
            em.getTransaction().commit();
            em.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        

}
