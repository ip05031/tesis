/*
 */
package bean;

import controller.AutorJPA;
import entity.Autor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named(value = "autorBean")
@SessionScoped
public class autorBean implements Serializable {

    private List<Autor> lAutor = new ArrayList<>();
    private AutorJPA autorJPA;
    private int idautor;
    private String nombreautor;
    private Autor tor;
    private Autor editarautor;
    private boolean verdad;
    private String tempA;

    public List<Autor> getlAutor() {
        return lAutor;
    }

    public void setlAutor(List<Autor> lAutor) {
        this.lAutor = lAutor;
    }

    public int getIdautor() {
        return idautor;
    }

    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }

    public String getNombreautor() {
        return nombreautor;
    }

    public void setNombreautor(String nombreautor) {
        this.nombreautor = nombreautor;
    }

    public Autor getTor() {
        return tor;
    }

    public void setTor(Autor tor) {
        this.tor = tor;
    }

    public Autor getEditarautor() {
        return editarautor;
    }

    public void setEditarautor(Autor editarautor) {
        this.editarautor = editarautor;
    }

    ////////////// metodo guardar, editar y eleiminar///////////////////////// 
    public void guardarautor() {
        validarCategoria();
        if (this.verdad) {
            tor = new Autor();
            tor.setNombreAutor(nombreautor);
            autorJPA = new AutorJPA();
            tor.setIdAutor(autorJPA.aumentarIdautor() + 1);
            autorJPA.guardarautorJPA(tor);
            nombreautor = "";

            this.getir();
            this.setIdautor(0);
            this.setNombreautor(null);
            this.setlAutor(null);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Autor Almacenado exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('ingresarAutor').hide();");
        }

    }

    public List<Autor> getir() {
        autorJPA = new AutorJPA();
        lAutor = autorJPA.getAutor();
        return lAutor;
    }

    public autorBean() {
        this.verdad = true;
    }

    public void editautor() {
        if (tempA.contentEquals(editarautor.getNombreAutor())) {
            autorJPA = new AutorJPA();
            autorJPA.editautorJPA(editarautor);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Autor modificado Exitosamente!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            RequestContext.getCurrentInstance().execute("PF('modificarautor').hide();");
        } else {
            validarCategoria();
            if (this.verdad) {
                autorJPA = new AutorJPA();
                autorJPA.editautorJPA(editarautor);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Autor modificado Exitosamente!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                RequestContext.getCurrentInstance().execute("PF('modificarautor').hide();");
            }
        }
    }

    public void capturarautor(Autor capturautor) {
        this.tempA = capturautor.getNombreAutor();
        this.editarautor = capturautor;

    }

    public void eliminarautor(Autor autorr) {

        autorJPA = new AutorJPA();
        autorJPA.eliminarautorJPA(autorr);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Autor Eliminado Exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void validarCategoria() {
        try {
            this.verdad = true;
            autorJPA = new AutorJPA();
            String autor = "";
            if (this.getNombreautor() != null) {
                autor = this.getNombreautor();
            } else {
                autor = this.editarautor.getNombreAutor();
            }
            if (autor.length() > 0) {
                if (autorJPA.searchCategora(autor)) {
                    verdad = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡EL nombre del autor esta asignado!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        } catch (Exception e) {
        }
    }
}
