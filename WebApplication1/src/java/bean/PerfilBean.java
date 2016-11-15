/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.UsuarioJPA;
import entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IPalacios
 */
@Named(value = "perfilBean")
@SessionScoped
public class PerfilBean implements Serializable {

    private Usuario perfilUsuario = new Usuario();
    private UsuarioJPA usuarioJPA;
    private String contra;
    private Date fecha_nac;

    public PerfilBean() {
        perfilUsuario = new Usuario();
    }

    public void cargarPerfil() {
        System.out.println("Cargar Perfil");
        FacesContext context = FacesContext.getCurrentInstance();
        perfilUsuario = (Usuario) context.getExternalContext().getSessionMap().get("logueado");
        fecha_nac = perfilUsuario.getFechanacimientou();
        System.out.println(perfilUsuario.getNickname());
        RequestContext.getCurrentInstance().update("perfil_user");
        System.out.println("fin cargar perfil");
    }

    public void modificarUsuario() {
        usuarioJPA = new UsuarioJPA();
        try {
            if (this.contra.compareTo("") != 0) {
                String otra = new usuarioBean().sha256(contra);
                perfilUsuario.setPassword(otra);
            }
            if (this.fecha_nac != null) {
                perfilUsuario.setFechanacimientou(this.fecha_nac);
            }
            usuarioJPA.updateUsuario(perfilUsuario);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Modificado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "Modificación realizada con éxito!"));
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "¡Ha ocurrido un problema!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("error en modificar usuario perfil bean");
        }

    }

    public Usuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Usuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

}
