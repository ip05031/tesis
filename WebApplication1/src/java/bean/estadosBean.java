/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.EstadoJPA;
import entity.Estado;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Sanchez
 */
@Named(value = "estadosBean")
@SessionScoped
public class estadosBean implements Serializable {

    private List<Estado> lEstado = new ArrayList<>();
    private EstadoJPA estadoJPA;
    private int idestado;
    private String nombrestado;
    private Estado estad;
    private Estado editarestado;

    public List<Estado> getlEstado() {
        return lEstado;
    }

    public void setlEstado(List<Estado> lEstado) {
        this.lEstado = lEstado;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public String getNombrestado() {
        return nombrestado;
    }

    public void setNombrestado(String nombrestado) {
        this.nombrestado = nombrestado;
    }

    public Estado getEditarestado() {
        return editarestado;
    }

    public void setEditarestado(Estado editarestado) {
        this.editarestado = editarestado;
    }
////////////////////////// metodos para guardar editar y eliminar ///////////////////////////////
 
   public void guardarestado(){
        estad= new Estado() ;
        estad.setNombreEstado(nombrestado);
        estadoJPA = new EstadoJPA();
        estad.setIdEstado(estadoJPA.aumentarIdestado()+1);
        estadoJPA.saveEstadoJPA(estad);
        nombrestado = "";
        
        this.Getir();
        this.setIdestado(0);
        this.setNombrestado(null);
        this.setlEstado(null);
        RequestContext.getCurrentInstance().execute("PF('ingresarEstado').hide();");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Estado Almacenado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
         
    } 
   
   public List<Estado> Getir() {
        estadoJPA = new EstadoJPA();
        lEstado = estadoJPA.getEstado();
        return lEstado;
    }
    
   public estadosBean() {
    }

     public void editestado(){
        
        estadoJPA = new EstadoJPA();
        estadoJPA.editestadoJPA(editarestado);
        RequestContext.getCurrentInstance().execute("PF('modificarestado').hide();");
    }
     
     
public void capturarestado(Estado capturaestado){
    this.editarestado=capturaestado;
}

public void eliminarestado(Estado deletestado){
        
        estadoJPA = new EstadoJPA();
        estadoJPA.eliminarestadoJPA(deletestado);
    }

}
