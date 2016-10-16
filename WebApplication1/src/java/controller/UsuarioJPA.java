package controller;

import entity.Usuario;
import entity.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class UsuarioJPA {

    EntityManager em;
    EntityManagerFactory emf;
    private List<Usuario> listaUsuario = new ArrayList<>();

    public UsuarioJPA() {
        emf = Persistence.createEntityManagerFactory("WebApplication1PU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Usuario> getUsuarios(int tipo) {
        String namedquery = "Usuario.findAll";
        switch (tipo) {
            case 1:
                namedquery = "Usuario.findVisitor";
                break;
            case 2:
                namedquery = "Usuario.findEmployee";
                break;
            case 3:
                namedquery = "Usuario.findAll";
                break;
            default:
                namedquery = "Usuario.findAll";
                break;
        }
        try {
            if (tipo == 1 || tipo == 2) {
                Integer i = 5;
                listaUsuario = em.createNamedQuery(namedquery, Usuario.class).setParameter("idTusuario", i)
                        .getResultList();
            } else {
                listaUsuario = em.createNamedQuery(namedquery, Usuario.class)
                        .getResultList();
            }
            return listaUsuario;
        } catch (Exception e) {
            System.out.println("Error en UsuarioJPA");
            System.out.println(e.getMessage());
            return listaUsuario;
        }
    }

    public void saveUsuario(Usuario usuario) {
        try {

            em.persist(usuario);
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ha ocurrido un error!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("Error en SaveUsuario JPA");
            System.out.println(e.getMessage());
        } finally {

        }
    }

    public Integer getClave() {
        Integer claveId = 0;
        try {
            claveId = (Integer) em.createNamedQuery("Usuario.findMaxId").getSingleResult();
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

    public void updateUsuario(Usuario user) {
        try {

            em.merge(user);
            //em.remove(em.merge(pant))
            em.getTransaction().commit();
            //em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
