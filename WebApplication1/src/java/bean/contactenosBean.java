/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javaUtil.EMail1;
import javaUtil.Mailer1;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.MessagingException;
import org.hibernate.validator.constraints.Email;
import org.primefaces.context.RequestContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@Named(value = "contactenosBean")
@SessionScoped
public class contactenosBean implements Serializable {

    private String cuentaMail;
    private String asuntoCorreo;
    private String mensajeCorreo;
    @Email(message = "Debe ingresar un correo con formato valido.")
    private String destin;
    private boolean catcha;

    public boolean isCatcha() {
        return catcha;
    }

    public void setCatcha(boolean catcha) {
        this.catcha = catcha;
    }
    

    public String getDestin() {
        return destin;
    }

    public void setDestin(String destin) {
        this.destin = destin;
    }

    public String getCuentaMail() {
        return cuentaMail;
    }

    public void setCuentaMail(String cuentaMail) {
        this.cuentaMail = cuentaMail;
    }

    public String getAsuntoCorreo() {
        return asuntoCorreo;
    }

    public void setAsuntoCorreo(String asuntoCorreo) {
        this.asuntoCorreo = asuntoCorreo;
    }

    public String getMensajeCorreo() {
        return mensajeCorreo;
    }

    public void setMensajeCorreo(String mensajeCorreo) {
        this.mensajeCorreo = mensajeCorreo;
    }

    public contactenosBean() {
         this.catcha=false;      

    }
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje enviado", "Gracias por esperar su mensaje ha sido enviado exitosamente.!!!");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
     public void submit() {
       // RequestContext.getCurrentInstance().execute("$(\".btn\" ).prop(\"disabled\", false);");
       this.catcha=true;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ahora ya puede enviar su mensaje", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }

    public void enviarCorreo() {
        this.showMessage();
        System.out.println("entro");
        Mailer1 cartero = new Mailer1();
        //findByRegistrado();
        /*
         1. Configuramos los parametros para enviar correos
         */
        //Cuenta general
        cartero.setCorreo("museomuna2016@gmail.com"); // 
        cartero.setPass("muna2016");

        //Datos del servidor de correos
        cartero.setSmtpHost("smtp.gmail.com");
        cartero.setSmtpPort("587");
        System.out.println("paso1");
        /*
         2. Configurar el correo a enviar
         */

        System.out.println("Solicitud de información");
        System.out.println(mensajeCorreo);
        EMail1 msj = new EMail1();
        msj.setRemitente("museomuna2016@gmail.com");
        System.out.println("paso2");
        //Seteando destinatarios

        msj.getDestinatarios().add("museomuna2016@gmail.com");

        msj.setAsunto("Solicitud de información");
        msj.setMensaje("Nombre solicitante: \n" + asuntoCorreo + "\n \n" + "correo: \n " + destin + "\n \n" + "Mensaje: \n" + mensajeCorreo);

        System.out.println("paso3");
          
         //FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_WARN, "Enviendo.... ¡Esto tomara un momento!", null);
         //FacesContext.getCurrentInstance().addMessage(null, message1);
         

        try {
           
     
            cartero.enviarCorreo(msj);
            //fmsj = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado.", "");
            this.setAsuntoCorreo(null);
            this.setCuentaMail(null);
            this.setDestin(null);
            this.setMensajeCorreo(null);
            this.catcha=false; 
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje enviado con exito.", "PrimeFaces Rocks."));
           // message1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Enviado Exitosamente!", null);
            //FacesContext.getCurrentInstance().addMessage(null, message1);
            
           
            System.out.println("envio");
            //cliDAO.merge(cli);
            //tx.commit();
        } catch (MessagingException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo envíar el correo.", null);
            System.out.println("no envio");
            System.out.println(ex);
        }

        //FacesContext.getCurrentInstance().addMessage(null, fmsj);
        //FacesContext.getCurrentInstance().addMessage(null, fmsjCliente);
    }

}
