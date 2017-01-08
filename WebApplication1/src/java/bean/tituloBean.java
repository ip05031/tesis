package bean;

import controller.PalabrasClavesJPA;
import controller.TituloJPA;
import entity.PalabraClave;
import entity.Titulo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named(value = "tituloBean")
@SessionScoped
public class tituloBean implements Serializable {

    private List<Titulo> listaTitulos = new ArrayList<>();
    private Integer idTitulo;
    private String titu;
    private TituloJPA tituloJPA;
    private Titulo titulo;
    private Titulo modifi;

    public tituloBean() {
        titulo = new Titulo();
        modifi = new Titulo();
        //    listaTitulos = new ArrayList<>();
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Titulo getModifi() {
        return modifi;
    }

    public void setModifi(Titulo modifi) {
        this.modifi = modifi;
    }

    public List<Titulo> getListaTitulos() {
        return listaTitulos;
    }

    public void setListaTitulos(List<Titulo> listaTitulos) {
        this.listaTitulos = listaTitulos;
    }

    public Integer getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }

    public String getTitu() {
        return titu;
    }

    public void setTitu(String titu) {
        this.titu = titu;
    }

    public List<Titulo> listaTitulos() {
        TituloJPA ti = new TituloJPA();
        return ti.getTitulos();
    }

    ////////////// metodo guardar, editar y eleiminar///////////////////////// 
    ////////////// falta arreglar lo de guardar //////////////////////////////
    public void guardarTitulo() {
        try {
            TituloJPA ti = new TituloJPA();
            titulo.setIdTitulo(ti.getClave() + 1);
            titulo.setTituloRevista(titu);
            ti = new TituloJPA();
            ti.saveTitulo(titulo);

            this.Getir();
            titulo = new Titulo();
            FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Título creado con éxito."));
            titu ="";
             RequestContext.getCurrentInstance().execute("PF('ingresarTitulo').hide();");
        } catch (Exception e) {
            System.out.println("error");
        }
        
    }

    public List<Titulo> Getir() {
        tituloJPA = new TituloJPA();
        listaTitulos = tituloJPA.getTitulos();
        return listaTitulos;
    }

    public void edititulo() {
        tituloJPA = new TituloJPA();
        tituloJPA.editituloJPA(modifi);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Titulo Editato exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
         RequestContext.getCurrentInstance().execute("PF('modificartitulo').hide();");
    }

    public void capturartitulo(Titulo capturatitulo) {
        this.modifi = capturatitulo;
    }

    public void eliminarTitulo(Titulo titulos) {

        tituloJPA = new TituloJPA();
        tituloJPA.deleteTituloJPA(titulos);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Titulo Eliminado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void validarTitulo() {
        tituloJPA = new TituloJPA();
        String titulo = this.getTitu();
        System.out.println("titulo:");
        System.out.println(this.getTitu());
        if (titulo.length() > 0) {
            System.out.println("comineza la validacion de titulo");
            if (tituloJPA.searchTitulo(titulo)) {
                FacesContext.getCurrentInstance().addMessage("Message2",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El titulo ya está registrada"));
            }
        }
    }

}
