/*
 *
 */
package controller;

import entity.PalabraClave;
import entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PalabrasClavesJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<PalabraClave> palabraClave = new ArrayList<>();

    public PalabrasClavesJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<PalabraClave> getPalabraClave() {
        try {
            palabraClave = em.createNamedQuery("PalabraClave.findAll", PalabraClave.class).getResultList();
            return palabraClave;
        } catch (Exception e) {
            return palabraClave;
        }
    }

    public List<String> getListaLicknombres(Integer idArticulo) {
        List<String> palabraClav = null;
        try {
            palabraClav = em.createNamedQuery("PalabraClave.finlistaNombre", String.class).setParameter("idArticulo", idArticulo).getResultList();
            return palabraClav;
        } catch (Exception e) {
            return palabraClav;
        }
    }

    public List<String> getPalabraClaveNombres() {
        List<String> palabraClav = null;
        try {
            palabraClav = em.createNamedQuery("PalabraClave.finlistaNombre", String.class).getResultList();
            return palabraClav;
        } catch (Exception e) {
            return palabraClav;
        }
    }

    public boolean searchPalabra(String palabra) {
        boolean existe = false;

        try {
            palabraClave = em.createNamedQuery("PalabraClave.findPalabra", PalabraClave.class)
                    .setParameter("palabra", palabra)
                    .getResultList();
            existe = palabraClave.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en Palabra clave JPA");
            System.out.println(e.getMessage());
            return existe;
        }

    }

    public void guardarpcJPA(PalabraClave palabra) {
        try {
            em.persist(palabra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editpcJPA(PalabraClave palabra) {
        try {
            em.merge(palabra);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarpcJPA(PalabraClave palabra) {
        try {
            em.remove(em.merge(palabra));
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer aumentarIdpalabra() {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("PalabraClave.aumentarId").getSingleResult();
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

    public void insertarBitacora(String tabla, String motivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
