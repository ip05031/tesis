/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Usuario;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author IPalacios
 */
public class bitacoraJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Usuario> listaUsuario = new ArrayList<>();

    public bitacoraJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

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
