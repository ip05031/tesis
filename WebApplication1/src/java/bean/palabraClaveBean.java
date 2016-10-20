/*
 *
 */
/*
 *
 */
package bean;

import controller.PalabrasClavesJPA;
import entity.PalabraClave;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "palabraClaveBean")
@SessionScoped
public class palabraClaveBean implements Serializable {

    private List<PalabraClave> lPalabra = new ArrayList<>();
    private PalabrasClavesJPA palabraJPA;
    private int idpalabra;
    private String nombrepalabra;
    private PalabraClave pc;
    private PalabraClave editarpc;
    
    public PalabraClave getEditarpc() {
        return editarpc;
    }

    public void setEditarpc(PalabraClave editarpc) {
        this.editarpc = editarpc;
    }

    public int getIdpalabra() {
        return idpalabra;
    }

    public void setIdpalabra(int idpalabra) {
        this.idpalabra = idpalabra;
    }

    public String getNombrepalabra() {
        return nombrepalabra;
    }

    public void setNombrepalabra(String nombrepalabra) {
        this.nombrepalabra = nombrepalabra;
    }
    
    public void guardarpc(){
        pc= new PalabraClave() ;
        pc.setNombrep(nombrepalabra);
        palabraJPA = new PalabrasClavesJPA();
        pc.setIdPalabra(palabraJPA.aumentarIdpalabra()+1);
       
       // String accion = "Se inserto Palabra Clave" ;
       // String tabla = "Palabra Clave" ;
        //new bitacoraBean().guardarbitacora(tabla, accion);
        
        palabraJPA.guardarpcJPA(pc);
        nombrepalabra = "";
        
        this.Getir();
        this.setIdpalabra(0);
        this.setNombrepalabra(null);
        this.setlPalabra(null);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Palabra Clave Almacenada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
         
    }
        

    public List<PalabraClave> Getir() {
        palabraJPA = new PalabrasClavesJPA();
        lPalabra = palabraJPA.getPalabraClave();
        return lPalabra;
    }
    
   
    
    
    public List<String > completeTheme(String query) {
        palabraJPA = new PalabrasClavesJPA();
        List <String > lPalabra = palabraJPA.getPalabraClaveNombres();
        List<String > filteredThemes = new ArrayList<String>();
         
        for (int i = 0; i < lPalabra.size(); i++) {
            String skin = lPalabra.get(i);
            if(skin.toLowerCase().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }
         
        return filteredThemes;
    }
    public List<String> getirNombres() {
        palabraJPA = new PalabrasClavesJPA();
        List <String > lPalabra = palabraJPA.getPalabraClaveNombres();
        return lPalabra;
    }

    public palabraClaveBean() {
    }

    public List<PalabraClave> getlPalabra() {
        return lPalabra;
    }

    public void setlPalabra(List<PalabraClave> lPalabra) {
        this.lPalabra = lPalabra;
    }
    
    public void editpc(){
        
        palabraJPA = new PalabrasClavesJPA();
        palabraJPA.editpcJPA(editarpc);
    }
public void capturarpalabra(PalabraClave capturapal){
    this.editarpc=capturapal;
    

}
public void eliminarpc(PalabraClave epc){
        
        palabraJPA = new PalabrasClavesJPA();
        palabraJPA.eliminarpcJPA(epc);
    }
}
