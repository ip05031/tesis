/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.PantallaJPA;
import controller.TipoUsuarioJPA;
import entity.Pantalla;
import entity.TipoUsuario;
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
 * @author IPalacios
 */
@Named(value = "tipoUsuarioBean")
@SessionScoped
public class tipoUsuarioBean implements Serializable {

    private List<TipoUsuario> ltipousuario = new ArrayList<>();
    private TipoUsuarioJPA tipoUsuarioJPA = new TipoUsuarioJPA();
    private TipoUsuario tipo;
    private List<Pantalla> origenPantallas = new ArrayList<>();
    private List<Pantalla> tempLista = new ArrayList<>();
    private List<Pantalla> destinoPantallas = new ArrayList<>();

    public List<Pantalla> getTempLista() {
        return tempLista;
    }

    public void setTempLista(List<Pantalla> tempLista) {
        this.tempLista = tempLista;
    }

    public List<Pantalla> getOrigenPantallas() {
        return origenPantallas;
    }

    public void setOrigenPantallas(List<Pantalla> origenPantallas) {
        this.origenPantallas = origenPantallas;
    }

    public List<Pantalla> getDestinoPantallas() {
        return destinoPantallas;
    }

    public void setDestinoPantallas(List<Pantalla> destinoPantallas) {
        this.destinoPantallas = destinoPantallas;
    }

    public tipoUsuarioBean() {
        this.tipo = new TipoUsuario();
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public List<TipoUsuario> getLtipousuario() {
        return ltipousuario;
    }

    public void setLtipousuario(List<TipoUsuario> ltipousuario) {
        this.ltipousuario = ltipousuario;
    }

    public List<TipoUsuario> getir() {
        tipoUsuarioJPA = new TipoUsuarioJPA();
        ltipousuario = tipoUsuarioJPA.getTipoUsuario();
        return ltipousuario;
    }

    public void saveTipo() {

        tipoUsuarioJPA = new TipoUsuarioJPA();
        if (destinoPantallas.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Tipo de Usuario debe Elegir patalla para asignar!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            tipo.setIdTusuario(tipoUsuarioJPA.getClave() + 1);
            tipo.setPantallaList(this.destinoPantallas);
            tipoUsuarioJPA.savePantalla(tipo);
            tipo = new TipoUsuario();
            this.origenPantallas = new ArrayList<>();
            this.tempLista = new ArrayList<>();
            this.destinoPantallas = new ArrayList<>();
            RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Tipo de Usuario Guardado Exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public List<Pantalla> getListaPantalla() {
        PantallaJPA panJPA = new PantallaJPA();
        this.origenPantallas = panJPA.getPantalla();
        return origenPantallas;
    }

    public void nuevoDato() {
        PantallaJPA panJPA = new PantallaJPA();

        this.origenPantallas = new ArrayList<>();
        this.tempLista = new ArrayList<>();
        this.destinoPantallas = new ArrayList<>();

        this.origenPantallas = panJPA.getPantalla();

        //  RequestContext.getCurrentInstance().execute("PF('nuevaPantalla').show();");
    }

    public void cancelarTodo() {
        this.origenPantallas = new ArrayList<>();
        this.tempLista = new ArrayList<>();
        this.destinoPantallas = new ArrayList<>();
        tipo = new TipoUsuario();
    }

    public void adaptarTipoUsuario(TipoUsuario tip) {

        this.tipo = tip;
        this.origenPantallas = new ArrayList<>();
        this.tempLista = new ArrayList<>();
        this.destinoPantallas = new ArrayList<>();
        PantallaJPA panJPA = new PantallaJPA();
        this.origenPantallas = panJPA.getPantalla();
        this.tempLista = tipo.getPantallaList();
        this.destinoPantallas.addAll(tempLista);
        this.origenPantallas.removeAll(tempLista);

    }

    public void mergeTipoUsuario() {
        if (destinoPantallas.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Debe Agregar almenos una pantalla!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            tipoUsuarioJPA = new TipoUsuarioJPA();
            this.tipo.setPantallaList(this.destinoPantallas);
            tipoUsuarioJPA.mergeTipoUsuario(this.tipo);
            tipo = new TipoUsuario();
            this.origenPantallas = new ArrayList<>();
            this.tempLista = new ArrayList<>();
            this.destinoPantallas = new ArrayList<>();
            RequestContext.getCurrentInstance().execute("PF('dlg2').hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Tipo de Usuario Guardado Exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void trasferirLista() {
        this.destinoPantallas.addAll(tempLista);
        this.origenPantallas.removeAll(tempLista);
        this.tempLista = new ArrayList<>();
    }

    public void trasferirEliminar() {
        this.destinoPantallas.removeAll(tempLista);
        this.origenPantallas.addAll(tempLista);
        this.tempLista = new ArrayList<>();
    }

    public void trasferirModificar() {
        PantallaJPA panJPA = new PantallaJPA();
        this.origenPantallas = panJPA.getPantalla();
        this.tempLista = tipo.getPantallaList();
        this.destinoPantallas.addAll(tempLista);
        this.origenPantallas.removeAll(tempLista);

    }

    public void eliminar(TipoUsuario tipo) {
        tipoUsuarioJPA = new TipoUsuarioJPA();
        tipoUsuarioJPA.deleteTipoUsuario(tipo);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Tipo de Usuario Eliminado Exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("newPantalla");
    }
}
