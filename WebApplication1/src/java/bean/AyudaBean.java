/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Admin
 */

@Named(value = "AyudaBean")
@SessionScoped

public class AyudaBean implements Serializable{
   
private String ayudaPDF;

    public String getAyudaPDF() {
        return ayudaPDF;
    }

    public void setAyudaPDF(String ayudaPDF) {
        this.ayudaPDF = ayudaPDF;
    }

    public AyudaBean() {
        this.ayudaPDF = "/ayuda/null.pdf";
       
    }
    
    public void direccionPDF(Integer A)
    {
        
        switch(A)
        {
          case 1: this.ayudaPDF = "/ayuda/adquisición.pdf"; break;
          case 2: this.ayudaPDF = "/ayuda/busqueda.pdf"; break;
          case 3: this.ayudaPDF = "/ayuda/contactanos.pdf"; break;
          case 4: this.ayudaPDF = "/ayuda/evento.pdf"; break;
          case 5: this.ayudaPDF = "/ayuda/importación.pdf"; break;
          case 6: this.ayudaPDF = "/ayuda/sesión.pdf"; break;
          case 7: this.ayudaPDF = "/ayuda/inventario.pdf"; break;
          case 8: this.ayudaPDF = "/ayuda/prestamo.pdf"; break;
          case 9: this.ayudaPDF = "/ayuda/reportes.pdf"; break;
          case 10: this.ayudaPDF = "/ayuda/revista.pdf"; break;
          case 11: this.ayudaPDF = "/ayuda/usuario.pdf"; break;
          default:this.ayudaPDF = "/ayuda/null.pdf"; break;
          
        }
    }
            

    
    
    
}
