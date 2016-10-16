/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Inventario;
import entity.Pantalla;
import entity.Revista;
import entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class InicioJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Revista> listadoRevista = new ArrayList<>();

    public InicioJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Revista> obtnerRevistas() {
        try {
            String filtroLetra = "A%";
            listadoRevista = em.createNamedQuery("Revista.findByLetterA", Revista.class)
                    .getResultList();
            return listadoRevista;
        } catch (Exception e) {
            return listadoRevista;
        }
    }
    
    public List<Pantalla> obtenerPantallas(){
        List<Pantalla> listado = null ;
        return listado;
    }

    public Revista obtenerRevista(int idRev) {

        Revista panta = new Revista();
        try {
            panta = em.createNamedQuery("Revista.findByIdRevista", Revista.class)
                    .setParameter("idRevista", idRev)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return panta;
    }

    public List<Usuario> existeUsuario(String nombre, String password) {
        List<Usuario> existe = new ArrayList<>();
        try {
            existe = em.createNamedQuery("Usuario.findBylogin",Usuario.class)
                    .setParameter("nickname", nombre)
                    .setParameter("contra", password)
                    .getResultList();
            return existe;
        } catch (Exception e) {
            System.out.println("Error al obtener el login");
            System.out.println(e.getMessage());
        }

        return existe;
    }
    
    
        public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Inventario.findMaxId").getSingleResult();
            if (claveId != null) {
                return claveId + 1;
            } else {
                return 0 + 1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return claveId + 1;
        }
    }
        
        
         public void saveInventario(Inventario inv) {
        try {

            em.persist(inv);
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println("Error en saveInventario JPA");
            System.out.println(e.getMessage());
        }
        finally{
            
        }
    }

}
