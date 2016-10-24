/*

 */
package controller;

import entity.Bitacora;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 */
public class BitacoraJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Bitacora> bitacora = new ArrayList<>();

    public BitacoraJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Bitacora> getBitacora() {
        try {
            bitacora = em.createNamedQuery("Bitacora.findAll", Bitacora.class).getResultList();
            return bitacora;
        } catch (Exception e) {
            return bitacora;
        }
    }

    public Bitacora LeerIdBitacora(Bitacora bita) {
        Bitacora bitacora = new Bitacora();
        try {
            bitacora = em.createNamedQuery("Bitacora.findByIdBitacora", Bitacora.class)
                    .setParameter("idBitacora", bita.getIdBitacora())
                    .getSingleResult();
            return bitacora;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return bitacora;
        }
    }

    public void insertarbitacoraJPA(Bitacora bitacoras) {
        try {
            em.persist(bitacoras);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*   public void mostrarbitacoraJPA(Bitacora bitacoras){
    try{
        em.merge(bitacoras);
        em.getTransaction().commit();
        em.close();
}
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }
      
   public void eliminarbitacoraJPA(Bitacora bitacoras){
    try{
        em.remove(em.merge(bitacoras));
        em.getTransaction().commit();
        em.close();
    }
    catch(Exception e){
        System.out.println(e.getMessage() );
    }  
    }*/
    // no has hecho nada aqui :P
    public BigInteger getClave() {
        BigInteger uno = new BigInteger("1");;
        BigInteger claveId = new BigInteger("0");
        try {
            claveId = (BigInteger) em.createNamedQuery("Bitacora.findMaxId").getSingleResult();
            System.out.println("Debe devolver algo");
            if (claveId != null) {
                claveId = claveId.add(uno);
                return claveId;
            } else {
                claveId = uno;
                return claveId;
            }
        } catch (Exception e) {
            System.out.println(e);
            claveId = uno;
            return claveId;
        }
    }
}