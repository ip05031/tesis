/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.PantallaJPA;
import entity.Pantalla;
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
 * @author Joao
 */
@Named(value = "pantallaBean")
@SessionScoped
public class pantallaBean implements Serializable {

    private List<Pantalla> lpantalla = new ArrayList<>();
    private PantallaJPA pantallaJPA;
    private String nombrePantalla;
    private String urlPantalla;
    private int idPantalla;
    private Pantalla pantalla;
    private Pantalla pantallaActualizar;

    public Pantalla getPantallaActualizar() {
        return pantallaActualizar;
    }

    public void setPantallaActualizar(Pantalla pantallaActualizar) {
        this.pantallaActualizar = pantallaActualizar;
    }

    public List<Pantalla> getLpantalla() {
        return lpantalla;
    }

    public void setLpantalla(List<Pantalla> lpantalla) {
        this.lpantalla = lpantalla;
    }

    public String getNombrePantalla() {
        return nombrePantalla;
    }

    public void setNombrePantalla(String nombrePantalla) {
        this.nombrePantalla = nombrePantalla;
    }

    public String getUrlPantalla() {
        return urlPantalla;
    }

    public void setUrlPantalla(String urlPantalla) {
        this.urlPantalla = urlPantalla;
    }

    public int getIdPantalla() {
        return idPantalla;
    }

    public void setIdPantalla(int idPantalla) {
        this.idPantalla = idPantalla;
    }

    public pantallaBean() {

    }
    
    public void leerId( Pantalla origenPantalla  ){
        //System.out.println("id : " + origenPantalla.getIdPantalla()+"");
        pantallaJPA = new PantallaJPA();
        Pantalla temporal = new Pantalla();
        
        try {
            temporal = pantallaJPA.leerId(origenPantalla);
            if ( temporal != null) {
                this.pantallaActualizar = temporal;
            }
        } catch (Exception e) {
        }
        
         
    }
    
    
    public List<Pantalla> getir() {
        pantallaJPA = new PantallaJPA();
        lpantalla = pantallaJPA.getPantalla();
        return lpantalla;
    }

    public void savePantalla() {
        pantallaJPA = new PantallaJPA();
        pantalla = new Pantalla();
        int id = pantallaJPA.obtenerUltimoId();
        
        pantalla.setIdPantalla(id);
        pantalla.setNombrepa(nombrePantalla);
        pantalla.setAccesopa(urlPantalla);
        pantallaJPA.savePantalla(pantalla);
        
        this.getir();
        this.setIdPantalla(0);
        this.setNombrePantalla(null);
        this.setUrlPantalla(null);
        this.setLpantalla(null);
        RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').hide();");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Pantalla Creada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
   
    public void modificarPantalla(){
        pantallaJPA = new PantallaJPA();
        pantallaJPA.updatePantalla(this.pantallaActualizar);
        this.getir();
        
        this.setIdPantalla(0);
        this.setNombrePantalla(null);
        this.setUrlPantalla(null);
        this.setLpantalla(null);
         RequestContext.getCurrentInstance().execute("PF('modalPantallaUpdate').hide();");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Pantalla Modificada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
      public void eliminarPantalla(Pantalla pant){
        pantallaJPA = new PantallaJPA();
        pantallaJPA.deletePantalla(pant);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Pantalla Eliminada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }  
    
}
