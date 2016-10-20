/*
 *
 */
/*
 *
 */
package bean;

import controller.DonantesJPA;
import entity.Donate;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named(value = "donantesBean")
@SessionScoped
public class donantesBean implements Serializable {

    private List<Donate> lDonante = new ArrayList<>();
    private DonantesJPA donantesJPA;
    private int iddonante;
    private String nombredonante;
    private String dirdonante;
    private String teldonante;
    private String corrdonante;
    private Donate nomd;
    private Donate editardon;

    public Donate getEditardon() {
        return editardon;
    }

    public void setEditardon(Donate editardon) {
        this.editardon = editardon;
    }
    
    public int getIddonante() {
        return iddonante;
    }

    public void setIddonante(int iddonante) {
        this.iddonante = iddonante;
    }

    public String getNombredonante() {
        return nombredonante;
    }

    public void setNombredonante(String nombredonante) {
        this.nombredonante = nombredonante;
    }

    public String getDirdonante() {
        return dirdonante;
    }

    public void setDirdonante(String dirdonante) {
        this.dirdonante = dirdonante;
    }

    public String getTeldonante() {
        return teldonante;
    }

    public void setTeldonante(String teldonante) {
        this.teldonante = teldonante;
    }

    public String getCorrdonante() {
        return corrdonante;
    }

    public void setCorrdonante(String corrdonante) {
        this.corrdonante = corrdonante;
    }
    
    
   public donantesBean() {
       
  }

    public List<Donate> getlDonante() {
        return lDonante;
    }

    public void setlDonante(List<Donate> lDonante) {
        this.lDonante = lDonante;
    }
    
    public List<Donate> Getir() {
        donantesJPA = new DonantesJPA();
        lDonante = donantesJPA.getDonate();
        return lDonante;
    }
    
    public void guardardonante(){
        nomd= new Donate() ;
        nomd.setNombred(nombredonante);
        nomd.setDireccion(dirdonante);
        nomd.setTelefono(teldonante);
        nomd.setCorreodonate(corrdonante);
        donantesJPA = new DonantesJPA();
        nomd.setIdDonante(donantesJPA.aumentarIddonante()+1);
        
        String accion = "Registro de nuevo Donante" ;
        String tabla = "Donante" ;
        new bitacoraBean().guardarbitacora(tabla, accion);
        
        donantesJPA.guardardonanteJPA(nomd);
        
        
        
        
        this.Getir();
        this.setIddonante(0);
        this.setNombredonante(null);
        this.setDirdonante(null);
        this.setTeldonante(null);
        this.setCorrdonante(null);
        this.setlDonante(null);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Donante Almacenado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    public void editardonante(){
        
        donantesJPA = new DonantesJPA();
        String accion = "Modificacion de datos en Donante" ;
        String tabla = "Donante" ;
        new bitacoraBean().guardarbitacora(tabla, accion);
        donantesJPA.editdonanteJPA(editardon);
    }
    
public void capturardonante(Donate capturardon){
    this.editardon=capturardon;
    

}


public void eliminardonante(Donate eliminardon){
        
        donantesJPA = new DonantesJPA();
        donantesJPA.eliminardonanteJPA(eliminardon);
    }
}
