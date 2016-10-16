/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class CategoriaJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Categoria> categoria = new ArrayList<>();

    public CategoriaJPA() {

        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Categoria> getCategoria() {
        try {
            categoria = em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
            return categoria;
        } catch (Exception e) {
            return categoria;
        }
    }
    
    public Categoria LeerIdCategoria(Categoria cat)
    {
        Categoria categoria = new Categoria();
        try {
            categoria = em.createNamedQuery("Categoria.findByIdCategoria", Categoria.class)
                    .setParameter("idCategoria",cat.getIdCategoria())
                    .getSingleResult();
            return categoria;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return categoria;
        }
    }
    
    public Categoria LeerCategoria(int cat) {
    Categoria cate = new Categoria();
    try {
            cate = (Categoria) em.createNamedQuery("Categoria.findByIdCategoria").setParameter("idCategoria", cat).getSingleResult();
            int i=0;
            return cate;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return cate;
        }
    }
    
     public Integer aumentarIdCategoria()
    {
        Integer temp = 0;
        try {
            temp = (Integer) em.createNamedQuery("Categoria.aumentarId").getSingleResult();
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
    
    
    
    
    
        public void saveCategoria(Categoria cat ){
        try {
            em.persist(cat);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void updateCategoria(Categoria cat ){
        try {
            em.merge(cat);
            em.getTransaction().commit();
            em.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void deleteCategoria(Categoria cat ){
        try {
            em.remove(em.merge(cat));
            em.getTransaction().commit();
            em.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        

}
