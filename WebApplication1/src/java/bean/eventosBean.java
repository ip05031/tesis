/*
 */
package bean;

import controller.EventosJPA;
import controller.UsuarioJPA;
import entity.Evento;
import entity.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaUtil.EMail;
import javaUtil.Mailer;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 */
@Named(value = "eventosBean")
@SessionScoped
public class eventosBean implements Serializable {

    private List<Evento> leventos = new ArrayList<>();
    private EventosJPA eventosJPA = new EventosJPA();

    private Usuario idUsuario;
    private int idevento;
    private String nombrevento;
    private String lugarevento;
    private Date fechaevento;
    private Date horaevento;
    private String imagenevento;
    private Evento nomevento;
    private Evento editarevento;
    private int Usuario;

    /// varialbles de correo
    private String cuentaMail;
    private String contra;
    private String serv;
    private String puerto;
    private String encabezado;
    private String cuerpo;

    //codigo para subir archivo
    private String destination = "C:\\us\\sistema\\eventos\\";
    private InputStream streamArchivo;
    private String ruta_archivo;
    private Evento eventoPdf;

    public Evento getEventoPdf() {
        return eventoPdf;
    }

    public void setEventoPdf(Evento eventoPdf) {
        this.eventoPdf = eventoPdf;
    }

    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int Usuario) {
        this.Usuario = Usuario;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombrevento() {
        return nombrevento;
    }

    public void setNombrevento(String nombrevento) {
        this.nombrevento = nombrevento;
    }

    public String getLugarevento() {
        return lugarevento;
    }

    public void setLugarevento(String lugarevento) {
        this.lugarevento = lugarevento;
    }

    public Date getFechaevento() {
        return fechaevento;
    }

    public void setFechaevento(Date fechaevento) {
        this.fechaevento = fechaevento;
    }

    public Evento getNomevento() {
        return nomevento;
    }

    public void setNomevento(Evento nomevento) {
        this.nomevento = nomevento;
    }

    public Date getHoraevento() {
        return horaevento;
    }

    public void setHoraevento(Date horaevento) {
        this.horaevento = horaevento;
    }

    public String getImagenevento() {
        return imagenevento;
    }

    public void setImagenevento(String imagenevento) {
        this.imagenevento = imagenevento;
    }

    public Evento getEditarevento() {
        return editarevento;
    }

    public void setEditarevento(Evento editarevento) {
        this.editarevento = editarevento;
    }

    public eventosBean() {

    }

    public List<Evento> getLeventos() {
        return leventos;
    }

    public void setLeventos(List<Evento> leventos) {
        this.leventos = leventos;
    }

    public EventosJPA getEventosJPA() {
        return eventosJPA;
    }

    public List<Evento> Getir() {
        eventosJPA = new EventosJPA();
        leventos = eventosJPA.getEventos();

        return leventos;
    }

    public void guardarevento() {
        cuentaMail = "museomuna2016@gmail.com";
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateadorhora = new SimpleDateFormat("hh:mm a");
        String datos = "Saludos .\n"
                + "Por este medio se informa a nuestros subscriptores que el museo tendrá un evento.\n"
                + "El dia de " + formateador.format(fechaevento) + " se realizará el evento de " + nombrevento + "a las " + formateadorhora.format(horaevento) + " en el local " + lugarevento + " del MUNA.\n"
                + "\n"
                + "Se adjunta el afiche del evento.\n"
                + "Atentamente \n"
                + "Museo Nacional de Antropología Dr. David J. Guzmán \n"
                + "www.cultura.gob.sv/muna \n"
                + "Dirreción MUNA";
        EMail msj = new EMail();
        msj.setRemitente(cuentaMail);

        //Seteando destinatarios
        UsuarioJPA usuJpa = new UsuarioJPA();
        List<Usuario> listUsuario = usuJpa.getUsuarios(1);
        try {
            for (Usuario us : listUsuario) {
                if (us.getCorreou() != null) {
                    if(!us.getCorreou().isEmpty()){
                    msj.getDestinatarios().add(us.getCorreou());
                    }

                }

            }
        } catch (Exception e) {
        }

       
        msj.setAsunto("Evento en el Museo Nacional de Antropología Dr. David J. Guzmán : "+ nombrevento);
        msj.setMensaje(datos);
        msj.setImagen(ruta_archivo);

        copyFile(this.ruta_archivo, streamArchivo, msj);

        eventosJPA = new EventosJPA();
        nomevento = new Evento();
        Usuario de = new Usuario();
        Date hora = new Date();
        de.setIdUsuario(Usuario);
        nomevento.setIdEvento(eventosJPA.aumentarIdevento() + 1);
        nomevento.setIdUsuario(de);
        nomevento.setNombree(nombrevento);
        nomevento.setLugare(lugarevento);
        nomevento.setFechae(fechaevento);
        nomevento.setHorae(horaevento);
        nomevento.setImagene(ruta_archivo);
        eventosJPA.guardareventoJPA(nomevento);

        this.Getir();
        de.setIdUsuario(0);
        this.setIdevento(0);
        this.setNomevento(null);
        this.setLugarevento(null);
        this.setFechaevento(null);
        this.setHoraevento(null);
        this.setImagenevento("");
        this.setRuta_archivo("");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Evento Almacenado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void editevento() {

        eventosJPA = new EventosJPA();
        eventosJPA.editeventoJPA(editarevento);
        copyFile(this.ruta_archivo, streamArchivo, null);
        System.out.println(this.ruta_archivo);
        this.setIdevento(0);
        this.setNomevento(null);
        this.setLugarevento(null);
        this.setFechaevento(null);
        this.setHoraevento(null);
        this.setImagenevento(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Evento Modificado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editarevent').hide();");

    }

    public void capturarevento(Evento capturarevento) {
        this.Usuario = capturarevento.getIdUsuario().getIdUsuario();
        this.editarevento = capturarevento;

    }

    public void eliminarevento(Evento eliminarevento) {

        eventosJPA = new EventosJPA();
        eventosJPA.eliminareventoJPA(eliminarevento);

        this.setIdevento(0);
        this.setNomevento(null);
        this.setLugarevento(null);
        this.setFechaevento(null);
        this.setHoraevento(null);
        this.setImagenevento(null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Evento Eliminado exitosamente!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void LeerId(Evento event) {
        EventosJPA jpa;
        Evento temp;
        try {
            jpa = new EventosJPA();
            temp = jpa.LeerIdEvento(event);
            if (temp != null) {
                this.editarevento = temp;

            }

        } catch (Exception e) {
        }
    }

    //codigo para subir archivo
    public void upload5(FileUploadEvent event) {
        try {
            this.streamArchivo = event.getFile().getInputstream();
            // Portevent es igual a Portada evento

            eventosJPA = new EventosJPA();
            this.setRuta_archivo("PortEvent" + (eventosJPA.aumentarIdevento() + 1) + ".jpg");

        } catch (IOException ex) {
            Logger.getLogger(eventosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upload6(FileUploadEvent event) {
        try {

            this.streamArchivo = event.getFile().getInputstream();
            //this.setRuta_archivo(event.getFile().getFileName());
            this.setRuta_archivo(editarevento.getImagene());

        } catch (IOException ex) {
            Logger.getLogger(eventosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyFile(String fileName, InputStream in, EMail msj) {
        try {

            if (fileName != null) {
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();

                File fi = new File(destination + fileName);
                byte[] fileContent = Files.readAllBytes(fi.toPath());

                if (msj != null) {
                    //Sector de parametrizacion;
                    Mailer cartero = new Mailer();
                    cuentaMail = "museomuna2016@gmail.com";
                    contra = "muna2016";
                    serv = "smtp.gmail.com";
                    puerto = "587";
                    cartero.setCorreo(cuentaMail); // 
                    cartero.setPass(contra);
                    cartero.setSmtpHost(serv);
                    cartero.setSmtpPort(puerto);
                    cartero.enviarCorreo(msj, fileContent);

                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException ex) {
            Logger.getLogger(eventosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    public void verPDF(Evento pdfEvent) {

        this.eventoPdf = pdfEvent;
    }

}
