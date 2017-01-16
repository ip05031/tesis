/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CategoriaJPA;
import controller.PantallaJPA;
import entity.Pantalla;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.validator.internal.util.logging.Log_$logger;
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
    private Boolean verdad;

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

    public void leerId(Pantalla origenPantalla) {
        //System.out.println("id : " + origenPantalla.getIdPantalla()+"");
        pantallaJPA = new PantallaJPA();
        Pantalla temporal = new Pantalla();

        try {
            temporal = pantallaJPA.leerId(origenPantalla);
            if (temporal != null) {
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
        try {
            pantallaJPA = new PantallaJPA();
            pantalla = new Pantalla();
            String txt1 = nombrePantalla.trim();
            String txt2 = urlPantalla.trim();

            if (txt1.compareToIgnoreCase("") == 0 || txt2.compareToIgnoreCase("") == 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Ingrese un nombre de pantalla y/o url de pantalla!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                // FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ingrese un nombre de pantalla y/o url de pantalla"));
                //FacesContext.getCurrentInstance().addMessage("ulr_pantalla_n", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ingrese nombre de pantalla"));
                //FacesContext.getCurrentInstance().addMessage("name_pantalla_n", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ingrese url de pantalla"));

            } else {
                validarAccesoPa(1);
                if (this.verdad) {
                    pantallaJPA = new PantallaJPA();
                    int id = pantallaJPA.obtenerUltimoId();

                    pantalla.setIdPantalla(id);
                    pantalla.setNombrepa(nombrePantalla);
                    pantalla.setAccesopa(urlPantalla);
                    pantalla.setPermiso(true);
                    pantallaJPA.savePantalla(pantalla);

                    this.getir();
                    this.setIdPantalla(0);
                    this.setNombrePantalla("");
                    this.setUrlPantalla("");
                    this.setLpantalla(null);                    
                    //FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Pantalla creada con éxito."));
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!Pantalla registrada exitosamente!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').hide();");
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Pantalla ya esta registrada!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            // buscar un metodo para redirigir a la página de error
        }

    }

    public void modificarPantalla() {
        pantallaJPA = new PantallaJPA();

        try {
            if (this.pantallaActualizar.getNombrepa().trim().compareToIgnoreCase("") == 0
                    || this.pantallaActualizar.getAccesopa().trim().compareToIgnoreCase("") == 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Ingrese un nombre de pantalla y/o url de pantalla!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                pantallaJPA.updatePantalla(this.pantallaActualizar);
                this.getir();
                this.setIdPantalla(0);
                this.setNombrePantalla(null);
                this.setUrlPantalla(null);
                this.setLpantalla(null);
                // FacesContext.getCurrentInstance().addMessage("Message3", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Pantalla modificada con éxito!"));
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!Registro modificado con exito!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                RequestContext.getCurrentInstance().execute("PF('modalPantallaUpdate').hide();");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void eliminarPantalla(Pantalla pant) {
        try {
            pantallaJPA = new PantallaJPA();
            pantallaJPA.deletePantalla(pant);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!Registro eliminado con exito!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            //FacesContext.getCurrentInstance().addMessage("Message4", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Pantalla eliminada con éxito."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void validarNombrePantalla(int tipo) {
        pantallaJPA = new PantallaJPA();
        String nombrepantalla = this.getNombrePantalla();
        if (nombrepantalla.length() > 0) {
            if (pantallaJPA.searchPantalla(1, nombrepantalla)) {
                if (tipo == 1) {
                    RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", true);");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Nombre de pantalla ya existe!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    //FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nombre de pantalla ya existe"));
                } else {
                    RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", true);");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Nombre de pantalla ya existe!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    // FacesContext.getCurrentInstance().addMessage("Message3", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nombre de pantalla ya existe"));
                }

            } else {
                RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", false);");
            }
        }
    }

    public void validarAccesoPa(int tipo) {
        this.verdad = true;
        pantallaJPA = new PantallaJPA();
        String acceso = this.getUrlPantalla();
        if (acceso.length() > 0) {
            if (pantallaJPA.searchPantalla(2, acceso)) {
                this.verdad = false;
                if (tipo == 1) {
                    RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", true);");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!Url de pantalla ya existe!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    //FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Url de pantalla ya existe"));
                } else {
                    RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", true);");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!Url de pantalla ya existe!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    //FacesContext.getCurrentInstance().addMessage("Message3", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Url de pantalla ya existe"));
                }

            } else {
                RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", false);");
            }
        }
    }
}
