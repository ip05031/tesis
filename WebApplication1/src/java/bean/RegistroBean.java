/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.InicioJPA;
import controller.UsuarioJPA;
import entity.Inventario;
import entity.Revista;
import entity.TipoUsuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import entity.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javaUtil.EMail;
import javaUtil.Mailer;
import sun.util.calendar.Gregorian;

/**
 *
 * @author IPalacios
 */
@Named(value = "registroBean")
@SessionScoped
public class RegistroBean implements Serializable {

    private Usuario newUser = new Usuario();
    private UsuarioJPA usuarioJPA;

    /**
     * Creates a new instance of RegistroBean
     */
    public RegistroBean() {

    }

    public void nuevoVisitante() {

        try {
            System.out.println("si llegó");
            String contra1 = newUser.getPassword();
            String nuevaContra = new usuarioBean().sha256(contra1);
            newUser.setPassword(nuevaContra);
            usuarioJPA = new UsuarioJPA();
            TipoUsuario tu = new TipoUsuario();
            tu.setIdTusuario(5);

            Calendar calendario = GregorianCalendar.getInstance();
            Date fechaRegistro = calendario.getTime();

            this.newUser.setIdTusuario(tu);
            this.newUser.setFechau(fechaRegistro);
            this.newUser.setIdUsuario(usuarioJPA.getClave());
            
            this.newUser.setEstadoi(1);
            //Codigo de aletorio de ingreso al usuario
            String inicio = getCadenaAlfanumAleatoria(8);
            int cantidad = newUser.getApellidosu().length();
            if (cantidad >= 4) {
                cantidad = 4;
            }
            String corta = newUser.getApellidosu().substring(cantidad - 1, cantidad);
            int it = (int) (Math.random() * 99 + 1);
            String uno = newUser.getIdUsuario() + "";
            uno = uno.substring(0, 1);
            String codigo = inicio + uno + it + corta;
            this.newUser.setClaveValidar(codigo);

            new UsuarioJPA().saveUsuario(newUser);
            //Codigo de Creacion de envio de mensaje 
            String claveEnviar = "http://localhost:8080/WebApplication1/faces/ValidarSubscriptor.xhtml?faces-redirect=true&claveSub=" + codigo;
            //Mensajeria de confirmacion
            Mailer cartero = new Mailer();
            String cuentaMail = "museomuna2016@gmail.com";
            String contra = "muna2016";
            String serv = "smtp.gmail.com";
            String puerto = "587";
            cartero.setCorreo(cuentaMail);
            cartero.setPass(contra);
            cartero.setSmtpHost(serv);
            cartero.setSmtpPort(puerto);

            EMail msj = new EMail();
            msj.setRemitente(cuentaMail);
            msj.getDestinatarios().add(newUser.getCorreou());
            msj.setAsunto("Envio de Código de confirmación sobre Subscripción al Portal de (MUNA)");
            String cuerpo = "Muchas Gracias por subcribirse al  portal del  Museo Nacional de Antropología Dr. David J. Guzmán (MUNA) solo queda un paso:\n"
                    + "Este correo es autogenerado; de clic sobre el link para poder su activar de su cuenta: \n" + claveEnviar
                    + "\n\n"
                    + "Atentamente \n"
                    + "Museo Nacional de Antropología Dr. David J. Guzmán \n"
                    + "Dirreción MUNA";;
            msj.setMensaje(cuerpo);
            cartero.enviarCorreo(msj, null);

            this.newUser = new Usuario();
            FacesContext.getCurrentInstance().addMessage("Message4", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "Revise su correo para continuar con el registro."));
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario Creado!", null);
            //FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            System.out.println("Error nuevo visitante");
            System.out.println(e.getMessage());
        }

    }

    public Usuario getNewUser() {
        return newUser;
    }

    public void setNewUser(Usuario newUser) {
        this.newUser = newUser;
    }

    /**
     *
     * @param longitud longitud de la cadena que se dese crear
     * @return
     */
    public String getCadenaAlfanumAleatoria(int longitud) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }

    public void validarUsuarioExiste() {
        usuarioJPA = new UsuarioJPA();
        String nickname = this.newUser.getNickname();
        if (nickname.length() > 3) {
            System.out.println("comineza la validacion");
            if (usuarioJPA.searchNickname(nickname)) {
                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Nombre de usuario no disponible"));
            } else {
                FacesContext.getCurrentInstance().addMessage("Message2", new FacesMessage(FacesMessage.SEVERITY_INFO, "!", "Usuario Válido"));
            }
        }
    }
}
