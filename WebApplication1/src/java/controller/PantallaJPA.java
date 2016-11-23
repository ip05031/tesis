package controller;

import entity.Categoria;
import entity.Pantalla;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PantallaJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Pantalla> pantalla = new ArrayList<>();

    public PantallaJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();

    }

    public Pantalla leerId(Pantalla pant) {

        Pantalla panta = new Pantalla();
        try {
            panta = em.createNamedQuery("Pantalla.findByIdPantalla", Pantalla.class)
                    .setParameter("idPantalla", pant.getIdPantalla())
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return panta;
    }

    public int obtenerUltimoId() {
        int i = 0;
        i = (Integer) em.createNamedQuery("Pantalla.findMaxId").getSingleResult();
        i++;
        return i;
    }

    public List<Pantalla> getPantalla() {
        try {
            pantalla = em.createNamedQuery("Pantalla.findAll", Pantalla.class).getResultList();
            return pantalla;
        } catch (Exception e) {
            return pantalla;
        }
    }

    public void savePantalla(Pantalla pant) {
        try {

            em.persist(pant);
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePantalla(Pantalla pant) {
        try {

            em.merge(pant);
            //em.remove(em.merge(pant))
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePantalla(Pantalla pant) {
        try {
            em.remove(em.merge(pant));
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
        public boolean searchPantalla(int tipo , String valor) {
        boolean existe = false;
        String query = "";
        String param = "";

        try {
            if ( tipo == 1 ){
                query = "Pantalla.findByNombrepa";
                param = "nombrepa";
            }
            else{
                query = "Pantalla.findByAccesopa";
                param = "accesopa";
            }
            pantalla = em.createNamedQuery(query, Pantalla.class)
                    .setParameter(param, valor)
                    .getResultList();
            existe = pantalla.size() > 0;
            return existe;
        } catch (Exception e) {
            System.out.println("Error en pantalla JPA");
            System.out.println(e.getMessage());
            return existe;
        }

    }
    
}


