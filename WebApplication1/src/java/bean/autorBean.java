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


@Named(value = "autorBean")
@SessionScoped
public class autorBean implements Serializable {

    private List<Autor> lAutor = new ArrayList<>();
    private AutorJPA autorJPA;
    private int idautor;
    private String nombreautor;
    private Autor tor;
    private Autor editarautor;

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
    
    public void guardarautor(){
        tor= new Autor();
        tor.setNombreAutor(nombreautor);
        autorJPA = new AutorJPA();
        tor.setIdAutor(autorJPA.aumentarIdautor()+1);
        autorJPA.guardarautorJPA(tor);
        nombreautor = "";
        
        this.getir();
        this.setIdautor(0);
        this.setNombreautor(null);
        this.setlAutor(null);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Autor Almacenado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
         
    }
    
    public List<Autor> getir() {
        autorJPA = new AutorJPA();
        lAutor = autorJPA.getAutor();
        return lAutor;
    }
    
    public autorBean() {
    }
    
    
    public void editautor(){
        
        autorJPA = new AutorJPA();
        autorJPA.editautorJPA(editarautor);
    }
public void capturarautor(Autor capturautor){
    this.editarautor=capturautor;
    

}
public void eliminarautor(Autor autorr){
        
        autorJPA = new AutorJPA();
        autorJPA.eliminarautorJPA(autorr);
    }
}
