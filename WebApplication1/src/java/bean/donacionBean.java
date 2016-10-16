/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.DonacionJPA;
import controller.DonantesJPA;
import controller.TipoUsuarioJPA;
import entity.Donaciones;
import entity.Donate;
import entity.TipoUsuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

@Named(value = "donacionBean")
@SessionScoped
public class donacionBean implements Serializable {

    private List<Donaciones> ldonaciones = new ArrayList<>();
    private DonacionJPA donacionJPA = new DonacionJPA();
    private Map<String, String> listadoDonantes = new HashMap<String, String>();
    private String destination = "C:\\us\\sistema\\donaciones";
    private String archivoDonacion;
    private int idDonacion;
    private Donate idDonante;
    private Donaciones donacionPdf;
    private Date fechaDonacion;
    private Donaciones donaciones;
    private Donaciones modDonaciones;
    private int donante;
    private String ruta_archivo;
    private InputStream streamArchivo;

    public Donaciones getDonacionPdf() {
        return donacionPdf;
    }

    public void setDonacionPdf(Donaciones donacionPdf) {
        this.donacionPdf = donacionPdf;
    }

    
    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public int getDonante() {
        return donante;
    }

    public void setDonante(int donante) {
        this.donante = donante;
    }

    public Donate getIdDonante() {
        return idDonante;
    }

    public List<Donaciones> getLdonaciones() {
        return ldonaciones;
    }

    public void setLdonaciones(List<Donaciones> ldonaciones) {
        this.ldonaciones = ldonaciones;
    }

    public DonacionJPA getDonacionJPA() {
        return donacionJPA;
    }

    public void setDonacionJPA(DonacionJPA donacionJPA) {
        this.donacionJPA = donacionJPA;
    }

    public String getArchivoDonacion() {
        return archivoDonacion;
    }

    public void setArchivoDonacion(String archivoDonacion) {
        this.archivoDonacion = archivoDonacion;
    }

    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public Donaciones getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(Donaciones donaciones) {
        this.donaciones = donaciones;
    }

    public Donaciones getModDonaciones() {
        return modDonaciones;
    }

    public void setModDonaciones(Donaciones modDonaciones) {
        this.modDonaciones = modDonaciones;
    }

    public donacionBean() {
        this.iterar();

    }

    public List<Donaciones> getir() {
        donacionJPA = new DonacionJPA();
        ldonaciones = donacionJPA.getDonaciones();
        this.iterar();
        return ldonaciones;
    }

    public Map<String, String> getListadoDonantes() {
        return listadoDonantes;
    }

    public void iterar() {
        List<Donate> lista = new ArrayList<>();
        lista = new DonantesJPA().getDonate();
        

        listadoDonantes = new HashMap<String, String>();

        for (int i = 0; i < lista.size(); i++) {

            Donate get = lista.get(i);

            listadoDonantes.put(get.getNombred(), get.getIdDonante().toString());

        }

    }

    ///////////////////////////////////////////////////////////////////////////////
    public void upload5(FileUploadEvent event) {
        try {
            this.streamArchivo = event.getFile().getInputstream();
            //this.setRuta_archivo(event.getFile().getFileName());
           
                    donacionJPA = new DonacionJPA();
                    this.setRuta_archivo("Donacion"+(donacionJPA.aumentarIdDonaciones() + 1)+".pdf");
                   
        } catch (IOException ex) {
            Logger.getLogger(donacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void upload6(FileUploadEvent event) {
        try {
            
            this.streamArchivo = event.getFile().getInputstream();
            //this.setRuta_archivo(event.getFile().getFileName());
            this.setRuta_archivo(modDonaciones.getArchivod());
            
             
        } catch (IOException ex) {
            Logger.getLogger(donacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    

     public void copyFile(String fileName, InputStream in) {
           try {
              System.out.println("nombre vacio"+fileName);
               if (fileName != null) {
                   // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
              
                int read = 0;
                byte[] bytes = new byte[1024];
              
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                    System.out.println("read:"+read);
                }
              
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
               }
                
                
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
    //////////////////////////////////////////////////////////////////////////////

    public void saveDonaciones() {
        copyFile(this.ruta_archivo, streamArchivo);
        donacionJPA = new DonacionJPA();
        donaciones = new Donaciones();
        Donate de = new Donate();
        
        de.setIdDonante(donante);
        donaciones.setIdDonacion(donacionJPA.aumentarIdDonaciones() + 1);
        donaciones.setIdDonante(de);
        donaciones.setFechadonacion(fechaDonacion);
        donaciones.setArchivod(ruta_archivo);
        
        
        donacionJPA.saveDonaciones(donaciones);
        this.getir();
        de.setIdDonante(0);
        this.setIdDonacion(0);
        this.setFechaDonacion(null);
        this.setArchivoDonacion(null);
        this.setRuta_archivo(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Donacion creada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('nuevaDonacion').hide();");
        System.out.println("entro");
        
        
        
       
        
        
        

        //////////////////////////////////////////////////77
    }

    public void updDonacion() {
        
        donacionJPA = new DonacionJPA();

        donacionJPA.updateDonaciones(modDonaciones);
        copyFile(this.ruta_archivo, streamArchivo);
        System.out.println(this.ruta_archivo);
        this.setIdDonacion(0);
        this.setFechaDonacion(null);
        this.setArchivoDonacion(null);
        this.setRuta_archivo(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Donacion modificada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('modDonacion').hide();");

    }

    public void dltDonacion(Donaciones don) {
        donacionJPA = new DonacionJPA();
        donacionJPA.deleteDonaciones(don);

        this.setIdDonacion(0);
        this.setFechaDonacion(null);
        this.setArchivoDonacion(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Donacion eliminada exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        //RequestContext context = RequestContext.getCurrentInstance();
        //context.execute("PF('dltDonacion').hide();");
    }

    public void LeerId(Donaciones don) {
        DonacionJPA jpa;
        Donaciones temp;
        try {
            jpa = new DonacionJPA();
            temp = jpa.LeerIdDonaciones(don);
            if (temp != null) {
                this.modDonaciones = temp;

            }

        } catch (Exception e) {
        }
    }
    
    public void verPDF(Donaciones pdfDon ){
        
    this.donacionPdf = pdfDon;
    
}

}
