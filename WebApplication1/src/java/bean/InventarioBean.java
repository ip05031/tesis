/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.EstadoJPA;
import controller.InventarioJPA;
import entity.Estado;
import entity.Inventario;
import entity.Revista;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javaUtil.InventarioEstado;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Sanchez
 */
@Named(value = "inventarioBean")
@SessionScoped
public class InventarioBean implements Serializable {

    private List<Inventario> listaInventario = new ArrayList<>();
    private InventarioJPA inventarioJPA;

    private Revista revista = new Revista();
    private Inventario inventario= new Inventario();
    private int idRevista;
    private int idInventario;
    private int existencias;
    private int estado;
    private int buenEstado;
    private int malEstado;
    private List<InventarioEstado> listaInventarEstado = new ArrayList<>();

    public InventarioBean() {
        inventario = new Inventario();
    }

    // funciones
    /* --------------------------------------------------------------------------------------------------------------------- */
    public List<Inventario> getir() {
        inventarioJPA = new InventarioJPA();
        listaInventario = inventarioJPA.getInventarios();
        return listaInventario;
    }

    public void guardando() {

        int bandera = 0;
        for (InventarioEstado in : listaInventarEstado) {
            if (in.getCantidad() != 0) {
                bandera = 1;
            }
        }

        if (bandera == 1) {

            System.out.println("Guardando Inventario");
            inventarioJPA = new InventarioJPA();
            this.idInventario = inventarioJPA.getClave();
            for (InventarioEstado in : listaInventarEstado) {
                for (int i = 0; i < in.getCantidad(); i++) {
                    saveInventario(idInventario, in.getEstado());
                    ++idInventario;
                }

            }

            this.idInventario = 0;
            this.buenEstado = 0;
            this.malEstado = 0;
            this.idRevista = 1;
            int i = 0;
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Inventario Almacenado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('ingresarInventario').hide();");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Debe ingresar almenos una cantidad de revistas!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void asiganarModificacion(Inventario iv){
    this.inventario = iv;
    
    }
    
    public void saveModificar( Estado esta) {
        try {   
            inventario.setIdEstado(esta);
//aca ba el cambio       
            inventarioJPA = new InventarioJPA();
            inventarioJPA.updateInventario(inventario);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Inventario Modificado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('modificarInventario').hide();");
        } catch (Exception e) {
            System.out.println("error en save inventairo");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void saveInventario(int clave, Estado esta) {
        try {

            Inventario inv = new Inventario();
            Revista revista2 = new Revista();
            revista2.setIdRevista(this.idRevista);
            inv.setIdInventario(clave);
            inv.setIdRevista(revista2);
            inv.setExistenciai(0);
            inv.setIdEstado(esta);
//aca ba el cambio       
            inventarioJPA = new InventarioJPA();
            inventarioJPA.guardarInventario(inv);
        } catch (Exception e) {
            System.out.println("error en save inventairo");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    public void eliminarinvent(Inventario einv) {

        inventarioJPA = new InventarioJPA();
        inventarioJPA.eliminarinventJPA(einv);
    }
    
    public List<Estado> listaTemporalEstados() {
        EstadoJPA estadoJPA = new EstadoJPA();
        return estadoJPA.getEstado();
    }

    public List<InventarioEstado> listaTemporal() {
        EstadoJPA estadoJPA = new EstadoJPA();
        listaInventarEstado.clear();
        InventarioEstado tem = null;
        List<Estado> listaEstado = estadoJPA.getEstado();
        if (listaEstado.isEmpty()) {
            System.out.println("error no encontro listas");            
        }else{
        for (Estado es : listaEstado) {
            Integer can = 0;
            tem = new InventarioEstado(es, can);
            listaInventarEstado.add(tem);
        }}
        return listaInventarEstado;
    }

    // SETTER Y GETTER
    /* --------------------------------------------------------------------------------------------------------------------- */
    public List<Inventario> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<Inventario> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public InventarioJPA getInventarioJPA() {
        return inventarioJPA;
    }

    public void setInventarioJPA(InventarioJPA inventarioJPA) {
        this.inventarioJPA = inventarioJPA;
    }

    public int getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(int idRevista) {
        this.idRevista = idRevista;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getBuenEstado() {
        return buenEstado;
    }

    public void setBuenEstado(int buenEstado) {
        this.buenEstado = buenEstado;
    }

    public int getMalEstado() {
        return malEstado;
    }

    public void setMalEstado(int malEstado) {
        this.malEstado = malEstado;
    }

    public List<InventarioEstado> getListaInventarEstado() {
        return listaInventarEstado;
    }

    public void setListaInventarEstado(List<InventarioEstado> listaInventarEstado) {
        this.listaInventarEstado = listaInventarEstado;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    
}
