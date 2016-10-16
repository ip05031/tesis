/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author IPalacios
 */
@Named(value = "perfilBean")
@SessionScoped
public class PerfilBean implements Serializable {
    private Usuario logueado = new Usuario();
    public PerfilBean() {
    }

    
    public void cargaDatos(){
        
        this.setLogueado( (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logueado") ) ;
    }
    
    
    
    public Usuario getLogueado() {
        return logueado;
    }

    public void setLogueado(Usuario logueado) {
        this.logueado = logueado;
    }
    
    
}
