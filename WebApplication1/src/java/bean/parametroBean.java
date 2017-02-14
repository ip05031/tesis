/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.ParametroJPA;
import entity.Parametro;
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
@Named(value = "parametroBean")
@SessionScoped
public class parametroBean implements Serializable {

    private List<Parametro> lparametro = new ArrayList<>();
    private Parametro parametro;
    private Parametro tempParametro;
    private Boolean validar;
    private String id;
    private String valor;
    private String descrip;

    public parametroBean() {
    }

    public List<Parametro> getLparametro() {
        return lparametro;
    }

    public void setLparametro(List<Parametro> lparametro) {
        this.lparametro = lparametro;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public Parametro getTempParametro() {
        return tempParametro;
    }

    public void setTempParametro(Parametro tempParametro) {
        this.tempParametro = tempParametro;
    }

    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void limpiar() {
        parametro = new Parametro();
    }

    //Funcion de guardado
    public List<Parametro> listaParametros() {
        lparametro = null;
        try {
            ParametroJPA parametroJPA = new ParametroJPA();
            lparametro = parametroJPA.getParametro();
        } catch (Exception e) {
        }

        return lparametro;
    }

    public void guardarParametro() {
        validarCategoria();
        if (validar) {
            ParametroJPA parametroJPA = new ParametroJPA();
            parametroJPA.saveParametroJPA(parametro);
            parametro = new Parametro();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡El registro a sido guardo exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('ingresarParametro').hide();");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡El Identificador de parametro ya existe!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

    }

    public void selecionarParametro(Parametro p) {
        this.parametro = p;
        this.tempParametro = p;

    }

    public void mergeParamtro() {
        if (tempParametro.getIdParametro().equalsIgnoreCase(parametro.getIdParametro())) {
            ParametroJPA parametroJPA = new ParametroJPA();
            parametroJPA.saveParametroJPA(parametro);
            parametro = new Parametro();
            RequestContext.getCurrentInstance().execute("PF('modificarParametro').hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Registro almacenado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else {
            validarCategoria();
            if (validar) {
                ParametroJPA parametroJPA = new ParametroJPA();
                parametroJPA.saveParametroJPA(parametro);
                parametro = new Parametro();
                RequestContext.getCurrentInstance().execute("PF('modificarParametro').hide();");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Registro almacenado exitosamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡El Identificador de parametro ya existe!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

    }

    public void validarCategoria() {
        this.validar = true;
        ParametroJPA parametroJPA = new ParametroJPA();
        String parametros = parametro.getIdParametro();
        if (parametros.length() > 0) {
            if (parametroJPA.leerParametro(parametros)) {
                this.validar = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡El Identificador de parametro ya existe!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

}
