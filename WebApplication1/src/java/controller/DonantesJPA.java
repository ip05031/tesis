/*
 *
 */
package controller;

import entity.Donate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DonantesJPA {
    
     EntityManager em;
     EntityManagerFactory emf;
     private List<Donate> donate= new ArrayList<>();
     
     
    public DonantesJPA() {
         emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
  
    public List<Donate> getDonate() {
        try {
            donate = em.createNamedQuery("Donate.findAll",Donate.class).getResultList();
            return donate;
        } catch (Exception e) {
            return donate;
        }
    }
    
    public void guardardonanteJPA(Donate donante){
    try{
        em.persist(donante);
        em.getTransaction().commit();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }
    }
    
     public void editdonanteJPA(Donate donante){
    try{
        em.merge(donante);
        em.getTransaction().commit();
        em.close();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }
      public void eliminardonanteJPA(Donate donante){
    try{
        em.remove(em.merge(donante));
        em.getTransaction().commit();
        em.close();
    }
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }
    
    public Integer aumentarIddonante()
    {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Donate.aumentarId").getSingleResult();
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
    
}

    
