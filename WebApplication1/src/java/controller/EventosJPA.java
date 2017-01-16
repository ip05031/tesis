/*
 
 */
package controller;

import entity.Evento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 */
public class EventosJPA {
    
    EntityManager em;
    EntityManagerFactory emf;
    private List<Evento> evento= new ArrayList<>();
    
    public EventosJPA() {
         emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Evento> getImgBanner(){
        try {
            evento = em.createNamedQuery("Evento.findAllBanner",Evento.class).getResultList();
            return evento;
        } catch (Exception e) {
            return evento;
        }
    }
    
    public List<Evento> getEventos()  {
        try {
            evento = em.createNamedQuery("Evento.findAll",Evento.class).getResultList();
            return evento;
        } catch (Exception e) {
            return evento;
        }
    }
    
    public void guardareventoJPA(Evento evento){
    try{
        em.persist(evento);
        em.getTransaction().commit();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }
    }
    
    public void editeventoJPA(Evento evento){
    try{
        em.merge(evento);
        em.getTransaction().commit();
        em.close();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }
      public void eliminareventoJPA(Evento evento){
    try{
        em.remove(em.merge(evento));
        em.getTransaction().commit();
        em.close();
    }
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }
      
      public Integer aumentarIdevento()
    {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Evento.aumentarId").getSingleResult();
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

   public Evento LeerIdEvento(Evento event)
    {
        Evento eventos = new Evento();
        try {
            eventos = em.createNamedQuery("Evento.findByIdEvento", Evento.class)
                    .setParameter("IdEvento",event.getIdEvento())
                    .getSingleResult();
            return eventos;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return eventos;
        }
    } 
    
    
}
