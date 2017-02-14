/*
 
 */
package controller;

import entity.Ocupacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**/
public class OcupacionJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Ocupacion> ocupacion = new ArrayList<>();

    public OcupacionJPA() {

        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Ocupacion> getOcupacion() {
        try {
            ocupacion = em.createNamedQuery("Ocupacion.findAll", Ocupacion.class).getResultList();
            return ocupacion;
        } catch (Exception e) {
            return ocupacion;
        }
    }
    
    
     public Integer aumentarIdOcupacion() {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Ocupacion.aumentarId").getSingleResult();
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
     
     public Ocupacion LeerIdOcupacion(Ocupacion ocup) {
        Ocupacion ocupacion = new Ocupacion();
        try {
            ocupacion = em.createNamedQuery("Ocupacion.findByIdOcupacion", Ocupacion.class)
                    .setParameter("idOcupacion", ocup.getIdOcupacion())
                    .getSingleResult();
            return ocupacion;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ocupacion;
        }
    }
     
     public Ocupacion LeerCategoria(int ocup) {
        Ocupacion ocupa = new Ocupacion();
        try {
            ocupa = (Ocupacion) em.createNamedQuery("Ocupacion.findByIdOcupacion").setParameter("idOcupacion", ocup).getSingleResult();
            int i = 0;
            return ocupa;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ocupa;
        }
    }
     
     
     public void saveOcupacion(Ocupacion ocup) {
        try {
            em.persist(ocup);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     
     public void updateOcupacion(Ocupacion ocup) {
        try {
            em.merge(ocup);
            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     
     
     public void deleteOcupacion(Ocupacion ocup) {
        try {
            em.remove(em.merge(ocup));
            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     
     public boolean searchOcupacion(String Ocupacion2) {
        boolean existe = false;

        try {
            ocupacion = em.createNamedQuery("Ocupacion.findOcupacion", Ocupacion.class)
                    .setParameter("ocupacion", Ocupacion2)
                    .getResultList();
            existe = ocupacion.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en Ocupacion JPA");
            System.out.println(e.getMessage());
            return existe;
        }

    }
    
}
