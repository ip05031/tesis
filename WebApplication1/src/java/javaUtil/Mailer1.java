/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaUtil;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
/**
 *
 * @author Admin
 */
public class Mailer1 {
    private String smtpHost;
    private String smtpPort;
    private String correo;
    private String pass;

    public Mailer1() {
    }

    public void enviarCorreo(EMail1 mail) throws AddressException, MessagingException {

        //Establemecemos los parametros de conexión para el servidor de correos.
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.smtpHost);
        properties.put("mail.smtp.port", this.smtpPort);
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");//Parametro de seguridad.

        //Creamos un objeto que permitirá iniciar sessión en el servidor de correos.
        SMTPAuthenticator auth = new SMTPAuthenticator(this.correo, this.pass);

        //Iniciamos sessión.
        Session session = Session.getInstance(properties, auth);

        InternetAddress remitente;
        //InternetAddress destinatario;

        MimeMessage msj = new MimeMessage(session);
        MimeBodyPart mensaje = new MimeBodyPart();
        MimeMultipart body = new MimeMultipart();

        Transport transport;

        String destinatarios = "";

        for (String dest : mail.getDestinatarios()) {
            destinatarios = destinatarios + dest + ",";
        }

        remitente = new InternetAddress(mail.getRemitente());
        //destinatario = new InternetAddress(mail.getDestinatarios().get(0));
        Address toList[] = InternetAddress.parse(destinatarios);

        //Setea el mensaje del correo
        mensaje.setText(mail.getMensaje());

        //Setea el archivo adjunto MIME
        DataSource dataSource;
        MimeBodyPart adjunto;
        String mime = "";

        /*
        for (Reporte rep : mail.getReportes()) {//Recorremos la lista de reportes
            for (String extension : rep.getExtensiones()) {//Recorremos la lista de extensiones
                adjunto = new MimeBodyPart();

                switch (extension) {
                    case "pdf":
                        mime = "application/pdf";
                        break;
                    case "docx":
                        mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                        break;
                }

                dataSource = new ByteArrayDataSource(rep.exportarBytes(extension), mime);
                adjunto.setDataHandler(new DataHandler(dataSource));
                adjunto.setFileName(rep.getNombre() + "." + extension);
                body.addBodyPart(adjunto);
            }
        }
        */

        body.addBodyPart(mensaje);

        msj.setSender(remitente);
        msj.setSubject(mail.getAsunto());
        //msj.setRecipient(Message.RecipientType.TO, destinatario);
        msj.setRecipients(Message.RecipientType.TO, toList);
        msj.setContent(body);

        transport = session.getTransport("smtp");
        transport.connect(this.smtpHost, this.correo, this.pass);

        transport.sendMessage(msj, toList);

    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    
}
