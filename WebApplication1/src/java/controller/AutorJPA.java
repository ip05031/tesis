/*
 */
package controller;

import entity.Autor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AutorJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Autor> autor = new ArrayList<>();

    public AutorJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Autor> getAutor() {
        try {
            autor = em.createNamedQuery("Autor.findAll", Autor.class).getResultList();
            return autor;
        } catch (Exception e) {
            return autor;
        }
    }

    public List<String> getListaAutores(Integer idAutor) {
        List<String> autordeRevista = null;
        try {
            autordeRevista = em.createNamedQuery("Autor.finlistaNombre", String.class).setParameter("idAutor", idAutor).getResultList();
            return autordeRevista;
        } catch (Exception e) {
            return autordeRevista;
        }
    }

    public List<String> getAutorNombre() {
        List<String> autordeRevista = null;
        try {
            autordeRevista = em.createNamedQuery("PalabraClave.finlistaNombre", String.class).getResultList();
            return autordeRevista;
        } catch (Exception e) {
            return autordeRevista;
        }
    }

    public void guardarautorJPA(Autor autores) {
        try {
            em.persist(autores);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editautorJPA(Autor autores) {
        try {
            em.merge(autores);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarautorJPA(Autor autores) {
        try {
            em.remove(em.merge(autores));
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer aumentarIdautor() {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Autor.aumentarId").getSingleResult();
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

    public boolean searchCategora(String autor2) {
        boolean existe = false;
      
        try {
            autor = em.createNamedQuery("Autor.findBySearch", Autor.class)
                    .setParameter("nombreAutor", autor2)
                    .getResultList();
            existe = autor.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return existe;
        }

    }
}
